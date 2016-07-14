package com.fanwe.model;

import java.util.List;

public class Store_pay_checkActModel extends BaseActModel
{

	private int show_payment;
	private int has_account;
	private int account_money;
	private List<Payment_listModel> payment_list;
	private List<FeeinfoModel> pay_data;

	public List<FeeinfoModel> getPay_data()
	{
		return pay_data;
	}

	public void setPay_data(List<FeeinfoModel> pay_data)
	{
		this.pay_data = pay_data;
	}

	public int getShow_payment()
	{
		return show_payment;
	}

	public void setShow_payment(int show_payment)
	{
		this.show_payment = show_payment;
	}

	public int getHas_account()
	{
		return has_account;
	}

	public void setHas_account(int has_account)
	{
		this.has_account = has_account;
	}

	public int getAccount_money()
	{
		return account_money;
	}

	public void setAccount_money(int account_money)
	{
		this.account_money = account_money;
	}

	public List<Payment_listModel> getPayment_list()
	{
		return payment_list;
	}

	public void setPayment_list(List<Payment_listModel> payment_list)
	{
		this.payment_list = payment_list;
	}

}
