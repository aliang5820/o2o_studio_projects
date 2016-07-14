package com.fanwe.fragment;

import java.util.ArrayList;
import java.util.List;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ListView;

import com.fanwe.adapter.MyCommentAdapter;
import com.fanwe.constant.Constant.TitleType;
import com.fanwe.http.InterfaceServer;
import com.fanwe.http.SDRequestCallBack;
import com.fanwe.library.dialog.SDDialogManager;
import com.fanwe.library.utils.SDToast;
import com.fanwe.library.utils.SDViewUtil;
import com.fanwe.model.CommentModel;
import com.fanwe.model.PageModel;
import com.fanwe.model.RequestModel;
import com.fanwe.model.Uc_review_indexActModel;
import com.fanwe.o2o.newo2o.R;
import com.handmark.pulltorefresh.library.PullToRefreshBase;
import com.handmark.pulltorefresh.library.PullToRefreshBase.Mode;
import com.handmark.pulltorefresh.library.PullToRefreshBase.OnRefreshListener2;
import com.handmark.pulltorefresh.library.PullToRefreshListView;
import com.lidroid.xutils.http.ResponseInfo;
import com.lidroid.xutils.view.annotation.ViewInject;

/**
 * 我的点评
 * 
 * @author js02
 * 
 */
public class MyCommentFragment extends BaseFragment
{

	@ViewInject(R.id.ptrlv_content)
	private PullToRefreshListView mPtrlvContent;

	@ViewInject(R.id.iv_empty)
	private ImageView mIv_empty;

	private List<CommentModel> mListModel = new ArrayList<CommentModel>();
	private MyCommentAdapter mAdapter;

	private PageModel mPage = new PageModel();

	@Override
	protected View onCreateContentView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState)
	{
		setmTitleType(TitleType.TITLE);
		return setContentView(R.layout.frag_my_comment);
	}

	@Override
	protected void init()
	{
		initTitle();
		bindDefaultData();
		initPullListView();
	}

	private void initTitle()
	{
		mTitle.setMiddleTextTop("我的点评");
	}

	private void bindDefaultData()
	{
		mAdapter = new MyCommentAdapter(mListModel, getActivity());
		mPtrlvContent.setAdapter(mAdapter);
	}

	protected void requestData(final boolean isLoadMore)
	{
		RequestModel model = new RequestModel();
		model.putCtl("uc_review");
		model.putUser();
		model.putPage(mPage.getPage());
		SDRequestCallBack<Uc_review_indexActModel> handler = new SDRequestCallBack<Uc_review_indexActModel>()
		{

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
		mPtrlvContent.setRefreshing();
	}

}