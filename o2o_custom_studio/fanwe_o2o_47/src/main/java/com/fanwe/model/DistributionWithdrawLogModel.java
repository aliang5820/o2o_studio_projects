package com.fanwe.model;

public class DistributionWithdrawLogModel
{
	private int id;
	private String money; // 提现金额
	private String create_time;
	private int is_paid; // 0:审核中，1:提现成功
	private int type; // 0:会员余额，1:银行卡

	// add
	private String typeFormat;
	private String is_paidFormat;

	public String getTypeFormat()
	{
		return typeFormat;
	}

	public void setTypeFormat(String typeFormat)
	{
		this.typeFormat = typeFormat;
	}

	public String getIs_paidFormat()
	{
		return is_paidFormat;
	}

	public void setIs_paidFormat(String is_paidFormat)
	{
		this.is_paidFormat = is_paidFormat;
	}

	public int getId()
	{
		return id;
	}

	public void setId(int id)
	{
		this.id = id;
	}

	public String getMoney()
	{
		return money;
	}

	public void setMoney(String money)
	{
		this.money = money;
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
		switch (is_paid)
		{
		case 0:
			this.is_paidFormat = "审核中";
			break;
		case 1:
			this.is_paidFormat = "提现成功";
			break;

		default:
			break;
		}
	}

	public int getType()
	{
		return type;
	}

	public void setType(int type)
	{
		this.type = type;
		switch (type)
		{
		case 0:
			this.typeFormat = "账户余额";
			break;
		case 1:
			this.typeFormat = "银行卡";
			break;

		default:
			break;
		}
	}

}
