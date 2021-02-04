package cn.com.lasong.widget;

import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;

import androidx.annotation.Nullable;

import cn.com.lasong.utils.TN;
import cn.com.lasong.widget.touch.MoveView;
import cn.com.lasong.R;
/**
 * Author: zhusong
 * Email: song.zhu@lasong.com.cn
 * Date: 2021/2/4
 * Description:
 */
public class IconMoveView extends MoveView implements View.OnClickListener {
    public IconMoveView(Context context) {
        this(context, null);
    }

    public IconMoveView(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public IconMoveView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        LayoutInflater.from(context).inflate(R.layout.view_icon, this);
        findViewById(R.id.ll_icon).setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        TN.show("点击浮窗");
    }
}
