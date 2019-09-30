package com.zhuorui.securities.personal.ui.presenter

import android.content.Context
import com.zhuorui.commonwidget.dialog.ConfirmToCancelDialog
import com.zhuorui.commonwidget.dialog.ProgressDialog
import com.zhuorui.securities.base2app.Cache
import com.zhuorui.securities.base2app.network.ErrorResponse
import com.zhuorui.securities.base2app.network.Network
import com.zhuorui.securities.base2app.rxbus.EventThread
import com.zhuorui.securities.base2app.rxbus.RxBus
import com.zhuorui.securities.base2app.rxbus.RxSubscribe
import com.zhuorui.securities.base2app.ui.fragment.AbsNetPresenter
import com.zhuorui.securities.personal.R
import com.zhuorui.securities.personal.config.LocalAccountConfig
import com.zhuorui.securities.personal.event.LoginStateChangeEvent
import com.zhuorui.securities.personal.net.IPersonalNet
import com.zhuorui.securities.personal.net.request.UserLoginRegisterRequest
import com.zhuorui.securities.personal.net.response.UserLoginCodeResponse
import com.zhuorui.securities.personal.ui.view.SettingPswView
import com.zhuorui.securities.personal.ui.viewmodel.SettingPswViewModel

/**
 * Created by Maxwell.
 * E-mail: maxwell_smith@163.com
 * Date: 2019/8/20
 * Desc:
 */
class SettingPswPresenter(context: Context) : AbsNetPresenter<SettingPswView, SettingPswViewModel>() {

    private val infodialog: ConfirmToCancelDialog by lazy {
        ConfirmToCancelDialog.createWidth265Dialog(context, false, false)
            .setMsgText(R.string.register_tips)
            .setCancelText(R.string.go_to_main)
            .setConfirmText(R.string.complete_info)
    }

    /* 加载进度条 */
    private val progressDialog by lazy {
        ProgressDialog(context)
    }

    override fun init() {
        super.init()
        view?.init()
    }

    fun requestUserLoginPwdCode(pwd: kotlin.String, code: kotlin.String, phone: kotlin.String) {
        dialogshow(1)
        val request = UserLoginRegisterRequest(pwd, code, phone, "0086", transactions.createTransaction())
        Cache[IPersonalNet::class.java]?.userPwdCode(request)
            ?.enqueue(Network.IHCallBack<UserLoginCodeResponse>(request))
    }

    @RxSubscribe(observeOnThread = EventThread.MAIN)
    fun onSendLoginPwdResponse(response: UserLoginCodeResponse) {
        if (response.request is UserLoginRegisterRequest) {
            if (LocalAccountConfig.read().saveLogin(
                    response.data.userId,
                    response.data.phone,
                    response.data.token
                )
            ) {
                dialogshow(0)
                view?.showDialog()
                // 通知登录状态发生改变
                RxBus.getDefault().post(LoginStateChangeEvent(true))
            }
        }
    }

    @RxSubscribe(observeOnThread = EventThread.MAIN)
    fun onErrorRes(response: ErrorResponse) {
        if (response.request is UserLoginRegisterRequest) {
            dialogshow(0)
        }
    }


    fun showDailog() {
        infodialog.setCallBack(object : ConfirmToCancelDialog.CallBack {
            override fun onCancel() {
                view?.gotomain()
            }

            override fun onConfirm() {
                view?.openaccount()
            }
        }).show()
    }

    fun dialogshow(type: Int) {
        when (type) {
            1 -> {
                progressDialog.setCancelable(false)
                progressDialog.show()
            }
            else -> {
                progressDialog.setCancelable(true)
                progressDialog.dismiss()
            }
        }
    }
}