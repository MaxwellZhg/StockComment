package com.zhuorui.securities.openaccount.ui

import android.os.Bundle
import android.view.View
import androidx.lifecycle.ViewModelProviders
import com.zhuorui.securities.base2app.ui.activity.AbsActivity
import com.zhuorui.securities.base2app.ui.fragment.AbsFragment
import com.zhuorui.securities.base2app.ui.fragment.AbsSwipeBackFragment
import com.zhuorui.securities.openaccount.BR
import com.zhuorui.securities.openaccount.R
import com.zhuorui.securities.openaccount.databinding.FragmentOaWaitAuditBinding
import com.zhuorui.securities.openaccount.ui.presenter.OAWaitAuditPresenter
import com.zhuorui.securities.openaccount.ui.view.OAWaitAuditView
import com.zhuorui.securities.openaccount.ui.viewmodel.OAWaitAuditViewModel
import kotlinx.android.synthetic.main.fragment_oa_wait_audit.*
import me.yokeyword.fragmentation.ISupportFragment

/**
 *    author : PengXianglin
 *    e-mail : peng_xianglin@163.com
 *    date   : 2019/8/28 17:23
 *    desc   : 等待审核页面
 */
class OAWaitAuditFragment :
    AbsSwipeBackFragment<FragmentOaWaitAuditBinding, OAWaitAuditViewModel, OAWaitAuditView, OAWaitAuditPresenter>(),
    OAWaitAuditView, View.OnClickListener {

    companion object {
        fun newInstance(): OAWaitAuditFragment {
            return OAWaitAuditFragment()
        }
    }

    override val layout: Int
        get() = R.layout.fragment_oa_wait_audit

    override val viewModelId: Int
        get() = BR.viewModel

    override val createPresenter: OAWaitAuditPresenter
        get() = OAWaitAuditPresenter()

    override val createViewModel: OAWaitAuditViewModel?
        get() = ViewModelProviders.of(this).get(OAWaitAuditViewModel::class.java)

    override val getView: OAWaitAuditView
        get() = this

    override fun onLazyInitView(savedInstanceState: Bundle?) {
        super.onLazyInitView(savedInstanceState)
        return_to_main.setOnClickListener(this)
    }

    override fun onClick(p0: View?) {
        // 返回首页
        val homeFragment = (activity as AbsActivity).supportFragmentManager.fragments[0] as AbsFragment<*, *, *, *>
        popTo(homeFragment::class.java, false)
    }
}