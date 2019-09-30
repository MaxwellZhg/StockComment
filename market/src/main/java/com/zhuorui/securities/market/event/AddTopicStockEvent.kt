package com.zhuorui.securities.market.event

import com.zhuorui.securities.market.model.SearchStockInfo

/**
 *    author : PengXianglin
 *    e-mail : peng_xianglin@163.com
 *    date   : 2019/8/8 16:40
 *    desc   : 传递添加自选股
 */
class AddTopicStockEvent(val stock: SearchStockInfo)