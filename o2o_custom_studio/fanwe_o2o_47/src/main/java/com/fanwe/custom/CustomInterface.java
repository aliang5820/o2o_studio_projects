package com.fanwe.custom;

import com.fanwe.common.HttpManagerX;
import com.lidroid.xutils.http.ResponseInfo;
import com.lidroid.xutils.http.callback.RequestCallBack;
import com.lidroid.xutils.http.client.HttpRequest.HttpMethod;

/**
 * 用户定义接口
 * 
 * @author Administrator
 * 
 */
public class CustomInterface
{

	private static final String URL_TONG_JI = "http://42.159.82.80/~poweru/diaoyong.php";

	public static void submitTongji()
	{
		HttpManagerX.getHttpUtils().send(HttpMethod.POST, URL_TONG_JI, new RequestCallBack<String>()
		{
			@Override
			public void onSuccess(ResponseInfo<String> responseInfo)
			{

			}
		});
	}

}
