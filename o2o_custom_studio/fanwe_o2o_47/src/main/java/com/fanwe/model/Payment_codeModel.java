package com.fanwe.model;

import com.fanwe.utils.JsonUtil;
import com.fanwe.utils.SDFormatUtil;

import java.util.Map;

public class Payment_codeModel {

    private String pay_info;
    private String pay_action;
    private String payment_name;
    private String pay_money;
    private String class_name;

    private Map<String, Object> config;

    public MalipayModel getMalipay() {
        return JsonUtil.map2Object(config, MalipayModel.class);
    }

    public WxappModel getWxapp() {
        return JsonUtil.map2Object(config, WxappModel.class);
    }

    public UpacpappModel getUpacpapp() {
        return JsonUtil.map2Object(config, UpacpappModel.class);
    }

    public Map<String, Object> getConfig() {
        return config;
    }

    public void setConfig(Map<String, Object> config) {
        this.config = config;
    }

    public String getPay_moneyFormat() {
        String result = payment_name + " " + SDFormatUtil.formatMoneyChina(pay_money);
        return result;
    }

    public String getPay_info() {
        return pay_info;
    }

    public void setPay_info(String pay_info) {
        this.pay_info = pay_info;
    }

    public String getPay_action() {
        return pay_action;
    }

    public void setPay_action(String pay_action) {
        this.pay_action = pay_action;
    }

    public String getPayment_name() {
        return payment_name;
    }

    public void setPayment_name(String payment_name) {
        this.payment_name = payment_name;
    }

    public String getPay_money() {
        return pay_money;
    }

    public void setPay_money(String pay_money) {
        this.pay_money = pay_money;
    }

    public String getClass_name() {
        return class_name;
    }

    public void setClass_name(String class_name) {
        this.class_name = class_name;
    }

}
