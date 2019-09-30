package com.zhuorui.securities.openaccount.model

import com.zhuorui.securities.openaccount.R

/**
 *    author : liuwei
 *    e-mail : vsanliu@foxmail.com
 *    date   : 2019-08-21 15:00
 *    desc   : 开户需要资料提示数据
 */
class OADataTips(type: Int) {

    var icon: Int? = 0
    var title: String? = ""
    var desc: String? = ""

    init {
        when (type) {
            1 -> {
                title = "中国内地身份证"
                desc = "年满18周岁的有效身份证"
                icon = R.mipmap.ic_idcard;
            }
            2 -> {
                title = "中国内地银行卡"
                desc = "仅用于实名验证"
                icon = R.mipmap.ic_bank_card;
            }
            3 -> {
                title = "良好的网络环境"
                desc = "建议使用WIFI或4G网络"
                icon = R.mipmap.ic_network;
            }
        }
    }

}