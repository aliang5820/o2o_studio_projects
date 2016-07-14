package com.fanwe.model;

import java.util.List;

public class Jygl_couponLocationActModel extends BaseCtlActModel
{
	private List<Jygl_couponLocationsModel> locations;
	private List<Jygl_couponYouhuisModel> youhuis;
	private Jygl_couponYouhuiModel youhui;
	private PageModel page;
	private String page_title;

	public List<Jygl_couponLocationsModel> getLocations()
	{
		return locations;
	}

	public void setLocations(List<Jygl_couponLocationsModel> locations)
	{
		this.locations = locations;
	}

	public PageModel getPage()
	{
		return page;
	}

	public void setPage(PageModel page)
	{
		this.page = page;
	}

	public Jygl_couponYouhuiModel getYouhui()
	{
		return youhui;
	}

	public void setYouhui(Jygl_couponYouhuiModel youhui)
	{
		this.youhui = youhui;
	}

	public List<Jygl_couponYouhuisModel> getYouhuis()
	{
		return youhuis;
	}

	public void setYouhuis(List<Jygl_couponYouhuisModel> youhuis)
	{
		this.youhuis = youhuis;
	}

	public String getPage_title()
	{
		return page_title;
	}

	public void setPage_title(String page_title)
	{
		this.page_title = page_title;
	}

}
