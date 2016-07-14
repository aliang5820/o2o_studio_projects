package com.fanwe.model;

import java.io.Serializable;

public class Uc_couponActItemModel implements Serializable
{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private String sub_name;
	private String name;
	private int number;
	private String password;
	private String end_time;
	private String confirm_time;
	private int deal_id;
	private int order_id;
	private int order_deal_id;
	private int supplier_id;
	private String couponSn;
	private String less_time;
	private String dealIcon;
	private String spAddress;
	private String spTel;
	private String spName;
	private String qrcode;

	public int getOrder_id()
	{
		return order_id;
	}

	public void setOrder_id(int order_id)
	{
		this.order_id = order_id;
	}

	public String getSub_name()
	{
		return sub_name;
	}

	public void setSub_name(String sub_name)
	{
		this.sub_name = sub_name;
	}

	public String getName()
	{
		return name;
	}

	public void setName(String name)
	{
		this.name = name;
	}

	public int getNumber()
	{
		return number;
	}

	public void setNumber(int number)
	{
		this.number = number;
	}

	public String getLess_time()
	{
		return less_time;
	}

	public void setLess_time(String less_time)
	{
		this.less_time = less_time;
	}

	public String getQrcode()
	{
		return qrcode;
	}

	public void setQrcode(String qrcode)
	{
		this.qrcode = qrcode;
	}

	public String getPassword()
	{
		return password;
	}

	public void setPassword(String password)
	{
		this.password = password;
	}

	public String getEnd_time()
	{
		return end_time;
	}

	public void setEnd_time(String end_time)
	{
		this.end_time = end_time;
	}

	public int getDeal_id()
	{
		return deal_id;
	}

	public void setDeal_id(int deal_id)
	{
		this.deal_id = deal_id;
	}

	public int getOrder_deal_id()
	{
		return order_deal_id;
	}

	public void setOrder_deal_id(int order_deal_id)
	{
		this.order_deal_id = order_deal_id;
	}

	public int getSupplier_id()
	{
		return supplier_id;
	}

	public void setSupplier_id(int supplier_id)
	{
		this.supplier_id = supplier_id;
	}

	public String getConfirm_time()
	{
		return confirm_time;
	}

	public void setConfirm_time(String confirm_time)
	{
		this.confirm_time = confirm_time;
	}

	public String getDealIcon()
	{
		return dealIcon;
	}

	public void setDealIcon(String dealIcon)
	{
		this.dealIcon = dealIcon;
	}

	public String getSpName()
	{
		return spName;
	}

	public void setSpName(String spName)
	{
		this.spName = spName;
	}

	public String getSpTel()
	{
		return spTel;
	}

	public void setSpTel(String spTel)
	{
		this.spTel = spTel;
	}

	public String getSpAddress()
	{
		return spAddress;
	}

	public void setSpAddress(String spAddress)
	{
		this.spAddress = spAddress;
	}

	public String getCouponSn()
	{
		return couponSn;
	}

	public void setCouponSn(String couponSn)
	{
		this.couponSn = couponSn;
	}

}