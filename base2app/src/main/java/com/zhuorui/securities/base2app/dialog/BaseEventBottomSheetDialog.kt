package com.zhuorui.securities.base2app.dialog

import android.content.Context

import com.zhuorui.securities.base2app.KeepNoProguard
import com.zhuorui.securities.base2app.rxbus.EventThread
import com.zhuorui.securities.base2app.rxbus.RxBus
import com.zhuorui.securities.base2app.rxbus.RxSubscribe

/**
 * Create by xieyingwu on 2018/8/23
 * baseDialog，包含RxBus事件传递
 */
abstract class BaseEventBottomSheetDialog protected constructor(context: Context) : BaseBottomSheetsDialog(context) {
    init {
        RxBus.getDefault().register(this)
    }

    override fun hide() {
        super.hide()
        RxBus.getDefault().unregister(this)
    }

    @RxSubscribe(observeOnThread = EventThread.MAIN)
    fun onDialogHideEvent(event: BaseEventDialog.DialogHideEvent) {
        val key = event.key
        if (this.javaClass.name == key)
            hide()
    }

    @KeepNoProguard
    protected class DialogHideEvent private constructor(var key: String) {
        companion object {

            fun sendDialogHideEvent(key: String) {
                RxBus.getDefault().post(BaseEventBottomSheetDialog.DialogHideEvent(key))
            }
        }
    }
}
