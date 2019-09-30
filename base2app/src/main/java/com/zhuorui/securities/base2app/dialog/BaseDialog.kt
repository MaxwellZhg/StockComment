package com.zhuorui.securities.base2app.dialog

import android.annotation.TargetApi
import android.app.Dialog
import android.content.Context
import android.content.DialogInterface
import android.graphics.drawable.ColorDrawable
import android.os.Build
import android.view.*
import androidx.annotation.StringRes
import androidx.core.content.ContextCompat
import butterknife.ButterKnife
import com.zhuorui.securities.base2app.R
import com.zhuorui.securities.base2app.util.ToastUtil

/**
 * Created by xieyingwu on 2017/4/19.
 * dialog创建
 */

@Suppress("RECEIVER_NULLABILITY_MISMATCH_BASED_ON_JAVA_ANNOTATIONS")
abstract class BaseDialog : DialogInterface.OnShowListener, DialogInterface.OnDismissListener,
    DialogInterface.OnCancelListener {
    protected var TAG: String? = null
    protected var context: Context? = null
    protected var dialog: Dialog? = null
    private var lifeCycle: DialogLifeCycle? = null
    protected var width = WindowManager.LayoutParams.WRAP_CONTENT
    protected var height = WindowManager.LayoutParams.WRAP_CONTENT

    protected val dialogStyle: Int
        get() = R.style.DialogTransparent30

    protected abstract val layout: Int

    protected val bgColor: Int
        get() = android.R.color.transparent

    val isShowing: Boolean
        get() = dialog != null && dialog!!.isShowing

    protected constructor(context: Context) {
        initView(context, 0)
    }

    protected constructor(context: Context, theme: Int = 0) {
        initView(context, theme)
    }

    protected constructor(context: Context, initView: Boolean, theme: Int = 0) {
        this.TAG = this.javaClass.name
        this.context = context
        if (initView) {
            initView(theme)
        }
    }

    private fun initView(context: Context, theme: Int) {
        this.TAG = this.javaClass.name
        this.context = context
        initView(theme)
    }

    protected constructor(context: Context, w: Int, h: Int) {
        this.width = w
        this.height = h
        initView(context, 0)
    }

    fun setLifeCycle(lifeCycle: DialogLifeCycle): BaseDialog {
        this.lifeCycle = lifeCycle
        return this
    }

    private fun initView(theme: Int) {
        dialog = context?.let { Dialog(it, if (theme != 0) theme else dialogStyle) }
        val layout = layout
        val customDialog = LayoutInflater.from(context).inflate(layout, null)
        val params = WindowManager.LayoutParams()
        params.width = width
        params.height = height
        dialog!!.setContentView(customDialog, params)
        ButterKnife.bind(this, customDialog)
        dialog!!.setOnShowListener(this)
        dialog!!.setOnDismissListener(this)
        dialog!!.setOnCancelListener(this)
        init()
    }

    protected open fun init() {}

    fun show() {
        dialog!!.show()
        //        setDialogBg();
    }

    protected fun reSeizeToWidth() {
        val window = dialog!!.window
        // 把 DecorView 的默认 padding 取消，同时 DecorView 的默认大小也会取消
        window?.decorView?.setPadding(0, 0, 0, 0)
        val layoutParams = window?.attributes
        // 设置宽度
        if (layoutParams != null) {
            layoutParams.width = WindowManager.LayoutParams.MATCH_PARENT
        }
        window?.attributes = layoutParams
    }

    private fun setDialogBg() {
        val window = dialog!!.window ?: return
        /*设置全屏展示*/
        window.setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN)
        val color = context?.let { ContextCompat.getColor(it, bgColor) }
        val drawable = color?.let { ColorDrawable(it) }
        window.setBackgroundDrawable(drawable)
    }

    open fun hide() {
        try {
            if (dialog!!.isShowing) {
                dialog!!.dismiss()
            }
        } catch (e: Exception) {
            e.printStackTrace()
        }

    }

    override fun onShow(dialog: DialogInterface) {
        if (lifeCycle != null)
            lifeCycle!!.onDialogShow(this.javaClass.name)
    }

    /**
     * 是否允许外部点击来取消对话框
     */
    protected fun changeDialogOutside(isCanClick: Boolean) {
        dialog!!.setCanceledOnTouchOutside(isCanClick)
    }

    /**
     * 是否要屏蔽返回键来取消对话框
     */
    protected fun ignoreBackPressed() {
        /*设置onKeyListener*/
        dialog!!.setOnKeyListener { dialog, keyCode, event -> keyCode == KeyEvent.KEYCODE_BACK && event.repeatCount == 0 }
    }

    override fun onDismiss(dialog: DialogInterface) {
        if (lifeCycle != null)
            lifeCycle!!.onDialogDismiss(this.javaClass.name)
    }

    override fun onCancel(dialog: DialogInterface) {

    }

    protected fun updatePosition(x: Int, y: Int, gravity: Int) {
        val window = dialog!!.window ?: return
        val lp = window.attributes
        lp.gravity = gravity
        lp.x = x
        lp.y = y
        window.attributes = lp
    }

    @TargetApi(Build.VERSION_CODES.P)
    protected fun usePDisplayCutout(cutoutMode: Int) {
        val window = dialog!!.window ?: return
        val lp = window.attributes
        lp.layoutInDisplayCutoutMode = cutoutMode
        window.attributes = lp
    }

    protected fun toast(@StringRes strResId: Int) {
        ToastUtil.instance.toast(strResId)
    }

    protected fun toast(@StringRes strRes: Int, shortDuration: Boolean) {
        if (shortDuration) {
            ToastUtil.instance.toast(strRes)
        } else {
            ToastUtil.instance.toastLong(strRes)
        }
    }

    protected fun toast(str: String, shortDuration: Boolean) {
        if (shortDuration) {
            ToastUtil.instance.toast(str)
        } else {
            ToastUtil.instance.toastLong(str)
        }
    }

    interface DialogLifeCycle {
        fun onDialogShow(dialogName: String)

        fun onDialogDismiss(dialogName: String)
    }
}
