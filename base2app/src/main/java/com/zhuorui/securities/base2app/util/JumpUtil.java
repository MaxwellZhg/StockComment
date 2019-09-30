package com.zhuorui.securities.base2app.util;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;
import android.os.Bundle;

import com.zhuorui.securities.base2app.infra.LogInfra;

import java.util.List;

/**
 * app跳转工具类
 */
public class JumpUtil {
    private JumpUtil() {
    }

    private static final String TAG = "JumpUtil";

    public static void jump(Context context, Class<?> cls, Bundle bundle, int... flags) {
        LogInfra.Log.d(TAG, "context = " + context + ";cls:" + cls);
        Intent intent = new Intent(context, cls);
//        intent.addFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);

        if (flags != null && flags.length > 0) {
            for (int flag : flags) {
                intent.addFlags(flag);
            }
        }

        if (bundle != null)
            intent.putExtras(bundle);
        if (isIntentAvailable(context, intent)) {
            context.startActivity(intent);
        } else {
            LogInfra.Log.d("JumpUtil", "jump app is not exist!");
        }
    }

    public static void jump(Context context, Class<?> cla, int... flags) {
        jump(context, cla, null, flags);
    }

    public static void jump(Activity currentAc, Class<?> cla, boolean closeCurrentAc) {
        jump(currentAc, cla, null);
        if (closeCurrentAc) {
            currentAc.finish();
        }
    }

    public static void jumpWithBundle(Activity currentAc, Class<?> cla, boolean closeCurrentAc, Bundle bundle) {
        jump(currentAc, cla, bundle);
        if (closeCurrentAc) currentAc.finish();
    }

    public static void jumpWithBundle(Context context, Class<?> cla, Bundle bundle) {
        jump(context, cla, bundle);
    }


    public static boolean isIntentAvailable(Context context, Intent intent) {
        final PackageManager packageManager = context.getPackageManager();
        List<ResolveInfo> list = packageManager.queryIntentActivities(intent,
                PackageManager.MATCH_DEFAULT_ONLY);
        return list.size() > 0;
    }

    public static void jumpActivityNewTask(Context context, Class<?> acClass, Bundle bundle) {
        LogInfra.Log.d(TAG, "context = " + context + ";acClass:" + acClass);
        Intent intent = new Intent(context, acClass);
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        if (bundle != null) {
            intent.putExtras(bundle);
        }
        if (isIntentAvailable(context, intent)) {
            context.startActivity(intent);
        } else {
            LogInfra.Log.d("JumpUtil", "jump app is not exist!");
        }
    }
}