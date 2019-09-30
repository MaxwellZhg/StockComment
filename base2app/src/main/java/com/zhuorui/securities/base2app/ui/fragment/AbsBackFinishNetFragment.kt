package com.zhuorui.securities.base2app.ui.fragment

import androidx.databinding.ViewDataBinding
import androidx.lifecycle.ViewModel

/**
 *    author : Pengxianglin
 *    e-mail : peng_xianglin@163.com
 *    date   : 2019-05-20 14:13
 *    desc   : 双击返回键退出Activity并带有RxBus的Fragment
 *             适用场景：一级页面（如主页中的Tab）
 *             在此基础上增加了网络通用处理
 */
abstract class AbsBackFinishNetFragment<D : ViewDataBinding, VM : ViewModel, V : AbsView, P : AbsNetPresenter<V, VM>> :
    AbsBackFinishEventFragment<D, VM, V, P>()