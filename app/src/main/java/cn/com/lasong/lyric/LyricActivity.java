package cn.com.lasong.lyric;

import android.content.res.AssetFileDescriptor;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.Nullable;

import java.io.IOException;

import cn.com.lasong.R;
import cn.com.lasong.base.BaseActivity;
import cn.com.lasong.utils.FormatUtils;
import cn.com.lasong.utils.ILog;
import cn.com.lasong.widget.lyric.LrcView;
import cn.com.lasong.widget.lyric.Lyric;
import cn.com.lasong.widget.lyric.LyricUtils;

/**
 * Author: zhusong
 * Email: song.zhu@lasong.com.cn
 * Date: 2020-03-04
 * Description: 歌词展示页面
 */
public class LyricActivity extends BaseActivity implements View.OnClickListener {

    private Button mBtnPlay;
    private TextView mTvSong;
    private LinearLayout mLlDesc;
    private TextView mTvSinger;
    private TextView mTvDuration;
    private LrcView mViewLrc;

    // Play
    // 负责解码
    private MP3DecodeThread mThread;
    // 负责播放
    private MP3Player mPlayer;

    private Lyric mLrc;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lyric);
        mBtnPlay = findViewById(R.id.btn_play);
        mTvSong = findViewById(R.id.tv_song);
        mLlDesc = findViewById(R.id.ll_desc);
        mTvSinger = findViewById(R.id.tv_singer);
        mTvDuration = findViewById(R.id.tv_duration);
        mViewLrc = findViewById(R.id.view_lrc);
        mBtnPlay.setOnClickListener(this);
        mPlayer = new MP3Player();
        mViewLrc.setProvider(mPlayer);

        Lyric lyric = null;
        try {
            lyric = LyricUtils.readLyric(getAssets().open("geqian.krc"));
        } catch (IOException e) {
            ILog.e(e);
        }

        if (null != lyric) {
            mTvSinger.setText(lyric.ar);
            mTvSong.setText(lyric.ti);
            mTvDuration.setText(String.format("00:00/%s", FormatUtils.getDuration(lyric.total - lyric.offset)));
        }
        mLrc = lyric;
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        stopMP3();
    }

    private void stopMP3() {
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
    }
    @Override
    public void onClick(View v) {
        stopMP3();

        startPlay();
        if (null != mViewLrc) {
            mViewLrc.showLyric(mLrc);
        }
    }
}
