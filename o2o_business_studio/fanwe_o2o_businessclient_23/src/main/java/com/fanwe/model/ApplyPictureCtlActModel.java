package com.fanwe.model;

import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * Created by Edison on 2016/9/4.
 */
@Data
@EqualsAndHashCode(callSuper = false)
public class ApplyPictureCtlActModel extends BaseCtlActModel {
    private String path; //本地原始地址
    //private String url = "./public/attachment/201609/05/23/de50dca0dbc171e6354ace9e4d7cd36f48.jpg"; //网络地址
    private String url; //网络地址
}
