package com.fanwe;

import java.util.ArrayList;
import java.util.List;

import android.app.Dialog;
import android.os.Bundle;
import android.view.View;
import android.widget.ListView;
import android.widget.TextView;

import com.fanwe.adapter.Xftj_groupAdapter;
import com.fanwe.businessclient.R;
import com.fanwe.constant.Constant.ExtraConstant;
import com.fanwe.http.InterfaceServer;
import com.fanwe.http.listener.SDRequestCallBack;
import com.fanwe.library.utils.SDViewBinder;
import com.fanwe.model.Biz_dealoCtl_DealsActModel;
import com.fanwe.model.RequestModel;
import com.fanwe.model.Xftj_groupItemModel;
import com.fanwe.model.Xftj_groupModel;
import com.fanwe.model.Xftj_store_itemModel;
import com.fanwe.utils.SDCollectionUtil;
import com.fanwe.utils.SDInterfaceUtil;
import com.fanwe.utils.SDToast;
import com.handmark.pulltorefresh.library.PullToRefreshBase;
import com.handmark.pulltorefresh.library.PullToRefreshBase.Mode;
import com.handmark.pulltorefresh.library.PullToRefreshBase.OnRefreshListener2;
import com.handmark.pulltorefresh.library.PullToRefreshListView;

/***
 * 
 * 优惠劵门店消费列表
 *
 */
public class Jygl_couponConsumptionActivity extends TitleBaseActivity
{

	private PullToRefreshListView mPtrList;
	TextView mTv_error;
	TextView mTv_name;
	TextView mTv_buy_count;
	TextView mTv_f_end_time;

	private int mCurrentPage = 1;
	private int mTotalPage = 0;
	private String mPage_title;
	private String mGroupItem_id;
	private String mStore_id;
	private List<Xftj_groupItemModel> mListModel;
	private Xftj_groupAdapter mAdapter;

	@Override
	protected void onCreate(Bundle arg0)
	{
		super.onCreate(arg0);
		setContentView(R.layout.act_xftj__group);
		init();
	}

	private void init()
	{
		register();
		initIntent();
		bindDefaultData();
		initPullView();

	}

	private void initPullView()
	{
		mPtrList.setMode(Mode.BOTH);
		mPtrList.setOnRefreshListener(new OnRefreshListener2<ListView>()
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
		mPtrList.setRefreshing();
	}

	private void loadMoreData()
	{
		if (mListModel != null && mListModel.size() > 0)
		{
			mCurrentPage++;
			if (mCurrentPage > mTotalPage && mTotalPage != 0)
			{
				SDToast.showToast("没有更多数据了!");
				mPtrList.onRefreshComplete();
			} else
			{
				requestBizEventoCtlEventsAct(true);
			}
		} else
		{
			refreshData();
		}
	}

	private void refreshData()
	{
		mCurrentPage = 1;
		requestBizEventoCtlEventsAct(false);
	}

	protected void requestBizEventoCtlEventsAct(final boolean isLoadMore)
	{
		RequestModel model = new RequestModel();
		model.putCtlAct("biz_dealo", "deals");
		model.put("deal_id", mGroupItem_id);
		model.put("location_id", mStore_id);
		model.put("page", mCurrentPage);
		model.put("page_title", mPage_title);

		SDRequestCallBack<Biz_dealoCtl_DealsActModel> handler = new SDRequestCallBack<Biz_dealoCtl_DealsActModel>()
		{
			private Dialog nDialog;

			@Override
			public void onFinish()
			{
				if (nDialog != null)
				{
					nDialog.dismiss();
				}
				mPtrList.onRefreshComplete();
				toggleEmptyMsg();
			}

			@Override
			public void onSuccess(Biz_dealoCtl_DealsActModel actModel)
			{
				if (!SDInterfaceUtil.dealactModel(actModel, Jygl_couponConsumptionActivity.this))
				{
					if (actModel.getDeal_info() != null)
					{
						addEventInfo(actModel.getDeal_info());
					}

					if (actModel.getPage() != null)
					{
						mCurrentPage = actModel.getPage().getPage();
						mTotalPage = actModel.getPage().getPage_total();
					}
					if (actModel.getDeals() != null && actModel.getDeals().size() > 0)
					{
						if (!isLoadMore)
						{
							mListModel.clear();
						}
						mListModel.addAll(actModel.getDeals());
						mAdapter.updateData(mListModel);
					} else
					{
						SDToast.showToast("未找到数据!");
					}
				}
				mTitle.setText("" + actModel.getPage_title());

			}
		};

		InterfaceServer.getInstance().requestInterface(model, handler);

	}

	private void addEventInfo(Xftj_groupModel deal_info)
	{
		SDViewBinder.setTextView(mTv_name, deal_info.getName());
		SDViewBinder.setTextView(mTv_buy_count, "本店消费:" + deal_info.getBuy_count());
		SDViewBinder.setTextView(mTv_f_end_time, "有效期:" + deal_info.getF_end_time());
	}

	private void bindDefaultData()
	{
		mListModel = new ArrayList<Xftj_groupItemModel>();
		mAdapter = new Xftj_groupAdapter(mListModel, this);
		mPtrList.setAdapter(mAdapter);

	}

	private void initIntent()
	{
		Xftj_store_itemModel model = (Xftj_store_itemModel) getIntent().getExtras().getSerializable(ExtraConstant.EXTRA_MODEL);
		mStore_id = model.getId();
		mGroupItem_id = getIntent().getExtras().getString(ExtraConstant.EXTRA_ID);
	}

	protected void toggleEmptyMsg()
	{
		if (SDCollectionUtil.isListHasData(mListModel))
		{
			mTv_error.setVisibility(View.GONE);
		} else
		{
			mTv_error.setVisibility(View.VISIBLE);
		}
	}

	private void register()
	{
		mPtrList = (PullToRefreshListView) findViewById(R.id.ptrList);
		mTv_error = (TextView) findViewById(R.id.tv_error);
		mTv_name = (TextView) findViewById(R.id.tv_xftj_group_name);
		mTv_buy_count = (TextView) findViewById(R.id.tv_xftj_group_buy_count);
		mTv_f_end_time = (TextView) findViewById(R.id.tv_xftj_group_f_end_time);

	}

}
