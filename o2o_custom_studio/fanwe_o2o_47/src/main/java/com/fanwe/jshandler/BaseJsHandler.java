package com.fanwe.jshandler;

import android.app.Activity;
import android.content.Intent;

public class BaseJsHandler
{
	private String name;
	protected Activity mActivity;

	public BaseJsHandler(String name, Activity activity)
	{
		super();
		this.name = name;
		this.mActivity = activity;
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
