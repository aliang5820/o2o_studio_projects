package com.fanwe.model;

import java.util.List;

public class Jygl_couponModel extends BaseCtlActModel
{
	private List<Jygl_couponListModel> item;
	private PageModel page;

	public List<Jygl_couponListModel> getItem()
	{
		return item;
	}

	public void setItem(List<Jygl_couponListModel> item)
	{
		this.item = item;
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
