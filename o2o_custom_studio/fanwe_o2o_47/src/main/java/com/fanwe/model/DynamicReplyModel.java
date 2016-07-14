package com.fanwe.model;

public class DynamicReplyModel implements Cloneable
{

	private int id; // 回复数据ID
	private int topic_id; // 回复主题ID
	private int user_id; // 回复用户ID
	private String user_name; // 回复用户名称
	private String content; // 回复内容
	private String create_time; // 回复时间
	private int reply_id; // 其它回复人ID
	private int reply_user_id; // 被回复人的ID
	private String reply_user_name; // 被回复人的名称
	private String format_create_time; // 格式化时间

	public int getId()
	{
		return id;
	}

	public void setId(int id)
	{
		this.id = id;
	}

	public int getTopic_id()
	{
		return topic_id;
	}

	public void setTopic_id(int topic_id)
	{
		this.topic_id = topic_id;
	}

	public int getUser_id()
	{
		return user_id;
	}

	public void setUser_id(int user_id)
	{
		this.user_id = user_id;
	}

	public String getUser_name()
	{
		return user_name;
	}

	public void setUser_name(String user_name)
	{
		this.user_name = user_name;
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

	public int getReply_id()
	{
		return reply_id;
	}

	public void setReply_id(int reply_id)
	{
		this.reply_id = reply_id;
	}

	public int getReply_user_id()
	{
		return reply_user_id;
	}

	public void setReply_user_id(int reply_user_id)
	{
		this.reply_user_id = reply_user_id;
	}

	public String getReply_user_name()
	{
		return reply_user_name;
	}

	public void setReply_user_name(String reply_user_name)
	{
		this.reply_user_name = reply_user_name;
	}

	public String getFormat_create_time()
	{
		return format_create_time;
	}

	public void setFormat_create_time(String format_create_time)
	{
		this.format_create_time = format_create_time;
	}

	@Override
	protected DynamicReplyModel clone() throws CloneNotSupportedException
	{
		DynamicReplyModel model = new DynamicReplyModel();
		model.setContent(content);
		model.setCreate_time(create_time);
		model.setFormat_create_time(format_create_time);
		model.setId(id);
		model.setReply_id(reply_id);
		model.setReply_user_id(reply_user_id);
		model.setReply_user_name(reply_user_name);
		model.setTopic_id(topic_id);
		model.setUser_id(user_id);
		model.setUser_name(user_name);
		return model;
	}

}
