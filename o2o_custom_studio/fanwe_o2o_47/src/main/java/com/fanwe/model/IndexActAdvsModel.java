package com.fanwe.model;

import java.io.Serializable;

public class IndexActAdvsModel implements Serializable
{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private int id;
	private String name;
	private String img;
	private int type;
	private AdvsDataModel data;
	private String ctl;

	public String getCtl()
	{
		return ctl;
	}

	public void setCtl(String ctl)
	{
		this.ctl = ctl;
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

	public AdvsDataModel getData()
	{
		return data;
	}

	public void setData(AdvsDataModel data)
	{
		this.data = data;
	}

	@Override
	public String toString()
	{
		return this.img;
	}

}
