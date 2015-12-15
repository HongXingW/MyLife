package com.mylife.materialdesign;

import android.graphics.Bitmap;
import android.graphics.BitmapShader;
import android.graphics.Canvas;
import android.graphics.ColorFilter;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.PixelFormat;
import android.graphics.RectF;
import android.graphics.Shader.TileMode;
import android.graphics.drawable.Drawable;
import android.util.Log;

/**
 * Created by whx on 2015/9/25.
 */

public class CircleImageDrawable extends Drawable
{

    private Paint mPaint;
    private int mWidth;
    private Bitmap mBitmap ;
    static final float toolBarHeight = 45;

    public CircleImageDrawable(Bitmap bitmap)
    {
        mBitmap = bitmap ;
        BitmapShader bitmapShader = new BitmapShader(bitmap, TileMode.CLAMP,
                TileMode.CLAMP);
        mPaint = new Paint();
        mPaint.setAntiAlias(true);
        mPaint.setShader(bitmapShader);
        mWidth = Math.min(mBitmap.getWidth(), mBitmap.getHeight());
    }

    static Drawable zoomDrawable(Bitmap bitmap){
        int width = bitmap.getWidth();
        int height= bitmap.getHeight();

        Matrix matrix = new Matrix();   // 创建操作图片用的 Matrix 对象
        float scaleWidth = (toolBarHeight/(float)width);   // 计算缩放比例
        float scaleHeight = (toolBarHeight/(float)height);
        matrix.postScale(scaleWidth, scaleHeight);         // 设置缩放比例
        // 建立新的 bitmap ，其内容是对原 bitmap 的缩放后的图
        Bitmap newbmp = Bitmap.createBitmap(bitmap, 0, 0, width, height, matrix, true);

        // 把 bitmap 转换成 drawable 并返回
        return new CircleImageDrawable(newbmp);
    }
    @Override
    public void draw(Canvas canvas)
    {
        canvas.drawCircle(mWidth / 2, mWidth / 2, mWidth / 2, mPaint);
    }

    @Override
    public int getIntrinsicWidth()
    {
        return mWidth;
    }

    @Override
    public int getIntrinsicHeight()
    {
        return mWidth;
    }

    @Override
    public void setAlpha(int alpha)
    {
        mPaint.setAlpha(alpha);
    }

    @Override
    public void setColorFilter(ColorFilter cf)
    {
        mPaint.setColorFilter(cf);
    }

    @Override
    public int getOpacity()
    {
        return PixelFormat.TRANSLUCENT;
    }

}
