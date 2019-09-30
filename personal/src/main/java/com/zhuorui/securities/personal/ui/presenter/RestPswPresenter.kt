package com.zhuorui.securities.personal.ui.presenter
import android.content.Context
import com.zhuorui.commonwidget.dialog.ProgressDialog
import com.zhuorui.securities.base2app.Cache
import com.zhuorui.securities.base2app.network.ErrorResponse
import com.zhuorui.securities.base2app.network.Network
import com.zhuorui.securities.base2app.rxbus.EventThread
import com.zhuorui.securities.base2app.rxbus.RxSubscribe
import com.zhuorui.securities.base2app.ui.fragment.AbsNetPresenter
import com.zhuorui.securities.base2app.util.ResUtil
import com.zhuorui.securities.personal.R
import com.zhuorui.securities.personal.net.IPersonalNet
import com.zhuorui.securities.personal.net.request.RestLoginPswRequest
import com.zhuorui.securities.personal.net.response.SendLoginCodeResponse
import com.zhuorui.securities.personal.ui.view.RestPswView
import com.zhuorui.securities.personal.ui.viewmodel.RestPswViewModel
import java.util.regex.Pattern

/**
 * Created by Maxwell.
 * E-mail: maxwell_smith@163.com
 * Date: 2019/8/21
 * Desc:
 */
class RestPswPresenter(context: Context):AbsNetPresenter<RestPswView, RestPswViewModel>() {
    override fun init() {
        super.init()
        view?.init()
    }
    /* 加载进度条 */
    private val progressDialog by lazy {
        ProgressDialog(context)
    }
    fun setTips(strnew:String?,strensure:String?){
        viewModel?.strnew?.set(strnew)
        viewModel?.strensure?.set(strensure)
    }
    fun detailtips(strnewpsw:String,strensurepsw:String){
        if (strnewpsw == "") {
            viewModel?.strnew?.set(ResUtil.getString(R.string.input_new_pws_mix))
            return
        }else{
            if (strnewpsw.length<6){
                viewModel?.strnew?.set(ResUtil.getString(R.string.input_new_pws_mix))
                if(strensurepsw != ""){
                    if(strnewpsw==strensurepsw){
                        viewModel?.strensure?.set(ResUtil.getString(R.string.input_new_pws_mix))
                        return
                    }else{
                        viewModel?.strensure?.set(ResUtil.getString(R.string.compare_no_match))
                        return
                    }
                }
            }else {
                val pattern = "^(?![A-Z]+$)(?![a-z]+$)(?!\\d+$)(?![\\W_]+$)\\S{6,20}$"
                //用正则式匹配文本获取匹配器
                val matcher = Pattern.compile(pattern).matcher(strnewpsw)
                if (!matcher.find()) {
                    viewModel?.strnew?.set(ResUtil.getString(R.string.new_psw_no_match))
                    if(strensurepsw != ""){
                        if(strnewpsw==strensurepsw){
                            viewModel?.strensure?.set(ResUtil.getString(R.string.new_psw_no_match))
                            return
                        }else{
                            viewModel?.strensure?.set(ResUtil.getString(R.string.compare_no_match))
                            return
                        }
                    }
                }
            }
        }
        if (strensurepsw == "") {
            viewModel?.strensure?.set(ResUtil.getString(R.string.compare_no_match))
            return
        }else{
            if(strnewpsw==strensurepsw){
                val pattern = "^(?![A-Z]+$)(?![a-z]+$)(?!\\d+$)(?![\\W_]+$)\\S{6,20}$"
                //用正则式匹配文本获取匹配器
                val matcher = Pattern.compile(pattern).matcher(strnewpsw)
                if (!matcher.find()) {
                    viewModel?.strnew?.set(ResUtil.getString(R.string.new_psw_no_match))
                    viewModel?.strensure?.set(ResUtil.getString(R.string.new_psw_no_match))
                    return
                }else {
                    viewModel?.strnew?.set("")
                    viewModel?.strensure?.set("")
                    return
                }
            }else{
                viewModel?.strnew?.set("")
                viewModel?.strensure?.set(ResUtil.getString(R.string.compare_no_match))
            }
            return
        }
    }
    fun requestRestLoginPsw(phone: kotlin.String?,newpsw:kotlin.String,code: kotlin.String?) {
        dialogshow(1)
        val request = RestLoginPswRequest(phone, newpsw,code, transactions.createTransaction())
        Cache[IPersonalNet::class.java]?.restLoginPsw(request)
            ?.enqueue(Network.IHCallBack<SendLoginCodeResponse>(request))
    }

    @RxSubscribe(observeOnThread = EventThread.MAIN)
    fun onRestLoginPswResponse(response: SendLoginCodeResponse) {
        if (response.request is RestLoginPswRequest) {
             dialogshow(0)
              view?.gotopswlogin()
        }
    }

    @RxSubscribe(observeOnThread = EventThread.MAIN)
    fun onErrorRes(response: ErrorResponse) {
        if (response.request is RestLoginPswRequest) {
            dialogshow(0)
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


}