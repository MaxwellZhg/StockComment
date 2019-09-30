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
import com.zhuorui.securities.base2app.util.ResUtil
import com.zhuorui.securities.personal.event.LoginStateChangeEvent
import com.zhuorui.securities.personal.R
import com.zhuorui.securities.personal.config.LocalAccountConfig
import com.zhuorui.securities.personal.event.DisctCodeSelectEvent
import com.zhuorui.securities.personal.net.IPersonalNet
import com.zhuorui.securities.personal.net.request.SendLoginCodeRequest
import com.zhuorui.securities.personal.net.request.UserLoginCodeRequest
import com.zhuorui.securities.personal.net.request.UserLoginOutRequest
import com.zhuorui.securities.personal.net.response.SendLoginCodeResponse
import com.zhuorui.securities.personal.net.response.UserLoginCodeResponse
import com.zhuorui.securities.personal.ui.dailog.ErrorTimesDialog
import com.zhuorui.securities.personal.ui.view.LoginRegisterView
import com.zhuorui.securities.personal.ui.viewmodel.LoginRegisterViewModel
import java.util.*

/**
 * Created by Maxwell.
 * E-mail: maxwell_smith@163.com
 * Date: 2019/8/20
 * Desc:
 */
class LoginRegisterPresenter(context: Context): AbsNetPresenter<LoginRegisterView, LoginRegisterViewModel>(){
    internal var timer: Timer? = null
    private var recLen = 60//跳过倒计时提示5秒
    internal var task: TimerTask? = null
    /* 加载进度条 */
    private val progressDialog by lazy {
        ProgressDialog(context)
    }
    private val errorDialog by lazy {
        ErrorTimesDialog(context,1)
    }
    override fun init() {
        super.init()
        view?.init()
    }


    fun requestSendLoginCode(str: kotlin.String) {
        dialogshow(1)
        val request = SendLoginCodeRequest(str, "0086", transactions.createTransaction())
        Cache[IPersonalNet::class.java]?.sendLoginCode(request)
            ?.enqueue(Network.IHCallBack<SendLoginCodeResponse>(request))
    }

    fun requestUserLoginCode(str: kotlin.String,vfcode:kotlin.String) {
        dialogshow(1)
        val request = UserLoginCodeRequest(str, vfcode,"0086", transactions.createTransaction())
        Cache[IPersonalNet::class.java]?.userLoginCode(request)
            ?.enqueue(Network.IHCallBack<UserLoginCodeResponse>(request))
    }
    @RxSubscribe(observeOnThread = EventThread.MAIN)
    fun onSendLoginCodeResponse(response: SendLoginCodeResponse) {
        if(response.request is SendLoginCodeRequest){
               dialogshow(0)
               startTimeCountDown()
        }
    }
    @RxSubscribe(observeOnThread = EventThread.MAIN)
    fun onUserLoginCodeResponse(response: UserLoginCodeResponse) {
        if (response.request is UserLoginCodeRequest) {
               dialogshow(0)
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
    fun onUserLoginOutResponse(response: SendLoginCodeResponse) {
        if (response.request is UserLoginOutRequest) {
            view?.gotomain()
        }
    }
    @RxSubscribe(observeOnThread = EventThread.MAIN)
    fun onErrorRes(response: ErrorResponse) {
        if (response.request is UserLoginCodeRequest) {
             dialogshow(0)
            if(response.code=="010003"){
                view?.gotopsw()
            }
            if(response.msg=="当天短信验证码超过次"){
                showErrorDailog()
            }
        }else if(response.request is SendLoginCodeRequest){
              dialogshow(0)
        }
    }

    fun requestUserLoginOut() {
        val request = UserLoginOutRequest("",transactions.createTransaction())
        Cache[IPersonalNet::class.java]?.userLoginOut(request)
            ?.enqueue(Network.IHCallBack<SendLoginCodeResponse>(request))
    }
    fun setState(value:Int){
        viewModel!!.state.set(value)
    }

    @Throws(InterruptedException::class)
    fun startTask() {
        if (task == null) {
            timer = Timer()
            task = object : TimerTask() {
                override fun run() {
                    recLen--
                    viewModel?.str?.set(ResUtil.getStringFormat(R.string.credit_time, recLen))
                    if (recLen < 0) {
                        timer!!.cancel()
                        task = null
                        timer = null
                        viewModel?.str?.set(ResUtil.getString(R.string.send_verification_code))
                    }
                }
            }
        }
        timer!!.schedule(task, 1000, 1000)
    }

      fun startTimeCountDown() {
        if (recLen == 60) {
            try {
                startTask()
            } catch (e: InterruptedException) {
                e.printStackTrace()
            }

        } else if (recLen < 0) {
            recLen = 60
            try {
                startTask()
            } catch (e: InterruptedException) {
                e.printStackTrace()
            }
        }
    }

    fun dialogshow(type:Int){
        when(type){
            1->{
                progressDialog.setCancelable(false)
                progressDialog.show()
              }
            else->{
                progressDialog.setCancelable(true)
                progressDialog.dismiss()

            }
            }
        }

    fun showErrorDailog() {
        errorDialog.show()
        errorDialog.setOnclickListener( View.OnClickListener {
            when(it.id){
                R.id.rl_complete_verify->{
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