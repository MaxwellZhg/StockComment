package com.zhuorui.securities.openaccount.ui.presenter

import android.content.res.Resources
import android.text.SpannableString
import android.text.Spanned
import android.text.TextPaint
import android.text.style.ClickableSpan
import android.text.style.ForegroundColorSpan
import android.view.View
import androidx.lifecycle.LifecycleOwner
import com.zhuorui.securities.base2app.ui.fragment.AbsNetPresenter
import com.zhuorui.securities.base2app.util.ResUtil
import com.zhuorui.securities.base2app.util.ToastUtil
import com.zhuorui.securities.openaccount.R
import com.zhuorui.securities.openaccount.model.OADataTips
import com.zhuorui.securities.openaccount.ui.view.OADataTipsView
import com.zhuorui.securities.openaccount.ui.viewmodel.OADataTipsViewModel

/**
 *    author : liuwei
 *    e-mail : vsanliu@foxmail.com
 *    date   : 2019-08-20 14:19
 *    desc   :
 */
class OADataTipsPresenter : AbsNetPresenter<OADataTipsView, OADataTipsViewModel>() {
    override fun init() {
        super.init()
        view?.init();
    }

    fun setLifecycleOwner(lifecycleOwner: LifecycleOwner) {
        // 监听datas的变化
        lifecycleOwner.let {
            viewModel?.datas?.observe(it,
                androidx.lifecycle.Observer<List<OADataTips>> { t -> view?.notifyDataSetChanged(t) })
        }
    }

    fun getDataTips() {
        val datas = mutableListOf<OADataTips>()
        datas.add(OADataTips(1))
        datas.add(OADataTips(2))
        datas.add(OADataTips(3))
        viewModel?.datas?.value = datas
    }

    fun getAgreementText(): SpannableString {
        var afr = ResUtil.getString(R.string.str_account_opening_agreement)
        var text = ResUtil.getString(R.string.str_confirm_Read_agreement)
        var spannableString = SpannableString(text + afr)
        spannableString.setSpan(
            AgreementClickableSpan(),
            text!!.length,
            spannableString?.length,
            Spanned.SPAN_INCLUSIVE_EXCLUSIVE
        )
        var color = ResUtil.getColor(R.color.app_bule)
        spannableString.setSpan(
            ForegroundColorSpan(color!!),
            text.length,
            spannableString.length,
            Spanned.SPAN_INCLUSIVE_EXCLUSIVE
        )
        return spannableString
    }

    class AgreementClickableSpan() : ClickableSpan() {
        override fun onClick(p0: View) {
            ToastUtil.instance.toast("点击开户协议")
        }

        override fun updateDrawState(ds: TextPaint) {
            super.updateDrawState(ds)
            ds.color = ds.linkColor
            ds.isUnderlineText = false
        }

    }
}