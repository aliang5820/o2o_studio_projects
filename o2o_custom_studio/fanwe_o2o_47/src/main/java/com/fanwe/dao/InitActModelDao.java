package com.fanwe.dao;

import com.fanwe.model.Init_indexActModel;

public class InitActModelDao
{
	public static boolean insertOrUpdate(Init_indexActModel model)
	{
		return JsonDbModelDaoX.getInstance().insertOrUpdate(model);
	}

	public static Init_indexActModel query()
	{
		Init_indexActModel model = JsonDbModelDaoX.getInstance().query(Init_indexActModel.class);
		return model;
	}

	public static void deleteModel()
	{
		JsonDbModelDaoX.getInstance().delete(Init_indexActModel.class);
	}

}
