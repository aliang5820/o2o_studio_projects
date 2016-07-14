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
import com.fanwe.model.Consignee_infoModel;
import com.fanwe.o2o.newo2o.R;

public class DeliveryAddressAdapter extends SDSimpleAdapter<Consignee_infoModel>
{

	private boolean mIsManage;

	public DeliveryAddressAdapter(List<Consignee_infoModel> listModel, boolean isManage, Activity activity)
	{
		super(listModel, activity);
		this.mIsManage = isManage;
	}

	@Override
	public int getLayoutId(int position, View convertView, ViewGroup parent)
	{
		return R.layout.item_lv_delivery_address;
	}

	@Override
	public void bindData(int position, View convertView, ViewGroup parent, Consignee_infoModel model)
	{
		TextView tv_default = ViewHolder.get(R.id.tv_default, convertView);
		TextView tv_name = ViewHolder.get(R.id.tv_name, convertView);
		TextView tv_mobile = ViewHolder.get(R.id.tv_mobile, convertView);
		TextView tv_address = ViewHolder.get(R.id.tv_address, convertView);
		ImageView iv_arrow_right = ViewHolder.get(R.id.iv_arrow_right, convertView);

		if (mIsManage)
		{
			iv_arrow_right.setVisibility(View.VISIBLE);
		} else
		{
			iv_arrow_right.setVisibility(View.GONE);
		}
		SDViewBinder.setTextView(tv_name, model.getConsignee()); // 名字
		SDViewBinder.setTextView(tv_mobile, model.getMobile()); // 电话
		SDViewBinder.setTextView(tv_address, model.getAddressLong()); // 地址
		SDViewBinder.setViewsVisibility(tv_default, model.getIs_default());
	}
}
