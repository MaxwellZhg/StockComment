package com.zhuorui.securities.personal.ui

import android.os.Bundle
import androidx.lifecycle.ViewModelProviders
import com.zhuorui.securities.base2app.ui.fragment.AbsBackFinishFragment
import com.zhuorui.securities.base2app.ui.fragment.AbsFragment
import com.zhuorui.securities.personal.BR
import com.zhuorui.securities.personal.R
import com.zhuorui.securities.personal.databinding.FragmentMyTabBinding
import com.zhuorui.securities.personal.ui.presenter.MyTabPresenter
import com.zhuorui.securities.personal.ui.view.MyTabVierw
import com.zhuorui.securities.personal.ui.viewmodel.MyTabVierwModel

/**
 *    author : PengXianglin
 *    e-mail : peng_xianglin@163.com
 *    date   : 2019/8/27 14:10
 *    desc   : 主页“我的”tab界面
 */
class MyTabFragment :
    AbsBackFinishFragment<FragmentMyTabBinding, MyTabVierwModel, MyTabVierw, MyTabPresenter>(),
    MyTabVierw {

    companion object {
        fun newInstance(): MyTabFragment {
            return MyTabFragment()
        }
    }

    override val layout: Int
        get() = R.layout.fragment_my_tab

    override val viewModelId: Int
        get() = BR.viewModel

    override val createPresenter: MyTabPresenter
        get() = MyTabPresenter()

    override val createViewModel: MyTabVierwModel?
        get() = ViewModelProviders.of(this).get(MyTabVierwModel::class.java)

    override val getView: MyTabVierw
        get() = this

    override fun onLazyInitView(savedInstanceState: Bundle?) {
        super.onLazyInitView(savedInstanceState)
        when (presenter?.getLoginStatus()) {
            false -> {
                (parentFragment as AbsFragment<*, *, *, *>).start(LoginRegisterFragment.newInstance())
            }
        }
    }
}