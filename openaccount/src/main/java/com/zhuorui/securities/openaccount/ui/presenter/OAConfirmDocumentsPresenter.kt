package com.zhuorui.securities.openaccount.ui.presenter

import android.text.TextUtils
import com.zhuorui.commonwidget.dialog.DatePickerDialog
import com.zhuorui.securities.base2app.ui.fragment.AbsPresenter
import com.zhuorui.securities.base2app.util.JsonUtil
import com.zhuorui.securities.base2app.util.ResUtil
import com.zhuorui.securities.openaccount.R
import com.zhuorui.securities.openaccount.model.CardOcrData
import com.zhuorui.securities.openaccount.ui.view.OAConfirmDocumentsView
import com.zhuorui.securities.openaccount.ui.viewmodel.OAConfirmDocumentsViewModel
import com.zhuorui.securities.pickerview.option.OnOptionSelectedListener
import java.text.SimpleDateFormat
import java.util.*

/**
 *    author : liuwei
 *    e-mail : vsanliu@foxmail.com
 *    date   : 2019-08-20 14:19
 *    desc   :
 */
class OAConfirmDocumentsPresenter : AbsPresenter<OAConfirmDocumentsView, OAConfirmDocumentsViewModel>() {

    var BIRTHDAY_DATE_FORMAT: String = "yyyy年MM月dd日"
    var mData: CardOcrData? = null
    var genderPickerData: MutableList<String>? = null
    var genderCodeData: MutableList<String>? = null
    var idCardValidData: MutableList<String>? = null
    var idCardValidNumDate: MutableList<Int>? = null
    var endValidPickerData: MutableList<String>? = null
    var mCardValidYear: Int? = null


    override fun init() {
        super.init()
        genderPickerData = ResUtil.getStringArray(R.array.gender)?.asList()?.toMutableList()
        genderCodeData = ResUtil.getStringArray(R.array.gender_code)?.asList()?.toMutableList()
        idCardValidData = ResUtil.getStringArray(R.array.id_card_valid)?.asList()?.toMutableList()
        idCardValidNumDate = ResUtil.getIntArray(R.array.id_card_valid_num)?.asList()?.toMutableList()
        view?.init()
    }

    fun setIdCardData(jsonData: String?) {
        if (TextUtils.isEmpty(jsonData)) return
        var data: CardOcrData = JsonUtil.fromJson(jsonData.toString(), CardOcrData::class.java)
        mData = data
        viewModel?.cardName = mData?.cardName
        view?.setName(data.cardName)
        var genderPos: Int? = genderCodeData?.indexOf(data.cardSex)
        var gender = if (genderPos == null) "" else genderPickerData?.get(genderPos)
        view?.setGender(gender)
        view?.setBirthday(SimpleDateFormat(BIRTHDAY_DATE_FORMAT).format(SimpleDateFormat("yyyy-MM-dd").parse(data.cardBirth)))
        view?.setCardNo(data.cardNo)
        var sDate = SimpleDateFormat("yyyy-MM-dd").parse(data.cardValidStartDate)
        view?.setCardValidStartDate(SimpleDateFormat(BIRTHDAY_DATE_FORMAT).format(sDate))
        initCardValidEndData(sDate.time)
        mCardValidYear = data.cardValidYear;
        view?.setCardValidEndDate(idCardValidNumDate?.indexOf(mCardValidYear)?.let { endValidPickerData?.get(it) })
        view?.setCardAddress(data.cardAddress)

    }

    /**
     * 初始化结束日期数据
     *
     */
    fun initCardValidEndData(timeInMillis: Long) {
        if (idCardValidNumDate == null) return
        var list: MutableList<Int> = idCardValidNumDate as MutableList<Int>
        var tms = Calendar.getInstance()
        tms.timeInMillis = timeInMillis
        var y: Int = tms.get(Calendar.YEAR)
        var m: Int = tms.get(Calendar.MONTH) + 1
        var d: Int = tms.get(Calendar.DAY_OF_MONTH)
        endValidPickerData = mutableListOf()
        for ((index, e) in list.withIndex()) {
            if (e == -1) {
                endValidPickerData?.add(idCardValidData?.get(index).toString())
            } else {
                var txt = "%d年%02d月%02d日（%s）"
                endValidPickerData?.add(String.format(txt, y + e, m, d, idCardValidData?.get(index)))
            }
        }
    }


    /**
     * 生日选择监听
     *
     */
    fun getBirthdayPickerListener(): DatePickerDialog.OnDateSelectedListener {
        return object : DatePickerDialog.OnDateSelectedListener {
            override fun onDateSelected(date: String) {
                view?.setBirthday(date)
            }
        }
    }

    /**
     * 开始日期选择监听
     *
     */
    fun getValidStartDatePickerListener(): DatePickerDialog.OnDateSelectedListener {
        return object : DatePickerDialog.OnDateSelectedListener {
            override fun onDateSelected(date: String) {
                view?.setCardValidStartDate(date)
                initCardValidEndData(SimpleDateFormat(BIRTHDAY_DATE_FORMAT).parse(date).time)
                view?.setCardValidEndDate(idCardValidNumDate?.indexOf(mCardValidYear)?.let { endValidPickerData?.get(it) })
            }

        }
    }

    /**
     * 结束日期选择监听
     *
     */
    fun getValidEndDatePickerListener(): OnOptionSelectedListener<String> {
        return object : OnOptionSelectedListener<String> {
            override fun onOptionSelected(data: MutableList<String>?) {
                var date = data?.get(0)
                view?.setCardValidEndDate(data?.get(0))
                mCardValidYear = endValidPickerData?.indexOf(date)?.let { idCardValidNumDate?.get(it) }
            }

        }
    }

    /**
     * 性别选择监听
     *
     */
    fun getGenderPickerListener(): OnOptionSelectedListener<String> {
        return object : OnOptionSelectedListener<String> {
            override fun onOptionSelected(data: MutableList<String>?) {
                var gender = data?.get(0)
                view?.setGender(gender)
            }
        }
    }
}