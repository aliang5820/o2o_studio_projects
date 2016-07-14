package com.fanwe.model;

import java.util.List;

public class Uc_ecv_exchangeModel extends BaseActModel
{
	private int score;
	private List<RedEnvelopeModel> data;
	private PageModel page;

	public int getScore()
	{
		return score;
	}

	public void setScore(int score)
	{
		this.score = score;
	}

	public List<RedEnvelopeModel> getData()
	{
		return data;
	}

	public void setData(List<RedEnvelopeModel> data)
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
