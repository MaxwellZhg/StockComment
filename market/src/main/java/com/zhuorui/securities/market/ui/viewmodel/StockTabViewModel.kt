package com.zhuorui.securities.market.ui.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.zhuorui.securities.base2app.util.ResUtil
import com.zhuorui.securities.market.R
import com.zhuorui.securities.market.model.StockTsEnum
import java.util.*

/**
 * Created by Maxwell.
 * E-mail: maxwell_smith@163.com
 * Date: 2019/8/14
 * Desc:
 */
class StockTabViewModel : ViewModel() {

    val toggleStockTab: MutableLiveData<Boolean> = MutableLiveData()
    val allNum: MutableLiveData<Int> = MutableLiveData()
    val hkNum: MutableLiveData<Int> = MutableLiveData()
    val hsNum: MutableLiveData<Int> = MutableLiveData()

    val mfragment: ArrayList<PageInfo> = ArrayList()

    inner class PageInfo(val title: String, val type: StockTsEnum?)

    init {
        toggleStockTab.value = false

        allNum.value = 0
        hkNum.value = 0
        hsNum.value = 0

        // 添加标题页面
        mfragment.add(PageInfo(ResUtil.getString(R.string.all_stock)!!, null))
        mfragment.add(PageInfo(ResUtil.getString(R.string.hk_stock)!!, StockTsEnum.HK))
        mfragment.add(PageInfo(ResUtil.getString(R.string.sh_stock)!!, StockTsEnum.HS))
    }
}