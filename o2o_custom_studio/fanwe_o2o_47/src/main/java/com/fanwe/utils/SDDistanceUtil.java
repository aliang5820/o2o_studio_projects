package com.fanwe.utils;

import android.text.TextUtils;

public class SDDistanceUtil
{

	public static String getFormatDistance(double distance)
	{
		if (distance > 0 && distance <= 1000)
		{
			return (int) distance + "m";
		} else if (distance > 1000 && distance <= 1000 * 100)
		{
			return SDNumberUtil.round(distance / 1000, 1) + "km";
		} else if (distance > 1000 * 100)
		{
			return ((int) (distance / 1000)) + "km";
		} else
		{
			return "";
		}
	}

	public static String getKmDistanceString(int distance)
	{
		if (distance > 0)
		{
			double doubleDistance = ((double) distance) / 1000;
			String strKmDistance = SDFormatUtil.formatNumberDouble(doubleDistance, 1);
			if (!TextUtils.isEmpty(strKmDistance))
			{
				return strKmDistance + "km";
			}
		}
		return "0.0km";
	}
}
