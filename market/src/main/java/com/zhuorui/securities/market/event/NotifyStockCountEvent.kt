package com.zhuorui.securities.market.event

import com.zhuorui.securities.market.model.StockTsEnum

/**
 *    author : PengXianglin
 *    e-mail : peng_xianglin@163.com
 *    date   : 2019/8/21 18:22
 *    desc   : 更新自选股条数事件
 */
class NotifyStockCountEvent(val ts: StockTsEnum?, val count: Int)