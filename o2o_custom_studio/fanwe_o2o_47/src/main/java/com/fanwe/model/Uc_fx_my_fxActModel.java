package com.fanwe.model;

import java.util.List;

public class Uc_fx_my_fxActModel extends BaseActModel
{

	private MyDistributionUser_dataModel user_data;

	private List<DistributionGoodsModel> item;

	public MyDistributionUser_dataModel getUser_data()
	{
		return user_data;
	}

	public void setUser_data(MyDistributionUser_dataModel user_data)
	{
		this.user_data = user_data;
	}

	public List<DistributionGoodsModel> getItem()
	{
		return item;
	}

	public void setItem(List<DistributionGoodsModel> item)
	{
		this.item = item;
	}

	private PageModel page;

	public PageModel getPage()
	{
		return page;
	}

	public void setPage(PageModel page)
	{
		this.page = page;
	}

}
