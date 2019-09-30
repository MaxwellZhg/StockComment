package com.zhuorui.securities.base2app.util

import android.app.Activity
import android.app.Application
import android.os.Bundle
import com.zhuorui.securities.base2app.BaseApplication
import com.zhuorui.securities.base2app.infra.LogInfra
import java.util.*

/**
 * Created by xieyingwu on 2017/12/22.
 * 监听Application内部AC的生命周期
 */
class AppActivityLifecycleImp(appCloseListener: AppCloseListener) : Application.ActivityLifecycleCallbacks {
    var createdACNum = 0
        private set/*记录创建的AC数量*/
    private var destroyACNum = 0/*记录销毁的AC数量*/
    private var appCloseListener: AppCloseListener? = null
    private var startAcTimes = 0/*记录启动Ac的次数*/
    var isInBackground = false
        private set/*标志当前应用处于后台*/
    private val activities = LinkedList<Activity>()
//    val activityRxBusScopePool = ConcurrentHashMap<Activity, LazyRxBus>()

    /**
     * 返回topActivity
     *
     * @return null表明还未有Ac
     */
    val topActivity: Activity?
        get() {
            val empty = activities.isEmpty()
            return if (empty) null else activities[0]
        }

    init {
        if (this.appCloseListener != null) this.appCloseListener = null
        this.appCloseListener = appCloseListener
    }

    override fun onActivityCreated(activity: Activity, savedInstanceState: Bundle?) {
//        activityRxBusScopePool.put(activity, LazyRxBus())

        activities.addFirst(activity)
        createdACNum++
        LogInfra.Log.i(TAG, "onActivityCreated() createdACNum = $createdACNum")
    }

    override fun onActivityStarted(activity: Activity) {
        /*Ac启动时增量*/
        startAcTimes++
        LogInfra.Log.i(TAG, "onActivityStarted() startAcTimes = $startAcTimes")
        if (isInBackground || startAcTimes == 1) {
            /*从后台重新进入前台*/
            if (appCloseListener != null) appCloseListener!!.app2Foreground()
        }
        /*Ac启动，不处于后台*/
        this.isInBackground = false
    }

    override fun onActivityResumed(activity: Activity) {

    }

    override fun onActivityPaused(activity: Activity) {

    }

    override fun onActivityStopped(activity: Activity) {
        /*Ac停止时减量*/
        startAcTimes--
        LogInfra.Log.i(TAG, "onActivityStopped() startAcTimes = $startAcTimes")
        startAcTimes = Math.max(0, startAcTimes)
        /*检测应用是否退至后台*/
        val isToBackground = startAcTimes <= 0
        LogInfra.Log.i(TAG, "isToBackground = $isToBackground")
        this.isInBackground = isToBackground
        if (this.isInBackground) {
            if (appCloseListener != null) appCloseListener!!.app2Background()
        }
    }

    override fun onActivitySaveInstanceState(activity: Activity, outState: Bundle) {

    }

    override fun onActivityDestroyed(activity: Activity) {
//        if (activityRxBusScopePool.containsKey(activity))
//            activityRxBusScopePool.remove(activity)

        activities.remove(activity)
        destroyACNum++
        LogInfra.Log.i(TAG, "onActivityDestroyed() destroyACNum = $destroyACNum")
        checkAppClose()
    }

    private fun checkAppClose() {
        LogInfra.Log.i(TAG, "checkAppClose()--> createdACNum = $createdACNum ;destroyACNum = $destroyACNum")
        if (createdACNum <= 0) return
        val isAllClose = createdACNum == destroyACNum
        if (isAllClose) {
            createdACNum = 0
            destroyACNum = 0
            startAcTimes = 0
            isInBackground = false
            LogInfra.Log.i(TAG, "appCloseListener = " + appCloseListener!!)
            if (appCloseListener != null) appCloseListener!!.appClose()
        }
    }

    /**
     * 关闭之前的Ac并启动一个新的Ac
     *
     * @param startNewAcClass newAcClass
     */
    fun finishAllAndStartNewAc(startNewAcClass: Class<*>) {
        val waitFinishAcs = LinkedList<Activity>()
        waitFinishAcs.addAll(activities)
        val size = waitFinishAcs.size
        if (size == 0) {
            /*若当前无topActivity；则主动跳转到NewAc*/
            JumpUtil.jumpActivityNewTask(BaseApplication.context, startNewAcClass, null)
            return
        }
        val topActivity = waitFinishAcs[0]
        JumpUtil.jump(topActivity, startNewAcClass, true)
        for (i in 1 until size) {
            val activity = waitFinishAcs[i]
            activity.finish()
        }
    }

    fun finishOtherAndStartNewAc(excludeFinishAcClass: Class<*>?, startNewAcClass: Class<*>, bundle: Bundle) {
        val waitFinishAcs = LinkedList<Activity>()
        if (excludeFinishAcClass != null) {
            val acName = excludeFinishAcClass.name
            for (activity in activities) {
                val name = activity.javaClass.name
                if (acName != name) {
                    waitFinishAcs.add(activity)
                }
            }
        } else {
            waitFinishAcs.addAll(activities)
        }
        val size = waitFinishAcs.size
        if (size == 0) {
            /*若当前无topActivity；则主动跳转到NewAc*/
            JumpUtil.jumpActivityNewTask(BaseApplication.context, startNewAcClass, bundle)
            return
        }
        val topActivity = waitFinishAcs[0]
        JumpUtil.jumpWithBundle(topActivity, startNewAcClass, true, bundle)
        for (i in 1 until size) {
            val activity = waitFinishAcs[i]
            activity.finish()
        }
    }

    fun finishOther(onlyKeepAcClass: Class<*>) {
        val waitFinishAcs = LinkedList<Activity>()
        val acName = onlyKeepAcClass.name
        var keepAcExit = false
        for (activity in activities) {
            val name = activity.javaClass.name
            if (acName != name) {
                waitFinishAcs.add(activity)
            } else {
                keepAcExit = true
            }
        }
        val size = waitFinishAcs.size
        if (keepAcExit) {
            /*finish掉待finish的ac*/
            for (i in 0 until size) {
                val activity = waitFinishAcs[i]
                activity.finish()
            }
            return
        }
        /*需要启动KeepAc*/
        if (size == 0) {
            /*若当前无TopActivity,则主动跳转到keepAc*/
            JumpUtil.jumpActivityNewTask(BaseApplication.context, onlyKeepAcClass, null)
            return
        }
        val topActivity = waitFinishAcs[0]
        JumpUtil.jumpWithBundle(topActivity, onlyKeepAcClass, true, null)
        for (i in 1 until size) {
            val activity = waitFinishAcs[i]
            activity.finish()
        }
    }

    fun findActivity(acClass: Class<*>): Activity? {
        for (activity in activities) {
            if (activity.javaClass.name == acClass.name) {
                return activity
            }
        }
        return null
    }

    /**
     * 是否有Ac存在
     *
     * @return boolean, 判断是否有Ac创建
     */
    fun hadAcCreated(): Boolean {
        return createdACNum > 0 && createdACNum > destroyACNum
    }

    interface AppCloseListener {
        fun appClose()

        fun app2Foreground()

        fun app2Background()
    }

    companion object {
        private val TAG = AppActivityLifecycleImp::class.java.simpleName
    }
}
