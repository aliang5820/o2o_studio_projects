package com.fanwe.fragment;

import java.util.ArrayList;
import java.util.List;

import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import com.fanwe.BindBankActivity;
import com.fanwe.MyWithdrawalsActivity;
import com.fanwe.adapter.Frag_tab3Adapter;
import com.fanwe.businessclient.R;
import com.fanwe.http.InterfaceServer;
import com.fanwe.http.listener.SDRequestCallBack;
import com.fanwe.library.utils.SDViewBinder;
import com.fanwe.model.Biz_withdrawalCtlModel;
import com.fanwe.model.Frag_tab3Model;
import com.fanwe.model.Frag_tab3_itemModel;
import com.fanwe.model.RequestModel;
import com.fanwe.utils.SDDialogUtil;
import com.fanwe.utils.SDInterfaceUtil;
import com.fanwe.utils.SDToast;
import com.handmark.pulltorefresh.library.PullToRefreshBase;
import com.handmark.pulltorefresh.library.PullToRefreshBase.Mode;
import com.handmark.pulltorefresh.library.PullToRefreshBase.OnRefreshListener2;
import com.handmark.pulltorefresh.library.PullToRefreshListView;

/**
 * 
 * 我的
 */
public class Tab_3_Fragment extends BaseFragment implements OnClickListener
{
	private PullToRefreshListView mPList;

	private LinearLayout mLl_bind;
	private LinearLayout mLl_no_bind;

	private TextView mTv_name;
	private TextView mTv_bank_name;
	private TextView mTv_bank_info;
	private TextView mTv_money;
	private TextView mTv_withdrawals;
	private TextView mTv_no_bank;

	private LinearLayout mLl_money;

	private List<Frag_tab3_itemModel> mListModel;
	private Frag_tab3Adapter mAdapter;

	private int mTotalPage = 0;
	private int mCurrentPage;

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState)
	{
		View view = inflater.inflate(R.layout.m_frag_tab_3, container, false);
		init(view);
		return view;
	}

	private void init(View view)
	{
		register(view);
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
				requestFrag_tab3(true);
			}
		} else
		{
			refreshData(false);
		}

	}

	protected void refreshData(boolean b)
	{
		mCurrentPage = 1;
		requestFrag_tab3(false);

	}

	private void requestFrag_tab3(final boolean isLoadMore)
	{
		RequestModel model = new RequestModel();
		model.putCtl("biz_withdrawal");
		SDRequestCallBack<Biz_withdrawalCtlModel> handler = new SDRequestCallBack<Biz_withdrawalCtlModel>()
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
			public void onSuccess(Biz_withdrawalCtlModel actModel)
			{
				if (!SDInterfaceUtil.dealactModel(actModel, getActivity()))
				{
					if (actModel.getSupplier_info() != null)
					{
						addEventInfo(actModel.getSupplier_info());
					}
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

			@Override
			public void onStart()
			{
				nDialog = SDDialogUtil.showLoading("加载中...");
			}

		};
		InterfaceServer.getInstance().requestInterface(model, handler);

	}

	protected void addEventInfo(Frag_tab3Model supplier_info)
	{
		SDViewBinder.setTextView(mTv_name, supplier_info.getName());
		SDViewBinder.setTextView(mTv_bank_name, supplier_info.getBank_name());
		SDViewBinder.setTextView(mTv_bank_info, supplier_info.getBank_info());
		SDViewBinder.setTextView(mTv_money, supplier_info.getMoney());

		if (TextUtils.isEmpty(supplier_info.getBank_name()) || TextUtils.isEmpty(supplier_info.getBank_info()))
		{
			mLl_no_bind.setVisibility(View.VISIBLE);
			mLl_bind.setVisibility(View.GONE);
			mLl_money.setVisibility(View.GONE);
		} else
		{
			mLl_no_bind.setVisibility(View.GONE);
			mLl_bind.setVisibility(View.VISIBLE);
			mLl_money.setVisibility(View.VISIBLE);
		}
	}

	private void initContent()
	{
		mListModel = new ArrayList<Frag_tab3_itemModel>();
		mAdapter = new Frag_tab3Adapter(mListModel, getActivity());
		mPList.setAdapter(mAdapter);
	}

	private void register(View view)
	{
		mLl_no_bind = (LinearLayout) view.findViewById(R.id.ll_no_bind);
		mLl_no_bind.setVisibility(View.GONE);
		mTv_no_bank = (TextView) view.findViewById(R.id.tv_no_bank);
		mTv_no_bank.setOnClickListener(this);

		mLl_bind = (LinearLayout) view.findViewById(R.id.ll_bind);
		mLl_bind.setVisibility(View.GONE);
		mTv_bank_name = (TextView) view.findViewById(R.id.frag_tab3_bank_name);
		mTv_bank_info = (TextView) view.findViewById(R.id.frag_tab3_bank_info);

		mPList = (PullToRefreshListView) view.findViewById(R.id.ptrList);

		mLl_money = (LinearLayout) view.findViewById(R.id.ll_money);
		mLl_money.setVisibility(View.GONE);
		mTv_name = (TextView) view.findViewById(R.id.frag_tab3_name);
		mTv_money = (TextView) view.findViewById(R.id.frag_tab3_money);

		mTv_withdrawals = (TextView) view.findViewById(R.id.frag_tab3_Withdrawals);
		mTv_withdrawals.setOnClickListener(this);

	}

	@Override
	public void onActivityResult(int requestCode, int resultCode, Intent data)
	{
		super.onActivityResult(requestCode, resultCode, data);

		if (resultCode == getActivity().RESULT_OK)
		{
			refreshData(false);
		}
	}

	@Override
	public void onClick(View v)
	{
		switch (v.getId())
		{
		case R.id.frag_tab3_Withdrawals:
			click_tv_withdrawals();
			break;
		case R.id.tv_no_bank:
			click_tv_no_bank();
			break;
		}
	}

	private void click_tv_withdrawals()
	{
		startActivityForResult(new Intent(getActivity(), MyWithdrawalsActivity.class), 1);
	}

	private void click_tv_no_bank()
	{
		startActivityForResult(new Intent(getActivity(), BindBankActivity.class), 1);
	}

}
