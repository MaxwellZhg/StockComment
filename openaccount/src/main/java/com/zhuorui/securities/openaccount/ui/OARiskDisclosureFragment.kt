package com.zhuorui.securities.openaccount.ui

import android.os.Bundle
import android.view.View
import androidx.lifecycle.ViewModelProviders
import com.zhuorui.securities.base2app.ui.fragment.AbsSwipeBackFragment
import com.zhuorui.securities.openaccount.BR
import com.zhuorui.securities.openaccount.R
import com.zhuorui.securities.openaccount.databinding.FragmentOaTakeBankCardPhotoBinding
import com.zhuorui.securities.openaccount.ui.presenter.OARiskDisclosurePresenter
import com.zhuorui.securities.openaccount.ui.view.OARiskDisclosureView
import com.zhuorui.securities.openaccount.ui.viewmodel.OARiskDisclosureViewModel
import kotlinx.android.synthetic.main.fragment_oa_risk_disclosure.*

/**
 *    author : PengXianglin
 *    e-mail : peng_xianglin@163.com
 *    date   : 2019/8/28 9:57
 *    desc   : 风险披露页面
 */
class OARiskDisclosureFragment :
    AbsSwipeBackFragment<FragmentOaTakeBankCardPhotoBinding, OARiskDisclosureViewModel, OARiskDisclosureView, OARiskDisclosurePresenter>(),
    OARiskDisclosureView, View.OnClickListener {

    companion object {
        fun newInstance(): OARiskDisclosureFragment {
            return OARiskDisclosureFragment()
        }
    }

    override val layout: Int
        get() = R.layout.fragment_oa_risk_disclosure

    override val viewModelId: Int
        get() = BR.viewModel

    override val createPresenter: OARiskDisclosurePresenter
        get() = OARiskDisclosurePresenter()

    override val createViewModel: OARiskDisclosureViewModel?
        get() = ViewModelProviders.of(this).get(OARiskDisclosureViewModel::class.java)

    override val getView: OARiskDisclosureView
        get() = this

    override fun onLazyInitView(savedInstanceState: Bundle?) {
        super.onLazyInitView(savedInstanceState)
        btn_next.setOnClickListener(this)
        btn_per.setOnClickListener(this)
        btn_speech_risk.setOnClickListener(this)
    }

    override fun onClick(p0: View?) {
        when (p0) {
            btn_next -> {
                // 跳转到下一步
                start(OASignatureFragment.newInstance())
            }
            btn_per -> {
                // 跳转到上一步
                pop()
            }
            btn_speech_risk -> {
                presenter?.speechRisk()
            }
        }
    }
}