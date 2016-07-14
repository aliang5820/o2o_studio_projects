package com.fanwe.adapter;

import java.util.ArrayList;
import java.util.List;

import android.app.Activity;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.fanwe.library.adapter.SDSimpleAdapter;
import com.fanwe.library.common.SDSelectManager.Mode;
import com.fanwe.library.utils.SDViewBinder;
import com.fanwe.library.utils.SDViewUtil;
import com.fanwe.library.utils.ViewHolder;
import com.fanwe.model.OrderCoupon_listModel;
import com.fanwe.o2o.newo2o.R;

public class RefundCouponAdapter extends SDSimpleAdapter<OrderCoupon_listModel>
{
	public RefundCouponAdapter(List<OrderCoupon_listModel> listModel, Activity activity)
	{
		super(listModel, activity);
		getSelectManager().setMode(Mode.MULTI);
	}

	public List<String> getSelectedIds()
	{
		List<String> listIds = new ArrayList<String>();
		List<OrderCoupon_listModel> listModel = getSelectManager().getSelectedItems();
		for (OrderCoupon_listModel model : listModel)
		{
			if (model.getIs_refund() == 1 && model.isSelected())
			{
				listIds.add(String.valueOf(model.getId()));
			}
		}
		return listIds;
	}

	@Override
	public int getLayoutId(int position, View convertView, ViewGroup parent)
	{
		return R.layout.item_refund_coupon;
	}

	@Override
	public void bindData(final int position, View convertView, ViewGroup parent, final OrderCoupon_listModel model)
	{
		View view_div = ViewHolder.get(R.id.view_div, convertView);
		TextView tv_password = ViewHolder.get(R.id.tv_password, convertView);
		TextView tv_consume_number = ViewHolder.get(R.id.tv_consume_number, convertView);
		TextView tv_time = ViewHolder.get(R.id.tv_time, convertView);
		TextView tv_status = ViewHolder.get(R.id.tv_status, convertView);
		ImageView iv_selected = ViewHolder.get(R.id.iv_selected, convertView);

		if (position == 0)
		{
			SDViewUtil.hide(view_div);
		} else
		{
			SDViewUtil.show(view_div);
		}

		SDViewBinder.setTextView(tv_password, model.getPassword());
		SDViewBinder.setTextView(tv_consume_number, model.getConsumeString());
		SDViewBinder.setTextView(tv_time, model.getTime_str());
		SDViewBinder.setTextView(tv_status, model.getStatus_str());

		if (SDViewBinder.setViewsVisibility(iv_selected, model.getIs_refund()))
		{
			if (model.isSelected())
			{
				iv_selected.setImageResource(R.drawable.ic_payment_selected);
			} else
			{
				iv_selected.setImageResource(R.drawable.ic_payment_normal);
			}
		}

		convertView.setOnClickListener(new OnClickListener()
		{

			@Override
			public void onClick(View v)
			{
				getSelectManager().performClick(position);
			}
		});
	}

}
