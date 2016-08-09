package com.fanwe.model;

import lombok.Data;

@Data
public class BaseCtlActModel {
    protected String act;
    protected int status = -1;
    protected String info;
    protected String sess_id;
    protected String ctl;
    protected int biz_login_status = -1;
    protected String page_title;
    protected String ref_uid;
    protected int is_auth;
}
