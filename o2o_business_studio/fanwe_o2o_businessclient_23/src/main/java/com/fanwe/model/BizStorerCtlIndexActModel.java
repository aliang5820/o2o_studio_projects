package com.fanwe.model;

import java.util.List;

/**
 * @author 作者 E-mail:309581534@qq.com
 * @version 创建时间：2015-5-18 上午10:19:49 类说明
 */
public class BizStorerCtlIndexActModel extends BaseCtlActModel
{
	private PageModel page;
	private List<BizStorerCtlIndexActItemModel> item;

	public PageModel getPage()
	{
		return page;
	}

	public void setPage(PageModel page)
	{
		this.page = page;
	}

	public List<BizStorerCtlIndexActItemModel> getItem()
	{
		return item;
	}

	public void setItem(List<BizStorerCtlIndexActItemModel> item)
	{
		this.item = item;
	}

}
