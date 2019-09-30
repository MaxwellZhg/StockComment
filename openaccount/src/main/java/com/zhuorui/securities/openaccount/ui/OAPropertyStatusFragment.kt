package com.zhuorui.securities.openaccount.ui

import android.view.View
import androidx.lifecycle.ViewModelProviders
import com.zhuorui.securities.base2app.ui.fragment.AbsSwipeBackFragment
import com.zhuorui.securities.openaccount.BR
import com.zhuorui.securities.openaccount.R
import com.zhuorui.securities.openaccount.databinding.FragmentOaPropertyStatusBinding
import com.zhuorui.securities.openaccount.ui.presenter.OAPropertyStatusPresenter
import com.zhuorui.securities.openaccount.ui.view.OAPropertyStatusView
import com.zhuorui.securities.openaccount.ui.viewmodel.OAPropertyStatusViewModel
import kotlinx.android.synthetic.main.fragment_oa_property_status.*

/**
 *    author : liuwei
 *    e-mail : vsanliu@foxmail.com
 *    date   : 2019-08-29 15:13
 *    desc   : 风险测评-财产状况
 */
class OAPropertyStatusFragment :
    AbsSwipeBackFragment<FragmentOaPropertyStatusBinding, OAPropertyStatusViewModel, OAPropertyStatusView, OAPropertyStatusPresenter>(),
    OAPropertyStatusView, View.OnClickListener {


    companion object {
        fun newInstance(): OAPropertyStatusFragment {
            val fragment = OAPropertyStatusFragment()
            return fragment
        }
    }


    override val layout: Int
        get() = R.layout.fragment_oa_property_status

    override val viewModelId: Int
        get() = BR.viewModel

    override val createPresenter: OAPropertyStatusPresenter
        get() = OAPropertyStatusPresenter()

    override val createViewModel: OAPropertyStatusViewModel?
        get() = ViewModelProviders.of(this).get(OAPropertyStatusViewModel::class.java)

    override val getView: OAPropertyStatusView
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
                start(OAInvestmentExperienceFragment.newInstance())
            }

        }
    }
}