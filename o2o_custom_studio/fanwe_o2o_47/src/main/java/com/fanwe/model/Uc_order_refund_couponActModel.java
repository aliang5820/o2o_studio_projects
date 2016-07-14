package com.fanwe.model;

import java.util.ArrayList;
import java.util.List;

import com.fanwe.library.utils.SDCollectionUtil;

public class Uc_order_refund_couponActModel extends BaseActModel
{
	private Uc_orderGoodsModel item;
	private List<OrderCoupon_listModel> coupon_list;

	public List<OrderCoupon_listModel> getCoupon_list()
	{
		return coupon_list;
	}

	public void setCoupon_list(List<OrderCoupon_listModel> coupon_list)
	{
		this.coupon_list = coupon_list;
		updateCouponInfo();
	}

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
		updateCouponInfo();
	}

	private void updateCouponInfo()
	{
		if (!SDCollectionUtil.isEmpty(coupon_list) && item != null)
		{
			for (OrderCoupon_listModel coupon : coupon_list)
			{
				coupon.setNumber(item.getNumber());
			}
		}
	}

}
