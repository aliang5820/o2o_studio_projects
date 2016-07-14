package com.fanwe.model;

import java.util.List;

import com.fanwe.library.utils.SDCollectionUtil;

public class Brand_listModel
{

	private int id;
	private String name;
	private boolean isSelect;

	public boolean isSelect()
	{
		return isSelect;
	}

	public void setSelect(boolean isSelect)
	{
		this.isSelect = isSelect;
	}

	public int getId()
	{
		return id;
	}

	public void setId(int id)
	{
		this.id = id;
	}

	public String getName()
	{
		return name;
	}

	public void setName(String name)
	{
		this.name = name;
	}

	public static int findIndex(int bid, List<Brand_listModel> listModel)
	{
		int index = 0;
		if (bid > 0 && !SDCollectionUtil.isEmpty(listModel))
		{
			Brand_listModel model = null;
			for (int i = 0; i < listModel.size(); i++)
			{
				model = listModel.get(i);
				if (model.getId() == bid)
				{
					index = i;
					break;
				}
			}
		}
		return index;
	}
}
