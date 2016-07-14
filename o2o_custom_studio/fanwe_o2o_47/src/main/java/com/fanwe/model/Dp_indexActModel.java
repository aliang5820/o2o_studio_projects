package com.fanwe.model;

import java.util.List;

public class Dp_indexActModel extends BaseActModel
{
	private List<CommentModel> item;

	private int message_count; // 点评数量

	private PageModel page;

	private int star_1; // 1星人数
	private int star_2; // 2星人数
	private int star_3; // 3星人数
	private int star_4; // 4星人数
	private int star_5; // 5星人数

	private int star_dp_width_1;
	private int star_dp_width_2;
	private int star_dp_width_3;
	private int star_dp_width_4;
	private int star_dp_width_5;
	private int buy_dp_width; // 平均值 进度条长度

	private int buy_dp_sum; // 购买点评数量
	private String buy_dp_avg; // 点评平均值

	private int allow_dp; // 1:允许点评，0:不允许
	private String name;

	public int getBuy_dp_width()
	{
		return buy_dp_width;
	}

	public void setBuy_dp_width(int buy_dp_width)
	{
		this.buy_dp_width = buy_dp_width;
	}

	public int getBuy_dp_sum()
	{
		return buy_dp_sum;
	}

	public void setBuy_dp_sum(int buy_dp_sum)
	{
		this.buy_dp_sum = buy_dp_sum;
	}

	public String getName()
	{
		return name;
	}

	public void setName(String name)
	{
		this.name = name;
	}

	public String getPage_title()
	{
		return page_title;
	}

	public void setPage_title(String page_title)
	{
		this.page_title = page_title;
	}

	public int getAllow_dp()
	{
		return allow_dp;
	}

	public void setAllow_dp(int allow_dp)
	{
		this.allow_dp = allow_dp;
	}

	public List<CommentModel> getItem()
	{
		return item;
	}

	public void setItem(List<CommentModel> item)
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

	public int getMessage_count()
	{
		return message_count;
	}

	public void setMessage_count(int message_count)
	{
		this.message_count = message_count;
	}

	public int getStar_1()
	{
		return star_1;
	}

	public void setStar_1(int star_1)
	{
		this.star_1 = star_1;
	}

	public int getStar_2()
	{
		return star_2;
	}

	public void setStar_2(int star_2)
	{
		this.star_2 = star_2;
	}

	public int getStar_3()
	{
		return star_3;
	}

	public void setStar_3(int star_3)
	{
		this.star_3 = star_3;
	}

	public int getStar_4()
	{
		return star_4;
	}

	public void setStar_4(int star_4)
	{
		this.star_4 = star_4;
	}

	public int getStar_5()
	{
		return star_5;
	}

	public void setStar_5(int star_5)
	{
		this.star_5 = star_5;
	}

	public int getStar_dp_width_1()
	{
		return star_dp_width_1;
	}

	public void setStar_dp_width_1(int star_dp_width_1)
	{
		this.star_dp_width_1 = star_dp_width_1;
	}

	public int getStar_dp_width_2()
	{
		return star_dp_width_2;
	}

	public void setStar_dp_width_2(int star_dp_width_2)
	{
		this.star_dp_width_2 = star_dp_width_2;
	}

	public int getStar_dp_width_3()
	{
		return star_dp_width_3;
	}

	public void setStar_dp_width_3(int star_dp_width_3)
	{
		this.star_dp_width_3 = star_dp_width_3;
	}

	public int getStar_dp_width_4()
	{
		return star_dp_width_4;
	}

	public void setStar_dp_width_4(int star_dp_width_4)
	{
		this.star_dp_width_4 = star_dp_width_4;
	}

	public int getStar_dp_width_5()
	{
		return star_dp_width_5;
	}

	public void setStar_dp_width_5(int star_dp_width_5)
	{
		this.star_dp_width_5 = star_dp_width_5;
	}

	public String getBuy_dp_avg()
	{
		return buy_dp_avg;
	}

	public void setBuy_dp_avg(String buy_dp_avg)
	{
		this.buy_dp_avg = buy_dp_avg;
	}

}
