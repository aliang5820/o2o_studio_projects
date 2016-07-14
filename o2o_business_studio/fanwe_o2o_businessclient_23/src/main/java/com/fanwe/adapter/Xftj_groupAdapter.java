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
import com.fanwe.model.Xftj_groupItemModel;

public class Xftj_groupAdapter extends SDSimpleAdapter<Xftj_groupItemModel>
{

	public Xftj_groupAdapter(List<Xftj_groupItemModel> listModel, Activity activity)
	{
		super(listModel, activity);
	}

	@Override
	public int getLayoutId(int position, View convertView, ViewGroup parent)
	{
		return R.layout.item_xftj_group;
	}

	@Override
	public void bindData(int position, View convertView, ViewGroup parent, Xftj_groupItemModel model)
	{
		TextView tv_xftj_group_sn = ViewHolder.get(R.id.item_xftj_group_sn, convertView);
		TextView tv_xftj_group_f_confirm_time = ViewHolder.get(R.id.item_xftj_group_f_confirm_time, convertView);
		TextView tv_xftj_group_user_name = ViewHolder.get(R.id.item_xftj_group_user_name, convertView);

		SDViewBinder.setTextView(tv_xftj_group_sn, model.getSn());
		SDViewBinder.setTextView(tv_xftj_group_f_confirm_time, model.getF_confirm_time());
		SDViewBinder.setTextView(tv_xftj_group_user_name, model.getUser_name());
	}

}
