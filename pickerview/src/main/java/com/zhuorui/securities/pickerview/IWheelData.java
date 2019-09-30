package com.zhuorui.securities.pickerview;

/**
 * author : liuwei
 * e-mail : vsanliu@foxmail.com
 * date   : 2019-08-22 11:32
 * desc   :
 */
public interface IWheelData<T> {

    /**
     * 获取需要显示的数据
     *
     * @return
     */
    String getItemText();

    /**
     * 获取需要返回选择的数据
     *
     * @return
     */
    T getItemData();
}
