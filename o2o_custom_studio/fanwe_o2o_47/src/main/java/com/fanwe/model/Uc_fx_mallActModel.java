package com.fanwe.model;

import java.util.List;

public class Uc_fx_mallActModel extends BaseActModel
{
	private int type; // 0团购，1商城
	private int is_why; // 1自己，2其他登陆用户，3未登录用户
	private MyDistributionUser_dataModel user_data;
	private PageModel page;
	private List<DistributionGoodsModel> deal_list;

	// 米果定制
	private List<DistributionGoodsModel> hot_list;

	public List<DistributionGoodsModel> getHot_list()
	{
		return hot_list;
	}

	public void setHot_list(List<DistributionGoodsModel> hot_list)
	{
		this.hot_list = hot_list;
	}

	public PageModel getPage()
	{
		return page;
	}

	public void setPage(PageModel page)
	{
		this.page = page;
	}

	public int getType()
	{
		return type;
	}

	public void setType(int type)
	{
		this.type = type;
	}

	public int getIs_why()
	{
		return is_why;
	}

	public void setIs_why(int is_why)
	{
		this.is_why = is_why;
	}

	public MyDistributionUser_dataModel getUser_data()
	{
		return user_data;
	}

	public void setUser_data(MyDistributionUser_dataModel user_data)
	{
		this.user_data = user_data;
	}

	public List<DistributionGoodsModel> getDeal_list()
	{
		return deal_list;
	}

	public void setDeal_list(List<DistributionGoodsModel> deal_list)
	{
		this.deal_list = deal_list;
	}

}
