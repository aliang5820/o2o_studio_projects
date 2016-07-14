package com.fanwe.common;

import com.lidroid.xutils.HttpUtils;

public class HttpManager
{
	private static final int TIME_OUT = 10 * 1000;

	private static HttpUtils mHttpUtils = null;

	/**
	 * 得到全局http请求对象
	 * 
	 * @return
	 */
	public static HttpUtils getHttpUtils()
	{
		if (mHttpUtils == null)
		{
			mHttpUtils = new HttpUtils(TIME_OUT);
			mHttpUtils.configCurrentHttpCacheExpiry(0);
		}
		return mHttpUtils;
	}

	/**
	 * 创建一个新的http请求对象
	 * 
	 * @return
	 */
	public static HttpUtils newHttpUtils()
	{
		return new HttpUtils(TIME_OUT);
	}

}
