package com.zhuorui.securities.market.ui

import android.os.Bundle
import androidx.lifecycle.ViewModelProviders
import com.zhuorui.securities.base2app.ui.fragment.AbsSwipeBackNetFragment
import com.zhuorui.securities.market.BR
import com.zhuorui.securities.market.R
import com.zhuorui.securities.market.model.TestEnum
import com.zhuorui.securities.market.ui.presenter.TestTabTwoPresenter
import com.zhuorui.securities.market.ui.view.TestTabTwoView
import com.zhuorui.securities.market.ui.viewmodel.TestTabTwoViewModel

class TabTwoFragment :AbsSwipeBackNetFragment<com.zhuorui.securities.market.databinding.FragmentTabTwoBinding,TestTabTwoViewModel,TestTabTwoView,TestTabTwoPresenter>(),TestTabTwoView{
    override val layout: Int
        get() =  R.layout.fragment_tab_two
    override val viewModelId: Int
        get() = BR.viewModel
    override val createPresenter: TestTabTwoPresenter
        get() = TestTabTwoPresenter()
    override val createViewModel: TestTabTwoViewModel?
        get() = ViewModelProviders.of(this).get(TestTabTwoViewModel::class.java)
    override val getView: TestTabTwoView
        get() = this

    companion object {
        fun newInstance(type: TestEnum?): TabTwoFragment {
            val fragment = TabTwoFragment()
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