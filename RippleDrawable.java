package com.mnnyang.ripples.widget;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.ColorFilter;
import android.graphics.LightingColorFilter;
import android.graphics.Paint;
import android.graphics.PixelFormat;
import android.graphics.drawable.Drawable;
import android.support.annotation.IntRange;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.MotionEvent;

/**
 * Created by mnnyang on 17-3-23.
 */

public class RippleDrawable extends Drawable {

    private Paint paint = new Paint(Paint.ANTI_ALIAS_FLAG);

    private Bitmap bitmap;


    @Override
    public int getAlpha() {
        return alpha;
    }

    /**
     * 透明度
     */
    private int alpha = 100;
    private int rippleColor = 0;

    //圆坐标半径
    private float mRipplePointX, mRipplePointY;

    private static float maxRadius;
    private static float minRadius = 30;

    private float mRippleRadius = minRadius;


    public RippleDrawable() {
        paint.setAntiAlias(true);
        paint.setDither(true);//防抖动

        setRippleColor(Color.BLUE);
    }

    public RippleDrawable(Bitmap bitmap) {
        this();
        this.bitmap = bitmap;
        /*滤镜 参数：   保留颜色  填充颜色*/
        setColorFilter(new LightingColorFilter(0xFFFF0000, 0x0033000000));
    }

    @Override
    public void setBounds(int left, int top, int right, int bottom) {
        super.setBounds(left, top, right, bottom);
        maxRadius = right - left > bottom - top ? right - left + 100 : bottom - top + 100;
//        maxRadius = 500;

    }

    @Override
    public void draw(@NonNull Canvas canvas) {
//        画颜色
//        canvas.drawColor(Color.RED);
//        canvas.drawBitmap(bitmap, 50, 50, paint);
//        画圆
        canvas.drawCircle(mRipplePointX, mRipplePointY, mRippleRadius, paint);
        if (mRippleRadius < maxRadius) {
            mRippleRadius += minRadius;
            invalidateSelf();
        }
    }

    public void setRippleColor(int color) {
        rippleColor = color;
        onColorOrAlphaChange();
    }


    @Override
    public void setAlpha(@IntRange(from = 0, to = 255) int alpha) {
        this.alpha = alpha;
        onColorOrAlphaChange();
    }

    private void onColorOrAlphaChange() {
        paint.setColor(rippleColor);
        System.out.println("color=" + paint.getColor());

        if (this.alpha != 255) {
            //得到颜色本身的透明度
            int pAlpha = paint.getAlpha();

            int realAlpha = (int) (pAlpha * (this.alpha / 255f));

            //把真是的颜色设置回去
            paint.setAlpha(realAlpha);

        }

        //刷新当前drawable
        invalidateSelf();
    }

    @Override
    public void setColorFilter(@Nullable ColorFilter colorFilter) {

        if (paint.getColorFilter() != colorFilter) {
            paint.setColorFilter(colorFilter);
        }

        invalidateSelf();
    }

    @Override
    public int getOpacity() {

        int alpha = paint.getAlpha();

        if (alpha == 255) {
            return PixelFormat.OPAQUE;
        } else if (alpha == 0) {
            return PixelFormat.TRANSPARENT;
        } else {
            //半透明
            return PixelFormat.TRANSLUCENT;
        }
    }


    public void onTouch(MotionEvent event) {

        switch (event.getActionMasked()) {
            case MotionEvent.ACTION_DOWN:
                onTouchDown(event.getX(), event.getY());
                break;
            case MotionEvent.ACTION_MOVE:
                onTouchMove(event.getX(), event.getY());
                break;
            case MotionEvent.ACTION_UP:
                onTouchUp(event.getX(), event.getY());
                break;
            case MotionEvent.ACTION_CANCEL:
                onTouchCancel(event.getX(), event.getY());
                break;
        }

        invalidateSelf();
    }

    private void onTouchCancel(float x, float y) {
    }

    private void onTouchUp(float x, float y) {
    }

    private void onTouchMove(float x, float y) {

    }

    private void onTouchDown(float x, float y) {
        mRipplePointX = x;
        mRipplePointY = y;
        mRippleRadius = 0;
    }
}
