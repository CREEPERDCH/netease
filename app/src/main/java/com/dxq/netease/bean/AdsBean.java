package com.dxq.netease.bean;

import java.io.Serializable;
import java.util.Arrays;

/**
 * Created by CREEPER_D on 2017/7/18.
 */

public class AdsBean implements Serializable {

    ActionParamsBean action_params;
    String[] res_url;

    public AdsBean(ActionParamsBean actionParams, String[] res_url) {
        this.action_params = actionParams;
        this.res_url = res_url;
    }

    public ActionParamsBean getActionParams() {
        return action_params;
    }

    public void setActionParams(ActionParamsBean actionParams) {
        this.action_params = actionParams;
    }

    public String[] getRes_url() {
        return res_url;
    }

    public void setRes_url(String[] res_url) {
        this.res_url = res_url;
    }

    @Override
    public String toString() {
        return "AdsBean{" +
                "actionParams=" + action_params +
                ", res_url=" + Arrays.toString(res_url) +
                '}';
    }
}
