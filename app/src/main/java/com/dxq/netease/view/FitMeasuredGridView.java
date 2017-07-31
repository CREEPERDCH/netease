package com.dxq.netease.view;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.GridView;

/**
 * Created by CREEPER_D on 2017/7/29.
 */

public class FitMeasuredGridView extends GridView {
    public FitMeasuredGridView(Context context) {
        super(context);
    }

    public FitMeasuredGridView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    //测量方法  修正在UNSPECIFIED模式下,展示只有一行item的BUG

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        int heightMode = MeasureSpec.getMode(heightMeasureSpec);
        int heightSize = MeasureSpec.getSize(heightMeasureSpec);
        //观察先前的系统源码,在UNSPECIFIED模式下,只有展示一行
        if (heightMode == MeasureSpec.UNSPECIFIED) {
            //偷天换日 将heightMeasureSpec中的模式偷偷的改为AT_MOST
            heightMeasureSpec = MeasureSpec.makeMeasureSpec(Integer.MAX_VALUE >> 2, MeasureSpec.AT_MOST);
        }
        //观察先前的系统源码,在AT_MOST模式下,能够正常展示所有内容的
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
    }
}
