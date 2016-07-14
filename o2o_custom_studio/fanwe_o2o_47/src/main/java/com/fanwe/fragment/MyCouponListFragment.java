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

import com.fanwe.MyCouponDetailActivity;
import com.fanwe.adapter.MyCouponsListAdapter;
import com.fanwe.http.InterfaceServer;
import com.fanwe.http.SDRequestCallBack;
import com.fanwe.library.dialog.SDDialogManager;
import com.fanwe.library.utils.SDToast;
import com.fanwe.library.utils.SDViewUtil;
import com.fanwe.model.PageModel;
import com.fanwe.model.RequestModel;
import com.fanwe.model.Uc_couponActItemModel;
import com.fanwe.model.Uc_coupon_indexActModel;
import com.fanwe.o2o.newo2o.R;
import com.handmark.pulltorefresh.library.PullToRefreshBase;
import com.handmark.pulltorefresh.library.PullToRefreshBase.Mode;
import com.handmark.pulltorefresh.library.PullToRefreshBase.OnRefreshListener2;
import com.handmark.pulltorefresh.library.PullToRefreshListView;
import com.lidroid.xutils.exception.HttpException;
import com.lidroid.xutils.http.ResponseInfo;
import com.lidroid.xutils.view.annotation.ViewInject;

public class MyCouponListFragment extends BaseFragment
{

	public static final class CouponTag
	{
		/** 所有 */
		public static final int ALL = 0;
		/** 快过期 */
		public static final int WILL_OVERDUE = 1;
		/** 未使用 */
		public static final int UN_USED = 2;
		/** 已失效 */
		public static final int OVERDUE = 3;
	}

	@ViewInject(R.id.ptrlv_content)
	private PullToRefreshListView mPtrlv_content;

	@ViewInject(R.id.iv_empty)
	private ImageView mIv_empty;

	private List<Uc_couponActItemModel> mListModel = new ArrayList<Uc_couponActItemModel>();
	private MyCouponsListAdapter mAdapter;

	private PageModel mPage = new PageModel();

	private int mStatus;

	public void setmStatus(int mStatus)
	{
		this.mStatus = mStatus;
	}

	public static MyCouponListFragment newInstance(int status)
	{
		MyCouponListFragment fragment = new MyCouponListFragment();
		fragment.setmStatus(status);
		return fragment;
	}

	@Override
	protected View onCreateContentView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState)
	{
		return setContentView(R.layout.frag_my_coupon_list);
	}

	@Override
	protected void init()
	{
		bindDefaultData();
		initPullToRefreshListView();
	}

	private void bindDefaultData()
	{
		mAdapter = new MyCouponsListAdapter(mListModel, getActivity());
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
				requestCoupons(false);
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
					requestCoupons(true);
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
					Uc_couponActItemModel model = mAdapter.getItem((int) id);
					Intent intent = new Intent();
					intent.setClass(getActivity(), MyCouponDetailActivity.class);
					intent.putExtra(MyCouponDetailActivity.EXTRA_COUPONLISTACTITEMMODEL, model);
					getActivity().startActivity(intent);
				}

			}
		});

		mPtrlv_content.setRefreshing();
	}

	private void requestCoupons(final boolean isLoadMore)
	{
		RequestModel model = new RequestModel();
		model.putCtl("uc_coupon");
		model.putUser();
		model.put("tag", mStatus); // 0:所有，1:快过期， 2:可以使用， 3:失败
		model.putPage(mPage.getPage());
		SDRequestCallBack<Uc_coupon_indexActModel> handler = new SDRequestCallBack<Uc_coupon_indexActModel>()
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
			public void onFailure(HttpException error, String msg)
			{

			}

			@Override
			public void onFinish()
			{
				SDDialogManager.dismissProgressDialog();
				mPtrlv_content.onRefreshComplete();
				SDViewUtil.toggleEmptyMsgByList(mListModel, mIv_empty);
			}
		};
		InterfaceServer.getInstance().requestInterface(model, handler);
	}
}