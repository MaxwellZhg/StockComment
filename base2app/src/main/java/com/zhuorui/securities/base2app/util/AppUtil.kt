package com.zhuorui.securities.base2app.util

import android.content.Context
import android.content.pm.PackageInfo
import android.content.pm.PackageManager
import android.os.Build
import android.util.DisplayMetrics
import android.view.WindowManager

/**
 * App相关信息获取
 */

object AppUtil {
    var screenHeight: Int = 0
        private set
    var screenWidth: Int = 0
        private set
    var dpi: Float = 0.toFloat()
        private set
    var phoneScreenHeight: Int = 0
        private set
    var phoneScreenWidth: Int = 0
        private set

    var phoneRealScreenHeight: Int = 0
        private set
    var phoneRealScreenWidth: Int = 0
        private set

    /**
     * 需要在Application中初始化
     *
     * @param context
     */
    fun init(context: Context) {
        val displayMetrics = context.resources.displayMetrics
        this.screenHeight = displayMetrics.heightPixels
        this.screenWidth = displayMetrics.widthPixels
        dpi = displayMetrics.density
        val dm = DisplayMetrics()
        val windowManager = context.getSystemService(Context.WINDOW_SERVICE) as WindowManager
        val defaultDisplay = windowManager.defaultDisplay
        defaultDisplay.getMetrics(dm)
        phoneScreenHeight = dm.heightPixels
        phoneScreenWidth = dm.widthPixels
        val dmReal = DisplayMetrics()
        defaultDisplay.getRealMetrics(dmReal)
        phoneRealScreenHeight = dmReal.heightPixels
        phoneRealScreenWidth = dmReal.widthPixels
    }


    fun getPackageInfo(context: Context): PackageInfo? {
        try {
            val pm = context.packageManager
            return pm.getPackageInfo(context.packageName, PackageManager.GET_CONFIGURATIONS)
        } catch (e: Exception) {
            e.printStackTrace()
        }
        return null
    }

    fun getVersionCode(context: Context): String? {
        val packageInfo = getPackageInfo(context) ?: return null
        return if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.P) {
            packageInfo.longVersionCode.toString() + ".0"
        } else {
            return packageInfo.versionCode.toString() + ".0"
        }
    }

    fun getVersionName(context: Context): String? {
        val packageInfo = getPackageInfo(context) ?: return null
        return packageInfo.versionName
    }

    fun getAppName(context: Context): String? {
        try {
            val packageInfo = getPackageInfo(context) ?: return null
            val labelRes = packageInfo.applicationInfo.labelRes
            return context.resources.getString(labelRes)
        } catch (e: Exception) {
            e.printStackTrace()
        }

        return null
    }
}