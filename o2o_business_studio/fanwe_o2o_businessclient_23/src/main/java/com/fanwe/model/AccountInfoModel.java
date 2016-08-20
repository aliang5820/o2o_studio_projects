package com.fanwe.model;

import lombok.Data;

/**
 * @author 作者 E-mail:309581534@qq.com
 * @version 创建时间：2015-5-9 下午3:49:43 类说明
 */
@Data
public class AccountInfoModel {
    private String account_id;
    private String supplier_id;
    private String account_name;
    private String account_password;
    private int account_type;
    private int is_new;
    private String qr_code;//推广二维码内容
}
