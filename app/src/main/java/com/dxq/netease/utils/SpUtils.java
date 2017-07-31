package com.dxq.netease.utils;

import android.content.Context;
import android.content.SharedPreferences;

import com.dxq.netease.conf.Constant;

/**
 * Created by CREEPER_D on 2017/7/9.
 */

public class SpUtils {

    public static boolean getBoolean(Context context, String key) {
        SharedPreferences sp = context.getSharedPreferences(Constant.SP_NAME, Context.MODE_PRIVATE);
        return sp.getBoolean(key, false);
    }

    public static void setBoolean(Context context, String key, Boolean value) {
        SharedPreferences sp = context.getSharedPreferences(Constant.SP_NAME, Context.MODE_PRIVATE);
        sp.edit().putBoolean(key, value).commit();
    }

    public static String getString(Context context, String key) {
        SharedPreferences sp = context.getSharedPreferences(Constant.SP_NAME, Context.MODE_PRIVATE);
        return sp.getString(key, "");
    }

    public static void setString(Context context, String key, String value) {
        SharedPreferences sp = context.getSharedPreferences(Constant.SP_NAME, Context.MODE_PRIVATE);
        sp.edit().putString(key, value).commit();
    }

    public static int getInt(Context context, String key) {
        SharedPreferences sp = context.getSharedPreferences(Constant.SP_NAME, Context.MODE_PRIVATE);
        return sp.getInt(key, 0);
    }

    public static void setInt(Context context, String key, int value) {
        SharedPreferences sp = context.getSharedPreferences(Constant.SP_NAME, Context.MODE_PRIVATE);
        sp.edit().putInt(key, value).commit();
    }

    public static long getLong(Context context, String key) {
        SharedPreferences sp = context.getSharedPreferences(Constant.SP_NAME, Context.MODE_PRIVATE);
        return sp.getLong(key, 0);
    }

    public static void setLong(Context context, String key, long value) {
        SharedPreferences sp = context.getSharedPreferences(Constant.SP_NAME, Context.MODE_PRIVATE);
        sp.edit().putLong(key, value).commit();
    }
}
