package com.fanwe.model;

public class BizDealrCtlItemModel
{

	private String id;
	// Type0
	private String sub_name;
	private String current_price;
	private String avg_point;
	private String dp_count;
	// Type 1
	private String name;
	private String total_num;
	private String user_count;
	// Type 2
	private String submit_count;
	private String total_count;
	// Type 3
	private String good_rate;
	private String ref_avg_price;

	private int type = 0;

	public int getType()
	{
		return type;
	}

	public void setType(int type)
	{
		this.type = type;
	}

	public String getName()
	{
		return name;
	}

	public void setName(String name)
	{
		this.name = name;
	}

	public String getTotal_num()
	{
		return total_num;
	}

	public void setTotal_num(String total_num)
	{
		this.total_num = total_num;
	}

	public String getUser_count()
	{
		return user_count;
	}

	public void setUser_count(String user_count)
	{
		this.user_count = user_count;
	}

	public String getSubmit_count()
	{
		return submit_count;
	}

	public void setSubmit_count(String submit_count)
	{
		this.submit_count = submit_count;
	}

	public String getTotal_count()
	{
		return total_count;
	}

	public void setTotal_count(String total_count)
	{
		this.total_count = total_count;
	}

	public String getGood_rate()
	{
		return good_rate;
	}

	public void setGood_rate(String good_rate)
	{
		this.good_rate = good_rate;
	}

	public String getRef_avg_price()
	{
		return ref_avg_price;
	}

	public void setRef_avg_price(String ref_avg_price)
	{
		this.ref_avg_price = ref_avg_price;
	}

	public String getId()
	{
		return id;
	}

	public void setId(String id)
	{
		this.id = id;
	}

	public String getSub_name()
	{
		return sub_name;
	}

	public void setSub_name(String sub_name)
	{
		this.sub_name = sub_name;
	}

	public String getCurrent_price()
	{
		return current_price;
	}

	public void setCurrent_price(String current_price)
	{
		this.current_price = current_price;
	}

	public String getAvg_point()
	{
		return avg_point;
	}

	public void setAvg_point(String avg_point)
	{
		this.avg_point = avg_point;
	}

	public String getDp_count()
	{
		return dp_count;
	}

	public void setDp_count(String dp_count)
	{
		this.dp_count = dp_count;
	}

}
