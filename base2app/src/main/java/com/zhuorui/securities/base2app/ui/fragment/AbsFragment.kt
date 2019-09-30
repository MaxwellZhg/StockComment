package com.zhuorui.securities.base2app.ui.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import androidx.lifecycle.ViewModel
import com.zhuorui.securities.base2app.util.StatusBarUtil
import me.yokeyword.fragmentation.SupportFragment

/**
 *    author : Pengxianglin
 *    e-mail : peng_xianglin@163.com
 *    date   : 2019-05-20 14:13
 *    desc   : 定义基础Fragment
 */
abstract class AbsFragment<D : ViewDataBinding, VM : ViewModel, V : AbsView, P : AbsPresenter<V, VM>> :
    SupportFragment(), AbsView {

    protected var TAG: String? = null

    /**
     * 绑定数据
     */
    private var dataBinding: D? = null
    /**
     * 控制逻辑
     */
    protected var presenter: P? = null
    /**
     * 是否DestroyView()被调用
     *
     * @return isDestroyView
     */
    protected var isDestroyView: Boolean = false
        private set
    /**
     * Fragment的ContentView布局
     *
     * @return layout
     */
    protected abstract val layout: Int
    /**
     * viewModel的id
     */
    protected abstract val viewModelId: Int
    /***
     * 创建presenter
     */
    protected abstract val createPresenter: P
    /***
     * 创建viewModel
     */
    protected abstract val createViewModel: VM?
    /**
     * 返回View
     */
    protected abstract val getView: V

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        TAG = this.javaClass.simpleName
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        isDestroyView = false
        dataBinding = DataBindingUtil.inflate(inflater, layout, container, false)
        if (rootViewFitsSystemWindowsPadding()) {
            dataBinding?.root?.setPadding(0, getRootViewFitsSystemWindowsPadding(), 0, 0)
        }
        val viewModel = createViewModel
        presenter = createPresenter
        context?.let { presenter?.bindContext(it) }
        presenter?.bindView(getView)
        presenter?.bindViewModel(viewModel)
        //关联ViewModel
        dataBinding?.setVariable(viewModelId, viewModel)
        // 让xml内绑定的LiveData和Observer建立连接，让LiveData能感知Fragment的生命周期
        dataBinding?.lifecycleOwner = this
        return dataBinding?.root
    }

    /**
     * 视图是否减去状态栏的高度
     */
    open fun rootViewFitsSystemWindowsPadding(): Boolean {
        return false
    }

    /**
     * 设置视图减去状态栏的高度
     */
    open fun getRootViewFitsSystemWindowsPadding(): Int {
        return StatusBarUtil.getStatusBarHeight(context)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        presenter?.init()
    }

    override fun isDestroy(): Boolean {
        return isDestroyView
    }

    override fun onDestroyView() {
        isDestroyView = true
        dataBinding?.unbind()
        dataBinding = null
        presenter?.destroy()
        presenter = null
        super.onDestroyView()
    }
}