package cn.com.lasong.lyric;

import android.media.AudioFormat;
import android.media.AudioManager;
import android.media.AudioTimestamp;
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
    // 间隔时间
    private final int INTERVAL = 1000 / PERIOD_FACTOR;
    // 采样率
    private int mSampleRate;

    @Override
    public long getCurrentPosition() {
        if (null == mAudioTrack || mSampleRate <= 0) {
            return 0;
        }
        int numFramesPlayed = mAudioTrack.getPlaybackHeadPosition();
        long audioTimeMs = (numFramesPlayed * 1000L) / mSampleRate;
        return audioTimeMs;
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
        mSampleRate = sampleRate;
        // 获取最小buffer大小
        int bufferSize = AudioTrack.getMinBufferSize(sampleRate,
                audioFormat, AudioFormat.ENCODING_PCM_16BIT);
        mAudioTrack = new AudioTrack(AudioManager.STREAM_MUSIC, sampleRate, audioFormat,
                AudioFormat.ENCODING_PCM_16BIT, bufferSize, AudioTrack.MODE_STREAM);
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
