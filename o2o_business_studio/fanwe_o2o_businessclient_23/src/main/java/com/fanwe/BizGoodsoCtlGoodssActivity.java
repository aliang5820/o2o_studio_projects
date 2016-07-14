package com.fanwe;

import java.util.ArrayList;
import java.util.List;

import android.app.Activity;
import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ListView;
import android.widget.TextView;

import com.fanwe.adapter.BizGoodsoCtlGoodssAdapter;
import com.fanwe.businessclient.R;
import com.fanwe.constant.Constant.ExtraConstant;
import com.fanwe.http.InterfaceServer;
import com.fanwe.http.listener.SDRequestCallBack;
import com.fanwe.model.BizGoodsoCtlGoodssActModel;
import com.fanwe.model.ItemBizGoodsoActGoodss;
import com.fanwe.model.RequestModel;
import com.fanwe.utils.SDCollectionUtil;
import com.fanwe.utils.SDDialogUtil;
import com.fanwe.utils.SDInterfaceUtil;
import com.fanwe.utils.SDToast;
import com.handmark.pulltorefresh.library.PullToRefreshBase;
import com.handmark.pulltorefresh.library.PullToRefreshBase.Mode;
import com.handmark.pulltorefresh.library.PullToRefreshBase.OnRefreshListener2;
import com.handmark.pulltorefresh.library.PullToRefreshListView;

/**
 * 
 * 商品订单列表
 */
public class BizGoodsoCtlGoodssActivity extends TitleBaseActivity
{
	private PullToRefreshListView mPtrlist;

	private TextView mTvError;

	private List<ItemBizGoodsoActGoodss> mListModel;
	private BizGoodsoCtlGoodssAdapter mAdapter;

	private int mCurrentPage = 1;
	private int mTotalPage = 0;

	private String mData_id;

	@Override
	protected void onCreate(Bundle arg0)
	{
		super.onCreate(arg0);
		setContentView(R.layout.act_bizgoodsoctl_goodssact);
		init();
	}

	private void init()
	{
		register();
		initTitle();
		initIntent();
		bindDefaultData();
		initPullView();
	}

	private void initIntent()
	{
		mData_id = getIntent().getExtras().getString(ExtraConstant.EXTRA_ID);
	}

	public void refreshData()
	{
		mCurrentPage = 1;
		requestBizEventoCtlEventsAct(false);
	}

	private void loadMoreData()
	{
		if (mListModel != null && mListModel.size() > 0)
		{
			mCurrentPage++;
			if (mCurrentPage > mTotalPage && mTotalPage != 0)
			{
				SDToast.showToast("没有更多数据了!");
				mPtrlist.onRefreshComplete();
			} else
			{
				requestBizEventoCtlEventsAct(true);
			}
		} else
		{
			refreshData();
		}
	}

	private void initPullView()
	{
		// TODO Auto-generated method stub
		mPtrlist.setMode(Mode.BOTH);
		mPtrlist.setOnRefreshListener(new OnRefreshListener2<ListView>()
		{
			@Override
			public void onPullDownToRefresh(PullToRefreshBase<ListView> refreshView)
			{
				refreshData();
			}

			@Override
			public void onPullUpToRefresh(PullToRefreshBase<ListView> refreshView)
			{
				loadMoreData();
			}
		});
		mPtrlist.setRefreshing();
	}

	protected void requestBizEventoCtlEventsAct(final boolean isLoadMore)
	{
		RequestModel model = new RequestModel();
		model.putCtlAct("biz_goodso", "goodss");
		model.put("page", mCurrentPage);
		model.put("data_id", mData_id);

		SDRequestCallBack<BizGoodsoCtlGoodssActModel> handler = new SDRequestCallBack<BizGoodsoCtlGoodssActModel>()
		{
			private Dialog nDialog;

			@Override
			public void onStart()
			{
				nDialog = SDDialogUtil.showLoading("加载中...");
			}

			@Override
			public void onFinish()
			{
				if (nDialog != null)
				{
					nDialog.dismiss();
				}
				mPtrlist.onRefreshComplete();
				toggleEmptyMsg();
			}

			@Override
			public void onSuccess(BizGoodsoCtlGoodssActModel actModel)
			{
				if (!SDInterfaceUtil.dealactModel(actModel, BizGoodsoCtlGoodssActivity.this))
				{
					if (actModel.getPage() != null)
					{
						mCurrentPage = actModel.getPage().getPage();
						mTotalPage = actModel.getPage().getPage_total();
					}
					if (actModel.getItem() != null && actModel.getItem().size() > 0)
					{
						if (!isLoadMore)
						{
							mListModel.clear();
						}
						mListModel.addAll(actModel.getItem());
						mAdapter.setmNow_time(actModel.getNow_time());
						mAdapter.setmOrder_delivery_expire(actModel.getOrder_delivery_expire());
						mAdapter.updateData(mListModel);
					} else
					{
						SDToast.showToast("未找到数据!");
					}
				}
			}
		};

		InterfaceServer.getInstance().requestInterface(model, handler);

	}

	protected void toggleEmptyMsg()
	{
		if (SDCollectionUtil.isListHasData(mListModel))
		{
			mTvError.setVisibility(View.GONE);
		} else
		{
			mTvError.setVisibility(View.VISIBLE);
		}
	}

	private void bindDefaultData()
	{
		mListModel = new ArrayList<ItemBizGoodsoActGoodss>();
		mAdapter = new BizGoodsoCtlGoodssAdapter(mListModel, this);
		mPtrlist.setAdapter(mAdapter);
	}

	private void register()
	{
		mPtrlist = (PullToRefreshListView) findViewById(R.id.ptrlist);
		mTvError = (TextView) findViewById(R.id.tv_error);
	}

	private void initTitle()
	{
		mTitle.setText("商品订单列表");
	}

	@Override
	protected void onActivityResult(int arg0, int arg1, Intent arg2)
	{
		super.onActivityResult(arg0, arg1, arg2);
		if (arg1 == Activity.RESULT_OK)
		{
			refreshData();
		}
	}

}
