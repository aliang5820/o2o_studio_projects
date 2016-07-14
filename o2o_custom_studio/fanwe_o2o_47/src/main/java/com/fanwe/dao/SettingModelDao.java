package com.fanwe.dao;

import java.util.List;

import com.fanwe.app.App;
import com.fanwe.common.DbManagerX;
import com.fanwe.constant.Constant.LoadImageType;
import com.fanwe.model.SettingModel;

public class SettingModelDao
{

	public static boolean insertOrCreate(SettingModel model)
	{
		if (model != null)
		{
			if (hasSettingModel())
			{
				return true;
			} else
			{
				try
				{
					DbManagerX.getDb().save(model);
					return true;
				} catch (Exception e)
				{
					e.printStackTrace();
				}
			}
		}
		return false;
	}

	public static SettingModel query()
	{
		try
		{
			List<SettingModel> listModel = DbManagerX.getDb().findAll(SettingModel.class);
			if (listModel != null && listModel.size() == 1)
			{
				return listModel.get(0);
			}
		} catch (Exception e)
		{
			e.printStackTrace();
		}
		return null;
	}

	public static int getLoadImageType()
	{
		SettingModel model = query();
		if (model != null)
		{
			return model.getLoadImageType();
		} else
		{
			return LoadImageType.ALL;
		}
	}

	public static int canPushMessage()
	{
		SettingModel model = query();
		if (model != null)
		{
			return model.getCanPushMessage();
		} else
		{
			return 1;
		}
	}

	public static boolean hasSettingModel()
	{
		SettingModel model = query();
		if (model != null)
		{
			return true;
		} else
		{
			return false;
		}
	}

	public static boolean updateLoadImageType(int loadImageType)
	{
		boolean result = false;
		SettingModel model = query();
		if (model != null)
		{
			try
			{
				model.setLoadImageType(loadImageType);
				DbManagerX.getDb().update(model);
				result = true;
			} catch (Exception e)
			{
				result = false;
			}
		}
		App.getApplication().mRuntimeConfig.updateIsCanLoadImage();
		return result;
	}

	public static boolean updateCanPushMessage(int canPushMessage)
	{
		SettingModel model = query();
		if (model != null)
		{
			try
			{
				model.setCanPushMessage(canPushMessage);
				DbManagerX.getDb().update(model);
				return true;
			} catch (Exception e)
			{
				e.printStackTrace();
			}
		}
		return false;
	}

}
