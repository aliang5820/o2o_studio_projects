package com.fanwe.model;

import java.util.List;

public class Goods_indexActModel extends BaseActModel
{
	private String cate_id;
	private String bid;
	private String page_title;
	private PageModel page;
	private List<GoodsModel> item;
	private List<Bcate_listModel> bcate_list;
	private List<Brand_listModel> brand_list;
	private List<CategoryOrderModel> navs;

	public List<CategoryOrderModel> getNavs()
	{
		return navs;
	}

	public void setNavs(List<CategoryOrderModel> navs)
	{
		this.navs = navs;
	}

	public List<Brand_listModel> getBrand_list()
	{
		return brand_list;
	}

	public void setBrand_list(List<Brand_listModel> brand_list)
	{
		this.brand_list = brand_list;
	}

	public String getCate_id()
	{
		return cate_id;
	}

	public void setCate_id(String cate_id)
	{
		this.cate_id = cate_id;
	}

	public String getBid()
	{
		return bid;
	}

	public void setBid(String bid)
	{
		this.bid = bid;
	}

	public List<GoodsModel> getItem()
	{
		return item;
	}

	public void setItem(List<GoodsModel> item)
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

	public List<Bcate_listModel> getBcate_list()
	{
		return bcate_list;
	}

	public void setBcate_list(List<Bcate_listModel> bcate_list)
	{
		this.bcate_list = bcate_list;
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