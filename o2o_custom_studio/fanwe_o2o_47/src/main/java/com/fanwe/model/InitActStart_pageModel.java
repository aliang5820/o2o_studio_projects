package com.fanwe.model;

public class InitActStart_pageModel
{
	private int id;
	private int type;
	private String name;
	private String img;
	private AdvsDataModel data;

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

	public int getType()
	{
		return type;
	}

	public void setType(int type)
	{
		this.type = type;
	}

	public String getImg()
	{
		return img;
	}

	public void setImg(String img)
	{
		this.img = img;
	}

	public AdvsDataModel getData()
	{
		return data;
	}

	public void setData(AdvsDataModel data)
	{
		this.data = data;
	}

}
