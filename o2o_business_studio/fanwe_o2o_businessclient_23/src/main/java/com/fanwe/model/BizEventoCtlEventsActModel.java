package com.fanwe.model;

import java.util.List;

/**
 * @author 作者 E-mail:309581534@qq.com
 * @version 创建时间：2015-6-5 上午10:52:02 类说明
 */
public class BizEventoCtlEventsActModel extends BaseCtlActModel
{
	private EventInfoBizEventoCtlEventsActModel event_info;
	private List<ItemBizEventoCtlEventsActModel> item;
	private PageModel page;

	public EventInfoBizEventoCtlEventsActModel getEvent_info()
	{
		return event_info;
	}

	public void setEvent_info(EventInfoBizEventoCtlEventsActModel event_info)
	{
		this.event_info = event_info;
	}

	public List<ItemBizEventoCtlEventsActModel> getItem()
	{
		return item;
	}

	public void setItem(List<ItemBizEventoCtlEventsActModel> item)
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

}
