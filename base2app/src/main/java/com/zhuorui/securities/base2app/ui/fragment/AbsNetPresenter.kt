package com.zhuorui.securities.base2app.ui.fragment

import androidx.lifecycle.ViewModel
import com.zhuorui.securities.base2app.R
import com.zhuorui.securities.base2app.network.BaseResponse
import com.zhuorui.securities.base2app.network.ErrorResponse
import com.zhuorui.securities.base2app.rxbus.EventThread
import com.zhuorui.securities.base2app.rxbus.RxSubscribe
import com.zhuorui.securities.base2app.util.ResUtil.getString

/**
 *    author : PengXianglin
 *    e-mail : peng_xianglin@163.com
 *    date   : 2019/8/16 15:32
 *    desc   :
 */
abstract class AbsNetPresenter<V : AbsView, VM : ViewModel> : AbsEventPresenter<V, VM>() {

    @RxSubscribe(observeOnThread = EventThread.MAIN)
    fun onSuccessful(response: BaseResponse) {
        if (!transactions.isMyTransaction(response)) return
        onBaseResponse(response)
    }

    /***
     * 针对默认的请求成功，进行回调接收，具体处理由子类去实现
     */
    protected open fun onBaseResponse(response: BaseResponse) {

    }

    @RxSubscribe(observeOnThread = EventThread.MAIN)
    fun onError(response: ErrorResponse) {
        if (!transactions.isMyTransaction(response)) return
        onErrorResponse(response)
    }

    /**
     * 默认针对于请求失败，进行toast提示
     *
     * @param response ErrorResponse
     */
    protected open fun onErrorResponse(response: ErrorResponse) {
        toast(if (response.isNetworkBroken) getString(R.string.network_anomaly) else response.msg)
    }
}