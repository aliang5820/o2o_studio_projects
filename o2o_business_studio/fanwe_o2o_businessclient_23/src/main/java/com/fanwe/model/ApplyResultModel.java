package com.fanwe.model;

import java.io.Serializable;

import lombok.Data;

/**
 * Created by Edison on 2016/9/5.
 */
@Data
public class ApplyResultModel implements Serializable {
    private String account_name; //账号
    private String h_name; //企业名称
    private String type_str;//申请类别描述
    private int type;//申请类别
    private String province;//省
    private String city;//市
    private String area;//区
    private String check_failed_reason;//失败原因
    private int check_status;//状态
    private String supplier_id;
}
