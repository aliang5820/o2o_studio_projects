package com.fanwe.model;

import java.util.List;

public class Uc_chargeIndexActModel extends BaseActModel
{
	private List<Payment_listModel> payment_list;

	public List<Payment_listModel> getPayment_list()
	{
		return payment_list;
	}

	public void setPayment_list(List<Payment_listModel> payment_list)
	{
		this.payment_list = payment_list;
	}
}
