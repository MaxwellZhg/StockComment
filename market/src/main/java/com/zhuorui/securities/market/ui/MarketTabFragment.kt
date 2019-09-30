package com.zhuorui.securities.market.ui

import androidx.lifecycle.ViewModelProviders
import com.zhuorui.securities.base2app.ui.fragment.AbsBackFinishFragment
import com.zhuorui.securities.market.BR
import com.zhuorui.securities.market.R
import com.zhuorui.securities.market.databinding.FragmentMarketTabBinding
import com.zhuorui.securities.market.ui.presenter.MarketTabPresenter
import com.zhuorui.securities.market.ui.view.MarketTabVierw
import com.zhuorui.securities.market.ui.viewmodel.MarketTabVierwModel

/**
 *    author : PengXianglin
 *    e-mail : peng_xianglin@163.com
 *    date   : 2019/8/27 13:40
 *    desc   : 主页行情Tab页面
 */
class MarketTabFragment :
    AbsBackFinishFragment<FragmentMarketTabBinding, MarketTabVierwModel, MarketTabVierw, MarketTabPresenter>(),
    MarketTabVierw {

    companion object {
        fun newInstance(): MarketTabFragment {
            return MarketTabFragment()
        }
    }

    override val layout: Int
        get() = R.layout.fragment_market_tab

    override val viewModelId: Int
        get() = BR.viewModel

    override val createPresenter: MarketTabPresenter
        get() = MarketTabPresenter()

    override val createViewModel: MarketTabVierwModel?
        get() = ViewModelProviders.of(this).get(MarketTabVierwModel::class.java)

    override val getView: MarketTabVierw
        get() = this

}