package com.fanwe;

import java.util.ArrayList;
import java.util.List;

import android.os.Bundle;
import android.widget.ListView;

import com.fanwe.adapter.WithdrawLogAdapter;
import com.fanwe.constant.Constant.TitleType;
import com.fanwe.http.InterfaceServer;
import com.fanwe.http.SDRequestCallBack;
import com.fanwe.library.utils.SDToast;
import com.fanwe.library.utils.SDViewUtil;
import com.fanwe.model.PageModel;
import com.fanwe.model.RequestModel;
import com.fanwe.model.Uc_money_withdraw_logActModel;
import com.fanwe.model.WithdrawLogModel;
import com.fanwe.o2o.newo2o.R;
import com.handmark.pulltorefresh.library.PullToRefreshBase;
import com.handmark.pulltorefresh.library.PullToRefreshBase.Mode;
import com.handmark.pulltorefresh.library.PullToRefreshBase.OnRefreshListener2;
import com.handmark.pulltorefresh.library.PullToRefreshListView;
import com.lidroid.xutils.http.ResponseInfo;
import com.lidroid.xutils.view.annotation.ViewInject;

/**
 * 提现明细(日志)
 * 
 * @author Administrator
 * 
 */
public class WithdrawLogActivity extends BaseActivity
{

	@ViewInject(R.id.ptrlv_content)
	private PullToRefreshListView mPtrlv_content;

	private List<WithdrawLogModel> mListModel = new ArrayList<WithdrawLogModel>();
	private WithdrawLogAdapter mAdapter;

	private PageModel mPage = new PageModel();

	@Override
	protected void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
		setmTitleType(TitleType.TITLE);
		setContentView(R.layout.act_withdraw_log);
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
		mAdapter = new WithdrawLogAdapter(mListModel, this);
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
					SDToast.showToast("没有更多数据了");
					mPtrlv_content.onRefreshComplete();
				}
			}
		});
	}

	@Override
	protected void onResume()
	{
		requestData(false);
		super.onResume();
	}

	private void requestData(final boolean isLoadMore)
	{
		RequestModel model = new RequestModel();
		model.putCtl("uc_money");
		model.putAct("withdraw_log");
		model.putPage(mPage);
		InterfaceServer.getInstance().requestInterface(model, new SDRequestCallBack<Uc_money_withdraw_logActModel>()
		{
			@Override
			public void onStart()
			{
				super.onStart();
			}

			@Override
			public void onSuccess(ResponseInfo<String> responseInfo)
			{
				if (actModel.getStatus() > 0)
				{
					mPage.update(actModel.getPage());
					SDViewUtil.updateAdapterByList(mListModel, actModel.getData(), mAdapter, isLoadMore);
				}
				super.onSuccess(responseInfo);
			}

			@Override
			public void onFinish()
			{
				mPtrlv_content.onRefreshComplete();
				super.onFinish();
			}
		});
	}

	private void initTitle()
	{
		mTitle.setMiddleTextTop("提现明细");
	}

}
