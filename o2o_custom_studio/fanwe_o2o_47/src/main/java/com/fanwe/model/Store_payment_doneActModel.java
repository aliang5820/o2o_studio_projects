package com.fanwe.model;

public class Store_payment_doneActModel extends BaseActModel
{
	private int order_id;
	private String order_sn;
	private int pay_status;
	private String pay_info;
	private Payment_codeModel payment_code;

	public int getOrder_id()
	{
		return order_id;
	}

	public void setOrder_id(int order_id)
	{
		this.order_id = order_id;
	}

	public String getOrder_sn()
	{
		return order_sn;
	}

	public void setOrder_sn(String order_sn)
	{
		this.order_sn = order_sn;
	}

	public int getPay_status()
	{
		return pay_status;
	}

	public void setPay_status(int pay_status)
	{
		this.pay_status = pay_status;
	}

	public String getPay_info()
	{
		return pay_info;
	}

	public void setPay_info(String pay_info)
	{
		this.pay_info = pay_info;
	}

	public Payment_codeModel getPayment_code()
	{
		return payment_code;
	}

	public void setPayment_code(Payment_codeModel payment_code)
	{
		this.payment_code = payment_code;
	}

}
