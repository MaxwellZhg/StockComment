package com.zhuorui.securities.openaccount.widget;

import android.content.Context;
import android.content.res.TypedArray;
import android.util.AttributeSet;
import android.widget.FrameLayout;
import android.widget.SeekBar;
import android.widget.TextView;
import com.zhuorui.securities.openaccount.R;

/**
 * author : liuwei
 * e-mail : vsanliu@foxmail.com
 * date   : 2019-08-26 13:44
 * desc   :
 */
public class OpenAccountStepView extends FrameLayout {
    private int mStep;
    private TextView vSymbol1;
    private TextView vSymbol2;
    private TextView vSymbol3;
    private TextView vTxt1;
    private TextView vTxt2;
    private TextView vTxt3;
    private SeekBar vSB;

    public OpenAccountStepView(Context context) {
        this(context, null);
    }

    public OpenAccountStepView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public OpenAccountStepView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        inflate(getContext(), R.layout.layout_open_account_step_view, this);
        initView();
        TypedArray a = getResources().obtainAttributes(attrs, R.styleable.OpenAccountStepView);
        mStep = a.getInt(R.styleable.OpenAccountStepView_zr_openAccountStep, 0);
        a.recycle();
        vSymbol3.setEnabled(mStep >= 4);
        vSymbol2.setEnabled(mStep >= 3);
        vSymbol1.setEnabled(mStep >= 1);
        vTxt3.setEnabled(mStep >= 4);
        vTxt2.setEnabled(mStep >= 3);
        vTxt1.setEnabled(mStep >= 1);
        vSB.setProgress(mStep);
        vSB.setEnabled(false);
    }

    private void initView() {
        vSymbol1 = findViewById(R.id.tv_c1);
        vSymbol2 = findViewById(R.id.tv_c2);
        vSymbol3 = findViewById(R.id.tv_c3);
        vTxt1 = findViewById(R.id.tv_txt1);
        vTxt2 = findViewById(R.id.tv_txt2);
        vTxt3 = findViewById(R.id.tv_txt3);
        vSB = findViewById(R.id.seekbar);
    }
}
