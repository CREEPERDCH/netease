package com.dxq.netease.utils;

import android.text.TextUtils;

/**
 * Created by CREEPER_D on 2017/7/18.
 */

public class HashCodeUtils {
    public static String getHashCodeFileName(String url) {
        String hashCode = "";
        if (!TextUtils.isEmpty(url)) {
            int i = url.hashCode();
            hashCode = "" + i;
        }
        return hashCode;
    }
}
