package com.fanwe.library.utils;

import android.view.View;
import android.view.ViewTreeObserver.OnGlobalLayoutListener;

public class SDViewSizeHandler
{

	private View mView;
	private int mWidth;
	private int mHeight;

	private SDViewSizeHandlerListener mListener;

	public void setListener(SDViewSizeHandlerListener listener)
	{
		this.mListener = listener;
	}

	private void reset()
	{
		mWidth = 0;
		mHeight = 0;
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
		int oldWidth = mWidth;
		int oldHeight = mHeight;

		int newWidth = mView.getWidth();
		int newHeight = mView.getHeight();

		if (newWidth != oldWidth)
		{
			mWidth = newWidth;
			if (mListener != null)
			{
				mListener.onWidthChanged(newWidth, oldWidth);
			}
		}

		if (newHeight != oldHeight)
		{
			mHeight = newHeight;
			if (mListener != null)
			{
				mListener.onHeightChanged(newHeight, oldHeight);
			}
		}
	}

	public interface SDViewSizeHandlerListener
	{
		public void onWidthChanged(int newWidth, int oldWidth);

		public void onHeightChanged(int newHeight, int oldHeight);
	}

}
