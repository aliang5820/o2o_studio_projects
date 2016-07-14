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
import com.fanwe.model.StoreModel;
import com.fanwe.o2o.newo2o.R;

public class MerchantListOrderAdapter extends SDSimpleAdapter<StoreModel>
{

	public MerchantListOrderAdapter(List<StoreModel> listModel, Activity activity)
	{
		super(listModel, activity);
	}

	@Override
	public int getLayoutId(int position, View convertView, ViewGroup parent)
	{
		return R.layout.item_lv_merchant_list_order;
	}

	@Override
	public void bindData(int position, View convertView, ViewGroup parent, StoreModel model)
	{
		ImageView ivLogo = ViewHolder.get(R.id.item_lv_merchant_list_order_iv_logo, convertView);
		TextView tvName = ViewHolder.get(R.id.item_lv_merchant_list_order_tv_name, convertView);
		TextView tvbrief = ViewHolder.get(R.id.item_lv_merchant_list_order_tv_brief, convertView);
		TextView tvAddress = ViewHolder.get(R.id.item_lv_merchant_list_order_tv_address, convertView);
		TextView tvDistance = ViewHolder.get(R.id.item_lv_merchant_list_order_tv_distance, convertView);

		SDViewBinder.setImageView(model.getPreview(), ivLogo);
		SDViewBinder.setTextView(tvName, model.getName());
		// SDViewBinder.setTextView(tvbrief, model.getBrief());
		SDViewBinder.setTextView(tvAddress, model.getAddress());
		SDViewBinder.setTextView(tvDistance, model.getDistanceFormat());
	}

}
