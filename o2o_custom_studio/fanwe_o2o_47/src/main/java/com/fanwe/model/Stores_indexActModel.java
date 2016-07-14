package com.fanwe.model;

import java.util.ArrayList;
import java.util.List;

import com.fanwe.fragment.MapSearchFragment.MapSearchModelSupplier;
import com.fanwe.library.utils.SDCollectionUtil;

public class Stores_indexActModel extends BaseActModel implements MapSearchModelSupplier
{
	private int city_id; // 当前城市ID
	private int area_id; // 当前大区ID
	private int quan_id; // 当前商圈ID
	private int cate_id; // 当前大分类ID
	private String page_title; // 页面标题
	private PageModel page;
	private List<StoreModel> item;
	private List<Bcate_listModel> bcate_list;
	private List<Quan_listModel> quan_list;
	private List<CategoryOrderModel> navs;

	public List<CategoryOrderModel> getNavs()
	{
		return navs;
	}

	public void setNavs(List<CategoryOrderModel> navs)
	{
		this.navs = navs;
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

	public List<StoreModel> getItem()
	{
		return item;
	}

	public void setItem(List<StoreModel> item)
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

	public int getCity_id()
	{
		return city_id;
	}

	public void setCity_id(int city_id)
	{
		this.city_id = city_id;
	}

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
			for (StoreModel model : item)
			{
				listMapModel.add(model.createMapSearchModel());
			}
		}
		return listMapModel;
	}

}