package com.fanwe.fragment;

import java.util.ArrayList;
import java.util.List;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import com.fanwe.adapter.MyRedEnvelopeAdapter;
import com.fanwe.event.EnumEventTag;
import com.fanwe.http.InterfaceServer;
import com.fanwe.http.SDRequestCallBack;
import com.fanwe.library.utils.SDToast;
import com.fanwe.library.utils.SDViewUtil;
import com.fanwe.model.PageModel;
import com.fanwe.model.RedEnvelopeModel;
import com.fanwe.model.RequestModel;
import com.fanwe.model.Uc_ecv_indexActModel;
import com.fanwe.o2o.newo2o.R;
import com.handmark.pulltorefresh.library.PullToRefreshBase;
import com.handmark.pulltorefresh.library.PullToRefreshBase.Mode;
import com.handmark.pulltorefresh.library.PullToRefreshBase.OnRefreshListener2;
import com.handmark.pulltorefresh.library.PullToRefreshListView;
import com.lidroid.xutils.http.ResponseInfo;
import com.sunday.eventbus.SDBaseEvent;

/**
 * 失效红包
 * 
 * @author Administrator
 * 
 */
public class MyRedEnvelopeInvalidFragment extends BaseFragment
{

	protected PullToRefreshListView mPtrlv_content;

	protected MyRedEnvelopeAdapter mAdapter;
	protected List<RedEnvelopeModel> mListModel = new ArrayList<RedEnvelopeModel>();

	protected Uc_ecv_indexActModel mActModel;
	protected PageModel mPage = new PageModel();
	/** 1:已失效，0:可使用 */
	protected int n_valid = 1;

	@Override
	protected View onCreateContentView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState)
	{
		return setContentView(R.layout.frag_my_red_envelope_invalid);
	}

	@Override
	protected void init()
	{
		findViews();
		bindDefaultData();
		initPullToRefreshListView();
	}

	protected void findViews()
	{
		mPtrlv_content = (PullToRefreshListView) findViewById(R.id.ptrlv_content);
	}

	private void bindDefaultData()
	{
		mAdapter = new MyRedEnvelopeAdapter(mListModel, getActivity());
		mPtrlv_content.getRefreshableView().setAdapter(mAdapter);
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
				requestData(false);
			}

			@Override
			public void onPullUpToRefresh(PullToRefreshBase<ListView> refreshView)
			{
				if (mPage.increment())
				{
					requestData(true);
				} else
				{
					SDToast.showToast("没有更多数据了");
					mPtrlv_content.onRefreshComplete();
				}
			}
		});
		mPtrlv_content.setRefreshing();
	}

	protected void requestData(final boolean isLoadMore)
	{
		RequestModel model = new RequestModel();
		model.putCtl("uc_ecv");
		model.put("n_valid", n_valid);
		model.putPage(mPage.getPage());
		InterfaceServer.getInstance().requestInterface(model, new SDRequestCallBack<Uc_ecv_indexActModel>()
		{
			@Override
			public void onSuccess(ResponseInfo<String> responseInfo)
			{
				if (actModel.getStatus() > 0)
				{
					mActModel = actModel;
					mPage.update(actModel.getPage());
					bindData(isLoadMore);
				}
				super.onSuccess(responseInfo);
			}

			@Override
			public void onFinish()
			{
				mPtrlv_content.onRefreshComplete();
				super.onFinish();
			}

		});
	}

	protected void bindData(boolean isLoadMore)
	{
		SDViewUtil.updateAdapterByList(mListModel, mActModel.getData(), mAdapter, isLoadMore);
	}

	@Override
	public void onEventMainThread(SDBaseEvent event)
	{
		super.onEventMainThread(event);
		switch (EnumEventTag.valueOf(event.getTagInt()))
		{
		case EXCHANGE_RED_ENVELOPE_SUCCESS:
			requestData(false);
			break;
		case GET_RED_ENVELOPE_SUCCESS:
			requestData(false);
			break;

		default:
			break;
		}
	}

}
