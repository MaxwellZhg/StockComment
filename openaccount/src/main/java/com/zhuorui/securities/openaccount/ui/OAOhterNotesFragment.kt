package com.zhuorui.securities.openaccount.ui

import android.view.View
import androidx.lifecycle.ViewModelProviders
import com.zhuorui.securities.base2app.ui.fragment.AbsSwipeBackFragment
import com.zhuorui.securities.openaccount.BR
import com.zhuorui.securities.openaccount.R
import com.zhuorui.securities.openaccount.databinding.FragmentOaOhterNotesBinding
import com.zhuorui.securities.openaccount.ui.presenter.OAOhterNotesPresenter
import com.zhuorui.securities.openaccount.ui.view.OAOhterNotesView
import com.zhuorui.securities.openaccount.ui.viewmodel.OAOhterNotesViewModel
import kotlinx.android.synthetic.main.fragment_oa_property_status.*

/**
 *    author : liuwei
 *    e-mail : vsanliu@foxmail.com
 *    date   : 2019-08-29 15:13
 *    desc   : 风险测评-个人信息
 */
class OAOhterNotesFragment :
    AbsSwipeBackFragment<FragmentOaOhterNotesBinding, OAOhterNotesViewModel, OAOhterNotesView, OAOhterNotesPresenter>(),
    OAOhterNotesView, View.OnClickListener {

    companion object {
        fun newInstance(): OAOhterNotesFragment {
            val fragment = OAOhterNotesFragment()
            return fragment
        }
    }


    override val layout: Int
        get() = R.layout.fragment_oa_ohter_notes

    override val viewModelId: Int
        get() = BR.viewModel

    override val createPresenter: OAOhterNotesPresenter
        get() = OAOhterNotesPresenter()

    override val createViewModel: OAOhterNotesViewModel?
        get() = ViewModelProviders.of(this).get(OAOhterNotesViewModel::class.java)

    override val getView: OAOhterNotesView
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
//                start(OAPropertyStatusFragment.newInstance())
            }

        }
    }
}