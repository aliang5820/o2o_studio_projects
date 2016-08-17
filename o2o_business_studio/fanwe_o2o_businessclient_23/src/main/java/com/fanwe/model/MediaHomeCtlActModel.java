package com.fanwe.model;

import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * Created by Edison on 2016/8/10.
 */
@Data
@EqualsAndHashCode(callSuper = false)
public class MediaHomeCtlActModel extends BaseCtlActModel {
    private String classPersonNum;//总下线人数
    private String totalRewardMoney;//总奖励金额
    private String a_Class_Person;//一级
    private String b_Class_Person;//二级
    private String c_Class_Person;//三级
    private String nowMonthSaleMoney;//本月订单奖励
    private String nowMonthPartnerMemMoney;//本月会员店招募奖励
    private String nowMonthMemMoney;//本月合伙人奖励
    private String withdrawalsMoney;//已提现佣金
    private String depositMoney;//未提现佣金
    private String extension_person;//推荐人
}
