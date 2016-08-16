package com.fanwe.model;

import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = false)
public class Sms_send_sms_codeActModel extends BaseCtlActModel {

    private int time;
    private String verify_image;
    private int width;
    private int height;
}
