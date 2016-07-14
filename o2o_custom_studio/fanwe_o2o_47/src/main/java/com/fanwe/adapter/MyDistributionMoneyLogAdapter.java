package com.fanwe.adapter;

import java.util.List;

import android.app.Activity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.fanwe.library.adapter.SDSimpleAdapter;
import com.fanwe.library.utils.SDViewBinder;
import com.fanwe.library.utils.ViewHolder;
import com.fanwe.model.DistributionMoneyLogModel;
import com.fanwe.o2o.newo2o.R;

public class MyDistributionMoneyLogAdapter extends SDSimpleAdapter<DistributionMoneyLogModel>
{
	public MyDistributionMoneyLogAdapter(List<DistributionMoneyLogModel> listModel, Activity activity)
	{
		super(listModel, activity);
	}

	@Override
	public int getLayoutId(int position, View convertView, ViewGroup parent)
	{
		return R.layout.item_my_distribution_money_log;
	}

	@Override
	public void bindData(int position, View convertView, ViewGroup parent, DistributionMoneyLogModel model)
	{
		TextView tv_money = ViewHolder.get(R.id.tv_money, convertView);
		TextView tv_detail = ViewHolder.get(R.id.tv_detail, convertView);
		TextView tv_time = ViewHolder.get(R.id.tv_time, convertView);

		SDViewBinder.setTextView(tv_money, model.getMoney());
		SDViewBinder.setTextView(tv_detail, model.getLog());
		SDViewBinder.setTextView(tv_time, model.getCreate_time());
	}
}
