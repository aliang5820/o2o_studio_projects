package com.fanwe.model;

import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * Created by Edison on 2016/9/4.
 */
@Data
@EqualsAndHashCode(callSuper = false)
public class ApplyPictureCtlActModel extends BaseCtlActModel {
    private int error;
    private String path; //本地地址
    private String url; //网络地址
}
