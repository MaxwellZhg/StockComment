package com.zhuorui.securities.infomation.ui

import androidx.lifecycle.ViewModelProviders
import com.zhuorui.securities.base2app.ui.fragment.AbsBackFinishFragment
import com.zhuorui.securities.infomation.BR
import com.zhuorui.securities.infomation.R
import com.zhuorui.securities.infomation.databinding.FragmentInfomationTabBinding
import com.zhuorui.securities.infomation.ui.presenter.InfomationTabPresenter
import com.zhuorui.securities.infomation.ui.view.InfomationTabView
import com.zhuorui.securities.infomation.ui.viewmodel.InfomationTabViewModel

/**
 *    author : PengXianglin
 *    e-mail : peng_xianglin@163.com
 *    date   : 2019/8/27 14:23
 *    desc   : 主页资讯tab页面
 */
class InfomationTabFragment :
    AbsBackFinishFragment<FragmentInfomationTabBinding, InfomationTabViewModel, InfomationTabView, InfomationTabPresenter>(),
    InfomationTabView {

    companion object {
        fun newInstance(): InfomationTabFragment {
            return InfomationTabFragment()
        }
    }

    override val layout: Int
        get() = R.layout.fragment_infomation_tab

    override val viewModelId: Int
        get() = BR.viewModel

    override val createPresenter: InfomationTabPresenter
        get() = InfomationTabPresenter()

    override val createViewModel: InfomationTabViewModel?
        get() = ViewModelProviders.of(this).get(InfomationTabViewModel::class.java)

    override val getView: InfomationTabView
        get() = this

}