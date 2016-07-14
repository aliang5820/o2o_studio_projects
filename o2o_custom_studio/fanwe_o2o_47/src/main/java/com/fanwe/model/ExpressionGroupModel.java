package com.fanwe.model;

import java.util.List;

public class ExpressionGroupModel
{
	private String name;
	private List<ExpressionModel> listExpression;

	public String getName()
	{
		return name;
	}

	public void setName(String name)
	{
		this.name = name;
	}

	public List<ExpressionModel> getListExpression()
	{
		return listExpression;
	}

	public void setListExpression(List<ExpressionModel> listExpression)
	{
		this.listExpression = listExpression;
	}

}
