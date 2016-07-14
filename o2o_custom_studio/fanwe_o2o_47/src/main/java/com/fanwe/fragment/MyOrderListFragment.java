package com.fanwe.fragment;

import java.util.ArrayList;
import java.util.List;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import com.fanwe.MyOrderListActivity;
import com.fanwe.adapter.MyOrderListAdapter;
import com.fanwe.constant.Constant.TitleType;
import com.fanwe.event.EnumEventTag;
import com.fanwe.http.InterfaceServer;
import com.fanwe.http.SDRequestCallBack;
import com.fanwe.library.dialog.SDDialogManager;
import com.fanwe.library.utils.SDToast;
import com.fanwe.library.utils.SDViewUtil;
import com.fanwe.model.PageModel;
import com.fanwe.model.RequestModel;
import com.fanwe.model.Uc_orderModel;
import com.fanwe.model.Uc_order_indexActModel;
import com.fanwe.o2o.newo2o.R;
import com.handmark.pulltorefresh.library.PullToRefreshBase;
import com.handmark.pulltorefresh.library.PullToRefreshBase.Mode;
import com.handmark.pulltorefresh.library.PullToRefreshBase.OnRefreshListener2;
import com.handmark.pulltorefresh.library.PullToRefreshListView;
import com.lidroid.xutils.exception.HttpException;
import com.lidroid.xutils.http.ResponseInfo;
import com.lidroid.xutils.view.annotation.ViewInject;
import com.sunday.eventbus.SDBaseEvent;

/**
 * 我的订单fragment
 * 
 * @author js02
 * 
 */
public class MyOrderListFragment extends BaseFragment
{

	@ViewInject(R.id.ptrlv_content)
	private PullToRefreshListView mPtrlv_content;

	@ViewInject(R.id.ll_empty)
	private View mLl_empty;

	private MyOrderListAdapter mAdapter;
	private List<Uc_orderModel> mListModel = new ArrayList<Uc_orderModel>();

	private PageModel mPage = new PageModel();

	private int mPayStatus = 0;

	@Override
	protected View onCreateContentView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState)
	{
		setmTitleType(TitleType.TITLE);
		return setContentView(R.layout.frag_my_order_list);
	}

	@Override
	protected void init()
	{
		getIntentData();
		initTitle();
		initPullToRefreshListView();
		bindDefaultData();
	}

	private void initTitle()
	{
		String title = "我的订单";
		switch (mPayStatus)
		{
		case 0:
			title = "待付款订单";
			break;
		case 1:
			title = "已付款订单";
			break;

		default:
			break;
		}
		mTitle.setMiddleTextTop(title);
	}

	private void getIntentData()
	{
		mPayStatus = getActivity().getIntent().getIntExtra(MyOrderListActivity.EXTRA_PAY_STATUS, 0);
	}

	private void bindDefaultData()
	{
		mAdapter = new MyOrderListAdapter(mListModel, getActivity());
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
				refreshData();
			}

			@Override
			public void onPullUpToRefresh(PullToRefreshBase<ListView> refreshView)
			{
				if (!mPage.increment())
				{
					SDToast.showToast("没有更多数据了");
					mPtrlv_content.onRefreshComplete();
				} else
				{
					requestData(true);
				}
			}
		});
		mPtrlv_content.setRefreshing();
	}

	public void refreshData()
	{
		mPage.resetPage();
		requestData(false);
	}

	protected void requestData(final boolean isLoadMore)
	{
		RequestModel model = new RequestModel();
		model.putCtl("uc_order");
		model.put("pay_status", mPayStatus);
		model.putUser();
		model.putPage(mPage.getPage());
		SDRequestCallBack<Uc_order_indexActModel> handler = new SDRequestCallBack<Uc_order_indexActModel>()
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
			public void onFailure(HttpException error, String msg)
			{

			}

			@Override
			public void onFinish()
			{
				SDDialogManager.dismissProgressDialog();
				mPtrlv_content.onRefreshComplete();
				SDViewUtil.toggleEmptyMsgByList(mListModel, mLl_empty);
			}
		};

		InterfaceServer.getInstance().requestInterface(model, handler);
	}

	@Override
	public void onEventMainThread(SDBaseEvent event)
	{
		super.onEventMainThread(event);
		switch (EnumEventTag.valueOf(event.getTagInt()))
		{
		case COMMENT_SUCCESS:
			refreshData();
			break;
		case PAY_ORDER_SUCCESS:
			refreshData();
			break;
		case REFRESH_ORDER_LIST:
			refreshData();
			break;

		default:
			break;
		}
	}

}