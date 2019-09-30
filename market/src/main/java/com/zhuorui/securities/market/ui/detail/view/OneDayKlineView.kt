package com.zhuorui.securities.market.ui.detail.view

import com.zhuorui.securities.base2app.ui.fragment.AbsView
import com.zhuorui.securities.market.stockChart.data.TimeDataManage

/**
 *    author : PengXianglin
 *    e-mail : peng_xianglin@163.com
 *    date   : 2019/8/22 9:50
 *    desc   :
 */
interface OneDayKlineView : AbsView {

    fun setDataToChart(timeDataManage: TimeDataManage?)
}