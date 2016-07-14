package com.fanwe.fragment;

import java.util.List;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import com.fanwe.adapter.OrderDetailFeeAdapter;
import com.fanwe.model.FeeinfoModel;
import com.fanwe.o2o.newo2o.R;
import com.lidroid.xutils.view.annotation.ViewInject;

/**
 * 订单详情页(费用详情fragment)
 * 
 * @author js02
 * 
 */
public class OrderDetailFeeFragment extends OrderDetailBaseFragment
{

	@ViewInject(R.id.frag_order_detail_fee_ll_fees)
	private LinearLayout mLlFees = null;

	private List<FeeinfoModel> mListModel;

	public void setListFeeinfo(List<FeeinfoModel> listModel)
	{
		this.mListModel = listModel;
		refreshData();
	}

	@Override
	protected View onCreateContentView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState)
	{
		return setContentView(R.layout.frag_order_detail_fee);
	}

	@Override
	protected void init()
	{
		bindData();
	}

	private void bindData()
	{
		if (!toggleFragmentView(mListModel))
		{
			return;
		}

		mLlFees.removeAllViews();
		OrderDetailFeeAdapter adapter = new OrderDetailFeeAdapter(mListModel, getActivity());
		for (int i = 0; i < mListModel.size(); i++)
		{
			View itemView = adapter.getView(i, null, null);
			mLlFees.addView(itemView);
		}
	}

	@Override
	protected void onRefreshData()
	{
		bindData();
		super.onRefreshData();
	}

}