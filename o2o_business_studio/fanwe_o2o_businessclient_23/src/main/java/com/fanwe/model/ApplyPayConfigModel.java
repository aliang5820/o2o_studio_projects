package com.fanwe.model;

import lombok.Data;

/**
 * Created by Edison on 2017/1/10.
 */
@Data
public class ApplyPayConfigModel {
    private String appid;
    private String noncestr;
    private String partnerid;
    private String prepayid;
    private String timestamp;
    private String sign;
    private String packagevalue;
    private String subject;
    private String body;
    private int total_fee;
    private String total_fee_format;
    private String out_trade_no;
    private String notify_url;
    private String key;
    private String secret;
}
