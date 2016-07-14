package com.fanwe.library.handler;

import android.view.View;
import android.view.ViewTreeObserver.OnGlobalLayoutListener;

public class SDViewLocationHandler
{

	private View mView;
	private int mX;
	private int mY;

	private SDViewLocationHandlerListener mListener;

	public void setmListener(SDViewLocationHandlerListener listener)
	{
		this.mListener = listener;
	}

	public void setTargetView(View view)
	{
		this.mView = view;
		handlerView(view);
	}

	private void handlerView(View view)
	{
		view.getViewTreeObserver().addOnGlobalLayoutListener(new OnGlobalLayoutListener()
		{
			@Override
			public void onGlobalLayout()
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
		});
	}

	private int[] getLocationOnScreen(View v)
	{
		int[] location = new int[2];
		v.getLocationOnScreen(location);
		return location;
	}

	public interface SDViewLocationHandlerListener
	{
		public void onYChanged(int newY, int oldY);

		public void onXChanged(int newX, int oldX);
	}

}
