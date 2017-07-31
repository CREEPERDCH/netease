package com.dxq.netease.utils;

import android.content.Context;

/**
 * Created by CREEPER_D on 2017/7/29.
 * 通过反射获取状态栏高度的一个工具类
 */

public class StatusUtils {
    public static int getStatusHeight(Context context) {
        int statusHeight = -1;
        try {
            Class clazz = Class.forName("com.android.internal.R$dimen");
            Object object = clazz.newInstance();
            int height = Integer.parseInt(clazz.getField("status_bar_height")
                    .get(object).toString());
            statusHeight = context.getResources().getDimensionPixelSize(height);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return statusHeight;
    }
}
