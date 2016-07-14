package com.fanwe.model;

public class Wap_qrcodeActModel extends BaseActModel
{

	private String id;// 门店ID
	private String name;// 门店名称
	private String mobile_brief;// 手机端列表简介
	private String address;
	private String op_type;// 操作类型; 1:打开手机端的门店详细页; 0:打开下单界面

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

	public String getMobile_brief()
	{
		return mobile_brief;
	}

	public void setMobile_brief(String mobile_brief)
	{
		this.mobile_brief = mobile_brief;
	}

	public String getAddress()
	{
		return address;
	}

	public void setAddress(String address)
	{
		this.address = address;
	}

	public String getOp_type()
	{
		return op_type;
	}

	public void setOp_type(String op_type)
	{
		this.op_type = op_type;
	}

}
