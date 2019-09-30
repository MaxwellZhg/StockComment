package com.zhuorui.securities.openaccount.ui

import android.os.Bundle
import android.text.method.LinkMovementMethod
import android.view.View
import android.widget.CompoundButton
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.SimpleItemAnimator
import com.zhuorui.securities.base2app.ui.fragment.AbsSwipeBackNetFragment
import com.zhuorui.securities.openaccount.BR
import com.zhuorui.securities.openaccount.R
import com.zhuorui.securities.openaccount.databinding.FragmentOaDataTipsBinding
import com.zhuorui.securities.openaccount.model.OADataTips
import com.zhuorui.securities.openaccount.ui.presenter.OADataTipsPresenter
import com.zhuorui.securities.openaccount.ui.view.OADataTipsView
import com.zhuorui.securities.openaccount.ui.viewmodel.OADataTipsViewModel
import kotlinx.android.synthetic.main.fragment_oa_data_tips.*


/**
 *    author : liuwei
 *    e-mail : vsanliu@foxmail.com
 *    date   : 2019-08-20 14:01
 *    desc   : 提示用户准备开户资料
 */

class OADataTipsFragment :
    AbsSwipeBackNetFragment<FragmentOaDataTipsBinding, OADataTipsViewModel, OADataTipsView, OADataTipsPresenter>(),
    OADataTipsView,
    CompoundButton.OnCheckedChangeListener, View.OnClickListener {


    var mAdapter: OADataTipsAdapter? = null

    companion object {
        fun newInstance(): OADataTipsFragment {
            return OADataTipsFragment()
        }
    }

    override val layout: Int
        get() = R.layout.fragment_oa_data_tips

    override val viewModelId: Int
        get() = BR.viewModel

    override val createPresenter: OADataTipsPresenter
        get() = OADataTipsPresenter()

    override val createViewModel: OADataTipsViewModel?
        get() = ViewModelProviders.of(this).get(OADataTipsViewModel::class.java)

    override val getView: OADataTipsView
        get() = this

    override fun onCheckedChanged(p0: CompoundButton?, p1: Boolean) {
        open_btn?.isEnabled = p1
    }

    override fun notifyDataSetChanged(list: List<OADataTips>?) {
        mAdapter?.items = list
    }

    override fun onClick(p0: View?) {
        when(p0?.id){
            R.id.open_btn ->{
                start(OAUploadDocumentsFragment.newInstance())

            }
        }
    }

    override fun init() {
        (rv.itemAnimator as SimpleItemAnimator).supportsChangeAnimations = false
        rv.layoutManager = LinearLayoutManager(context)
        mAdapter = OADataTipsAdapter()
        rv.adapter = mAdapter
        cbox.setOnCheckedChangeListener(this)
        agreement.text = presenter?.getAgreementText()
        agreement.movementMethod = LinkMovementMethod.getInstance()
        open_btn.setOnClickListener(this)
    }

    override fun onLazyInitView(savedInstanceState: Bundle?) {
        super.onLazyInitView(savedInstanceState)
        presenter?.setLifecycleOwner(this)
        presenter?.getDataTips()
    }






}
