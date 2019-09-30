package com.zhuorui.securities.market.event

import com.zhuorui.securities.market.model.StockMarketInfo

/**
 *    author : PengXianglin
 *    e-mail : peng_xianglin@163.com
 *    date   : 2019/8/23 18:20
 *    desc   : 删除自选股事件
 */
class DeleteTopicStockEvent(val stockInfo: StockMarketInfo) {
}