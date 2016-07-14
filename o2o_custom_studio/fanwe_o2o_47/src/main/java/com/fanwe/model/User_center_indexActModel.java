package com.fanwe.model;

public class User_center_indexActModel extends BaseActModel
{

	private int uid;
	private String user_name;
	private String user_money_format;
	private String user_avatar;

	private String user_money;
	private String user_score;
	private String coupon_count;
	private String youhui_count;
	private String wait_dp_count;
	private String not_pay_order_count;

	public String getWait_dp_count()
	{
		return wait_dp_count;
	}

	public void setWait_dp_count(String wait_dp_count)
	{
		this.wait_dp_count = wait_dp_count;
	}

	public String getUser_avatar()
	{
		return user_avatar;
	}

	public void setUser_avatar(String user_avatar)
	{
		this.user_avatar = user_avatar;
	}

	public int getUid()
	{
		return uid;
	}

	public void setUid(int uid)
	{
		this.uid = uid;
	}

	public String getUser_name()
	{
		return user_name;
	}

	public void setUser_name(String user_name)
	{
		this.user_name = user_name;
	}

	public String getUser_money()
	{
		return user_money;
	}

	public void setUser_money(String user_money)
	{
		this.user_money = user_money;
	}

	public String getUser_money_format()
	{
		return user_money_format;
	}

	public void setUser_money_format(String user_money_format)
	{
		this.user_money_format = user_money_format;
	}

	public String getUser_score()
	{
		return user_score;
	}

	public void setUser_score(String user_score)
	{
		this.user_score = user_score;
	}

	public String getCoupon_count()
	{
		return coupon_count;
	}

	public void setCoupon_count(String coupon_count)
	{
		this.coupon_count = coupon_count;
	}

	public String getYouhui_count()
	{
		return youhui_count;
	}

	public void setYouhui_count(String youhui_count)
	{
		this.youhui_count = youhui_count;
	}

	public String getNot_pay_order_count()
	{
		return not_pay_order_count;
	}

	public void setNot_pay_order_count(String not_pay_order_count)
	{
		this.not_pay_order_count = not_pay_order_count;
	}

}
