package com.fanwe.model;

import java.util.List;

public class Store_pay_promoteActModel extends BaseActModel
{

	private int location_id;
	private String location_name;
	private int supplier_id;
	private List<PromoteModel> promote;

	private String discount_price;
	private String pay_price;
	private String money;

	// add
	private String pay_priceFormat;

	public String getPay_priceFormat()
	{
		return pay_priceFormat;
	}

	public String getMoney()
	{
		return money;
	}

	public void setMoney(String money)
	{
		this.money = money;
	}

	public String getDiscount_price()
	{
		return discount_price;
	}

	public void setDiscount_price(String discount_price)
	{
		this.discount_price = discount_price;
	}

	public String getPay_price()
	{
		return pay_price;
	}

	public void setPay_price(String pay_price)
	{
		this.pay_price = pay_price;
		this.pay_priceFormat = "¥" + pay_price;
	}

	public int getLocation_id()
	{
		return location_id;
	}

	public void setLocation_id(int location_id)
	{
		this.location_id = location_id;
	}

	public String getLocation_name()
	{
		return location_name;
	}

	public void setLocation_name(String location_name)
	{
		this.location_name = location_name;
	}

	public int getSupplier_id()
	{
		return supplier_id;
	}

	public void setSupplier_id(int supplier_id)
	{
		this.supplier_id = supplier_id;
	}

	public List<PromoteModel> getPromote()
	{
		return promote;
	}

	public void setPromote(List<PromoteModel> promote)
	{
		this.promote = promote;
	}

}
