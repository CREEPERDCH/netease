package com.dxq.netease.bean;

import java.util.ArrayList;

/**
 * Created by CREEPER_D on 2017/7/23.
 */

public class HomeListBean {

    ArrayList<HomeBean> T1348647909107;

    public HomeListBean(ArrayList<HomeBean> t1348647909107) {
        T1348647909107 = t1348647909107;
    }

    public ArrayList<HomeBean> getT1348647909107() {
        return T1348647909107;
    }

    public void setT1348647909107(ArrayList<HomeBean> t1348647909107) {
        T1348647909107 = t1348647909107;
    }

    @Override
    public String toString() {
        return "HomeListBean{" +
                "T1348647909107=" + T1348647909107 +
                '}';
    }
}
