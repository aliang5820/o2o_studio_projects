package com.fanwe.model;

import java.io.Serializable;

import com.fanwe.library.utils.SDTypeParseUtil;
import com.fanwe.utils.SDFormatUtil;

public class CartGoodsModel implements Serializable
{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private int id;
	private int return_score;
	private int return_total_score;
	private String unit_price;
	private String total_price;
	private int number;
	private int deal_id;
	private String attr;
	private String name;
	private String sub_name;
	private int max;
	private String icon;

	// add
	private String unit_priceFormat;
	private double unit_priceDouble;

	public String getUnit_priceFormat()
	{
		return unit_priceFormat;
	}

	public String getTotal_priceFormat()
	{
		return SDFormatUtil.formatMoneyChina(this.unit_priceDouble * number);
	}

	public String getReturn_scoreFormat()
	{
		return return_score + "积分";
	}

	public String getReturn_total_scoreFormat()
	{
		return return_score * number + "积分";
	}

	public int getId()
	{
		return id;
	}

	public void setId(int id)
	{
		this.id = id;
	}

	public int getReturn_score()
	{
		return return_score;
	}

	public void setReturn_score(int return_score)
	{
		this.return_score = return_score;
	}

	public int getReturn_total_score()
	{
		return return_total_score;
	}

	public void setReturn_total_score(int return_total_score)
	{
		this.return_total_score = return_total_score;
	}

	public String getUnit_price()
	{
		return unit_price;
	}

	public void setUnit_price(String unit_price)
	{
		this.unit_price = unit_price;
		this.unit_priceFormat = SDFormatUtil.formatMoneyChina(unit_price);
		this.unit_priceDouble = SDTypeParseUtil.getDouble(unit_price);
	}

	public String getTotal_price()
	{
		return total_price;
	}

	public void setTotal_price(String total_price)
	{
		this.total_price = total_price;
	}

	public int getNumber()
	{
		return number;
	}

	public void setNumber(int number)
	{
		this.number = number;
	}

	public int getDeal_id()
	{
		return deal_id;
	}

	public void setDeal_id(int deal_id)
	{
		this.deal_id = deal_id;
	}

	public String getAttr()
	{
		return attr;
	}

	public void setAttr(String attr)
	{
		this.attr = attr;
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

	public int getMax()
	{
		return max;
	}

	public void setMax(int max)
	{
		this.max = max;
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
