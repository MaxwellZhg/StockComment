package com.zhuorui.securities.openaccount.model

/**
 *    author : liuwei
 *    e-mail : vsanliu@foxmail.com
 *    date   : 2019-08-28 14:00
 *    desc   :
 */

class CardOcrData() {
    var id: String? = null //开户ID
    var userId: String? = null //用户id
    var openStatus: Int? = 0 //开户状态
    var cardNo: String? = null //身份证号
    var cardName: String? = null //姓名
    var cardSex: String? = null //性别
    var cardNation: String? = null //民族
    var cardBirth: String? = null //出生日期
    var cardAddress: String? = null //住址
    var cardFrontPhoto: String? = null //身份证人像面url
    var cardAuthority: String? = null //签发机关
    var cardValidStartDate: String? = null //证件有效期开始日期
    var cardValidEndDate: String? = null //证件有效期结束时间
    var cardValidYear: Int? = null //有效期
    var cardBackPhoto: String? = null //身份证国徽面url

}