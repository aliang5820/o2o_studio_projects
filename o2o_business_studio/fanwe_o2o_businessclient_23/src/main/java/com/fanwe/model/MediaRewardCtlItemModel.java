package com.fanwe.model;

/**
 * Created by Edison on 2016/8/1.
 */
public class MediaRewardCtlItemModel {
    private long id;
    private int type;//奖励类型
    private String name;//产品名称或者合伙人昵称，会员店名称
    private long time;//订单奖励才有的下单时间
    private String costMoney;//消费金额
    private String rewardMoney;//奖励金额

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public long getTime() {
        return time;
    }

    public void setTime(long time) {
        this.time = time;
    }

    public String getCostMoney() {
        return costMoney;
    }

    public void setCostMoney(String costMoney) {
        this.costMoney = costMoney;
    }

    public String getRewardMoney() {
        return rewardMoney;
    }

    public void setRewardMoney(String rewardMoney) {
        this.rewardMoney = rewardMoney;
    }
}
