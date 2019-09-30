package com.zhuorui.securities.personal.ui.presenter

import com.zhuorui.securities.base2app.ui.fragment.AbsPresenter
import com.zhuorui.securities.personal.config.LocalAccountConfig
import com.zhuorui.securities.personal.ui.view.MyTabVierw
import com.zhuorui.securities.personal.ui.viewmodel.MyTabVierwModel

/**
 *    author : PengXianglin
 *    e-mail : peng_xianglin@163.com
 *    date   : 2019/8/27 14:13
 *    desc   :
 */
class MyTabPresenter : AbsPresenter<MyTabVierw, MyTabVierwModel>() {

    fun getLoginStatus(): Boolean {
        return LocalAccountConfig.read().isLogin()
    }
}