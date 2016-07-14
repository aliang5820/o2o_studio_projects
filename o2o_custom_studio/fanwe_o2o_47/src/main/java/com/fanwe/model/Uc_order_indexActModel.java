package com.fanwe.model;

import java.util.List;

public class Uc_order_indexActModel extends BaseActModel
{

	private PageModel page;

	private List<Uc_orderModel> item;

	public List<Uc_orderModel> getItem()
	{
		return item;
	}

	public void setItem(List<Uc_orderModel> item)
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
