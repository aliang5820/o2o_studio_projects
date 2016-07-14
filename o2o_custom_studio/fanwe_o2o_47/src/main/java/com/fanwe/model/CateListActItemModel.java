package com.fanwe.model;

public class CateListActItemModel
{
	private String id = null;
	private String name = null;
	private String pid = null;

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

	public String getPid()
	{
		return pid;
	}

	public void setPid(String pid)
	{
		this.pid = pid;
	}

	public String getPy()
	{
		return py;
	}

	public void setPy(String py)
	{
		this.py = py;
	}

	public String getIcon()
	{
		return icon;
	}

	public void setIcon(String icon)
	{
		this.icon = icon;
	}

	public String getHas_child()
	{
		return has_child;
	}

	public void setHas_child(String has_child)
	{
		this.has_child = has_child;
	}

	private String py = null;
	private String icon = null;
	private String has_child = null;
}