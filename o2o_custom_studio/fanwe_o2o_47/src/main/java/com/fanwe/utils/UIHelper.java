package com.fanwe.utils;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;

import com.fanwe.MainActivity;

/**
 * Title:UI跳转类
 * 
 * @author: yhz CreateTime：2014-6-4 下午2:30:46
 */
public class UIHelper
{
	// url跳转
	public static void showHTML(Activity context, String url)
	{
		Intent intent = new Intent();
		intent.setAction("android.intent.action.VIEW");
		Uri content_url = Uri.parse(url);
		intent.setData(content_url);
		context.startActivity(intent);
	}

	// 跳主Activity
	public static void showMain(Activity OldActivity, boolean isFinish)
	{
		Intent intent = new Intent(OldActivity, MainActivity.class);
		OldActivity.startActivity(intent);
		if (isFinish)
			OldActivity.finish();
	}

	// 普通跳转
	public static void showNormal(Activity OldActivity, Class<?> cls, boolean isFinish)
	{
		Intent intent = new Intent(OldActivity, cls);
		OldActivity.startActivity(intent);
		if (isFinish)
			OldActivity.finish();
	}

	// 跳转返回
	public static void showForsult(Activity OldActivity, Class<?> cls, int RequestCode)
	{
		Intent intent = new Intent(OldActivity, cls);
		OldActivity.startActivityForResult(intent, RequestCode);
	}

}
