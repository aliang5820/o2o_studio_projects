package com.fanwe.model;

import java.util.List;

public class Uc_youhui_indexActModel extends BaseActModel
{
	private List<Uc_youhuiActItemModel> item;
	private PageModel page;

	public List<Uc_youhuiActItemModel> getItem()
	{
		return item;
	}

	public void setItem(List<Uc_youhuiActItemModel> item)
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