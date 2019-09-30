package com.zhuorui.securities.base2app.ui.view

import android.content.Context
import android.content.pm.ApplicationInfo
import android.content.pm.PackageManager
import android.util.AttributeSet
import android.util.DisplayMetrics
import android.util.Log
import android.util.TypedValue
import android.view.WindowManager
import androidx.appcompat.widget.AppCompatTextView

import com.zhuorui.securities.base2app.R

/**
 * 自动调整字体大小适配屏幕的TextView
 * * Create by pengxinaglin on 2018/8/29.
 */
class AutoTextView(context: Context, attrs: AttributeSet) : AppCompatTextView(context, attrs) {
    private var baseScreenWidth = 480
    private var baseScreenHeight = 720

    init {
        getMetaData(context)
        val type = context.obtainStyledAttributes(attrs, R.styleable.AutoTextView)//获得属性值
        val px = type.getFloat(R.styleable.AutoTextView_textSizePx, 25f)
        Log.d("LOGCAT", "px:$px")
        Log.d("LOGCAT", "baseScreenHeight:$baseScreenHeight")
        Log.d("LOGCAT", "baseScreenWidth:$baseScreenWidth")
        setTextSize(TypedValue.COMPLEX_UNIT_PX, getFontSize(px).toFloat())
        type.recycle()
    }

    private fun getMetaData(context: Context) {
        val packageManager = context.packageManager
        val applicationInfo: ApplicationInfo?
        try {
            applicationInfo = packageManager.getApplicationInfo(
                context
                    .packageName, PackageManager.GET_META_DATA
            )
            if (applicationInfo != null && applicationInfo.metaData != null) {
                baseScreenWidth = applicationInfo.metaData.get(KEY_DESIGN_WIDTH) as Int
                baseScreenHeight = applicationInfo.metaData.get(KEY_DESIGN_HEIGHT) as Int
            }
        } catch (e: PackageManager.NameNotFoundException) {
            throw RuntimeException(
                "you must set $KEY_DESIGN_HEIGHT and $KEY_DESIGN_WIDTH  in your manifest file.", e
            )
        }

    }

    /**
     * @param textSize 设计稿中的dp大小
     */
    override fun setTextSize(textSize: Float) {
        setTextSize(TypedValue.COMPLEX_UNIT_PX, getFontSize(textSize).toFloat())
    }

    /**
     * 获取当前手机屏幕分辨率，然后根据和设计图的比例对照换算实际字体大小
     *
     * @param textSize
     * @return
     */
    private fun getFontSize(textSize: Float): Int {
        val dm = DisplayMetrics()
        val windowManager = context.getSystemService(Context.WINDOW_SERVICE) as WindowManager
        windowManager.defaultDisplay.getMetrics(dm)
        val screenHeight = dm.heightPixels
        val screenWidth = dm.widthPixels
        val scaleH = (screenHeight / baseScreenHeight).toFloat()
        val scaleW = (screenWidth / baseScreenWidth).toFloat()
        val scale = if (scaleH < scaleW) scaleW else scaleH
        return (textSize * scale).toInt()
    }

    companion object {
        private val KEY_DESIGN_HEIGHT = "design_height"
        private val KEY_DESIGN_WIDTH = "design_width"
    }
}