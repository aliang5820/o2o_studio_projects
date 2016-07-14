package com.fanwe.model;

public class Xftj_buyModel
{
	private String id;
	private String sub_name; // 商品名
	private String f_create_time; // 时间
	private String buy_count; // 销售量
	private String refund_count; // 退款量
	private String confirm_count; // 验证量
	private String icon; // 图标

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

	public String getConfirm_count()
	{
		return confirm_count;
	}

	public void setConfirm_count(String confirm_count)
	{
		this.confirm_count = confirm_count;
	}

	public String getIcon()
	{
		return icon;
	}

	public void setIcon(String icon)
	{
		this.icon = icon;
	}

	public String getId()
	{
		return id;
	}

	public void setId(String id)
	{
		this.id = id;
	}

}
