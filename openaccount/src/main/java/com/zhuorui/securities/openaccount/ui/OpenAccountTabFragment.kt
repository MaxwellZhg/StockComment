package com.zhuorui.securities.openaccount.ui

import android.os.Bundle
import android.view.View
import androidx.lifecycle.ViewModelProviders
import com.zhuorui.securities.base2app.ui.fragment.AbsBackFinishFragment
import com.zhuorui.securities.base2app.ui.fragment.AbsFragment
import com.zhuorui.securities.openaccount.BR
import com.zhuorui.securities.openaccount.R
import com.zhuorui.securities.openaccount.databinding.FragmentOpenAccountBinding
import com.zhuorui.securities.openaccount.ui.presenter.OpenAccountTabPresenter
import com.zhuorui.securities.openaccount.ui.view.OpenAccountTabView
import com.zhuorui.securities.openaccount.ui.viewmodel.OpenAccountTabViewModel
import com.zhuorui.securities.personal.ui.LoginRegisterFragment

/**
 * Created by Maxwell.
 * E-mail: maxwell_smith@163.com
 * Date: 2019/8/6
 * Desc:
 */
open class OpenAccountTabFragment :
    AbsBackFinishFragment<FragmentOpenAccountBinding, OpenAccountTabViewModel, OpenAccountTabView, OpenAccountTabPresenter>(),
    OpenAccountTabView, View.OnClickListener {

    override val layout: Int
        get() = R.layout.fragment_open_account

    override val viewModelId: Int
        get() = BR.viewModel

    override val createPresenter: OpenAccountTabPresenter
        get() = OpenAccountTabPresenter()

    override val createViewModel: OpenAccountTabViewModel?
        get() = ViewModelProviders.of(this).get(OpenAccountTabViewModel::class.java)

    override val getView: OpenAccountTabView
        get() = this

    override fun rootViewFitsSystemWindowsPadding(): Boolean {
        return true
    }

    override fun onLazyInitView(savedInstanceState: Bundle?) {
        super.onLazyInitView(savedInstanceState)
        when (presenter?.getLoginStatus()) {
            false -> {
                (parentFragment as AbsFragment<*, *, *, *>).start(LoginRegisterFragment.newInstance())
            }
            true -> {
                (parentFragment as AbsFragment<*, *, *, *>).start(OASelectRegionFragment.newInstance())
            }
        }
    }

    override fun init() {

    }

    override fun onClick(p0: View?) {
        when (p0?.id) {

        }
    }

    companion object {
        fun newInstance(): OpenAccountTabFragment {
            return OpenAccountTabFragment()
        }
    }
}