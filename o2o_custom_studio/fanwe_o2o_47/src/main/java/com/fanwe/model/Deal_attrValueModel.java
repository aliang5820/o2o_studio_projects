package com.fanwe.model;

import com.fanwe.library.adapter.SDAdapter.SDSelectable;
import com.fanwe.library.utils.SDTypeParseUtil;

public class Deal_attrValueModel implements SDSelectable
{

	private int id; // 套餐编号
	private String name; // 套餐名称
	private String price; // 递增的价格

	// add
	private double price_double;
	private boolean selected;

	public int getId()
	{
		return id;
	}

	public void setId(int id)
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

	public String getPrice()
	{
		return price;
	}

	public void setPrice(String price)
	{
		this.price = price;
		this.price_double = SDTypeParseUtil.getDouble(price);
	}

	public double getPrice_double()
	{
		return price_double;
	}

	public void setPrice_double(double price_double)
	{
		this.price_double = price_double;
	}

	public boolean isSelected()
	{
		return selected;
	}

	public void setSelected(boolean selected)
	{
		this.selected = selected;
	}

}
