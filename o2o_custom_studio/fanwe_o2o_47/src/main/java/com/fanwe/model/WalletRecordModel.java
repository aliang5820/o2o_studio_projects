package com.fanwe.model;

import java.io.Serializable;

import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * Created by Edison on 2016/11/8.
 */
@Data
@EqualsAndHashCode(callSuper = false)
public class WalletRecordModel extends BaseActModel implements Serializable {
    private long time;//申请时间
    private String desc;//描述
    private String value;//值
}
