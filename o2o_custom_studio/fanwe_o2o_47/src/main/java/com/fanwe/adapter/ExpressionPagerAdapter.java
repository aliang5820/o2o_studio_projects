package com.fanwe.adapter;

import java.util.List;

import android.app.Activity;
import android.view.View;

import com.fanwe.adapter.ExpressionAdapter.ExpressionAdapterListener;
import com.fanwe.library.adapter.SDPagerAdapter;
import com.fanwe.library.customview.SDGridLinearLayout;
import com.fanwe.model.ExpressionModel;

public class ExpressionPagerAdapter extends SDPagerAdapter<List<ExpressionModel>>
{

	private ExpressionAdapterListener mListener;

	public void setmListener(ExpressionAdapterListener mListener)
	{
		this.mListener = mListener;
	}

	public ExpressionPagerAdapter(List<List<ExpressionModel>> listModel, Activity activity)
	{
		super(listModel, activity);
	}

	@Override
	public View getView(View container, int position)
	{
		final SDGridLinearLayout ll = new SDGridLinearLayout(mActivity);
		ll.setmColNumber(7);
		List<ExpressionModel> listModel = getItemModel(position);
		ExpressionAdapter adapter = new ExpressionAdapter(listModel, mActivity);
		adapter.setListener(mListener);
		ll.setAdapter(adapter);
		return ll;
	}

}
