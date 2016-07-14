package com.fanwe.model;

import java.util.List;

public class Biz_withdrawalCtlModel extends BaseCtlActModel
{
	private PageModel page;
	private List<Frag_tab3_itemModel> item;
	private Frag_tab3Model supplier_info;

	public PageModel getPage()
	{
		return page;
	}

	public void setPage(PageModel page)
	{
		this.page = page;
	}

	public List<Frag_tab3_itemModel> getItem()
	{
		return item;
	}

	public void setItem(List<Frag_tab3_itemModel> item)
	{
		this.item = item;
	}

	public Frag_tab3Model getSupplier_info()
	{
		return supplier_info;
	}

	public void setSupplier_info(Frag_tab3Model supplier_info)
	{
		this.supplier_info = supplier_info;
	}

}
