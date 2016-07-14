package com.fanwe.model;

import java.util.List;

/**
 * @author 作者 E-mail:309581534@qq.com
 * @version 创建时间：2015-6-5 上午9:06:44 类说明
 */
public class BizEventoCtlModel extends BaseCtlActModel
{
	private PageModel page;
	private List<ItemBizEventoCtlModel> item;

	public PageModel getPage()
	{
		return page;
	}

	public void setPage(PageModel page)
	{
		this.page = page;
	}

	public List<ItemBizEventoCtlModel> getItem()
	{
		return item;
	}

	public void setItem(List<ItemBizEventoCtlModel> item)
	{
		this.item = item;
	}

}
