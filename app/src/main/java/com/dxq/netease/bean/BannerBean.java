package com.dxq.netease.bean;

/**
 * Created by CREEPER_D on 2017/7/23.
 */

public class BannerBean {

    String imgsrc;
    String title;

    public BannerBean(String imgsrc, String title) {
        this.imgsrc = imgsrc;
        this.title = title;
    }

    public String getImgsrc() {
        return imgsrc;
    }

    public void setImgsrc(String imgsrc) {
        this.imgsrc = imgsrc;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    @Override
    public String toString() {
        return "BannerBean{" +
                "imgsrc='" + imgsrc + '\'' +
                ", title='" + title + '\'' +
                '}';
    }
}
