package com.zhuorui.securities.base2app.config;

import android.text.TextUtils;

/**
 * App运行模式
 */
public enum RunMode {
    ci, /*测试环境*/
    gray,/*灰度环境*/
    qa; /*正式环境*/

    private static RunMode current = qa;

    public boolean isPrMode() {
        return current == gray;
    }

    public boolean isCiMode() {
        return current == ci;
    }

    public boolean isQaMode() {
        return current == qa;
    }

    public RunMode getRunModel() {
        return current;
    }

    public static RunMode readRunModeByString(String run_mode) {
        if (TextUtils.isEmpty(run_mode)) {
            current = qa;/*默认正式环境*/
            return current;
        }
        try {
            current = RunMode.valueOf(run_mode);
            return current;
        } catch (IllegalArgumentException e) {
            e.printStackTrace();
        }
        current = qa;/*默认正式环境*/
        return current;
    }
}
