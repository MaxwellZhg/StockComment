package com.zhuorui.commonwidget.dialog

import android.app.Dialog
import android.content.Context
import android.view.Gravity
import android.view.View
import android.view.animation.AnimationUtils
import com.zhuorui.commonwidget.R

/**
 * Created by Maxwell.
 * E-mail: maxwell_smith@163.com
 * Date: 2019/8/22
 * Desc:
 */

class ProgressDialog(context: Context): Dialog(context, R.style.loading_dialog_style){

    //dismiss回调
    private var dismissCallback: () -> Unit = {}

    init {
        setContentView(R.layout.layout_dialog_loading)
        //旋转动画
        startAnimation()
        // 设置居中
        window!!.attributes.gravity = Gravity.CENTER
        val lp = window!!.attributes
        // 设置背景层透明度
        lp.dimAmount = 0.6f
        window!!.attributes = lp
        // 取消监听
        setOnCancelListener {
            dismissCallback()
        }
    }

    override fun show() {
        super.show()
        // 启动动画
        startAnimation()
    }

    override fun onStop() {
        super.onStop()
        //  停止动画
        findViewById<View>(R.id.net_iv_loading).clearAnimation()
    }

    /**
     * 销毁
     */
    fun setDismissCallback(callback: () -> Unit){
        dismissCallback = callback
    }

    /**
     * 动画
     */

    private fun startAnimation() {
        val rotate = AnimationUtils.loadAnimation(context, R.anim.rotate)
        findViewById<View>(R.id.net_iv_loading).startAnimation(rotate)
    }
}
