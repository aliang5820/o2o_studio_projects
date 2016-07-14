package com.fanwe.model;

import java.util.List;
import java.util.Map;

public class Discover_indexActModel extends BaseActModel
{

	private Map<String, String> expression_replace_array;
	private List<DiscoveryTagModel> tag_list;
	private List<DynamicModel> data_list;
	private PageModel page;

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

	public List<DynamicModel> getData_list()
	{
		return data_list;
	}

	public void setData_list(List<DynamicModel> data_list)
	{
		this.data_list = data_list;
		updateExpressionMap();
	}

	public List<DiscoveryTagModel> getTag_list()
	{
		return tag_list;
	}

	public void setTag_list(List<DiscoveryTagModel> tag_list)
	{
		this.tag_list = tag_list;
		if (this.tag_list != null)
		{
			DiscoveryTagModel model = new DiscoveryTagModel();
			model.setName("全部");
			this.tag_list.add(0, model);
		}
	}

}
