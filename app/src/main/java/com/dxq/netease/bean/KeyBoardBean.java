package com.dxq.netease.bean;

import java.util.List;

/**
 * Created by Administrator on 2017/7/26 0026.
 */

public class KeyBoardBean {

    /**
     * favCount : 0
     * against : 0
     * unionState : false
     * ip : 183.18.*.*
     * siteName : 网易
     * postId : CQ7JLALD0517EM6I_137785681
     * source : ph
     * productKey : a2869674571f77b5a0867c3d71db5856
     * content : 大家有没有觉得，地址为“广州市花都区新华镇”的网友，大部分留言都好屌？怀疑是网易请的水军聚集地 包吃包住！！
     * deviceInfo : {"deviceName":"iPhone 7 Plus"}
     * shareCount : 0
     * buildLevel : 1
     * createTime : 2017-07-26 16:06:28
     * anonymous : false
     * commentId : 137785681
     * isDel : false
     * user : {"vipInfo":"","nickname":"华为手机中国骄傲","location":"广东省湛江市","avatar":"http://mobilepics.nosdn.127.net/ChEPlqU77f503VUqLTmHNRFLAn8Mc86t513378902","id":"Y2hhb3JlbnlpamluZ0AxNjMuY29t","redNameInfo":[],"userId":3626956}
     * vote : 13
     */
    private int favCount;
    private int against;
    private boolean unionState;
    private String ip;
    private String siteName;
    private String postId;
    private String source;
    private String productKey;
    private String content;
    private DeviceInfoEntity deviceInfo;
    private int shareCount;
    private int buildLevel;
    private String createTime;
    private boolean anonymous;
    private int commentId;
    private boolean isDel;
    private UserEntity user;
    private int vote;

    public void setFavCount(int favCount) {
        this.favCount = favCount;
    }

    public void setAgainst(int against) {
        this.against = against;
    }

    public void setUnionState(boolean unionState) {
        this.unionState = unionState;
    }

    public void setIp(String ip) {
        this.ip = ip;
    }

    public void setSiteName(String siteName) {
        this.siteName = siteName;
    }

    public void setPostId(String postId) {
        this.postId = postId;
    }

    public void setSource(String source) {
        this.source = source;
    }

    public void setProductKey(String productKey) {
        this.productKey = productKey;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public void setDeviceInfo(DeviceInfoEntity deviceInfo) {
        this.deviceInfo = deviceInfo;
    }

    public void setShareCount(int shareCount) {
        this.shareCount = shareCount;
    }

    public void setBuildLevel(int buildLevel) {
        this.buildLevel = buildLevel;
    }

    public void setCreateTime(String createTime) {
        this.createTime = createTime;
    }

    public void setAnonymous(boolean anonymous) {
        this.anonymous = anonymous;
    }

    public void setCommentId(int commentId) {
        this.commentId = commentId;
    }

    public void setIsDel(boolean isDel) {
        this.isDel = isDel;
    }

    public void setUser(UserEntity user) {
        this.user = user;
    }

    public void setVote(int vote) {
        this.vote = vote;
    }

    public int getFavCount() {
        return favCount;
    }

    public int getAgainst() {
        return against;
    }

    public boolean isUnionState() {
        return unionState;
    }

    public String getIp() {
        return ip;
    }

    public String getSiteName() {
        return siteName;
    }

    public String getPostId() {
        return postId;
    }

    public String getSource() {
        return source;
    }

    public String getProductKey() {
        return productKey;
    }

    public String getContent() {
        return content;
    }

    public DeviceInfoEntity getDeviceInfo() {
        return deviceInfo;
    }

    public int getShareCount() {
        return shareCount;
    }

    public int getBuildLevel() {
        return buildLevel;
    }

    public String getCreateTime() {
        return createTime;
    }

    public boolean isAnonymous() {
        return anonymous;
    }

    public int getCommentId() {
        return commentId;
    }

    public boolean isIsDel() {
        return isDel;
    }

    public UserEntity getUser() {
        return user;
    }

    public int getVote() {
        return vote;
    }

    public static class DeviceInfoEntity {
        /**
         * deviceName : iPhone 7 Plus
         */
        private String deviceName;

        public void setDeviceName(String deviceName) {
            this.deviceName = deviceName;
        }

        public String getDeviceName() {
            return deviceName;
        }
    }

    public static class UserEntity {
        /**
         * vipInfo :
         * nickname : 华为手机中国骄傲
         * location : 广东省湛江市
         * avatar : http://mobilepics.nosdn.127.net/ChEPlqU77f503VUqLTmHNRFLAn8Mc86t513378902
         * id : Y2hhb3JlbnlpamluZ0AxNjMuY29t
         * redNameInfo : []
         * userId : 3626956
         */
        private String vipInfo;
        private String nickname;
        private String location;
        private String avatar;
        private String id;
        private List<?> redNameInfo;
        private int userId;

        public void setVipInfo(String vipInfo) {
            this.vipInfo = vipInfo;
        }

        public void setNickname(String nickname) {
            this.nickname = nickname;
        }

        public void setLocation(String location) {
            this.location = location;
        }

        public void setAvatar(String avatar) {
            this.avatar = avatar;
        }

        public void setId(String id) {
            this.id = id;
        }

        public void setRedNameInfo(List<?> redNameInfo) {
            this.redNameInfo = redNameInfo;
        }

        public void setUserId(int userId) {
            this.userId = userId;
        }

        public String getVipInfo() {
            return vipInfo;
        }

        public String getNickname() {
            return nickname;
        }

        public String getLocation() {
            return location;
        }

        public String getAvatar() {
            return avatar;
        }

        public String getId() {
            return id;
        }

        public List<?> getRedNameInfo() {
            return redNameInfo;
        }

        public int getUserId() {
            return userId;
        }
    }
    //Alt + Insert


}
