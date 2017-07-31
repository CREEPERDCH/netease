package com.dxq.netease.bean;

import java.io.Serializable;

/**
 * Created by CREEPER_D on 2017/7/25.
 */

public class NewsDetailImageBean implements Serializable{

    String ref;
    String src;

    public NewsDetailImageBean(String ref, String src) {
        this.ref = ref;
        this.src = src;
    }

    public String getRef() {
        return ref;
    }

    public void setRef(String ref) {
        this.ref = ref;
    }

    public String getSrc() {
        return src;
    }

    public void setSrc(String src) {
        this.src = src;
    }

    @Override
    public String toString() {
        return "NewsDetailImageBean{" +
                "ref='" + ref + '\'' +
                ", src='" + src + '\'' +
                '}';
    }
}
