package com.fanwe.model;

import java.io.Serializable;
import java.util.List;

public class MapBaseRouteModel implements Serializable
{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	protected String name;
	protected int sort;
	protected String time;
	protected String distance;

	protected List<String> listStep;

	public List<String> getListStep()
	{
		return listStep;
	}

	public void setListStep(List<String> listStep)
	{
		this.listStep = listStep;
	}

	public String getName()
	{
		return name;
	}

	public void setName(String name)
	{
		this.name = name;
	}

	public int getSort()
	{
		return sort;
	}

	public void setSort(int sort)
	{
		this.sort = sort;
	}

	public String getTime()
	{
		return time;
	}

	public void setTime(String time)
	{
		this.time = time;
	}

	public String getDistance()
	{
		return distance;
	}

	public void setDistance(String distance)
	{
		this.distance = distance;
	}

}
