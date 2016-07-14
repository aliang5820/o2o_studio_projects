package com.fanwe.model;

import java.util.List;

public class Store_pay_count_store_pay_totalActModel extends BaseActModel
{

	private String order_sn; // 订单号
	private String order_info; // 订单信息
	private String pay_info; // 显示的信息

	private int pay_status; // 当pay_status=1时，订单已经支付成功，显示pay_info信息
	private List<FeeinfoModel> pay_data;

	public String getOrder_sn()
	{
		return order_sn;
	}

	public void setOrder_sn(String order_sn)
	{
		this.order_sn = order_sn;
	}

	public String getOrder_info()
	{
		return order_info;
	}

	public void setOrder_info(String order_info)
	{
		this.order_info = order_info;
	}

	public String getPay_info()
	{
		return pay_info;
	}

	public void setPay_info(String pay_info)
	{
		this.pay_info = pay_info;
	}

	public int getPay_status()
	{
		return pay_status;
	}

	public void setPay_status(int pay_status)
	{
		this.pay_status = pay_status;
	}

	public List<FeeinfoModel> getPay_data()
	{
		return pay_data;
	}

	public void setPay_data(List<FeeinfoModel> pay_data)
	{
		this.pay_data = pay_data;
	}

}
