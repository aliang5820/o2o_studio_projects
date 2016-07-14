package com.fanwe.model;

public class PageModel
{

	private int page = 1;
	private int page_total = 0;
	private int page_size;
	private int data_total;

	public int getPage_size()
	{
		return page_size;
	}

	public void setPage_size(int page_size)
	{
		this.page_size = page_size;
	}

	public int getData_total()
	{
		return data_total;
	}

	public void setData_total(int data_total)
	{
		this.data_total = data_total;
	}

	public int getPage()
	{
		return page;
	}

	public void setPage(int page)
	{
		this.page = page;
	}

	public int getPage_total()
	{
		return page_total;
	}

	public void setPage_total(int page_total)
	{
		this.page_total = page_total;
	}

	public boolean increment()
	{
		page++;
		if (page > page_total)
		{
			page--;
			return false;
		} else
		{
			return true;
		}
	}

	public void resetPage()
	{
		page = 1;
	}

	public void update(PageModel model)
	{
		if (model != null)
		{
			this.page = model.getPage();
			this.page_total = model.getPage_total();
		}
	}

}
