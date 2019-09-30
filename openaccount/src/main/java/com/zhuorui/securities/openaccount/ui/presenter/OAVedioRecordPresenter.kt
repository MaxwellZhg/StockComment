package com.zhuorui.securities.openaccount.ui.presenter

import com.zhuorui.securities.base2app.Cache
import com.zhuorui.securities.base2app.network.ErrorResponse
import com.zhuorui.securities.base2app.network.Network
import com.zhuorui.securities.base2app.rxbus.EventThread
import com.zhuorui.securities.base2app.rxbus.RxSubscribe
import com.zhuorui.securities.base2app.ui.fragment.AbsNetPresenter
import com.zhuorui.securities.openaccount.manager.OpenInfoManager
import com.zhuorui.securities.openaccount.net.IOpenAccountNet
import com.zhuorui.securities.openaccount.net.request.LiveCodeRequest
import com.zhuorui.securities.openaccount.net.request.LiveRecognRequest
import com.zhuorui.securities.openaccount.net.response.LiveCodeResponse
import com.zhuorui.securities.openaccount.net.response.LiveRecognResponse
import com.zhuorui.securities.openaccount.ui.view.OAVedioRecordView
import com.zhuorui.securities.openaccount.ui.viewmodel.OAVedioRecordViewModel
import com.zhuorui.securities.openaccount.utils.Base64Enum
import com.zhuorui.securities.openaccount.utils.FileToBase64Util
import io.reactivex.Observable
import io.reactivex.ObservableOnSubscribe
import io.reactivex.disposables.Disposable
import io.reactivex.schedulers.Schedulers
import java.util.*

/**
 * Created by Maxwell.
 * E-mail: maxwell_smith@163.com
 * Date: 2019/8/27
 * Desc:
 */
class OAVedioRecordPresenter : AbsNetPresenter<OAVedioRecordView, OAVedioRecordViewModel>() {

    private val disposables = LinkedList<Disposable>()

    override fun init() {
        super.init()
        requestVedioVerifyCode()
    }

    /**
     * 请求活体检查数字码
     */
    fun requestVedioVerifyCode() {
        OpenInfoManager.getInstance()?.info?.id?.let {
            val request = LiveCodeRequest(it, transactions.createTransaction())
            Cache[IOpenAccountNet::class.java]?.getLiveCode(request)
                ?.enqueue(Network.IHCallBack<LiveCodeResponse>(request))
        }
    }

    @RxSubscribe(observeOnThread = EventThread.MAIN)
    fun onVerifyLiveCode(response: LiveCodeResponse) {
        if (response.request is LiveCodeRequest) {
            viewModel?.str?.set(response.data.validateCode)
        }
    }

    fun uploadVedio(vedioBytes: ByteArray?) {
        view?.showUploading()

        //  在子线程中计算视频流的Base64码，然后上传
        val disposable = Observable.create(ObservableOnSubscribe<String> { emitter ->
            emitter.onNext(FileToBase64Util.getBase64String(Base64Enum.MP4, vedioBytes))
            emitter.onComplete()
        }).subscribeOn(Schedulers.io())
            .subscribe {
                val request = LiveRecognRequest(
                    it,
                    viewModel?.str?.get(),
                    OpenInfoManager.getInstance()?.info?.id,
                    transactions.createTransaction()
                )
                Cache[IOpenAccountNet::class.java]?.getLiveRecogn(request)
                    ?.enqueue(Network.IHCallBack<LiveRecognResponse>(request))
            }
        disposables.add(disposable)
    }

    @RxSubscribe(observeOnThread = EventThread.MAIN)
    fun onLiveRecognResponse(response: LiveRecognResponse) {
        // 设置数据
        val info = OpenInfoManager.getInstance()?.info
        info?.openStatus = response.data.openStatus
        info?.video = response.data.video
        info?.validateCode = response.data.validateCode

        view?.hideUploading()
        view?.uploadComplete()
    }

    override fun onErrorResponse(response: ErrorResponse) {
        super.onErrorResponse(response)
        if (response.request is LiveRecognRequest) {
            view?.hideUploading()
        }
    }

    override fun destroy() {
        super.destroy()

        // 释放disposable
        if (disposables.isNullOrEmpty()) return
        for (disposable in disposables) {
            disposable.dispose()
        }
        disposables.clear()
    }
}