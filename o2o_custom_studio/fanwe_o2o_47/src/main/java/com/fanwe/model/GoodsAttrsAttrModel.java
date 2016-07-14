package com.fanwe.model;

import java.io.Serializable;

public class GoodsAttrsAttrModel implements Serializable
{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private String attr_id = null;
	private String attr_name = null;
	private String attr_image = null;
	private String attr_price = null;
	private String attr_price_format = null;

	public String getAttr_id()
	{
		return attr_id;
	}

	public void setAttr_id(String attr_id)
	{
		this.attr_id = attr_id;
	}

	public String getAttr_name()
	{
		return attr_name;
	}

	public void setAttr_name(String attr_name)
	{
		this.attr_name = attr_name;
	}

	public String getAttr_image()
	{
		return attr_image;
	}

	public void setAttr_image(String attr_image)
	{
		this.attr_image = attr_image;
	}

	public String getAttr_price()
	{
		return attr_price;
	}

	public void setAttr_price(String attr_price)
	{
		this.attr_price = attr_price;
	}

	public String getAttr_price_format()
	{
		return attr_price_format;
	}

	public void setAttr_price_format(String attr_price_format)
	{
		this.attr_price_format = attr_price_format;
	}

}