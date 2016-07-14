package com.fanwe.model;

public class HomeAdvsModel
{
	private String id = null;
	private String name = null;
	private String img = null;
	private String type = null;

	private HomeAdvsDataModel data = null;

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

	public String getImg()
	{
		return img;
	}

	public void setImg(String img)
	{
		this.img = img;
	}

	public String getType()
	{
		return type;
	}

	public void setType(String type)
	{
		this.type = type;
	}

	public HomeAdvsDataModel getData()
	{
		return data;
	}

	public void setData(HomeAdvsDataModel data)
	{
		this.data = data;
	}

}