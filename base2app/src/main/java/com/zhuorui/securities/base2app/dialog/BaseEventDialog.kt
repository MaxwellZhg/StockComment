package com.zhuorui.securities.base2app.dialog

import android.content.Context
import android.content.DialogInterface
import android.text.TextUtils
import com.zhuorui.securities.base2app.KeepNoProguard
import com.zhuorui.securities.base2app.rxbus.RxBus
import com.zhuorui.securities.base2app.rxbus.RxSubscribe
import com.zhuorui.securities.base2app.rxbus.EventThread

/**
 * Created by xieyingwu on 2017/8/2.
 * baseDialog，包含RxBus事件传递
 */

abstract class BaseEventDialog : BaseDialog {

    private var eventKey: String? = null

    fun setEventKey(eventKey: String): BaseEventDialog {
        this.eventKey = eventKey
        return this
    }

    protected constructor(context: Context, theme: Int = 0) : super(context, theme) {
        RxBus.getDefault().register(this)
    }

    constructor(context: Context, initView: Boolean) : super(context, initView) {
        RxBus.getDefault().register(this)
    }

    constructor(context: Context, initView: Boolean, theme: Int) : super(context, initView, theme) {
        RxBus.getDefault().register(this)
    }

    constructor(context: Context, w: Int, h: Int) : super(context, w, h) {
        RxBus.getDefault().register(this)
    }

    override fun onDismiss(dialog: DialogInterface) {
        super.onDismiss(dialog)
        RxBus.getDefault().unregister(this)
    }

    @RxSubscribe(observeOnThread = EventThread.MAIN)
    open fun onDialogHideEvent(event: DialogHideEvent) {
        val key = event.key
        if (!TextUtils.isEmpty(eventKey)) {
            if (eventKey == key) {
                hide()
            }
        } else {
            if (this.javaClass.name == key) {
                hide()
            }
        }
    }

    @KeepNoProguard
    class DialogHideEvent private constructor(var key: String) {
        companion object {

            fun sendDialogHideEvent(key: String) {
                RxBus.getDefault().post(DialogHideEvent(key))
            }
        }
    }
}
