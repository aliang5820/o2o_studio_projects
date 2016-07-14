package com.fanwe;

import java.util.ArrayList;
import java.util.List;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ListView;

import com.fanwe.adapter.NoticeListAdapter;
import com.fanwe.constant.Constant.TitleType;
import com.fanwe.http.InterfaceServer;
import com.fanwe.http.SDRequestCallBack;
import com.fanwe.library.dialog.SDDialogManager;
import com.fanwe.library.utils.SDToast;
import com.fanwe.library.utils.SDViewUtil;
import com.fanwe.model.NoticeModel;
import com.fanwe.model.Notice_indexActModel;
import com.fanwe.model.PageModel;
import com.fanwe.model.RequestModel;
import com.fanwe.o2o.newo2o.R;
import com.handmark.pulltorefresh.library.PullToRefreshBase;
import com.handmark.pulltorefresh.library.PullToRefreshBase.Mode;
import com.handmark.pulltorefresh.library.PullToRefreshBase.OnRefreshListener2;
import com.handmark.pulltorefresh.library.PullToRefreshListView;
import com.lidroid.xutils.http.ResponseInfo;
import com.lidroid.xutils.view.annotation.ViewInject;

/**
 * 公告列表
 * 
 * @author js02
 * 
 */
public class NoticeListActivity extends BaseActivity
{

	@ViewInject(R.id.act_news_list_ptrlv_content)
	private PullToRefreshListView mPtrlvContent = null;

	private List<NoticeModel> mListModel = new ArrayList<NoticeModel>();
	private NoticeListAdapter mAdapter;

	private PageModel mPage = new PageModel();

	@Override
	protected void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
		setmTitleType(TitleType.TITLE);
		setContentView(R.layout.act_notice_list);
		init();
	}

	private void init()
	{
		initTitle();
		bindDefaultData();
		initPullToRefreshListView();
	}

	private void initPullToRefreshListView()
	{
		mPtrlvContent.setMode(Mode.BOTH);
		mPtrlvContent.setOnRefreshListener(new OnRefreshListener2<ListView>()
		{

			@Override
			public void onPullDownToRefresh(PullToRefreshBase<ListView> refreshView)
			{
				mPage.resetPage();
				requestData(false);
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
					requestData(true);
				}
			}
		});

		mPtrlvContent.setOnItemClickListener(new OnItemClickListener()
		{
			@Override
			public void onItemClick(AdapterView<?> parent, View view, int position, long id)
			{
				NoticeModel model = mAdapter.getItem((int) id);
				if (model != null)
				{
					Intent intent = new Intent(getApplicationContext(), NoticeDetailActivity.class);
					intent.putExtra(NoticeDetailActivity.EXTRA_NOTICE_ID, model.getId());
					startActivity(intent);
				}

			}
		});

		mPtrlvContent.setRefreshing();

	}

	private void initTitle()
	{
		mTitle.setMiddleTextTop("公告列表");
	}

	private void bindDefaultData()
	{
		mAdapter = new NoticeListAdapter(mListModel, this);
		mPtrlvContent.setAdapter(mAdapter);
	}

	private void requestData(final boolean isLoadMore)
	{
		RequestModel model = new RequestModel();
		model.putCtl("notice");
		model.putPage(mPage.getPage());
		SDRequestCallBack<Notice_indexActModel> handler = new SDRequestCallBack<Notice_indexActModel>()
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
					SDViewUtil.updateAdapterByList(mListModel, actModel.getList(), mAdapter, isLoadMore);
				}
			}

			@Override
			public void onFinish()
			{
				SDDialogManager.dismissProgressDialog();
				mPtrlvContent.onRefreshComplete();
			}
		};
		InterfaceServer.getInstance().requestInterface(model, handler);

	}

}