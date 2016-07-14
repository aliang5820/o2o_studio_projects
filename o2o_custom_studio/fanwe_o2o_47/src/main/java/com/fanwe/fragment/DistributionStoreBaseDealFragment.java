package com.fanwe.fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import com.fanwe.adapter.DistributionStoreTuanAdapter;
import com.fanwe.model.PageModel;
import com.fanwe.model.Uc_fx_mallActModel;
import com.fanwe.o2o.newo2o.R;

public class DistributionStoreBaseDealFragment extends BaseFragment
{

	private LinearLayout mLl_deal;

	private PageModel mPage = new PageModel();

	/** 0:团购，1:商品 */
	private int mType;

	public int getmType()
	{
		return mType;
	}

	public void setmType(int mType)
	{
		this.mType = mType;
	}

	@Override
	protected View onCreateContentView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState)
	{
		return setContentView(R.layout.frag_distribution_store_tuan);
	}

	@Override
	protected void init()
	{
		findViews();
	}

	private void findViews()
	{
		mLl_deal = (LinearLayout) getView().findViewById(R.id.ll_deal);
	}

	public PageModel getmPage()
	{
		return mPage;
	}

	public void pullRefresh(final Uc_fx_mallActModel model)
	{
		bindHotList(model);
		mLl_deal.removeAllViews();
		addDealView(model);
	}

	private void bindHotList(Uc_fx_mallActModel model)
	{
		DistributionHotGoodsFragment frag = new DistributionHotGoodsFragment();
		frag.setmActModel(model);
		getSDFragmentManager().replace(R.id.frag_distribution_store_tuan_fl_deal_hot, frag);
	}

	public void pullLoadMore(Uc_fx_mallActModel model)
	{
		addDealView(model);
	}

	private void addDealView(Uc_fx_mallActModel model)
	{
		DistributionStoreTuanAdapter adapter = new DistributionStoreTuanAdapter(model.getDeal_list(), getActivity());
		for (int i = 0; i < adapter.getCount(); i++)
		{
			View itemView = adapter.getView(i, null, null);
			if (itemView != null)
			{
				mLl_deal.addView(itemView);
			}
		}
	}

}
