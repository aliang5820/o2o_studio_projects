package com.fanwe.library.customview;

import java.util.ArrayList;
import java.util.List;

import com.fanwe.library.utils.SDViewUtil;

import android.content.Context;
import android.support.v4.view.ViewPager;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;

/**
 * Found at http://stackoverflow.com/questions/7814017/is-it-possible-to-disable-scrolling-on-a-viewpager.
 * Convenient way to temporarily disable ViewPager navigation while interacting with ImageView.
 * 
 * Julia Zudikova
 */

/**
 * Hacky fix for Issue #4 and
 * http://code.google.com/p/android/issues/detail?id=18990
 * <p/>
 * ScaleGestureDetector seems to mess up the touch events, which means that
 * ViewGroups which make use of onInterceptTouchEvent throw a lot of
 * IllegalArgumentException: pointerIndex out of range.
 * <p/>
 * There's not much I can do in my code for now, but we can mask the result by
 * just catching the problem and ignoring it.
 * 
 * @author Chris Banes
 */
public class HackyViewPager extends ViewPager
{

	private List<View> listIgnoreView = new ArrayList<View>();
	private boolean isLocked;

	public HackyViewPager(Context context)
	{
		super(context);
		isLocked = false;
	}

	public HackyViewPager(Context context, AttributeSet attrs)
	{
		super(context, attrs);
		isLocked = false;
	}

	private boolean isTouchIgnoreView(MotionEvent ev)
	{
		for (View view : listIgnoreView)
		{
			if (SDViewUtil.isTouchView(view, (int) ev.getRawX(), (int) ev.getRawY()))
			{
				return true;
			}
		}
		return false;
	}

	public void addIgnoreView(View view)
	{
		if (view != null)
		{
			listIgnoreView.add(view);
		}
	}

	public void removeIgnoreView(View view)
	{
		if (view != null)
		{
			listIgnoreView.remove(view);
		}
	}

	public void clearIgnoreView()
	{
		listIgnoreView.clear();
	}

	@Override
	public boolean onInterceptTouchEvent(MotionEvent ev)
	{
		if (!isLocked)
		{
			try
			{
				if (isTouchIgnoreView(ev))
				{
					return false;
				} else
				{
					return super.onInterceptTouchEvent(ev);
				}
			} catch (IllegalArgumentException e)
			{
				e.printStackTrace();
				return false;
			}
		}
		return false;
	}

	@Override
	public boolean onTouchEvent(MotionEvent event)
	{
		if (!isLocked)
		{
			return super.onTouchEvent(event);
		}
		return false;
	}

	public void toggleLock()
	{
		isLocked = !isLocked;
	}

	public void setLocked(boolean isLocked)
	{
		this.isLocked = isLocked;
	}

	public boolean isLocked()
	{
		return isLocked;
	}
}
