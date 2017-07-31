package com.dxq.netease.adapter;

import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;

import com.dxq.netease.R;
import com.dxq.netease.bean.KeyBoardBean;
import com.dxq.netease.utils.ImageUtils;

import java.util.ArrayList;

/**
 * Created by CREEPER_D on 2017/7/27.
 */

public class CommentAdapter extends MyBaseAdapter<KeyBoardBean> {

    private static final int TYPE_HOT = 0;//热门
    private static final int TYPE_COMMENT = 1;//一般

    public CommentAdapter(ArrayList<KeyBoardBean> data) {
        super(data);
        //往集合最前面添加一个空的数据,把以前的评论数据顶下去
        mData.add(0,new KeyBoardBean());
    }

    //根据position来判断当前position位置上的item属于哪一种类型
    @Override
    public int getItemViewType(int position) {
        if (position == 0) {
            return TYPE_HOT;
        } else {
            return TYPE_COMMENT;
        }
    }

    //返回值代表这个列表有多少种item类型
    @Override
    public int getViewTypeCount() {
        return 2;
    }

    public class CommentViewHolder {

        ImageView ivUserIcon;
        TextView tvUserName;
        TextView tvUserInfo;
        ImageView ivSupport;
        TextView tvVoteCount;
        FrameLayout flSubFloor;
        TextView tvComment;


    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        //加判断,如果position知道了,对应这个位置上的这个item也就知道这个类型
        if (getItemViewType(position) == TYPE_HOT) {
            //当前view应该要展示成热门跟帖的效果
            convertView = View.inflate(parent.getContext(), R.layout.item_hot_title, null);
        } else {
            CommentViewHolder holder;
            if (convertView == null) {
                convertView = View.inflate(parent.getContext(), R.layout.item, null);
                holder = new CommentViewHolder();
                holder.ivUserIcon = convertView.findViewById(R.id.iv_user_icon);
                holder.tvUserName = convertView.findViewById(R.id.tv_user_name);
                holder.tvUserInfo = convertView.findViewById(R.id.tv_user_info);
                holder.ivSupport = convertView.findViewById(R.id.iv_support);
                holder.tvVoteCount = convertView.findViewById(R.id.tv_vote_count);
                holder.flSubFloor = convertView.findViewById(R.id.fl_sub_floor);
                holder.tvComment = convertView.findViewById(R.id.tv_comment);
                convertView.setTag(holder);
            } else {
                holder = (CommentViewHolder) convertView.getTag();
            }
            //修改控件的展示
            KeyBoardBean keyBoardBean = mData.get(position);
            Log.d(getClass().getSimpleName() + "dxq", "getView: " + keyBoardBean);
            changeUI(holder, keyBoardBean);
        }
        return convertView;
    }

    private void changeUI(CommentViewHolder holder, KeyBoardBean keyBoardBean) {
        holder.tvUserName.setText(keyBoardBean.getUser().getNickname());
        holder.tvComment.setText(keyBoardBean.getContent());
        holder.tvVoteCount.setText(keyBoardBean.getVote() + "");

        String info = keyBoardBean.getUser().getLocation() + " " +
                keyBoardBean.getDeviceInfo().getDeviceName() + " " +
                keyBoardBean.getCreateTime();
        holder.tvUserInfo.setText(info);

        String url = keyBoardBean.getUser().getAvatar();
        if (TextUtils.isEmpty(url)) {
            holder.ivUserIcon.setImageResource(R.drawable.icon_user_default);
        } else {
            ImageUtils.getSingleTon().showImage(url, holder.ivUserIcon);
        }
    }
}
