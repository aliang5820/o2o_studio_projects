package com.fanwe.model;

public class Cart_doneActModel extends BaseActModel
{

	private int pay_status;
	private int order_id;

	public int getPay_status()
	{
		return pay_status;
	}

	public void setPay_status(int pay_status)
	{
		this.pay_status = pay_status;
	}

	public int getOrder_id()
	{
		return order_id;
	}

	public void setOrder_id(int order_id)
	{
		this.order_id = order_id;
	}

}
