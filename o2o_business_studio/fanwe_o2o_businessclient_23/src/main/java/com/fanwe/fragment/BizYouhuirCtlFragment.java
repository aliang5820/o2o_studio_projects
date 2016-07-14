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

import com.fanwe.Biz_tuan_msg_Activity;
import com.fanwe.adapter.BizYouhuirCtlAdapter;
import com.fanwe.businessclient.R;
import com.fanwe.http.InterfaceServer;
import com.fanwe.http.listener.SDRequestCallBack;
import com.fanwe.library.dialog.SDDialogManager;
import com.fanwe.model.BizYouhuirCtlIndexActModel;
import com.fanwe.model.BizYouhuirCtlItemModel;
import com.fanwe.model.RequestModel;
import com.fanwe.utils.SDCollectionUtil;
import com.fanwe.utils.SDInterfaceUtil;
import com.fanwe.utils.SDToast;
import com.handmark.pulltorefresh.library.PullToRefreshBase;
import com.handmark.pulltorefresh.library.PullToRefreshBase.Mode;
import com.handmark.pulltorefresh.library.PullToRefreshBase.OnRefreshListener2;
import com.handmark.pulltorefresh.library.PullToRefreshListView;

/**
 * 
 * 优惠劵评分
 */
public class BizYouhuirCtlFragment extends BaseFragment
{
	private PullToRefreshListView mList;
	private TextView mTvError;

	private int mCurrentPage = 1;
	private int mTotalPage = 0;

	private List<BizYouhuirCtlItemModel> mListModel;
	private BizYouhuirCtlAdapter mAdapter;

	@Override
	protected int onCreateContentView()
	{
		return R.layout.base_list_include;
	}

	private void register(View view)
	{
		mList = (PullToRefreshListView) view.findViewById(R.id.list);
		mTvError = (TextView) view.findViewById(R.id.tv_error);
	}

	@Override
	protected void init()
	{
		register(getView());
		bindDefaultData();
		initPullView();
	}

	private void bindDefaultData()
	{
		mListModel = new ArrayList<BizYouhuirCtlItemModel>();
		mAdapter = new BizYouhuirCtlAdapter(mListModel, getActivity());
		mList.setAdapter(mAdapter);
		mList.setOnItemClickListener(new OnItemClickListener()
		{
			@Override
			public void onItemClick(AdapterView<?> parent, View view, int position, long id)
			{

				Intent intent = new Intent(getActivity(), Biz_tuan_msg_Activity.class);
				intent.putExtra(Biz_tuan_msg_Activity.EXTRA_ID, mListModel.get((int) id).getId());
				intent.putExtra(Biz_tuan_msg_Activity.EXTRA_TYPE, 1);
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
				refreshData();
			}

			@Override
			public void onPullUpToRefresh(PullToRefreshBase<ListView> refreshView)
			{
				loadMoreDataType1();
			}
		});
		mList.setRefreshing();
	}

	@Override
	protected void onRefreshData()
	{
		mCurrentPage = 1;
		requestCtlBizYouhuirActIndex(false);
	}

	private void loadMoreDataType1()
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
				requestCtlBizYouhuirActIndex(true);
			}
		} else
		{
			refreshData();
		}
	}

	protected void requestCtlBizYouhuirActIndex(final boolean isLoadMore)
	{
		RequestModel model = new RequestModel();
		model.putCtlAct("biz_youhuir", "index");
		model.put("page", mCurrentPage);
		SDRequestCallBack<BizYouhuirCtlIndexActModel> handler = new SDRequestCallBack<BizYouhuirCtlIndexActModel>()
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
			public void onSuccess(BizYouhuirCtlIndexActModel actModel)
			{
				if (!SDInterfaceUtil.dealactModel(actModel, getActivity()))
				{
					switch (actModel.getStatus())
					{
					case 0:
						SDToast.showToast(actModel.getInfo());
						break;
					case 1:
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
						break;
					}
				}

			}

			@Override
			public void onStart()
			{
				nDialog = SDDialogManager.showProgressDialog("加载中...");
			}
		};

		InterfaceServer.getInstance().requestInterface(model, handler);

	}

	protected void toggleEmptyMsg()
	{
		if (SDCollectionUtil.isListHasData(mListModel))
		{
			if (mTvError.getVisibility() == View.VISIBLE)
			{
				mTvError.setVisibility(View.GONE);
			}
		} else
		{
			if (mTvError.getVisibility() == View.GONE)
			{
				mTvError.setVisibility(View.VISIBLE);
			}
		}
	}

}
