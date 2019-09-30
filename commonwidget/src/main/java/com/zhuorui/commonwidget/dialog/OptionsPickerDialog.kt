package com.zhuorui.commonwidget.dialog

import android.content.Context
import android.view.View
import android.widget.TextView
import butterknife.BindView
import com.zhuorui.commonwidget.R
import com.zhuorui.commonwidget.R2
import com.zhuorui.securities.base2app.dialog.BaseBottomSheetsDialog
import com.zhuorui.securities.pickerview.IWheelData
import com.zhuorui.securities.pickerview.option.OnOptionSelectedListener
import com.zhuorui.securities.pickerview.option.OptionsPicker


/**
 *    author : liuwei
 *    e-mail : vsanliu@foxmail.com
 *    date   : 2019-08-21 18:33
 *    desc   :
 */
class OptionsPickerDialog<T>(context: Context) : BaseBottomSheetsDialog(context),
    View.OnClickListener {

    override fun onClick(p0: View?) {
        when (p0?.id) {
            R.id.confirm -> {
                hide()
                pickver.confirm()
            }
            R.id.cancel -> {
                hide()
            }
        }
    }


    @BindView(R2.id.picker)
    lateinit var pickver: OptionsPicker<T>
    @BindView(R2.id.confirm)
    lateinit var confirm: TextView
    @BindView(R2.id.cancel)
    lateinit var cancel: TextView
    @BindView(R2.id.title)
    lateinit var title: TextView

    override val layout: Int
        get() = R.layout.dialog_options_picker

    init {
        dialog?.setCancelable(false)
        dialog?.setCanceledOnTouchOutside(false)
        confirm.setOnClickListener(this)
        cancel.setOnClickListener(this)
        title.setOnClickListener(this)
    }

    fun setData(vararg datas: MutableList<T>?) {
        pickver.setData(*datas)

    }

    fun setCurrentData(vararg datas: T){
        pickver.setCurrentData(*datas)
    }

    fun setOnOptionSelectedListener(l: OnOptionSelectedListener<T>?) {
        pickver.setOnOptionSelectedListener(l)
    }


}

