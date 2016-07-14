package com.fanwe.model;

import java.util.List;
import java.util.Map;

public class Uc_home_indexActModel extends BaseActModel
{
	private PageModel page;

	private int is_fav; // 0未关注，1已经关注
	private int is_why; // 0未登录也不查看其它人要求登录 ,1 自己，2其它登录用户看，3未登录用户看
	private User_dataModel user_data;

	private Map<String, String> expression_replace_array;

	private List<DynamicModel> data_list;

	public void updateExpressionMap()
	{
		if (data_list != null && expression_replace_array != null)
		{
			for (DynamicModel model : data_list)
			{
				model.setMapKeyUrl(expression_replace_array);
			}
		}
	}

	public PageModel getPage()
	{
		return page;
	}

	public void setPage(PageModel page)
	{
		this.page = page;
	}

	public int getIs_fav()
	{
		return is_fav;
	}

	public void setIs_fav(int is_fav)
	{
		this.is_fav = is_fav;
	}

	public int getIs_why()
	{
		return is_why;
	}

	public void setIs_why(int is_why)
	{
		this.is_why = is_why;
	}

	public User_dataModel getUser_data()
	{
		return user_data;
	}

	public void setUser_data(User_dataModel user_data)
	{
		this.user_data = user_data;
	}

	public Map<String, String> getExpression_replace_array()
	{
		return expression_replace_array;
	}

	public void setExpression_replace_array(Map<String, String> expression_replace_array)
	{
		this.expression_replace_array = expression_replace_array;
		updateExpressionMap();
	}

	public List<DynamicModel> getData_list()
	{
		return data_list;
	}

	public void setData_list(List<DynamicModel> data_list)
	{
		this.data_list = data_list;
		updateExpressionMap();
	}

}
