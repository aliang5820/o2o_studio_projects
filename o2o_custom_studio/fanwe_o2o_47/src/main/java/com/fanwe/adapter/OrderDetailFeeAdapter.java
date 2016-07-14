package com.fanwe.adapter;

import java.util.List;

import android.app.Activity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.fanwe.library.adapter.SDSimpleAdapter;
import com.fanwe.library.utils.SDViewBinder;
import com.fanwe.library.utils.ViewHolder;
import com.fanwe.model.FeeinfoModel;
import com.fanwe.o2o.newo2o.R;

public class OrderDetailFeeAdapter extends SDSimpleAdapter<FeeinfoModel>
{

	public OrderDetailFeeAdapter(List<FeeinfoModel> listModel, Activity activity)
	{
		super(listModel, activity);
	}

	@Override
	public int getLayoutId(int position, View convertView, ViewGroup parent)
	{
		return R.layout.item_order_detail_fee;
	}

	@Override
	public void bindData(int position, View convertView, ViewGroup parent, FeeinfoModel model)
	{
		TextView tvName = ViewHolder.get(R.id.item_order_fee_tv_name, convertView);
		TextView tvValue = ViewHolder.get(R.id.item_order_fee_tv_value, convertView);

		SDViewBinder.setTextView(tvName, model.getName());
		SDViewBinder.setTextView(tvValue, model.getValue());
	}

}
