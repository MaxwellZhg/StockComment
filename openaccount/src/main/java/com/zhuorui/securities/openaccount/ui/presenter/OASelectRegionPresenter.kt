package com.zhuorui.securities.openaccount.ui.presenter

import com.zhuorui.securities.base2app.Cache
import com.zhuorui.securities.base2app.network.Network
import com.zhuorui.securities.base2app.rxbus.EventThread
import com.zhuorui.securities.base2app.rxbus.RxSubscribe
import com.zhuorui.securities.base2app.ui.fragment.AbsNetPresenter
import com.zhuorui.securities.openaccount.manager.OpenInfoManager
import com.zhuorui.securities.openaccount.net.IOpenAccountNet
import com.zhuorui.securities.openaccount.net.request.OpenInfoRequest
import com.zhuorui.securities.openaccount.net.response.OpenInfoResponse
import com.zhuorui.securities.openaccount.ui.view.OASeletRegionView
import com.zhuorui.securities.openaccount.ui.viewmodel.OASelectRegonViewModel

/**
 *    author : liuwei
 *    e-mail : vsanliu@foxmail.com
 *    date   : 2019-08-20 14:19
 *    desc   :
 */
class OASelectRegionPresenter : AbsNetPresenter<OASeletRegionView, OASelectRegonViewModel>() {

    override fun init() {
        super.init()
        requestOpenInfo()
    }

    /**
     * 获取开户信息
     */
    fun requestOpenInfo() {
        val request = OpenInfoRequest(transactions.createTransaction())
        Cache[IOpenAccountNet::class.java]?.getOpenInfo(request)
            ?.enqueue(Network.IHCallBack<OpenInfoResponse>(request))
    }

    @RxSubscribe(observeOnThread = EventThread.COMPUTATION)
    fun onOpenInfoResponse(response: OpenInfoResponse) {
        // 记录开户信息
        OpenInfoManager.getInstance()?.info = response.data
    }
}