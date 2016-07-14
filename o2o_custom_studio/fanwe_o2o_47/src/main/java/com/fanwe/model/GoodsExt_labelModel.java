package com.fanwe.model;

import java.io.Serializable;

public class GoodsExt_labelModel implements Serializable
{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private String type = null;
	private String ico = null;
	private String name = null;

	public String getType()
	{
		return type;
	}

	public void setType(String type)
	{
		this.type = type;
	}

	public String getIco()
	{
		return ico;
	}

	public void setIco(String ico)
	{
		this.ico = ico;
	}

	public String getName()
	{
		return name;
	}

	public void setName(String name)
	{
		this.name = name;
	}

}
