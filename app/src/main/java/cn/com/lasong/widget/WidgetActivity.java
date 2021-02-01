package cn.com.lasong.widget;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;

import androidx.annotation.Nullable;

import cn.com.lasong.MainActivity;
import cn.com.lasong.R;
import cn.com.lasong.base.BaseActivity;
import cn.com.lasong.widget.dialog.TopSheetDialog;
import cn.com.lasong.widget.utils.ViewHelper;

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
        ViewHelper.transparentStatusBar(this);
    }

    TopSheetDialog dialog;
    public void onClick(View view) {
        int id = view.getId();
        switch (id) {
            case R.id.btn_top_sheet: {
                if (null != dialog)
                    dialog.dismiss();
                dialog = new TopSheetDialog(this);
                dialog.setContentView(R.layout.view_lyric);
                // 可隐藏
                dialog.setCancelable(true);
                // 是否拦截触摸事件
                dialog.setConsumeTouch(false);
                // 在控件外触摸不隐藏
                dialog.setCanceledOnTouchOutside(false);
                dialog.show();
                break;
            }
            case R.id.btn_new_page: {
                Intent intent = new Intent(this, MainActivity.class);
                startActivity(intent);
                break;
            }
        }
    }
}
