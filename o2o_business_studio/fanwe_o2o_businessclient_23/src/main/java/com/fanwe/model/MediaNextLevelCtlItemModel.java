package com.fanwe.model;

import lombok.Data;

/**
 * Created by Edison on 2016/7/31.
 */
@Data
public class MediaNextLevelCtlItemModel {
    private String id;
    private String type;//角色类型
    private String nickName;// 昵称
    private long focesTime; //关注时间
    private String pictureUrl;//头像
}
