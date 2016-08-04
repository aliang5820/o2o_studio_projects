package com.fanwe.model;

/**
 * Created by Edison on 2016/7/31.
 */
public class MediaNextLevelCtlItemModel {
    private String id;
    private String type;//角色类型
    private String nickName;// 昵称
    private long focesTime; //关注时间
    private String pictureUrl;//头像

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getNickName() {
        return nickName;
    }

    public void setNickName(String nickName) {
        this.nickName = nickName;
    }

    public long getFocesTime() {
        return focesTime;
    }

    public void setFocesTime(long focesTime) {
        this.focesTime = focesTime;
    }

    public String getPictureUrl() {
        return pictureUrl;
    }

    public void setPictureUrl(String pictureUrl) {
        this.pictureUrl = pictureUrl;
    }
}
