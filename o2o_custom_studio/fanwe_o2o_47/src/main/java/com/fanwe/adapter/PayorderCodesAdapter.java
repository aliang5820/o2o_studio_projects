package com.fanwe.adapter;

import java.util.List;

import android.app.Activity;
import android.text.TextUtils;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.fanwe.library.adapter.SDSimpleAdapter;
import com.fanwe.library.utils.SDViewBinder;
import com.fanwe.library.utils.ViewHolder;
import com.fanwe.model.Payment_doneActCouponlistModel;
import com.fanwe.o2o.newo2o.R;

public class PayorderCodesAdapter extends SDSimpleAdapter<Payment_doneActCouponlistModel>
{
	public PayorderCodesAdapter(List<Payment_doneActCouponlistModel> listModel, Activity activity)
	{
		super(listModel, activity);
	}

	@Override
	public int getLayoutId(int position, View convertView, ViewGroup parent)
	{
		return R.layout.item_pay_order_code;
	}

	@Override
	public void bindData(int position, View convertView, ViewGroup parent, Payment_doneActCouponlistModel model)
	{
		ImageView ivCode = ViewHolder.get(R.id.item_pay_order_code_iv_code, convertView);
		TextView tvCode = ViewHolder.get(R.id.item_pay_order_code_tv_code, convertView);

		SDViewBinder.setImageView(model.getQrcode(), ivCode);
		if (!TextUtils.isEmpty(model.getPassword()))
		{
			tvCode.setText("验证码:" + model.getPassword());
		}
	}
}
