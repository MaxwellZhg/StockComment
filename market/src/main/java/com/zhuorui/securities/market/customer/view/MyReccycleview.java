package com.zhuorui.securities.market.customer.view;

import android.content.Context;
import android.util.AttributeSet;
import android.view.MotionEvent;
import androidx.recyclerview.widget.RecyclerView;

public class MyReccycleview extends RecyclerView {
    public MyReccycleview(Context context) {
        super(context);
    }

    public MyReccycleview(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public MyReccycleview(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    public boolean onInterceptTouchEvent(MotionEvent e) {
        return false;
    }

    @Override
    public boolean onTouchEvent(MotionEvent e) {
        return true;
    }
}
