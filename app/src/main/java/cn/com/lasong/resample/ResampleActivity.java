package cn.com.lasong.resample;

import android.os.Bundle;
import android.os.Environment;
import android.view.View;

import androidx.annotation.Nullable;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;

import cn.com.lasong.R;
import cn.com.lasong.base.BaseActivity;
import cn.com.lasong.media.AVChannelLayout;
import cn.com.lasong.media.AVSampleFormat;
import cn.com.lasong.media.Resample;

public class ResampleActivity extends BaseActivity {

    private boolean mIsRunning = false;
    private Resample mResample;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_resample);
        mResample = new Resample();
        mResample.init(AVChannelLayout.AV_CH_LAYOUT_STEREO, AVSampleFormat.AV_SAMPLE_FMT_S16.ordinal(), 44100,
                AVChannelLayout.AV_CH_LAYOUT_MONO, AVSampleFormat.AV_SAMPLE_FMT_S16.ordinal(), 44100);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        mResample.release();
        mResample = null;
    }

    public void resample(View view) {
        if (mIsRunning) {
            return;
        }

        mIsRunning = true;

        new Thread() {
            @Override
            public void run() {
                super.run();
                try {
                    InputStream is = getResources().getAssets().open("shimian.pcm");

                    FileOutputStream fos = new FileOutputStream(new File(Environment.getExternalStorageDirectory(), "resample.pcm"));
                    byte[] buf = new byte[1024];
                    byte[] out = new byte[1024];
                    int length = 0;
                    while ((length = is.read(buf)) > 0) {
                        int size = mResample.resample(buf, out);
                        fos.write(out, 0, size);
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                }
                mIsRunning = false;
            }
        }.start();


    }


}
