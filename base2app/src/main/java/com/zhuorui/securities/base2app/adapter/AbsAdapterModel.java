package com.zhuorui.securities.base2app.adapter;

/**
 * Created by xieyingwu on 2018/4/8
 * 定义Adapter的抽象model
 */
public abstract class AbsAdapterModel {
    public boolean selected;
    public int viewType;

    public AbsAdapterModel(){}

    public AbsAdapterModel(int viewType) {
        this.viewType = viewType;
    }
}
