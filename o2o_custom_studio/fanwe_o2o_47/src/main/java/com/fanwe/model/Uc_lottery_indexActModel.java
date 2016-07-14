package com.fanwe.model;

import java.util.List;

public class Uc_lottery_indexActModel extends BaseActModel
{

	private List<Uc_lotteryActItemModel> item;
	private PageModel page;

	public PageModel getPage()
	{
		return page;
	}

	public void setPage(PageModel page)
	{
		this.page = page;
	}

	public List<Uc_lotteryActItemModel> getItem()
	{
		return item;
	}

	public void setItem(List<Uc_lotteryActItemModel> item)
	{
		this.item = item;
	}

}
