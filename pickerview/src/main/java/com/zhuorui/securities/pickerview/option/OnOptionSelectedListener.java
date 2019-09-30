package com.zhuorui.securities.pickerview.option;

import java.util.List;

/**
 * author : liuwei
 * e-mail : vsanliu@foxmail.com
 * date   : 2019-08-22 11:37
 * desc   :
 */
public interface OnOptionSelectedListener<T extends Object> {

     void onOptionSelected(List<T> data);
}
