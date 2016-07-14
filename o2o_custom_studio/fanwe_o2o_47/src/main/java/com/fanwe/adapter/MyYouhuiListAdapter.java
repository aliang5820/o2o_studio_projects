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
import com.fanwe.model.Uc_youhuiActItemModel;
import com.fanwe.o2o.newo2o.R;

public class MyYouhuiListAdapter extends SDSimpleAdapter<Uc_youhuiActItemModel>
{

	public MyYouhuiListAdapter(List<Uc_youhuiActItemModel> listModel, Activity activity)
	{
		super(listModel, activity);
	}

	@Override
	public int getLayoutId(int position, View convertView, ViewGroup parent)
	{
		return R.layout.item_my_youhui_list;
	}

	@Override
	public void bindData(int position, View convertView, ViewGroup parent, Uc_youhuiActItemModel model)
	{
		ImageView iv_qrcode = ViewHolder.get(R.id.iv_qrcode, convertView);
		TextView tv_sn_number = ViewHolder.get(R.id.tv_sn_number, convertView);
		TextView tv_name = ViewHolder.get(R.id.tv_name, convertView);
		TextView tv_expire_time = ViewHolder.get(R.id.tv_expire_time, convertView);

		SDViewBinder.setImageView(model.getQrcode(), iv_qrcode);
		SDViewBinder.setTextView(tv_sn_number, model.getYouhui_sn());
		SDViewBinder.setTextView(tv_name, model.getName());
		SDViewBinder.setTextView(tv_expire_time, model.getExpire_time());
	}

}
