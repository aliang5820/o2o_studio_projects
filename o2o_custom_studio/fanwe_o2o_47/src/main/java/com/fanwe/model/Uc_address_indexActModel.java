package com.fanwe.model;

import java.util.List;

public class Uc_address_indexActModel extends BaseActModel
{
	private List<Consignee_infoModel> consignee_list;

	private PageModel page;

	public List<Consignee_infoModel> getConsignee_list()
	{
		return consignee_list;
	}

	public void setConsignee_list(List<Consignee_infoModel> consignee_list)
	{
		this.consignee_list = consignee_list;
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
