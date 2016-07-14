package com.handmark.pulltorefresh.library.internal;

import android.view.View;
import android.widget.AbsListView;
import android.widget.Adapter;

public class SDUtils
{

	public static void measureView(View v)
	{
		int w = View.MeasureSpec.makeMeasureSpec(0, View.MeasureSpec.UNSPECIFIED);
		int h = View.MeasureSpec.makeMeasureSpec(0, View.MeasureSpec.UNSPECIFIED);
		v.measure(w, h);
	}

	public static boolean isFirstItemTotallyVisible(AbsListView absListView)
	{
		final Adapter adapter = absListView.getAdapter();
		if (null == adapter || adapter.isEmpty())
		{
			return true;
		} else
		{
			if (absListView.getFirstVisiblePosition() <= 1)
			{
				final View firstVisibleChild = absListView.getChildAt(0);
				if (firstVisibleChild != null)
				{
					return firstVisibleChild.getTop() >= absListView.getTop();
				}
			}
		}
		return false;
	}

	public static boolean isLastItemTotallyVisible(AbsListView absListView)
	{
		final Adapter adapter = absListView.getAdapter();
		if (null == adapter || adapter.isEmpty())
		{
			return true;
		} else
		{
			final int lastItemPosition = absListView.getCount() - 1;
			final int lastVisiblePosition = absListView.getLastVisiblePosition();
			if (lastVisiblePosition >= lastItemPosition - 1)
			{
				final int childIndex = lastVisiblePosition - absListView.getFirstVisiblePosition();
				final View lastVisibleChild = absListView.getChildAt(childIndex);
				if (lastVisibleChild != null)
				{
					return lastVisibleChild.getBottom() <= absListView.getBottom();
				}
			}
		}
		return false;
	}

}
