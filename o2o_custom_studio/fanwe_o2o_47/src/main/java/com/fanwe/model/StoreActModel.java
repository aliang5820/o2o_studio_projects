package com.fanwe.model;

import java.util.List;

public class StoreActModel extends BaseActModel
{

	private Store_infoModel store_info;
	private List<StoreModel> other_supplier_location;
	private List<GoodsModel> tuan_list;
	private List<GoodsModel> deal_list;
	private List<EventModel> event_list;
	private List<YouhuiModel> youhui_list;
	private List<CommentModel> dp_list;

	public List<CommentModel> getDp_list()
	{
		return dp_list;
	}

	public void setDp_list(List<CommentModel> dp_list)
	{
		this.dp_list = dp_list;
	}

	public Store_infoModel getStore_info()
	{
		return store_info;
	}

	public void setStore_info(Store_infoModel store_info)
	{
		this.store_info = store_info;
	}

	public List<StoreModel> getOther_supplier_location()
	{
		return other_supplier_location;
	}

	public void setOther_supplier_location(List<StoreModel> other_supplier_location)
	{
		this.other_supplier_location = other_supplier_location;
	}

	public List<GoodsModel> getTuan_list()
	{
		return tuan_list;
	}

	public void setTuan_list(List<GoodsModel> tuan_list)
	{
		this.tuan_list = tuan_list;
	}

	public List<GoodsModel> getDeal_list()
	{
		return deal_list;
	}

	public void setDeal_list(List<GoodsModel> deal_list)
	{
		this.deal_list = deal_list;
	}

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

}
