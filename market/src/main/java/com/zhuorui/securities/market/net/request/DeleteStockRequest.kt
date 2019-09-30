package com.zhuorui.securities.market.net.request

import com.zhuorui.securities.base2app.network.BaseRequest

/**
 *    author : PengXianglin
 *    e-mail : peng_xianglin@163.com
 *    date   : 2019/8/23 14:19
 *    desc   : 删除自选股
 */
class DeleteStockRequest(val custom: Any, val ids: Array<String?>, transaction: String) : BaseRequest(transaction) {

    init {
        generateSign()
    }
}