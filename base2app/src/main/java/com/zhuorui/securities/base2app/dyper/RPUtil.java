package com.zhuorui.securities.base2app.dyper;

import android.app.AppOpsManager;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Binder;
import android.os.Build;
import android.os.Environment;
import android.provider.Settings;
import android.text.TextUtils;

import java.io.File;
import java.io.FileInputStream;
import java.util.Properties;

/**
 * Create by xieyingwu on 2018/4/3.
 * 类描述：动态权限申请处理问题
 */
public class RPUtil {
    private RPUtil() {
    }

    /**
     * 跳转到当前应用的详情页面
     *
     * @param context context
     * @return true 跳转成功
     */
    public static boolean toAppDetailInfo(Context context) {
        try {
            String packageName = context.getPackageName();
            Intent intent = new Intent();
            intent.setAction(Settings.ACTION_APPLICATION_DETAILS_SETTINGS);
            intent.setData(Uri.fromParts("package", packageName, null));
            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            context.startActivity(intent);
            return true;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }

    /**
     * 检测是否获取了XiaoMi手机的指定OP权限
     *
     * @param context context
     * @param op      OP
     * @return true表明获取权限
     */
    public static boolean checkAppRPForXiaoMi(Context context, String op) {
        if (isMIUI(context)) {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
                try {
                    AppOpsManager appOpsManager = (AppOpsManager) context.getSystemService(Context.APP_OPS_SERVICE);
                    if (appOpsManager == null) return true;
                    int checkOp = appOpsManager.checkOp(op, Binder.getCallingUid(), context.getPackageName());
                    if (checkOp == AppOpsManager.MODE_IGNORED) {
                        return false;
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }
        return true;
    }

    private static final String SP_NAME = "miui_sp_name";
    private static final String KEY_MIUI = "key_miui";
    private static final String KEY_MIUI_VERSION_CODE = "ro.miui.ui.version.code";
    private static final String KEY_MIUI_VERSION_NAME = "ro.miui.ui.version.name";
    private static final String KEY_MIUI_INTERNAL_STORAGE = "ro.miui.internal.storage";
    private static final String VALUE_IS_MIUI = "value_is_miui";
    private static final String VALUE_NOT_MIUI = "value_not_miui";

    /**
     * 判断当前系统是否是MIUI系统
     *
     * @param context context
     * @return true 表明是miui系统
     */
    public static boolean isMIUI(Context context) {
        SharedPreferences sp = context.getSharedPreferences(SP_NAME, Context.MODE_PRIVATE);
        String isMiui = sp.getString(KEY_MIUI, null);
        if (TextUtils.isEmpty(isMiui)) {
            /*表明还未存储当前是否是MIUI,需要存储*/
            Properties prop = new Properties();
            boolean isMIUI;
            try {
                prop.load(new FileInputStream(new File(Environment.getRootDirectory(), "build.prop")));
            } catch (Exception e) {
                return false;
            }
            isMIUI = prop.getProperty(KEY_MIUI_VERSION_CODE, null) != null
                    || prop.getProperty(KEY_MIUI_VERSION_NAME, null) != null
                    || prop.getProperty(KEY_MIUI_INTERNAL_STORAGE, null) != null;
            boolean commit = sp.edit().putString(KEY_MIUI, isMIUI ? VALUE_IS_MIUI : VALUE_NOT_MIUI).commit();
            return isMIUI;
        } else {
            return VALUE_IS_MIUI.equals(isMiui);
        }
    }

}
