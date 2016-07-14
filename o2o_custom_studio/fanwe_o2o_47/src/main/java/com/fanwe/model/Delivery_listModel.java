package com.fanwe.model;

/**
 * 配送方式实体
 * 
 * @author js02
 * 
 */
public class Delivery_listModel
{

	private int id;
	private String code;
	private String name;
	private String description;
	private String has_calc;

	public String getDescription()
	{
		return description;
	}

	public void setDescription(String description)
	{
		this.description = description;
	}

	public int getId()
	{
		return id;
	}

	public void setId(int id)
	{
		this.id = id;
	}

	public String getCode()
	{
		return code;
	}

	public void setCode(String code)
	{
		this.code = code;
	}

	public String getName()
	{
		return name;
	}

	public void setName(String name)
	{
		this.name = name;
	}

	public String getHas_calc()
	{
		return has_calc;
	}

	public void setHas_calc(String has_calc)
	{
		this.has_calc = has_calc;
	}

	@Override
	public String toString()
	{
		return getName();
	}

}
