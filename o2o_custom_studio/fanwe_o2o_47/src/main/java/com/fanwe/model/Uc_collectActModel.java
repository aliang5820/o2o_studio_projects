package com.fanwe.model;

import java.util.List;

public class Uc_collectActModel extends BaseActModel
{

	private List<GoodsModel> goods_list;
	private List<YouhuiModel> youhui_list;
	private List<EventModel> event_list;

	private PageModel page;

	public List<EventModel> getEvent_list()
	{
		return event_list;
	}

	public void setEvent_list(List<EventModel> event_list)
	{
		this.event_list = event_list;
	}

	public List<YouhuiModel> getYouhui_list()
	{
		return youhui_list;
	}

	public void setYouhui_list(List<YouhuiModel> youhui_list)
	{
		this.youhui_list = youhui_list;
	}

	public List<GoodsModel> getGoods_list()
	{
		return goods_list;
	}

	public void setGoods_list(List<GoodsModel> goods_list)
	{
		this.goods_list = goods_list;
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
