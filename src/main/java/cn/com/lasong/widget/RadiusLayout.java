package cn.com.lasong.widget;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.util.AttributeSet;
import android.widget.RelativeLayout;
import cn.com.lasong.widget.utils.ViewHelper;

/**
 * Author: zhusong
 * Email: song.zhu@lasong.com.cn
 * Date: 2021/4/29
 * Description:
 * 圆角背景
 */
public class RadiusLayout extends RelativeLayout {
    public RadiusLayout(Context context) {
        this(context, null);
    }

    public RadiusLayout(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    final Paint mPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
    Path bgPath = new Path();
    Path borderPath = new Path();
    boolean bgCircle;
    // 标记边界需要圆角的值
    int borderFlags;
    int bgColor;
    float bgRadius;
    int borderWidth;
    int borderColor;
    public RadiusLayout(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        TypedArray ta = context.obtainStyledAttributes(attrs, R.styleable.RadiusLayout);
        bgCircle = ta.getBoolean(R.styleable.RadiusLayout_bgCircle, false);
        bgColor = ta.getColor(R.styleable.RadiusLayout_bgColor, Color.TRANSPARENT);
        bgRadius = ta.getDimension(R.styleable.RadiusLayout_bgRadius, 0);
        borderFlags = ta.getInt(R.styleable.RadiusLayout_radiusFlags, ViewHelper.BORDER_ALL);
        borderWidth = ta.getDimensionPixelSize(R.styleable.RadiusLayout_borderWidth, 0);
        borderColor = ta.getColor(R.styleable.RadiusLayout_borderColor, Color.TRANSPARENT);
        ta.recycle();
    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
        final int left = getPaddingLeft();
        final int top = getPaddingTop();
        final int right = w - getPaddingRight();
        final int bottom = h - getPaddingBottom();

        bgPath = ViewHelper.updateRadiusPath(bgPath, left, top, right, bottom,
                bgRadius, bgRadius, borderFlags);

        final float offset = borderWidth * 1.0f / 2;
        final float borderLeft = left + offset;
        final float borderTop = top + offset;
        final float borderRight = right - offset;
        final float borderBottom = bottom - offset;
        borderPath = ViewHelper.updateRadiusPath(borderPath, borderLeft, borderTop, borderRight, borderBottom,
                bgRadius - offset, bgRadius - offset, borderFlags);
    }

    @Override
    protected void dispatchDraw(Canvas canvas) {

        int saveCount = canvas.save();

        final float left = getPaddingLeft();
        final float top = getPaddingTop();
        final float right = getWidth() - getPaddingRight();
        final float bottom = getHeight() - getPaddingBottom();
        final float cx = (right - left) / 2;
        final float cy = (bottom - top) / 2;
        final float radius = Math.min(cx, cy);
        if (bgColor != Color.TRANSPARENT) {
            mPaint.setStyle(Paint.Style.FILL);
            mPaint.setColor(bgColor);
            if (bgCircle) {
                canvas.drawCircle(cx, cy, radius, mPaint);
            } else {
                canvas.drawPath(bgPath, mPaint);
            }
        }

        if (borderWidth > 0 && borderColor != Color.TRANSPARENT) {
            mPaint.setStyle(Paint.Style.STROKE);
            mPaint.setStrokeWidth(borderWidth);
            mPaint.setColor(borderColor);
            if (bgCircle) {
                canvas.drawCircle(cx, cy, radius - borderWidth * 1.0f / 2, mPaint);
            } else {
                canvas.drawPath(bgPath, mPaint);
            }
            canvas.drawPath(borderPath, mPaint);
        }

        canvas.restoreToCount(saveCount);
        super.dispatchDraw(canvas);
    }
}
