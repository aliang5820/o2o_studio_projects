package com.fanwe.model;

import java.io.Serializable;

import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * Created by Edison on 2016/9/8.
 */
@Data
@EqualsAndHashCode(callSuper = false)
public class ApplyInfoModel extends BaseCtlActModel implements Serializable {
    /**
     * name : 大保健
     * address : 天府广场
     * deal_cate : 28
     * deal_child_cate : 64
     * tel : 15982034811
     * open_time : 发货吧
     * h_name : 腹肌
     * h_faren :
     * h_license : ./public/attachment/201609/07/23/bcd3fc8e52fd274f7eba5304161bdc7163.jpg
     * h_other_license : ./public/attachment/201609/07/23/a6f77596fa1f6fa5d3748d5d672f5b1178.jpg
     * h_supplier_logo : ./public/attachment/201609/07/23/799443ab6925f2f2dad8700b738181b084.jpg
     * h_supplier_image : ./public/attachment/201609/07/23/e03bfa754b00c199e565f4e09a1aca0717.jpg
     * h_tel : 15982034811
     * h_bank_info : 4558876669
     * h_bank_user : 发挥好好
     * h_bank_name : 凤凰古城V
     */
    private String submit_id;
    private String name;
    private String address;
    private String deal_cate;
    private String deal_child_cate;
    private String tel;  //商户电话
    private String mobile;// 手机号
    private String open_time;
    private String area;//选择的地区
    private int area_id;//选择的地区id
    private String h_name;
    private String h_faren;
    private String h_license;
    private String h_other_license;
    private String h_supplier_logo;
    private String h_supplier_image;
    private String h_tel; //联系电话
    private String h_bank_info;
    private String h_bank_user;
    private String h_bank_name;
}
