package com.fanwe.utils;

import android.app.Application;
import android.app.Notification;
import android.app.NotificationManager;
import android.content.Context;
import android.widget.RemoteViews;

import com.fanwe.app.App;

public class SDNotificationUtil
{

	private static NotificationManager mManager = (NotificationManager) getApplication().getSystemService(Context.NOTIFICATION_SERVICE);

	public static void notify(int id, Notification notification)
	{
		notify(null, id, notification);
	}

	public static void notify(String tag, int id, Notification notification)
	{
		mManager.notify(tag, id, notification);
	}

	public static void cancel(int id)
	{
		cancel(null, id);
	}

	public static void cancel(String tag, int id)
	{
		mManager.cancel(tag, id);
	}

	public static RemoteViews createRemoteViews(int layoutId)
	{
		RemoteViews remoteViews = null;
		if (layoutId != 0)
		{
			remoteViews = new RemoteViews(getApplication().getPackageName(), layoutId);
		}
		return remoteViews;
	}

	public static Notification createNotification(int resIdIcon, String tickerText, int layoutId)
	{
		return createNotification(resIdIcon, tickerText, createRemoteViews(layoutId));
	}

	public static Notification createNotification(int resIdIcon, String tickerText, RemoteViews remoteView)
	{
		Notification notification = new Notification();

		if (resIdIcon != 0)
		{
			notification.icon = resIdIcon;
		}

		if (tickerText != null)
		{
			notification.tickerText = tickerText;
		}

		if (remoteView != null)
		{
			notification.contentView = remoteView;
		}

		return notification;
	}

	public static Application getApplication()
	{
		return App.getApplication();
	}

}
