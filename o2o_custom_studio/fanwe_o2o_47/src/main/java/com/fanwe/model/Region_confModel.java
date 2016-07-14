package com.fanwe.model;

import org.xutils.db.annotation.Column;
import org.xutils.db.annotation.Table;

@Table(name = "Region_confModel")
public class Region_confModel
{

	@Column(name = "id", isId = true, autoGen = false)
	private int id;

	@Column(name = "pid")
	private int pid;

	@Column(name = "name")
	private String name;

	@Column(name = "region_level")
	private String region_level;

	public int getId()
	{
		return id;
	}

	public void setId(int id)
	{
		this.id = id;
	}

	public int getPid()
	{
		return pid;
	}

	public void setPid(int pid)
	{
		this.pid = pid;
	}

	public String getName()
	{
		return name;
	}

	public void setName(String name)
	{
		this.name = name;
	}

	public String getRegion_level()
	{
		return region_level;
	}

	public void setRegion_level(String region_level)
	{
		this.region_level = region_level;
	}

	@Override
	public String toString()
	{
		return getName();
	}

}
