package com.fanwe.adapter;

import java.util.List;

import android.app.Activity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.fanwe.adapter.DealAttrAdapter.DealAttrAdapterListener;
import com.fanwe.library.adapter.SDSimpleAdapter;
import com.fanwe.library.customview.FlowLayout;
import com.fanwe.library.utils.SDViewBinder;
import com.fanwe.library.utils.ViewHolder;
import com.fanwe.model.Deal_attrModel;
import com.fanwe.o2o.newo2o.R;

public class DealAttrGroupAdapter extends SDSimpleAdapter<Deal_attrModel>
{

	private DealAttrAdapterListener mListener;

	public void setmListener(DealAttrAdapterListener mListener)
	{
		this.mListener = mListener;
	}

	public DealAttrGroupAdapter(List<Deal_attrModel> listModel, Activity activity)
	{
		super(listModel, activity);
	}

	@Override
	public int getLayoutId(int position, View convertView, ViewGroup parent)
	{
		return R.layout.item_deal_attr_group;
	}

	@Override
	public void bindData(int position, View convertView, ViewGroup parent, Deal_attrModel model)
	{
		TextView tv_name = ViewHolder.get(R.id.tv_attr_name, convertView);
		FlowLayout flow_attr = ViewHolder.get(R.id.flow_attr, convertView);

		SDViewBinder.setTextView(tv_name, model.getName());

		DealAttrAdapter adapter = new DealAttrAdapter(model.getAttr_list(), mActivity);
		adapter.setListener(mListener);
		flow_attr.setAdapter(adapter);
	}

}
