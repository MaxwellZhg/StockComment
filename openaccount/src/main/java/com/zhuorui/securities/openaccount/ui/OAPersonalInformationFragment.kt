package com.zhuorui.securities.openaccount.ui

import android.os.Bundle
import android.view.View
import androidx.lifecycle.ViewModelProviders
import androidx.lifecycle.get
import com.zhuorui.securities.base2app.ui.fragment.AbsSwipeBackFragment
import com.zhuorui.securities.openaccount.BR
import com.zhuorui.securities.openaccount.R
import com.zhuorui.securities.openaccount.databinding.FragmentOaPersonalInformationBinding
import com.zhuorui.securities.openaccount.ui.presenter.OAPersonalInformationPresenter
import com.zhuorui.securities.openaccount.ui.view.OAPersonalInformationView
import com.zhuorui.securities.openaccount.ui.viewmodel.OAPersonalInformationViewModel
import kotlinx.android.synthetic.main.fragment_oa_property_status.*

/**
 *    author : liuwei
 *    e-mail : vsanliu@foxmail.com
 *    date   : 2019-08-29 15:13
 *    desc   : 风险测评-个人信息
 */
class OAPersonalInformationFragment :
    AbsSwipeBackFragment<FragmentOaPersonalInformationBinding, OAPersonalInformationViewModel, OAPersonalInformationView, OAPersonalInformationPresenter>(),
    OAPersonalInformationView, View.OnClickListener {

    companion object {
        fun newInstance(): OAPersonalInformationFragment {
            val fragment = OAPersonalInformationFragment()
            return fragment
        }
    }


    override val layout: Int
        get() = R.layout.fragment_oa_personal_information

    override val viewModelId: Int
        get() = BR.viewModel

    override val createPresenter: OAPersonalInformationPresenter
        get() = OAPersonalInformationPresenter()

    override val createViewModel: OAPersonalInformationViewModel?
        get() = ViewModelProviders.of(this).get(OAPersonalInformationViewModel::class.java)

    override val getView: OAPersonalInformationView
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
                start(OAPropertyStatusFragment.newInstance())
            }

        }
    }
}