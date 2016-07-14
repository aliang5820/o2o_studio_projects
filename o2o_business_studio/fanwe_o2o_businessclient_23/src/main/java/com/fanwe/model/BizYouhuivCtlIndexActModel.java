package com.fanwe.model;

import java.util.ArrayList;

/**
 * @author 作者 E-mail:309581534@qq.com
 * @version 创建时间：2015-5-15 下午5:05:55 类说明
 */
public class BizYouhuivCtlIndexActModel extends BaseCtlActModel
{
	private ArrayList<BizYouhuivCtlIndexActItemModel> item;
	private PageModel page;

	public PageModel getPage()
	{
		return page;
	}

	public void setPage(PageModel page)
	{
		this.page = page;
	}

	public ArrayList<BizYouhuivCtlIndexActItemModel> getItem()
	{
		return item;
	}

	public void setItem(ArrayList<BizYouhuivCtlIndexActItemModel> item)
	{
		this.item = item;
	}

}
