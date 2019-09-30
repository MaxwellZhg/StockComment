@file:Suppress("UNCHECKED_CAST")

package com.zhuorui.securities.base2app

import com.zhuorui.securities.base2app.network.Network

import java.util.HashMap

/**
 * 集合Map实现单例模式
 */
object Cache {
    private val caches = HashMap<String, Any>()

    operator fun <T> get(cls: Class<T>): T? {
        var `object` = caches[cls.name]
        if (`object` == null) {
            instance(cls)
            `object` = caches[cls.name]
        }
        return `object` as T?
    }

    fun <T> remove(cls: Class<T>): T? {
        return caches.remove(cls.name) as T?
    }

    fun clear() {
        caches.clear()
    }

    private fun instance(clz: Class<*>) {
        val name = clz.name
        var instance: Any? = null
        if (name.endsWith("Net")) {
            /*针对单独一个网络请求*/
            instance = Network.retrofit!!.create(clz)
        } else {
            try {
                /*针对Manager处理的网络请求*/
                val clazz = Class.forName(name.replace(".I", "."))
                val cons = clazz.getDeclaredConstructor()
                cons.isAccessible = true
                instance = cons.newInstance()
            } catch (e: Exception) {
                e.printStackTrace()
            }

        }
        caches[clz.name] = instance!!
    }
}
