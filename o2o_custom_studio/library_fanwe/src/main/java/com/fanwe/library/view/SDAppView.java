package com.fanwe.library.view;

import android.app.Activity;
import android.content.Context;
import android.util.AttributeSet;
import android.widget.LinearLayout;

public class SDAppView extends LinearLayout
{

	private Activity mActivity;

	public SDAppView(Context context, AttributeSet attrs, int defStyle)
	{
		super(context, attrs, defStyle);
		baseInit();
	}

	public SDAppView(Context context, AttributeSet attrs)
	{
		super(context, attrs);
		baseInit();
	}

	public SDAppView(Context context)
	{
		super(context);
		baseInit();
	}

	public void setActivity(Activity activity)
	{
		this.mActivity = activity;
	}

	public Activity getActivity()
	{
		return mActivity;
	}

	private void baseInit()
	{
		Context context = getContext();
		if (context instanceof Activity)
		{
			mActivity = (Activity) context;
		}
	}

	/**
	 * 为了统一规范，子类的初始化操作重写此方法，然后在需要初始化的地方调用，父类不调用此方法
	 */
	protected void init()
	{

	}

}
