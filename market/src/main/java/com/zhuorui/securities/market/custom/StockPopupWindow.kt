package com.zhuorui.securities.market.custom

import android.content.Context
import android.view.View
import android.view.ViewGroup
import android.widget.PopupWindow
import com.zhuorui.securities.market.R
import android.graphics.drawable.ColorDrawable
import com.zhuorui.securities.base2app.util.ResUtil

/**
 *    author : PengXianglin
 *    e-mail : peng_xianglin@163.com
 *    date   : 2019/8/20 17:06
 *    desc   : 自选股列表长按点击弹窗
 */
class StockPopupWindow(contentView: View, width: Int, height: Int) : PopupWindow(contentView, width, height),
    View.OnClickListener, PopupWindow.OnDismissListener {

    private var callBack: CallBack? = null

    interface CallBack {

        fun onStickyOnTop()

        fun onRemind()

        fun onDelete()

        fun onDismiss()
    }

    init {
        setOnDismissListener(this)
        contentView.findViewById<View>(R.id.tv_sticky_on_top).setOnClickListener(this)
        contentView.findViewById<View>(R.id.tv_remind).setOnClickListener(this)
        contentView.findViewById<View>(R.id.tv_delete).setOnClickListener(this)
    }

    override fun onDismiss() {
        callBack?.onDismiss()
    }

    override fun onClick(p0: View?) {
        when (p0?.id) {
            R.id.tv_sticky_on_top -> {
                dismiss()
                callBack?.onStickyOnTop()
            }
            R.id.tv_remind -> {
                dismiss()
                callBack?.onRemind()
            }
            else -> {
                dismiss()
                callBack?.onDelete()
            }
        }
    }

    override fun showAtLocation(parent: View?, gravity: Int, x: Int, y: Int) {
        super.showAtLocation(parent, gravity, x, y - ResUtil.getDimensionDp2Px(60f))
    }

    companion object {

        fun create(context: Context, callback: CallBack): StockPopupWindow {
            val popupWindowView = View.inflate(context, R.layout.pop_stock, null)
            val popupWindow =
                StockPopupWindow(
                    popupWindowView,
                    ViewGroup.LayoutParams.WRAP_CONTENT,
                    ViewGroup.LayoutParams.WRAP_CONTENT
                )
            // 设置背景
            popupWindow.setBackgroundDrawable(ColorDrawable())
            // 外部点击事件
            popupWindow.isOutsideTouchable = true
            popupWindow.callBack = callback
            return popupWindow
        }
    }

}