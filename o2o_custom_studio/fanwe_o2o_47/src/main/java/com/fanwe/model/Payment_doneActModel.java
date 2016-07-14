package com.fanwe.model;

import java.util.List;

public class Payment_doneActModel extends BaseActModel
{
	private int pay_status;
	private int order_id;
	private String order_sn;
	private String pay_info;
	private Payment_codeModel payment_code;
	private List<Payment_doneActCouponlistModel> couponlist;

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

	public List<Payment_doneActCouponlistModel> getCouponlist()
	{
		return couponlist;
	}

	public void setCouponlist(List<Payment_doneActCouponlistModel> couponlist)
	{
		this.couponlist = couponlist;
	}

	public int getPay_status()
	{
		return pay_status;
	}

	public void setPay_status(int pay_status)
	{
		this.pay_status = pay_status;
	}

}
