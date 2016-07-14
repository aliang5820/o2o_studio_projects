package com.fanwe.model;

import java.util.List;

public class Uc_review_indexActModel extends BaseActModel
{
	private List<CommentModel> item;

	private PageModel page;

	public PageModel getPage()
	{
		return page;
	}

	public void setPage(PageModel page)
	{
		this.page = page;
	}

	public List<CommentModel> getItem()
	{
		return item;
	}

	public void setItem(List<CommentModel> item)
	{
		this.item = item;
	}

}
