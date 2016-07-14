package com.fanwe.model;

import java.util.List;

public class Biz_goodsoCtlModel extends BaseCtlActModel
{
	private List<Xftj_commodityItemModel> item;
	private PageModel page;

	public List<Xftj_commodityItemModel> getItem()
	{
		return item;
	}

	public void setItem(List<Xftj_commodityItemModel> item)
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
