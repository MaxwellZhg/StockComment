package com.zhuorui.securities.openaccount.ui.presenter

import android.media.MediaPlayer
import com.zhuorui.securities.base2app.ui.fragment.AbsPresenter
import com.zhuorui.securities.openaccount.R
import com.zhuorui.securities.openaccount.ui.view.OARiskDisclosureView
import com.zhuorui.securities.openaccount.ui.viewmodel.OARiskDisclosureViewModel

/**
 *    author : PengXianglin
 *    e-mail : peng_xianglin@163.com
 *    date   : 2019/8/28 10:00
 *    desc   :
 */
class OARiskDisclosurePresenter : AbsPresenter<OARiskDisclosureView, OARiskDisclosureViewModel>() {

    var mediaPlayer: MediaPlayer? = null

    fun speechRisk() {
        if (mediaPlayer != null && mediaPlayer!!.isPlaying) {
            mediaPlayer?.release()
        }
        mediaPlayer = MediaPlayer.create(context, R.raw.risk_txt)
        mediaPlayer?.setOnCompletionListener {
            viewModel?.playingRisk?.value = false
            // 播放完成，释放资源
            mediaPlayer?.release()
            mediaPlayer = null
        }
        mediaPlayer?.setOnPreparedListener {
            // 开始播放
            mediaPlayer?.start()
            viewModel?.playingRisk?.value = true
        }
    }
}