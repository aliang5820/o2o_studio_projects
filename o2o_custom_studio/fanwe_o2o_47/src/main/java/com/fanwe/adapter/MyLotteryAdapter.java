package com.fanwe.adapter;

import java.util.List;

import android.app.Activity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.fanwe.library.adapter.SDSimpleAdapter;
import com.fanwe.library.utils.SDViewBinder;
import com.fanwe.library.utils.ViewHolder;
import com.fanwe.model.Uc_lotteryActItemModel;
import com.fanwe.o2o.newo2o.R;

public class MyLotteryAdapter extends SDSimpleAdapter<Uc_lotteryActItemModel>
{
	public MyLotteryAdapter(List<Uc_lotteryActItemModel> listModel, Activity activity)
	{
		super(listModel, activity);
	}

	@Override
	public int getLayoutId(int position, View convertView, ViewGroup parent)
	{
		return R.layout.item_my_lottery;
	}

	@Override
	public void bindData(int position, View convertView, ViewGroup parent, Uc_lotteryActItemModel model)
	{

		ImageView iv_image = ViewHolder.get(R.id.iv_image, convertView);
		TextView tv_name = ViewHolder.get(R.id.tv_name, convertView);
		TextView tv_sn_number = ViewHolder.get(R.id.tv_sn_number, convertView);
		TextView tv_create_time = ViewHolder.get(R.id.tv_create_time, convertView);

		SDViewBinder.setImageView(model.getIcon(), iv_image);
		SDViewBinder.setTextView(tv_name, model.getName());
		SDViewBinder.setTextView(tv_sn_number, model.getLottery_sn());
		SDViewBinder.setTextView(tv_create_time, model.getCreate_time());
	}
}
