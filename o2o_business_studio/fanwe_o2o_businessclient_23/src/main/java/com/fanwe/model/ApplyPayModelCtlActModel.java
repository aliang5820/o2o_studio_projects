package com.fanwe.model;

import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * Created by Edison on 2016/8/10.
 */
@Data
@EqualsAndHashCode(callSuper = false)
public class ApplyPayModelCtlActModel extends BaseCtlActModel {
    private String charge;
}
