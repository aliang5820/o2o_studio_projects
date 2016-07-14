package com.fanwe.fragment;

import java.util.ArrayList;
import java.util.List;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ListView;

import com.fanwe.adapter.MyLotteryAdapter;
import com.fanwe.constant.Constant.TitleType;
import com.fanwe.http.InterfaceServer;
import com.fanwe.http.SDRequestCallBack;
import com.fanwe.library.dialog.SDDialogManager;
import com.fanwe.library.utils.SDToast;
import com.fanwe.library.utils.SDViewUtil;
import com.fanwe.model.PageModel;
import com.fanwe.model.RequestModel;
import com.fanwe.model.Uc_lotteryActItemModel;
import com.fanwe.model.Uc_lottery_indexActModel;
import com.fanwe.o2o.newo2o.R;
import com.handmark.pulltorefresh.library.PullToRefreshBase;
import com.handmark.pulltorefresh.library.PullToRefreshBase.Mode;
import com.handmark.pulltorefresh.library.PullToRefreshBase.OnRefreshListener2;
import com.handmark.pulltorefresh.library.PullToRefreshListView;
import com.lidroid.xutils.http.ResponseInfo;
import com.lidroid.xutils.view.annotation.ViewInject;

/**
 * 我的抽奖
 * 
 * @author js02
 * 
 */
public class MyLotteryFragment extends BaseFragment
{

	@ViewInject(R.id.ptrlv_content)
	private PullToRefreshListView mPtrlvContent;

	@ViewInject(R.id.iv_empty)
	private ImageView mIv_empty;

	private List<Uc_lotteryActItemModel> mListModel = new ArrayList<Uc_lotteryActItemModel>();
	private MyLotteryAdapter mAdapter;

	private PageModel mPage = new PageModel();

	@Override
	protected View onCreateContentView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState)
	{
		setmTitleType(TitleType.TITLE);
		return setContentView(R.layout.frag_my_lottery);
	}

	@Override
	protected void init()
	{
		initTitle();
		bindDefaultData();
		initPullListView();
	}

	private void initTitle()
	{
		mTitle.setMiddleTextTop("我的抽奖");
	}

	private void bindDefaultData()
	{
		mAdapter = new MyLotteryAdapter(mListModel, getActivity());
		mPtrlvContent.setAdapter(mAdapter);
	}

	protected void requestData(final boolean isLoadMore)
	{
		RequestModel model = new RequestModel();
		model.putCtl("uc_lottery");
		model.putUser();
		model.putPage(mPage.getPage());
		SDRequestCallBack<Uc_lottery_indexActModel> handler = new SDRequestCallBack<Uc_lottery_indexActModel>()
		{

			@Override
			public void onSuccess(ResponseInfo<String> responseInfo)
			{
				if (actModel.getStatus() == 1)
				{
					mPage.update(actModel.getPage());
					SDViewUtil.updateAdapterByList(mListModel, actModel.getItem(), mAdapter, isLoadMore);
				}
			}

			@Override
			public void onFinish()
			{
				SDDialogManager.dismissProgressDialog();
				mPtrlvContent.onRefreshComplete();
				SDViewUtil.toggleEmptyMsgByList(mListModel, mIv_empty);
			}
		};

		InterfaceServer.getInstance().requestInterface(model, handler);

	}

	private void initPullListView()
	{
		mPtrlvContent.setMode(Mode.BOTH);
		mPtrlvContent.setOnRefreshListener(new OnRefreshListener2<ListView>()
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
				if (!mPage.increment())
				{
					SDToast.showToast("没有更多数据了");
					mPtrlvContent.onRefreshComplete();
				} else
				{
					requestData(true);
				}
			}
		});
		mPtrlvContent.setRefreshing();
	}

}