package com.fanwe.model;

public class Do_replyDataModel
{

	private int reply_id;
	private int user_id;
	private String content;
	private String user_name;

	public int getReply_id()
	{
		return reply_id;
	}

	public void setReply_id(int reply_id)
	{
		this.reply_id = reply_id;
	}

	public int getUser_id()
	{
		return user_id;
	}

	public void setUser_id(int user_id)
	{
		this.user_id = user_id;
	}

	public String getContent()
	{
		return content;
	}

	public void setContent(String content)
	{
		this.content = content;
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
