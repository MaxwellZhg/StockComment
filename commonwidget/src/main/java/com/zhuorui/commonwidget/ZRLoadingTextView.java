package com.zhuorui.commonwidget;

import android.animation.ValueAnimator;
import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.view.View;
import org.jetbrains.annotations.NotNull;

/**
 * author : PengXianglin
 * e-mail : peng_xianglin@163.com
 * date   : 2019/8/30 14:45
 * desc   : 加载变色的TextView
 */
public class ZRLoadingTextView extends View {

    // 最大值
    private static final float MAX = 100;
    // 系统默认:文字正常时颜色
    private static final int TEXT_COLOR_NORMAL = Color.parseColor("#000000");
    // 系统默认:文字高亮颜色
    private static final int TEXT_COLOR_HIGHLIGHT = Color.parseColor("#FF0000");
    // 绘制方向
    private static final int LEFT = 1, TOP = 2, RIGHT = 3, BOTTOM = 4;

    /**
     * 画笔
     */
    private Paint mPaint;

    /**
     * 控件绘制位置起始的X,Y坐标值
     */
    private int mStartX = 0, mStartY = 0;

    /**
     * 文字正常颜色
     */
    private int mTextColorNormal;

    /**
     * 文字高亮颜色
     */
    private int mTextColorHighLight;

    /**
     * 文字
     */
    private String mText;

    /**
     * 文字大小
     */
    private int mTextSize;

    /**
     * 绘制方向
     */
    private int mDirection;

    /**
     * loading刻度
     */
    private float mProgress = 0;

    /**
     * 是否正在加载,避免开启多个线程绘图
     */
    private boolean mIsLoading = false;


    public ZRLoadingTextView(Context context) {
        this(context, null);
    }

    public ZRLoadingTextView(Context context, AttributeSet attrs) {
        super(context, attrs);

        TypedArray a = context.obtainStyledAttributes(attrs, R.styleable.ZRLoadingTextView);

        mText = a.getString(R.styleable.ZRLoadingTextView_text);
        mTextColorNormal = a.getColor(R.styleable.ZRLoadingTextView_text_color_normal,
                TEXT_COLOR_NORMAL);
        mTextColorHighLight = a.getColor(
                R.styleable.ZRLoadingTextView_text_color_hightlight,
                TEXT_COLOR_HIGHLIGHT);
        // 文字大小
        mTextSize = a.getDimensionPixelSize(R.styleable.ZRLoadingTextView_text_size, 16);
        mDirection = a.getInt(R.styleable.ZRLoadingTextView_direction, LEFT);

        a.recycle();
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        if (TextUtils.isEmpty(mText)) {
            super.onMeasure(widthMeasureSpec, heightMeasureSpec);
            return;
        }

        int width = onMeasureR(0, widthMeasureSpec);
        int height = onMeasureR(1, heightMeasureSpec);
        setMeasuredDimension(width, height);
    }

    /**
     * 计算控件宽高
     *
     * @param attr       属性     [0宽,1高]
     * @param oldMeasure
     * @author Ruffian
     */
    public int onMeasureR(int attr, int oldMeasure) {
        int newSize = 0;
        int mode = MeasureSpec.getMode(oldMeasure);
        int oldSize = MeasureSpec.getSize(oldMeasure);

        switch (mode) {
            case MeasureSpec.EXACTLY:
                newSize = oldSize;
                break;
            case MeasureSpec.AT_MOST:
            case MeasureSpec.UNSPECIFIED:
                float value;
                if (attr == 0) {
                    value = mPaint.measureText(mText);
                    // newSize
                    newSize = (int) (getPaddingLeft() + value + getPaddingRight());

                } else if (attr == 1) {
                    Paint.FontMetrics fontMetrics = mPaint.getFontMetrics();
                    value = Math.abs((fontMetrics.bottom - fontMetrics.top));
                    // newSize
                    newSize = (int) (getPaddingTop() + value + getPaddingBottom());
                }
                break;
        }
        return newSize;
    }

    /**
     * X,Y控件居中绘制 <br/>
     * 对于文本居中绘制<br/>
     * 1.mPaint.measureText(mText)精确度高于mBound.width()
     * 2.文字高度测量:Math.abs((fontMetrics.bottom - fontMetrics.top))
     * 3.http://blog.csdn.net/u014702653/article/details/51985821
     */

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        if (TextUtils.isEmpty(mText)) return;

        // 控件高度/2 + 文字高度/2,绘制文字从文字左下角开始,因此"+"
        Paint.FontMetricsInt fm = mPaint.getFontMetricsInt();
        mStartY = getMeasuredHeight() / 2 - fm.descent
                + (fm.bottom - fm.top) / 2;

        mStartX = (int) (getMeasuredWidth() / 2 - mPaint.measureText(mText) / 2);

        onDrawR(canvas);
    }

    /**
     * 绘制文字
     *
     * @param canvas
     * @param normalOrHightLight [0:正常模式,1:高亮模式]
     * @param start
     * @param end
     * @author Ruffian
     */
    protected void onDrawTextOrBitmap(Canvas canvas, int normalOrHightLight, int start, int end) {
        canvas.save();
        switch (mDirection) {
            case LEFT:
            case RIGHT:

                // X轴画图
                canvas.clipRect(start, 0, end, getMeasuredHeight());

                break;
            case TOP:
            case BOTTOM:

                // Y轴画图
                canvas.clipRect(0, start, getMeasuredWidth(), end);

                break;
        }

        // 绘制文字
        if (normalOrHightLight == 0) {
            mPaint.setColor(mTextColorNormal);
        } else {
            mPaint.setColor(mTextColorHighLight);
        }
        canvas.drawText(mText, mStartX, mStartY, mPaint);

        canvas.restore();
    }

    /**
     * 控件绘制
     *
     * @param canvas
     * @author Ruffian
     */
    public void onDrawR(Canvas canvas) {
        /**
         * 主要思想:绘制两遍文字/图像,通过裁剪画布拼接两部分文字/图像,实现进度绘制的效果
         */

        // 需要变色的宽高总值(长度)
        int drawTotalWidth = 0;
        int drawTotalHeight = 0;

        // X,Y变色的进度实时值
        int spliteXPro = 0;
        int spliteYPro = 0;

        // X,Y变色的最大值(坐标)
        int spliteXMax = 0;
        int spliteYMax = 0;

        // 开始变色的X,Y起始坐标值
        int spliteYStart = 0;
        int spliteXStart = 0;

        Paint.FontMetricsInt fm = mPaint.getFontMetricsInt();


        drawTotalWidth = (int) mPaint.measureText(mText);
        drawTotalHeight = Math.abs(fm.ascent);

        spliteYStart = (fm.descent - fm.top) - Math.abs(fm.ascent)
                + getPaddingTop();
        // 开始裁剪的Y坐标值:(https://img-blog.csdn.net/20160721172427552)图中descent位置+paddingTop

        spliteYMax = Math.abs(fm.top) + (fm.descent);
        // Y变色(裁剪)的进度最大值(坐标):(https://img-blog.csdn.net/20160721172427552)看图

        spliteXPro = (int) ((mProgress / MAX) * drawTotalWidth);
        spliteYPro = (int) ((mProgress / MAX) * drawTotalHeight);

        spliteXStart = mStartX;// 开始裁剪的X坐标值:文字开始绘画的地方
        spliteXMax = mStartX + drawTotalWidth;
        // X变色(裁剪)的进度最大值(坐标):X变色(裁剪)起始位置+需要变色(裁剪)的宽总值(长度)

        switch (mDirection) {
            case TOP:
                // 从上到下,分界线上边是高亮颜色,下边是原始默认颜色
                onDrawTextOrBitmap(canvas, 1, spliteYStart, spliteYStart
                        + spliteYPro);
                onDrawTextOrBitmap(canvas, 0, spliteYStart + spliteYPro, spliteYMax);
                break;
            case BOTTOM:
                // 从下到上,分界线下边是默认颜色 ,上边是高亮颜色
                onDrawTextOrBitmap(canvas, 0, spliteYStart, spliteYMax - spliteYPro);
                onDrawTextOrBitmap(canvas, 1, spliteYMax - spliteYPro, spliteYMax);
                break;
            case LEFT:
                // 从左到右,分界线左边是高亮颜色,右边是原始默认颜色
                onDrawTextOrBitmap(canvas, 1, spliteXStart, spliteXStart
                        + spliteXPro);
                onDrawTextOrBitmap(canvas, 0, spliteXStart + spliteXPro, spliteXMax);
                break;
            case RIGHT:
                // 从右到左,分界线左边是默认颜色 ,右边是高亮颜色
                onDrawTextOrBitmap(canvas, 0, spliteXStart, spliteXMax - spliteXPro);
                onDrawTextOrBitmap(canvas, 1, spliteXMax - spliteXPro, spliteXMax);
                break;
        }
    }

    private void setProgress(float progress) {
        this.mProgress = progress;
        postInvalidate();
    }

    private ValueAnimator anim;

    /**
     * 开始执行loading
     *
     * @param duration 执行时间，设置变色执行的时间
     */
    public void start(final long duration) {
        if (mIsLoading) {
            clear();
        }
        anim = ValueAnimator.ofFloat(0f, MAX);
        anim.setDuration(duration);
        anim.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                float currentValue = (float) animation.getAnimatedValue();
                setProgress(currentValue);
            }
        });
        anim.start();
        mIsLoading = true;
    }

    @Override
    protected void onDetachedFromWindow() {
        super.onDetachedFromWindow();
        clear();
    }

    public void clear() {
        // 清除动画
        if (anim != null && anim.isRunning())
            anim.cancel();
        mIsLoading = false;
        setProgress(0);
    }

    public void setText(@NotNull String text) {
        if (TextUtils.isEmpty(text)) return;

        this.mText = text;

        // 初始化画笔,绘制的范围
        Rect mBound = new Rect();
        mPaint = new Paint();
        mPaint.setAntiAlias(true);
        mPaint.setStyle(Paint.Style.FILL);
        mPaint.setTextSize(mTextSize);
        mPaint.getTextBounds(mText, 0, mText.length(), mBound);

        postInvalidate();
    }
}
