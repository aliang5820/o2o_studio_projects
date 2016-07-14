package com.fanwe.fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import com.baidu.mapapi.model.LatLng;
import com.baidu.mapapi.search.route.RoutePlanSearch;
import com.fanwe.o2o.newo2o.R;
import com.handmark.pulltorefresh.library.PullToRefreshBase;
import com.handmark.pulltorefresh.library.PullToRefreshBase.Mode;
import com.handmark.pulltorefresh.library.PullToRefreshBase.OnRefreshListener2;
import com.handmark.pulltorefresh.library.PullToRefreshListView;
import com.lidroid.xutils.view.annotation.ViewInject;

/**
 * 
 * @author yhz
 * @create time 2014-11-13
 */
public class RouteinfoTabBaseFragment extends BaseFragment
{
	@ViewInject(R.id.frag_routeinfo_base_lv_content)
	protected PullToRefreshListView mPtrlvContent = null;

	protected RoutePlanSearch mSearch;
	protected LatLng mLlStart = null;
	protected LatLng mLlEnd = null;

	public LatLng getmLlStart()
	{
		return mLlStart;
	}

	public void setmLlStart(LatLng mLlStart)
	{
		this.mLlStart = mLlStart;
	}

	public LatLng getmLlEnd()
	{
		return mLlEnd;
	}

	public void setmLlEnd(LatLng mLlEnd)
	{
		this.mLlEnd = mLlEnd;
	}

	@Override
	protected View onCreateContentView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState)
	{
		return setContentView(R.layout.frag_routeinfo_base);
	}

	@Override
	protected void init()
	{
		findViews(getView());
		bindDefaultData();
		initPullToRefreshListView();
	}

	public RoutePlanSearch newRoutePlanSearch()
	{
		destroyRoutePlanSearch();
		mSearch = RoutePlanSearch.newInstance();
		return mSearch;
	}

	public void destroyRoutePlanSearch()
	{
		if (mSearch != null)
		{
			mSearch.destroy();
			mSearch = null;
		}
	}

	public void findViews(View view)
	{
		mPtrlvContent = (PullToRefreshListView) view.findViewById(R.id.frag_routeinfo_base_lv_content);
	}

	public void initPullToRefreshListView()
	{
		mPtrlvContent.setMode(Mode.PULL_FROM_START);
		mPtrlvContent.setOnRefreshListener(new OnRefreshListener2<ListView>()
		{

			@Override
			public void onPullDownToRefresh(PullToRefreshBase<ListView> refreshView)
			{

				requestData();
			}

			@Override
			public void onPullUpToRefresh(PullToRefreshBase<ListView> refreshView)
			{
			}
		});
		mPtrlvContent.setRefreshing();
	}

	public void refreshData()
	{
		requestData();
	}

	protected void requestData()
	{

	}

	public void stopRefresh()
	{
		mPtrlvContent.onRefreshComplete();
	}

	public void bindDefaultData()
	{

	}

	@Override
	public void onDestroy()
	{
		destroyRoutePlanSearch();
		super.onDestroy();
	}

}
