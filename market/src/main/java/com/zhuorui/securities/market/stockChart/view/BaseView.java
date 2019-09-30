package com.zhuorui.securities.market.stockChart.view;

import android.content.Context;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.view.View;
import android.view.animation.*;
import android.widget.LinearLayout;
import androidx.annotation.Nullable;
import com.zhuorui.securities.market.stockChart.CoupleChartGestureListener;
import com.zhuorui.securities.market.stockChart.data.KLineDataManage;
import com.zhuorui.securities.market.stockChart.data.TimeDataManage;
import com.zhuorui.securities.market.stockChart.event.BaseEvent;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

public class BaseView extends LinearLayout {

    public boolean landscape = false;//是否横屏模式
    public int precision = 3;//小数精度
    public Paint mPaint;

    public BaseView(Context context) {
        this(context, null);
    }

    public OnHighlightValueSelectedListener mHighlightValueSelectedListener;
    public CoupleChartGestureListener gestureListenerLine;
    public CoupleChartGestureListener gestureListenerBar;
    public CoupleChartGestureListener gestureListenerCandle;

    public BaseView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        mPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
    }

    public void setPrecision(int precision) {
        this.precision = precision;
    }

    public void setHighlightValueSelectedListener(OnHighlightValueSelectedListener l) {
        mHighlightValueSelectedListener = l;
    }

    public interface OnHighlightValueSelectedListener {
        void onDayHighlightValueListener(TimeDataManage mData, int index, boolean isSelect);

        void onKHighlightValueListener(KLineDataManage data, int index, boolean isSelect);
    }

    public CoupleChartGestureListener getGestureListenerLine() {
        return gestureListenerLine;
    }

    public CoupleChartGestureListener getGestureListenerBar() {
        return gestureListenerBar;
    }
    public CoupleChartGestureListener getGestureListenerCandle() {
        return gestureListenerCandle;
    }

    /**
     * 分时图最后一点的圆圈动画
     * @param heartbeatView
     */
    public void playHeartbeatAnimation(final View heartbeatView) {
        AnimationSet swellAnimationSet = new AnimationSet(true);
        swellAnimationSet.addAnimation(new ScaleAnimation(1.0f, 2.0f, 1.0f, 2.0f, Animation.RELATIVE_TO_SELF, 0.5f, Animation.RELATIVE_TO_SELF, 0.5f));
        swellAnimationSet.setDuration(1000);
        swellAnimationSet.setInterpolator(new AccelerateInterpolator());
        swellAnimationSet.setFillAfter(true);//动画终止时停留在最后一帧，不然会回到没有执行之前的状态
        heartbeatView.startAnimation(swellAnimationSet);
        swellAnimationSet.setAnimationListener(new Animation.AnimationListener() {
            @Override
            public void onAnimationStart(Animation animation) {
            }

            @Override
            public void onAnimationRepeat(Animation animation) {
            }

            @Override
            public void onAnimationEnd(Animation animation) {
                AnimationSet shrinkAnimationSet = new AnimationSet(true);
                shrinkAnimationSet.addAnimation(new ScaleAnimation(2.0f, 1.0f, 2.0f, 1.0f, Animation.RELATIVE_TO_SELF, 0.5f, Animation.RELATIVE_TO_SELF, 0.5f));
                shrinkAnimationSet.setDuration(1000);
                shrinkAnimationSet.setInterpolator(new DecelerateInterpolator());
                shrinkAnimationSet.setFillAfter(false);
                heartbeatView.startAnimation(shrinkAnimationSet);// 动画结束时重新开始，实现心跳的View
                shrinkAnimationSet.setAnimationListener(new Animation.AnimationListener() {
                    @Override
                    public void onAnimationStart(Animation animation) {
                    }

                    @Override
                    public void onAnimationEnd(Animation animation) {
                        playHeartbeatAnimation(heartbeatView);
                    }

                    @Override
                    public void onAnimationRepeat(Animation animation) {

                    }
                });
            }
        });
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onEventMainThread(BaseEvent event) {

    }

}
