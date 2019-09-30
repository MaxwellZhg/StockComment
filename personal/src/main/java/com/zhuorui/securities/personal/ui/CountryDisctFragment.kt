package com.zhuorui.securities.personal.ui

import android.annotation.SuppressLint
import android.os.Bundle
import android.os.Handler
import android.os.Message
import android.text.Editable
import android.text.TextUtils
import android.text.TextWatcher
import android.view.View
import android.widget.AdapterView
import androidx.lifecycle.ViewModelProviders
import com.google.gson.Gson
import com.zhuorui.securities.base2app.ui.fragment.AbsSwipeBackNetFragment
import com.zhuorui.securities.base2app.util.GetJsonDataUtil
import com.zhuorui.securities.base2app.util.ToastUtil
import com.zhuorui.securities.personal.BR
import com.zhuorui.securities.personal.R
import com.zhuorui.securities.personal.databinding.CountryCityFragmentBinding
import com.zhuorui.securities.personal.ui.adapter.SortAdapter
import com.zhuorui.securities.personal.ui.compare.PinyinComparator
import com.zhuorui.securities.personal.ui.model.JsonBean
import com.zhuorui.securities.personal.ui.presenter.CountryDisctPresenter
import com.zhuorui.securities.personal.ui.view.CountryDisctView
import com.zhuorui.securities.personal.ui.viewmodel.CountryDisctViewModel
import io.reactivex.Observable
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import kotlinx.android.synthetic.main.country_city_fragment.*
import me.jessyan.autosize.utils.LogUtils
import org.json.JSONArray
import java.util.*


/**
 * Created by Maxwell.
 * E-mail: maxwell_smith@163.com
 * Date: 2019/8/19
 * Desc:
 */
class CountryDisctFragment :AbsSwipeBackNetFragment<CountryCityFragmentBinding, CountryDisctViewModel, CountryDisctView, CountryDisctPresenter>(),View.OnClickListener,CountryDisctView,TextWatcher,AdapterView.OnItemClickListener {
    private val MSG_LOAD_DATA = 0x0001
    private val MSG_LOAD_SUCCESS = 0x0002
    private val MSG_LOAD_FAILED = 0x0003
    private var thread: Thread? = null
    private lateinit var jsonBean: LinkedList<JsonBean>
    private var result: LinkedList<JsonBean> = LinkedList()
    private var isLoaded: Boolean = false
    private var adapter:SortAdapter?=null
    private var handler = Handler()
    private var getTopicStockDataRunnable: GetTopicStockDataRunnable? = null
    override val layout: Int
        get() = R.layout.common_country_code_fragment
    override val viewModelId: Int
        get() = BR.viewmodel
    override val createPresenter: CountryDisctPresenter
        get() = CountryDisctPresenter()
    override val createViewModel: CountryDisctViewModel?
        get() = ViewModelProviders.of(this).get(CountryDisctViewModel::class.java)
    override val getView: CountryDisctView
        get() = this

    override fun onClick(p0: View?) {
        when (p0?.id) {
            R.id.iv_back -> {
                pop()
            }
        }
    }

    override fun init() {
        iv_back.setOnClickListener(this)
        quickindexbar.setonTouchLetterListener{
            for (i in 0 until jsonBean.size) {

                val city = jsonBean[i]

                val l = city.sortLetters

                if (TextUtils.equals(it, l)) {

                    //匹配成功
                    lv_country.setSelection(i)

                    break

                }

            }
        }
        et_search.addTextChangedListener(this)
        lv_country.setOnItemClickListener(this)
    }

    override fun rootViewFitsSystemWindowsPadding(): Boolean {
        return true
    }

    override fun onLazyInitView(savedInstanceState: Bundle?) {
        super.onLazyInitView(savedInstanceState)
        initJsonData()
    }

    @SuppressLint("HandlerLeak")
    private val mHandler = object : Handler() {
        override fun handleMessage(msg: Message) {
            when (msg.what) {
                MSG_LOAD_DATA -> if (thread == null) {//如果已创建就不再重新创建子线程了

                    // Toast.makeText(this@AddintoAddressActivity, "Begin Parse Data", Toast.LENGTH_SHORT).show()
                    thread = Thread(Runnable {
                        // 子线程中解析省市区数据
                        initJsonData()
                    })
                    thread!!.start()
                }

                MSG_LOAD_SUCCESS -> {
                    //Toast.makeText(AddintoAddressActivity.this, "Parse Succeed", Toast.LENGTH_SHORT).show();
                    LogUtils.e(jsonBean.toString())
                    Observable.just(jsonBean)
                        .subscribeOn(Schedulers.io())
                        .observeOn(AndroidSchedulers.mainThread())
                        .subscribe {
                            Collections.sort(it, PinyinComparator())
                            adapter = SortAdapter(requireContext())
                            it.addFirst(JsonBean("meiguo","美國","meiguo",false,"+1","United States of America","美国","常用地区","U"))
                            it.addFirst(JsonBean("zhongguotaiwan","中國台灣","zhongguotaiwan",false,"+886","Taiwan,China","中国台湾","常用地区","T"))
                            it.addFirst(JsonBean("zhongguoaomen","中國澳門","zhongguoaomen",false,"+853","Macao,China","中国澳门","常用地区","M"))
                            it.addFirst(JsonBean("zhongguoxianggang","中國香港","zhongguoxianggang",false,"+852","Hongkong,China","中国香港","常用地区","H"))
                            it.addFirst(JsonBean("zhongguo","中國内地","zhongguo",false,"+86","China","中国内地","常用地区","C"))
                            adapter?.addItems(it)
                            lv_country.adapter = adapter
                            adapter?.notifyDataSetChanged()
                        }
                    isLoaded = true
                }

                MSG_LOAD_FAILED -> {
                    ToastUtil.instance.toast("failed")
                }
            }
        }
    }

    private fun initJsonData() {
        val JsonData = GetJsonDataUtil().getJson(requireContext(), "countrys.json")//获取assets目录下的json文件数据
        jsonBean = parseData(JsonData)//用Gson 转成实体
    }

    private fun parseData(result: String): LinkedList<JsonBean> {//Gson 解析
        val detail = LinkedList<JsonBean>()
        try {
            val data = JSONArray(result)
            val gson = Gson()
            for (i in 0 until data.length()) {
                val entity:JsonBean = gson.fromJson<JsonBean>(data.optJSONObject(i).toString(), JsonBean::class.java)
                detail.add(JsonBean(entity.cn_py,entity.hant,entity.hant_py,entity.isUsed,entity.number,entity.en,entity.cn,entity.cn_py.substring(0,1).toUpperCase(),entity.en.substring(0,1).toUpperCase()))
            }
            mHandler.sendEmptyMessage(MSG_LOAD_SUCCESS)
        } catch (e: Exception) {
            e.printStackTrace()
            mHandler.sendEmptyMessage(MSG_LOAD_FAILED)
        }

        return detail
    }

    override fun onDestroy() {
        super.onDestroy()
        mHandler.removeCallbacksAndMessages(null)
    }

    override fun afterTextChanged(p0: Editable?) {
        if (p0.toString().isNotEmpty()) {

            p0?.toString()?.trim()?.let {
                handler.removeCallbacks(getTopicStockDataRunnable)
                getTopicStockDataRunnable = GetTopicStockDataRunnable(it)
                handler.postDelayed(getTopicStockDataRunnable, 500)
            }

        }
    }

    override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {

    }

    override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {

    }

    private inner class GetTopicStockDataRunnable(var keyWord: String) : Runnable {
        override fun run() {
             result.clear()
             result.addAll(presenter?.deatilJson(jsonBean,keyWord, presenter?.judgeSerachType(keyWord)!!)!!)
            if(result?.size>0) {
                adapter?.clearItems()
                adapter?.addItems(result)
                adapter?.notifyDataSetChanged()
            }else{
                adapter?.clearItems()
                adapter?.addItems(jsonBean)
                adapter?.notifyDataSetChanged()
            }
        }
    }

    override fun onItemClick(p0: AdapterView<*>, p1: View?, p2: Int, p3: Long) {
             presenter?.postValue(adapter!!.list[p2].cn,adapter!!.list[p2].number)
             pop()
    }



}

