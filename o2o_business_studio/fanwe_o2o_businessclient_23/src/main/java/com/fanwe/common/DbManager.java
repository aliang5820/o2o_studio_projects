package com.fanwe.common;

import com.fanwe.application.App;
import com.lidroid.xutils.DbUtils;
import com.lidroid.xutils.DbUtils.DbUpgradeListener;

public class DbManager
{

	private static final String DB_DIR = null;
	private static final String DB_NAME = "fanwe_o2o_bus.db";
	private static final int DB_VERSION = 1;

	private static DbUtils mDbUtils = null;

	public static DbUtils getDbUtils()
	{
		if (mDbUtils == null)
		{
			mDbUtils = DbUtils.create(App.getApp(), DB_DIR, DB_NAME, DB_VERSION, new FwDbUpgradeListener());
		}
		return mDbUtils;
	}

	static class FwDbUpgradeListener implements DbUpgradeListener
	{

		@Override
		public void onUpgrade(DbUtils db, int oldVersion, int newVersion)
		{
			// TODO Auto-generated method stub

		}

	}

}
