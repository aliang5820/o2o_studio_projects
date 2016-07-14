package com.fanwe.config;

import com.fanwe.library.config.SDConfig;
import com.fanwe.o2o.newo2o.R;

public class AppConfig
{

	//
	public static int getRegionConfVersion()
	{
		return SDConfig.getInstance().getInt(R.string.config_region_version, 0);
	}

	public static void setRegionConfVersion(int value)
	{
		SDConfig.getInstance().setInt(R.string.config_region_version, value);
	}

	//
	public static String getSessionId()
	{
		return SDConfig.getInstance().getString(R.string.config_session_id, "");
	}

	public static void setSessionId(String value)
	{
		SDConfig.getInstance().setString(R.string.config_session_id, value);
	}

	//
	public static String getUserName()
	{
		return SDConfig.getInstance().getString(R.string.config_user_name, "");
	}

	public static void setUserName(String userName)
	{
		SDConfig.getInstance().setString(R.string.config_user_name, userName);
	}

	//
	public static String getRefId()
	{
		return SDConfig.getInstance().getString(R.string.config_ref_id, "");
	}

	public static void setRefId(String value)
	{
		SDConfig.getInstance().setString(R.string.config_ref_id, value);
	}

	//
	public static String getImageCode()
	{
		return SDConfig.getInstance().getString(R.string.config_image_code, null);
	}

	public static void setImageCode(String value)
	{
		SDConfig.getInstance().setString(R.string.config_image_code, value);
	}

}
