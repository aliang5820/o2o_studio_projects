package com.fanwe.model;

import java.util.List;

public class Uc_coupon_indexActModel extends BaseActModel
{
	private List<Uc_couponActItemModel> item;
	private PageModel page;

	public List<Uc_couponActItemModel> getItem()
	{
		return item;
	}

	public void setItem(List<Uc_couponActItemModel> item)
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