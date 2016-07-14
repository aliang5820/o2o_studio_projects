package com.fanwe.model;

import com.fanwe.constant.Constant.DealTag;
import com.fanwe.o2o.newo2o.R;

public class Deal_tagsModel
{

	private int k; // 1:免预约，2:多套餐，4:折扣券，5:过期退，6:随时退，8:免运费
	private String v; // 标签名称

	public int getK()
	{
		return k;
	}

	public void setK(int k)
	{
		this.k = k;
	}

	public String getV()
	{
		return v;
	}

	public void setV(String v)
	{
		this.v = v;
	}

	public int getIcon()
	{
		int icon = 0;
		switch (k)
		{
		case DealTag.FREE_LOTTERY:
			icon = R.drawable.ic_tag_free_lottery;
			break;
		case DealTag.FREE_RESERVATION:
			icon = R.drawable.ic_tag_free_reservation;
			break;
		case DealTag.MULTI_PACKAGES:
			icon = R.drawable.ic_tag_multi_packages;
			break;
		case DealTag.CAN_RESERVATION:
			icon = R.drawable.ic_tag_can_reservation;
			break;
		case DealTag.DISCOUNT_TICKETS:
			icon = R.drawable.ic_tag_discount_tickets;
			break;
		case DealTag.EXPIRED_REFUND:
			icon = R.drawable.ic_tag_expired_refund;
			break;
		case DealTag.ANY_REFUND:
			icon = R.drawable.ic_tag_any_refund;
			break;
		case DealTag.WEEK_REFUND:
			icon = R.drawable.ic_tag_week_refund;
			break;
		case DealTag.FREE_FREIGHT:
			icon = R.drawable.ic_tag_free_freight;
			break;
		case DealTag.FULL_MINUS:
			icon = R.drawable.ic_tag_full_minus;
			break;

		default:
			break;
		}
		return icon;
	}

}
