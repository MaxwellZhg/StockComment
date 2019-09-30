package com.zhuorui.securities.openaccount.widget;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.Path;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;
import com.zhuorui.securities.base2app.util.ResUtil;
import com.zhuorui.securities.openaccount.R;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

/**
 * author : PengXianglin
 * e-mail : peng_xianglin@163.com
 * date   : 2019/8/28 11:24
 * desc   : 手写电子签名View
 */
public class SignatureView extends View {
    private static final String TAG = "SignatureView";
    private Paint mPathPaint;
    private Paint mBitmapPaint;
    private Canvas mCanvas;
    private Path mPath;
    private Bitmap mBitmap;

    /**
     * 画图区域的最大位置
     */
    private float mSmallX = 0, mSmallY = 0, mBigX = 0, mBigY = 0;
    private float mPreX, mPreY;

    public SignatureView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    private void init() {
        mBitmapPaint = new Paint(Paint.DITHER_FLAG);
        mPathPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        mPathPaint.setDither(true);
        mPathPaint.setStyle(Paint.Style.STROKE);
        mPathPaint.setStrokeWidth(ResUtil.INSTANCE.getDimensionDp2Px(8f));
        mPathPaint.setColor(ResUtil.INSTANCE.getColor(R.color.color_282828));
        mPathPaint.setStrokeJoin(Paint.Join.ROUND);//线段结束处的形状
        mPathPaint.setStrokeCap(Paint.Cap.ROUND);//线段开始结束处的形状
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        resetSign();
    }

    private void resetSign() {
        mPath = new Path();
        mBitmap = Bitmap.createBitmap(getMeasuredWidth(), getMeasuredHeight(), Bitmap.Config.ARGB_8888);
        mBitmap.eraseColor(Color.WHITE);
        mCanvas = new Canvas(mBitmap);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        canvas.drawBitmap(mBitmap, 0, 0, mBitmapPaint);
        canvas.drawPath(mPath, mPathPaint);
    }

    /**
     * 计算画图区域的位置
     *
     * @param x
     * @param y
     */
    private void computeDrawMaxRang(float x, float y) {
        if (mSmallX == 0 && mSmallY == 0 && mBigX == 0 && mBigY == 0) {
            mSmallX = mBigX = x;
            mBigY = mSmallY = y;
        } else {
            mSmallX = Math.min(mSmallX, x);
            mSmallY = Math.min(mSmallY, y);
            mBigX = Math.max(mBigX, x);
            mBigY = Math.max(mBigY, y);
        }
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:
                mPreX = event.getX();
                mPreY = event.getY();
                computeDrawMaxRang(mPreX, mPreY);
                mPath.reset();
                mPath.moveTo(mPreX, mPreY);
                break;
            case MotionEvent.ACTION_MOVE:
                float x = event.getX();
                float y = event.getY();
                computeDrawMaxRang(x, y);
                //绘制圆滑曲线（贝塞尔曲线）
                mPath.quadTo(mPreX, mPreY, x, y);
                mPreX = x;
                mPreY = y;
                break;
            case MotionEvent.ACTION_UP:
                computeDrawMaxRang(event.getX(), event.getY());
                mPath.lineTo(event.getX(), event.getY());
                mCanvas.drawPath(mPath, mPathPaint);
                break;
        }
        invalidate();
        return true;
    }

    /**
     * 保存bitmap到文件
     *
     * @param fileapth
     * @return 成功返回true，反之false
     */
    @SuppressLint("WrongThread")
    public boolean saveBitmapToFile(String fileapth) {
        File file = new File(fileapth);
        if (file.exists()) {
            float signAreaWidth = mBigX - mSmallX;
            float signAreaHeight = mBigY - mSmallY;
            Bitmap bitmap = Bitmap.createBitmap(mBitmap, (int) mSmallX, (int) mSmallY, (int) signAreaWidth, (int) signAreaHeight, new Matrix(), true);
            FileOutputStream fos = null;
            try {
                fos = new FileOutputStream(file);
                bitmap.compress(Bitmap.CompressFormat.JPEG, 100, fos);
                return true;
            } catch (FileNotFoundException e) {
                e.printStackTrace();
                return false;
            } finally {
                try {
                    fos.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        } else
            return false;
    }

    public Bitmap getBitmap() {
        return mBitmap;
    }

    public void reset() {
        resetSign();
        invalidate();
    }
}