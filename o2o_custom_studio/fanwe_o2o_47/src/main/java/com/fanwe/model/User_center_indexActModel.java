package com.fanwe.model;

import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = false)
public class User_center_indexActModel extends BaseActModel {

    private int uid;
    private String user_name;
    private String user_money_format;
    private String user_avatar;
    private int is_extension;
    private String user_money;
    private String user_score;
    private String coupon_count;
    private String youhui_count;
    private String wait_dp_count;
    private String not_pay_order_count;
}
