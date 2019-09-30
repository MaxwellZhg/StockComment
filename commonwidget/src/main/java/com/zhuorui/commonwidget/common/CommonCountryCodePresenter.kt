package com.zhuorui.commonwidget.common

import com.zhuorui.securities.base2app.ui.fragment.AbsNetPresenter
import java.util.*
import java.util.regex.Pattern

/**
 * Created by Maxwell.
 * E-mail: maxwell_smith@163.com
 * Date: 2019/8/30
 * Desc:
 */
class CommonCountryCodePresenter :AbsNetPresenter<CommonCountryCodeView,CommonCountryViewModel>(){
    var list: LinkedList<JsonBean> = LinkedList()
    lateinit var searchStr:String
    override fun init() {
        super.init()
    }

    fun deatilJson(detail: LinkedList<JsonBean>, str:String, type:Int): LinkedList<JsonBean> {
        list.clear()
        when(type){
            1->{
                val pinyin = PinyinUtils.getPingYin(str)
                searchStr=pinyin.substring(0, 1).toUpperCase()
            }
            2->{
                searchStr=str.substring(0, 1).toUpperCase()
            }
            3->{
                searchStr=str
            }
        }
        /*if(type==1) {
    val pinyin = PinyinUtils.getPingYin(str)
    pinyin.substring(0, 1).toUpperCase()
}else{
    str
}*/
        for(jsonbean in detail){
            when(type){
                1->{
                    if(jsonbean.sortLetters==searchStr&&jsonbean.cn.contains(str)){
                        list.add(jsonbean)
                    }
                }
                2->{
                    if(jsonbean.sortEnletters==searchStr&&jsonbean.en.contains(str)){
                        list.add(jsonbean)
                    }
                }
                3->{
                    if(jsonbean.number.contains(searchStr)){
                        list.add(jsonbean)
                    }
                }
            }
        }
        return list
    }

    fun judgeSerachType(str: String) : Int{
        val pattern1 = "^[\\u4E00-\\u9FA5]+\$"
        val pattern2 ="^[A-Za-z]+\$"
        val pattern3 ="^\\+?[1-9][0-9]*\$"
        //用正则式匹配文本获取匹配器
        val matcher1 = Pattern.compile(pattern1).matcher(str)
        val matcher2 = Pattern.compile(pattern2).matcher(str)
        val matcher3 = Pattern.compile(pattern3).matcher(str)
        return when {
            matcher1.find() -> 1
            matcher2.find() -> 2
            matcher3.find() -> 3
            else -> 0
        }
    }
}