package com.fanwe.library.webview;

import android.app.Activity;
import android.content.Intent;

public class BaseJsHandler
{
	private static final String DEFAULT_NAME = "App";

	private String name;
	private Activity mActivity;

	public BaseJsHandler(Activity activity)
	{
		this(DEFAULT_NAME, activity);
	}

	public BaseJsHandler(String name, Activity activity)
	{
		this.name = name;
		this.mActivity = activity;
	}

	public Activity getActivity()
	{
		return mActivity;
	}

	public String getName()
	{
		return name;
	}

	public void setName(String name)
	{
		this.name = name;
	}

	protected void startActivity(Intent intent)
	{
		if (intent != null && mActivity != null)
		{
			mActivity.startActivity(intent);
		}
	}

	protected void finish()
	{
		if (mActivity != null)
		{
			mActivity.finish();
		}
	}

}
