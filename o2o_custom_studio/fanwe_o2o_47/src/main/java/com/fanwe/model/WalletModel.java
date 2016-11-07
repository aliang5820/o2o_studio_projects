package com.fanwe.model;

import java.io.Serializable;

import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * Created by Edison on 2016/11/7.
 */
@Data
@EqualsAndHashCode(callSuper = false)
public class WalletModel extends BaseActModel implements Serializable {
    private float money;//可提现金额
    private float in_day_money;//今日收入
    private float out_money;//今日提现
    private int is_bind_wx;//绑定微信
    private int is_bind_zf;//支付宝
    private int is_bind_yh;//银行卡
    private String wx_account;//微信账号
    private String zf_account;//支付宝账号
    private String yh_account;//银行卡账号
}
