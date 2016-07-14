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
import com.fanwe.model.Jygl_couponListModel;

public class Jygl_coupon_listAdapter extends SDSimpleAdapter<Jygl_couponListModel>
{

	public Jygl_coupon_listAdapter(List<Jygl_couponListModel> listModel, Activity activity)
	{
		super(listModel, activity);
	}

	@Override
	public int getLayoutId(int position, View convertView, ViewGroup parent)
	{
		return R.layout.item_jygl_coupon;
	}

	@Override
	public void bindData(int position, View convertView, ViewGroup parent, Jygl_couponListModel model)
	{
		ImageView item_jygl_coupon_icon = ViewHolder.get(R.id.item_jygl_coupon_icon, convertView);
		TextView item_jygl_coupon_name = ViewHolder.get(R.id.item_jygl_coupon_name, convertView);
		TextView item_jygl_coupon_f_end_time = ViewHolder.get(R.id.item_jygl_coupon_f_end_time, convertView);
		TextView item_jygl_coupon_use_count = ViewHolder.get(R.id.item_jygl_coupon_use_count, convertView);
		TextView item_jygl_coupon_user_count = ViewHolder.get(R.id.item_jygl_coupon_user_count, convertView);

		SDViewBinder.setImageView(model.getIcon(), item_jygl_coupon_icon);
		SDViewBinder.setTextView(item_jygl_coupon_name, model.getName());
		SDViewBinder.setTextView(item_jygl_coupon_f_end_time, model.getF_end_time());
		SDViewBinder.setTextView(item_jygl_coupon_use_count, model.getUse_count());
		SDViewBinder.setTextView(item_jygl_coupon_user_count, model.getUser_count());
	}
}
