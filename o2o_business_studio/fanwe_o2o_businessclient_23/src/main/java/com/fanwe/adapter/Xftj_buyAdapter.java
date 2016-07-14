package com.fanwe.adapter;

import java.util.List;

import android.app.Activity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.fanwe.businessclient.R;
import com.fanwe.library.adapter.SDSimpleAdapter;
import com.fanwe.library.utils.SDViewBinder;
import com.fanwe.library.utils.ViewHolder;
import com.fanwe.model.Xftj_buyModel;

public class Xftj_buyAdapter extends SDSimpleAdapter<Xftj_buyModel>
{

	public Xftj_buyAdapter(List<Xftj_buyModel> listModel, Activity activity)
	{
		super(listModel, activity);
	}

	@Override
	public int getLayoutId(int position, View convertView, ViewGroup parent)
	{
		return R.layout.item_xftj_buy;
	}

	@Override
	public void bindData(int position, View convertView, ViewGroup parent, Xftj_buyModel model)
	{
		ImageView iv_xfty_buy_icon = ViewHolder.get(R.id.iv_xfty_buy_icon, convertView);
		TextView tv_xfty_buy_sub_name = ViewHolder.get(R.id.tv_xfty_buy_sub_name, convertView);
		TextView tv_xfty_buy_f_create_time = ViewHolder.get(R.id.tv_xfty_buy_f_create_time, convertView);
		TextView tv_xfty_buy_buy_count = ViewHolder.get(R.id.tv_xfty_buy_buy_count, convertView);
		TextView tv_xfty_buy_confirm_count = ViewHolder.get(R.id.tv_xfty_buy_confirm_count, convertView);
		TextView tv_xfty_buy_refund_count = ViewHolder.get(R.id.tv_xfty_buy_refund_count, convertView);

		SDViewBinder.setImageView(model.getIcon(), iv_xfty_buy_icon);
		SDViewBinder.setTextView(tv_xfty_buy_sub_name, model.getSub_name());
		SDViewBinder.setTextView(tv_xfty_buy_f_create_time, model.getF_create_time());
		SDViewBinder.setTextView(tv_xfty_buy_buy_count, model.getBuy_count());
		SDViewBinder.setTextView(tv_xfty_buy_confirm_count, model.getConfirm_count());
		SDViewBinder.setTextView(tv_xfty_buy_refund_count, model.getRefund_count());
	}
}
