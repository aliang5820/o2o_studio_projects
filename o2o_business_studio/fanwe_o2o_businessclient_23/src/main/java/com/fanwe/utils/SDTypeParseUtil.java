package com.fanwe.utils;

import android.text.TextUtils;
/**
 * 
 * @author js02
 *
 */
public class SDTypeParseUtil
{
	
	public static float getFloatFromString(String content, float defaultValue)
	{
		if (!TextUtils.isEmpty(content))
		{
			try
			{
				return Float.parseFloat(content);
			} catch (Exception e)
			{
				return defaultValue;
			}
		} else
		{
			return defaultValue;
		}
	}
	
	public static Double getDoubleFromString(String content, double defaultValue)
	{
		if (!TextUtils.isEmpty(content))
		{
			try
			{
				return Double.parseDouble(content);
			} catch (Exception e)
			{
				return defaultValue;
			}
		} else
		{
			return defaultValue;
		}
	}
	
	public static Long getLongFromString(String content, long defaultValue)
	{
		if (!TextUtils.isEmpty(content))
		{
			try
			{
				return Long.parseLong(content);
			} catch (Exception e)
			{
				return defaultValue;
			}
		} else
		{
			return defaultValue;
		}
	}
	
	public static int getIntFromString(String content, int defaultValue)
	{
		if (!TextUtils.isEmpty(content))
		{
			try
			{
				return Integer.parseInt(content);
			} catch (Exception e)
			{
				return defaultValue;
			}
		} else
		{
			return defaultValue;
		}
	}
	
	
	

}
