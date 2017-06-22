package com.trilink.ghbaqi.viewconflict.view;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.View;

import com.trilink.ghbaqi.viewconflict.R;

/**
 * 自定义控件 继承自 View
 * 1. 需要处理 wrap_content ，在 onMeasure（）方法中对 宽高为 wrap_content 时做处理
 * 2. 本身就支持 margin 属性，不需要特殊处理。
 * 3. padding 属性需要特殊考虑 : 计算宽高时需要除去 padding ， 内部绘制图形也要根据实际情况考虑 padding 的影响
 * 4. 自定义属性 建立-读取-使用
 */

public class CircleView extends View {

    private Paint mPaint;
    private int mColor = Color.GREEN;
    private int mPaintWidth;

    public CircleView(Context context) {
        this(context, null);
    }

    public CircleView(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public CircleView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        TypedArray a = context.obtainStyledAttributes(attrs, R.styleable.CircleView);
        mColor = a.getColor(R.styleable.CircleView_circle_color, Color.RED);
        mPaintWidth = a.getInteger(R.styleable.CircleView_paint_width, 5);
        a.recycle();
        init();
    }

    private void init() {
        mPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        mPaint.setColor(mColor);
        mPaint.setStrokeWidth(mPaintWidth);
    }

    /**
     * 对于 wrap_content 时，宽度默认 300 ， 高度默认 100 。
     */
    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);

        int widthMode = MeasureSpec.getMode(widthMeasureSpec);
        int widthSize = MeasureSpec.getSize(widthMeasureSpec);
        int heightMode = MeasureSpec.getMode(heightMeasureSpec);
        int heightSize = MeasureSpec.getSize(heightMeasureSpec);

        if (widthMode == MeasureSpec.AT_MOST && heightMode == MeasureSpec.AT_MOST) {
            setMeasuredDimension(300, 100);
        } else if (widthMode == MeasureSpec.AT_MOST) {
            setMeasuredDimension(300, heightSize);
        } else if (heightMode == MeasureSpec.AT_MOST) {
            setMeasuredDimension(widthSize, 100);
        }
    }

    /**
     * 绘制图形时，根据具体实际情况 需要消除 padding 的影响
     */
    @Override
    protected void onDraw(Canvas canvas) {
        //        super.onDraw(canvas);
        int paddingLeft = getPaddingLeft();
        int paddingTop = getPaddingTop();
        int paddingRight = getPaddingRight();
        int paddingBottom = getPaddingBottom();

        int width = getWidth() - paddingLeft - paddingRight;
        int height = getHeight() - paddingBottom - paddingTop;
        int radius = Math.min(width / 2, height / 2);
        canvas.drawCircle(width / 2 + paddingLeft/2+paddingRight/2, height / 2 + paddingTop/2+paddingBottom/2, radius, mPaint);

    }
}
