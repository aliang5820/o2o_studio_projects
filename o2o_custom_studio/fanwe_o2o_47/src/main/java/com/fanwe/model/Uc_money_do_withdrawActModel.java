package com.fanwe.model;

public class Uc_money_do_withdrawActModel extends BaseActModel
{

	private int withdraw_id;
	private String bank_name;

	public int getWithdraw_id()
	{
		return withdraw_id;
	}

	public void setWithdraw_id(int withdraw_id)
	{
		this.withdraw_id = withdraw_id;
	}

	public String getBank_name()
	{
		return bank_name;
	}

	public void setBank_name(String bank_name)
	{
		this.bank_name = bank_name;
	}

}
