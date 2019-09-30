package com.zhuorui.securities.market.net.request

import com.zhuorui.securities.base2app.network.BaseRequest

/**
 *    author : PengXianglin
 *    e-mail : peng_xianglin@163.com
 *    date   : 2019/8/8 11:37
 *    desc   : 自选股股票列表（含大盘指数）
 */
class RecommendStocklistRequest(val ts: String?, val currentPage: Int, val pageSize: Int, transaction: String) :
    BaseRequest(transaction) {
    init {
        generateSign()
    }
}