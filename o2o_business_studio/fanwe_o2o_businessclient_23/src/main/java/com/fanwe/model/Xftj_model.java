package com.fanwe.model;

import java.util.List;

public class Xftj_model extends BaseCtlActModel
{
	private PageModel page;
	//团购列表
	private List<Xftj_buyModel> item;
	//门店销量列表
	private List<Xftj_store_itemModel> locations;
	private Xftj_storeModel deal_info;

	public List<Xftj_buyModel> getItem()
	{
		return item;
	}

	public void setItem(List<Xftj_buyModel> item)
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

	public List<Xftj_store_itemModel> getLocations()
	{
		return locations;
	}

	public void setLocations(List<Xftj_store_itemModel> locations)
	{
		this.locations = locations;
	}

	public Xftj_storeModel getDeal_info()
	{
		return deal_info;
	}

	public void setDeal_info(Xftj_storeModel deal_info)
	{
		this.deal_info = deal_info;
	}

}
