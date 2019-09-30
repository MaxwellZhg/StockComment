package com.zhuorui.securities.ui

import com.zhuorui.securities.base2app.ui.fragment.AbsPresenter

/**
 *    author : PengXianglin
 *    e-mail : peng_xianglin@163.com
 *    date   : 2019/8/16 16:07
 *    desc   :
 */
class MainFramgentPresenter : AbsPresenter<MainFragmentView, MainFragmentViewModel>() {

    override fun init() {
        view?.init()
    }

}