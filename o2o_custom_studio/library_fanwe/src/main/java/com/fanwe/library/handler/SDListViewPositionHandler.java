package com.fanwe.library.handler;

import android.widget.AbsListView;
import android.widget.ListAdapter;

import com.fanwe.library.handler.SDViewSizeHandler.SDViewSizeHandlerListener;
import com.fanwe.library.utils.SDViewUtil;

public class SDListViewPositionHandler
{

	private AbsListView mListView;
	private SDViewSizeHandler mViewSizeHandler = new SDViewSizeHandler();

	public SDListViewPositionHandler()
	{
		mViewSizeHandler.setmListener(mListenerViewSize);
	}

	public void setTargetView(AbsListView listView)
	{
		this.mListView = listView;
		mViewSizeHandler.setTargetView(listView);
	}

	private SDViewSizeHandlerListener mListenerViewSize = new SDViewSizeHandlerListener()
	{

		@Override
		public void onWidthChanged(int newWidth, int oldWidth)
		{

		}

		@Override
		public void onHeightChanged(int newHeight, int oldHeight)
		{
			if (oldHeight <= 0)
			{
				return;
			}
			ListAdapter listAdapter = mListView.getAdapter();
			if (listAdapter == null)
			{
				return;
			}
			int totalCount = listAdapter.getCount();
			if (totalCount <= 0)
			{
				return;
			}

			int differ = newHeight - oldHeight;
			if (differ < 0)
			{
				// item需要向上滚动
				scroll(differ, newHeight);
			} else
			{

			}
		}
	};

	private void scroll(int differ, int newHeight)
	{
		int differAbs = Math.abs(differ);
		if (differAbs > newHeight)
		{
			// 需要滚动多次
			int time = differAbs / newHeight;
			for (int i = 0; i < time; i++)
			{
				SDViewUtil.scrollListBy(differ, mListView);
			}

			int left = differAbs % newHeight;
			if (left > 0)
			{
				if (differ < 0)
				{
					left = -left;
				}
				SDViewUtil.scrollListBy(left, mListView);
			}
		} else
		{
			SDViewUtil.scrollListBy(differ, mListView);
		}
	}

}
