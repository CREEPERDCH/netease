package com.dxq.netease.utils;

import android.text.TextUtils;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.util.List;

/**
 * Created by CREEPER_D on 2017/7/18.
 */

public class JsonUtils {

    static Gson gson = new Gson();

    public static <T> T parseJson(String json, Class<T> clazz) {
        if (TextUtils.isEmpty(json)) {
            return null;
        }
        T listBean = gson.fromJson(json, clazz);
        return listBean;
    }

    //将List集合转为String
    public static String list2String(List<String> list) {
        String json = gson.toJson(list);
        return json;
    }

    //将String转为List集合
    public static List<String> string2List(String json) {
        List<String> list = gson.fromJson(json, new TypeToken<List<String>>() {
        }.getType());
        return list;
    }
}
