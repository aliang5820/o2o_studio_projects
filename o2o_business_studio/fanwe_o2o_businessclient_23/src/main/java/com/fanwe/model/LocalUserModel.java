package com.fanwe.model;

import lombok.Data;

@Data
public class LocalUserModel {
    private String user_id;
    private String supplier_id;
    private String submit_id;
    private String account_name;
    private String account_password;
    private int is_new;
    private int account_type;
    private String qr_code;
}
