package com.zhuorui.securities.base2app.ui.fragment

import androidx.databinding.ViewDataBinding
import androidx.lifecycle.ViewModel

/**
 *    author : Pengxianglin
 *    e-mail : peng_xianglin@163.com
 *    date   : 2019-05-20 14:13
 *    desc   : 定义基础侧滑返回并带有RxBus的Fragment
 *             适用场景：二级页面（如查看详情）
 *             在此基础上增加了网络通用处理
 */
abstract class AbsSwipeBackNetFragment<D : ViewDataBinding, VM : ViewModel, V : AbsView, P : AbsNetPresenter<V, VM>> :
    AbsSwipeBackEventFragment<D, VM, V, P>()