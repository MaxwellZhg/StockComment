package com.zhuorui.securities.openaccount.ui

import android.view.View
import androidx.lifecycle.ViewModelProviders
import com.zhuorui.securities.base2app.ui.fragment.AbsSwipeBackFragment
import com.zhuorui.securities.openaccount.BR
import com.zhuorui.securities.openaccount.R
import com.zhuorui.securities.openaccount.databinding.FragmentOaInvestmentExperienceBinding
import com.zhuorui.securities.openaccount.ui.presenter.OAInvestmentExperiencePresenter
import com.zhuorui.securities.openaccount.ui.view.OAInvestmentExperienceView
import com.zhuorui.securities.openaccount.ui.viewmodel.OAInvestmentExperienceViewModel
import kotlinx.android.synthetic.main.fragment_oa_property_status.*

/**
 *    author : liuwei
 *    e-mail : vsanliu@foxmail.com
 *    date   : 2019-08-29 15:13
 *    desc   : 风险测评-投资经验
 */
class OAInvestmentExperienceFragment :
    AbsSwipeBackFragment<FragmentOaInvestmentExperienceBinding, OAInvestmentExperienceViewModel, OAInvestmentExperienceView, OAInvestmentExperiencePresenter>(),
    OAInvestmentExperienceView, View.OnClickListener {

    companion object {
        fun newInstance(): OAInvestmentExperienceFragment {
            val fragment = OAInvestmentExperienceFragment()
            return fragment
        }
    }

    override val layout: Int
        get() = R.layout.fragment_oa_investment_experience

    override val viewModelId: Int
        get() = BR.viewModel

    override val createPresenter: OAInvestmentExperiencePresenter
        get() = OAInvestmentExperiencePresenter()

    override val createViewModel: OAInvestmentExperienceViewModel?
        get() = ViewModelProviders.of(this).get(OAInvestmentExperienceViewModel::class.java)

    override val getView: OAInvestmentExperienceView
        get() = this

    override fun init() {
        btn_per.setOnClickListener(this)
        btn_next.setOnClickListener(this)
    }

    override fun onClick(p0: View?) {
        when (p0?.id) {
            R.id.btn_per -> {
                pop()
            }
            R.id.btn_next -> {
                start(OAOhterNotesFragment.newInstance())
            }

        }
    }
}