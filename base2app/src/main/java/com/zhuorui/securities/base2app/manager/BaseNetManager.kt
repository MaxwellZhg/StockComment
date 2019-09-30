package com.zhuorui.securities.base2app.manager

import com.zhuorui.securities.base2app.R
import com.zhuorui.securities.base2app.network.BaseResponse
import com.zhuorui.securities.base2app.network.ErrorResponse
import com.zhuorui.securities.base2app.network.Transactions
import com.zhuorui.securities.base2app.rxbus.EventThread
import com.zhuorui.securities.base2app.rxbus.RxSubscribe
import com.zhuorui.securities.base2app.util.ResUtil
import com.zhuorui.securities.base2app.util.ToastUtil

/**
 * Create by xieyingwu on 2018/4/3.
 * 类描述：BaseManager针对有网络请求的基类管理类
 */
open class BaseNetManager : AbsEventManager() {
    protected var transactions: Transactions = Transactions()

    override fun onDestroy() {
        transactions.clear()
        super.onDestroy()
    }

    /**
     * 请求失败处理
     *
     * @param vo ErrorResponse
     */
    protected open fun baseError(vo: ErrorResponse) {
        ToastUtil.instance.toast(
            if (vo.isNetworkBroken) ResUtil.getString(R.string.network_anomaly)!! else vo.msg ?: ""
        )
    }

    /**
     * 请求成功处理；默认Response为BaseResponse
     *
     * @param vo BaseResponse
     */
    protected open fun baseResponse(vo: BaseResponse) {

    }

    @RxSubscribe(observeOnThread = EventThread.MAIN)
    fun onErrorVo(vo: ErrorResponse) {
        if (!transactions.isMyTransaction(vo)) return
        this.baseError(vo)
    }

    @RxSubscribe(observeOnThread = EventThread.MAIN)
    fun onBaseVo(vo: BaseResponse) {
        if (!transactions.isMyTransaction(vo) || !vo.isSuccess()) return
        this.baseResponse(vo)
    }
}
