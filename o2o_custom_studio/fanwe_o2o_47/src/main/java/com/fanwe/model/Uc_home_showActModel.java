package com.fanwe.model;

import java.util.Map;

public class Uc_home_showActModel extends BaseActModel
{

	private int is_fav; // 0未关注，1已经关注
	private int is_why; // 0未登录也不查看其它人要求登录 ,1 自己，2其它登录用户看，3未登录用户看
	private Map<String, String> expression_replace_array;
	private DynamicModel data;

	public Map<String, String> getExpression_replace_array()
	{
		return expression_replace_array;
	}

	public void setExpression_replace_array(Map<String, String> expression_replace_array)
	{
		this.expression_replace_array = expression_replace_array;
		updateExpressionMap();
	}

	public void updateExpressionMap()
	{
		if (data != null && expression_replace_array != null)
		{
			data.setMapKeyUrl(expression_replace_array);
		}
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

	public DynamicModel getData()
	{
		return data;
	}

	public void setData(DynamicModel data)
	{
		this.data = data;
		updateExpressionMap();
	}

}
