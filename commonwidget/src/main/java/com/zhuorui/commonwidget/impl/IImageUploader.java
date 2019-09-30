package com.zhuorui.commonwidget.impl;

/**
 * author : liuwei
 * e-mail : vsanliu@foxmail.com
 * date   : 2019-08-28 10:31
 * desc   :
 */
public interface IImageUploader {

    void setOnUploaderListener(OnImageUploaderListener listener);

    void upLoad(String path);
}
