package com.fanwe.model;

import java.util.List;

import android.text.TextUtils;

public class My_order_listActItemModel
{
	private String id = null;
	private String sn = null;
	private String create_time = null;
	private String create_time_format = null;
	private String total_money = null;
	private String money = null;
	private String total_money_format = null;
	private String money_format = null;
	private String status = null;
	private String num = null;
	private List<OrderGoodsModel> orderGoods = null;
	private String delivery_status = null;

	// ===================add
	private String status_format = null;

	public String getStatus_format()
	{
		if (!TextUtils.isEmpty(status))
		{
			status_format = status;
		}
		if (!TextUtils.isEmpty(delivery_status))
		{
			if (!TextUtils.isEmpty(status_format))
			{
				status_format = status_format + "、" + delivery_status;
			} else
			{
				status_format = delivery_status;
			}
		}
		return status_format;
	}

	public void setStatus_format(String status_format)
	{
		this.status_format = status_format;
	}

	public String getDelivery_status()
	{
		return delivery_status;
	}

	public void setDelivery_status(String delivery_status)
	{
		this.delivery_status = delivery_status;
	}

	public String getId()
	{
		return id;
	}

	public void setId(String id)
	{
		this.id = id;
	}

	public String getSn()
	{
		return sn;
	}

	public void setSn(String sn)
	{
		this.sn = sn;
	}

	public String getCreate_time()
	{
		return create_time;
	}

	public void setCreate_time(String create_time)
	{
		this.create_time = create_time;
	}

	public String getCreate_time_format()
	{
		return create_time_format;
	}

	public void setCreate_time_format(String create_time_format)
	{
		this.create_time_format = create_time_format;
	}

	public String getTotal_money()
	{
		return total_money;
	}

	public void setTotal_money(String total_money)
	{
		this.total_money = total_money;
	}

	public String getMoney()
	{
		return money;
	}

	public void setMoney(String money)
	{
		this.money = money;
	}

	public String getTotal_money_format()
	{
		return total_money_format;
	}

	public void setTotal_money_format(String total_money_format)
	{
		this.total_money_format = total_money_format;
	}

	public String getMoney_format()
	{
		return money_format;
	}

	public void setMoney_format(String money_format)
	{
		this.money_format = money_format;
	}

	public String getStatus()
	{
		return status;
	}

	public void setStatus(String status)
	{
		this.status = status;
	}

	public String getNum()
	{
		return num;
	}

	public void setNum(String num)
	{
		this.num = num;
	}

	public List<OrderGoodsModel> getOrderGoods()
	{
		return orderGoods;
	}

	public void setOrderGoods(List<OrderGoodsModel> orderGoods)
	{
		this.orderGoods = orderGoods;
	}

}