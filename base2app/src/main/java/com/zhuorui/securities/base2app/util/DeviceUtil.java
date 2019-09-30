package com.zhuorui.securities.base2app.util;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Build;
import android.provider.Settings;
import android.text.TextUtils;
import com.zhuorui.securities.base2app.BaseApplication;

import java.io.IOException;
import java.io.InputStreamReader;
import java.io.LineNumberReader;
import java.util.Locale;
import java.util.Objects;
import java.util.UUID;

/**
 * Created by xieyingwu on 2018/5/3
 * 设备信息获取工具类
 */
public class DeviceUtil {
    private DeviceUtil() {
    }

    /**
     * 获取当前手机系统语言。
     *
     * @return 返回当前系统语言。例如：当前设置的是“中文-中国”，则返回“zh-CN”
     */
    public static String getSystemLanguage() {
        return Locale.getDefault().getLanguage();
    }

    /**
     * 获取当前手机系统版本号
     *
     * @return  系统版本号
     */
    public static String getSystemVersion() {
        return android.os.Build.VERSION.RELEASE;
    }

    /**
     * @return 设备品牌
     **/
    public static String getDeviceBrand() {
        return Build.BRAND;
    }

    /**
     * 获取设备厂商
     * <p>如 Xiaomi</p>
     *
     * @return 设备厂商
     */
    public static String getManufacturer() {
        return Build.MANUFACTURER;
    }

    /**
     * @return 设备用户名
     **/
    public static String getDeviceUserName() {
        return Build.USER;
    }

    /**
     * @return 设备型号
     **/
    public static String getDeviceModel() {
        return Build.MODEL;
    }

    /**
     * @return 产品名
     */
    public static String getDeviceProduct() {
        return Build.PRODUCT;
    }

    /**
     * 获取MAC地址；联网状态才能够返回有效值
     *
     * @return MAC
     */
    private static String getDeviceMac() {
        StringBuilder macSerial = new StringBuilder();
        try {
            Process pp = Runtime.getRuntime().exec("cat /sys/class/net/wlan0/address");
            InputStreamReader ir = new InputStreamReader(pp.getInputStream());
            LineNumberReader input = new LineNumberReader(ir);
            String line;
            while ((line = input.readLine()) != null) {
                macSerial.append(line.trim());
            }

            input.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        if (!TextUtils.isEmpty(macSerial.toString())) {
            macSerial = new StringBuilder(macSerial.toString().replaceAll(":", "").toUpperCase());
        }
        return macSerial.toString();
    }

    /***
     * 获取设备唯一标识符
     * **/
    public static String getDeviceUuid() {
        /*获取AndroidId*/
        String uuid = Settings.System.getString(Objects.requireNonNull(BaseApplication.Companion.getContext()).getContentResolver(), Settings.Secure.ANDROID_ID);
        if (!TextUtils.isEmpty(uuid))
            return uuid;
        /*获取mac地址*/
        uuid = getDeviceMac();
        if (!TextUtils.isEmpty(uuid)) return uuid;
        /*若未获取到；则主动生成一个uuid*/
        SharedPreferences sp = BaseApplication.Companion.getContext().getSharedPreferences("uuid--sp", Context.MODE_PRIVATE);
        String spUuid = sp.getString("uuid--", null);
        if (TextUtils.isEmpty(spUuid)) {
            String uuidStr = UUID.randomUUID().toString() + System.currentTimeMillis();
            uuid = Md5Util.getMd5Str(uuidStr);
        } else {
            return spUuid;
        }
        return uuid;
    }

    /**
     * @return 软件release版本
     **/
    public static String getVersionRelease() {
        return Build.VERSION.RELEASE;
    }


}
