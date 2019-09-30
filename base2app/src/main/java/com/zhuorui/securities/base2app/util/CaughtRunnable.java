package com.zhuorui.securities.base2app.util;

/**
 * Created by xieyingwu on 2017/7/27.
 * 自身Runnable实现异常捕获
 */

public abstract class CaughtRunnable implements Runnable {
    private static final String TAG = CaughtRunnable.class.getName();

    public CaughtRunnable() {
        super();
    }

    @Override
    public void run() {
        try {
            runSafe();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    protected abstract void runSafe();
}
