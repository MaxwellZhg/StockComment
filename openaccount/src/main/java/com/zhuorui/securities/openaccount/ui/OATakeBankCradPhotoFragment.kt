package com.zhuorui.securities.openaccount.ui

import android.os.Bundle
import android.text.InputFilter
import android.text.InputType
import android.view.View
import androidx.lifecycle.ViewModelProviders
import com.zhuorui.commonwidget.dialog.GetPicturesModeDialog
import com.zhuorui.commonwidget.dialog.OptionsPickerDialog
import com.zhuorui.securities.base2app.ui.fragment.AbsSwipeBackFragment
import com.zhuorui.securities.openaccount.BR
import com.zhuorui.securities.openaccount.R
import com.zhuorui.securities.openaccount.custom.TakePhotoBankCardTipsDialog
import com.zhuorui.securities.openaccount.databinding.FragmentOaTakeBankCardPhotoBinding
import com.zhuorui.securities.openaccount.ui.presenter.OATakeBankCradPhotoPresenter
import com.zhuorui.securities.openaccount.ui.view.OATakeBankCradPhotoView
import com.zhuorui.securities.openaccount.ui.viewmodel.OATakeBankCradPhotoViewModel
import com.zhuorui.securities.openaccount.widget.BankCardTextWatcher
import com.zhuorui.securities.pickerview.option.OnOptionSelectedListener
import kotlinx.android.synthetic.main.fragment_oa_take_bank_card_photo.*

/**
 *    author : PengXianglin
 *    e-mail : peng_xianglin@163.com
 *    date   : 2019/8/26 17:08
 *    desc   : 拍摄银行卡页面
 */
class OATakeBankCradPhotoFragment :
    AbsSwipeBackFragment<FragmentOaTakeBankCardPhotoBinding, OATakeBankCradPhotoViewModel, OATakeBankCradPhotoView, OATakeBankCradPhotoPresenter>(),
    OATakeBankCradPhotoView, View.OnClickListener, OnOptionSelectedListener<String> {

    companion object {
        fun newInstance(): OATakeBankCradPhotoFragment {
            return OATakeBankCradPhotoFragment()
        }
    }

    override val layout: Int
        get() = R.layout.fragment_oa_take_bank_card_photo

    override val viewModelId: Int
        get() = BR.viewModel

    override val createPresenter: OATakeBankCradPhotoPresenter
        get() = OATakeBankCradPhotoPresenter()

    override val createViewModel: OATakeBankCradPhotoViewModel?
        get() = ViewModelProviders.of(this).get(OATakeBankCradPhotoViewModel::class.java)

    override val getView: OATakeBankCradPhotoView
        get() = this

    override fun onLazyInitView(savedInstanceState: Bundle?) {
        super.onLazyInitView(savedInstanceState)
        tv_take_sample.setOnClickListener(this)
        tv_bank.vEt.setOnClickListener(this)
        tv_card_id?.vRightIcon?.setOnClickListener(this)
        btn_next.setOnClickListener(this)

        tv_card_id.vEt.inputType = InputType.TYPE_CLASS_NUMBER or InputType.TYPE_NUMBER_VARIATION_NORMAL
        tv_card_id.vEt.filters = arrayOf(InputFilter.LengthFilter(26))
        tv_card_id.vEt.maxLines = 1
        BankCardTextWatcher.bind(tv_card_id.vEt)
    }

    override fun onClick(p0: View?) {
        when (p0) {
            btn_next -> {
                // 跳转到下一步
                start(OARiskDisclosureFragment.newInstance())
            }
            tv_bank.vEt -> {
                // 选择银行
                val dialog = context?.let { OptionsPickerDialog<String>(it) }
                val regionData: MutableList<String> =
                    mutableListOf("中国工商银行", "中国农业银行", "中国银行", "中国建设银行", "中国邮政储蓄银行", "交通银行", "招商银行")
                dialog?.setData(regionData)
                dialog?.setOnOptionSelectedListener(this)
                dialog?.show()
            }
            tv_card_id.vRightIcon -> {
                // 选取银行卡照片方式
                val dialog = context?.let { GetPicturesModeDialog(it) }
                dialog?.show()
            }
            else -> {
                // 拍照示例
                context?.let { TakePhotoBankCardTipsDialog(it).show() }
            }
        }
    }

    override fun onOptionSelected(data: MutableList<String>?) {
        tv_bank.vEt.text = data?.get(0)
    }
}