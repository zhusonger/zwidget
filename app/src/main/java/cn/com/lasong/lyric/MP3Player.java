package cn.com.lasong.lyric;

import android.media.AudioFormat;
import android.media.AudioManager;
import android.media.AudioTrack;
import android.media.MediaFormat;

import java.nio.ByteBuffer;

import cn.com.lasong.utils.ILog;
import cn.com.lasong.widget.lyric.ITimeProvider;

/**
 * Author: zhusong
 * Email: song.zhu@lasong.com.cn
 * Date: 2020-03-05
 * Description: MP3播放器
 */
public class MP3Player implements ITimeProvider, IMP3DecodeCallback{

    private final int PERIOD_FACTOR = 20;
    private final int INTERVAL = 1000 / PERIOD_FACTOR;

    @Override
    public long getCurrentPosition() {
        if (null == mAudioTrack) {
            return 0;
        }
        return 0;
    }

    @Override
    public long getPollingInterval() {
        return INTERVAL;
    }

    //===========播放相关============//
    private AudioTrack mAudioTrack = null;
    @Override
    public void onFormat(MediaFormat format) {
        if (null == format) {
            return;
        }
        int sampleRate = format.getInteger(MediaFormat.KEY_SAMPLE_RATE);
        int channelCount = format.getInteger(MediaFormat.KEY_CHANNEL_COUNT);
        int audioFormat = channelCount > 1 ? AudioFormat.CHANNEL_OUT_STEREO : AudioFormat.CHANNEL_OUT_MONO;
        // 获取最小buffer大小
        int bufferSize = AudioTrack.getMinBufferSize(sampleRate,
                audioFormat, AudioFormat.ENCODING_PCM_16BIT);
        mAudioTrack = new AudioTrack(AudioManager.STREAM_MUSIC, sampleRate, audioFormat,
                AudioFormat.ENCODING_PCM_16BIT, bufferSize, AudioTrack.MODE_STREAM);
        // sampleRate是一秒的采样率, 我们周期根据需要调整除数, 1000/20 就是50ms一次回调
        mAudioTrack.setPositionNotificationPeriod(sampleRate / PERIOD_FACTOR);
        mAudioTrack.setPlaybackPositionUpdateListener(new AudioTrack.OnPlaybackPositionUpdateListener() {
            @Override
            public void onMarkerReached(AudioTrack track) {
                ILog.d("onMarkerReached:" + track.getNotificationMarkerPosition()+", "+track.getPlaybackHeadPosition());
            }

            @Override
            public void onPeriodicNotification(AudioTrack track) {
                ILog.d("onPeriodicNotification:" + track.getNotificationMarkerPosition()+", "+track.getPlaybackHeadPosition());
            }
        });
        mAudioTrack.play();
    }

    @Override
    public void onDrain(ByteBuffer buffer, long presentationTimeUs) {
        if (null == mAudioTrack) {
            return;
        }
        if (null == buffer || buffer.remaining() <= 0) {
            return;
        }
        byte[] data = new byte[buffer.remaining()];
        buffer.get(data);
        mAudioTrack.write(data, 0, data.length);
    }

    @Override
    public void onEndOfStream(int err) {
        if (null != mAudioTrack) {
            try {
                mAudioTrack.stop();
                mAudioTrack.release();
                mAudioTrack = null;
            } catch (Exception e) {
                ILog.e(e);
            }
        }
    }
}
