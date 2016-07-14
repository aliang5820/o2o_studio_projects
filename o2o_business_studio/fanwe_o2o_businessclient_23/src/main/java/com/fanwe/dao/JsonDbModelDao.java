package com.fanwe.dao;

import java.util.List;

import com.fanwe.common.DbManager;
import com.fanwe.library.utils.AESUtil;
import com.fanwe.model.JsonDbModel;
import com.fanwe.utils.JsonUtil;
import com.lidroid.xutils.db.sqlite.Selector;
import com.lidroid.xutils.db.sqlite.WhereBuilder;

public class JsonDbModelDao
{

	private static JsonDbModelDao mInstance = null;

	public static JsonDbModelDao getInstance()
	{
		if (mInstance == null)
		{
			syncInit();
		}
		return mInstance;
	}

	private static synchronized void syncInit()
	{
		if (mInstance == null)
		{
			mInstance = new JsonDbModelDao();
		}
	}

	private <T> boolean insertJsonDbModel(T model, boolean encrypt)
	{
		if (model != null)
		{
			try
			{
				JsonDbModel jsonDbModel = new JsonDbModel();
				jsonDbModel.setKey(model.getClass().getName());
				String json = JsonUtil.object2Json(model);
				if (encrypt) // 需要加密
				{
					json = AESUtil.encrypt(json);
				}
				jsonDbModel.setValue(json);
				DbManager.getDbUtils().save(jsonDbModel);
				return true;
			} catch (Exception e)
			{
				e.printStackTrace();
			}
		}
		return false;
	}

	public <T> boolean insertOrUpdateJsonDbModel(T model, boolean encrypt)
	{
		if (model != null)
		{
			deleteJsonDbModel(model.getClass());
			return insertJsonDbModel(model, encrypt);
		} else
		{
			return false;
		}
	}

	public <T> boolean insertOrUpdateJsonDbModel(T model)
	{
		return insertOrUpdateJsonDbModel(model, false);
	}

	public <T> boolean deleteJsonDbModel(Class<T> clazz)
	{
		if (clazz != null)
		{
			try
			{
				DbManager.getDbUtils().delete(JsonDbModel.class, WhereBuilder.b("key", "=", clazz.getName()));
				return true;
			} catch (Exception e)
			{
				e.printStackTrace();
			}
		}
		return false;
	}

	public <T> T queryJsonDbModel(Class<T> clazz, boolean decrypt)
	{
		if (clazz != null)
		{
			try
			{
				List<JsonDbModel> listJsonDbModel = DbManager.getDbUtils().findAll(Selector.from(JsonDbModel.class).where("key", "=", clazz.getName()));
				if (listJsonDbModel != null && listJsonDbModel.size() == 1)
				{
					JsonDbModel jsonDbModel = listJsonDbModel.get(0);
					String value = jsonDbModel.getValue();
					if (value != null)
					{
						if (decrypt) // 需要解密
						{
							value = AESUtil.decrypt(value);
						}
						return JsonUtil.json2Object(value, clazz);
					}
				}
			} catch (Exception e)
			{
				e.printStackTrace();
			}
		}
		return null;
	}

	public <T> T queryJsonDbModel(Class<T> clazz)
	{
		return queryJsonDbModel(clazz, false);
	}

}
