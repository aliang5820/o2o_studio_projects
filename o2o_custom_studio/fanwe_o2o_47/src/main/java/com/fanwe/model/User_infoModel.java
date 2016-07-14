package com.fanwe.model;

public class User_infoModel extends BaseActModel
{
	private int id;
	private String user_name;
	private String user_pwd;
	private String email;
	private int is_tmp;
	private String user_avatar;
	private String mobile;
	private String mobile_format;
	private String money;
	private String money_format;
	private String consignee;

	public String getMobile_format()
	{
		return mobile_format;
	}

	public void setMobile_format(String mobile_format)
	{
		this.mobile_format = mobile_format;
	}

	public int getId()
	{
		return id;
	}

	public void setId(int id)
	{
		this.id = id;
	}

	public String getUser_name()
	{
		return user_name;
	}

	public void setUser_name(String user_name)
	{
		this.user_name = user_name;
	}

	public String getUser_pwd()
	{
		return user_pwd;
	}

	public void setUser_pwd(String user_pwd)
	{
		this.user_pwd = user_pwd;
	}

	public String getEmail()
	{
		return email;
	}

	public void setEmail(String email)
	{
		this.email = email;
	}

	public int getIs_tmp()
	{
		return is_tmp;
	}

	public void setIs_tmp(int is_tmp)
	{
		this.is_tmp = is_tmp;
	}

	public String getUser_avatar()
	{
		return user_avatar;
	}

	public void setUser_avatar(String user_avatar)
	{
		this.user_avatar = user_avatar;
	}

	public String getMobile()
	{
		return mobile;
	}

	public void setMobile(String mobile)
	{
		this.mobile = mobile;
	}

	public String getConsignee()
	{
		return consignee;
	}

	public void setConsignee(String consignee)
	{
		this.consignee = consignee;
	}

	public String getMoney()
	{
		return money;
	}

	public void setMoney(String money)
	{
		this.money = money;
	}

	public String getMoney_format()
	{
		return money_format;
	}

	public void setMoney_format(String money_format)
	{
		this.money_format = money_format;
	}

}
