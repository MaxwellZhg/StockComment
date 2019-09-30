package com.zhuorui.securities.base2app.util;

import android.util.Log;
import androidx.collection.SparseArrayCompat;

/**
 * Create by xieyingwu on 2018/9/30
 * 快速点击处理
 */
public final class QuickClickUtil {
    private static final String TAG = QuickClickUtil.class.getSimpleName();
    private long quickClickTimeMills;
    private SparseArrayCompat<Long> clickMap = new SparseArrayCompat<>();
    private Callback callback;

    public QuickClickUtil(long quickClickTimeMills) {
        quickClickTimeMills = Math.max(0, quickClickTimeMills);
        this.quickClickTimeMills = quickClickTimeMills;
    }

    public void setCallback(Callback callback) {
        this.callback = callback;
    }

    public void clearClickRecord() {
        clickMap.clear();
    }

    public boolean clickRecord(int viewId) {
        Long lastClickTime = clickMap.get(viewId);
        if (lastClickTime == null) {
            /*第一次点击;记录下当前点击时间*/
            clickMap.put(viewId, System.currentTimeMillis());
        } else {
            long intervalTime = System.currentTimeMillis() - lastClickTime;
            Log.i(TAG, "viewId = " + viewId + " ;intervalTime = " + intervalTime);
            if (intervalTime < quickClickTimeMills) {
                if (callback != null) callback.clickToFast();
                return false;
            }
            /*更新click点击时间*/
            clickMap.put(viewId, System.currentTimeMillis());
        }
        return true;
    }

    public interface Callback {
        void clickToFast();
    }
}
