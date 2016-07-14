package com.fanwe.model;

import java.util.List;

public class NearbyuserActModel extends BaseActModel
{

	private String email;

	private List<NearbyuserActItemModel> item;

	private PageModel page;

	public String getEmail()
	{
		return email;
	}

	public void setEmail(String email)
	{
		this.email = email;
	}

	public List<NearbyuserActItemModel> getItem()
	{
		return item;
	}

	public void setItem(List<NearbyuserActItemModel> item)
	{
		this.item = item;
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
