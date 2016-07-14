package com.fanwe.model;

public class Uc_home_focusActModel extends BaseActModel
{

	private int tag; // 1取消关注， 2关注， 3不能关注自己， 4用户未登录
	private String html;

	public int getTag()
	{
		return tag;
	}

	public void setTag(int tag)
	{
		this.tag = tag;
	}

	public String getHtml()
	{
		return html;
	}

	public void setHtml(String html)
	{
		this.html = html;
	}

}
