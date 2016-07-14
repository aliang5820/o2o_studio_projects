package com.fanwe.model;

public class Jygl_couponListModel
{
	private String id; 
	private String name; //标题
	private String f_end_time; //过期时间
	private String use_count;  //已使用
	private String user_count; //已下载
	private String icon;  //图片

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

	public String getUser_count()
	{
		return user_count;
	}

	public void setUser_count(String user_count)
	{
		this.user_count = user_count;
	}

	public String getUse_count()
	{
		return use_count;
	}

	public void setUse_count(String use_count)
	{
		this.use_count = use_count;
	}

	public String getIcon()
	{
		return icon;
	}

	public void setIcon(String icon)
	{
		this.icon = icon;
	}

}
