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

import com.fanwe.adapter.Jygl_coupon_listAdapter;
import com.fanwe.businessclient.R;
import com.fanwe.constant.Constant.ExtraConstant;
import com.fanwe.http.InterfaceServer;
import com.fanwe.http.listener.SDRequestCallBack;
import com.fanwe.model.Jygl_couponListModel;
import com.fanwe.model.Jygl_couponModel;
import com.fanwe.model.RequestModel;
import com.fanwe.utils.SDDialogUtil;
import com.fanwe.utils.SDInterfaceUtil;
import com.fanwe.utils.SDToast;
import com.handmark.pulltorefresh.library.PullToRefreshBase;
import com.handmark.pulltorefresh.library.PullToRefreshBase.Mode;
import com.handmark.pulltorefresh.library.PullToRefreshBase.OnRefreshListener2;
import com.handmark.pulltorefresh.library.PullToRefreshListView;

/***
 * 
 * 优惠劵列表
 *
 */
public class Jygl_couponActivity extends TitleBaseActivity
{

	private PullToRefreshListView mList;
	TextView mTv_error;
	private int mCurrentPage = 1;
	private List<Jygl_couponListModel> mListModel;
	private int mTotalPage = 0;
	private Jygl_coupon_listAdapter mAdapter;

	@Override
	protected void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
		setContentView(R.layout.act_jygl_coupon);
		init();
	}

	private void register()
	{
		mList = (PullToRefreshListView) findViewById(R.id.jygl_coupon_list);
		mTv_error = (TextView) findViewById(R.id.tv_error);
	}

	private void init()
	{
		initTitle();
		register();
		initContent();
		initPullView();
	}

	private void initContent()
	{
		mListModel = new ArrayList<Jygl_couponListModel>();
		mAdapter = new Jygl_coupon_listAdapter(mListModel, this);
		mList.setAdapter(mAdapter);
		mList.setOnItemClickListener(new OnItemClickListener()
		{

			@Override
			public void onItemClick(AdapterView<?> parent, View view, int position, long id)
			{
				Intent intent = new Intent(Jygl_couponActivity.this, Jygl_couponActActivity.class);
				intent.putExtra(ExtraConstant.EXTRA_ID, mListModel.get((int) id).getId());
				startActivity(intent);

			}
		});
	}

	private void initPullView()
	{
		mList.setMode(Mode.BOTH);
		mList.setOnRefreshListener(new OnRefreshListener2<ListView>()
		{
			@Override
			public void onPullDownToRefresh(PullToRefreshBase<ListView> refreshView)
			{
				refreshData(false);
			}

			@Override
			public void onPullUpToRefresh(PullToRefreshBase<ListView> refreshView)
			{
				loadMoreData();
			}

		});
		mList.setRefreshing();
	}

	private void refreshData(boolean isReturnHead)
	{
		mCurrentPage = 1;
		requestJygl__coupon_list(false);
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
				requestJygl__coupon_list(true);
			}
		} else
		{
			refreshData(false);
		}

	}

	private void requestJygl__coupon_list(final boolean isLoadMore)
	{

		RequestModel model = new RequestModel();
		model.putCtl("biz_youhuio");
		SDRequestCallBack<Jygl_couponModel> handler = new SDRequestCallBack<Jygl_couponModel>()
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
			}

			@Override
			public void onSuccess(Jygl_couponModel actModel)
			{
				if (!SDInterfaceUtil.dealactModel(actModel, Jygl_couponActivity.this))
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

	private void initTitle()
	{
		mTitle.setText("优惠劵列表");
	}
}
