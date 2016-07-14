package com.fanwe.model;

import java.util.List;

public class Notice_indexActModel extends BaseActModel
{
	private List<NoticeModel> list;

	private PageModel page;

	public List<NoticeModel> getList()
	{
		return list;
	}

	public void setList(List<NoticeModel> list)
	{
		this.list = list;
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
