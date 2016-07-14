package com.fanwe.model;

import java.util.List;

public class Uc_fxwithdraw_indexActModel extends BaseActModel
{

	private String fxmoney;
	private List<DistributionWithdrawLogModel> list;
	private PageModel page;

	public String getFxmoney()
	{
		return fxmoney;
	}

	public void setFxmoney(String fxmoney)
	{
		this.fxmoney = fxmoney;
	}

	public List<DistributionWithdrawLogModel> getList()
	{
		return list;
	}

	public void setList(List<DistributionWithdrawLogModel> list)
	{
		this.list = list;
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
