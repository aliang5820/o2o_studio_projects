package com.fanwe.model;

import java.io.Serializable;

import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * Created by Edison on 2016/11/10.
 */
@Data
@EqualsAndHashCode(callSuper = false)
public class WalletDrawRecordModel extends BaseCtlActModel implements Serializable {
    private long create_time;//创建时间
    private long auth_time;//审核时间
    private long pay_time;//确认支付时间
    private int is_delete;//1 正常  0删除
    private int is_pay;//0 未支付  1支付成功 2支付失败
    private int is_auth;//0 未审核  1 审核通过 2 审核失败
    private String pay_remark;//支付备注
    private String auth_remark;//审核备注
    private int card_id;//银行卡(支付宝 微信)id
    private int pay_type;//卡类型 0支付宝 1微信 2 借记卡 3 贷记卡
    private float money;//提现金额
    private int user_type;//0 普通 ，1 会员店 ，2 商户合伙人，3个人合伙人 4 代理商
    private String order_no;//订单编号
}
