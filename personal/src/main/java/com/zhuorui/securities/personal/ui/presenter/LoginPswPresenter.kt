package com.zhuorui.securities.personal.ui.presenter

import android.content.Context
import android.view.View
import com.zhuorui.commonwidget.dialog.ProgressDialog
import com.zhuorui.securities.base2app.Cache
import com.zhuorui.securities.base2app.network.ErrorResponse
import com.zhuorui.securities.base2app.network.Network
import com.zhuorui.securities.base2app.rxbus.EventThread
import com.zhuorui.securities.base2app.rxbus.RxBus
import com.zhuorui.securities.base2app.rxbus.RxSubscribe
import com.zhuorui.securities.base2app.ui.fragment.AbsNetPresenter
import com.zhuorui.securities.personal.event.LoginStateChangeEvent
import com.zhuorui.securities.personal.R
import com.zhuorui.securities.personal.config.LocalAccountConfig
import com.zhuorui.securities.personal.event.DisctCodeSelectEvent
import com.zhuorui.securities.personal.net.IPersonalNet
import com.zhuorui.securities.personal.net.request.UserLoginPwdRequest
import com.zhuorui.securities.personal.net.response.UserLoginCodeResponse
import com.zhuorui.securities.personal.ui.dailog.ErrorTimesDialog
import com.zhuorui.securities.personal.ui.view.LoginPswView
import com.zhuorui.securities.personal.ui.viewmodel.LoginPswViewModel

/**
 * Created by Maxwell.
 * E-mail: maxwell_smith@163.com
 * Date: 2019/8/20
 * Desc:
 */
class LoginPswPresenter(context: Context) : AbsNetPresenter<LoginPswView, LoginPswViewModel>(){
    /* 加载进度条 */
    private val progressDialog by lazy {
        ProgressDialog(context)
    }
    private val errorDialog by lazy {
        ErrorTimesDialog(context,2)
    }
    override fun init() {
        super.init()
        view?.init()
    }
    fun requestLoginPwd(phone: kotlin.String,password: kotlin.String,phoneArea:kotlin.String) {
        dialogshow(1)
        val request = UserLoginPwdRequest(phone, password,"0086", transactions.createTransaction())
        Cache[IPersonalNet::class.java]?.userLoginByPwd(request)
            ?.enqueue(Network.IHCallBack<UserLoginCodeResponse>(request))
    }

    @RxSubscribe(observeOnThread = EventThread.MAIN)
    fun onUserLoginPwdResponse(response: UserLoginCodeResponse) {
        dialogshow(0)
        if (response.request is UserLoginPwdRequest) {
            if (LocalAccountConfig.read().saveLogin(
                    response.data.userId,
                    response.data.phone,
                    response.data.token
                )
            ) {
                view?.gotomain()
                // 通知登录状态发生改变
                RxBus.getDefault().post(LoginStateChangeEvent(true))
            }
        }
    }
    @RxSubscribe(observeOnThread = EventThread.MAIN)
    fun onErrorRes(response: ErrorResponse) {
        if (response.request is UserLoginPwdRequest) {
            dialogshow(0)
            if(response.code=="010006"){
                if(response.errordata?.loginCount==0) {
                    showErrorDailog()
                }
            }
        }
    }

    private fun showPswError() {

    }


    fun dialogshow(type:Int){
        when(type){
            1->{
                progressDialog.setCancelable(false)
                progressDialog.show()
            }
            else->{
                if(progressDialog!=null) {
                    progressDialog.setCancelable(true)
                    progressDialog.dismiss()
                }
            }
        }
    }
    fun showErrorDailog() {
        errorDialog.show()
        errorDialog.setOnclickListener( View.OnClickListener {
            when(it.id){
                R.id.rl_complete_psw->{
                    errorDialog.dismiss()
                }
            }
        })
    }

    @RxSubscribe(observeOnThread = EventThread.COMPUTATION)
    fun onLoginStateChangeEvent(event: DisctCodeSelectEvent) {
        // 传值
        viewModel?.strdisct?.set(event.str)
        viewModel?.code?.set(event.code)
    }

}