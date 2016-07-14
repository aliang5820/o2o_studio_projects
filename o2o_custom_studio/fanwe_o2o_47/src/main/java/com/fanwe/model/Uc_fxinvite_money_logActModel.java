package com.fanwe.model;

import java.util.List;

public class Uc_fxinvite_money_logActModel extends BaseActModel
{

	private String fxmoney; // 当前分销收益
	private String pname; // 推荐人名称
	private List<DistributionMoneyLogModel> list;
	private PageModel page;

	public PageModel getPage()
	{
		return page;
	}

	public void setPage(PageModel page)
	{
		this.page = page;
	}

	public String getFxmoney()
	{
		return fxmoney;
	}

	public void setFxmoney(String fxmoney)
	{
		this.fxmoney = fxmoney;
	}

	public String getPname()
	{
		return pname;
	}

	public void setPname(String pname)
	{
		this.pname = pname;
	}

	public List<DistributionMoneyLogModel> getList()
	{
		return list;
	}

	public void setList(List<DistributionMoneyLogModel> list)
	{
		this.list = list;
	}

}
