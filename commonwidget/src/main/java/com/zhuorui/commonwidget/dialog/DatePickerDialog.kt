package com.zhuorui.commonwidget.dialog

import android.content.Context
import android.text.TextUtils
import android.view.View
import android.widget.TextView
import butterknife.BindView
import com.zhuorui.commonwidget.R
import com.zhuorui.commonwidget.R2
import com.zhuorui.securities.base2app.dialog.BaseBottomSheetsDialog
import com.zhuorui.securities.pickerview.IWheelData
import com.zhuorui.securities.pickerview.date.DatePicker
import com.zhuorui.securities.pickerview.option.OnOptionSelectedListener
import com.zhuorui.securities.pickerview.option.OptionsPicker
import java.text.DateFormat
import java.text.SimpleDateFormat
import java.util.*
import kotlin.text.Typography.times


/**
 *    author : liuwei
 *    e-mail : vsanliu@foxmail.com
 *    date   : 2019-08-21 18:33
 *    desc   :
 */
class DatePickerDialog(context: Context) : BaseBottomSheetsDialog(context),
    View.OnClickListener {

    var format: String = "yyyy-MM-dd"
    var listener: OnDateSelectedListener? = null

    override fun onClick(p0: View?) {
        when (p0?.id) {
            R.id.confirm -> {
                hide()
                listener?.onDateSelected(pickver.getDate(SimpleDateFormat(format)))
            }
            R.id.cancel -> {
                hide()
            }
        }
    }


    @BindView(R2.id.picker)
    lateinit var pickver: DatePicker
    @BindView(R2.id.confirm)
    lateinit var confirm: TextView
    @BindView(R2.id.cancel)
    lateinit var cancel: TextView
    @BindView(R2.id.title)
    lateinit var title: TextView

    override val layout: Int
        get() = R.layout.dialog_date_picker

    init {
        dialog?.setCancelable(false)
        dialog?.setCanceledOnTouchOutside(false)
        confirm.setOnClickListener(this)
        cancel.setOnClickListener(this)
        title.setOnClickListener(this)
    }

    fun setCurrentData(timeInMillis: Long) {
        var tms = Calendar.getInstance()
        tms.timeInMillis = timeInMillis
        var y: Int = tms.get(Calendar.YEAR)
        var m: Int = tms.get(Calendar.MONTH) + 1
        var d: Int = tms.get(Calendar.DAY_OF_MONTH)
        pickver.setDate(y, m, d)
    }

    fun setCurrentData(timeStr: String, format: String?) {
        if (TextUtils.isEmpty(timeStr)) return
        this.format = format.toString()
        setCurrentData(SimpleDateFormat(format).parse(timeStr).time)
    }

    fun setOnDateSelectedListener(l: OnDateSelectedListener?) {
        listener = l
    }

    interface OnDateSelectedListener {

        fun onDateSelected(date: String)
    }


}

