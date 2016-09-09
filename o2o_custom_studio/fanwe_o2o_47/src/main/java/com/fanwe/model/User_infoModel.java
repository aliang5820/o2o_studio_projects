package com.fanwe.model;

import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = false)
public class User_infoModel extends BaseActModel {
    private int id;
    private int supplier_id;//用于自媒体首页
    private String user_name;
    private String user_pwd;
    private String email;
    private int is_tmp;
    private String user_avatar;
    private String mobile;
    private String mobile_format;
    private String money;
    private String money_format;
    private String consignee;
    private String qr_code;
}
