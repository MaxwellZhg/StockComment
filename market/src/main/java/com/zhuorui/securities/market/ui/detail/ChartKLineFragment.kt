package com.zhuorui.securities.market.ui.detail

import android.os.Bundle
import androidx.lifecycle.ViewModelProviders
import com.zhuorui.securities.base2app.ui.fragment.AbsFragment
import com.zhuorui.securities.market.BR
import com.zhuorui.securities.market.R
import com.zhuorui.securities.market.databinding.FragmentKlineBinding
import com.zhuorui.securities.market.stockChart.data.KLineDataManage
import com.zhuorui.securities.market.ui.detail.presenter.ChartKLinePresenter
import com.zhuorui.securities.market.ui.detail.view.KlineView
import com.zhuorui.securities.market.ui.detail.viewmodel.KlineViewModel
import kotlinx.android.synthetic.main.fragment_kline.*

/**
 * K线
 */
class ChartKLineFragment : AbsFragment<FragmentKlineBinding, KlineViewModel, KlineView, ChartKLinePresenter>(),
    KlineView {

    private var land: Boolean = false//是否横屏

    companion object {

        fun newInstance(type: Int, land: Boolean): ChartKLineFragment {
            val fragment = ChartKLineFragment()
            val bundle = Bundle()
            bundle.putInt("type", type)
            bundle.putBoolean("landscape", land)
            fragment.arguments = bundle
            return fragment
        }
    }

    override val layout: Int
        get() = R.layout.fragment_kline

    override val viewModelId: Int
        get() = BR.viewModel

    override val createPresenter: ChartKLinePresenter
        get() = ChartKLinePresenter()

    override val createViewModel: KlineViewModel?
        get() = ViewModelProviders.of(this).get(KlineViewModel::class.java)

    override val getView: KlineView
        get() = this

    override fun onLazyInitView(savedInstanceState: Bundle?) {
        super.onLazyInitView(savedInstanceState)

        land = arguments!!.getBoolean("landscape")
        combinedchart.initChart(land)

        presenter?.kType = arguments!!.getInt("type")
        presenter?.land = land

//        try {
//            if (mType == 1) {
//                `object` = JSONObject(ChartData.KLINEDATA)
//            } else if (mType == 7) {
//                `object` = JSONObject(ChartData.KLINEWEEKDATA)
//            } else if (mType == 30) {
//                `object` = JSONObject(ChartData.KLINEMONTHDATA)
//            }
//        } catch (e: JSONException) {
//            e.printStackTrace()
//        }

        combinedchart.getGestureListenerBar().setCoupleClick {
            if (land) {
                presenter?.loadIndexData()
            }
        }

        presenter?.loadKlineData()
    }

    override fun doBarChartSwitch(indexType: Int) {
        combinedchart.doBarChartSwitch(indexType)
    }

    override fun setDataToChart(kLineDataManage: KLineDataManage?) {
        combinedchart.setDataToChart(kLineDataManage)
    }
}