package com.fanwe.model;

import lombok.Data;

/**
 * Created by Edison on 2016/8/10.
 */
@Data
public class ApplyOrderCtlActModel extends BaseCtlActModel {
    private long orderId;
    private double price;
}
