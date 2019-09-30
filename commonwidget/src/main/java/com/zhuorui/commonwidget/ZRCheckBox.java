package com.zhuorui.commonwidget;

import android.content.Context;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.PixelFormat;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.util.AttributeSet;
import android.util.Log;
import androidx.appcompat.widget.AppCompatCheckBox;

/**
 * author : liuwei
 * e-mail : vsanliu@foxmail.com
 * date   : 2019-08-29 16:37
 * desc   :
 */
public class ZRCheckBox extends AppCompatCheckBox {

    private int w;
    private int h;
    private Drawable mButtonDrawable;

    public ZRCheckBox(Context context) {
        this(context, null);
    }

    public ZRCheckBox(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public ZRCheckBox(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        w = h = (int) (getResources().getDisplayMetrics().density * 20);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            setButtonDrawable(getButtonDrawable());
            invalidate();
        } else if (mButtonDrawable != null) {
            setButtonDrawable(mButtonDrawable);
            invalidate();
        }
    }

    @Override
    public void setButtonDrawable(int resId) {
        if (resId != 0) {
            mButtonDrawable = zoomImage(resId, w, h);
            super.setButtonDrawable(mButtonDrawable);
        } else {
            super.setButtonDrawable(resId);
        }
    }

    @Override
    public void setButtonDrawable(Drawable drawable) {
        if (drawable != null && w > 0 && h > 0) {
            mButtonDrawable = zoomImage(drawableToBitmap(drawable), w, h);
            super.setButtonDrawable(mButtonDrawable);
        } else {
            super.setButtonDrawable(drawable);

        }
    }

    /**
     * 将本地资源图片大小缩放
     *
     * @param resId
     * @param w
     * @param h
     * @return
     */
    public Drawable zoomImage(int resId, int w, int h) {
        Resources res = getResources();
        Bitmap oldBmp = BitmapFactory.decodeResource(res, resId);
        return zoomImage(oldBmp, w, h);
    }

    /**
     * 将本地资源图片大小缩放
     *
     * @param w
     * @param h
     * @return
     */
    public Drawable zoomImage(Bitmap oldBmp, int w, int h) {
        Log.i("lw", "zoomImage: " + w + " " + h);
        Resources res = getResources();
        Bitmap newBmp = Bitmap.createScaledBitmap(oldBmp, w, h, true);
        Drawable drawable = new BitmapDrawable(res, newBmp);
        return drawable;
    }

    /**
     * 将Drawable转换为Bitmap
     *
     * @param drawable
     * @return
     */
    private Bitmap drawableToBitmap(Drawable drawable) {
        //取drawable的宽高
        int width = drawable.getIntrinsicWidth();
        int height = drawable.getIntrinsicHeight();
        //取drawable的颜色格式
        Bitmap.Config config = drawable.getOpacity() != PixelFormat.OPAQUE
                ? Bitmap.Config.ARGB_8888
                : Bitmap.Config.RGB_565;
        //创建对应的bitmap
        Bitmap bitmap = Bitmap.createBitmap(width, height, config);
        //创建对应的bitmap的画布
        Canvas canvas = new Canvas(bitmap);
        drawable.setBounds(0, 0, width, height);
        Log.i("lw", "drawableToBitmap: " + width + " " + height);
        //把drawable内容画到画布中
        drawable.draw(canvas);
        return bitmap;
    }
}
