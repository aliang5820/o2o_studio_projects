package com.fanwe.model;

public class VoucherModel
{

	private String name;
	private String sn;

	public String getName()
	{
		return name;
	}

	public void setName(String name)
	{
		this.name = name;
	}

	public String getSn()
	{
		return sn;
	}

	public void setSn(String sn)
	{
		this.sn = sn;
	}

	@Override
	public String toString()
	{
		return this.name;
	}

}
