package com.fanwe.model;

import java.util.ArrayList;
import java.util.List;

import com.fanwe.fragment.MapSearchFragment.MapSearchModelSupplier;
import com.fanwe.library.utils.SDCollectionUtil;

public class Events_indexActModel extends BaseActModel implements MapSearchModelSupplier
{
	private int city_id; // 城市ID
	private int area_id; // 大区ID
	private int quan_id; // 商圈ID
	private int cate_id; // 大分类ID
	private String page_title; // 页面标题
	private PageModel page;
	private List<EventModel> item;
	private List<Bcate_listModel> bcate_list;
	private List<Quan_listModel> quan_list;
	private List<CategoryOrderModel> navs;

	public List<Bcate_listModel> getBcate_list()
	{
		return bcate_list;
	}

	public void setBcate_list(List<Bcate_listModel> bcate_list)
	{
		this.bcate_list = bcate_list;
	}

	public List<Quan_listModel> getQuan_list()
	{
		return quan_list;
	}

	public void setQuan_list(List<Quan_listModel> quan_list)
	{
		this.quan_list = quan_list;
	}

	public List<CategoryOrderModel> getNavs()
	{
		return navs;
	}

	public void setNavs(List<CategoryOrderModel> navs)
	{
		this.navs = navs;
	}

	public int getCity_id()
	{
		return city_id;
	}

	public void setCity_id(int city_id)
	{
		this.city_id = city_id;
	}

	public int getArea_id()
	{
		return area_id;
	}

	public void setArea_id(int area_id)
	{
		this.area_id = area_id;
	}

	public int getQuan_id()
	{
		return quan_id;
	}

	public void setQuan_id(int quan_id)
	{
		this.quan_id = quan_id;
	}

	public int getCate_id()
	{
		return cate_id;
	}

	public void setCate_id(int cate_id)
	{
		this.cate_id = cate_id;
	}

	public List<EventModel> getItem()
	{
		return item;
	}

	public void setItem(List<EventModel> item)
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

	public String getPage_title()
	{
		return page_title;
	}

	public void setPage_title(String page_title)
	{
		this.page_title = page_title;
	}

	@Override
	public List<MapSearchBaseModel> getListMapSearchModel()
	{
		List<MapSearchBaseModel> listMapModel = null;
		if (!SDCollectionUtil.isEmpty(item))
		{
			listMapModel = new ArrayList<MapSearchBaseModel>();
			for (EventModel model : item)
			{
				listMapModel.add(model.createMapSearchModel());
			}
		}
		return listMapModel;
	}

}