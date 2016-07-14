package com.fanwe.model;

import java.util.List;

public class Uc_home_load_move_replyActModel extends BaseActModel
{

	private List<DynamicReplyModel> reply_data;
	private PageModel page;

	public List<DynamicReplyModel> getReply_data()
	{
		return reply_data;
	}

	public void setReply_data(List<DynamicReplyModel> reply_data)
	{
		this.reply_data = reply_data;
	}

	public PageModel getPage()
	{
		return page;
	}

	public void setPage(PageModel page)
	{
		this.page = page;
	}

}
