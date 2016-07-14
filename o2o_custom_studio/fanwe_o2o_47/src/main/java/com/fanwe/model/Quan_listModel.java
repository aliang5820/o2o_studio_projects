package com.fanwe.model;

import java.util.List;

import com.fanwe.library.utils.SDCollectionUtil;

public class Quan_listModel
{
	private int id;
	private int pid;
	private String name;
	private List<Quan_listModel> quan_sub;

	// /////////////////////////add
	private boolean isSelect;
	private boolean isHasChild;

	public int getPid()
	{
		return pid;
	}

	public void setPid(int pid)
	{
		this.pid = pid;
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

	public List<Quan_listModel> getQuan_sub()
	{
		return quan_sub;
	}

	public void setQuan_sub(List<Quan_listModel> quan_sub)
	{
		this.quan_sub = quan_sub;
		if (quan_sub != null && quan_sub.size() > 1)
		{
			setHasChild(true);
		} else
		{
			setHasChild(false);
		}
	}

	public static int[] findIndex(int qid, List<Quan_listModel> listModel)
	{
		int leftIndex = 0;
		int rightIndex = 0;
		if (qid > 0 && !SDCollectionUtil.isEmpty(listModel))
		{
			Quan_listModel model = null;
			for (int i = 0; i < listModel.size(); i++)
			{
				model = listModel.get(i);
				if (model.getId() == qid)
				{
					leftIndex = i;
					break;
				} else
				{
					List<Quan_listModel> listChildModel = model.getQuan_sub();
					if (!SDCollectionUtil.isEmpty(listChildModel))
					{
						for (int j = 0; j < listChildModel.size(); j++)
						{
							model = listChildModel.get(j);
							if (model.getId() == qid)
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
		return new int[] { leftIndex, rightIndex };
	}

}