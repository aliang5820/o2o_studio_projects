package com.fanwe.model;

import lombok.Data;

/**
 * Created by Edison on 2016/8/1.
 */
@Data
public class MediaRewardItemModel {
    private long user_id;
    private long order_id;
    private long order_sn;//订单号
    private String user_name;//用户名称
    private String user_type;//0 普通 ，1 会员店 ，2 商户合伙人，3个人合伙人
    private long orderTime;//订单奖励才有的下单时间
    private String consume_money;//消费金额
    private String reward_money;//奖励金额
    private int belong_id;//消费类型
}
