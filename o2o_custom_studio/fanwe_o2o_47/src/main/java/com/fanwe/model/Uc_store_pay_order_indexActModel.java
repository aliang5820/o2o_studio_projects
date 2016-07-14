package com.fanwe.model;

import java.util.List;

public class Uc_store_pay_order_indexActModel extends BaseActModel
{

	private List<StoreOrderModel> item;
	private PageModel page;

	public List<StoreOrderModel> getItem()
	{
		return item;
	}

	public void setItem(List<StoreOrderModel> item)
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
