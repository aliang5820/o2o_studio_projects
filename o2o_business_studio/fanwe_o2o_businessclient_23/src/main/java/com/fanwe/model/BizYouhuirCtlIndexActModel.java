package com.fanwe.model;

import java.util.List;

/**
 * @author 作者 E-mail:309581534@qq.com
 * @version 创建时间：2015-5-18 上午10:14:55 类说明
 */
public class BizYouhuirCtlIndexActModel extends BaseCtlActModel
{
	private PageModel page;

	private List<BizYouhuirCtlItemModel> item;

	public PageModel getPage()
	{
		return page;
	}

	public void setPage(PageModel page)
	{
		this.page = page;
	}

	public List<BizYouhuirCtlItemModel> getItem()
	{
		return item;
	}

	public void setItem(List<BizYouhuirCtlItemModel> item)
	{
		this.item = item;
	}

}
