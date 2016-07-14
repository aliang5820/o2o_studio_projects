package com.fanwe.customview;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.GridView;

public class SDGridViewInScroll extends GridView
{

	public SDGridViewInScroll(Context context)
	{
		super(context);
	}

	public SDGridViewInScroll(Context context, AttributeSet attrs, int defStyle)
	{
		super(context, attrs, defStyle);
	}

	public SDGridViewInScroll(Context context, AttributeSet attrs)
	{
		super(context, attrs);
	}

	@Override
	protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec)
	{

		int expandSpec = MeasureSpec.makeMeasureSpec(Integer.MAX_VALUE >> 2, MeasureSpec.AT_MOST);

		super.onMeasure(widthMeasureSpec, expandSpec);
	}

}
