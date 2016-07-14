package com.fanwe.fragment;

import java.util.ArrayList;
import java.util.List;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import com.fanwe.adapter.FriendCircleAdapter;
import com.fanwe.library.utils.SDViewUtil;
import com.fanwe.model.DynamicModel;
import com.fanwe.model.PageModel;
import com.fanwe.o2o.newo2o.R;
import com.handmark.pulltorefresh.library.PullToRefreshBase;
import com.handmark.pulltorefresh.library.PullToRefreshBase.Mode;
import com.handmark.pulltorefresh.library.PullToRefreshBase.OnRefreshListener2;
import com.handmark.pulltorefresh.library.PullToRefreshListView;

public class DynamicFragment extends BaseFragment
{

	protected PullToRefreshListView mPtrlv_content;

	/** 该动态列表主人id (可以不设置) */
	protected int mUserId;
	protected FriendCircleAdapter mAdapter;
	protected List<DynamicModel> mListModel = new ArrayList<DynamicModel>();
	protected PageModel mPage = new PageModel();
	protected DynamicFragmentListener mListener;

	public void setmListener(DynamicFragmentListener mListener)
	{
		this.mListener = mListener;
	}

	/**
	 * 该动态列表主人id (可以不设置)
	 * 
	 * @param mId
	 */
	public void setUserId(int userId)
	{
		this.mUserId = userId;
	}

	public void setListModel(List<DynamicModel> listModel, boolean isLoadMore)
	{
		SDViewUtil.updateAdapterByList(mListModel, listModel, mAdapter, isLoadMore);
	}

	@Override
	protected View onCreateContentView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState)
	{
		return setContentView(R.layout.frag_dynamic);
	}

	@Override
	protected void init()
	{
		findViews();
		bindDefaultData();
		initPullToRefreshListView();
	}

	private void findViews()
	{
		mPtrlv_content = (PullToRefreshListView) findViewById(R.id.ptrlv_content);
	}

	private void bindDefaultData()
	{
		mAdapter = new FriendCircleAdapter(mListModel, getActivity());
		mAdapter.setUserId(mUserId);
		mPtrlv_content.setAdapter(mAdapter);
	}

	private void initPullToRefreshListView()
	{
		mPtrlv_content.setMode(Mode.BOTH);
		mPtrlv_content.setOnRefreshListener(new OnRefreshListener2<ListView>()
		{

			@Override
			public void onPullDownToRefresh(PullToRefreshBase<ListView> refreshView)
			{
				DynamicFragment.this.onPullDownToRefresh(refreshView);
			}

			@Override
			public void onPullUpToRefresh(PullToRefreshBase<ListView> refreshView)
			{
				DynamicFragment.this.onPullUpToRefresh(refreshView);
			}
		});
	}

	protected void onPullDownToRefresh(PullToRefreshBase<ListView> refreshView)
	{
		if (mListener != null)
		{
			mListener.onPullDownToRefresh(refreshView);
		}
	}

	protected void onPullUpToRefresh(PullToRefreshBase<ListView> refreshView)
	{
		if (mListener != null)
		{
			mListener.onPullUpToRefresh(refreshView);
		}
	}

	public void setRefreshing()
	{
		if (mPtrlv_content != null)
		{
			mPtrlv_content.setRefreshing();
		}
	}

	public void onRefreshComplete()
	{
		if (mPtrlv_content != null)
		{
			mPtrlv_content.onRefreshComplete();
		}
	}

	public PullToRefreshListView getPullToRefreshListView()
	{
		return mPtrlv_content;
	}

	public interface DynamicFragmentListener
	{
		public void onPullDownToRefresh(PullToRefreshBase<ListView> refreshView);

		public void onPullUpToRefresh(PullToRefreshBase<ListView> refreshView);
	}

	@Override
	public void onDestroy()
	{
		if (mAdapter != null)
		{
			mAdapter.destroy();
		}
		super.onDestroy();
	}

}
