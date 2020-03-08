package cn.com.lasong.lyric;

import android.content.res.AssetFileDescriptor;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;
import android.widget.TextView;

import androidx.annotation.Nullable;

import java.io.IOException;
import java.util.Timer;
import java.util.TimerTask;

import cn.com.lasong.R;
import cn.com.lasong.base.BaseActivity;
import cn.com.lasong.media.MP3DecodeThread;
import cn.com.lasong.media.MP3Player;
import cn.com.lasong.utils.FormatUtils;
import cn.com.lasong.utils.ILog;
import cn.com.lasong.widget.lyric.ITimeProvider;
import cn.com.lasong.widget.lyric.LrcView;
import cn.com.lasong.widget.lyric.Lyric;
import cn.com.lasong.widget.lyric.LyricUtils;

/**
 * Author: zhusong
 * Email: song.zhu@lasong.com.cn
 * Date: 2020-03-04
 * Description: 歌词展示页面
 */
public class LyricActivity extends BaseActivity implements View.OnClickListener, ITimeProvider, Runnable {

    private ImageButton mBtnPlay;
    private TextView mTvSong;
    private TextView mTvSinger;
    private TextView mTvDuration;
    private LrcView mViewLrc;

    // Play
    // 负责解码
    private MP3DecodeThread mThread;
    // 负责播放
    private MP3Player mPlayer;
    // 歌词
    private Lyric mLrc;
    private String mTotalDuration;
    private Timer mTimer;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lyric);
        mBtnPlay = findViewById(R.id.btn_play);
        mTvSong = findViewById(R.id.tv_song);
        mTvSinger = findViewById(R.id.tv_singer);
        mTvDuration = findViewById(R.id.tv_duration);
        mViewLrc = findViewById(R.id.view_lrc);
        mBtnPlay.setOnClickListener(this);
        mPlayer = new MP3Player();
        mViewLrc.setProvider(this);

        Lyric lyric = null;
        try {
            lyric = LyricUtils.readLyric(getAssets().open("geqian.krc"));
        } catch (IOException e) {
            ILog.e(e);
        }

        if (null != lyric) {
            mTvSinger.setText(lyric.ar);
            mTvSong.setText(lyric.ti);
            mTotalDuration = FormatUtils.getDuration(lyric.total - lyric.offset);
            updateTime();
        }
        mLrc = lyric;
    }

    private void updateTime() {
        if (null != mTvDuration) {
            mTvDuration.setText(String.format("%s/%s", FormatUtils.getDuration(null != mPlayer ? getCurrentPosition() : 0), mTotalDuration));
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        stopMP3();
    }

    private void stopMP3() {
        if (null != mTimer) {
            mTimer.cancel();
            mTimer = null;
        }
        if (null != mThread) {
            mThread.interrupt();
            // 等待线程技术
            while (!mThread.isDone()) {
                try {
                    Thread.sleep(10);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
            mThread = null;
        }
    }

    private void startPlay() {
        AssetFileDescriptor afd = null;
        try {
            afd = getAssets().openFd("geqian.mp3");
        } catch (IOException e) {
            ILog.e(e);
        }

        if (null == afd) {
            return;
        }
        mThread = new MP3DecodeThread(afd);
        mThread.setCallback(mPlayer);
        mThread.start();

        mTimer = new Timer();
        TimerTask timerTask = new TimerTask() {
            @Override
            public void run() {
                runOnUiThread(LyricActivity.this);
            }
        };
        mTimer.schedule(timerTask, 0, 1000);
    }
    @Override
    public void onClick(View v) {
        if (v == mBtnPlay) {
            stopMP3();

            startPlay();
            if (null != mViewLrc) {
                mViewLrc.showLyric(mLrc);
            }
        }
    }

    @Override
    public void run() {
        updateTime();
    }

    @Override
    public long getCurrentPosition() {
        if (null != mPlayer) {
            return mPlayer.getCurrentPosition();
        }
        return 0;
    }

    @Override
    public long getPollingInterval() {
        return 50;
    }
}
