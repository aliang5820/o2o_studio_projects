package com.fanwe.model;

import com.fanwe.library.adapter.SDAdapter.SDSelectable;

public class DiscoveryTagModel implements SDSelectable
{

	private int id;
	private String name;

	// add
	private boolean selected;

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

	public boolean isSelected()
	{
		return selected;
	}

	public void setSelected(boolean selected)
	{
		this.selected = selected;
	}

}
