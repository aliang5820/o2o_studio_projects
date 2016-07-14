package com.fanwe.adapter;

import java.util.List;

import android.app.Activity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.fanwe.library.adapter.SDSimpleAdapter;
import com.fanwe.library.utils.SDViewBinder;
import com.fanwe.library.utils.SDViewUtil;
import com.fanwe.library.utils.ViewHolder;
import com.fanwe.model.RedEnvelopeModel;
import com.fanwe.o2o.newo2o.R;

public class MyRedEnvelopeAdapter extends SDSimpleAdapter<RedEnvelopeModel>
{

	public MyRedEnvelopeAdapter(List<RedEnvelopeModel> listModel, Activity activity)
	{
		super(listModel, activity);
	}

	@Override
	public int getLayoutId(int position, View convertView, ViewGroup parent)
	{
		return R.layout.item_my_red_envelope;
	}

	@Override
	public void bindData(int position, View convertView, ViewGroup parent, RedEnvelopeModel model)
	{
		TextView tv_money = ViewHolder.get(R.id.tv_money, convertView);
		TextView tv_name = ViewHolder.get(R.id.tv_name, convertView);
		TextView tv_status = ViewHolder.get(R.id.tv_status, convertView);
		TextView tv_time = ViewHolder.get(R.id.tv_time, convertView);
		TextView tv_use_limit = ViewHolder.get(R.id.tv_use_limit, convertView);

		SDViewBinder.setTextView(tv_money, model.getMoney_format());
		SDViewBinder.setTextView(tv_name, model.getName());
		SDViewBinder.setTextView(tv_status, model.getStatusFormat());
		SDViewBinder.setTextView(tv_time, model.getDatetime());
		SDViewBinder.setTextView(tv_use_limit, model.getUse_limitToString());

		SDViewUtil.setBackgroundColorResId(tv_status, model.getStatusColorResId());
	}

}
