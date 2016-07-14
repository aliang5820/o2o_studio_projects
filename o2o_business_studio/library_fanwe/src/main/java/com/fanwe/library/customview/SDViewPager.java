package com.fanwe.library.customview;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;

public class SDViewPager extends HackyViewPager
{

	private EnumMeasureMode mMeasureMode = EnumMeasureMode.NORMAL;

	public void setmMeasureMode(EnumMeasureMode measureMode)
	{
		if (measureMode != null)
		{
			this.mMeasureMode = measureMode;
			requestLayout();
		}
	}

	public SDViewPager(Context context)
	{
		this(context, null);
	}

	public SDViewPager(Context context, AttributeSet attrs)
	{
		super(context, attrs);
	}

	@Override
	protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec)
	{
		switch (mMeasureMode)
		{
		case NORMAL:
			measureNormal(widthMeasureSpec, heightMeasureSpec);
			break;
		case FIRST_CHILD:
			measureByFirstHeight(widthMeasureSpec, heightMeasureSpec);
			break;
		case MAX_CHILD:
			measureByMaxHeight(widthMeasureSpec, heightMeasureSpec);
			break;
		default:
			measureNormal(widthMeasureSpec, heightMeasureSpec);
			break;
		}
	}

	protected void measureNormal(int widthMeasureSpec, int heightMeasureSpec)
	{
		super.onMeasure(widthMeasureSpec, heightMeasureSpec);
	}

	protected void measureByMaxHeight(int widthMeasureSpec, int heightMeasureSpec)
	{
		int height = 0;
		for (int i = 0; i < getChildCount(); i++)
		{
			View child = getChildAt(i);
			child.measure(widthMeasureSpec, MeasureSpec.makeMeasureSpec(0, MeasureSpec.UNSPECIFIED));
			int h = child.getMeasuredHeight();
			if (h > height)
			{
				height = h;
			}
		}
		heightMeasureSpec = MeasureSpec.makeMeasureSpec(height, MeasureSpec.EXACTLY);
		super.onMeasure(widthMeasureSpec, heightMeasureSpec);
	}

	protected void measureByFirstHeight(int widthMeasureSpec, int heightMeasureSpec)
	{
		View view = getChildAt(0);
		if (view != null)
		{
			view.measure(widthMeasureSpec, heightMeasureSpec);
		}
		setMeasuredDimension(getMeasuredWidth(), measureHeight(heightMeasureSpec, view));
	}

	protected int measureHeight(int measureSpec, View view)
	{
		int result = 0;
		int specMode = MeasureSpec.getMode(measureSpec);
		int specSize = MeasureSpec.getSize(measureSpec);
		if (specMode == MeasureSpec.EXACTLY)
		{
			result = specSize;
		} else
		{
			if (view != null)
			{
				result = view.getMeasuredHeight();
			}
			if (specMode == MeasureSpec.AT_MOST)
			{
				result = Math.min(result, specSize);
			}
		}
		return result;
	}

	public enum EnumMeasureMode
	{
		/** 正常模式 */
		NORMAL,
		/** 按最大页面的高度来设置高度 */
		MAX_CHILD,
		/** 按第一个页面的高度来设置高度 */
		FIRST_CHILD;
	}

}
