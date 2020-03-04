package cn.com.lasong.widget.touch;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.LinearLayout;

import androidx.annotation.Nullable;

/**
 * Author: zhusong
 * Email: song.zhu@kascend.com
 * Date: 2020/03/04
 * Description: 可移动控件
 */
public class MoveView extends LinearLayout {
    public MoveView(Context context) {
        this(context, null);
    }

    public MoveView(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public MoveView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }


}
