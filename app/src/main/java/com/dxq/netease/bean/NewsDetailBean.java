package com.dxq.netease.bean;

import java.util.List;

/**
 * Created by CREEPER_D on 2017/7/25.
 */

public class NewsDetailBean {

    String body;//正文
    String title;//标题
    String source;//来源
    String ptime;//发布的时间
    int replyCount;//评论数量
    List<NewsDetailImageBean> img;

    public NewsDetailBean(String body, String title, String source, String ptime, int replyCount, List<NewsDetailImageBean> img) {
        this.body = body;
        this.title = title;
        this.source = source;
        this.ptime = ptime;
        this.replyCount = replyCount;
        this.img = img;
    }

    public String getBody() {
        return body;
    }

    public void setBody(String body) {
        this.body = body;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getSource() {
        return source;
    }

    public void setSource(String source) {
        this.source = source;
    }

    public String getPtime() {
        return ptime;
    }

    public void setPtime(String ptime) {
        this.ptime = ptime;
    }

    public int getReplyCount() {
        return replyCount;
    }

    public void setReplyCount(int replyCount) {
        this.replyCount = replyCount;
    }

    public List<NewsDetailImageBean> getImg() {
        return img;
    }

    public void setImg(List<NewsDetailImageBean> img) {
        this.img = img;
    }

    @Override
    public String toString() {
        return "NewsDetailBean{" +
                "body='" + body + '\'' +
                ", title='" + title + '\'' +
                ", source='" + source + '\'' +
                ", ptime='" + ptime + '\'' +
                ", replyCount=" + replyCount +
                ", img=" + img +
                '}';
    }
}
