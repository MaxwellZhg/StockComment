package com.zhuorui.commonwidget;

import android.animation.ObjectAnimator;
import android.content.Context;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.LinearInterpolator;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;
import com.zhuorui.securities.base2app.util.AnimationBuild;

/**
 * author : liuwei
 * e-mail : vsanliu@foxmail.com
 * date   : 2019-08-27 18:39
 * desc   :
 */
public class ZRLoadingView extends FrameLayout {

    private ObjectAnimator rotate;//旋转动画
    private ImageView ivCircle;
    private TextView tvMsg;

    public ZRLoadingView(Context context) {
        this(context, null);
    }

    public ZRLoadingView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public ZRLoadingView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        inflate(getContext(), R.layout.layout_loading_view, this);
        ivCircle = findViewById(R.id.net_iv_loading);
        tvMsg = findViewById(R.id.tv_msg);
        rotate = new AnimationBuild.PropertyAnimationBuilder()
                .animation(ivCircle, AnimationBuild.ROTATION, 0f, 359f)
                .duration(1200)
                .build();
        rotate.setRepeatCount(Animation.INFINITE);
        rotate.setInterpolator(new LinearInterpolator());
    }

    /**
     * 开启动画
     */
    public void startAnimation() {
        if (!rotate.isRunning() && !rotate.isRunning()) {
            rotate.start();
        }
    }

    /**
     * 关闭动画
     */
    public void stopAnimation() {
        if (rotate.isRunning() && rotate.isRunning()) {
            rotate.cancel();
        }
    }

    @Override
    protected void onWindowVisibilityChanged(int visibility) {
        super.onWindowVisibilityChanged(visibility);
        if (visibility != View.VISIBLE)
            stopAnimation();
    }

    public void setMessage(CharSequence msg) {
        if (TextUtils.isEmpty(msg)) {
            tvMsg.setVisibility(GONE);
        } else {
            tvMsg.setVisibility(VISIBLE);
            tvMsg.setText(msg);
        }
    }

}
