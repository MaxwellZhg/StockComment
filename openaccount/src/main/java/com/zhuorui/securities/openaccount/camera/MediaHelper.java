//package com.zhuorui.securities.openaccount.camera;
//
//import android.app.Activity;
//import android.graphics.Bitmap;
//import android.graphics.BitmapFactory;
//import android.graphics.ImageFormat;
//import android.graphics.Matrix;
//import android.hardware.Camera;
//import android.media.AudioManager;
//import android.media.CamcorderProfile;
//import android.media.MediaPlayer;
//import android.media.MediaRecorder;
//import android.util.Log;
//import android.view.GestureDetector;
//import android.view.MotionEvent;
//import android.view.OrientationEventListener;
//import android.view.Surface;
//import android.view.SurfaceHolder;
//import android.view.SurfaceView;
//import android.view.View;
//import android.view.ViewGroup;
//import android.widget.ImageView;
//
//import com.bumptech.glide.Glide;
//import com.bumptech.glide.load.engine.DiskCacheStrategy;
//import com.zhuorui.securities.base2app.infra.LogInfra;
//import com.zhuorui.securities.base2app.util.AppUtil;
//import com.zhuorui.securities.base2app.util.ResUtil;
//
//import java.io.BufferedOutputStream;
//import java.io.File;
//import java.io.FileOutputStream;
//import java.io.IOException;
//import java.util.List;
//
///**
// * Created by pengxianglin on 2017/11/16.
// */
//public class MediaHelper implements SurfaceHolder.Callback, MediaPlayer.OnVideoSizeChangedListener {
//    private static final String TAG = "MediaUtils";
//    private Activity activity;
//    private MediaRecorder mMediaRecorder;
//    private MediaPlayer mediaPlayer;
//    private CamcorderProfile profile;
//    private Camera mCamera;
//    private SurfaceView mSurfaceView;
//    private SurfaceHolder mSurfaceHolder;
//    private ImageView imageView;
//    private File targetVideoDir, targetPictureDir;
//    private String targetVideoName, targetPictureName;
//    private File targetFile;
//    private int previewWidth = 1280, previewHeight = 720;
//    private boolean isRecording;
//    private boolean isTakeing;
//    private GestureDetector mDetector;
//    private boolean isZoomIn = false;
//    private final int orientation_0 = 0, orientation_90 = 90, orientation_180 = 180, orientation_270 = 270, orientation_360 = 360;
//    private int screenOrientation;
//    private int cameraPosition = Camera.CameraInfo.CAMERA_FACING_BACK;//0代表前置摄像头，1代表后置摄像头
//    private int currentPlayPosition;//记录暂停的位置
//    private CompleteListener completeListener;
//    public OrientationEventListener mOrientationListener;
//    public OrientationEventCallBack mOrientationEventCallBack;
//
//    public interface OrientationEventCallBack {
//        void onOrientationChanged(int orientation);
//    }
//
//    public MediaHelper(final Activity activity) {
//        this.activity = activity;
//        mOrientationListener = new OrientationEventListener(activity) {
//            @Override
//            public void onOrientationChanged(int orientation) {
//                if (orientation > 325 || orientation <= 45) {
//                    screenOrientation = 0;
//                } else if (orientation > 45 && orientation <= 135) {
//                    screenOrientation = 270;
//                } else if (orientation > 135 && orientation < 225) {
//                    screenOrientation = 180;
//                } else {
//                    screenOrientation = 90;
//                }
//                if (mOrientationEventCallBack != null)
//                    mOrientationEventCallBack.onOrientationChanged(screenOrientation);
//                LogInfra.Log.d(TAG, "orientation: " + screenOrientation);
//            }
//        };
//    }
//
//    public void setOrientationEventCallBack(OrientationEventCallBack callBack) {
//        this.mOrientationEventCallBack = callBack;
//    }
//
//    public void setTargetDir(File pictureDir, File videoDir) {
//        this.targetPictureDir = pictureDir;
//        if (!pictureDir.exists()) {
//            File parent = new File(pictureDir.getParent());
//            if (!parent.exists()) {
//                parent.mkdir();
//            }
//            pictureDir.mkdir();
//        }
//        this.targetVideoDir = videoDir;
//        if (!videoDir.exists()) {
//            videoDir.mkdir();
//        }
//    }
//
//    public void setTargetName(String targetPictureName, String targetVideoName) {
//        this.targetVideoName = targetVideoName;
//        this.targetPictureName = targetPictureName;
//    }
//
//    public String getTargetFilePath() {
//        if (targetFile == null) return null;
//        return targetFile.getPath();
//    }
//
//    private boolean deleteTargetFile() {
//        if (targetFile == null) return false;
//        if (targetFile.exists()) {
//            return targetFile.delete();
//        } else {
//            return false;
//        }
//    }
//
//    public void setSurfaceView(SurfaceView view) {
//        this.mSurfaceView = view;
//        mSurfaceHolder = mSurfaceView.getHolder();
//        mSurfaceHolder.setFixedSize(previewWidth, previewHeight);
//        mSurfaceHolder.setType(SurfaceHolder.SURFACE_TYPE_PUSH_BUFFERS);
//        mSurfaceHolder.addCallback(this);
//        mDetector = new GestureDetector(activity, new ZoomGestureListener());
//        mSurfaceView.setOnTouchListener(new View.OnTouchListener() {
//            @Override
//            public boolean onTouch(View v, MotionEvent event) {
//                mDetector.onTouchEvent(event);
//                return true;
//            }
//        });
//    }
//
//    /**
//     * 继续播放
//     */
//    private void onResume() {
//        //页面从前台到后台会执行 onPause ->onStop 此时Surface会被销毁，
//        //再一次从后台 到前台时需要 重新创建Surface
//        try {
//            play();
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
//    }
//
//    /**
//     * 播放预览
//     */
//    private void play() throws IOException {
//        File video = new File(targetVideoDir, targetVideoName);
//        if (video.exists()) {
//            targetFile = video;
//            imageView.setVisibility(View.GONE);
//            mSurfaceView.setVisibility(View.VISIBLE);
//            if (mediaPlayer == null) {
//                mediaPlayer = new MediaPlayer();
//            }
//            mediaPlayer.reset();
//            mediaPlayer.setAudioStreamType(AudioManager.STREAM_MUSIC);
//            mediaPlayer.setDataSource(getTargetFilePath());
//            // 把视频输出到SurfaceView上
//            mediaPlayer.setDisplay(mSurfaceHolder);
//            mediaPlayer.prepare();
//            mediaPlayer.setOnVideoSizeChangedListener(this);
//            mediaPlayer.setLooping(true);
//            mediaPlayer.seekTo(currentPlayPosition);
//            mediaPlayer.start();
//        } else {
//            targetFile = new File(targetPictureDir, targetPictureName);
//            imageView.setVisibility(View.VISIBLE);
//            mSurfaceView.setVisibility(View.GONE);
//            Glide.with(this.activity)
//                    .load(getTargetFilePath())
//                    .diskCacheStrategy(DiskCacheStrategy.NONE)
//                    .skipMemoryCache(true)
//                    .into(imageView);
//        }
//    }
//
//    @Override
//    public void onVideoSizeChanged(MediaPlayer mp, int width, int height) {
//        /*更新播放视频控件宽高*/
//        updateSurfaceWH(width, height);
//    }
//
//    private void updateSurfaceWH(int width, int height) {
//        /*计算按照当前屏幕比例缩放后的视频大小*/
//        int[] wh = ResUtil.calculateWH(AppUtil.getScreenWidth(this.activity), AppUtil.getScreenHeight(this.activity), width, height);
//        Log.i(TAG, "updateSurfaceWH() --> w = " + wh[0] + " ; h = " + wh[1]);
//        ViewGroup.LayoutParams lp = mSurfaceView.getLayoutParams();
//        lp.width = wh[0];
//        lp.height = wh[1];
//        mSurfaceView.setLayoutParams(lp);
//    }
//
//    public boolean isRecording() {
//        return isRecording;
//    }
//
//    /**
//     * 拍照
//     */
//    public void capture() {
//        if (prepareCapture()) {
//            try {
//                mCamera.autoFocus(new Camera.AutoFocusCallback() {
//                    @Override
//                    public void onAutoFocus(boolean success, Camera camera) {
//                        if (isRecording || isTakeing) return;
//                        isTakeing = true;
//                        try {
//                            camera.takePicture(null, null, pictureCallback);
//                        } catch (Exception e) {
//                            e.printStackTrace();
//                        }
//                    }
//                });
//            } catch (Exception e) {
//                e.printStackTrace();
//            }
//        }
//    }
//
//    // 设置相机横竖屏
//    private void setCameraDisplayOrientation(Activity activity, int cameraId, Camera camera) {
//        Camera.CameraInfo info = new Camera.CameraInfo();
//        Camera.getCameraInfo(cameraId, info);
//        int rotation = activity.getWindowManager().getDefaultDisplay()
//                .getRotation();
//        int degrees = 0;
//        switch (rotation) {
//            case Surface.ROTATION_0:
//                degrees = 0;
//                break;
//            case Surface.ROTATION_90:
//                degrees = 90;
//                break;
//            case Surface.ROTATION_180:
//                degrees = 180;
//                break;
//            case Surface.ROTATION_270:
//                degrees = 270;
//                break;
//        }
//
//        int result;
//        if (info.facing == Camera.CameraInfo.CAMERA_FACING_FRONT) {
//            result = (info.orientation + degrees) % 360;
//            result = (360 - result) % 360;
//        } else {
//            result = (info.orientation - degrees + 360) % 360;
//        }
//        camera.setDisplayOrientation(result);
//    }
//
//    // 旋转图片
//    public static Bitmap rotateBitmapByDegree(Bitmap bm, int degree) {
//        Bitmap returnBm = null;
//        Matrix matrix = new Matrix();
//        matrix.postRotate(degree);
//        try {
//            returnBm = Bitmap.createBitmap(bm, 0, 0, bm.getWidth(),
//                    bm.getHeight(), matrix, true);
//        } catch (OutOfMemoryError e) {
//        }
//        if (returnBm == null) {
//            returnBm = bm;
//        }
//        if (bm != returnBm) {
//            bm.recycle();
//        }
//        return returnBm;
//    }
//
//
//    private final Camera.PictureCallback pictureCallback = new Camera.PictureCallback() {
//
//        @Override
//        public void onPictureTaken(byte[] data, Camera camera) {
//            if (data != null) {
//                //生成缩略图
//                try {
//                    //解析生成相机返回的图片
//                    Bitmap bitmap = BitmapFactory.decodeByteArray(data, 0, data.length);
//                    //实际拍摄的方向
//                    Camera.CameraInfo info = new Camera.CameraInfo();
//                    Camera.getCameraInfo(cameraPosition, info);
//                    if (info.facing == Camera.CameraInfo.CAMERA_FACING_FRONT) {
//                        if (screenOrientation == orientation_0) {
//                            bitmap = rotateBitmapByDegree(bitmap, orientation_270);
//                        } else if (screenOrientation == orientation_90) {
//                            bitmap = rotateBitmapByDegree(bitmap, orientation_0);
//                        } else if (screenOrientation == orientation_180) {
//                            bitmap = rotateBitmapByDegree(bitmap, orientation_90);
//                        } else {
//                            bitmap = rotateBitmapByDegree(bitmap, orientation_180);
//                        }
//                    } else {
//                        if (screenOrientation == orientation_0) {
//                            bitmap = rotateBitmapByDegree(bitmap, orientation_90);
//                        } else if (screenOrientation == orientation_90) {
//                            bitmap = rotateBitmapByDegree(bitmap, orientation_0);
//                        } else if (screenOrientation == orientation_180) {
//                            bitmap = rotateBitmapByDegree(bitmap, orientation_270);
//                        } else {
//                            bitmap = rotateBitmapByDegree(bitmap, orientation_180);
//                        }
//                    }
//                    File file = new File(targetPictureDir, targetPictureName);
//                    BufferedOutputStream bos = new BufferedOutputStream(new FileOutputStream(file));
//                    bitmap.compress(Bitmap.CompressFormat.JPEG, 100, bos);
//                    bos.flush();
//                    bos.close();
//                } catch (Exception e) {
//                    e.printStackTrace();
//                }
//
//            }
//            //显示拍照图片
//            try {
//                play();
//                releaseCamera();
//                if (completeListener != null) {
//                    completeListener.onComplete();
//                }
//            } catch (IOException e) {
//                e.printStackTrace();
//            }
//            isTakeing = false;
//        }
//    };
//
//    /**
//     * 录制
//     */
//    public void record() {
//        if (isRecording) {
//            try {
//                mMediaRecorder.stop();  // stop the recording
//            } catch (RuntimeException e) {
//                Log.d(TAG, "RuntimeException: stop() is called immediately after start()");
//                //noinspection ResultOfMethodCallIgnored
//                if (targetFile != null) {
//                    targetFile.delete();
//                }
//            }
//            releaseMediaRecorder(); // release the MediaRecorder object
//            mCamera.lock();         // take camera access back from MediaRecorder
//            isRecording = false;
//        } else {
//            startRecordThread();
//        }
//    }
//
//    private boolean prepareCapture() {
//        try {
//            if (null != mCamera) {
//                Camera.Parameters params = mCamera.getParameters();
//                List<Camera.Size> mSupportedPreviewSizes = params.getSupportedPreviewSizes();
////                List<Camera.Size> mSupportedVideoSizes = params.getSupportedVideoSizes();
////                Camera.Size optimalSize = CameraHelper.getOptimalVideoSize(mSupportedVideoSizes,
////                        mSupportedPreviewSizes, mSurfaceView.getWidth(), mSurfaceView.getHeight());
//                // Use the same size for recording profile.
////                previewWidth = 1920;
////                previewHeight = 1080;
//                params.setPreviewSize(previewWidth, previewHeight);
////                List<Camera.Size> mSupportedPictureSizes = params.getSupportedPictureSizes();
////                Camera.Size optimalPictureSize = CameraHelper.getOptimalVideoSize(mSupportedPictureSizes,
////                        mSupportedPreviewSizes, mSurfaceView.getWidth(), mSurfaceView.getHeight());
//                params.setPictureSize(previewWidth, previewHeight);
//                //设置图片格式
//                params.setPictureFormat(ImageFormat.JPEG);
//                params.setJpegQuality(100);
//                params.setJpegThumbnailQuality(100);
//                List<String> modes = params.getSupportedFocusModes();
//                if (modes.contains(Camera.Parameters.FOCUS_MODE_AUTO)) {
//                    //支持自动聚焦模式
//                    params.setFocusMode(Camera.Parameters.FOCUS_MODE_AUTO);
//                }
//                mCamera.setParameters(params);
//                setCameraDisplayOrientation(activity, cameraPosition, mCamera);
//            }
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
//        return true;
//    }
//
//    private boolean prepareRecord() {
//        try {
//            mMediaRecorder = new MediaRecorder();
//            mCamera.unlock();
//            mMediaRecorder.setCamera(mCamera);
//            mMediaRecorder.setAudioSource(MediaRecorder.AudioSource.DEFAULT);
//            mMediaRecorder.setVideoSource(MediaRecorder.VideoSource.CAMERA);
//            mMediaRecorder.setProfile(profile);
//            // 实际视屏录制后的方向
////            if (cameraPosition == Camera.CameraInfo.CAMERA_FACING_BACK) {
////                mMediaRecorder.setOrientationHint(orientation_90);
////            } else {
////                mMediaRecorder.setOrientationHint(orientation_270);
////            }
//            Camera.CameraInfo info = new Camera.CameraInfo();
//            Camera.getCameraInfo(cameraPosition, info);
//            if (info.facing == Camera.CameraInfo.CAMERA_FACING_FRONT) {
//                if (screenOrientation == orientation_0) {
//                    mMediaRecorder.setOrientationHint(orientation_270);
//                } else if (screenOrientation == orientation_90) {
//                    mMediaRecorder.setOrientationHint(orientation_0);
//                } else if (screenOrientation == orientation_180) {
//                    mMediaRecorder.setOrientationHint(orientation_90);
//                } else {
//                    mMediaRecorder.setOrientationHint(orientation_180);
//                }
//            } else {
//                if (screenOrientation == orientation_0) {
//                    mMediaRecorder.setOrientationHint(orientation_90);
//                } else if (screenOrientation == orientation_90) {
//                    mMediaRecorder.setOrientationHint(orientation_0);
//                } else if (screenOrientation == orientation_180) {
//                    mMediaRecorder.setOrientationHint(orientation_270);
//                } else {
//                    mMediaRecorder.setOrientationHint(orientation_180);
//                }
//            }
//            mMediaRecorder.setOutputFile(new File(targetVideoDir, targetVideoName).getPath());
//        } catch (Exception e) {
//            e.printStackTrace();
//            Log.d("MediaRecorder", "Exception prepareRecord: ");
//            releaseMediaRecorder();
//            return false;
//        }
//        try {
//            mMediaRecorder.prepare();
//        } catch (IllegalStateException e) {
//            Log.d("MediaRecorder", "IllegalStateException preparing MediaRecorder: " + e.getMessage());
//            releaseMediaRecorder();
//            return false;
//        } catch (IOException e) {
//            Log.d("MediaRecorder", "IOException preparing MediaRecorder: " + e.getMessage());
//            releaseMediaRecorder();
//            return false;
//        }
//        return true;
//    }
//
//    public void stopRecordSave() {
//        Log.d("Recorder", "stopRecordSave");
//        if (isRecording) {
//            isRecording = false;
//            try {
//                mMediaRecorder.stop();
//                releaseCamera();
//                targetFile = new File(targetVideoDir, targetVideoName);
//                //播放预览
//                play();
//                Log.d("Recorder", targetFile.getPath());
//                if (completeListener != null) {
//                    completeListener.onComplete();
//                }
//            } catch (RuntimeException r) {
//                Log.d("Recorder", "RuntimeException: stop() is called immediately after start()");
//            } catch (IOException e) {
//                e.printStackTrace();
//            } finally {
//                releaseMediaRecorder();
//            }
//        }
//    }
//
//    public void cancelPaly(boolean deleteFile) {
//        releaseMediaPlayer();
//        if (deleteFile) {
//            deleteTargetFile();
//        }
//        imageView.setVisibility(View.GONE);
//        mSurfaceView.setVisibility(View.GONE);
//        //恢复SurfaceView大小
//        ViewGroup.LayoutParams lp = mSurfaceView.getLayoutParams();
//        lp.width = ViewGroup.LayoutParams.MATCH_PARENT;
//        lp.height = ViewGroup.LayoutParams.MATCH_PARENT;
//        mSurfaceView.setVisibility(View.VISIBLE);
//        currentPlayPosition = 0;
//        if (mSurfaceHolder != null) {
//            startPreView(mSurfaceHolder);
//        }
//    }
//
//    public void stopRecordUnSave() {
//        Log.d("Recorder", "stopRecordUnSave");
//        if (isRecording) {
//            isRecording = false;
//            try {
//                mMediaRecorder.stop();
//            } catch (RuntimeException r) {
//                Log.d("Recorder", "RuntimeException: stop() is called immediately after start()");
//            } finally {
//                releaseMediaRecorder();
//                //删除视频文件
//                File video = new File(targetVideoDir, targetVideoName);
//                if (video.exists()) {
//                    video.delete();
//                }
//            }
//        }
//    }
//
//    private void startPreView(SurfaceHolder holder) {
//        if (mCamera == null) {
//            mCamera = Camera.open(cameraPosition);//根据用户的切换选择摄像头
//        }
//        if (mCamera != null) {
//            Camera.CameraInfo info = new Camera.CameraInfo();
//            Camera.getCameraInfo(cameraPosition, info);
//            if (info.facing == Camera.CameraInfo.CAMERA_FACING_FRONT) {
//                if (info.orientation == orientation_270) {
//                    mCamera.setDisplayOrientation(orientation_90);
//                }
//            } else {
//                if (info.orientation == orientation_90) {
//                    mCamera.setDisplayOrientation(orientation_90);
//                } else if (info.orientation == orientation_270) {
//                    mCamera.setDisplayOrientation(orientation_270);
//                }
//            }
//            try {
//                mCamera.setPreviewDisplay(holder);
//                Camera.Parameters parameters = mCamera.getParameters();
////                List<Camera.Size> mSupportedPreviewSizes = parameters.getSupportedPreviewSizes();
////                List<Camera.Size> mSupportedVideoSizes = parameters.getSupportedVideoSizes();
////                Camera.Size optimalSize = CameraHelper.getOptimalVideoSize(mSupportedVideoSizes,
////                        mSupportedPreviewSizes, mSurfaceView.getWidth(), mSurfaceView.getHeight());
//                // Use the same size for recording profile.
////                previewWidth = 1920;
////                previewHeight = 1080;
//                parameters.setPreviewSize(previewWidth, previewHeight);
//                profile = CamcorderProfile.get(CamcorderProfile.QUALITY_HIGH);
//                profile.videoCodec = MediaRecorder.VideoEncoder.H264;
//                // 这里是重点，分辨率和比特率
//                // 分辨率越大视频大小越大，比特率越大视频越清晰
//                // 清晰度由比特率决定，视频尺寸和像素量由分辨率决定
//                // 比特率越高越清晰（前提是分辨率保持不变），分辨率越大视频尺寸越大。
//                profile.videoFrameWidth = previewWidth;
//                profile.videoFrameHeight = previewHeight;
//                // 这样设置 1080p的视频 大小在5M , 可根据自己需求调节
//                profile.videoBitRate = 3 * previewWidth * previewHeight;
//                List<String> focusModes = parameters.getSupportedFocusModes();
//                if (focusModes.contains(Camera.Parameters.FOCUS_MODE_CONTINUOUS_VIDEO)) {
//                    //支持连续自动对焦模式
//                    parameters.setFocusMode(Camera.Parameters.FOCUS_MODE_CONTINUOUS_VIDEO);
//                }
//                mCamera.setParameters(parameters);
//                mCamera.startPreview();
//            } catch (IOException e) {
//                e.printStackTrace();
//            }
//        }
//    }
//
//    private void releaseMediaRecorder() {
//        if (mMediaRecorder != null) {
//            // clear recorder configuration
//            mMediaRecorder.reset();
//            // release the recorder object
//            mMediaRecorder.release();
//            mMediaRecorder = null;
//            Log.d("Recorder", "release Recorder");
//        }
//    }
//
//    public void release() {
//        releaseMediaPlayer();
//        releaseCamera();
//        releaseMediaRecorder();
//    }
//
//    private void releaseMediaPlayer() {
//        if (mediaPlayer != null) {
//            if (mediaPlayer.isPlaying()) {
//                mediaPlayer.stop();//停止播放
//            }
//            // release the mediaPlayer
//            mediaPlayer.release();
//            mediaPlayer = null;
//            Log.d("Recorder", "release mediaPlayer");
//        }
//    }
//
//    private void releaseCamera() {
//        if (mCamera != null) {
//            // release the camera for other applications
//            mCamera.release();
//            mCamera = null;
//            Log.d("Recorder", "release Camera");
//        }
//    }
//
//    @Override
//    public void surfaceCreated(SurfaceHolder holder) {
//        mSurfaceHolder = holder;
//        if (currentPlayPosition > 0) {
//            onResume();
//        } else {
//            startPreView(holder);
//        }
//    }
//
//    @Override
//    public void surfaceChanged(SurfaceHolder holder, int format, int width, int height) {
//
//    }
//
//    @Override
//    public void surfaceDestroyed(SurfaceHolder holder) {
//        if (mCamera != null) {
//            Log.d(TAG, "surfaceDestroyed: ");
//            releaseCamera();
//        }
//        if (mMediaRecorder != null) {
//            releaseMediaRecorder();
//        }
//        if (mediaPlayer != null && mediaPlayer.isPlaying()) {
//            mediaPlayer.pause();
//            currentPlayPosition = mediaPlayer.getCurrentPosition();
//            releaseMediaPlayer();
//        }
//    }
//
//    private void startRecordThread() {
//        if (prepareRecord()) {
//            try {
//                mMediaRecorder.start();
//                isRecording = true;
//                Log.d("Recorder", "Start Record");
//            } catch (RuntimeException r) {
//                releaseMediaRecorder();
//                Log.d("Recorder", "RuntimeException: start() is called immediately after stop()");
//            }
//        }
//    }
//
//    public void setImageView(ImageView imageView) {
//        this.imageView = imageView;
//    }
//
//    public void setCompleteListener(CompleteListener completeListener) {
//        this.completeListener = completeListener;
//    }
//
//    private class ZoomGestureListener extends GestureDetector.SimpleOnGestureListener {
//        //双击手势事件
//        @Override
//        public boolean onDoubleTap(MotionEvent e) {
//            super.onDoubleTap(e);
//            Log.d(TAG, "onDoubleTap: 双击事件");
//            if (!isZoomIn) {
//                setZoom(10);
//                isZoomIn = true;
//            } else {
//                setZoom(0);
//                isZoomIn = false;
//            }
//            return true;
//        }
//
//    }
//
//    private void setZoom(int zoomValue) {
//        if (mCamera != null) {
//            Camera.Parameters parameters = mCamera.getParameters();
//            if (parameters.isZoomSupported()) {
//                int maxZoom = parameters.getMaxZoom();
//                if (maxZoom == 0) {
//                    return;
//                }
//                if (zoomValue > maxZoom) {
//                    zoomValue = maxZoom;
//                }
//                parameters.setZoom(zoomValue);
//                mCamera.setParameters(parameters);
//            }
//        }
//    }
//
//    public void switchCamera() {
//        int cameraCount = 0;
//        Camera.CameraInfo cameraInfo = new Camera.CameraInfo();
//        cameraCount = Camera.getNumberOfCameras();//得到摄像头的个数
//
//        for (int i = 0; i < cameraCount; i++) {
//            Camera.getCameraInfo(i, cameraInfo);//得到每一个摄像头的信息
//            if (cameraPosition == Camera.CameraInfo.CAMERA_FACING_BACK) {
//                //现在是后置，变更为前置
//                if (cameraInfo.facing == Camera.CameraInfo.CAMERA_FACING_FRONT) {//代表摄像头的方位，CAMERA_FACING_FRONT前置      CAMERA_FACING_BACK后置
//                    mCamera.stopPreview();//停掉原来摄像头的预览
//                    mCamera.release();//释放资源
//                    mCamera = null;//取消原来摄像头
//                    mCamera = Camera.open(i);//打开当前选中的摄像头
//                    cameraPosition = Camera.CameraInfo.CAMERA_FACING_FRONT;
//                    startPreView(mSurfaceHolder);
//                    break;
//                }
//            } else {
//                //现在是前置， 变更为后置
//                if (cameraInfo.facing == Camera.CameraInfo.CAMERA_FACING_BACK) {//代表摄像头的方位，CAMERA_FACING_FRONT前置      CAMERA_FACING_BACK后置
//                    mCamera.stopPreview();//停掉原来摄像头的预览
//                    mCamera.release();//释放资源
//                    mCamera = null;//取消原来摄像头
//                    mCamera = Camera.open(i);//打开当前选中的摄像头
//                    cameraPosition = Camera.CameraInfo.CAMERA_FACING_BACK;
//                    startPreView(mSurfaceHolder);
//                    break;
//                }
//            }
//        }
//    }
//
//    interface CompleteListener {
//        void onComplete();
//    }
//}