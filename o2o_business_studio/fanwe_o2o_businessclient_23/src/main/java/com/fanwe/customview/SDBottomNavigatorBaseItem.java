package com.fanwe.customview;

import android.annotation.SuppressLint;
import android.content.Context;
import android.util.AttributeSet;
import android.widget.LinearLayout;

@SuppressLint("NewApi")
public abstract class SDBottomNavigatorBaseItem extends LinearLayout
{
	protected boolean mSelected = false;

	public SDBottomNavigatorBaseItem(Context context, AttributeSet attrs, int defStyle)
	{
		super(context, attrs, defStyle);
		// TODO Auto-generated constructor stub
	}

	public SDBottomNavigatorBaseItem(Context context, AttributeSet attrs)
	{
		super(context, attrs);
		// TODO Auto-generated constructor stub
	}

	public SDBottomNavigatorBaseItem(Context context)
	{
		super(context);
		// TODO Auto-generated constructor stub
	}

	public abstract void setSelectedState(boolean select);

	public abstract boolean getSelectedState();

}
