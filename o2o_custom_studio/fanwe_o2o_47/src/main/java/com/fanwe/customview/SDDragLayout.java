package com.fanwe.customview;

import android.content.Context;
import android.support.v4.widget.ViewDragHelper;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;
import android.widget.LinearLayout;

public class SDDragLayout extends LinearLayout
{
	private ViewDragHelper mDragHelper;
	private View mDragView;

	private float mReleasedX = 0;
	private float mReleasedY = 0;

	private SDDragLayoutListener mListener;

	public void setmListener(SDDragLayoutListener mListener)
	{
		this.mListener = mListener;
	}

	public float getmReleasedX()
	{
		return mReleasedX;
	}

	public float getmReleasedY()
	{
		return mReleasedY;
	}

	public SDDragLayout(Context context)
	{
		this(context, null);
	}

	public SDDragLayout(Context context, AttributeSet attrs)
	{
		this(context, attrs, 0);
	}

	public void setDragView(View view)
	{
		this.mDragView = view;
	}

	public SDDragLayout(Context context, AttributeSet attrs, int defStyle)
	{
		super(context, attrs, defStyle);
		mDragHelper = ViewDragHelper.create(this, 1f, new DragHelperCallback());
	}

	private class DragHelperCallback extends ViewDragHelper.Callback
	{

		@Override
		public boolean tryCaptureView(View child, int pointerId)
		{
			return child == mDragView;
		}

		@Override
		public int getViewHorizontalDragRange(View child)
		{
			return getMeasuredWidth();
		}

		@Override
		public int getViewVerticalDragRange(View child)
		{
			return getMeasuredHeight();
		}

		@Override
		public int clampViewPositionVertical(View child, int top, int dy)
		{
			final int topBound = getPaddingTop();
			final int bottomBound = getHeight() - mDragView.getHeight();
			final int newTop = Math.min(Math.max(top, topBound), bottomBound);
			return newTop;
		}

		@Override
		public int clampViewPositionHorizontal(View child, int left, int dx)
		{
			final int leftBound = getPaddingLeft();
			final int rightBound = getWidth() - mDragView.getWidth();
			final int newLeft = Math.min(Math.max(left, leftBound), rightBound);
			return newLeft;
		}

		@Override
		public void onViewReleased(View releasedChild, float xvel, float yvel)
		{
			if (mListener != null)
			{
				mListener.onViewReleased(releasedChild, xvel, yvel);
			}
		}
	}

	@Override
	public boolean onInterceptTouchEvent(MotionEvent ev)
	{
		return mDragHelper.shouldInterceptTouchEvent(ev);
	}

	@Override
	public boolean onTouchEvent(MotionEvent ev)
	{
		mDragHelper.processTouchEvent(ev);
		if (mDragHelper.isViewUnder(mDragView, (int) ev.getX(), (int) ev.getY()))
		{
			return true;
		}
		return false;
	}

	public static abstract class SDDragLayoutListener
	{
		public void onViewReleased(View releasedChild, float xvel, float yvel)
		{
		}
	}

}
