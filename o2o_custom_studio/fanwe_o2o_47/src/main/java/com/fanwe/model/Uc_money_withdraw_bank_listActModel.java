package com.fanwe.model;

import java.util.ArrayList;
import java.util.List;

public class Uc_money_withdraw_bank_listActModel extends BaseActModel
{

	private List<UserBankModel> bank_list;
	private double money;
	private String real_name;
	private String mobile;

	public List<UserBankModel> getBank_list()
	{
		return bank_list;
	}

	public void setBank_list(List<UserBankModel> bank_list)
	{
		if (bank_list == null)
		{
			bank_list = new ArrayList<UserBankModel>();
		}
		UserBankModel model = new UserBankModel();
		model.setBank_name("使用新卡提现");
		bank_list.add(model);
		this.bank_list = bank_list;
	}

	public double getMoney()
	{
		return money;
	}

	public void setMoney(double money)
	{
		this.money = money;
	}

	public String getReal_name()
	{
		return real_name;
	}

	public void setReal_name(String real_name)
	{
		this.real_name = real_name;
	}

	public String getMobile()
	{
		return mobile;
	}

	public void setMobile(String mobile)
	{
		this.mobile = mobile;
	}

}
