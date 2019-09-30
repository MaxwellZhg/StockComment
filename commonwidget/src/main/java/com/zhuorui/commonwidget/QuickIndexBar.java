package com.zhuorui.commonwidget;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;

/**
 * Created by Maxwell.
 * E-mail: maxwell_smith@163.com
 * Date: 2019/8/19
 * Desc:
 */
public class QuickIndexBar extends View {

    private String[] indexArr = {"A", "B", "C", "D", "E", "F", "G", "H",
            "I", "J", "K", "L", "M", "N", "O", "P", "Q", "R", "S", "T", "U",
            "V", "W", "X", "Y", "Z"};

    private Paint paint;
    private int width;
    private float cellHeight; //每个字母所占的高度

    private int lastIndex = -1;//记录上次的触摸字母的索引
    private int mDimensionPixelSize;
    private int mTextNormalColor;
    private int mTextPressColor;
    private int mBgColor;

    public QuickIndexBar(Context context) {
        this(context, null);
    }

    public QuickIndexBar(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public QuickIndexBar(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        init(context, attrs);
    }

    private void init(Context context, AttributeSet attrs) {

        //获取自定义控件的属性，返回一个数组
        TypedArray array = context.obtainStyledAttributes(attrs, R.styleable.QuickIndexBar);

        //字母大小
        mDimensionPixelSize = array.getDimensionPixelSize(R.styleable.QuickIndexBar_text_size, 40);

        //字母普通颜色
        mTextNormalColor = array.getColor(R.styleable.QuickIndexBar_text_normal_color, Color.parseColor("#ffffff"));

        //字母出触摸颜色
        mTextPressColor = array.getColor(R.styleable.QuickIndexBar_text_press_color, Color.parseColor("#000000"));

        //背景颜色
        mBgColor = array.getColor(R.styleable.QuickIndexBar_bg_color, Color.parseColor("#00000000"));

        setBackgroundColor(mBgColor);


        paint = new Paint(Paint.ANTI_ALIAS_FLAG); //抗锯齿
        paint.setColor(mTextNormalColor);
        paint.setTextSize(mDimensionPixelSize);
        paint.setDither(true);
        paint.setTextAlign(Paint.Align.CENTER); //设置文本绘制起点是文字边框底部的中心
    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
        width = getMeasuredWidth();
        //每个字母所占的高度
        cellHeight = getMeasuredHeight() * 1f / indexArr.length;
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        for (int i = 0; i < indexArr.length; i++) {
            //绘制文本x坐标: width/2;
            float x = width / 3;
            //绘制文本y坐标: 格子高度的一半 + 文本高度的一半 + position*格子高度
            //获取文本高度
            int textHeight = getTextHeight(indexArr[i]);
            float y = cellHeight / 3 + textHeight / 3 + i * cellHeight;
            //文本绘制起点是文字边框底部的中心
            //绘制到正中心

            //引起重绘,点击字母改变字母颜色
            paint.setColor(lastIndex == i ? mTextPressColor : mTextNormalColor);

            //x, y:绘制起点是文字底边中心
            canvas.drawText(indexArr[i], x, y, paint);
        }
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:
            case MotionEvent.ACTION_MOVE:
                //计算触摸点对应的字母:根据触摸点的y坐标除以cellHeight,得到的值就是字母对应的索引;
                float y = event.getY();
                int index = (int) (y / cellHeight); //得到字母对应的索引
                if (lastIndex != index) {
                    //说明当前触摸字母和上一个不是同一个字母
                    // Log.i("info", "onTouchEvent: " + indexArr[index]);
                    //对index做安全性的检查
                    if (index >= 0 && index < indexArr.length) {
                        if (listener != null) {
                            listener.onTouchLetter(indexArr[index]);
                        }
                    }
                }
                lastIndex = index;
                break;
            case MotionEvent.ACTION_UP:
                lastIndex = -1;//重置
                break;
        }
        //引起重绘,点击字母改变字母颜色，内部回调用onDraw()方法
        invalidate();

        return true;
    }

    /**
     * 获取Paint绘制文本高度
     *
     * @param text
     * @return
     */
    private int getTextHeight(String text) {
        Rect rect = new Rect();
        paint.getTextBounds(text, 0, text.length(), rect);
        return rect.height();
    }

    private onTouchLetterListener listener;

    public void setonTouchLetterListener(onTouchLetterListener listener) {
        this.listener = listener;
    }

    /**
     * 触摸字母的监听
     */
    public interface onTouchLetterListener {
        void onTouchLetter(String letter);
    }
}
