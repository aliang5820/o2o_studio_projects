package com.fanwe.model;

import java.util.List;

/**
 * 
 * @author yhz
 * @create time 2014-8-5
 */
public class Biz_tuan_msgActModel extends BaseCtlActModel
{
	private PageModel page = null;

	private List<Biz_tuan_msgModel> item = null;

	public PageModel getPage()
	{
		return page;
	}

	public void setPage(PageModel page)
	{
		this.page = page;
	}

	public List<Biz_tuan_msgModel> getItem()
	{
		return item;
	}

	public void setItem(List<Biz_tuan_msgModel> item)
	{
		this.item = item;
	}

}
