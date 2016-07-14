package com.fanwe;

import java.util.ArrayList;
import java.util.List;

import android.app.Dialog;
import android.os.Bundle;
import android.view.View;
import android.widget.ListView;
import android.widget.TextView;

import com.fanwe.adapter.Jygl_coupon_listThirdAdapter;
import com.fanwe.businessclient.R;
import com.fanwe.constant.Constant.ExtraConstant;
import com.fanwe.http.InterfaceServer;
import com.fanwe.http.listener.SDRequestCallBack;
import com.fanwe.library.utils.SDViewBinder;
import com.fanwe.model.Jygl_couponLocationActModel;
import com.fanwe.model.Jygl_couponLocationsModel;
import com.fanwe.model.Jygl_couponYouhuiModel;
import com.fanwe.model.Jygl_couponYouhuisModel;
import com.fanwe.model.RequestModel;
import com.fanwe.utils.SDCollectionUtil;
import com.fanwe.utils.SDDialogUtil;
import com.fanwe.utils.SDInterfaceUtil;
import com.fanwe.utils.SDToast;
import com.handmark.pulltorefresh.library.PullToRefreshBase;
import com.handmark.pulltorefresh.library.PullToRefreshBase.Mode;
import com.handmark.pulltorefresh.library.PullToRefreshBase.OnRefreshListener2;
import com.handmark.pulltorefresh.library.PullToRefreshListView;

/***
 * 
 * 活动报名列表
 *
 */
public class Jygl_couponYouhuisActivity extends TitleBaseActivity
{
	private PullToRefreshListView mList;
	TextView mTv_error;
	TextView mTv_name;
	TextView mTv_use_count;
	TextView mTv_f_end_time;

	private int mCurrentPage = 1;
	private int mTotalPage = 0;
	private String mPage_title;
	private String mYouhui_id;
	private String mLocation_id;
	private List<Jygl_couponYouhuisModel> mListModel;
	private Jygl_coupon_listThirdAdapter mAdapter;

	@Override
	protected void onCreate(Bundle arg0)
	{
		super.onCreate(arg0);
		setContentView(R.layout.act_jygl_coupon_list_youhuis);
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
		mList.setMode(Mode.BOTH);
		mList.setOnRefreshListener(new OnRefreshListener2<ListView>()
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
		mList.setRefreshing();
	}

	private void loadMoreData()
	{
		if (mListModel != null && mListModel.size() > 0)
		{
			mCurrentPage++;
			if (mCurrentPage > mTotalPage && mTotalPage != 0)
			{
				SDToast.showToast("没有更多数据了!");
				mList.onRefreshComplete();
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
		model.putCtlAct("biz_youhuio", "youhuis");
		model.put("youhui_id", mYouhui_id);
		model.put("location_id", mLocation_id);
		model.put("page", mCurrentPage);
		model.put("page_title", mPage_title);

		SDRequestCallBack<Jygl_couponLocationActModel> handler = new SDRequestCallBack<Jygl_couponLocationActModel>()
		{
			private Dialog nDialog;

			@Override
			public void onFinish()
			{
				if (nDialog != null)
				{
					nDialog.dismiss();
				}
				mList.onRefreshComplete();
				toggleEmptyMsg();
			}

			@Override
			public void onSuccess(Jygl_couponLocationActModel actModel)
			{
				if (!SDInterfaceUtil.dealactModel(actModel, Jygl_couponYouhuisActivity.this))
				{
					if (actModel.getYouhui() != null)
					{
						addEventInfo(actModel.getYouhui());
					}

					if (actModel.getPage() != null)
					{
						mCurrentPage = actModel.getPage().getPage();
						mTotalPage = actModel.getPage().getPage_total();
					}
					if (actModel.getYouhuis() != null && actModel.getYouhuis().size() > 0)
					{
						if (!isLoadMore)
						{
							mListModel.clear();
						}
						mListModel.addAll(actModel.getYouhuis());
						mAdapter.updateData(mListModel);
					} else
					{
						SDToast.showToast("未找到数据!");
					}
				}
				mTitle.setText("" + actModel.getPage_title());

			}

			@Override
			public void onStart()
			{
				nDialog = SDDialogUtil.showLoading("加载中...");
			}
		};

		InterfaceServer.getInstance().requestInterface(model, handler);

	}

	private void addEventInfo(Jygl_couponYouhuiModel event_info)
	{
		SDViewBinder.setTextView(mTv_name, event_info.getName());
		SDViewBinder.setTextView(mTv_use_count, "本店消费:" + event_info.getUse_count());
		SDViewBinder.setTextView(mTv_f_end_time, "有效期:" + event_info.getF_end_time());
	}

	private void bindDefaultData()
	{
		mListModel = new ArrayList<Jygl_couponYouhuisModel>();
		mAdapter = new Jygl_coupon_listThirdAdapter(mListModel, this);
		mList.setAdapter(mAdapter);

	}

	private void initIntent()
	{
		Jygl_couponLocationsModel model = (Jygl_couponLocationsModel) getIntent().getExtras().getSerializable(ExtraConstant.EXTRA_MODEL);
		mLocation_id = model.getLocation_id();
		mYouhui_id = getIntent().getExtras().getString(ExtraConstant.EXTRA_ID);
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
		mList = (PullToRefreshListView) findViewById(R.id.jygl_coupon_list_youhuis);
		mTv_error = (TextView) findViewById(R.id.tv_error);
		mTv_name = (TextView) findViewById(R.id.item_jygl_coupon_list_name);
		mTv_use_count = (TextView) findViewById(R.id.item_jygl_coupon_list_use_count);
		mTv_f_end_time = (TextView) findViewById(R.id.item_jygl_coupon_list_f_end_time);

	}
}
