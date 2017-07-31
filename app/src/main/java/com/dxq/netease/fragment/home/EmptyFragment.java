package com.dxq.netease.fragment.home;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.dxq.netease.fragment.LogFragment;

/**
 * Created by CREEPER_D on 2017/7/23.
 */

public class EmptyFragment extends LogFragment {

    String text = getClass().getSimpleName();

    @Override
    public View onChildCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        TextView textView = new TextView(getContext());
        textView.setText(text);
        textView.setTextSize(30);
        return textView;
    }

    @Override
    protected void initData() {

    }
}
