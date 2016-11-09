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
    private long time;//创建时间
    private int user_type;//0 普通 ，1 会员店 ，2 商户合伙人，3个人合伙人 4 代理商
    private int type;//0 提现 1分佣
    private int pay_type;//卡类型 0支付宝 1微信 2 借记卡 3 贷记卡 4其他
    private String content;//日志内容
    private int pay_status;//支付状态 0 失败 1成功
    private float money;//金钱
    private String order_no;//订单编号
}
