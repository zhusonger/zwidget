package cn.com.lasong;

import android.os.Bundle;
import android.util.Log;

import androidx.annotation.Nullable;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import cn.com.lasong.base.BaseActivity;
import cn.com.lasong.utils.ILog;
import cn.com.lasong.utils.T;

/**
 * Author: zhusong
 * Email: song.zhu@lasong.com.cn
 * Date: 2020-03-04
 * Description:
 */
public class MainActivity extends BaseActivity {

    private RecyclerView mRvMain;
    private MainAdapter mAdapter;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mRvMain = findViewById(R.id.rv_main);
        mRvMain.setLayoutManager(new LinearLayoutManager(this));
        mAdapter = new MainAdapter();
        mRvMain.addItemDecoration(new DividerItemDecoration(this, DividerItemDecoration.VERTICAL));
        mRvMain.setAdapter(mAdapter);
        ILog.setLogLevel(Log.DEBUG);
    }

    private long mLastBackTs;
    @Override
    public void onBackPressed() {
        long now = System.currentTimeMillis();
        if (mLastBackTs <= 0) {
            mLastBackTs = now;
        }
        long interval = (now - mLastBackTs);
        if (interval > 0 &&  interval <= 1000) {
            super.onBackPressed();
        } else {
            T.show("Click Back Again!");
        }
        mLastBackTs = now;

    }
}
