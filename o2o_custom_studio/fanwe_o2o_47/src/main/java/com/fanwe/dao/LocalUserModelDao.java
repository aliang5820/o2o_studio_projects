package com.fanwe.dao;

import com.fanwe.model.LocalUserModel;

public class LocalUserModelDao
{

	public static boolean insertModel(LocalUserModel model)
	{
		return JsonDbModelDaoX.getInstance().insertOrUpdate(model, true);

	}

	public static LocalUserModel queryModel()
	{
		return JsonDbModelDaoX.getInstance().query(LocalUserModel.class, true);
	}

	public static void deleteAllModel()
	{
		JsonDbModelDaoX.getInstance().delete(LocalUserModel.class);
	}

}
