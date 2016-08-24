package com.fanwe.model;

import lombok.Data;

/**
 * Created by Edison on 2016/7/31.
 */
@Data
public class MediaNextLevelItemModel {
    private String user_name;//昵称
    private int type;//类型
    private long time;//注册时间
    private String parent_name;//推荐人
}
