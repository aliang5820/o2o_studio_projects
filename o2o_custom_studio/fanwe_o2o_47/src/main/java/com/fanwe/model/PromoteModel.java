package com.fanwe.model;

public class PromoteModel
{
	private int id;
	private String name;
	private String class_name;
	private int sort;
	private String config;
	private String description;
	private int type;
	private int supplier_id;
	private int supplier_or_platform;

	private String discount_price;

	// add
	private String discount_priceFormat;

	public String getDiscount_priceFormat()
	{
		return discount_priceFormat;
	}

	public String getDiscount_price()
	{
		return discount_price;
	}

	public void setDiscount_price(String discount_price)
	{
		this.discount_price = discount_price;
		this.discount_priceFormat = "-¥" + discount_price;
	}

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

	public String getClass_name()
	{
		return class_name;
	}

	public void setClass_name(String class_name)
	{
		this.class_name = class_name;
	}

	public int getSort()
	{
		return sort;
	}

	public void setSort(int sort)
	{
		this.sort = sort;
	}

	public String getConfig()
	{
		return config;
	}

	public void setConfig(String config)
	{
		this.config = config;
	}

	public String getDescription()
	{
		return description;
	}

	public void setDescription(String description)
	{
		this.description = description;
	}

	public int getType()
	{
		return type;
	}

	public void setType(int type)
	{
		this.type = type;
	}

	public int getSupplier_id()
	{
		return supplier_id;
	}

	public void setSupplier_id(int supplier_id)
	{
		this.supplier_id = supplier_id;
	}

	public int getSupplier_or_platform()
	{
		return supplier_or_platform;
	}

	public void setSupplier_or_platform(int supplier_or_platform)
	{
		this.supplier_or_platform = supplier_or_platform;
	}

}
