package com.handmark.pulltorefresh.library;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.AdapterView.OnItemClickListener;

import com.handmark.pulltorefresh.library.internal.SDUtils;

public class SDPullToRefreshListView extends PullToRefreshBase<ListView>
{

	public SDPullToRefreshListView(Context context, AttributeSet attrs)
	{
		super(context, attrs);
	}

	public SDPullToRefreshListView(Context context, com.handmark.pulltorefresh.library.PullToRefreshBase.Mode mode,
			com.handmark.pulltorefresh.library.PullToRefreshBase.AnimationStyle style)
	{
		super(context, mode, style);
	}

	public SDPullToRefreshListView(Context context, com.handmark.pulltorefresh.library.PullToRefreshBase.Mode mode)
	{
		super(context, mode);
	}

	public SDPullToRefreshListView(Context context)
	{
		super(context);
	}

	@Override
	public com.handmark.pulltorefresh.library.PullToRefreshBase.Orientation getPullToRefreshScrollDirection()
	{
		return Orientation.VERTICAL;
	}

	@Override
	protected ListView createRefreshableView(Context context, AttributeSet attrs)
	{
		ListView listView = new ListView(context, attrs);
		listView.setId(R.id.listview);
		return listView;
	}

	@Override
	protected boolean isReadyForPullEnd()
	{
		return SDUtils.isLastItemTotallyVisible(getRefreshableView());
	}

	@Override
	protected boolean isReadyForPullStart()
	{
		return SDUtils.isFirstItemTotallyVisible(getRefreshableView());
	}

	//
	public void setOnItemClickListener(OnItemClickListener listener)
	{
		getRefreshableView().setOnItemClickListener(listener);
	}

	public void setAdapter(ListAdapter adapter)
	{
		getRefreshableView().setAdapter(adapter);
	}

}
