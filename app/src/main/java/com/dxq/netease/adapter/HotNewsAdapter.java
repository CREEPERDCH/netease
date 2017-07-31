package com.dxq.netease.adapter;

import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.dxq.netease.R;
import com.dxq.netease.bean.HomeBean;
import com.dxq.netease.utils.ImageUtils;

import java.util.ArrayList;

/**
 * Created by CREEPER_D on 2017/7/23.
 */

public class HotNewsAdapter extends MyBaseAdapter<HomeBean> {
    public HotNewsAdapter(ArrayList<HomeBean> mData) {
        super(mData);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        HotNewsViewHolder holder;
        if (convertView == null) {
            holder = new HotNewsViewHolder();
            convertView = View.inflate(parent.getContext(), R.layout.item_hot_news, null);
            holder.mIvHot = convertView.findViewById(R.id.iv_hot);
            holder.mTvTitle = convertView.findViewById(R.id.tv_title);
            holder.mTvSource = convertView.findViewById(R.id.tv_source);
            holder.mTvReply = convertView.findViewById(R.id.tv_reply);
            holder.mTvTop = convertView.findViewById(R.id.tv_top);
            convertView.setTag(holder);
        } else {
            holder = (HotNewsViewHolder) convertView.getTag();
        }
        changeUI(holder, mData.get(position));
        return convertView;
    }

    private void changeUI(HotNewsViewHolder holder, HomeBean homeBean) {
        holder.mTvTitle.setText(homeBean.getTitle());
        holder.mTvSource.setText(homeBean.getSource());
        //判断是否需要置顶
        if ("S".equals(homeBean.getInterest())) {
            holder.mTvTop.setVisibility(View.VISIBLE);
            holder.mTvReply.setVisibility(View.GONE);
        } else {
            holder.mTvTop.setVisibility(View.GONE);
            int replyCount = Integer.valueOf(homeBean.getReplyCount());
            if (replyCount == 0) {
                holder.mTvReply.setVisibility(View.GONE);
            } else {
                holder.mTvReply.setVisibility(View.VISIBLE);
                holder.mTvReply.setText(replyCount + "跟帖");
            }
        }
        ImageUtils.getSingleTon().showImage(homeBean.getImg(), holder.mIvHot);
    }

    public void addData(ArrayList<HomeBean> t1348647909107) {
        mData.addAll(t1348647909107);
        notifyDataSetChanged();
    }

    public void updateData(ArrayList<HomeBean> t1348647909107) {
        mData.clear();
        mData.addAll(t1348647909107);
        notifyDataSetChanged();
    }

    public class HotNewsViewHolder {
        ImageView mIvHot;
        TextView mTvTitle;
        TextView mTvSource;
        TextView mTvReply;
        TextView mTvTop;
    }
}
