package com.zhuorui.securities.openaccount.ui

import android.os.Bundle
import android.view.View
import androidx.lifecycle.ViewModelProviders
import com.zhuorui.commonwidget.dialog.OptionsPickerDialog
import com.zhuorui.securities.base2app.ui.fragment.AbsSwipeBackNetFragment
import com.zhuorui.securities.openaccount.BR
import com.zhuorui.securities.openaccount.R
import com.zhuorui.securities.openaccount.databinding.FragmentOaSelectRegionBinding
import com.zhuorui.securities.openaccount.ui.presenter.OASelectRegionPresenter
import com.zhuorui.securities.openaccount.ui.view.OASeletRegionView
import com.zhuorui.securities.openaccount.ui.viewmodel.OASelectRegonViewModel
import com.zhuorui.securities.pickerview.option.OnOptionSelectedListener
import kotlinx.android.synthetic.main.fragment_oa_select_region.*

/**
 *    author : liuwei
 *    e-mail : vsanliu@foxmail.com
 *    date   : 2019-08-20 14:01
 *    desc   : 选择开户用户地区
 */

class OASelectRegionFragment :
    AbsSwipeBackNetFragment<FragmentOaSelectRegionBinding, OASelectRegonViewModel, OASeletRegionView, OASelectRegionPresenter>(),
    OASeletRegionView, View.OnClickListener, OnOptionSelectedListener<String> {

    var dialog: OptionsPickerDialog<String>? = null
    val regionData: MutableList<String> = mutableListOf("中国澳门", "中国香港", "中国内地", "中国台湾", "海外居民")

    override fun onLazyInitView(savedInstanceState: Bundle?) {
        super.onLazyInitView(savedInstanceState)

        next.setOnClickListener(this)
        region.setOnClickListener(this)
        region.text = regionData[2]
        dialog = activity?.let { OptionsPickerDialog<String>(it) }
        dialog?.setData(regionData)
        dialog?.setOnOptionSelectedListener(this)
    }

    override fun onClick(p0: View?) {
        when (p0?.id) {
            R.id.next -> {
//                start(OADataTipsFragment.newInstance())
                start(OAVedioRecordFragment.newInstance())
            }
            R.id.region -> {
                dialog?.setCurrentData(region.text.toString())
                dialog?.show()
            }
        }
    }

    companion object {
        fun newInstance(): OASelectRegionFragment {
            return OASelectRegionFragment()
        }
    }

    override val layout: Int
        get() = R.layout.fragment_oa_select_region

    override val viewModelId: Int
        get() = BR.viewModel

    override val createPresenter: OASelectRegionPresenter
        get() = OASelectRegionPresenter()

    override val createViewModel: OASelectRegonViewModel?
        get() = ViewModelProviders.of(this).get(OASelectRegonViewModel::class.java)

    override val getView: OASeletRegionView
        get() = this

    override fun onOptionSelected(data: MutableList<String>?) {
        region.text = data?.get(0)
    }

}
