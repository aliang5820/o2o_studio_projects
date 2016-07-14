package com.fanwe.fragment;

import java.util.ArrayList;
import java.util.List;

import android.text.TextUtils;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ListView;
import android.widget.TextView;

import com.fanwe.Biz_tuan_msg_Activity;
import com.fanwe.adapter.Biz_tuan_msgAdapter;
import com.fanwe.businessclient.R;
import com.fanwe.http.InterfaceServer;
import com.fanwe.http.listener.SDRequestCallBack;
import com.fanwe.listener.RefreshActListener;
import com.fanwe.model.Biz_tuan_msgActModel;
import com.fanwe.model.Biz_tuan_msgModel;
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
 * 评论详情-差评
 */
public class Biz_tuan_msg_right_Fragment extends BaseFragment implements RefreshActListener
{
	private PullToRefreshListView mList = null;

	private TextView mTvError = null;

	private int mCurrentPage = 1;

	private int mTotalPage = 0;

	private List<Biz_tuan_msgModel> mListModel = null;

	private Biz_tuan_msgAdapter mAdapter = null;

	private String mDataID = null;

	private int mCurrentType = 0;

	@Override
	protected int onCreateContentView()
	{
		return R.layout.frag_biz_tuan_msg_right;
	}

	private void register(View view)
	{
		mList = (PullToRefreshListView) view.findViewById(R.id.frag_biztuanmsgright_list);
		mTvError = (TextView) view.findViewById(R.id.frag_biztuanmsgright_tv_error);
	}

	@Override
	protected void init()
	{
		register(getView());
		initIntentInfo();
		bindDefaultData();
		initPullView();
	}

	private void initIntentInfo()
	{
		mDataID = getActivity().getIntent().getExtras().getString(Biz_tuan_msg_Activity.EXTRA_ID);

	}

	private void bindDefaultData()
	{
		// TODO Auto-generated method stub
		mListModel = new ArrayList<Biz_tuan_msgModel>();
		mAdapter = new Biz_tuan_msgAdapter(mListModel, getActivity(), mDataID, mCurrentType, this);
		mList.setAdapter(mAdapter);
		mList.setOnItemClickListener(new OnItemClickListener()
		{

			@Override
			public void onItemClick(AdapterView<?> parent, View view, int position, long id)
			{
				// TODO Auto-generated method stub

			}
		});
	}

	private void initPullView()
	{
		// TODO Auto-generated method stub
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
		requestBiz_tuanType0(false, isReturnHead);
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
				requestBiz_tuanType0(true, false);
			}
		} else
		{
			refreshData(false);
		}
	}

	protected void requestBiz_tuanType0(final boolean isLoadMore, final boolean isReturnHeda)
	{

		if (TextUtils.isEmpty(mDataID))
		{
			return;
		}

		RequestModel model = new RequestModel();

		if (mCurrentType == 0)
		{
			model.putCtlAct("biz_dealr", "dealr_dp_list");
		} else if (mCurrentType == 1)
		{
			model.putCtlAct("biz_youhuir", "youhuir_dp_list");
		} else if (mCurrentType == 2)
		{
			model.putCtlAct("biz_eventr", "eventr_dp_list");
		} else if (mCurrentType == 3)
		{
			model.putCtlAct("biz_storer", "storer_dp_list");
		}
		model.put("data_id", mDataID);
		model.put("page", mCurrentPage);
		model.put("is_bad", 1);

		SDRequestCallBack<Biz_tuan_msgActModel> handler = new SDRequestCallBack<Biz_tuan_msgActModel>()
		{
			@Override
			public void onFinish()
			{
				mList.onRefreshComplete();
				if (isReturnHeda)
					mList.getRefreshableView().setSelection(0);
				toggleEmptyMsg();

			}

			@Override
			public void onSuccess(Biz_tuan_msgActModel actModel)
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
						// SDToast.showToast("未找到数据!");
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

	@Override
	public void refreshActivity()
	{
		// TODO Auto-generated method stub
		refreshData(true);
	}
}
