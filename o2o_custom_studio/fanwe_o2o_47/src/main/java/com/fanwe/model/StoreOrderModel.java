package com.fanwe.model;

import android.text.TextUtils;

import com.fanwe.utils.SDFormatUtil;

public class StoreOrderModel
{

	private int id;
	private String order_sn;
	private double total_price;
	private int order_status;
	private int pay_status;
	private String create_time;
	private double pay_amount;
	private double payment_fee;
	private double discount_price;
	private double other_money;
	private String location_name;
	private String status;

	// add
	private String feeInfo;
	private String youhuiInfo;

	public String getYouhuiInfo()
	{
		if (TextUtils.isEmpty(youhuiInfo))
		{
			StringBuilder sb = new StringBuilder();
			if (discount_price > 0)
			{
				sb.append("已优惠:");
				sb.append(SDFormatUtil.formatMoneyChina(discount_price));
			}
			if (other_money > 0)
			{
				sb.append("，");
				sb.append("不可优惠:");
				sb.append(SDFormatUtil.formatMoneyChina(other_money));
			}
			youhuiInfo = sb.toString();
		}
		return youhuiInfo;
	}

	public String getFeeInfo()
	{
		if (TextUtils.isEmpty(feeInfo))
		{
			StringBuilder sb = new StringBuilder();
			sb.append("总价:");
			sb.append(SDFormatUtil.formatMoneyChina(total_price));

			if (pay_amount > 0)
			{
				sb.append("，");
				sb.append("已支付:");
				sb.append(SDFormatUtil.formatMoneyChina(pay_amount));
			}
			feeInfo = sb.toString();
		}
		return feeInfo;
	}

	public double getOther_money()
	{
		return other_money;
	}

	public void setOther_money(double other_money)
	{
		this.other_money = other_money;
	}

	public int getId()
	{
		return id;
	}

	public void setId(int id)
	{
		this.id = id;
	}

	public String getOrder_sn()
	{
		return order_sn;
	}

	public void setOrder_sn(String order_sn)
	{
		this.order_sn = order_sn;
	}

	public double getTotal_price()
	{
		return total_price;
	}

	public void setTotal_price(double total_price)
	{
		this.total_price = total_price;
	}

	public int getOrder_status()
	{
		return order_status;
	}

	public void setOrder_status(int order_status)
	{
		this.order_status = order_status;
	}

	public int getPay_status()
	{
		return pay_status;
	}

	public void setPay_status(int pay_status)
	{
		this.pay_status = pay_status;
	}

	public String getCreate_time()
	{
		return create_time;
	}

	public void setCreate_time(String create_time)
	{
		this.create_time = create_time;
	}

	public double getPay_amount()
	{
		return pay_amount;
	}

	public void setPay_amount(double pay_amount)
	{
		this.pay_amount = pay_amount;
	}

	public double getPayment_fee()
	{
		return payment_fee;
	}

	public void setPayment_fee(double payment_fee)
	{
		this.payment_fee = payment_fee;
	}

	public double getDiscount_price()
	{
		return discount_price;
	}

	public void setDiscount_price(double discount_price)
	{
		this.discount_price = discount_price;
	}

	public String getLocation_name()
	{
		return location_name;
	}

	public void setLocation_name(String location_name)
	{
		this.location_name = location_name;
	}

	public String getStatus()
	{
		return status;
	}

	public void setStatus(String status)
	{
		this.status = status;
	}

}
