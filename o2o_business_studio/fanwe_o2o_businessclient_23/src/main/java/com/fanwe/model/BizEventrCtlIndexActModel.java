package com.fanwe.model;

import java.util.List;

/**
 * @author 作者 E-mail:309581534@qq.com
 * @version 创建时间：2015-5-18 上午10:18:30 类说明
 */
public class BizEventrCtlIndexActModel extends BaseCtlActModel
{
	private PageModel page;
	private List<BizEventrCtlIndexActItemModel> item;

	public PageModel getPage()
	{
		return page;
	}

	public void setPage(PageModel page)
	{
		this.page = page;
	}

	public List<BizEventrCtlIndexActItemModel> getItem()
	{
		return item;
	}

	public void setItem(List<BizEventrCtlIndexActItemModel> item)
	{
		this.item = item;
	}

}
