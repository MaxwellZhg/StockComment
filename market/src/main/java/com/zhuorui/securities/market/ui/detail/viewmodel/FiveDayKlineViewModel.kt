package com.zhuorui.securities.market.ui.detail.viewmodel

import androidx.lifecycle.ViewModel
import com.zhuorui.securities.market.model.StockTopic
import com.zhuorui.securities.market.stockChart.data.TimeDataManage

/**
 *    author : PengXianglin
 *    e-mail : peng_xianglin@163.com
 *    date   : 2019/8/22 9:48
 *    desc   : 分时数据
 */
class FiveDayKlineViewModel : ViewModel() {
    var stockTopic: StockTopic? = null
    var kDataManager: TimeDataManage? = null
}