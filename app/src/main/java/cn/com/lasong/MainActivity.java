package cn.com.lasong;

import android.os.Bundle;
import android.util.Log;

import androidx.annotation.Nullable;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import cn.com.lasong.base.BaseActivity;
import cn.com.lasong.utils.ILog;

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
        mRvMain.setAdapter(mAdapter);
        ILog.setLogLevel(Log.DEBUG);
    }
}
