package com.fanwe.model;

import com.fanwe.o2o.newo2o.R;
import com.fanwe.utils.SDFormatUtil;

public class RedEnvelopeModel
{
	private int id;
	private int use_status;
	private String datetime;
	private String name;
	private String money;
	private int exchange_score;
	private int use_limit;

	// add
	private String money_format;
	private int statusColorResId;
	private String statusFormat;

	public int getExchange_score()
	{
		return exchange_score;
	}

	public void setExchange_score(int exchange_score)
	{
		this.exchange_score = exchange_score;
	}

	public int getStatusColorResId()
	{
		return statusColorResId;
	}

	public void setStatusColorResId(int statusColorResId)
	{
		this.statusColorResId = statusColorResId;
	}

	public String getStatusFormat()
	{
		return statusFormat;
	}

	public void setStatusFormat(String statusFormat)
	{
		this.statusFormat = statusFormat;
	}

	public int getId()
	{
		return id;
	}

	public void setId(int id)
	{
		this.id = id;
	}

	public int getUse_status()
	{
		return use_status;
	}

	public void setUse_status(int use_status)
	{
		this.use_status = use_status;
		switch (use_status)
		{
		case 0:
			this.statusFormat = "可使用";
			this.statusColorResId = R.color.main_color;
			break;
		case 1:
			this.statusFormat = "已失效";
			this.statusColorResId = R.color.gray;
			break;

		default:
			break;
		}
	}

	public String getDatetime()
	{
		return datetime;
	}

	public void setDatetime(String datetime)
	{
		this.datetime = datetime;
	}

	public String getName()
	{
		return name;
	}

	public void setName(String name)
	{
		this.name = name;
	}

	public String getMoney()
	{
		return money;
	}

	public void setMoney(String money)
	{
		this.money = money;
		this.money_format = SDFormatUtil.formatMoneyChina(money);
	}

	public String getMoney_format()
	{
		return money_format;
	}

	public void setMoney_format(String money_format)
	{
		this.money_format = money_format;
	}

	public int getUse_limit()
	{
		return use_limit;
	}

	public void setUse_limit(int use_limit)
	{
		this.use_limit = use_limit;
	}

	public String getUse_limitToString()
	{
		if (use_limit == 0)
		{
			return "(无限)";
		} else if (use_limit == 1)
		{
			return "";
		} else
		{
			return "(×" + use_limit + ")";
		}
	}

}
