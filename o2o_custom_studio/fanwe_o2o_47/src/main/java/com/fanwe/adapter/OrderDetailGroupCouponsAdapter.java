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
import com.fanwe.model.OrderDetailCoupon_listModel;
import com.fanwe.o2o.newo2o.R;

public class OrderDetailGroupCouponsAdapter extends SDSimpleAdapter<OrderDetailCoupon_listModel>
{
	public OrderDetailGroupCouponsAdapter(List<OrderDetailCoupon_listModel> listModel, Activity activity)
	{
		super(listModel, activity);
	}

	@Override
	public int getLayoutId(int position, View convertView, ViewGroup parent)
	{
		return R.layout.item_order_detail_group_coupons;
	}

	@Override
	public void bindData(int position, View convertView, ViewGroup parent, OrderDetailCoupon_listModel model)
	{
		View viewDiv = ViewHolder.get(R.id.item_order_detail_group_coupons_view_div, convertView);
		TextView tvPassword = ViewHolder.get(R.id.item_order_detail_group_coupons_tv_password, convertView);
		TextView tvStatus = ViewHolder.get(R.id.item_order_detail_group_coupons_tv_status, convertView);

		if (position == 0)
		{
			SDViewUtil.hide(viewDiv);
		} else
		{
			SDViewUtil.show(viewDiv);
		}
		SDViewBinder.setTextView(tvPassword, model.getPassword(), "未找到");
		SDViewBinder.setTextView(tvStatus, model.getStatus_format(), "未找到");
	}
}