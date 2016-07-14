package com.fanwe.model;

import java.io.Serializable;
import java.util.List;

import com.fanwe.library.utils.SDTypeParseUtil;

public class CommentModel implements Serializable
{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private int id; // 点评数据ID
	private String type; // 点评的东西的类型
	private int data_id; // 点评的东西的ID
	private String user_name; // 用户名
	private String content; // 点评内容
	private String create_time; // 点评时间
	private String reply_time; // 回复时间
	private String reply_content; // 回复内容
	private String name; // 点评的东西的名称
	private String point; // 评分

	private List<String> images;
	private List<String> oimages;

	// add
	private float pointFloat;

	public float getPointFloat()
	{
		return pointFloat;
	}

	public int getId()
	{
		return id;
	}

	public void setId(int id)
	{
		this.id = id;
	}

	public String getType()
	{
		return type;
	}

	public void setType(String type)
	{
		this.type = type;
	}

	public String getReply_content()
	{
		return reply_content;
	}

	public void setReply_content(String reply_content)
	{
		this.reply_content = reply_content;
	}

	public String getName()
	{
		return name;
	}

	public void setName(String name)
	{
		this.name = name;
	}

	public String getReply_time()
	{
		return reply_time;
	}

	public void setReply_time(String reply_time)
	{
		this.reply_time = reply_time;
	}

	public List<String> getImages()
	{
		return images;
	}

	public void setImages(List<String> images)
	{
		this.images = images;
	}

	public List<String> getOimages()
	{
		return oimages;
	}

	public void setOimages(List<String> oimages)
	{
		this.oimages = oimages;
	}

	public int getData_id()
	{
		return data_id;
	}

	public void setData_id(int id)
	{
		this.data_id = id;
	}

	public String getContent()
	{
		return content;
	}

	public void setContent(String content)
	{
		this.content = content;
	}

	public String getCreate_time()
	{
		return create_time;
	}

	public void setCreate_time(String create_time)
	{
		this.create_time = create_time;
	}

	public String getPoint()
	{
		return point;
	}

	public void setPoint(String point)
	{
		this.point = point;
		this.pointFloat = SDTypeParseUtil.getFloat(point);
	}

	public String getUser_name()
	{
		return user_name;
	}

	public void setUser_name(String user_name)
	{
		this.user_name = user_name;
	}

}