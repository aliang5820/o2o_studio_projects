package com.fanwe.model;

import java.util.List;

/**
 * @author 作者 E-mail:309581534@qq.com
 * @version 创建时间：2015-6-9 下午2:19:12 类说明
 */
public class BizGoodsoCtlGoodssActModel extends BaseCtlActModel
{
	private PageModel page;

	private List<ItemBizGoodsoActGoodss> item;
	
	private int order_delivery_expire;
	
	private int now_time;

	public PageModel getPage()
	{
		return page;
	}

	public void setPage(PageModel page)
	{
		this.page = page;
	}

	public List<ItemBizGoodsoActGoodss> getItem()
	{
		return item;
	}

	public void setItem(List<ItemBizGoodsoActGoodss> item)
	{
		this.item = item;
	}

	public int getOrder_delivery_expire()
	{
		return order_delivery_expire;
	}

	public void setOrder_delivery_expire(int order_delivery_expire)
	{
		this.order_delivery_expire = order_delivery_expire;
	}

	public int getNow_time()
	{
		return now_time;
	}

	public void setNow_time(int now_time)
	{
		this.now_time = now_time;
	}
	
	
}
