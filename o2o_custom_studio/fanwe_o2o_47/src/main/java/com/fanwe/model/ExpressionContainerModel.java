package com.fanwe.model;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ExpressionContainerModel
{
	private List<ExpressionModel> qq;

	// add
	private List<ExpressionGroupModel> listExpressionGruop = new ArrayList<ExpressionGroupModel>();
	private Map<String, String> mapKeyUrl = new HashMap<String, String>();

	public Map<String, String> getMapKeyUrl()
	{
		return mapKeyUrl;
	}

	public List<ExpressionGroupModel> getListExpressionGruop()
	{
		return listExpressionGruop;
	}

	public List<ExpressionModel> getQq()
	{
		return qq;
	}

	public void setQq(List<ExpressionModel> qq)
	{
		this.qq = qq;
		addListExpression(qq, "QQ");
	}

	private void addListExpression(List<ExpressionModel> listExpression, String groupName)
	{
		if (listExpression != null && !listExpression.isEmpty())
		{
			ExpressionGroupModel model = new ExpressionGroupModel();
			model.setName(groupName);
			model.setListExpression(listExpression);
			listExpressionGruop.add(model);
			updateDictonary();
		}
	}

	public void updateDictonary()
	{
		if (listExpressionGruop != null)
		{
			for (ExpressionGroupModel groupModel : listExpressionGruop)
			{
				List<ExpressionModel> listExpress = groupModel.getListExpression();
				for (ExpressionModel model : listExpress)
				{
					mapKeyUrl.put(model.getEmotion(), model.getFilename());
				}
			}
		}
	}

}
