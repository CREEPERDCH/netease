package com.dxq.netease.bean;

import java.io.Serializable;

/**
 * Created by CREEPER_D on 2017/7/18.
 */

public class ActionParamsBean implements Serializable {

    String link_url;

    public ActionParamsBean(String link_url) {
        this.link_url = link_url;
    }

    public String getLink_url() {
        return link_url;
    }

    public void setLink_url(String link_url) {
        this.link_url = link_url;
    }

    @Override
    public String toString() {
        return "ActionParamsBean{" +
                "link_url='" + link_url + '\'' +
                '}';
    }
}
