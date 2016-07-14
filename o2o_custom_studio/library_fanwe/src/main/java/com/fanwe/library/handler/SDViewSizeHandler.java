package com.fanwe.library.handler;

import android.view.View;
import android.view.ViewTreeObserver.OnGlobalLayoutListener;

public class SDViewSizeHandler
{

	private View mView;
	private int mWidth;
	private int mHeight;

	private SDViewSizeHandlerListener mListener;

	public void setmListener(SDViewSizeHandlerListener listener)
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
		});
	}

	public interface SDViewSizeHandlerListener
	{
		public void onWidthChanged(int newWidth, int oldWidth);

		public void onHeightChanged(int newHeight, int oldHeight);
	}

}
