package com.dxq.netease.view;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.RectF;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;

/**
 * Created by CREEPER_D on 2017/7/20.
 */

public class SkipVIew extends View {

    private static final float TEXT_SIZE = 10;
    private static final float ARC_WIDTH = 2;
    private static final int TEXT_MARGIN = 10;
    private static final long REFLASH_TIME = 100;
    private Paint mTextPaint;
    private Paint mCirclePaint;
    private Paint mArcPaint;
    private float mMeasureTextWidth;
    private float mCircleDoubleRadius;
    private float mArcDoubleRadius;
    private RectF rectF;
    private Handler mHandler;
    private float currentTime = 0;
    private float totalShowTime = 2500;

    public SkipVIew(Context context) {
        super(context);
    }

    public SkipVIew(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        initView();
    }

    private void initView() {
        //文字的画笔
        mTextPaint = new Paint();
        mTextPaint.setTextSize(TEXT_SIZE);
        mTextPaint.setColor(Color.WHITE);
        mTextPaint.setAntiAlias(true);

        //中间的圆的画笔
        mCirclePaint = new Paint();
        mCirclePaint.setColor(Color.GRAY);
        mCirclePaint.setAntiAlias(true);

        //外部圆弧的画笔
        mArcPaint = new Paint();
        mArcPaint.setColor(Color.RED);
        mArcPaint.setAntiAlias(true);
        mArcPaint.setStyle(Paint.Style.STROKE);
        mArcPaint.setStrokeWidth(ARC_WIDTH);

        /**
         * 1.文字的宽度
         * 2.文字距离中间圆边框的间距
         * 3.外部圆弧的笔画宽度
         *
         */
        mMeasureTextWidth = mTextPaint.measureText("跳过");

        //计算中圆的直径
        mCircleDoubleRadius = mMeasureTextWidth + TEXT_MARGIN * 2;

        //计算外部圆弧的直径
        mArcDoubleRadius = mCircleDoubleRadius + ARC_WIDTH * 2;

        //准备一个矩形用来画外部圆弧
        rectF = new RectF(0 + ARC_WIDTH / 2, 0 + ARC_WIDTH / 2, mArcDoubleRadius - ARC_WIDTH / 2, mArcDoubleRadius - ARC_WIDTH / 2);

        mHandler = new Handler() {
            @Override
            public void handleMessage(Message msg) {
                super.handleMessage(msg);
                //接收到消息会更新一下,重绘角度
                currentTime += REFLASH_TIME;
                //角度百分百时,不再发信息
                if (currentTime > totalShowTime) {
                    if (mOnSkipListener != null) {
                        mOnSkipListener.onSkip();
                    }
                    return;
                }
                invalidate();
                //继续发延时信息给自己
                this.sendEmptyMessageDelayed(0, REFLASH_TIME);
            }
        };
    }

    public void start() {
        mHandler.sendEmptyMessageDelayed(0, REFLASH_TIME);
    }

    public void stop() {
        mHandler.removeCallbacksAndMessages(null);
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:
                setAlpha(0.5f);
                break;
            case MotionEvent.ACTION_MOVE:
                break;
            case MotionEvent.ACTION_UP:
                //移除handler的未处理信息
                mHandler.removeCallbacksAndMessages(null);
                setAlpha(1.0f);
                if (mOnSkipListener != null) {
                    mOnSkipListener.onSkip();
                }
                break;
        }
        return true;
    }

    /**
     * 定义一个接口
     */
    public interface OnSkipListener {
        void onSkip();
    }

    private OnSkipListener mOnSkipListener;

    public void setmOnSkipListener(OnSkipListener listener) {
        this.mOnSkipListener = listener;
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        //外部圆弧
        canvas.save();
        canvas.rotate(-90, getMeasuredWidth() / 2, getMeasuredHeight() / 2);
        float angle = currentTime / totalShowTime * 360;
        canvas.drawArc(rectF, 0, angle, false, mArcPaint);
        canvas.restore();

        //中间的圆
        canvas.drawCircle(getMeasuredWidth() / 2, getMeasuredHeight() / 2, mCircleDoubleRadius / 2, mCirclePaint);

        //内部的文字
        Paint.FontMetrics fontMetrics = mTextPaint.getFontMetrics();
        float ascent = fontMetrics.ascent;
        float descent = fontMetrics.descent;
        float top = fontMetrics.top;
        float bottom = fontMetrics.bottom;
        Log.d(getClass().getSimpleName() + "dxq", "onDraw: " + ascent + " " + descent + " " + top + " " + bottom);
        float baseLine = getMeasuredHeight() / 2 - (top + bottom) / 2;
        canvas.drawText("跳过", getMeasuredWidth() / 2 - mMeasureTextWidth / 2, baseLine, mTextPaint);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        int widthMode = MeasureSpec.getMode(widthMeasureSpec);
        int widthSize = MeasureSpec.getSize(widthMeasureSpec);
        int heightMode = MeasureSpec.getMode(heightMeasureSpec);
        int heightSize = MeasureSpec.getSize(heightMeasureSpec);

        if (widthMode == MeasureSpec.AT_MOST) {
            widthSize = (int) mArcDoubleRadius;
        }
        if (heightMode == MeasureSpec.AT_MOST) {
            heightSize = (int) mArcDoubleRadius;
        }

        setMeasuredDimension(widthSize, heightSize);
    }
}
