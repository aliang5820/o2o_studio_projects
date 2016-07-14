package com.fanwe;

import java.util.ArrayList;
import java.util.List;

import android.app.Dialog;
import android.os.Bundle;
import android.view.View;
import android.widget.ListView;
import android.widget.TextView;

import com.fanwe.adapter.BizEventoCtlEventsActAdapter;
import com.fanwe.businessclient.R;
import com.fanwe.constant.Constant.ExtraConstant;
import com.fanwe.http.InterfaceServer;
import com.fanwe.http.listener.SDRequestCallBack;
import com.fanwe.library.utils.SDViewBinder;
import com.fanwe.model.BizEventoCtlEventsActModel;
import com.fanwe.model.EventInfoBizEventoCtlEventsActModel;
import com.fanwe.model.ItemBizEventoCtlEventsActModel;
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
 * 活动报名列表
 */
public class BizEventoCtlEventsActActivity extends TitleBaseActivity
{
	private PullToRefreshListView mPtrlist;

	private TextView mTvError;
	private TextView mTv_name;
	private TextView mTv_submit_count;
	private TextView mTv_f_event_end_time;

	private List<ItemBizEventoCtlEventsActModel> mListModel;
	private BizEventoCtlEventsActAdapter mAdapter;

	private int mCurrentPage = 1;
	private int mTotalPage = 0;

	private String mData_id;

	@Override
	protected void onCreate(Bundle arg0)
	{
		// TODO Auto-generated method stub
		super.onCreate(arg0);
		setContentView(R.layout.act_bizeventoctl_eventsact);
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
		model.putCtlAct("biz_evento", "events");
		model.put("page", mCurrentPage);
		model.put("data_id", mData_id);

		SDRequestCallBack<BizEventoCtlEventsActModel> handler = new SDRequestCallBack<BizEventoCtlEventsActModel>()
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
			public void onSuccess(BizEventoCtlEventsActModel actModel)
			{
				if (!SDInterfaceUtil.dealactModel(actModel, BizEventoCtlEventsActActivity.this))
				{
					if (actModel.getEvent_info() != null)
					{
						addEventInfo(actModel.getEvent_info());
					}

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

	private void addEventInfo(EventInfoBizEventoCtlEventsActModel event_info)
	{
		// TODO Auto-generated method stub
		SDViewBinder.setTextView(mTv_name, event_info.getName());
		SDViewBinder.setTextView(mTv_submit_count, event_info.getSubmit_count());
		SDViewBinder.setTextView(mTv_f_event_end_time, event_info.getF_event_end_time());
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
		// TODO Auto-generated method stub
		mListModel = new ArrayList<ItemBizEventoCtlEventsActModel>();
		mAdapter = new BizEventoCtlEventsActAdapter(mListModel, this);
		mPtrlist.setAdapter(mAdapter);
	}

	private void register()
	{
		mPtrlist = (PullToRefreshListView) findViewById(R.id.ptrlist);
		mTvError = (TextView) findViewById(R.id.tv_error);
		mTv_name = (TextView) findViewById(R.id.tv_name);
		mTv_submit_count = (TextView) findViewById(R.id.tv_submit_count);
		mTv_f_event_end_time = (TextView) findViewById(R.id.tv_f_event_end_time);
	}

	private void initTitle()
	{
		mTitle.setText("活动报名列表");
	}

}
