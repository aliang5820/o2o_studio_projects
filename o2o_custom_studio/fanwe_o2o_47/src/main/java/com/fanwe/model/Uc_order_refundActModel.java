package com.fanwe.model;

import java.util.ArrayList;
import java.util.List;

public class Uc_order_refundActModel extends BaseActModel
{

	private Uc_orderGoodsModel item;

	public List<Uc_orderGoodsModel> getListItem()
	{
		List<Uc_orderGoodsModel> listItem = new ArrayList<Uc_orderGoodsModel>();
		if (item != null)
		{
			listItem.add(item);
		}
		return listItem;
	}

	public Uc_orderGoodsModel getItem()
	{
		return item;
	}

	public void setItem(Uc_orderGoodsModel item)
	{
		this.item = item;
	}

}
