package com.fanwe.model;

import java.util.List;

public class VoucherActBcate_listModel
{
	private String id = null;
	private String name = null;

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

	public List<VoucherActBcate_listBcate_typeModel> getBcate_type()
	{
		return bcate_type;
	}

	public void setBcate_type(List<VoucherActBcate_listBcate_typeModel> bcate_type)
	{
		this.bcate_type = bcate_type;
	}

	private List<VoucherActBcate_listBcate_typeModel> bcate_type = null;
}