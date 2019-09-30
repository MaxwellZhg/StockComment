package com.zhuorui.securities.base2app.dialog

import android.content.Context
import android.content.DialogInterface
import android.view.Gravity
import android.view.KeyEvent
import android.view.View
import android.widget.FrameLayout
import androidx.annotation.StringRes

import com.zhuorui.securities.base2app.R
import com.zhuorui.securities.base2app.util.ResUtil
import com.zhuorui.securities.base2app.util.ToastUtil

import butterknife.ButterKnife
import com.google.android.material.bottomsheet.BottomSheetDialog

/**
 * Create by xieyingwu on 2018/8/23
 * 定义基础的BottomSheets弹窗
 */
abstract class BaseBottomSheetsDialog protected constructor(context: Context) : DialogInterface.OnShowListener,
    DialogInterface.OnDismissListener, DialogInterface.OnCancelListener {
    protected var TAG: String? = null
    protected var context: Context? = null
    protected var dialog: BottomSheetDialog? = null

    protected val width: Int
        get() = FrameLayout.LayoutParams.WRAP_CONTENT

    protected val height: Int
        get() = FrameLayout.LayoutParams.WRAP_CONTENT

    protected val dialogStyle: Int
        get() = R.style.BottomSheetDialogStyle

    protected abstract val layout: Int

    init {
        init(context)
    }

    private fun init(context: Context) {
        this.TAG = this.javaClass.name
        this.context = context
        initView()
    }

    protected open fun autoWH(): Boolean {
        return true
    }

    private fun initView() {
        dialog = context?.let { BottomSheetDialog(it, dialogStyle) }
        val layout = layout
        if (autoWH()) {
            dialog?.setContentView(layout)
        } else {
            val view = View.inflate(context, layout, null)
            val lp = FrameLayout.LayoutParams(width, height)
            dialog?.setContentView(view, lp)
        }
        dialog?.setOnShowListener(this)
        dialog?.setOnDismissListener(this)
        dialog?.setOnCancelListener(this)
        dialog?.let { ButterKnife.bind(this, it) }
        /*设置dialog的视图默认背景色为transparent透明色*/
        try {
            val parentView = dialog?.delegate?.findViewById<View>(com.google.android.material.R.id.design_bottom_sheet)
            ResUtil.getColor(android.R.color.transparent)?.let { parentView?.setBackgroundColor(it) }
        } catch (e: Exception) {
            e.printStackTrace()
        }

    }

    protected fun setCancelable(clickOutSideCancel: Boolean) {
        dialog?.setCancelable(clickOutSideCancel)
    }

    open fun hide() {
        if (dialog?.isShowing!!) {
            dialog?.dismiss()
        }
    }

    protected fun ignoreBackPressed() {
        /*设置onKeyListener*/
        dialog?.setOnKeyListener { dialog, keyCode, event -> keyCode == KeyEvent.KEYCODE_BACK && event.repeatCount == 0 }
    }

    override fun onShow(dialog: DialogInterface) {
        dialogOnShow()
    }

    override fun onDismiss(dialog: DialogInterface) {
        dialogOnDismiss()
    }

    override fun onCancel(dialog: DialogInterface) {
        dialogOnCancel()
    }

    protected fun dialogOnShow() {}

    protected fun dialogOnDismiss() {}

    protected fun dialogOnCancel() {}

    fun show() {
        if (!dialog!!.isShowing) {
            dialog!!.show()
        }
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
}
