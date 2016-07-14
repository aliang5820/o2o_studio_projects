package com.fanwe.library.utils;

import android.view.View;
import android.view.ViewTreeObserver.OnGlobalLayoutListener;

public class SDViewPositionHandler
{

	private View mView;
	private int mX;
	private int mY;

	private SDViewPositionHandlerListener mListener;

	public void setListener(SDViewPositionHandlerListener listener)
	{
		this.mListener = listener;
	}

	private void reset()
	{
		mX = 0;
		mY = 0;
	}

	public void handle(View view)
	{
		this.mView = view;
		if (view != null)
		{
			reset();
			view.getViewTreeObserver().addOnGlobalLayoutListener(new OnGlobalLayoutListener()
			{
				@Override
				public void onGlobalLayout()
				{
					process();
				}
			});
		}
	}

	private void process()
	{
		int oldX = mX;
		int oldY = mY;

		int[] location = getLocationOnScreen(mView);
		int newX = location[0];
		int newY = location[1];

		if (newX != oldX)
		{
			mX = newX;
			if (mListener != null)
			{
				mListener.onXChanged(newX, oldX);
			}
		}

		if (newY != oldY)
		{
			mY = newY;
			if (mListener != null)
			{
				mListener.onYChanged(newY, oldY);
			}
		}
	}

	private int[] getLocationOnScreen(View v)
	{
		int[] location = new int[2];
		v.getLocationOnScreen(location);
		return location;
	}

	public interface SDViewPositionHandlerListener
	{
		public void onYChanged(int newY, int oldY);

		public void onXChanged(int newX, int oldX);
	}

}
