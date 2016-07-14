package com.fanwe.model;

import java.util.List;

public class Uc_fxinvite_indexActModel extends BaseActModel
{

	private String fxmoney;
	private String pname;
	private int user_temid;
	private PageModel page;
	private List<DistributionRecommendModel> list;

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

	public int getUser_temid()
	{
		return user_temid;
	}

	public void setUser_temid(int user_temid)
	{
		this.user_temid = user_temid;
	}

	public List<DistributionRecommendModel> getList()
	{
		return list;
	}

	public void setList(List<DistributionRecommendModel> list)
	{
		this.list = list;
	}

}
