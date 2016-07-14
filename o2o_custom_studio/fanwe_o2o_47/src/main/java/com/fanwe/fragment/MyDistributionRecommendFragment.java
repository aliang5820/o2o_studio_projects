package com.fanwe.fragment;

import java.util.ArrayList;
import java.util.List;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.TextView;

import com.fanwe.CheckDistributionRecommendActivity;
import com.fanwe.adapter.DistributionRecommendAdapter;
import com.fanwe.adapter.DistributionRecommendAdapter.DistributionRecommendAdapterListener;
import com.fanwe.common.CommonInterface;
import com.fanwe.http.SDRequestCallBack;
import com.fanwe.library.utils.SDToast;
import com.fanwe.library.utils.SDViewBinder;
import com.fanwe.library.utils.SDViewUtil;
import com.fanwe.model.DistributionRecommendModel;
import com.fanwe.model.PageModel;
import com.fanwe.model.Uc_fxinvite_indexActModel;
import com.fanwe.o2o.newo2o.R;
import com.handmark.pulltorefresh.library.PullToRefreshBase;
import com.handmark.pulltorefresh.library.PullToRefreshBase.Mode;
import com.handmark.pulltorefresh.library.PullToRefreshBase.OnRefreshListener2;
import com.handmark.pulltorefresh.library.PullToRefreshListView;
import com.lidroid.xutils.exception.HttpException;
import com.lidroid.xutils.http.ResponseInfo;
import com.lidroid.xutils.view.annotation.ViewInject;

/**
 * 我的分销推荐
 * 
 * @author Administrator
 * 
 */
public class MyDistributionRecommendFragment extends BaseFragment
{

	@ViewInject(R.id.ptrlv_content)
	private PullToRefreshListView mPtrlv_content;

	@ViewInject(R.id.tv_distribution_earn_money)
	private TextView mTv_distribution_earn_money;

	@ViewInject(R.id.tv_my_recommended)
	private TextView mTv_my_recommended;

	private DistributionRecommendAdapter mAdapter;
	private List<DistributionRecommendModel> mListModel = new ArrayList<DistributionRecommendModel>();

	private PageModel mPage = new PageModel();

	@Override
	protected View onCreateContentView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState)
	{
		return setContentView(R.layout.frag_my_distribution_recommend);
	}

	@Override
	protected void init()
	{
		bindDefaultData();
		initPullToRefreshListView();
	}

	private void bindDefaultData()
	{
		mAdapter = new DistributionRecommendAdapter(mListModel, true, getActivity());
		mAdapter.setListener(new DistributionRecommendAdapterListener()
		{

			@Override
			public void onCheckRecommend(DistributionRecommendModel model)
			{
				// TODO 跳到查看推荐人界面

				int id = model.getId();
				String userName = model.getUser_name();

				Intent intent = new Intent(getActivity(), CheckDistributionRecommendActivity.class);
				intent.putExtra(CheckDistributionRecommendActivity.EXTRA_ID, id);
				intent.putExtra(CheckDistributionRecommendActivity.EXTRA_USER_NAME, userName);
				startActivity(intent);
			}
		});
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
				mPage.resetPage();
				requestData(false);
			}

			@Override
			public void onPullUpToRefresh(PullToRefreshBase<ListView> refreshView)
			{
				if (mPage.increment())
				{
					requestData(true);
				} else
				{
					SDToast.showToast("未找到更多数据");
					mPtrlv_content.onRefreshComplete();
				}
			}
		});
		mPtrlv_content.setRefreshing();
	}

	protected void requestData(final boolean isLoadMore)
	{
		CommonInterface.requestDistributionRecommend(mPage.getPage(), -1, new SDRequestCallBack<Uc_fxinvite_indexActModel>()
		{

			@Override
			public void onSuccess(ResponseInfo<String> responseInfo)
			{
				if (actModel.getStatus() == 1)
				{
					mPage.update(actModel.getPage());
					SDViewUtil.updateAdapterByList(mListModel, actModel.getList(), mAdapter, isLoadMore);
					bindData(actModel);
				}
			}

			@Override
			public void onStart()
			{

			}

			@Override
			public void onFinish()
			{
				mPtrlv_content.onRefreshComplete();
			}

			@Override
			public void onFailure(HttpException error, String msg)
			{

			}
		});
	}

	protected void bindData(Uc_fxinvite_indexActModel model)
	{
		SDViewBinder.setTextView(mTv_distribution_earn_money, model.getFxmoney());
		SDViewBinder.setTextView(mTv_my_recommended, model.getPname());
	}

}
