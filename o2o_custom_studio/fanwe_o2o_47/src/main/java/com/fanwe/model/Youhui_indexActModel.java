package com.fanwe.model;

import java.util.List;

public class Youhui_indexActModel extends BaseActModel
{
	private Youhui_infoModel youhui_info;
	private List<StoreModel> other_supplier_location;
	private List<CommentModel> dp_list;

	public List<CommentModel> getDp_list()
	{
		return dp_list;
	}

	public void setDp_list(List<CommentModel> dp_list)
	{
		this.dp_list = dp_list;
	}

	public Youhui_infoModel getYouhui_info()
	{
		return youhui_info;
	}

	public void setYouhui_info(Youhui_infoModel youhui_info)
	{
		this.youhui_info = youhui_info;
	}

	public List<StoreModel> getOther_supplier_location()
	{
		return other_supplier_location;
	}

	public void setOther_supplier_location(List<StoreModel> other_supplier_location)
	{
		this.other_supplier_location = other_supplier_location;
	}

}