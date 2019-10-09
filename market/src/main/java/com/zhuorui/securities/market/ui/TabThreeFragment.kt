package com.zhuorui.securities.market.ui

import android.os.Bundle
import androidx.lifecycle.ViewModelProviders
import com.zhuorui.securities.base2app.ui.fragment.AbsSwipeBackNetFragment
import com.zhuorui.securities.market.BR
import com.zhuorui.securities.market.R
import com.zhuorui.securities.market.model.StockTsEnum
import com.zhuorui.securities.market.model.TestEnum
import com.zhuorui.securities.market.ui.presenter.TestTabThreePresenter
import com.zhuorui.securities.market.ui.view.TestTabThreeView
import com.zhuorui.securities.market.ui.viewmodel.TestTabThreeViewModel

class TabThreeFragment :AbsSwipeBackNetFragment<com.zhuorui.securities.market.databinding.FragmentTabThreeBinding,TestTabThreeViewModel,TestTabThreeView,TestTabThreePresenter>(),TestTabThreeView{
    override val layout: Int
        get() =  R.layout.fragment_tab_three
    override val viewModelId: Int
        get() = BR.viewModel
    override val createPresenter: TestTabThreePresenter
        get() = TestTabThreePresenter()
    override val createViewModel: TestTabThreeViewModel?
        get() = ViewModelProviders.of(this).get(TestTabThreeViewModel::class.java)
    override val getView: TestTabThreeView
        get() = this
    override fun rootViewFitsSystemWindowsPadding(): Boolean {
        return true
    }

    companion object {
        fun newInstance(type: TestEnum?): TabThreeFragment {
            val fragment = TabThreeFragment()
            if (type != null) {
                val bundle = Bundle()
                bundle.putSerializable("type", type)
                fragment.arguments = bundle
            }
            return fragment
        }
    }
    override fun onLazyInitView(savedInstanceState: Bundle?) {
        super.onLazyInitView(savedInstanceState)
        val type = arguments?.getSerializable("type") as TestEnum?
        presenter?.setType(type)
    }
}