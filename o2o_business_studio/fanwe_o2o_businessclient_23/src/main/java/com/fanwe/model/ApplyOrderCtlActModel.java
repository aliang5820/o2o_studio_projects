package com.fanwe.model;

import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * Created by Edison on 2016/8/10.
 */
@Data
@EqualsAndHashCode(callSuper = false)
public class ApplyOrderCtlActModel extends BaseCtlActModel {
    private long orderId;
    private double price;
    private int is_pay;
    private String order_sn;//订单号
}
