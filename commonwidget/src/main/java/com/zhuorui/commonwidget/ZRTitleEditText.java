package com.zhuorui.commonwidget;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.drawable.Drawable;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.util.AttributeSet;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewParent;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;
import com.zhuorui.commonwidget.impl.IZRTitleView;

/**
 * author : liuwei
 * e-mail : vsanliu@foxmail.com
 * date   : 2019-08-23 10:09
 * desc   :
 */
public class ZRTitleEditText extends FrameLayout implements View.OnFocusChangeListener, TextWatcher, IZRTitleView {

    public static final int HORIZONTAL = 0;
    public static final int VERTICAL = 1;

    private TextView vTitle;
    public EditText vEt;
    public ImageView vRightIcon;
    //    private ImageView vRImg;
    private int mOrientation = -1;
    private Drawable mRightBtnDraw;
    private boolean mTitleBaseline = false;


    public ZRTitleEditText(Context context) {
        this(context, null);
    }

    public ZRTitleEditText(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public ZRTitleEditText(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        TypedArray a = getContext().obtainStyledAttributes(attrs, R.styleable.ZRTitleEditText);
        int orientation = a.getInt(R.styleable.ZRTitleEditText_zr_teditOrientation, VERTICAL);
        String title = a.getString(R.styleable.ZRTitleEditText_zr_teditTitle);
        String text = a.getString(R.styleable.ZRTitleEditText_zr_teditText);
        String hiht = a.getString(R.styleable.ZRTitleEditText_zr_teditHint);
        mTitleBaseline = a.getBoolean(R.styleable.ZRTitleTextView_zr_titleWidthBaseline, mTitleBaseline);
        if (TextUtils.isEmpty(hiht)) {
            hiht = "请输入" + title;
        }
        setOrientation(orientation);
        setTitle(title);
        setText(text);
        setHint(hiht);
        setRightIcon(a);
        a.recycle();
        if (mTitleBaseline) {
            post(new Runnable() {
                @Override
                public void run() {
                    titleBasel();
                }
            });

        }
    }

    private void titleBasel() {
        ViewParent parent = getParent();
        if (parent != null && parent instanceof ViewGroup) {
            ViewGroup group = (ViewGroup) parent;
            for (int i = 0, l = group.getChildCount(); i < l; i++) {
                View v = group.getChildAt(i);
                if (v != ZRTitleEditText.this && v instanceof IZRTitleView) {
                    ((IZRTitleView) v).setTitleWidth(vTitle.getWidth());
                }
            }

        }
    }

    public void setTitleWidth(int width) {
        if (width > 0) {
            ViewGroup.LayoutParams params = vTitle.getLayoutParams();
            params.width = width;
            vTitle.setLayoutParams(params);
        }
    }

    private void setRightIcon(TypedArray a) {
        vRightIcon = findViewById(R.id.iv_right_icon);
        if (vRightIcon == null) return;
        boolean visible = a.getBoolean(R.styleable.ZRTitleEditText_zr_iconVisible, false);
        if (visible) {
            vRightIcon.setVisibility(VISIBLE);
            int width = a.getDimensionPixelOffset(R.styleable.ZRTitleEditText_zr_iconWidth, 0);
            int hight = a.getDimensionPixelOffset(R.styleable.ZRTitleEditText_zr_iconHight, 0);
            int resId = a.getResourceId(R.styleable.ZRTitleEditText_zr_iconSrc, 0);
            ViewGroup.LayoutParams params = vRightIcon.getLayoutParams();
            params.width = width > 0 ? width : ViewGroup.LayoutParams.WRAP_CONTENT;
            params.height = hight > 0 ? hight : ViewGroup.LayoutParams.WRAP_CONTENT;
            vRightIcon.setImageResource(resId);
        } else {
            vRightIcon.setVisibility(GONE);
        }

    }

    public void setHint(String hiht) {
        vEt.setHint(hiht);
    }

    public void setText(String text) {
        vEt.setText(text);
    }

    public void setTitle(String title) {
        vTitle.setText(title);
    }

    public void setOrientation(int orientation) {
        if (orientation == mOrientation) return;
        mOrientation = orientation;
        removeAllViews();
        inflate(getContext(), orientation == VERTICAL ? R.layout.layout_title_edittext_vertical : R.layout.layout_title_edittext_horizontal, this);
        String title = vTitle != null ? vTitle.getText().toString() : "";
        String text = vEt != null ? vEt.getText().toString() : "";
        String hint = vEt != null ? vEt.getHint().toString() : "";
        vTitle = findViewById(R.id.tv_title);
        vEt = findViewById(R.id.et_edittext);
//        vRImg = findViewById(R.id.iv_image);
        vEt.setOnFocusChangeListener(this);
        vEt.addTextChangedListener(this);
        vEt.setText(title);
        vEt.setHint(hint);
        vEt.setText(text);
        vTitle.setText(title);
    }

    @Override
    public void onFocusChange(View view, boolean b) {

    }

    @Override
    public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

    }

    @Override
    public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

    }

    @Override
    public void afterTextChanged(Editable editable) {

    }
}
