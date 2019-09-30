package com.zhuorui.securities.personal.ui.view

import com.zhuorui.securities.base2app.ui.fragment.AbsView

/**
 * Created by Maxwell.
 * E-mail: maxwell_smith@163.com
 * Date: 2019/8/20
 * Desc:
 */
interface LoginRegisterView :AbsView{
    fun init()
   //逻辑扩展接口
    fun gotopsw()
    fun gotomain()
}