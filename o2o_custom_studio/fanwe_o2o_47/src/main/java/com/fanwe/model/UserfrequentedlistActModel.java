package com.fanwe.model;

import java.util.List;

public class UserfrequentedlistActModel extends BaseActModel
{

	private List<UserfrequentedlistActItemModel> item = null;

	public List<UserfrequentedlistActItemModel> getItem()
	{
		return item;
	}

	public void setItem(List<UserfrequentedlistActItemModel> item)
	{
		this.item = item;
	}

}
