package com.fanwe.model;

import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * Created by Edison on 2017/1/24.
 */
@Data
@EqualsAndHashCode(callSuper = false)
public class AccountUpdateActModel extends BaseActModel {
    private String info;
}
