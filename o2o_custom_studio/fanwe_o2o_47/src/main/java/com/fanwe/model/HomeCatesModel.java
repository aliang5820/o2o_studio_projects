package com.fanwe.model;

public class HomeCatesModel
{
	private String id = null;
	private String name = null;
	private String icon_img = null;
	private String recommend = null;

	public String getRecommend()
	{
		return recommend;
	}

	public void setRecommend(String recommend)
	{
		this.recommend = recommend;
	}

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

	public String getIcon_img()
	{
		return icon_img;
	}

	public void setIcon_img(String icon_img)
	{
		this.icon_img = icon_img;
	}

}