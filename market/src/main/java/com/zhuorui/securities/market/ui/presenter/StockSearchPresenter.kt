package com.zhuorui.securities.market.ui.presenter

import com.zhuorui.securities.base2app.Cache
import com.zhuorui.securities.base2app.network.BaseResponse
import com.zhuorui.securities.base2app.network.Network
import com.zhuorui.securities.base2app.rxbus.EventThread
import com.zhuorui.securities.base2app.rxbus.RxBus
import com.zhuorui.securities.base2app.rxbus.RxSubscribe
import com.zhuorui.securities.base2app.ui.fragment.AbsNetPresenter
import com.zhuorui.securities.personal.config.LocalAccountConfig
import com.zhuorui.securities.market.R
import com.zhuorui.securities.market.event.AddTopicStockEvent
import com.zhuorui.securities.market.model.SearchStockInfo
import com.zhuorui.securities.market.net.IStockNet
import com.zhuorui.securities.market.net.request.CollectionStockRequest
import com.zhuorui.securities.market.net.request.StockSearchRequest
import com.zhuorui.securities.market.net.response.StockSearchResponse
import com.zhuorui.securities.market.ui.SearchStocksAdapter
import com.zhuorui.securities.market.ui.view.StockSearchView
import com.zhuorui.securities.market.ui.viewmodel.StockSearchViewModel
import me.jessyan.autosize.utils.LogUtils

/**
 *    author : PengXianglin
 *    e-mail : peng_xianglin@163.com
 *    date   : 2019/8/19 16:16
 *    desc   :
 */
class StockSearchPresenter : AbsNetPresenter<StockSearchView, StockSearchViewModel>() {

    override fun init() {
        super.init()
        view?.init()
    }

    fun getTopicStockData(keyWord: String, count: Int) {
        val requset = StockSearchRequest(keyWord, 0, count, transactions.createTransaction())
        Cache[IStockNet::class.java]?.search(requset)
            ?.enqueue(Network.IHCallBack<StockSearchResponse>(requset))
    }

    @RxSubscribe(observeOnThread = EventThread.MAIN)
    fun onStockSearchResponse(response: StockSearchResponse) {
        val datas = response.data?.datas
        if (datas.isNullOrEmpty()) return
        viewModel?.adapter?.value?.clearItems()
        if (viewModel?.adapter?.value?.items == null) {
            viewModel?.adapter?.value?.items = ArrayList()
        }
        viewModel?.adapter?.value?.addItems(datas)
        LogUtils.e(viewModel?.adapter?.value?.items?.size.toString())

    }

    fun onAddTopicClickItem(item: SearchStockInfo?) {
        // 点击添加到自选列表
        if (LocalAccountConfig.read().isLogin()) {
            // 已登录
            val requset =
                CollectionStockRequest(item!!, item.type!!, item.ts!!, item.code!!, 0, transactions.createTransaction())
            Cache[IStockNet::class.java]?.collection(requset)
                ?.enqueue(Network.IHCallBack<BaseResponse>(requset))
        } else {
            // 未登录
            item?.let { AddTopicStockEvent(it) }?.let {
                RxBus.getDefault().post(it)
                toast(R.string.add_topic_successful)
            }
        }
    }

    override fun onBaseResponse(response: BaseResponse) {
        super.onBaseResponse(response)
        if (response.request is CollectionStockRequest) {
            val stockInfo = (response.request as CollectionStockRequest).custom as SearchStockInfo
            RxBus.getDefault().post(AddTopicStockEvent(stockInfo))
            toast(R.string.add_topic_successful)
        }
    }

    fun getAdapter(): SearchStocksAdapter? {
        if (viewModel?.adapter?.value == null) {
            viewModel?.adapter?.value = SearchStocksAdapter()
        }
        return viewModel?.adapter?.value
    }
}