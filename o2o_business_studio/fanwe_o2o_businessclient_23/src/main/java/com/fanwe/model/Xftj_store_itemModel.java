package com.fanwe.model;

import java.io.Serializable;

@SuppressWarnings("serial")
public class Xftj_store_itemModel implements Serializable
{
	private String id;
	private String name;
	private String use_count;

	public String getName()
	{
		return name;
	}

	public void setName(String name)
	{
		this.name = name;
	}

	public String getUse_count()
	{
		return use_count;
	}

	public void setUse_count(String use_count)
	{
		this.use_count = use_count;
	}

	public String getId()
	{
		return id;
	}

	public void setId(String id)
	{
		this.id = id;
	}

}
