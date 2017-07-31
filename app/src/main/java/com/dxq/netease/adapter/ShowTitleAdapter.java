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

public class ShowTitleAdapter extends MyBaseAdapter<String> {

    private boolean mIsShowDelete = false;

    public ShowTitleAdapter(ArrayList<String> data) {
        super(data);
    }

    public class ShowTitleViewHolder {
        TextView tvChangeTitle;
        ImageView ivChangeTitle;

    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ShowTitleViewHolder holder;
        if (convertView == null) {
            convertView = View.inflate(parent.getContext(), R.layout.item_change_title, null);
            holder = new ShowTitleViewHolder();
            holder.ivChangeTitle = convertView.findViewById(R.id.iv_change_title);
            holder.tvChangeTitle = convertView.findViewById(R.id.tv_change_title);
            convertView.setTag(holder);
        } else {
            holder = (ShowTitleViewHolder) convertView.getTag();
        }
        changeUI(holder, position);
        return convertView;
    }

    private void changeUI(ShowTitleViewHolder holder, int position) {
        String s = mData.get(position);
        holder.tvChangeTitle.setText(s);
        if (mIsShowDelete) {
            holder.ivChangeTitle.setVisibility(position == 0 ? View.GONE : View.VISIBLE);
        } else {
            holder.ivChangeTitle.setVisibility(View.GONE);
        }
    }

    public boolean isShowDelete() {
        return mIsShowDelete;
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

    public void setShowDelete(boolean b) {
        if (mIsShowDelete == b) {
            return;
        }
        mIsShowDelete = b;
        notifyDataSetChanged();
    }
}
