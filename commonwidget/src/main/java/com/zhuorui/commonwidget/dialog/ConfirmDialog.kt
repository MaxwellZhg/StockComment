package com.zhuorui.commonwidget.dialog

import android.content.Context
import android.graphics.drawable.Drawable
import android.text.Spannable
import android.text.Spanned
import android.view.View
import android.view.WindowManager
import android.widget.TextView
import androidx.annotation.DrawableRes
import androidx.annotation.StringRes
import butterknife.BindView
import com.zhuorui.commonwidget.R
import com.zhuorui.commonwidget.R2
import com.zhuorui.securities.base2app.dialog.BaseDialog
import com.zhuorui.securities.base2app.util.ResUtil

/**
 *    author : PengXianglin
 *    e-mail : peng_xianglin@163.com
 *    date   : 2019/8/29 10:27
 *    desc   : 确定对话框
 */
class ConfirmDialog(
    context: Context,
    width: Int,
    private val canceledOnTouchOutside: Boolean,
    private val ignoreBack: Boolean
) : BaseDialog(context, width, WindowManager.LayoutParams.WRAP_CONTENT),
    View.OnClickListener {

    @BindView(R2.id.tv_msg)
    lateinit var tv_msg: TextView
    @BindView(R2.id.tv_confirm)
    lateinit var tv_confirm: TextView

    override val layout: Int
        get() = R.layout.dialog_confirm

    override fun init() {
        tv_confirm.setOnClickListener(this)

        changeDialogOutside(canceledOnTouchOutside)
        if (ignoreBack)
            ignoreBackPressed()
    }

    fun setMagDrawableStart(drawable: Drawable): ConfirmDialog {
        tv_msg.setCompoundDrawablesRelativeWithIntrinsicBounds(drawable, null, null, null)
        return this
    }

    fun setMagDrawableStart(@DrawableRes drawableId: Int): ConfirmDialog {
        tv_msg.setCompoundDrawablesRelativeWithIntrinsicBounds(ResUtil.getDrawable(drawableId), null, null, null)
        return this
    }

    fun setMsgText(str: String): ConfirmDialog {
        tv_msg.text = str
        return this
    }

    fun setMsgText(@StringRes strId: Int): ConfirmDialog {
        tv_msg.text = ResUtil.getString(strId)
        return this
    }

    fun setMsgText(str: Spanned): ConfirmDialog {
        tv_msg.text = str
        return this
    }

    fun setMsgText(str: Spannable): ConfirmDialog {
        tv_msg.text = str
        return this
    }

    fun setConfirmText(str: String): ConfirmDialog {
        tv_confirm.text = str
        return this
    }

    fun setConfirmText(@StringRes strId: Int): ConfirmDialog {
        tv_confirm.text = ResUtil.getString(strId)
        return this
    }

    override fun onClick(p0: View) {
        dialog?.dismiss()
    }

    // 默认提供的构造，有其他形态时，需要自行 new ConfirmDialog()
    companion object {

        /**
         * 提供一个默认构造宽度为265dp方法
         */
        fun createWidth265Dialog(
            context: Context,
            canceledOnTouchOutside: Boolean,
            ignoreBack: Boolean
        ): ConfirmDialog {
            return ConfirmDialog(context, ResUtil.getDimensionDp2Px(265f), canceledOnTouchOutside, ignoreBack)
        }

        /**
         * 提供一个默认构造宽度为290dp方法
         */
        fun createWidth290Dialog(
            context: Context,
            canceledOnTouchOutside: Boolean,
            ignoreBack: Boolean
        ): ConfirmDialog {
            return ConfirmDialog(context, ResUtil.getDimensionDp2Px(290f), canceledOnTouchOutside, ignoreBack)
        }
    }
}