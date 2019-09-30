package com.zhuorui.securities.openaccount.ui

import android.os.Bundle
import android.view.View
import androidx.lifecycle.ViewModelProviders
import com.zhuorui.commonwidget.dialog.DatePickerDialog
import com.zhuorui.commonwidget.dialog.OptionsPickerDialog
import com.zhuorui.securities.base2app.ui.fragment.AbsFragment
import com.zhuorui.securities.base2app.ui.fragment.AbsSwipeBackFragment
import com.zhuorui.securities.openaccount.BR
import com.zhuorui.securities.openaccount.R
import com.zhuorui.securities.openaccount.databinding.FragmentOaConfirmDocumentsBinding
import com.zhuorui.securities.openaccount.ui.presenter.OAConfirmDocumentsPresenter
import com.zhuorui.securities.openaccount.ui.view.OAConfirmDocumentsView
import com.zhuorui.securities.openaccount.ui.viewmodel.OAConfirmDocumentsViewModel
import kotlinx.android.synthetic.main.fragment_oa_confirm_documents.*

/**
 *    author : liuwei
 *    e-mail : vsanliu@foxmail.com
 *    date   : 2019-08-23 14:09
 *    desc   : 确认身份信息
 */
class OAConfirmDocumentsFragment :
    AbsSwipeBackFragment<FragmentOaConfirmDocumentsBinding, OAConfirmDocumentsViewModel, OAConfirmDocumentsView, OAConfirmDocumentsPresenter>(),
    OAConfirmDocumentsView, View.OnClickListener {

    var mDatePicker: DatePickerDialog? = null
    var mOptionsPicker: OptionsPickerDialog<String>? = null


    companion object {
        fun newInstance(data: String): OAConfirmDocumentsFragment {
            val fragment = OAConfirmDocumentsFragment()
            val bundle = Bundle()
            bundle.putString("data", data)
            fragment.arguments = bundle
            return fragment
        }
    }

    override val layout: Int
        get() = R.layout.fragment_oa_confirm_documents

    override val viewModelId: Int
        get() = BR.viewModel

    override val createPresenter: OAConfirmDocumentsPresenter
        get() = OAConfirmDocumentsPresenter()

    override val createViewModel: OAConfirmDocumentsViewModel?
        get() = ViewModelProviders.of(this).get(OAConfirmDocumentsViewModel::class.java)

    override val getView: OAConfirmDocumentsView
        get() = this

    override fun setBirthday(date: String?) {
        et_birthday.text = date
    }

    override fun setName(name: String?) {
        et_cn_name.setText(name)
    }

    override fun setGender(gender: String?) {
        et_gender.text = gender
    }

    override fun setCardNo(cardNo: String?) {
        et_idcard_no.setText(cardNo)
    }

    override fun setCardValidStartDate(date: String?) {
        et_s_expiry.text = date
    }

    override fun setCardValidEndDate(date: String?) {
        et_e_expiry.text = date
    }

    override fun setCardAddress(address: String?) {
        et_address.setText(address)
    }


    override fun init() {
        btn_per.setOnClickListener(this)
        btn_next.setOnClickListener(this)
        et_gender.setOnClickListener(this)
        et_birthday.setOnClickListener(this)
        et_s_expiry.setOnClickListener(this)
        et_e_expiry.setOnClickListener(this)
        mDatePicker = context?.let { DatePickerDialog(it) }
        mOptionsPicker = context?.let { OptionsPickerDialog(it) }
    }

    override fun onLazyInitView(savedInstanceState: Bundle?) {
        super.onLazyInitView(savedInstanceState)
        var jsonData: String? = arguments?.getString("data")
        presenter?.setIdCardData(jsonData)
    }

    override fun onClick(p0: View?) {
        when (p0) {
            btn_per -> {
                pop()
            }
            btn_next -> {
//                start(OATakeBankCradPhotoFragment.newInstance())
                start(OAVedioRecordFragment.newInstance())
//                start(OAPersonalInformationFragment.newInstance())
            }
            et_birthday -> {
                mDatePicker?.setOnDateSelectedListener(presenter?.getBirthdayPickerListener())
                mDatePicker?.setCurrentData(et_birthday.text, presenter?.BIRTHDAY_DATE_FORMAT)
                mDatePicker?.show()
            }
            et_gender -> {
                mOptionsPicker?.setData(presenter?.genderPickerData)
                mOptionsPicker?.setOnOptionSelectedListener(presenter?.getGenderPickerListener())
                mOptionsPicker?.setCurrentData(et_gender.text)
                mOptionsPicker?.show()
            }
            et_s_expiry -> {
                mDatePicker?.setOnDateSelectedListener(presenter?.getValidStartDatePickerListener())
                mDatePicker?.setCurrentData(et_s_expiry.text, presenter?.BIRTHDAY_DATE_FORMAT)
                mDatePicker?.show()
            }
            et_e_expiry -> {
                mOptionsPicker?.setData(presenter?.endValidPickerData)
                mOptionsPicker?.setOnOptionSelectedListener(presenter?.getValidEndDatePickerListener())
                mOptionsPicker?.setCurrentData(et_e_expiry.text)
                mOptionsPicker?.show()
            }
        }
    }


}