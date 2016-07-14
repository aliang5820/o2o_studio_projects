package com.fanwe;

import android.widget.ListView;

import com.fanwe.library.pulltorefresh.SDPullToRefresh;
import com.fanwe.library.pulltorefresh.SDPullToRefreshListener;
import com.fanwe.model.PageModel;
import com.handmark.pulltorefresh.library.PullToRefreshBase;
import com.handmark.pulltorefresh.library.PullToRefreshBase.Mode;
import com.handmark.pulltorefresh.library.PullToRefreshListView;

public class BasePullToRefreshListViewActivity extends BaseActivity implements SDPullToRefresh, SDPullToRefreshListener<PullToRefreshListView>
{

	protected PullToRefreshListView mPullView;
	protected PageModel mPageModel = new PageModel();

	protected void initPullToRefreshListView(PullToRefreshListView listView)
	{
		if (listView != null)
		{
			this.mPullView = listView;
			setModeBoth();
			listView.setOnRefreshListener(new PullToRefreshBase.OnRefreshListener2<ListView>()
			{
				@Override
				public void onPullDownToRefresh(PullToRefreshBase<ListView> refreshView)
				{
					BasePullToRefreshListViewActivity.this.onPullDownToRefresh(mPullView);
				}

				@Override
				public void onPullUpToRefresh(PullToRefreshBase<ListView> refreshView)
				{
					BasePullToRefreshListViewActivity.this.onPullUpToRefresh(mPullView);
				}
			});
		}
	}

	@Override
	public void setRefreshing()
	{
		mPullView.setRefreshing();
	}

	@Override
	public void setModePullFromStart()
	{
		mPullView.setMode(Mode.PULL_FROM_START);
	}

	@Override
	public void setModePullFromEnd()
	{
		mPullView.setMode(Mode.PULL_FROM_END);
	}

	@Override
	public void setModeBoth()
	{
		mPullView.setMode(Mode.BOTH);
	}

	@Override
	public void setModeDisabled()
	{
		mPullView.setMode(Mode.DISABLED);
	}

	@Override
	public void onRefreshComplete()
	{
		mPullView.onRefreshComplete();
	}

	@Override
	public void onPullDownToRefresh(PullToRefreshListView view)
	{

	}

	@Override
	public void onPullUpToRefresh(PullToRefreshListView view)
	{

	}
}
