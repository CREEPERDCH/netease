package com.dxq.netease.event;

/**
 * Created by CREEPER_D on 2017/7/29.
 */

public class TabHostVisiableEvent {
    //代表是否需要tabhost进行显示
    public boolean isVisiable;

    public TabHostVisiableEvent(boolean isVisiable) {
        this.isVisiable = isVisiable;
    }
}
