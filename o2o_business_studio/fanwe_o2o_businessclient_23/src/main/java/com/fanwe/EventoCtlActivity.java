package com.fanwe;

import java.util.ArrayList;
import java.util.List;

import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ListView;
import android.widget.TextView;

import com.fanwe.adapter.EventoCtlAdapter;
import com.fanwe.businessclient.R;
import com.fanwe.constant.Constant.ExtraConstant;
import com.fanwe.http.InterfaceServer;
import com.fanwe.http.listener.SDRequestCallBack;
import com.fanwe.model.BizEventoCtlModel;
import com.fanwe.model.ItemBizEventoCtlModel;
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
 * 活动列表
 */
public class EventoCtlActivity extends TitleBaseActivity
{
	private PullToRefreshListView mPtrlist;
	private TextView mTvError;

	private int mCurrentPage = 1;
	private int mTotalPage = 0;

	private List<ItemBizEventoCtlModel> mListModel;
	private EventoCtlAdapter mAdapter;

	@Override
	protected void onCreate(Bundle arg0)
	{
		// TODO Auto-generated method stub
		super.onCreate(arg0);
		setContentView(R.layout.act_eventoctl);
		init();
	}

	private void init()
	{
		initTitle();
		register();
		bindDefaultData();
		initPullView();
	}

	private void refreshData()
	{
		mCurrentPage = 1;
		requestBizEventoCtl(false);
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
				requestBizEventoCtl(true);
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

	protected void requestBizEventoCtl(final boolean isLoadMore)
	{
		RequestModel model = new RequestModel();
		model.putCtl("biz_evento");
		model.put("page", mCurrentPage);

		SDRequestCallBack<BizEventoCtlModel> handler = new SDRequestCallBack<BizEventoCtlModel>()
		{
			private Dialog nDialog;

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
			public void onSuccess(BizEventoCtlModel actModel)
			{
				if (!SDInterfaceUtil.dealactModel(actModel, EventoCtlActivity.this))
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
						mAdapter.updateData(mListModel);
					} else
					{
						SDToast.showToast("未找到数据!");
					}
				}
			}

			@Override
			public void onStart()
			{
				nDialog = SDDialogUtil.showLoading("加载中...");
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
		// TODO Auto-generated method stub
		mListModel = new ArrayList<ItemBizEventoCtlModel>();
		mAdapter = new EventoCtlAdapter(mListModel, this);
		mPtrlist.setAdapter(mAdapter);
		mPtrlist.setOnItemClickListener(new OnItemClickListener()
		{

			@Override
			public void onItemClick(AdapterView<?> parent, View view, int position, long id)
			{
				// TODO Auto-generated method stub
				Intent intent = new Intent(EventoCtlActivity.this, BizEventoCtlEventsActActivity.class);
				intent.putExtra(ExtraConstant.EXTRA_ID, mListModel.get((int) id).getId());
				startActivity(intent);
			}
		});
	}

	private void register()
	{
		mPtrlist = (PullToRefreshListView) findViewById(R.id.ptrlist);
		mTvError = (TextView) findViewById(R.id.tv_error);
	}

	private void initTitle()
	{
		mTitle.setText("活动列表");
	}

}
