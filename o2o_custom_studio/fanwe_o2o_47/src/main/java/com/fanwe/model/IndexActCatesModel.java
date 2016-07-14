package com.fanwe.model;

import java.io.Serializable;

public class IndexActCatesModel implements Serializable
{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private String id;
	private String name;
	private String icon_img;
	private String recommend;

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

	public String getRecommend()
	{
		return recommend;
	}

	public void setRecommend(String recommend)
	{
		this.recommend = recommend;
	}

}
