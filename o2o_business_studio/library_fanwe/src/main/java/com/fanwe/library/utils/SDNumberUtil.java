package com.fanwe.library.utils;

import java.math.BigDecimal;

public class SDNumberUtil
{

	public static double distance(double lat1, double lon1, double lat2, double lon2)
	{
		double theta = lon1 - lon2;
		double dist = Math.sin(deg2rad(lat1)) * Math.sin(deg2rad(lat2)) + Math.cos(deg2rad(lat1)) * Math.cos(deg2rad(lat2))
				* Math.cos(deg2rad(theta));
		dist = Math.acos(dist);
		dist = rad2deg(dist);
		double miles = dist * 60 * 1.1515 * 1.609344 * 1000;
		return miles;
	}

	// 将角度转换为弧度
	static double deg2rad(double degree)
	{
		return degree / 180 * Math.PI;
	}

	// 将弧度转换为角度
	static double rad2deg(double radian)
	{
		return radian * 180 / Math.PI;
	}

	/**
	 * 提供精确的小数位四舍五入处理。
	 * 
	 * @param v
	 *            需要四舍五入的数字
	 * @param scale
	 *            小数点后保留几位
	 * @return 四舍五入后的结果
	 */
	public static double round(double value, int scale)
	{
		if (scale < 0)
		{
			scale = 0;
		}
		BigDecimal bdValue = new BigDecimal(value);
		BigDecimal oneValue = new BigDecimal(1);
		return bdValue.divide(oneValue, scale, BigDecimal.ROUND_HALF_UP).doubleValue();
	}

	/**
	 * 乘法
	 * 
	 * @param value1
	 * @param value2
	 * @param scale
	 *            保留小数位
	 * @return
	 */
	public static double multiply(double value1, double value2, int scale)
	{
		double result = 0;
		BigDecimal b1 = new BigDecimal(String.valueOf(value1));
		BigDecimal b2 = new BigDecimal(String.valueOf(value2));

		result = b1.multiply(b2).doubleValue();
		result = round(result, scale);
		return result;
	}

	/**
	 * 加法
	 * 
	 * @param value1
	 * @param value2
	 * @param scale
	 *            保留小数位
	 * @return
	 */
	public static double add(double value1, double value2, int scale)
	{
		double result = 0;
		BigDecimal b1 = new BigDecimal(String.valueOf(value1));
		BigDecimal b2 = new BigDecimal(String.valueOf(value2));

		result = b1.add(b2).doubleValue();
		result = round(result, scale);
		return result;
	}

	/**
	 * 减法
	 * 
	 * @param value1
	 * @param value2
	 * @param scale
	 *            保留小数位
	 * @return
	 */
	public static double subtract(double value1, double value2, int scale)
	{
		double result = 0;
		BigDecimal b1 = new BigDecimal(String.valueOf(value1));
		BigDecimal b2 = new BigDecimal(String.valueOf(value2));

		result = b1.subtract(b2).doubleValue();
		result = round(result, scale);
		return result;
	}

	/**
	 * 除法
	 * 
	 * @param value1
	 * @param value2
	 * @param scale
	 *            保留小数位
	 * @return
	 */
	public static double divide(double value1, double value2, int scale)
	{
		double result = 0;
		BigDecimal b1 = new BigDecimal(String.valueOf(value1));
		BigDecimal b2 = new BigDecimal(String.valueOf(value2));

		result = b1.divide(b2).doubleValue();
		result = round(result, scale);
		return result;
	}

}
