package com.fanwe.umeng;

import android.content.Context;

import com.umeng.message.PushAgent;

public class UmengPushManager
{
	private static PushAgent mPushAgent;

	/**
	 * 必须在Application的onCreate方法中调用
	 * 
	 * @param context
	 */
	public static void init(Context context)
	{
		mPushAgent = PushAgent.getInstance(context);
		mPushAgent.setNotificationClickHandler(new AppUmengNotificationClickHandler());
		mPushAgent.enable();
	}

	public static PushAgent getPushAgent()
	{
		return mPushAgent;
	}

}
