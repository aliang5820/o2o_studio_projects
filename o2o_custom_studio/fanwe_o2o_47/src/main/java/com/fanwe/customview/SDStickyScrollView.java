package com.fanwe.customview;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;

import com.fanwe.library.customview.StickyScrollView;
import com.handmark.pulltorefresh.library.PullToRefreshBase;

public class SDStickyScrollView extends PullToRefreshBase<StickyScrollView>
{

	public SDStickyScrollView(Context context, AttributeSet attrs)
	{
		super(context, attrs);
	}

	public SDStickyScrollView(Context context, com.handmark.pulltorefresh.library.PullToRefreshBase.Mode mode,
			com.handmark.pulltorefresh.library.PullToRefreshBase.AnimationStyle animStyle)
	{
		super(context, mode, animStyle);
	}

	public SDStickyScrollView(Context context, com.handmark.pulltorefresh.library.PullToRefreshBase.Mode mode)
	{
		super(context, mode);
	}

	public SDStickyScrollView(Context context)
	{
		super(context);
	}

	@Override
	public com.handmark.pulltorefresh.library.PullToRefreshBase.Orientation getPullToRefreshScrollDirection()
	{
		return Orientation.VERTICAL;
	}

	@Override
	protected StickyScrollView createRefreshableView(Context context, AttributeSet attrs)
	{
		return new StickyScrollView(context, attrs);
	}

	@Override
	protected boolean isReadyForPullEnd()
	{
		View scrollViewChild = getRefreshableView().getChildAt(0);
		if (null != scrollViewChild)
		{
			return getRefreshableView().getScrollY() >= (scrollViewChild.getHeight() - getHeight());
		}
		return false;
	}

	@Override
	protected boolean isReadyForPullStart()
	{
		return getRefreshableView().getScrollY() == 0;
	}

}
