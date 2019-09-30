package com.zhuorui.commonwidget.dialog

import android.content.Context
import android.text.Spannable
import android.text.Spanned
import android.view.View
import android.view.WindowManager
import android.widget.TextView
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
 *    desc   : 确定取消对话框
 */
class ConfirmToCancelDialog(
    context: Context,
    width: Int,
    private val canceledOnTouchOutside: Boolean,
    private val ignoreBack: Boolean
) : BaseDialog(context, width, WindowManager.LayoutParams.WRAP_CONTENT),
    View.OnClickListener {

    @BindView(R2.id.tv_msg)
    lateinit var tv_msg: TextView
    @BindView(R2.id.tv_cancel)
    lateinit var tv_cancel: TextView
    @BindView(R2.id.tv_confirm)
    lateinit var tv_confirm: TextView

    private var callBack: CallBack? = null

    override val layout: Int
        get() = R.layout.dialog_confirm_to_cancel

    override fun init() {
        tv_cancel.setOnClickListener(this)
        tv_confirm.setOnClickListener(this)

        changeDialogOutside(canceledOnTouchOutside)
        if (ignoreBack)
            ignoreBackPressed()
    }

    fun setMsgText(str: String): ConfirmToCancelDialog {
        tv_msg.text = str
        return this
    }

    fun setMsgText(@StringRes strId: Int): ConfirmToCancelDialog {
        tv_msg.text = ResUtil.getString(strId)
        return this
    }

    fun setMsgText(str: Spanned): ConfirmToCancelDialog {
        tv_msg.text = str
        return this
    }

    fun setMsgText(str: Spannable): ConfirmToCancelDialog {
        tv_msg.text = str
        return this
    }

    fun setCancelText(str: String): ConfirmToCancelDialog {
        tv_cancel.text = str
        return this
    }

    fun setCancelText(@StringRes strId: Int): ConfirmToCancelDialog {
        tv_cancel.text = ResUtil.getString(strId)
        return this
    }

    fun setConfirmText(str: String): ConfirmToCancelDialog {
        tv_confirm.text = str
        return this
    }

    fun setConfirmText(@StringRes strId: Int): ConfirmToCancelDialog {
        tv_confirm.text = ResUtil.getString(strId)
        return this
    }

    override fun onClick(p0: View) {
        when (p0) {
            tv_cancel -> {
            }
            else -> {
            }
        }
        dialog?.dismiss()
    }

    fun setCallBack(callBack: CallBack): ConfirmToCancelDialog {
        this.callBack = callBack
        return this
    }

    /***
     * 回调点击
     */
    interface CallBack {

        fun onCancel()

        fun onConfirm()
    }

    // 默认提供的构造，有其他形态时，需要自行 new ConfirmDialog()
    companion object {

        /**
         * 提供一个默认构造宽度为250dp方法
         */
        fun createWidth250Dialog(
            context: Context,
            canceledOnTouchOutside: Boolean,
            ignoreBack: Boolean
        ): ConfirmToCancelDialog {
            return ConfirmToCancelDialog(context, ResUtil.getDimensionDp2Px(250f), canceledOnTouchOutside, ignoreBack)
        }

        /**
         * 提供一个默认构造宽度为265dp方法
         */
        fun createWidth265Dialog(
            context: Context,
            canceledOnTouchOutside: Boolean,
            ignoreBack: Boolean
        ): ConfirmToCancelDialog {
            return ConfirmToCancelDialog(context, ResUtil.getDimensionDp2Px(265f), canceledOnTouchOutside, ignoreBack)
        }

        /**
         * 提供一个默认构造宽度为290dp方法
         */
        fun createWidth290Dialog(
            context: Context,
            canceledOnTouchOutside: Boolean,
            ignoreBack: Boolean
        ): ConfirmToCancelDialog {
            return ConfirmToCancelDialog(context, ResUtil.getDimensionDp2Px(290f), canceledOnTouchOutside, ignoreBack)
        }
    }
}