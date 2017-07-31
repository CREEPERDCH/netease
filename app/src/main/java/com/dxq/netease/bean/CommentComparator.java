package com.dxq.netease.bean;

import java.util.Comparator;

/**
 * Created by CREEPER_D on 2017/7/27.
 */

public class CommentComparator implements Comparator<KeyBoardBean> {
    @Override
    public int compare(KeyBoardBean lhs, KeyBoardBean rhs) {
        //按照javaBean中的点赞数
        int voteL = lhs.getVote();
        int voteR = rhs.getVote();
        if (voteL == voteR) {
            return 0;
        }
        return voteL < voteR ? 1 : -1;
    }
}
