package com.zhuorui.securities.market.ui

import android.os.Bundle
import androidx.lifecycle.ViewModelProviders
import com.zhuorui.securities.base2app.ui.fragment.AbsSwipeBackNetFragment
import com.zhuorui.securities.market.BR
import com.zhuorui.securities.market.R
import com.zhuorui.securities.market.model.StockTsEnum
import com.zhuorui.securities.market.model.TestEnum
import com.zhuorui.securities.market.ui.presenter.TestTabOnePresenter
import com.zhuorui.securities.market.ui.view.TestTabOneView
import com.zhuorui.securities.market.ui.viewmodel.TestTabOneViewModel

class TabOneFragment :AbsSwipeBackNetFragment<com.zhuorui.securities.market.databinding.FragmentTabOneBinding,TestTabOneViewModel,TestTabOneView,TestTabOnePresenter>(),TestTabOneView{
    override val layout: Int
        get() =  R.layout.fragment_tab_one
    override val viewModelId: Int
        get() = BR.viewModel
    override val createPresenter: TestTabOnePresenter
        get() = TestTabOnePresenter()
    override val createViewModel: TestTabOneViewModel?
        get() = ViewModelProviders.of(this).get(TestTabOneViewModel::class.java)
    override val getView: TestTabOneView
        get() = this

    companion object {
        fun newInstance(type: TestEnum?): TabOneFragment {
            val fragment = TabOneFragment()
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