package com.zhuorui.securities.base2app.util

import android.graphics.drawable.Drawable
import android.util.TypedValue
import androidx.annotation.*
import androidx.core.content.ContextCompat
import com.zhuorui.securities.base2app.BaseApplication

/**
 * Created by xieyingwu on 2017/7/6.
 * 获取资源
 */
object ResUtil {

    fun getDimensionPixelSize(@DimenRes dimenResId: Int): Int? {
        return BaseApplication.context?.resources?.getDimensionPixelSize(dimenResId)
    }

    fun getColor(@ColorRes colorResId: Int): Int? {
        return BaseApplication.context?.let { ContextCompat.getColor(it, colorResId) }
    }

    fun getString(stringResId: Int): String? {
        return BaseApplication.context?.getString(stringResId)
    }

    fun getStringFormat(@StringRes stringResId: Int, formatArgs: Any): String? {
        return BaseApplication.context?.getString(stringResId, formatArgs)
    }

    fun getDrawable(@DrawableRes drawableResId: Int): Drawable? {
        return BaseApplication.context?.let { ContextCompat.getDrawable(it, drawableResId) }
    }

    fun getDimensionDp2Px(dpValue: Float): Int {
        val displayMetrics = BaseApplication.context?.resources?.displayMetrics
        return TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, dpValue, displayMetrics).toInt()
    }

    fun getStringArray(@ArrayRes stringArrayResId: Int): Array<String>? {
        return BaseApplication.context?.resources?.getStringArray(stringArrayResId)
    }

    fun getIntArray(@ArrayRes intArrayResId: Int): IntArray? {
        return BaseApplication.context?.resources?.getIntArray(intArrayResId)
    }
}
