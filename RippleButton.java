package com.mnnyang.ripples.widget;


import android.content.Context;
import android.graphics.Canvas;
import android.graphics.drawable.Drawable;
import android.support.annotation.NonNull;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.widget.Button;

/**
 * Created by mnnyang on 17-3-23.
 */

public class RippleButton extends Button {

    RippleDrawable rippleDrawable;

    public RippleButton(Context context) {
        this(context, null);
    }

    public RippleButton(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public RippleButton(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);

        rippleDrawable = new RippleDrawable();

        //设置刷新借口, view中已经实现
        rippleDrawable.setCallback(this);
    }

    //验证
    @Override
    protected boolean verifyDrawable(@NonNull Drawable who) {
        //验证drawable是否ok
        return who == rippleDrawable || super.verifyDrawable(who);

    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);

        //设投资drawable绘制和刷新的区域
        rippleDrawable.setBounds(0, 0, getWidth(), getHeight());

    }

    @Override
    protected void onDraw(Canvas canvas) {

        rippleDrawable.draw(canvas);

        super.onDraw(canvas);
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        rippleDrawable.onTouch(event);
        return super.onTouchEvent(event);
    }


}
