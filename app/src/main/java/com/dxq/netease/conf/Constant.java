package com.dxq.netease.conf;

/**
 * Created by CREEPER_D on 2017/7/18.
 */

public class Constant {

    public static final String SP_NAME = "config";

    public static final String AD_JSON = "AD_JSON";

    public static final String OUT_DATE_TIME = "OUT_DATE_TIME";

    public static final String AD_PIC_INDEX = "AD_PIC_INDEX";

    public static final String SHOW_TITLE = "SHOW_TITLE";

    public static final String ADD_TITLE = "ADD_TITLE";

    public static class URL {

        public static final String AD_URL = "http://g1.163.com/madr?app=7A16FBB6&platform=android&category=STARTUP&location=1";

        public static final String HOME_URL = "http://c.m.163.com/nc/article/headline/T1348647909107/" +
                "START-END.html?from=toutiao&size=10&passport=&devId=bMo6EQztO2ZGFBurrbgcMQ%3D%3D&net=wifi";

        public static final String NEWS_DETAIL_URL = "http://c.m.163.com/nc/article/NEWS_ID/full.html";

        public static final String KEY_BOARD_URL = "http://comment.api.163.com/api/v1/" +
                "products/a2869674571f77b5a0867c3d71db5856/threads/NEWS_ID/app/" +
                "comments/hotList?offset=0&limit=10&showLevelThreshold=10&headLimit=2&tailLimit=2";
    }

    public static String getHomeUrl(int start, int end) {
        String homeUrl = Constant.URL.HOME_URL.replace("START", start + "").replace("END", end + "");
        return homeUrl;
    }

    public static String getNewsDetailUrl(String resId) {
        String newsDetailUrl = URL.NEWS_DETAIL_URL.replace("NEWS_ID", resId);
        return newsDetailUrl;
    }

    public static String getKeyBoardUrl(String newsId) {
        String keyBoardUrl = URL.KEY_BOARD_URL.replace("NEWS_ID", newsId);
        return keyBoardUrl;
    }
}
