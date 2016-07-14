package com.fanwe.umeng.push;

import android.content.Context;

import com.umeng.message.UmengNotificationClickHandler;
import com.umeng.message.entity.UMessage;

/**
 * 友盟推送通知栏点击事件处理
 * 
 * @author Administrator
 * 
 */
public class AppUmengNotificationClickHandler extends UmengNotificationClickHandler
{
	@Override
	public void dealWithCustomAction(Context context, UMessage msg)
	{
		super.dealWithCustomAction(context, msg);
	}

	@Override
	public void autoUpdate(Context context, UMessage msg)
	{
		super.autoUpdate(context, msg);
	}

	@Override
	public void dismissNotification(Context context, UMessage msg)
	{
		super.dismissNotification(context, msg);
	}

	@Override
	public void handleMessage(Context context, UMessage msg)
	{
		super.handleMessage(context, msg);
	}

	@Override
	public void launchApp(Context context, UMessage msg)
	{
		super.launchApp(context, msg);
	}

	@Override
	public void openActivity(Context context, UMessage msg)
	{
		super.openActivity(context, msg);
	}

	@Override
	public void openUrl(Context context, UMessage msg)
	{
		super.openUrl(context, msg);
	}
}
