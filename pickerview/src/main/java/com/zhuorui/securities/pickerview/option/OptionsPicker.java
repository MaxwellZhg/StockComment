package com.zhuorui.securities.pickerview.option;

import android.content.Context;
import android.util.AttributeSet;
import android.util.Log;
import android.util.LogPrinter;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import com.zhuorui.securities.pickerview.IWheelData;
import com.zhuorui.securities.pickerview.WheelPicker;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * author : liuwei
 * e-mail : vsanliu@foxmail.com
 * date   : 2019-08-22 11:26
 * desc   :
 */
public class OptionsPicker<T> extends LinearLayout {

    private boolean circle = false;
    private OnOptionSelectedListener mSelectedLis;

    public OptionsPicker(Context context) {
        this(context, null);
    }

    public OptionsPicker(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public OptionsPicker(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        setOrientation(HORIZONTAL);
    }

    /**
     * 设置不联动数据
     */
    public void setData(List<T>... datas) {
        removeAllViews();
        for (List<T> data : datas) {
            WheelPicker wp = addWheel();
            wp.setDataList(data);
        }
    }

    public void setCurrentData(T... data) {
        for (int i = 0, len = getChildCount(); i < len; i++) {
            if (i >= data.length) break;
            WheelPicker wp = (WheelPicker) getChildAt(i);
            wp.setCurrentPosition(wp.getDataList().indexOf(data[i]));
        }
    }
    
    /**
     * 设置不联动数据
     */
    public void setWheelData(List<? extends IWheelData<T>>... datas) {
//        for (List<? extends IWheelData<T>> data : datas) {
//            addWheel(data);
//        }

    }

    /**
     * 设置二级联动数据
     */
    public void setRelationData(List<? extends IWheelData<T>> data1, List<List<? extends IWheelData<T>>> data2) {

    }

    /**
     * 设置三级联动数据
     */
    public void setRelationData(List<? extends IWheelData<T>> data1, List<List<? extends IWheelData<T>>> data2, List<List<? extends IWheelData<T>>> data3) {

    }

    public void setOnOptionSelectedListener(OnOptionSelectedListener<T> l) {
        mSelectedLis = l;
    }

    public void confirm() {
        List<T> t = new ArrayList<>();
        for (int i = 0, len = getChildCount(); i < len; i++) {
            WheelPicker wp = (WheelPicker) getChildAt(i);
            T ts = (T) wp.getDataList().get(wp.getCurrentPosition());

            t.add(ts);
        }
        mSelectedLis.onOptionSelected(t);
    }

    private WheelPicker addWheel() {
        LinearLayout.LayoutParams lp = new LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT, 1.0f);
        WheelPicker<T> wp = new WheelPicker<T>(getContext());
        addView(wp, lp);
        return wp;
    }


}
