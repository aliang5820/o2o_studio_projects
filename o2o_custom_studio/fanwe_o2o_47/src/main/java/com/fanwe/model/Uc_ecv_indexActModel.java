package com.fanwe.model;

import java.util.List;

public class Uc_ecv_indexActModel extends BaseActModel
{

	private List<RedEnvelopeModel> data;
	private String user_avatar;
	private int ecv_count;
	private double ecv_total;

	private PageModel page;

	public List<RedEnvelopeModel> getData()
	{
		return data;
	}

	public void setData(List<RedEnvelopeModel> data)
	{
		this.data = data;
	}

	public String getUser_avatar()
	{
		return user_avatar;
	}

	public void setUser_avatar(String user_avatar)
	{
		this.user_avatar = user_avatar;
	}

	public int getEcv_count()
	{
		return ecv_count;
	}

	public void setEcv_count(int ecv_count)
	{
		this.ecv_count = ecv_count;
	}

	public double getEcv_total()
	{
		return ecv_total;
	}

	public void setEcv_total(double ecv_total)
	{
		this.ecv_total = ecv_total;
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
