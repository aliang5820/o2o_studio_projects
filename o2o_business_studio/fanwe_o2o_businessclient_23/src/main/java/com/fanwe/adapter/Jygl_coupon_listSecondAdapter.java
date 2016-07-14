package com.fanwe.adapter;

import java.util.List;

import android.app.Activity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.fanwe.businessclient.R;
import com.fanwe.library.adapter.SDSimpleAdapter;
import com.fanwe.library.utils.SDViewBinder;
import com.fanwe.library.utils.ViewHolder;
import com.fanwe.model.Jygl_couponLocationsModel;

public class Jygl_coupon_listSecondAdapter extends SDSimpleAdapter<Jygl_couponLocationsModel>
{

	public Jygl_coupon_listSecondAdapter(List<Jygl_couponLocationsModel> listModel, Activity activity)
	{
		super(listModel, activity);
	}

	@Override
	public int getLayoutId(int position, View convertView, ViewGroup parent)
	{
		return R.layout.item_jygl_coupon_list_location;
	}

	@Override
	public void bindData(int position, View convertView, ViewGroup parent, Jygl_couponLocationsModel model)
	{
		TextView tv_list_location_name = ViewHolder.get(R.id.item_jygl_coupon_list_location_name, convertView);
		TextView tv_list_location_use_count = ViewHolder.get(R.id.item_jygl_coupon_list_location_use_count, convertView);

		SDViewBinder.setTextView(tv_list_location_name, model.getName());
		SDViewBinder.setTextView(tv_list_location_use_count, model.getUse_count());
	}

}
