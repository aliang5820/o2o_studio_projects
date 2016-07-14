package com.fanwe.common;

import com.lidroid.xutils.HttpUtils;

public class HttpManagerX
{
	private static HttpUtils mHttpUtils = null;

	private HttpManagerX()
	{
	}

	/**
	 * 得到全局http请求对象
	 * 
	 * @return
	 */
	public static HttpUtils getHttpUtils()
	{
		if (mHttpUtils == null)
		{
			mHttpUtils = newHttpUtils();
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
		HttpUtils httpUtils = new HttpUtils();
		httpUtils.configCurrentHttpCacheExpiry(0);
		return httpUtils;
	}

}
