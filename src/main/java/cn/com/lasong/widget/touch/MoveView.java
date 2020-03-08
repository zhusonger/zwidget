package cn.com.lasong.widget.touch;

import android.content.Context;
import android.graphics.PointF;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.widget.RelativeLayout;

import androidx.annotation.Nullable;

/**
 * Author: zhusong
 * Email: song.zhu@kascend.com
 * Date: 2020/03/04
 * Description: 可移动控件
 */
public class MoveView extends RelativeLayout {
    public MoveView(Context context) {
        this(context, null);
    }

    public MoveView(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public MoveView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    // Step1
    // 返回true拦截事件, 不往下传递
    @Override
    public boolean dispatchTouchEvent(MotionEvent ev) {
        boolean ret = super.dispatchTouchEvent(ev);
        return true;
    }

    // Step2
    // 本控件内处理, 按照控件正常拦截机制拦截
    @Override
    public boolean onInterceptTouchEvent(MotionEvent ev) {
        boolean ret = super.onInterceptTouchEvent(ev);
        return ret;
    }

    // 记录最新的位置
    private PointF mLastP = new PointF();
    // Step3 当前View拦截到事件就进行触摸拖动
    @Override
    public boolean onTouchEvent(MotionEvent event) {
        boolean ret = super.onTouchEvent(event);
        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN: {
                mLastP.x = event.getRawX();
                mLastP.y = event.getRawY();
                break;
            }

            case MotionEvent.ACTION_MOVE: {
                float currentX = event.getRawX();
                float currentY = event.getRawY();
                float offsetX = currentX - mLastP.x;
                float offsetY = currentY - mLastP.y;
                setX(getX() + offsetX);
                setY(getY() + offsetY);
                // 更新最后的位置坐标
                mLastP.x = currentX;
                mLastP.y = currentY;
                break;
            }
        }
        return true;
    }
}
