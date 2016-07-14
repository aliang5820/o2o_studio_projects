package com.fanwe.dao;

import com.fanwe.model.LocalUserModel;


/**
 * @author 作者 E-mail:
 * @version 创建时间：2015-10-14 下午4:40:41
 * 类说明
 */
public class UserModelDao
{
	public static boolean saveModel(LocalUserModel  model)
	{
		return JsonDbModelDao.getInstance().insertOrUpdateJsonDbModel(model, true);

	}

	public static LocalUserModel  getModel()
	{
		return JsonDbModelDao.getInstance().queryJsonDbModel(LocalUserModel.class, true);
	}

	public static void deleteAllModel()
	{
		JsonDbModelDao.getInstance().deleteJsonDbModel(LocalUserModel.class);
	}
}
