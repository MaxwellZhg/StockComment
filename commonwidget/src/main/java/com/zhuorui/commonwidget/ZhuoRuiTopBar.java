package com.zhuorui.commonwidget;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Color;
import android.text.TextPaint;
import android.util.AttributeSet;
import android.util.TypedValue;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;
import com.zhuorui.securities.base2app.util.StatusBarUtil;
import me.yokeyword.fragmentation.ISupportActivity;

/**
 * author : liuwei
 * e-mail : vsanliu@foxmail.com
 * date   : 2019-08-20 15:38
 * desc   : 自定义TopBar
 */
public class ZhuoRuiTopBar extends FrameLayout {

    private View mTitleView;
    private View mBackView;
    private View mShareView;
    private Boolean mShare;

    public ZhuoRuiTopBar(Context context) {
        this(context, null);
    }

    public ZhuoRuiTopBar(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public ZhuoRuiTopBar(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        TypedArray a = getContext().obtainStyledAttributes(attrs, R.styleable.ZhuoRuiTopBar);
        String mTitle = a.getString(R.styleable.ZhuoRuiTopBar_zr_topbarTitle);
        mShare= a.getBoolean(R.styleable.ZhuoRuiTopBar_zr_shareVisibility,false);
        setBackView(getBackView());
        setTitleView(getTitleView());
        setTitle(mTitle);
        setShareView(getShareView());
        setBackgroundColor(Color.parseColor("#211F2A"));
        setPadding(0, StatusBarUtil.getStatusBarHeight(context), 0, 0);
        if(mShare){
            mShareView.setVisibility(VISIBLE);
        }else{
            mShareView.setVisibility(INVISIBLE);
        }
        setBackClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
                Context context = view.getContext();
                if (context instanceof ISupportActivity) {
                    ((ISupportActivity) context).onBackPressedSupport();
                }
            }
        });
    }

    public void setTitleView(View v) {
        if (v == null) return;
        if (mTitleView != null) removeView(mTitleView);
        mTitleView = v;
        addView(v);
    }

    private View getTitleView() {
        TextView tv = new TextView(getContext());
        tv.setTextColor(Color.parseColor("#FFFFFF"));
        tv.setTextSize(TypedValue.COMPLEX_UNIT_SP, 20);
        TextPaint tp = tv.getPaint();
        tp.setFakeBoldText(true);
        LayoutParams lp = new LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT, Gravity.CENTER);
        tv.setLayoutParams(lp);
        return tv;
    }

    public void setTitle(String mTitle) {
        if (mTitleView != null && mTitleView instanceof TextView) {
            ((TextView) mTitleView).setText(mTitle);
        }
    }

    public void setBackView(View v) {
        if (v == null) return;
        if (mBackView != null) removeView(mBackView);
        mBackView = v;
        addView(mBackView);
    }

    private View getBackView() {
        ImageView iv = new ImageView(getContext());
        iv.setImageResource(R.mipmap.ic_arrow_left_white);
        float density = getResources().getDisplayMetrics().density;
        int wh = (int) (density * 44f);
        int padding = (int) (density * 12);
        FrameLayout.LayoutParams lp = new LayoutParams(wh, wh, Gravity.LEFT | Gravity.CENTER_VERTICAL);
        iv.setLayoutParams(lp);
        iv.setPadding(padding, padding, padding, padding);
        return iv;
    }

    public void setBackClickListener(OnClickListener l) {
        if (mBackView != null) mBackView.setOnClickListener(l);
    }

    public void setShareView(View v) {
        if (v == null) return;
        if (mShareView != null) removeView(mShareView);
        mShareView = v;
        addView(mShareView);
    }

    private View getShareView() {
        ImageView iv = new ImageView(getContext());
        iv.setImageResource(R.mipmap.share_more);
        float density = getResources().getDisplayMetrics().density;
        int wh = (int) (density * 44f);
        int padding = (int) (density * 12);
        FrameLayout.LayoutParams lp = new LayoutParams(wh, wh, Gravity.RIGHT | Gravity.CENTER_VERTICAL);
        iv.setLayoutParams(lp);
        iv.setPadding(padding, padding, padding, padding);
        return iv;
    }
}
