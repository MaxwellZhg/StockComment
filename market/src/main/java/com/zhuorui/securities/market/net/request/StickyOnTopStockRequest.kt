package com.zhuorui.securities.market.net.request

import com.zhuorui.securities.base2app.network.BaseRequest

/**
 *    author : PengXianglin
 *    e-mail : peng_xianglin@163.com
 *    date   : 2019/8/23 14:19
 *    desc   : 置顶自选股
 */
class StickyOnTopStockRequest(val custom: Any, val id: String, transaction: String) : BaseRequest(transaction) {

    init {
        generateSign()
    }
}