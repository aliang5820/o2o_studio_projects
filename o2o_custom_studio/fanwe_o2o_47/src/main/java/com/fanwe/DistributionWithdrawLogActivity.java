package com.fanwe;

import java.util.ArrayList;
import java.util.List;

import android.os.Bundle;
import android.widget.ListView;

import com.fanwe.adapter.DistributionWithdrawLogAdapter;
import com.fanwe.common.CommonInterface;
import com.fanwe.constant.Constant.TitleType;
import com.fanwe.http.SDRequestCallBack;
import com.fanwe.library.utils.SDToast;
import com.fanwe.library.utils.SDViewUtil;
import com.fanwe.model.DistributionWithdrawLogModel;
import com.fanwe.model.PageModel;
import com.fanwe.model.Uc_fxwithdraw_indexActModel;
import com.fanwe.o2o.newo2o.R;
import com.handmark.pulltorefresh.library.PullToRefreshBase;
import com.handmark.pulltorefresh.library.PullToRefreshBase.Mode;
import com.handmark.pulltorefresh.library.PullToRefreshBase.OnRefreshListener2;
import com.handmark.pulltorefresh.library.PullToRefreshListView;
import com.lidroid.xutils.exception.HttpException;
import com.lidroid.xutils.http.ResponseInfo;
import com.lidroid.xutils.view.annotation.ViewInject;

/**
 * 分销提现日志
 * 
 * @author Administrator
 * 
 */
public class DistributionWithdrawLogActivity extends BaseActivity
{

	@ViewInject(R.id.ptrlv_content)
	private PullToRefreshListView mPtrlv_content;

	private List<DistributionWithdrawLogModel> mListModel = new ArrayList<DistributionWithdrawLogModel>();
	private DistributionWithdrawLogAdapter mAdapter;

	private PageModel mPage = new PageModel();

	@Override
	protected void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
		setmTitleType(TitleType.TITLE);
		setContentView(R.layout.act_distribution_withdraw_log);
		init();
	}

	private void init()
	{
		initTitle();
		bindDefaultData();
		initPullToRefreshListView();
	}

	private void bindDefaultData()
	{
		mAdapter = new DistributionWithdrawLogAdapter(mListModel, this);
		mPtrlv_content.setAdapter(mAdapter);
	}

	private void initTitle()
	{
		mTitle.setMiddleTextTop("分销提现日志");
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

	private void requestData(final boolean isLoadMore)
	{
		CommonInterface.requestDistributionWithdrawLog(mPage.getPage(), new SDRequestCallBack<Uc_fxwithdraw_indexActModel>()
		{

			@Override
			public void onSuccess(ResponseInfo<String> responseInfo)
			{
				if (actModel.getStatus() == 1)
				{
					mPage.update(actModel.getPage());
					SDViewUtil.updateAdapterByList(mListModel, actModel.getList(), mAdapter, isLoadMore);
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

}
