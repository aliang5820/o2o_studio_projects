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
import com.fanwe.model.Xftj_commodityItemModel;

public class Xftj_commodityItemAdapter extends SDSimpleAdapter<Xftj_commodityItemModel>
{

	public Xftj_commodityItemAdapter(List<Xftj_commodityItemModel> listModel, Activity activity)
	{
		super(listModel, activity);
	}

	@Override
	public int getLayoutId(int position, View convertView, ViewGroup parent)
	{
		return R.layout.item_xftj_commodity;
	}

	@Override
	public void bindData(int position, View convertView, ViewGroup parent, Xftj_commodityItemModel model)
	{
		ImageView iv_xftj_commodity_icon = ViewHolder.get(R.id.iv_xftj_commodity_icon, convertView);
		TextView tv_xftj_commodity_sub_name = ViewHolder.get(R.id.tv_xftj_commodity_sub_name, convertView);
		TextView tv_xftj_commodity_f_create_time = ViewHolder.get(R.id.tv_xftj_commodity_f_create_time, convertView);
		TextView tv_xftj_commodity_buy_count = ViewHolder.get(R.id.tv_xftj_commodity_buy_count, convertView);
		TextView tv_xftj_commodity_refund_count = ViewHolder.get(R.id.tv_xftj_commodity_refund_count, convertView);

		SDViewBinder.setImageView(model.getIcon(), iv_xftj_commodity_icon);
		SDViewBinder.setTextView(tv_xftj_commodity_sub_name, model.getSub_name());
		SDViewBinder.setTextView(tv_xftj_commodity_f_create_time, model.getF_create_time());
		SDViewBinder.setTextView(tv_xftj_commodity_buy_count, model.getBuy_count());
		SDViewBinder.setTextView(tv_xftj_commodity_refund_count, model.getRefund_count());
	}

}
