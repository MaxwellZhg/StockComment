package com.zhuorui.securities.openaccount.widget;

import android.Manifest;
import android.content.Context;
import android.os.Build;
import android.util.AttributeSet;
import android.view.SurfaceView;
import androidx.annotation.RequiresApi;
import com.qw.soul.permission.SoulPermission;
import com.qw.soul.permission.bean.Permission;
import com.qw.soul.permission.bean.Permissions;
import com.qw.soul.permission.callbcak.CheckRequestPermissionsListener;
import com.zhuorui.securities.base2app.util.ToastUtil;

/**
 * author : PengXianglin
 * e-mail : peng_xianglin@163.com
 * date   : 2019/8/30 16:48
 * desc   : 自定义相机View
 */
public class CameraView extends SurfaceView implements CheckRequestPermissionsListener {

    // 初始化是否完成
    private boolean mInited;

    public CameraView(Context context) {
        super(context);
    }

    public CameraView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public CameraView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    public CameraView(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
    }

    public void init() {
        if (mInited) return;
        // 请求权限
        SoulPermission.getInstance().checkAndRequestPermissions(
                Permissions.build(
                        Manifest.permission.CAMERA,
                        Manifest.permission.WRITE_EXTERNAL_STORAGE,
                        Manifest.permission.READ_EXTERNAL_STORAGE
                ), this);
    }


    @Override
    public void onAllPermissionOk(Permission[] allPermissions) {
        // 获得权限，初始化录制界面
        mInited = true;
    }

    @Override
    public void onPermissionDenied(Permission[] refusedPermissions) {
        // 没有权限
        ToastUtil.Companion.getInstance().toast("没有权限");
    }

    @Override
    protected void onDetachedFromWindow() {
        super.onDetachedFromWindow();
        // 释放资源
        stop();
        release();
    }

    /**
     * 拍摄照片
     */
    public void takePhoto(TakePhotoCallBack callBack) {

    }

    /**
     * 拍摄视频
     *
     * @param duration 需要拍多长时间
     * @param callBack 拍摄结果返回
     */
    public void recordVedio(long duration, RecordVedioCallBack callBack) {

    }

    /**
     * 暂停拍摄，如APP被后台了，取消摄像头的调用，节省电量
     */
    public void stop() {
        mInited = false;
    }

    /**
     * 释放资源
     */
    public void release() {

    }

    /**
     * 拍摄照片回调照片流
     */
    public interface TakePhotoCallBack {
        void onTakeComplete(byte[] photoBytes);
    }

    /**
     * 拍摄视频回调视频流
     */
    public interface RecordVedioCallBack {
        void onRecordComplete(byte[] vedioBytes);
    }
}