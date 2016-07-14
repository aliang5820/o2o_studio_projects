package com.fanwe.model;

public class Jygl_couponYouhuiModel
{
	private String id;
	private String name; // 标题
	private String use_count; // 总共消费
	private String f_end_time; // 有效期时间

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

	public String getF_end_time()
	{
		return f_end_time;
	}

	public void setF_end_time(String f_end_time)
	{
		this.f_end_time = f_end_time;
	}

	public String getUse_count()
	{
		return use_count;
	}

	public void setUse_count(String use_count)
	{
		this.use_count = use_count;
	}

}
