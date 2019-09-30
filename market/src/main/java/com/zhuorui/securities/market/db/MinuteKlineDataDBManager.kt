package com.zhuorui.securities.market.db

import com.zhuorui.securities.market.db.greendao.DaoMaster
import com.zhuorui.securities.market.db.greendao.DaoSession
import com.zhuorui.securities.market.db.greendao.MinuteKlineDataDao
import com.zhuorui.securities.market.socket.vo.kline.MinuteKlineData
import com.zhuorui.securities.base2app.BaseApplication
import com.zhuorui.securities.base2app.infra.LogInfra
import org.greenrobot.greendao.database.Database
import java.util.concurrent.ConcurrentHashMap

/**
 * author : PengXianglin
 * e-mail : peng_xianglin@163.com
 * date   : 2019/8/14 15:04
 * desc   : 分时K线数据数据库管理
 */
class MinuteKlineDataDBManager private constructor(private val mTsCode: String) {

    companion object {

        private const val TAG = "MinuteKlineDataDBManager"

        private val mCaches = ConcurrentHashMap<String, MinuteKlineDataDBManager>()

        /**
         * 根据一个tsCode返回一个数据库操作管理器
         *
         * @param tsCode 一支股票的唯一标识
         * @return
         */
        fun getInstance(tsCode: String): MinuteKlineDataDBManager {
            var instance: MinuteKlineDataDBManager? = mCaches[tsCode]
            if (instance == null) {
                instance = MinuteKlineDataDBManager(tsCode)
                instance.init()
                mCaches[tsCode] = instance
                LogInfra.Log.d(TAG, "current caches:$mCaches")
            }
            return instance
        }
    }

    private var mHelper: DaoMaster.DevOpenHelper? = null
    private var mDatabase: Database? = null
    private var mDaoSession: DaoSession? = null
    private var mMinuteKlineDataDao: MinuteKlineDataDao? = null

    /**
     * 注意:每一支股票都会根据 tsCode 的不同而独立创建一个数据库表
     * 当删除一支自选股时，请删除对应的数据库表 [.dropTable].
     */
    fun init() {
        mHelper = DaoMaster.DevOpenHelper(BaseApplication.context, "MinuteKlineData$mTsCode.db")
        mDatabase = mHelper!!.writableDb
        val mDaoMaster = DaoMaster(mDatabase)
        mDaoSession = mDaoMaster.newSession()
        mMinuteKlineDataDao = mDaoSession!!.minuteKlineDataDao
    }

    private fun checkRunThread() {
        if (Thread.currentThread().name == "main") {
            throw RuntimeException("访问数据库必须子线程中执行！")
        }
    }

    /**
     * 增加单个数据
     */
    @Synchronized
    fun insert(klineData: MinuteKlineData) {
        checkRunThread()
        mMinuteKlineDataDao!!.insert(klineData)
    }

    /**
     * 增加多个数据
     */
    @Synchronized
    fun insertInTx(klineDatas: List<MinuteKlineData>?) {
        if (klineDatas == null) return
        checkRunThread()
        mMinuteKlineDataDao!!.insertInTx(klineDatas)
    }

    /**
     * 单个删除
     */
    @Synchronized
    fun deleteTopicMo(klineData: MinuteKlineData) {
        checkRunThread()
        mMinuteKlineDataDao!!.delete(klineData)
    }

//    /**
//     * 删除数据库表
//     * 注意：删除数据库表会销毁对应tsCode的MinuteKlineDataDBManager实例
//     */
//    @Synchronized
//    fun dropTable() {
//        MinuteKlineDataDao.dropTable(mDatabase!!, true)
//        closeDataBase()
//    }

    /**
     * 删除所有
     */
    @Synchronized
    fun deleteAll() {
        checkRunThread()
        mMinuteKlineDataDao!!.deleteAll()
    }

    /**
     * 更新单个数据
     */
    @Synchronized
    fun update(data: MinuteKlineData) {
        checkRunThread()
        mMinuteKlineDataDao!!.update(data)
    }

    /**
     * 查询所有
     */
    @Synchronized
    fun queryAll(): List<MinuteKlineData> {
        checkRunThread()
        return mMinuteKlineDataDao!!.queryBuilder().list()
    }

    /**
     * 注意：尽量不用时要关闭数据库
     * 关闭数据库
     */
    fun closeDataBase() {
        checkRunThread()
        LogInfra.Log.d(TAG, "closeDataBase")
        closeHelper()
        closeDaoSession()
        mCaches.remove(mTsCode)
    }

    private fun closeDaoSession() {
        if (null != mDaoSession) {
            mDaoSession!!.clear()
            mDaoSession = null
        }
    }

    private fun closeHelper() {
        if (mHelper != null) {
            mHelper!!.close()
            mHelper = null
        }
    }
}