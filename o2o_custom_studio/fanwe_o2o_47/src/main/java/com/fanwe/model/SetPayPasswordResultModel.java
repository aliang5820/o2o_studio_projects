package com.fanwe.model;

import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * Created by Edison on 2016/11/11.
 */
@Data
@EqualsAndHashCode(callSuper = false)
public class SetPayPasswordResultModel extends BaseActModel {
    private boolean set_status;
    private String info;
    private long account_id;
}
