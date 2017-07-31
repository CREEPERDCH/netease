package com.dxq.netease.adapter;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by CREEPER_D on 2017/7/23.
 */

public class HomePagerAdapter extends FragmentPagerAdapter {

    ArrayList<Fragment> mFragments;
    List<String> mStringArray;

    public HomePagerAdapter(FragmentManager childFragmentManager, ArrayList<Fragment> fragments, List<String> stringArray) {
        super(childFragmentManager);
        this.mFragments = fragments;
        this.mStringArray = stringArray;
    }

    @Override
    public Fragment getItem(int position) {
        return mFragments.get(position);
    }

    @Override
    public int getCount() {
        return mFragments.size();
    }

    @Override
    public CharSequence getPageTitle(int position) {
        return mStringArray.get(position);
    }

    public void updateData(ArrayList<Fragment> fragments, ArrayList<String> strings) {
        mFragments = fragments;
        mStringArray = strings;
        notifyDataSetChanged();
    }
}
