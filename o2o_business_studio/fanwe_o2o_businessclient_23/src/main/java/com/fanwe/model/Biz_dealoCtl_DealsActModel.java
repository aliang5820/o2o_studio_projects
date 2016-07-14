package com.fanwe.model;

import java.util.List;

public class Biz_dealoCtl_DealsActModel extends BaseCtlActModel
{
	private List<Xftj_groupItemModel> deals;
	private Xftj_groupModel deal_info;
	private PageModel page;
	private String page_title;

	public List<Xftj_groupItemModel> getDeals()
	{
		return deals;
	}

	public void setDeals(List<Xftj_groupItemModel> deals)
	{
		this.deals = deals;
	}

	public Xftj_groupModel getDeal_info()
	{
		return deal_info;
	}

	public void setDeal_info(Xftj_groupModel deal_info)
	{
		this.deal_info = deal_info;
	}

	public PageModel getPage()
	{
		return page;
	}

	public void setPage(PageModel page)
	{
		this.page = page;
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
