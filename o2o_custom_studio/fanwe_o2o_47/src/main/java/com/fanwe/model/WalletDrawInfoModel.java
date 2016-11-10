package com.fanwe.model;

import java.io.Serializable;

import lombok.Data;

/**
 * Created by Edison on 2016/11/11.
 */
@Data
public class WalletDrawInfoModel implements Serializable {
    private boolean is_withdraw;//是否开启提现
    private int type;// 提现方式 0支付宝 1微信 2银行卡
    private int withdraw_fee_type;//
    private int withdraw_fee;//费率，需要除100
}
