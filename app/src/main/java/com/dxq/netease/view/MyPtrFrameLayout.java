package com.dxq.netease.view;

import android.content.Context;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.ViewConfiguration;

import in.srain.cube.views.ptr.PtrClassicFrameLayout;

/**
 * Created by CREEPER_D on 2017/7/25.
 */

public class MyPtrFrameLayout extends PtrClassicFrameLayout {

    float mStartX = 0;
    float mStartY = 0;

    float mSumX = 0;
    float mSumY = 0;

    public MyPtrFrameLayout(Context context) {
        super(context);
    }

    public MyPtrFrameLayout(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    @Override
    public boolean dispatchTouchEvent(MotionEvent e) {
        switch (e.getAction()) {
            case MotionEvent.ACTION_DOWN:
                mStartX = e.getX();
                mStartY = e.getY();

                mSumX = 0;
                mSumY = 0;
                break;
            case MotionEvent.ACTION_MOVE:
                float newX = e.getX();
                float newY = e.getY();
                float dx = newX - mStartX;
                float dy = newY - mStartY;

                mSumX += dx;
                mSumY += dy;
                if (Math.abs(mSumX) > ViewConfiguration.getTouchSlop() && Math.abs(mSumX) > Math.abs(mSumY)) {
                    return dispatchTouchEventSupper(e);
                }

                mStartX = newX;
                mStartY = newY;
                break;
            case MotionEvent.ACTION_UP:
                break;
        }
        return super.dispatchTouchEvent(e);
    }
}
