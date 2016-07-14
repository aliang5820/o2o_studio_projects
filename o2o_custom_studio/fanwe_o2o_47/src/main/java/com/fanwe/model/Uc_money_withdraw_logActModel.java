package com.fanwe.model;

import java.util.List;

public class Uc_money_withdraw_logActModel extends BaseActModel
{

	private PageModel page;
	private List<WithdrawLogModel> data;

	public List<WithdrawLogModel> getData()
	{
		return data;
	}

	public void setData(List<WithdrawLogModel> data)
	{
		this.data = data;
	}

	public PageModel getPage()
	{
		return page;
	}

	public void setPage(PageModel page)
	{
		this.page = page;
	}

}
