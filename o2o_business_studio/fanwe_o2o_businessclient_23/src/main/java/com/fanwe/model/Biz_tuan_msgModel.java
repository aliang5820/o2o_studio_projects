package com.fanwe.model;

import java.util.ArrayList;

/**
 * 
 * @author yhz
 * @create time 2014-8-5
 */
public class Biz_tuan_msgModel
{
	private String id;
	private String create_time;
	private String content;
	private String reply_content;
	private String point_percent;
	private String point;
	private String user_name;
	private ArrayList<String> images;

	public String getId()
	{
		return id;
	}

	public void setId(String id)
	{
		this.id = id;
	}

	public String getUser_name()
	{
		return user_name;
	}

	public void setUser_name(String user_name)
	{
		this.user_name = user_name;
	}

	public String getPoint()
	{
		return point;
	}

	public void setPoint(String point)
	{
		this.point = point;
	}

	public String getCreate_time()
	{
		return create_time;
	}

	public void setCreate_time(String create_time)
	{
		this.create_time = create_time;
	}

	public String getContent()
	{
		return content;
	}

	public void setContent(String content)
	{
		this.content = content;
	}

	public String getReply_content()
	{
		return reply_content;
	}

	public void setReply_content(String reply_content)
	{
		this.reply_content = reply_content;
	}

	public String getPoint_percent()
	{
		return point_percent;
	}

	public void setPoint_percent(String point_percent)
	{
		this.point_percent = point_percent;
	}

	public ArrayList<String> getImages()
	{
		return images;
	}

	public void setImages(ArrayList<String> images)
	{
		this.images = images;
	}

}
