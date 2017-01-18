package com.fanwe.model;

import lombok.Data;

/**
 * Created by Edison on 2017/1/9.
 */
@Data
public class ApplyPayModel {
    private String pay_info;
    private String payment_name;
    private int pay_money;
    private String class_name;
    private ApplyPayConfigModel config;
}
