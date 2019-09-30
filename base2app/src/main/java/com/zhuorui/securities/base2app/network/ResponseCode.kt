package com.zhuorui.securities.base2app.network

/**
 *    author : PengXianglin
 *    e-mail : peng_xianglin@163.com
 *    date   : 2019/8/5 11:41
 *    desc   : 业务请求的返回码
 */
object ResponseCode {
    //以900101为例:90表示推送服务错误码开始,01表示公共模块错误,01模块内具体错误码。“000000”表示成功

    //公共模块 01
    var EMPTY_PARAM = "900101"// 参数为空
    var ERROR_PARAM = "900102"// 参数错误
    var REQUIRED_PARAMETER_MISSING = "900103"// 缺少必要参数
    var CONVERT_DTO_ERROR = "900104"// 对象转换出错
    var NO_PERMISSION = "900105"// 没有权限
    var SYSTEM_ERROR = "900106"// 未知系统错误
    var REQUEST_PATH_ERROR = "900107"// 请求业务路径错误
    var PARAM_FORMAT_ERROR = "900108"// 参数格式错误
    var SERVER_ERROT = "900109"// 内部服务器发生错误
    var BUSS_NOT_EXIT = "900110"// 业务不存在
    var NOT_YET_CERTIFIED = "900111"// 还未认证

    //登录模块 02
    var TOKEN_INVALID = "900201"// Token值非法
    var AUTH_FAIL = "900202"// 认证失败
}