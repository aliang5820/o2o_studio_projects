package com.fanwe.fragment;

import java.util.ArrayList;
import java.util.List;

import android.app.Dialog;
import android.content.Intent;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ListView;
import android.widget.TextView;

import com.fanwe.Jygl_couponStoreActivity;
import com.fanwe.adapter.Xftj_buyAdapter;
import com.fanwe.businessclient.R;
import com.fanwe.constant.Constant.ExtraConstant;
import com.fanwe.http.InterfaceServer;
import com.fanwe.http.listener.SDRequestCallBack;
import com.fanwe.model.RequestModel;
import com.fanwe.model.Xftj_buyModel;
import com.fanwe.model.Xftj_model;
import com.fanwe.utils.SDInterfaceUtil;
import com.fanwe.utils.SDToast;
import com.handmark.pulltorefresh.library.PullToRefreshBase;
import com.handmark.pulltorefresh.library.PullToRefreshBase.Mode;
import com.handmark.pulltorefresh.library.PullToRefreshBase.OnRefreshListener2;
import com.handmark.pulltorefresh.library.PullToRefreshListView;

/**
 * 
 * 团购列表
 */
public class GroupListFragment extends BaseFragment
{

	private PullToRefreshListView mPList;
	TextView mTv_error;

	private List<Xftj_buyModel> mListModel;
	private Xftj_buyAdapter mAdapter;

	private int mTotalPage = 0;
	private int mCurrentPage;

	@Override
	protected int onCreateContentView()
	{
		return R.layout.frag_xftj_buy;
	}

	@Override
	protected void init()
	{
		register(getView());
		initContent();
		initPullView();
	}

	private void initPullView()
	{
		mPList.setMode(Mode.BOTH);
		mPList.setOnRefreshListener(new OnRefreshListener2<ListView>()
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
		mPList.setRefreshing();

	}

	protected void loadMoreData()
	{

		if (mListModel != null && mListModel.size() > 0)
		{
			mCurrentPage++;
			if (mCurrentPage > mTotalPage && mTotalPage != 0)
			{
				SDToast.showToast("没有更多数据了!");
				mPList.onRefreshComplete();
			} else
			{
				requestXftj_buy(true);
			}
		} else
		{
			refreshData(false);
		}

	}

	private void refreshData(boolean isReturnHead)
	{
		mCurrentPage = 1;
		requestXftj_buy(false);
	}

	private void requestXftj_buy(final boolean isLoadMore)
	{

		RequestModel model = new RequestModel();
		model.putCtl("biz_dealo");
		SDRequestCallBack<Xftj_model> handler = new SDRequestCallBack<Xftj_model>()
		{
			private Dialog nDialog;

			@Override
			public void onFinish()
			{
				if (nDialog != null)
				{
					nDialog.dismiss();
				}
				mPList.onRefreshComplete();
			}

			@Override
			public void onSuccess(Xftj_model actModel)
			{
				if (!SDInterfaceUtil.dealactModel(actModel, getActivity()))
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
		};
		InterfaceServer.getInstance().requestInterface(model, handler);

	}

	private void initContent()
	{
		mListModel = new ArrayList<Xftj_buyModel>();
		mAdapter = new Xftj_buyAdapter(mListModel, getActivity());
		mPList.setAdapter(mAdapter);
		mPList.setOnItemClickListener(new OnItemClickListener()
		{

			@Override
			public void onItemClick(AdapterView<?> parent, View view, int position, long id)
			{
				Intent intent = new Intent(getActivity(), Jygl_couponStoreActivity.class);
				intent.putExtra(ExtraConstant.EXTRA_ID, mListModel.get((int) id).getId());
				startActivity(intent);
			}

		});

	}

	private void register(View view)
	{
		mPList = (PullToRefreshListView) view.findViewById(R.id.ptrList);
		mTv_error = (TextView) view.findViewById(R.id.tv_error);
	}

}
