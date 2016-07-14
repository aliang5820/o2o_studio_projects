package com.fanwe.model;

import java.io.Serializable;

public class MerchantDetailActTuan_listModel implements Serializable
{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private String id = null;
	private String name = null;
	private String sub_name = null;
	private String origin_price = null;
	private String current_price = null;
	private String discount = null;
	private String save_price = null;

	public String getId()
	{
		return id;
	}

	public void setId(String id)
	{
		this.id = id;
	}

	public String getName()
	{
		return name;
	}

	public void setName(String name)
	{
		this.name = name;
	}

	public String getSub_name()
	{
		return sub_name;
	}

	public void setSub_name(String sub_name)
	{
		this.sub_name = sub_name;
	}

	public String getOrigin_price()
	{
		return origin_price;
	}

	public void setOrigin_price(String origin_price)
	{
		this.origin_price = "￥" + origin_price;
	}

	public String getCurrent_price()
	{
		return current_price;
	}

	public void setCurrent_price(String current_price)
	{
		this.current_price = "￥" + current_price;
	}

	public String getDiscount()
	{
		return discount;
	}

	public void setDiscount(String discount)
	{
		this.discount = discount;
	}

	public String getSave_price()
	{
		return save_price;
	}

	public void setSave_price(String save_price)
	{
		this.save_price = "节省：￥" + save_price;
	}
}