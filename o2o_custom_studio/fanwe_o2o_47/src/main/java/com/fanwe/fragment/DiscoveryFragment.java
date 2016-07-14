package com.fanwe.fragment;

import android.widget.ListView;

import com.fanwe.http.InterfaceServer;
import com.fanwe.http.SDRequestCallBack;
import com.fanwe.library.utils.SDToast;
import com.fanwe.model.Discover_indexActModel;
import com.fanwe.model.RequestModel;
import com.handmark.pulltorefresh.library.PullToRefreshBase;
import com.lidroid.xutils.http.ResponseInfo;

/**
 * 发现fragment
 * 
 * @author Administrator
 * 
 */
public class DiscoveryFragment extends DynamicFragment
{

	private String tag;

	public void setTag(String tag)
	{
		this.tag = tag;
	}

	@Override
	protected void init()
	{
		super.init();
		setRefreshing();
	}

	@Override
	public void setRefreshing()
	{
		if (!isRemovedFromViewPager())
		{
			super.setRefreshing();
		}
	}

	@Override
	protected void onPullDownToRefresh(PullToRefreshBase<ListView> refreshView)
	{
		mPage.resetPage();
		requestData(false);
		super.onPullDownToRefresh(refreshView);
	}

	@Override
	protected void onPullUpToRefresh(PullToRefreshBase<ListView> refreshView)
	{
		if (mPage.increment())
		{
			requestData(true);
		} else
		{
			SDToast.showToast("没有更多数据了");
			onRefreshComplete();
		}
		super.onPullUpToRefresh(refreshView);
	}

	private void requestData(final boolean isLoadMore)
	{
		RequestModel model = new RequestModel();
		model.putCtl("discover");
		model.put("tag", tag);
		model.putPage(mPage.getPage());

		InterfaceServer.getInstance().requestInterface(model, new SDRequestCallBack<Discover_indexActModel>()
		{
			@Override
			public void onStart()
			{
			}

			@Override
			public void onSuccess(ResponseInfo<String> responseInfo)
			{
				if (actModel.getStatus() > 0)
				{
					mPage.update(actModel.getPage());
					setListModel(actModel.getData_list(), isLoadMore);
				}
			}

			@Override
			public void onFinish()
			{
				onRefreshComplete();
			}
		});
	}

}
