package com.fanwe.fragment;

import java.util.ArrayList;
import java.util.List;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ListView;

import com.fanwe.adapter.MerchantListAdapter;
import com.fanwe.http.InterfaceServer;
import com.fanwe.http.SDRequestCallBack;
import com.fanwe.library.utils.SDToast;
import com.fanwe.library.utils.SDViewUtil;
import com.fanwe.model.PageModel;
import com.fanwe.model.RequestModel;
import com.fanwe.model.StoreModel;
import com.fanwe.model.Stores_indexActModel;
import com.fanwe.o2o.newo2o.R;
import com.handmark.pulltorefresh.library.PullToRefreshBase;
import com.handmark.pulltorefresh.library.PullToRefreshBase.Mode;
import com.handmark.pulltorefresh.library.PullToRefreshBase.OnRefreshListener2;
import com.handmark.pulltorefresh.library.PullToRefreshListView;
import com.lidroid.xutils.exception.HttpException;
import com.lidroid.xutils.http.ResponseInfo;
import com.lidroid.xutils.view.annotation.ViewInject;

public class NearByMerchantFragment extends BaseFragment
{

	@ViewInject(R.id.frag_nearby_discount_lv)
	private PullToRefreshListView mIpLvVip = null;

	@ViewInject(R.id.frag_nearby_discount_iv_empty)
	private ImageView mIvEmpty = null;

	private MerchantListAdapter mAdapter = null;
	private List<StoreModel> mListMerchantModel = new ArrayList<StoreModel>();

	private PageModel mPage = new PageModel();
	private int cate_id;

	@Override
	protected View onCreateContentView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState)
	{
		return setContentView(R.layout.frag_nearby_discount);
	}

	@Override
	protected void init()
	{
		bindDefaultData();
		initPullListView();

	}

	private void bindDefaultData()
	{
		mAdapter = new MerchantListAdapter(mListMerchantModel, getActivity());
		mIpLvVip.setAdapter(mAdapter);
	}

	protected void requestNearByVip(final boolean isLoadMore)
	{

		RequestModel model = new RequestModel();
		model.putCtl("merchantlist");
		model.putUser();
		model.putCityId();
		model.putLocationScreen();
		model.putLocation();
		model.put("cate_id", cate_id);
		model.putPage(mPage.getPage());
		SDRequestCallBack<Stores_indexActModel> handler = new SDRequestCallBack<Stores_indexActModel>()
		{

			@Override
			public void onSuccess(ResponseInfo<String> responseInfo)
			{
				if (actModel.getStatus() == 1)
				{
					mPage.update(actModel.getPage());
					if (!isLoadMore)
					{
						mListMerchantModel.clear();
					}
					if (actModel.getItem() != null)
					{
						mListMerchantModel.addAll(actModel.getItem());
					} else
					{
						SDToast.showToast("没有更多数据了");
					}
					mAdapter.updateData(mListMerchantModel);
				}
			}

			@Override
			public void onFailure(HttpException error, String msg)
			{

			}

			@Override
			public void onFinish()
			{
				mIpLvVip.onRefreshComplete();
				SDViewUtil.toggleEmptyMsgByList(mListMerchantModel, mIvEmpty);
			}
		};

		InterfaceServer.getInstance().requestInterface(model, handler);

	}

	private void initPullListView()
	{
		mIpLvVip.setMode(Mode.BOTH);
		mIpLvVip.setOnRefreshListener(new OnRefreshListener2<ListView>()
		{

			@Override
			public void onPullDownToRefresh(PullToRefreshBase<ListView> refreshView)
			{
				mPage.resetPage();
				requestNearByVip(false);
			}

			@Override
			public void onPullUpToRefresh(PullToRefreshBase<ListView> refreshView)
			{
				if (!mPage.increment())
				{
					SDToast.showToast("没有更多数据了");
					mIpLvVip.onRefreshComplete();
				} else
				{
					requestNearByVip(true);
				}
			}
		});
		mIpLvVip.setRefreshing();
	}

	@Override
	public void onClick(View arg0)
	{
		// TODO Auto-generated method stub

	}

}