package com.dxq.netease.bean;

import java.util.ArrayList;

/**
 * Created by CREEPER_D on 2017/7/23.
 */

public class HomeBean {

    String img;
    String source;
    String title;
    String replyCount;//回帖数
    String interest;//"s"代表置顶
    String id;//需要使用ID来获取新闻详情

    ArrayList<BannerBean> ads;//轮播图数据

    public HomeBean(String img, String source, String title, String replyCount, String interest, String id, ArrayList<BannerBean> ads) {
        this.img = img;
        this.source = source;
        this.title = title;
        this.replyCount = replyCount;
        this.interest = interest;
        this.id = id;
        this.ads = ads;
    }

    public String getImg() {
        return img;
    }

    public void setImg(String img) {
        this.img = img;
    }

    public String getSource() {
        return source;
    }

    public void setSource(String source) {
        this.source = source;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getReplyCount() {
        return replyCount;
    }

    public void setReplyCount(String replyCount) {
        this.replyCount = replyCount;
    }

    public String getInterest() {
        return interest;
    }

    public void setInterest(String interest) {
        this.interest = interest;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public ArrayList<BannerBean> getAds() {
        return ads;
    }

    public void setAds(ArrayList<BannerBean> ads) {
        this.ads = ads;
    }

    @Override
    public String toString() {
        return "HomeBean{" +
                "img='" + img + '\'' +
                ", source='" + source + '\'' +
                ", title='" + title + '\'' +
                ", replyCount='" + replyCount + '\'' +
                ", interest='" + interest + '\'' +
                ", id='" + id + '\'' +
                ", ads=" + ads +
                '}';
    }
}
