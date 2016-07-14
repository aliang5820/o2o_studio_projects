package com.fanwe.customview;

import java.util.ArrayList;
import java.util.List;

import android.content.Context;
import android.graphics.Rect;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewConfiguration;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.Scroller;

public class SDSlidingFinishLayout extends RelativeLayout
{
	private static final String TAG = "SDSlidingFinishLayout";
	private static final int ANIMATION_TIME = 200;

	private ViewGroup mParent = null;

	private Scroller mScroller = null;
	private int mAvalibleTouchWidth = 0;
	private boolean mCanSliding = false;
	private boolean mCanFinish = false;

	private int mStartX = 0;
	private int mStartY = 0;
	private int mTempX = 0;
	private int mTempY = 0;

	private int mViewWidth = 0;

	private SDSlidingFinishLayoutListener mListener = null;

	private int mMinDistance = 0;

	private int mMinDistanceY = ViewConfiguration.get(getContext()).getScaledTouchSlop();

	private List<View> mListIgnoredViews = new ArrayList<View>();

	public int getmAvalibleTouchWidth()
	{
		return mAvalibleTouchWidth;
	}

	public void setmAvalibleTouchWidth(int mAvalibleTouchWidth)
	{
		this.mAvalibleTouchWidth = mAvalibleTouchWidth;
	}

	public SDSlidingFinishLayoutListener getmListener()
	{
		return mListener;
	}

	public void setmListener(SDSlidingFinishLayoutListener mListener)
	{
		this.mListener = mListener;
	}

	public SDSlidingFinishLayout(Context context)
	{
		this(context, null, 0);
	}

	public SDSlidingFinishLayout(Context context, AttributeSet attrs)
	{
		this(context, attrs, 0);
	}

	public SDSlidingFinishLayout(Context context, AttributeSet attrs, int defStyle)
	{
		super(context, attrs, defStyle);
		init();
	}

	private void init()
	{
		mScroller = new Scroller(getContext());
		mMinDistance = ViewConfiguration.get(getContext()).getScaledTouchSlop();
	}

	@Override
	protected void onLayout(boolean changed, int l, int t, int r, int b)
	{
		if (changed)
		{
			mParent = (ViewGroup) this.getParent();
			mViewWidth = this.getWidth();
		}
		super.onLayout(changed, l, t, r, b);
	}

	@Override
	public boolean onInterceptTouchEvent(MotionEvent ev)
	{
		switch (ev.getAction())
		{
		case MotionEvent.ACTION_DOWN:
			mStartX = (int) ev.getRawX();
			mStartY = (int) ev.getRawY();
			mTempX = mStartX;
			mTempY = mStartY;
			break;
		case MotionEvent.ACTION_MOVE:
			int moveX = (int) ev.getRawX();
			int moveY = (int) ev.getRawY();

			int deltaX = mTempX - moveX;
			int deltaY = mTempY - moveY;
			mTempX = moveX;
			mTempY = moveY;

			if (!isInIgnoredView(ev) && Math.abs(moveY - mStartY) < mMinDistanceY && deltaX < 0 && Math.abs(moveX - mStartX) > mMinDistance)
			{
				if (mAvalibleTouchWidth <= 0)
				{
					return true;
				} else if (mStartX < mAvalibleTouchWidth)
				{
					return true;
				}

			} else
			{
				return false;
			}

			break;
		case MotionEvent.ACTION_UP:
			break;
		}
		return super.onInterceptTouchEvent(ev);
	}

	@Override
	public boolean onTouchEvent(MotionEvent event)
	{
		switch (event.getAction())
		{
		case MotionEvent.ACTION_DOWN:
			mStartX = (int) event.getRawX();
			mStartY = (int) event.getRawY();
			mTempX = mStartX;
			mTempY = mStartY;
			break;
		case MotionEvent.ACTION_MOVE:
			int moveX = (int) event.getRawX();
			int moveY = (int) event.getRawY();

			int deltaX = mTempX - moveX;
			int deltaY = mTempY - moveY;
			mTempX = moveX;
			mTempY = moveY;

			if (Math.abs(moveY - mStartY) < mMinDistanceY && Math.abs(moveX - mStartX) > mMinDistance)
			{
				mCanSliding = true;
			}

			if (mCanSliding && moveX - mStartX > 0)
			{
				mParent.scrollBy(deltaX, 0);
				if (mListener != null)
				{
					mListener.onScrolling();
				}
			}
			break;
		case MotionEvent.ACTION_UP:
			mCanSliding = false;
			if (-mParent.getScrollX() > mViewWidth / 3)
			{
				mCanFinish = true;
				scrollRight();

			} else
			{
				mCanFinish = false;
				scrollOriginalPositon();

			}

			break;
		}
		return true;
	}

	private void scrollOriginalPositon()
	{
		mScroller.startScroll(mParent.getScrollX(), 0, -mParent.getScrollX(), 0, ANIMATION_TIME);
		postInvalidate();
	}

	private void scrollRight()
	{
		mScroller.startScroll(mParent.getScrollX(), 0, -(mViewWidth + mParent.getScrollX()) + 1, 0, ANIMATION_TIME);
		postInvalidate();
	}

	@Override
	public void computeScroll()
	{
		if (mScroller.computeScrollOffset())
		{
			mParent.scrollTo(mScroller.getCurrX(), mScroller.getCurrY());
			postInvalidate();
			if (mScroller.isFinished())
			{
				if (mListener != null)
				{
					if (mCanFinish)
					{
						mListener.onFinish();
					} else
					{
						mListener.onScrollToStart();
					}
				}
			}
		}
		super.computeScroll();
	}

	private boolean isInIgnoredView(MotionEvent ev)
	{
		Rect rect = new Rect();
		for (View v : mListIgnoredViews)
		{
			v.getGlobalVisibleRect(rect);
			if (rect.contains((int) ev.getX(), (int) ev.getY()))
			{
				return true;
			}
		}
		return false;
	}

	public void addIgnoredView(View v)
	{
		if (!mListIgnoredViews.contains(v))
		{
			mListIgnoredViews.add(v);
		}
	}

	public void removeIgnoredView(View v)
	{
		mListIgnoredViews.remove(v);
	}

	public void clearIgnoredViews()
	{
		mListIgnoredViews.clear();
	}

	public interface SDSlidingFinishLayoutListener
	{
		public void onFinish();

		public void onScrolling();

		public void onScrollToStart();
	}

}
