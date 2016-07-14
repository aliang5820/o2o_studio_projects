package com.fanwe.model;

import java.util.List;

public class Deal_attrModel
{
	private int id; // 属性分类 ID
	private String name; // 属性名称
	private List<Deal_attrValueModel> attr_list;

	/**
	 * 获得选中的属性值实体
	 * 
	 * @return
	 */
	public Deal_attrValueModel getSelectedAttr()
	{
		Deal_attrValueModel model = null;
		if (attr_list != null)
		{
			for (Deal_attrValueModel item : attr_list)
			{
				if (item.isSelected())
				{
					model = item;
					break;
				}
			}
		}
		return model;
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

	public List<Deal_attrValueModel> getAttr_list()
	{
		return attr_list;
	}

	public void setAttr_list(List<Deal_attrValueModel> attr_list)
	{
		this.attr_list = attr_list;
	}

}
