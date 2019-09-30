package com.zhuorui.commonwidget;

import android.content.Context;
import android.content.Intent;
import android.content.res.TypedArray;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.util.AttributeSet;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;
import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.zhuorui.commonwidget.dialog.GetPicturesModeDialog;
import com.zhuorui.commonwidget.impl.IImageUploader;
import com.zhuorui.commonwidget.impl.OnImageUploaderListener;
import com.zhuorui.securities.base2app.util.ResUtil;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;


/**
 * author : liuwei
 * e-mail : vsanliu@foxmail.com
 * date   : 2019-08-23 15:34
 * desc   : 上传图片View
 */
public class ZRUploadImageView extends FrameLayout implements View.OnClickListener, GetPicturesModeDialog.OnGetPicturesModeListener, OnImageUploaderListener {

    private TextView vTitle;
    private TextView vBtnText;
    private View vBtn;
    private ImageView vImg;
    private ImageView vRBtn;
    private TextView vMask;
    private ZRLoadingView vLoading;
    private ZRRotateTextView vWatermark;
    private String mBtnText;
    private Drawable mPlaceholder;
    private GetPicturesModeDialog dialog;
    private OnUploadImageListener mListener;
    private IImageUploader mIUploader;
    private String mFileName;
    private int mAlbumRequestCode = 1;
    private int mCameraRequestCode = 2;
    private final int MASK_BG_ERROR = R.drawable.rectangle_solid80000000_strokeca0000;
    private final int MASK_BG_SUCCESS = R.drawable.rectangle_solid80000000_stroke1a6ed2;
    private final int MASK_BG_NORMAL = R.drawable.rectangle_dottedline616161;
    private int mStatus = 0;//0 初始状态 1 上传中状态 2上传成功 3上传失败
    private String mPath;

    public ZRUploadImageView(Context context) {
        this(context, null);
    }

    public ZRUploadImageView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public ZRUploadImageView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        inflate(getContext(), R.layout.layout_uplod_image_view, this);
        initView();
        TypedArray a = getContext().obtainStyledAttributes(attrs, R.styleable.ZRUploadImageView);
        String title = a.getString(R.styleable.ZRUploadImageView_zr_upimageviewTitle);
        String watermarkTxt = a.getString(R.styleable.ZRUploadImageView_zr_upimageviewWatermark);
        mBtnText = a.getString(R.styleable.ZRUploadImageView_zr_upimageviewBtnText);
        mPlaceholder = a.getDrawable(R.styleable.ZRUploadImageView_zr_upimageviewPlaceholder);
        mAlbumRequestCode = a.getInt(R.styleable.ZRUploadImageView_zr_toAlbumRequestCode, mAlbumRequestCode);
        mCameraRequestCode = a.getInt(R.styleable.ZRUploadImageView_zr_toCameraRequestCode, mCameraRequestCode);
        setTitle(title);
        setButtonText(mBtnText);
        setPlaceholder(mPlaceholder);
        setWatermark(watermarkTxt);
        mFileName = String.valueOf(hashCode()) + ".jpg";
        a.recycle();
    }

    private void initView() {
        vTitle = findViewById(R.id.tv_title);
        vBtnText = findViewById(R.id.tv_btn_text);
        vBtn = findViewById(R.id.sb_btn);
        vImg = findViewById(R.id.iv_image);
        vMask = findViewById(R.id.v_mask);
        vWatermark = findViewById(R.id.iv_watermark);
        vLoading = findViewById(R.id.loading);
        vRBtn = findViewById(R.id.iv_rbtn);
        vBtn.setOnClickListener(this);
    }

    public void setPlaceholder(Drawable drawable) {
        mPlaceholder = drawable;
        vImg.setImageDrawable(drawable);
    }

    public void setWatermark(String watermarkTxt) {
        vWatermark.setText(watermarkTxt);
    }

    public void setButtonText(String mBtnText) {
        vBtnText.setText(mBtnText);
    }

    public void setTitle(String title) {
        vTitle.setText(title);
    }

    @Override
    public void onClick(View view) {
        if (view == vBtn) {
            if (dialog == null) {
                dialog = new GetPicturesModeDialog(view.getContext());
                dialog.setListener(this);
            }
            dialog.show();
        }
    }

    @Override
    public int getToAlbumRequestCode() {
        return mAlbumRequestCode;
    }

    @Override
    public int getToCameraRequestCode() {
        return mCameraRequestCode;
    }

    @NotNull
    @Override
    public String getCameraSavePath() {
        return getContext().getExternalCacheDir().getAbsolutePath() + "/" + mFileName;
    }

    @Override
    public void onPicturePath(@NotNull String path) {
        setFilePath(path);
        if (mIUploader != null) {
            changeUi(1);
            mIUploader.upLoad(path);
        } else {
            changeUi(2);
            mListener.onPicturePath(this, mPath);
        }
    }

    private void changeUi(int status) {
        changeUi(status, "");
    }

    private void changeUi(int status, String msg) {
        if (mStatus == status) return;
        mStatus = status;
        switch (mStatus) {
            case 0:
                vMask.setVisibility(VISIBLE);
                vMask.setText("");
                vMask.setBackgroundResource(MASK_BG_NORMAL);
                vImg.setImageDrawable(mPlaceholder);
                vLoading.setVisibility(GONE);
                vLoading.stopAnimation();
                vWatermark.setVisibility(GONE);
                vRBtn.setVisibility(GONE);
                break;
            case 1:
                vMask.setVisibility(GONE);
                vLoading.setVisibility(VISIBLE);
                vLoading.setMessage(ResUtil.INSTANCE.getString(R.string.upload_waiting));
                vLoading.startAnimation();
                vWatermark.setVisibility(GONE);
                vRBtn.setVisibility(GONE);
                break;
            case 2:
                vMask.setVisibility(VISIBLE);
                vMask.setText("");
                vMask.setBackgroundResource(MASK_BG_SUCCESS);
                vLoading.setVisibility(GONE);
                vLoading.stopAnimation();
                vWatermark.setVisibility(VISIBLE);
                vRBtn.setVisibility(VISIBLE);
                vRBtn.setImageResource(R.mipmap.ic_select_bule);
                break;
            case 3:
                vMask.setVisibility(VISIBLE);
                vMask.setText(msg);
                vMask.setBackgroundResource(MASK_BG_ERROR);
                vLoading.setVisibility(GONE);
                vLoading.stopAnimation();
                vWatermark.setVisibility(GONE);
                vRBtn.setVisibility(VISIBLE);
                vRBtn.setImageResource(R.mipmap.ic_delete_red);
                break;
        }
    }

    public void setFilePath(String path) {
        mPath = path;
        vBtnText.setText(ResUtil.INSTANCE.getString(R.string.str_reshooting));
        Glide.with(vImg).load(path).placeholder(mPlaceholder).error(mPlaceholder).centerCrop().diskCacheStrategy(DiskCacheStrategy.NONE).into(vImg);
    }

    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (dialog != null) dialog.onActivityResult(requestCode, resultCode, data);
    }

    @Override
    public void goCamera(@Nullable Integer toCameraRequestCode, @Nullable Uri uri) {
        if (mListener != null) mListener.goCamera(toCameraRequestCode, uri);
    }

    @Override
    public void goAlbum(@Nullable Integer toAlbumRequestCode) {
        if (mListener != null) mListener.goAlbum(toAlbumRequestCode);
    }

    public void setOnUploadImageListener(OnUploadImageListener l) {
        mListener = l;
    }

    /**
     * 设置图片上传器
     *
     * @param iUploader
     */
    public void setUploader(IImageUploader iUploader) {
        mIUploader = iUploader;
        if (mIUploader != null) {
            mIUploader.setOnUploaderListener(this);
        }
    }

    @Override
    public void onFail(String msg) {
        changeUi(3);
        mListener.onPicturePath(this, null);
    }


    @Override
    public void onSuccess() {
        changeUi(2);
        mListener.onPicturePath(this, mPath);
    }

    @NotNull
    public String getPath() {
        return mStatus == 2 ? mPath : null;
    }

    public interface OnUploadImageListener {
        void goCamera(int requestCode, Uri uri);

        void goAlbum(int requestCode);

        /**
         * 回调选择地图片地址，设置了上传器，会等上传结果再返回
         *
         * @param v
         * @param path
         */
        void onPicturePath(ZRUploadImageView v, String path);
    }


}
