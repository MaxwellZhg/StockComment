package com.zhuorui.securities.openaccount.utils;

import android.content.Context;

/**
 * Created by Maxwell.
 * E-mail: maxwell_smith@163.com
 * Date: 2019/8/28
 * Desc:
 */
public class DensityUtil {
    public static int dip2px(Context context, float dpValue) {
        final float scale =context.getResources().getDisplayMetrics().density;
        return(int) (dpValue * scale + 0.5f);
    }
    public static int px2dip(Context context,float pxValue) {final
    float scale =context.getResources().getDisplayMetrics().density;
    return(int) (pxValue / scale + 0.5f);
    }



}
