package com.fanwe.dao;

import java.util.List;

import android.text.TextUtils;

import com.fanwe.common.DbManagerX;
import com.fanwe.model.Region_confModel;

public class Region_confModelDao
{

	private static Region_confModelDao mInstance = null;

	private Region_confModelDao()
	{
	}

	public static Region_confModelDao getInstance()
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
			mInstance = new Region_confModelDao();
		}
	}

	public boolean deleteAllData()
	{
		try
		{
			DbManagerX.getDb().delete(Region_confModel.class);
		} catch (Exception e)
		{
			return false;
		}
		return true;
	}

	public boolean deleteOldAndSaveNew(List<Region_confModel> listModel)
	{
		if (listModel != null && listModel.size() > 0)
		{
			if (deleteAllData())
			{
				try
				{
					DbManagerX.getDb().save(listModel);
					return true;
				} catch (Exception e)
				{
					e.printStackTrace();
				}
			}
		}
		return false;
	}

	public List<Region_confModel> getCountryList()
	{
		return getListByPid(0);
	}

	public List<Region_confModel> getListByPid(int pid)
	{
		List<Region_confModel> listModel = null;
		try
		{
			listModel = DbManagerX.getDb().selector(Region_confModel.class).where("pid", "=", pid).findAll();
		} catch (Exception e)
		{
			e.printStackTrace();
		}
		return listModel;
	}

	public List<Region_confModel> getListById(String id)
	{
		if (!TextUtils.isEmpty(id))
		{
			try
			{
				return DbManagerX.getDb().selector(Region_confModel.class).where("id", "=", id).findAll();
			} catch (Exception e)
			{
				e.printStackTrace();
			}
		}
		return null;
	}

	public List<Region_confModel> getListByParentModel(Region_confModel parent)
	{
		return getListByPid(parent.getId());
	}

}
