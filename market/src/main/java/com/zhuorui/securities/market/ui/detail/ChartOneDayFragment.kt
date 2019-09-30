package com.zhuorui.securities.market.ui.detail

import android.os.Bundle
import androidx.lifecycle.ViewModelProviders
import com.zhuorui.securities.base2app.ui.fragment.AbsFragment
import com.zhuorui.securities.market.BR
import com.zhuorui.securities.market.R
import com.zhuorui.securities.market.databinding.FragmentOneDayBinding
import com.zhuorui.securities.market.stockChart.data.TimeDataManage
import com.zhuorui.securities.market.ui.detail.presenter.ChartOneDayPresenter
import com.zhuorui.securities.market.ui.detail.view.OneDayKlineView
import com.zhuorui.securities.market.ui.detail.viewmodel.OneDayKlineViewModel
import kotlinx.android.synthetic.main.fragment_one_day.*

/**
 * 分时页
 */
class ChartOneDayFragment :
    AbsFragment<FragmentOneDayBinding, OneDayKlineViewModel, OneDayKlineView, ChartOneDayPresenter>(),
    OneDayKlineView {

    private var land: Boolean = false // 是否横屏

    companion object {

        fun newInstance(land: Boolean): ChartOneDayFragment {
            val fragment = ChartOneDayFragment()
            val bundle = Bundle()
            bundle.putBoolean("landscape", land)
            fragment.arguments = bundle
            return fragment
        }
    }

    override val layout: Int
        get() = R.layout.fragment_one_day

    override val viewModelId: Int
        get() = BR.viewModel

    override val createPresenter: ChartOneDayPresenter
        get() = ChartOneDayPresenter()

    override val createViewModel: OneDayKlineViewModel?
        get() = ViewModelProviders.of(this).get(OneDayKlineViewModel::class.java)

    override val getView: OneDayKlineView
        get() = this

    override fun onLazyInitView(savedInstanceState: Bundle?) {
        super.onLazyInitView(savedInstanceState)
        land = arguments!!.getBoolean("landscape")
        chart!!.initChart(land)


        presenter?.loadDBKlineMinuteData()
        presenter?.loadKNetlineMinuteData()
    }

    override fun setDataToChart(timeDataManage: TimeDataManage?) {
        chart?.setDataToChart(timeDataManage)
    }
}