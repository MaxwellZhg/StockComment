package com.zhuorui.securities.openaccount.ui.view

import com.zhuorui.securities.base2app.ui.fragment.AbsView

/**
 * Created by Maxwell.
 * E-mail: maxwell_smith@163.com
 * Date: 2019/8/27
 * Desc:
 */
interface OAVedioRecordView : AbsView {

    fun showUploading()

    fun hideUploading()

    fun uploadComplete()
}