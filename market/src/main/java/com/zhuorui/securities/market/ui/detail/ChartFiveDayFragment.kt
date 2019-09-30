package com.zhuorui.securities.market.ui.detail

import android.os.Bundle
import androidx.lifecycle.ViewModelProviders
import com.zhuorui.securities.base2app.ui.fragment.AbsFragment
import com.zhuorui.securities.market.BR
import com.zhuorui.securities.market.R
import com.zhuorui.securities.market.databinding.FragmentFiveDayBinding
import com.zhuorui.securities.market.stockChart.data.TimeDataManage
import com.zhuorui.securities.market.ui.detail.presenter.ChartFiveDayPresenter
import com.zhuorui.securities.market.ui.detail.view.FiveDayKlineView
import com.zhuorui.securities.market.ui.detail.viewmodel.FiveDayKlineViewModel
import kotlinx.android.synthetic.main.fragment_five_day.*

/**
 * 五日分时页
 */
class ChartFiveDayFragment :
    AbsFragment<FragmentFiveDayBinding, FiveDayKlineViewModel, FiveDayKlineView, ChartFiveDayPresenter>(),
    FiveDayKlineView {

    private var land: Boolean = false // 是否横屏

    companion object {

        fun newInstance(land: Boolean): ChartFiveDayFragment {
            val fragment = ChartFiveDayFragment()
            val bundle = Bundle()
            bundle.putBoolean("landscape", land)
            fragment.arguments = bundle
            return fragment
        }
    }

    override val layout: Int
        get() = R.layout.fragment_five_day


    override val viewModelId: Int
        get() = BR.viewModel

    override val createPresenter: ChartFiveDayPresenter
        get() = ChartFiveDayPresenter()

    override val createViewModel: FiveDayKlineViewModel?
        get() = ViewModelProviders.of(this).get(FiveDayKlineViewModel::class.java)

    override val getView: FiveDayKlineView
        get() = this

    override fun onLazyInitView(savedInstanceState: Bundle?) {
        super.onLazyInitView(savedInstanceState)
        land = arguments!!.getBoolean("landscape")
        chart!!.initChart(land)

        presenter?.loadKlineFiveDayData()
    }

    override fun setDataToChart(timeDataManage: TimeDataManage?) {
        chart?.setDataToChart(timeDataManage)
    }
}