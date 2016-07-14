package com.fanwe.fragment;

import java.util.ArrayList;
import java.util.List;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ImageView;
import android.widget.ListView;

import com.fanwe.EventDetailActivity;
import com.fanwe.adapter.EventListAdapter;
import com.fanwe.http.InterfaceServer;
import com.fanwe.http.SDRequestCallBack;
import com.fanwe.library.dialog.SDDialogManager;
import com.fanwe.library.utils.SDToast;
import com.fanwe.library.utils.SDViewUtil;
import com.fanwe.model.EventModel;
import com.fanwe.model.PageModel;
import com.fanwe.model.RequestModel;
import com.fanwe.model.Uc_collectActModel;
import com.fanwe.o2o.newo2o.R;
import com.handmark.pulltorefresh.library.PullToRefreshBase;
import com.handmark.pulltorefresh.library.PullToRefreshBase.Mode;
import com.handmark.pulltorefresh.library.PullToRefreshBase.OnRefreshListener2;
import com.handmark.pulltorefresh.library.PullToRefreshListView;
import com.lidroid.xutils.exception.HttpException;
import com.lidroid.xutils.http.ResponseInfo;
import com.lidroid.xutils.view.annotation.ViewInject;

/**
 * 我的活动收藏
 * 
 * @author js02
 * 
 */
public class MyCollectionEventFragment extends BaseFragment
{

	@ViewInject(R.id.ptrlv_content)
	private PullToRefreshListView mPtrlv_content;

	@ViewInject(R.id.iv_empty)
	private ImageView mIvEmpty;

	private EventListAdapter mAdapter;
	private List<EventModel> mListModel = new ArrayList<EventModel>();

	private PageModel mPage = new PageModel();

	@Override
	protected View onCreateContentView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState)
	{
		return setContentView(R.layout.frag_my_collection_tuan_goods);
	}

	@Override
	protected void init()
	{
		bindDefaultData();
		initPullToRefreshListView();
	}

	private void bindDefaultData()
	{
		mAdapter = new EventListAdapter(mListModel, getActivity());
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
				mPage.resetPage();
				requestCollect(false);
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
					requestCollect(true);
				}
			}
		});
		mPtrlv_content.setOnItemClickListener(new OnItemClickListener()
		{
			@Override
			public void onItemClick(AdapterView<?> parent, View view, int position, long id)
			{
				if (mAdapter != null)
				{
					EventModel model = mAdapter.getItem((int) id);
					if (model != null)
					{
						Intent intent = new Intent(getActivity(), EventDetailActivity.class);
						intent.putExtra(EventDetailActivity.EXTRA_EVENT_ID, model.getId());
						startActivity(intent);
					}
				}

			}

		});
		mPtrlv_content.setRefreshing();
	}

	private void requestCollect(final boolean isLoadMore)
	{
		RequestModel model = new RequestModel();
		model.putCtl("uc_collect");
		model.putPage(mPage.getPage());
		model.putAct("event_collect");
		model.putUser();
		SDRequestCallBack<Uc_collectActModel> handler = new SDRequestCallBack<Uc_collectActModel>()
		{

			@Override
			public void onStart()
			{
				SDDialogManager.showProgressDialog("请稍候...");
			}

			@Override
			public void onSuccess(ResponseInfo<String> responseInfo)
			{
				if (actModel.getStatus() == 1)
				{
					mPage.update(actModel.getPage());
					SDViewUtil.updateAdapterByList(mListModel, actModel.getEvent_list(), mAdapter, isLoadMore);
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
				SDViewUtil.toggleEmptyMsgByList(mListModel, mIvEmpty);
			}
		};
		InterfaceServer.getInstance().requestInterface(model, handler);

	}

}