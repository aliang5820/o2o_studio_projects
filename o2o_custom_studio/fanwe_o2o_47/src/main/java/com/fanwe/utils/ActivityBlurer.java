package com.fanwe.utils;

import android.app.Activity;
import android.graphics.Color;
import android.view.View;
import android.view.animation.AlphaAnimation;
import android.widget.FrameLayout;

public class ActivityBlurer
{

	private Activity mActivity;

	private View mViewBlur;
	private boolean mIsFinishByUser = false;
	private AlphaAnimation mAlphaAnimShow = new AlphaAnimation(0, 1);
	private AlphaAnimation mAlphaAnimHide = new AlphaAnimation(1, 0);

	public ActivityBlurer(Activity activity)
	{
		super();
		this.mActivity = activity;
		init();
	}

	private void init()
	{
		mAlphaAnimShow.setDuration(100);
		mAlphaAnimHide.setDuration(200);
	}

	public void onResume()
	{
		if (mViewBlur != null)
		{
			mViewBlur.startAnimation(mAlphaAnimHide);
			mViewBlur.setVisibility(View.GONE);
		}
	}

	public void finish()
	{
		mIsFinishByUser = true;
		mAlphaAnimShow = null;
		mAlphaAnimHide = null;
		mViewBlur = null;
		System.gc();
	}

	public void onPause()
	{
		if (!mIsFinishByUser)
		{
			if (mViewBlur == null)
			{
				FrameLayout flAll = (FrameLayout) mActivity.findViewById(android.R.id.content);
				mViewBlur = new View(mActivity);
				mViewBlur.setBackgroundColor(Color.parseColor("#77000000"));
				mViewBlur.setVisibility(View.GONE);
				flAll.addView(mViewBlur);
			}
			mViewBlur.startAnimation(mAlphaAnimShow);
			mViewBlur.setVisibility(View.VISIBLE);
		}
	}
}
