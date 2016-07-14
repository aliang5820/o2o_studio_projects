package com.fanwe.model;

import java.util.List;

import com.fanwe.library.utils.SDCollectionUtil;

public class Bcate_listModel
{
	private int id;
	private String name;
	private String icon_img;
	private List<Bcate_listModel> bcate_type;

	// /////////////////////////add
	private boolean isSelect;
	private boolean isHasChild;

	public String getIcon_img()
	{
		return icon_img;
	}

	public void setIcon_img(String icon_img)
	{
		this.icon_img = icon_img;
	}

	public boolean isHasChild()
	{
		return isHasChild;
	}

	public void setHasChild(boolean isHasChild)
	{
		this.isHasChild = isHasChild;
	}

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

	public List<Bcate_listModel> getBcate_type()
	{
		return bcate_type;
	}

	public void setBcate_type(List<Bcate_listModel> bcate_type)
	{
		this.bcate_type = bcate_type;
		if (bcate_type != null && bcate_type.size() > 1)
		{
			setHasChild(true);
		} else
		{
			setHasChild(false);
		}
	}

	public static int[] findIndex(int cate_id, int tid, List<Bcate_listModel> listModel)
	{
		int leftIndex = 0;
		int rightIndex = 0;
		if (!SDCollectionUtil.isEmpty(listModel))
		{
			if (cate_id > 0 || tid > 0)
			{
				Bcate_listModel model = null;
				for (int i = 0; i < listModel.size(); i++)
				{
					model = listModel.get(i);
					if (model.getId() == cate_id) // 找到大分类
					{
						leftIndex = i;
						List<Bcate_listModel> listChildModel = model.getBcate_type();
						if (!SDCollectionUtil.isEmpty(listChildModel))
						{
							for (int j = 0; j < listChildModel.size(); j++)
							{
								model = listChildModel.get(j);
								if (model.getId() == tid) // 找到小分类
								{
									rightIndex = j;
									break;
								}
							}
						}
						break;
					}
				}
			}
		}
		return new int[] { leftIndex, rightIndex };
	}

	public static int[] findIndex(int cate_id, List<Bcate_listModel> listModel)
	{
		int leftIndex = 0;
		int rightIndex = 0;
		if (!SDCollectionUtil.isEmpty(listModel))
		{
			if (cate_id > 0)
			{
				Bcate_listModel model = null;
				for (int i = 0; i < listModel.size(); i++)
				{
					model = listModel.get(i);
					if (model.getId() == cate_id)
					{
						leftIndex = i;
						break;
					} else
					{
						List<Bcate_listModel> listChildModel = model.getBcate_type();
						if (!SDCollectionUtil.isEmpty(listChildModel))
						{
							for (int j = 0; j < listChildModel.size(); j++)
							{
								model = listChildModel.get(j);
								if (model.getId() == cate_id)
								{
									leftIndex = i;
									rightIndex = j;
									break;
								}
							}
						}
					}
				}
			}
		}
		return new int[] { leftIndex, rightIndex };
	}

}