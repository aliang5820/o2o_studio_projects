package com.fanwe.model;

import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = false)
public class InitActModel extends BaseCtlActModel {
    private int api_wx;
    private String wx_app_key;
    private String wx_app_secret;
}
