package com.dxq.netease.adapter;

import android.widget.BaseAdapter;

import java.util.ArrayList;

/**
 * Created by CREEPER_D on 2017/7/23.
 */

public abstract class MyBaseAdapter<T> extends BaseAdapter {

    ArrayList<T> mData;

    public MyBaseAdapter(ArrayList<T> mData) {
        this.mData = mData;
    }

    public ArrayList<T> getData() {
        return mData;
    }

    @Override
    public int getCount() {
        return mData == null ? 0 : mData.size();
    }

    @Override
    public Object getItem(int i) {
        return mData.get(i);
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

}
