package com.fanwe.adapter;

import java.util.List;

import android.app.Activity;
import android.content.Intent;
import android.text.TextUtils;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.fanwe.StoreOrderDetailActivity;
import com.fanwe.library.adapter.SDSimpleAdapter;
import com.fanwe.library.utils.SDViewBinder;
import com.fanwe.library.utils.SDViewUtil;
import com.fanwe.model.StoreOrderModel;
import com.fanwe.o2o.newo2o.R;

public class StoreOrderListAdapter extends SDSimpleAdapter<StoreOrderModel>
{

	public StoreOrderListAdapter(List<StoreOrderModel> listModel, Activity activity)
	{
		super(listModel, activity);
	}

	@Override
	public int getLayoutId(int position, View convertView, ViewGroup parent)
	{
		return R.layout.item_store_order_list;
	}

	@Override
	public void bindData(int position, View convertView, ViewGroup parent, final StoreOrderModel model)
	{
		TextView tv_order_sn = get(R.id.tv_order_sn, convertView);
		TextView tv_order_fee_info = get(R.id.tv_order_fee_info, convertView);
		TextView tv_order_youhui_info = get(R.id.tv_order_youhui_info, convertView);
		LinearLayout ll_youhui_info = get(R.id.ll_youhui_info, convertView);
		TextView tv_order_status = get(R.id.tv_order_status, convertView);
		TextView tv_create_time = get(R.id.tv_create_time, convertView);
		TextView tv_store_name = get(R.id.tv_store_name, convertView);
		LinearLayout ll_pay = get(R.id.ll_pay, convertView);

		SDViewBinder.setTextView(tv_order_sn, model.getOrder_sn());
		SDViewBinder.setTextView(tv_order_fee_info, model.getFeeInfo());

		String youhuiInfo = model.getYouhuiInfo();
		if (TextUtils.isEmpty(youhuiInfo))
		{
			SDViewUtil.hide(ll_youhui_info);
		} else
		{
			SDViewUtil.show(ll_youhui_info);
			SDViewBinder.setTextView(tv_order_youhui_info, youhuiInfo);
		}

		SDViewBinder.setTextView(tv_order_status, model.getStatus());
		SDViewBinder.setTextView(tv_create_time, model.getCreate_time());
		SDViewBinder.setTextView(tv_store_name, model.getLocation_name());

		if (model.getPay_status() == 0)
		{
			SDViewUtil.show(ll_pay);
			ll_pay.setOnClickListener(new OnClickListener()
			{

				@Override
				public void onClick(View v)
				{
					Intent intent = new Intent(mActivity, StoreOrderDetailActivity.class);
					intent.putExtra(StoreOrderDetailActivity.EXTRA_ORDER_ID, model.getId());
					mActivity.startActivity(intent);
				}
			});
		} else
		{
			SDViewUtil.hide(ll_pay);
			ll_pay.setOnClickListener(null);
		}
	}
}
