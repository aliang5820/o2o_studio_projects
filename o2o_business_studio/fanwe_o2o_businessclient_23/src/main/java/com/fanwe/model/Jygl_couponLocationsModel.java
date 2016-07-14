package com.fanwe.model;

import java.io.Serializable;

@SuppressWarnings("serial")
public class Jygl_couponLocationsModel implements Serializable
{
	private String location_id;
	private String name; // 门店
	private String use_count; // 消费数量

	public void setName(String name)
	{
		this.name = name;
	}

	public String getName()
	{
		return name;
	}

	public String getUse_count()
	{
		return use_count;
	}

	public void setUse_count(String use_count)
	{
		this.use_count = use_count;
	}

	public String getLocation_id()
	{
		return location_id;
	}

	public void setLocation_id(String location_id)
	{
		this.location_id = location_id;
	}

}
