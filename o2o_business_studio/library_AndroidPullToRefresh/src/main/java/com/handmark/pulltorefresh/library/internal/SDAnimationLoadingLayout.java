package com.handmark.pulltorefresh.library.internal;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.drawable.AnimationDrawable;
import android.graphics.drawable.Drawable;

import com.handmark.pulltorefresh.library.R;
import com.handmark.pulltorefresh.library.PullToRefreshBase.Mode;
import com.handmark.pulltorefresh.library.PullToRefreshBase.Orientation;
import com.handmark.pulltorefresh.library.internal.LoadingLayout;

public class SDAnimationLoadingLayout extends LoadingLayout
{

	protected Drawable mDrawablePrepare;
	protected Drawable mDrawableRefreshing;

	public SDAnimationLoadingLayout(Context context, Mode mode, Orientation scrollDirection, TypedArray attrs)
	{
		super(context, mode, scrollDirection, attrs);

		if (attrs.hasValue(R.styleable.PullToRefresh_ptrDrawableAnimationPrepare))
		{
			mDrawablePrepare = attrs.getDrawable(R.styleable.PullToRefresh_ptrDrawableAnimationPrepare);
		}

		if (attrs.hasValue(R.styleable.PullToRefresh_ptrDrawableAnimationRefreshing))
		{
			mDrawableRefreshing = attrs.getDrawable(R.styleable.PullToRefresh_ptrDrawableAnimationRefreshing);
		}
	}

	public void setAnimationPrepareResId(int animationPrepareResId)
	{
		if (animationPrepareResId != 0)
		{
			mDrawablePrepare = getResources().getDrawable(animationPrepareResId);
		} else
		{
			mDrawablePrepare = null;
		}
	}

	public void setAnimationRefreshingResId(int animationRefreshingResId)
	{
		if (animationRefreshingResId != 0)
		{
			mDrawableRefreshing = getResources().getDrawable(animationRefreshingResId);
		} else
		{
			mDrawableRefreshing = null;
		}
	}

	@Override
	protected int getDefaultDrawableResId()
	{
		return 0;
	}

	@Override
	protected void onLoadingDrawableSet(Drawable imageDrawable)
	{

	}

	@Override
	protected void onPullImpl(float scaleOfLayout)
	{
		if (mDrawablePrepare instanceof AnimationDrawable)
		{
			AnimationDrawable animationDrawable = (AnimationDrawable) mDrawablePrepare;

			int totalCount = animationDrawable.getNumberOfFrames();
			int index = 0;
			if (scaleOfLayout >= 1)
			{
				index = totalCount - 1;
			} else
			{
				double percent = scaleOfLayout % 1;
				index = (int) (percent * totalCount);
			}
			setLoadingDrawable(animationDrawable.getFrame(index));
		}
	}

	@Override
	protected void pullToRefreshImpl()
	{

	}

	@Override
	protected void refreshingImpl()
	{
		if (mDrawableRefreshing != null)
		{
			setLoadingDrawable(mDrawableRefreshing);
		}
	}

	@Override
	protected void releaseToRefreshImpl()
	{

	}

	@Override
	protected void resetImpl()
	{
		if (mDrawablePrepare != null)
		{
			setLoadingDrawable(mDrawablePrepare);
		}
	}

}
