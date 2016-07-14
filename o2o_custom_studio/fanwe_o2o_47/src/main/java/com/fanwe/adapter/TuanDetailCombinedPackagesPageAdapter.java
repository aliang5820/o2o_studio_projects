package com.fanwe.adapter;

import java.util.List;

import android.app.Activity;
import android.view.View;

import com.fanwe.adapter.TuanDetailCombinedPackagesAdapter.TuanDetailCombinedPackagesAdapterListener;
import com.fanwe.library.adapter.SDPagerAdapter;
import com.fanwe.library.customview.SDGridLinearLayout;
import com.fanwe.model.Deal_indexActModel;

public class TuanDetailCombinedPackagesPageAdapter extends SDPagerAdapter<List<Deal_indexActModel>>
{

	private TuanDetailCombinedPackagesAdapterListener mListener;

	public void setmListener(TuanDetailCombinedPackagesAdapterListener mListener)
	{
		this.mListener = mListener;
	}

	public TuanDetailCombinedPackagesPageAdapter(List<List<Deal_indexActModel>> listModel, Activity activity)
	{
		super(listModel, activity);
	}

	@Override
	public View getView(View container, int position)
	{
		SDGridLinearLayout ll = new SDGridLinearLayout(mActivity);
		ll.setmColNumber(3);
		List<Deal_indexActModel> listModel = getItemModel(position);

		TuanDetailCombinedPackagesAdapter adapter = new TuanDetailCombinedPackagesAdapter(listModel, mActivity);
		adapter.setmListener(mListener);
		ll.setAdapter(adapter);
		return ll;
	}

}
