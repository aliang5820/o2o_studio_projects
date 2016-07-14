package com.fanwe.common;

import android.view.View;
import android.view.View.OnClickListener;

import com.fanwe.customview.SDBottomNavigatorBaseItem;

public class SDViewNavigatorManager
{

	private SDBottomNavigatorBaseItem[] mItems = null;

	private int mCurrentIndex = -1;

	private SDViewNavigatorManagerListener mListener = null;

	public int getmCurrentIndex()
	{
		return mCurrentIndex;
	}

	public SDViewNavigatorManagerListener getmListener()
	{
		return mListener;
	}

	public void setmListener(SDViewNavigatorManagerListener mListener)
	{
		this.mListener = mListener;
	}

	public void setItems(SDBottomNavigatorBaseItem[] items)
	{
		if (items != null && items.length > 0)
		{
			mItems = items;
			for (int i = 0; i < mItems.length; i++)
			{
				mItems[i].setId(i);
				mItems[i].setOnClickListener(new SDBottomNavigatorView_listener());
				mItems[i].setSelectedState(false);
			}
		}
	}

	class SDBottomNavigatorView_listener implements OnClickListener
	{

		@Override
		public void onClick(View v)
		{
			setSelectIndex(v.getId(), v, true);
		}

	}

	public boolean setSelectIndex(int index, View v, boolean notifyListener)
	{
		if (mItems != null && mItems.length > 0 && index < mItems.length)
		{
			if (index != mCurrentIndex)
			{
				mItems[index].setSelectedState(true);
				if (mCurrentIndex != -1)
				{
					mItems[mCurrentIndex].setSelectedState(false);
				}
				mCurrentIndex = index;
				if (mListener != null && notifyListener)
				{
					mListener.onItemClick(v, index);
				}
				return true;
			}
		}
		return false;
	}

	public interface SDViewNavigatorManagerListener
	{
		public void onItemClick(View v, int index);
	}

}
