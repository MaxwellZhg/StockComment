package com.zhuorui.securities.openaccount.net

import com.zhuorui.securities.openaccount.net.api.OpenAccountApi
import com.zhuorui.securities.openaccount.net.request.LiveCodeRequest
import com.zhuorui.securities.openaccount.net.request.LiveRecognRequest
import com.zhuorui.securities.openaccount.net.request.OpenInfoRequest
import com.zhuorui.securities.openaccount.net.request.SubSignatureRequest
import com.zhuorui.securities.openaccount.net.response.LiveCodeResponse
import com.zhuorui.securities.openaccount.net.response.LiveRecognResponse
import com.zhuorui.securities.openaccount.net.response.OpenInfoResponse
import com.zhuorui.securities.openaccount.net.response.SubSignatureResponse
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.POST

/**
 * Created by Maxwell.
 * E-mail: maxwell_smith@163.com
 * Date: 2019/8/28
 * Desc:
 */
interface IOpenAccountNet {

    //获取开户信息
    @POST(OpenAccountApi.GET_OPEN_INFO)
    fun getOpenInfo(@Body request: OpenInfoRequest): Call<OpenInfoResponse>

    //获取视频验证码
    @POST(OpenAccountApi.GET_LIVE_CODE)
    fun getLiveCode(@Body request: LiveCodeRequest): Call<LiveCodeResponse>

    //获取上传活体检测
    @POST(OpenAccountApi.LIVENESS_RECOGNITION)
    fun getLiveRecogn(@Body request: LiveRecognRequest): Call<LiveRecognResponse>

    @POST(OpenAccountApi.SUB_SIGNATURE)
    fun subSignature(@Body request: SubSignatureRequest): Call<SubSignatureResponse>
}