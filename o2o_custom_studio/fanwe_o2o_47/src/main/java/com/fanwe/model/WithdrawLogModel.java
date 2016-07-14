package com.fanwe.model;

import com.fanwe.utils.SDFormatUtil;

public class WithdrawLogModel
{
	private int id;
	private int user_id;
	private double money;
	private String create_time;
	private int is_paid;
	private String pay_time;
	private String bank_name;
	private String bank_account;
	private String bank_user;
	private int is_delete;

	// add
	private String moneyFormat;

	public String getMoneyFormat()
	{
		return moneyFormat;
	}

	public int getId()
	{
		return id;
	}

	public void setId(int id)
	{
		this.id = id;
	}

	public int getUser_id()
	{
		return user_id;
	}

	public void setUser_id(int user_id)
	{
		this.user_id = user_id;
	}

	public double getMoney()
	{
		return money;
	}

	public void setMoney(double money)
	{
		this.money = money;
		this.moneyFormat = "-" + SDFormatUtil.formatMoneyChina(money);
	}

	public String getCreate_time()
	{
		return create_time;
	}

	public void setCreate_time(String create_time)
	{
		this.create_time = create_time;
	}

	public int getIs_paid()
	{
		return is_paid;
	}

	public void setIs_paid(int is_paid)
	{
		this.is_paid = is_paid;
	}

	public String getPay_time()
	{
		return pay_time;
	}

	public void setPay_time(String pay_time)
	{
		this.pay_time = pay_time;
	}

	public String getBank_name()
	{
		return bank_name;
	}

	public void setBank_name(String bank_name)
	{
		this.bank_name = bank_name;
	}

	public String getBank_account()
	{
		return bank_account;
	}

	public void setBank_account(String bank_account)
	{
		this.bank_account = bank_account;
	}

	public String getBank_user()
	{
		return bank_user;
	}

	public void setBank_user(String bank_user)
	{
		this.bank_user = bank_user;
	}

	public int getIs_delete()
	{
		return is_delete;
	}

	public void setIs_delete(int is_delete)
	{
		this.is_delete = is_delete;
	}

}
