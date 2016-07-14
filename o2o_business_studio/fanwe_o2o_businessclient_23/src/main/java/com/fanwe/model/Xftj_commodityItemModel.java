package com.fanwe.model;

public class Xftj_commodityItemModel
{
	private String id;
	private String sub_name; // 标题
	private String f_create_time; // 发布时间
	private String buy_count; // 销售量
	private String refund_count; // 退款量
	private String icon; // 图片

	public String getId()
	{
		return id;
	}

	public void setId(String id)
	{
		this.id = id;
	}

	public String getSub_name()
	{
		return sub_name;
	}

	public void setSub_name(String sub_name)
	{
		this.sub_name = sub_name;
	}

	public String getF_create_time()
	{
		return f_create_time;
	}

	public void setF_create_time(String f_create_time)
	{
		this.f_create_time = f_create_time;
	}

	public String getBuy_count()
	{
		return buy_count;
	}

	public void setBuy_count(String buy_count)
	{
		this.buy_count = buy_count;
	}

	public String getRefund_count()
	{
		return refund_count;
	}

	public void setRefund_count(String refund_count)
	{
		this.refund_count = refund_count;
	}

	public String getIcon()
	{
		return icon;
	}

	public void setIcon(String icon)
	{
		this.icon = icon;
	}

}
