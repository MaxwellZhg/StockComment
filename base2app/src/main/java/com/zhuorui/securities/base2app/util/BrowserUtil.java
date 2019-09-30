package com.zhuorui.securities.base2app.util;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;

/**
 * Create by xieyingwu on 2018/10/16
 * 浏览器跳转的工具类
 */
public class BrowserUtil {
    private BrowserUtil() {
    }

    public static boolean jumpToBrowser(Context context, String url) {
        try {
            Uri uri = Uri.parse(url);
            Intent intent = new Intent(Intent.ACTION_VIEW, uri);
            context.startActivity(intent);
            return true;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }
}
