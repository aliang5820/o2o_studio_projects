package com.fanwe.model;

import java.util.HashMap;
import java.util.Map;

public class Uc_home_publishActModel extends BaseActModel
{

	private ExpressionContainerModel expression;

	private Map<String, String> mapKeyUrl = new HashMap<String, String>();

	public Map<String, String> getMapKeyUrl()
	{
		return mapKeyUrl;
	}

	public ExpressionContainerModel getExpression()
	{
		return expression;
	}

	public void setExpression(ExpressionContainerModel expression)
	{
		this.expression = expression;
		if (expression != null)
		{
			this.mapKeyUrl = expression.getMapKeyUrl();
		}
	}

}
