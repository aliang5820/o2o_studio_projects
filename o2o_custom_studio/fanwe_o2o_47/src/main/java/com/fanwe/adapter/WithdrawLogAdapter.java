package com.fanwe.adapter;

import java.util.List;

import android.app.Activity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.fanwe.library.adapter.SDSimpleAdapter;
import com.fanwe.library.utils.SDViewBinder;
import com.fanwe.model.WithdrawLogModel;
import com.fanwe.o2o.newo2o.R;

public class WithdrawLogAdapter extends SDSimpleAdapter<WithdrawLogModel>
{

	public WithdrawLogAdapter(List<WithdrawLogModel> listModel, Activity activity)
	{
		super(listModel, activity);
	}

	@Override
	public int getLayoutId(int position, View convertView, ViewGroup parent)
	{
		return R.layout.item_withdraw_log;
	}

	@Override
	public void bindData(int position, View convertView, ViewGroup parent, WithdrawLogModel model)
	{
		TextView tv_bank = get(R.id.tv_bank, convertView);
		TextView tv_time = get(R.id.tv_time, convertView);
		TextView tv_money = get(R.id.tv_money, convertView);

		SDViewBinder.setTextView(tv_bank, model.getBank_name());
		SDViewBinder.setTextView(tv_time, model.getCreate_time());
		SDViewBinder.setTextView(tv_money, model.getMoneyFormat());
	}

}
