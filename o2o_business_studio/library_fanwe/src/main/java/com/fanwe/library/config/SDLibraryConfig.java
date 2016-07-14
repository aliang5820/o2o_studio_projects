package com.fanwe.library.config;

import android.graphics.Color;

public class SDLibraryConfig
{

	private int mMainColor;
	private int mMainColorPress;

	private int mGrayPressColor;

	private int mStrokeColor;
	private int mStrokeWidth;

	private int mCornerRadius;

	private int mTitleHeight;
	private int mTitleColor;
	private int mTitleColorPressed;

	public SDLibraryConfig()
	{
		setmStrokeWidth(1);
		setmCornerRadius(10);
		setmGrayPressColor(Color.parseColor("#E5E5E5"));
		setmStrokeColor(Color.parseColor("#E5E5E5"));
		setmMainColor(Color.parseColor("#FC7507"));
		setmMainColorPress(Color.parseColor("#FFCC66"));
		setmTitleHeight(80);
		setmTitleColor(Color.parseColor("#FC7507"));
		setmTitleColorPressed(Color.parseColor("#FFCC66"));
	}

	public int getmTitleColorPressed()
	{
		return mTitleColorPressed;
	}

	public void setmTitleColorPressed(int mTitleColorPressed)
	{
		this.mTitleColorPressed = mTitleColorPressed;
	}

	public int getmTitleColor()
	{
		return mTitleColor;
	}

	public void setmTitleColor(int mTitleColor)
	{
		this.mTitleColor = mTitleColor;
	}

	public int getmTitleHeight()
	{
		return mTitleHeight;
	}

	public void setmTitleHeight(int mTitleHeight)
	{
		this.mTitleHeight = mTitleHeight;
	}

	public int getmCornerRadius()
	{
		return mCornerRadius;
	}

	public void setmCornerRadius(int mCornerRadius)
	{
		this.mCornerRadius = mCornerRadius;
	}

	public int getmStrokeColor()
	{
		return mStrokeColor;
	}

	public void setmStrokeColor(int mStrokeColor)
	{
		this.mStrokeColor = mStrokeColor;
	}

	public int getmGrayPressColor()
	{
		return mGrayPressColor;
	}

	public void setmGrayPressColor(int mGrayPressColor)
	{
		this.mGrayPressColor = mGrayPressColor;
	}

	public int getmStrokeWidth()
	{
		return mStrokeWidth;
	}

	public void setmStrokeWidth(int mStrokeWidth)
	{
		this.mStrokeWidth = mStrokeWidth;
	}

	public int getmMainColorPress()
	{
		return mMainColorPress;
	}

	public void setmMainColorPress(int mMainColorPress)
	{
		this.mMainColorPress = mMainColorPress;
	}

	public int getmMainColor()
	{
		return mMainColor;
	}

	public void setmMainColor(int mMainColor)
	{
		this.mMainColor = mMainColor;
	}

}
