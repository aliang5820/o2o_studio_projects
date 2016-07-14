package com.fanwe.model;

import com.fanwe.library.adapter.SDAdapter.SDSelectable;

/**
 * 支付方式实体
 * 
 * @author js02
 * 
 */
public class Payment_listModel implements SDSelectable
{

	private int id;
	private String code;
	private String name;
	private String logo;

	// add
	private boolean selected;

	public boolean isSelected()
	{
		return selected;
	}

	public void setSelected(boolean selected)
	{
		this.selected = selected;
	}

	public String getLogo()
	{
		return logo;
	}

	public void setLogo(String logo)
	{
		this.logo = logo;
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

}
