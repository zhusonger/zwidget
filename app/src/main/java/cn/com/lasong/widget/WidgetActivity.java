package cn.com.lasong.widget;

import android.os.Bundle;
import android.view.View;

import androidx.annotation.Nullable;

import cn.com.lasong.R;
import cn.com.lasong.base.BaseActivity;
import cn.com.lasong.widget.dialog.TopSheetDialog;

/**
 * Author: zhusong
 * Email: song.zhu@lasong.com.cn
 * Date: 2020/8/12
 * Description:
 */
public class WidgetActivity extends BaseActivity {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_widget);
    }

    public void onClick(View view) {
        int id = view.getId();
        switch (id) {
            case R.id.btn_top_sheet: {
                TopSheetDialog dialog = new TopSheetDialog(this);
                dialog.setContentView(R.layout.view_lyric);
                // 是否拦截触摸事件
                dialog.setConsumeTouch(false);
                // 在控件外触摸不隐藏
                dialog.setCanceledOnTouchOutside(false);
                // 可隐藏
                dialog.setCancelable(true);
                dialog.show();
                break;
            }
        }
    }
}
