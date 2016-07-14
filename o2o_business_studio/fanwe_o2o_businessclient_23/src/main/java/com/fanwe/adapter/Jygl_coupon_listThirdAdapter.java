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
import com.fanwe.model.Jygl_couponYouhuisModel;

public class Jygl_coupon_listThirdAdapter extends SDSimpleAdapter<Jygl_couponYouhuisModel>
{

	public Jygl_coupon_listThirdAdapter(List<Jygl_couponYouhuisModel> listModel, Activity activity)
	{
		super(listModel, activity);
	}

	@Override
	public int getLayoutId(int position, View convertView, ViewGroup parent)
	{
		return R.layout.item_jygl_coupon_list_youhuis;
	}

	@Override
	public void bindData(int position, View convertView, ViewGroup parent, Jygl_couponYouhuisModel model)
	{
		TextView tv_youhuis_youhui_sn = ViewHolder.get(R.id.item_jygl_coupon_list_youhuis_youhui_sn, convertView);
		TextView tv_youhuis_f_confirm_time = ViewHolder.get(R.id.item_jygl_coupon_list_youhuis_f_confirm_time, convertView);
		TextView tv_youhuis_user_name = ViewHolder.get(R.id.item_jygl_coupon_list_youhuis_user_name, convertView);

		SDViewBinder.setTextView(tv_youhuis_youhui_sn, model.getYouhui_sn());
		SDViewBinder.setTextView(tv_youhuis_f_confirm_time, model.getF_confirm_time());
		SDViewBinder.setTextView(tv_youhuis_user_name, model.getUser_name());
	}

}
