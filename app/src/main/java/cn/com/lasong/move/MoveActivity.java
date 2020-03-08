package cn.com.lasong.move;

import android.os.Bundle;

import androidx.annotation.Nullable;

import cn.com.lasong.R;
import cn.com.lasong.lyric.LyricActivity;

/**
 * Author: zhusong
 * Email: song.zhu@lasong.com.cn
 * Date: 2020-03-08
 * Description: 移动控件展示Demo
 */
public class MoveActivity extends LyricActivity {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public void setContentView(int layoutResID) {
        super.setContentView(R.layout.activity_move);
    }
}
