package com.fanwe.model;

import java.util.List;

public class Uc_event_indexActModel extends BaseActModel
{

	private String page_title;
	private List<Uc_eventActItemModel> item;
	private PageModel page;

	public PageModel getPage()
	{
		return page;
	}

	public void setPage(PageModel page)
	{
		this.page = page;
	}

	public List<Uc_eventActItemModel> getItem()
	{
		return item;
	}

	public void setItem(List<Uc_eventActItemModel> item)
	{
		this.item = item;
	}

	public String getPage_title()
	{
		return page_title;
	}

	public void setPage_title(String page_title)
	{
		this.page_title = page_title;
	}

}
