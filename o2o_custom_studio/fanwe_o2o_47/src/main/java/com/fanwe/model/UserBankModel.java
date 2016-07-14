package com.fanwe.model;

public class UserBankModel
{

	private int id;
	private int user_id;
	private String bank_name;
	private String bank_account;
	private String bank_user;
	private String bank_mobile;

	public int getUser_id()
	{
		return user_id;
	}

	public void setUser_id(int user_id)
	{
		this.user_id = user_id;
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

	public String getBank_mobile()
	{
		return bank_mobile;
	}

	public void setBank_mobile(String bank_mobile)
	{
		this.bank_mobile = bank_mobile;
	}

	public int getId()
	{
		return id;
	}

	public void setId(int id)
	{
		this.id = id;
	}

	public String getBank_name()
	{
		return bank_name;
	}

	public void setBank_name(String bank_name)
	{
		this.bank_name = bank_name;
	}

	@Override
	public String toString()
	{
		return bank_name;
	}

}
