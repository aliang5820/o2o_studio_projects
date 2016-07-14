package com.fanwe.model;

import java.io.Serializable;

public class IndexActIndexsModel implements Serializable
{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private int id;
	private String name;
	private String icon_name;
	private String color;
	private String img;
	private int type;
	private AdvsDataModel data;

	// /////////////////add
	private String temp;

	public String getIcon_name()
	{
		return icon_name;
	}

	public void setIcon_name(String icon_name)
	{
		this.icon_name = icon_name;
	}

	public String getColor()
	{
		return color;
	}

	public void setColor(String color)
	{
		this.color = color;
	}

	public void setTempName(String tempName)
	{
		this.temp = this.name;
		this.name = tempName;
	}

	public void restoreOriginalName()
	{
		this.name = this.temp;
	}

	public AdvsDataModel getData()
	{
		return data;
	}

	public void setData(AdvsDataModel data)
	{
		this.data = data;
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

	public String getImg()
	{
		return img;
	}

	public void setImg(String img)
	{
		this.img = img;
	}

	public int getType()
	{
		return type;
	}

	public void setType(int type)
	{
		this.type = type;
	}

}