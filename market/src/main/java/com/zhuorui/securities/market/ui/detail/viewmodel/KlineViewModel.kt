package com.zhuorui.securities.market.ui.detail.viewmodel

import androidx.lifecycle.ViewModel
import com.zhuorui.securities.market.model.StockTopic
import com.zhuorui.securities.market.stockChart.data.KLineDataManage

/**
 *    author : PengXianglin
 *    e-mail : peng_xianglin@163.com
 *    date   : 2019/8/22 11:02
 *    desc   :
 */
class KlineViewModel : ViewModel() {

    var stockTopic: StockTopic? = null
    var kDataManager: KLineDataManage? = null
}