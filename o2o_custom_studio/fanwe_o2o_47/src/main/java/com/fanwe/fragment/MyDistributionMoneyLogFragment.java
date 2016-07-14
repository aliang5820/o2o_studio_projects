package com.fanwe.fragment;

import java.util.ArrayList;
import java.util.List;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.TextView;

import com.fanwe.adapter.MyDistributionMoneyLogAdapter;
import com.fanwe.common.CommonInterface;
import com.fanwe.http.SDRequestCallBack;
import com.fanwe.library.utils.SDToast;
import com.fanwe.library.utils.SDViewBinder;
import com.fanwe.library.utils.SDViewUtil;
import com.fanwe.model.DistributionMoneyLogModel;
import com.fanwe.model.PageModel;
import com.fanwe.model.Uc_fxinvite_money_logActModel;
import com.fanwe.o2o.newo2o.R;
import com.handmark.pulltorefresh.library.PullToRefreshBase;
import com.handmark.pulltorefresh.library.PullToRefreshBase.Mode;
import com.handmark.pulltorefresh.library.PullToRefreshBase.OnRefreshListener2;
import com.handmark.pulltorefresh.library.PullToRefreshListView;
import com.lidroid.xutils.exception.HttpException;
import com.lidroid.xutils.http.ResponseInfo;
import com.lidroid.xutils.view.annotation.ViewInject;

/**
 * 我的分销资金日志
 * 
 * @author Administrator
 * 
 */
public class MyDistributionMoneyLogFragment extends BaseFragment
{

	@ViewInject(R.id.ptrlv_content)
	private PullToRefreshListView mPtrlv_content;

	@ViewInject(R.id.tv_distribution_earn_money)
	private TextView mTv_distribution_earn_money;

	@ViewInject(R.id.tv_my_recommended)
	private TextView mTv_my_recommended;

	private PageModel mPage = new PageModel();

	private MyDistributionMoneyLogAdapter mAdapter;
	private List<DistributionMoneyLogModel> mlistModel = new ArrayList<DistributionMoneyLogModel>();

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
		mAdapter = new MyDistributionMoneyLogAdapter(mlistModel, getActivity());
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
		CommonInterface.requestDistributionMoneyLog(mPage.getPage(), new SDRequestCallBack<Uc_fxinvite_money_logActModel>()
		{

			@Override
			public void onSuccess(ResponseInfo<String> responseInfo)
			{
				if (actModel.getStatus() == 1)
				{
					mPage.update(actModel.getPage());
					bindData(actModel);
					SDViewUtil.updateAdapterByList(mlistModel, actModel.getList(), mAdapter, isLoadMore);
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

	protected void bindData(Uc_fxinvite_money_logActModel actModel)
	{
		SDViewBinder.setTextView(mTv_distribution_earn_money, actModel.getFxmoney());
		SDViewBinder.setTextView(mTv_my_recommended, actModel.getPname());
	}

}
