package com.dxq.netease.adapter;

import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.dxq.netease.R;

import java.util.ArrayList;

/**
 * Created by CREEPER_D on 2017/7/29.
 */

public class AddTitleAdapter extends MyBaseAdapter<String> {
    public AddTitleAdapter(ArrayList<String> data) {
        super(data);
    }

    public class AddTitleViewHolder {
        TextView tvChangeTitle;
        ImageView ivChangeTitle;

    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        AddTitleViewHolder holder;
        if (convertView == null) {
            holder = new AddTitleViewHolder();
            convertView = View.inflate(parent.getContext(), R.layout.item_change_title, null);
            holder.ivChangeTitle = convertView.findViewById(R.id.iv_change_title);
            holder.tvChangeTitle = convertView.findViewById(R.id.tv_change_title);
            convertView.setTag(holder);
        } else {
            holder = (AddTitleViewHolder) convertView.getTag();
        }
        changeUI(holder, mData.get(position));
        return convertView;
    }

    private void changeUI(AddTitleViewHolder holder, String s) {
        holder.tvChangeTitle.setText(s);
    }

    public String deleteItem(int position) {
        String remove = mData.remove(position);
        notifyDataSetChanged();
        return remove;
    }

    public void addItem(String item) {
        mData.add(item);
        notifyDataSetChanged();
    }
}
