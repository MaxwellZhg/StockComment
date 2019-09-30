package com.zhuorui.securities.base2app.ui.fragment

import android.content.Context
import androidx.annotation.StringRes
import androidx.lifecycle.ViewModel
import com.zhuorui.securities.base2app.util.ToastUtil

/**
 *    author : PengXianglin
 *    e-mail : peng_xianglin@163.com
 *    date   : 2019/8/16 14:02
 *    desc   : 抽象的Presenter
 */
abstract class AbsPresenter<V : AbsView, VM : ViewModel> {

    protected var TAG: String? = null

    protected var context: Context? = null

    protected var view: V? = null

    protected var viewModel: VM? = null

    init {
        TAG = this.javaClass.simpleName
    }

    /**
     * 初始化
     */
    open fun init() {}

    /**
     * 销毁
     */
    open fun destroy() {}

    fun bindContext(context: Context) {
        this.context = context
    }

    fun bindView(view: V) {
        this.view = view
    }

    fun bindViewModel(viewModel: VM?) {
        this.viewModel = viewModel
    }

    fun toast(@StringRes res: Int) {
        ToastUtil.instance.toast(res)
    }

    fun toast(str: String?) {
        str?.let { ToastUtil.instance.toast(it) }
    }
}