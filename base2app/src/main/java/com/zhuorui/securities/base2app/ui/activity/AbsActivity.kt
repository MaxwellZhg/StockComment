package com.zhuorui.securities.base2app.ui.activity

import android.content.pm.ActivityInfo
import android.graphics.Color
import android.os.Build
import android.os.Bundle
import android.util.Log
import android.view.*
import androidx.annotation.StringRes
import com.zhuorui.securities.base2app.util.AppUtil
import com.zhuorui.securities.base2app.util.QuickClickUtil
import com.zhuorui.securities.base2app.util.StatusBarUtil
import com.zhuorui.securities.base2app.util.ToastUtil
import me.yokeyword.fragmentation.SupportActivity

/**
 * Create by xieyingwu on 2018/4/3.
 * 类描述：抽象的AC基类
 */
abstract class AbsActivity : SupportActivity(), QuickClickUtil.Callback {
    protected var TAG: String? = null
    private var inStopLife: Boolean = false

    /**
     * 获取当前Ac的布局的xml布局content的布局id
     *
     * @return id
     */
    protected abstract val acContentRootViewId: Int

    /**
     * 默认采用全屏展示
     *
     * @return true;展示全屏
     */
    protected open val isFullScreen: Boolean
        get() = true

    /**
     * ContentView的布局
     *
     * @return layout
     */
    protected abstract val layout: Int

    protected val isDestroy: Boolean
        get() = isFinishing || isDestroyed

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        TAG = this.javaClass.simpleName
        if (isFullScreen) beFullScreen()
        setContentView(layout)
        if (needChangeHeight()) {
            try {
                val container = findViewById<View>(acContentRootViewId)
                /*若是8.0设备，则判断导航栏的问题*/
                val parent = container.parent
                if (parent is ViewGroup) {
                    val rootView = parent as View
                    listenerNavigationListener(rootView)
                }
                val lp = container.layoutParams
                lp.height = AppUtil.screenHeight
                Log.e(TAG, "lp.height = " + lp.height)
            } catch (e: Exception) {
                e.printStackTrace()
            }
        }
    }

    override fun onResume() {
        inStopLife = false
        super.onResume()
    }

    override fun onStop() {
        inStopLife = true
        super.onStop()
    }

    private fun listenerNavigationListener(rootView: View?) {
        if (rootView == null) return
        val phoneRealScreenHeight = AppUtil.phoneRealScreenHeight
        val phoneScreenHeight = AppUtil.phoneScreenHeight
        if (phoneRealScreenHeight != phoneScreenHeight) {
            /*若实际屏幕与视图屏幕不对，则表明存在虚拟按键，可进行虚拟按键的显示隐藏监听*/
            rootView.viewTreeObserver.addOnGlobalLayoutListener(object : ViewTreeObserver.OnGlobalLayoutListener {
                var rootViewHeight: Int = 0

                override fun onGlobalLayout() {
                    if (inStopLife) return /*stop状态不更新*/
                    val viewHeight = rootView.height
                    if (rootViewHeight != viewHeight) {
                        rootViewHeight = viewHeight
                        if (rootViewHeight == AppUtil.phoneRealScreenHeight || rootViewHeight > AppUtil.phoneScreenHeight) {
                            /*虚拟按键隐藏了*/
                            navigation(rootViewHeight)
                        } else {
                            /*虚拟按键显示了*/
                            navigation(AppUtil.phoneScreenHeight)
                        }
                    }
                }
            })
        }
    }

    private fun navigation(value: Int) {
        try {
            val container = findViewById<View>(acContentRootViewId)
            val lp = container.layoutParams
            val isPortrait = this.resources.configuration.orientation == ActivityInfo.SCREEN_ORIENTATION_PORTRAIT
            if (isPortrait) {
                lp.height = value
            } else {
                return
                //                lp.width = value;
            }
            Log.e(TAG, "lp.value = $value")
            container.layoutParams = lp
        } catch (e: Exception) {
            e.printStackTrace()
        }

    }

    private fun beFullScreen() {
        //设置状态栏沉浸
        val window = window
        window.requestFeature(Window.FEATURE_NO_TITLE)
        if (checkFullDisplay()) {
            window.addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_NAVIGATION)
        } else {
            window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS or WindowManager.LayoutParams.FLAG_TRANSLUCENT_NAVIGATION)
        }
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {//5.0以上
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                window.decorView.systemUiVisibility = View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
                window.statusBarColor = Color.TRANSPARENT
            } else {
                window.addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS)
                window.decorView.systemUiVisibility = View.SYSTEM_UI_FLAG_LAYOUT_STABLE
                StatusBarUtil.StatusBarBrightnessMode(this)
            }
            window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS)
        } else if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {//4.4
            val localLayoutParams = getWindow().attributes
            localLayoutParams.flags = WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS or localLayoutParams.flags
        }

        if (statusBarLightMode()) {
            StatusBarUtil.StatusBarLightMode(this)
        }
    }

    protected fun checkFullDisplay(): Boolean {
        return false
    }

    /**
     * 状态栏图标文字是否变灰
     *
     * @return
     */
    protected open fun statusBarLightMode(): Boolean {
        return true
    }

    protected fun needChangeHeight(): Boolean {
        return false
    }

    protected fun toast(@StringRes res: Int) {
        ToastUtil.instance.toast(res)
    }

    protected fun toast(str: String?) {
        str?.let { ToastUtil.instance.toast(it) }
    }

    override fun clickToFast() {

    }
}