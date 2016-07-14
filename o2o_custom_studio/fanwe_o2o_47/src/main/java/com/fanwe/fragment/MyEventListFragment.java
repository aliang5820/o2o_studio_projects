package com.fanwe.fragment;

import java.util.ArrayList;
import java.util.List;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ListView;

import com.fanwe.adapter.MyEventAdapter;
import com.fanwe.http.InterfaceServer;
import com.fanwe.http.SDRequestCallBack;
import com.fanwe.library.dialog.SDDialogManager;
import com.fanwe.library.utils.SDToast;
import com.fanwe.library.utils.SDViewUtil;
import com.fanwe.model.PageModel;
import com.fanwe.model.RequestModel;
import com.fanwe.model.Uc_eventActItemModel;
import com.fanwe.model.Uc_event_indexActModel;
import com.fanwe.o2o.newo2o.R;
import com.handmark.pulltorefresh.library.PullToRefreshBase;
import com.handmark.pulltorefresh.library.PullToRefreshBase.Mode;
import com.handmark.pulltorefresh.library.PullToRefreshBase.OnRefreshListener2;
import com.handmark.pulltorefresh.library.PullToRefreshListView;
import com.lidroid.xutils.http.ResponseInfo;
import com.lidroid.xutils.view.annotation.ViewInject;

/**
 * 我参加的活动列表
 * 
 * @author js02
 * 
 */
public class MyEventListFragment extends BaseFragment
{

	public static final class EventTag
	{
		/** 全部 */
		public static final int ALL = 0;
		/** 快过期 */
		public static final int WILL_OVERDUE = 1;
		/** 未使用 */
		public static final int UN_USED = 2;
		/** 已失效 */
		public static final int OVERDUE = 3;
	}

	@ViewInject(R.id.ptrlv_content)
	private PullToRefreshListView mPtrlvContent;

	@ViewInject(R.id.iv_empty)
	private ImageView mIv_empty;

	private List<Uc_eventActItemModel> mListModel = new ArrayList<Uc_eventActItemModel>();
	private MyEventAdapter mAdapter;

	private PageModel mPage = new PageModel();

	private int mStatus;

	public void setmStatus(int mStatus)
	{
		this.mStatus = mStatus;
	}

	public static MyEventListFragment newInstance(int status)
	{
		MyEventListFragment fragment = new MyEventListFragment();
		fragment.setmStatus(status);
		return fragment;
	}

	@Override
	protected View onCreateContentView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState)
	{
		return setContentView(R.layout.frag_my_event_list);
	}

	@Override
	protected void init()
	{
		bindDefaultData();
		initPullListView();
	}

	private void bindDefaultData()
	{
		mAdapter = new MyEventAdapter(mListModel, getActivity());
		mPtrlvContent.setAdapter(mAdapter);
	}

	protected void requestMyEvent(final boolean isLoadMore)
	{
		RequestModel model = new RequestModel();
		model.putCtl("uc_event");
		model.putUser();
		model.putPage(mPage.getPage());
		model.put("tag", mStatus);
		SDRequestCallBack<Uc_event_indexActModel> handler = new SDRequestCallBack<Uc_event_indexActModel>()
		{

			@Override
			public void onStart()
			{
				SDDialogManager.showProgressDialog("请稍候");
			}

			@Override
			public void onSuccess(ResponseInfo<String> responseInfo)
			{
				if (actModel.getStatus() == 1)
				{
					mPage.update(actModel.getPage());
					SDViewUtil.updateAdapterByList(mListModel, actModel.getItem(), mAdapter, isLoadMore);
				}
			}

			@Override
			public void onFinish()
			{
				SDDialogManager.dismissProgressDialog();
				mPtrlvContent.onRefreshComplete();
				SDViewUtil.toggleEmptyMsgByList(mListModel, mIv_empty);
			}
		};

		InterfaceServer.getInstance().requestInterface(model, handler);

	}

	private void initPullListView()
	{
		mPtrlvContent.setMode(Mode.BOTH);
		mPtrlvContent.setOnRefreshListener(new OnRefreshListener2<ListView>()
		{

			@Override
			public void onPullDownToRefresh(PullToRefreshBase<ListView> refreshView)
			{
				mPage.resetPage();
				requestMyEvent(false);
			}

			@Override
			public void onPullUpToRefresh(PullToRefreshBase<ListView> refreshView)
			{
				if (!mPage.increment())
				{
					SDToast.showToast("没有更多数据了");
					mPtrlvContent.onRefreshComplete();
				} else
				{
					requestMyEvent(true);
				}
			}
		});
		mPtrlvContent.setRefreshing();
	}

}