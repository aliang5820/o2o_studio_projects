package com.fanwe.model;

import java.io.Serializable;

public class Consignee_infoModel implements Serializable
{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private int id;
	private int user_id; // 当前会员ID
	private int region_lv1; // 国ID
	private int region_lv2; // 省ID
	private int region_lv3; // 市ID
	private int region_lv4; // 区ID

	private String address; // 详细地址
	private String mobile; // 手机号
	private String zip; // 邮编
	private String consignee; // 收件人姓名
	private int is_default; // 1 默认地址

	private String region_lv1_name; // 国名
	private String region_lv2_name; // 省名
	private String region_lv3_name; // 市名
	private String region_lv4_name; // 区名

	public String getAddressRegion()
	{
		StringBuilder sb = new StringBuilder();
		sb.append(region_lv1_name);
		sb.append(" ");
		sb.append(region_lv2_name);
		sb.append(" ");
		sb.append(region_lv3_name);
		sb.append(" ");
		sb.append(region_lv4_name);
		return sb.toString();
	}

	public String getAddressLong()
	{
		StringBuilder sb = new StringBuilder();
		sb.append(getAddressRegion());
		sb.append(" ");
		sb.append(address);
		return sb.toString();
	}

	public int getId()
	{
		return id;
	}

	public void setId(int id)
	{
		this.id = id;
	}

	public int getUser_id()
	{
		return user_id;
	}

	public void setUser_id(int user_id)
	{
		this.user_id = user_id;
	}

	public int getRegion_lv1()
	{
		return region_lv1;
	}

	public void setRegion_lv1(int region_lv1)
	{
		this.region_lv1 = region_lv1;
	}

	public int getRegion_lv2()
	{
		return region_lv2;
	}

	public void setRegion_lv2(int region_lv2)
	{
		this.region_lv2 = region_lv2;
	}

	public int getRegion_lv3()
	{
		return region_lv3;
	}

	public void setRegion_lv3(int region_lv3)
	{
		this.region_lv3 = region_lv3;
	}

	public int getRegion_lv4()
	{
		return region_lv4;
	}

	public void setRegion_lv4(int region_lv4)
	{
		this.region_lv4 = region_lv4;
	}

	public String getAddress()
	{
		return address;
	}

	public void setAddress(String address)
	{
		this.address = address;
	}

	public String getMobile()
	{
		return mobile;
	}

	public void setMobile(String mobile)
	{
		this.mobile = mobile;
	}

	public String getZip()
	{
		return zip;
	}

	public void setZip(String zip)
	{
		this.zip = zip;
	}

	public String getConsignee()
	{
		return consignee;
	}

	public void setConsignee(String consignee)
	{
		this.consignee = consignee;
	}

	public int getIs_default()
	{
		return is_default;
	}

	public void setIs_default(int is_default)
	{
		this.is_default = is_default;
	}

	public String getRegion_lv1_name()
	{
		return region_lv1_name;
	}

	public void setRegion_lv1_name(String region_lv1_name)
	{
		this.region_lv1_name = region_lv1_name;
	}

	public String getRegion_lv2_name()
	{
		return region_lv2_name;
	}

	public void setRegion_lv2_name(String region_lv2_name)
	{
		this.region_lv2_name = region_lv2_name;
	}

	public String getRegion_lv3_name()
	{
		return region_lv3_name;
	}

	public void setRegion_lv3_name(String region_lv3_name)
	{
		this.region_lv3_name = region_lv3_name;
	}

	public String getRegion_lv4_name()
	{
		return region_lv4_name;
	}

	public void setRegion_lv4_name(String region_lv4_name)
	{
		this.region_lv4_name = region_lv4_name;
	}

}
