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
import com.fanwe.model.Xftj_store_itemModel;

public class Xftj_storeItemAdapter extends SDSimpleAdapter<Xftj_store_itemModel>
{
	public Xftj_storeItemAdapter(List<Xftj_store_itemModel> listModel, Activity activity)
	{
		super(listModel, activity);
	}

	@Override
	public int getLayoutId(int position, View convertView, ViewGroup parent)
	{
		return R.layout.item_xftj_store;
	}

	@Override
	public void bindData(int position, View convertView, ViewGroup parent, Xftj_store_itemModel model)
	{
		TextView item_xftj_store_name = ViewHolder.get(R.id.item_xftj_store_name, convertView);
		TextView item_xftj_store_use_count = ViewHolder.get(R.id.item_xftj_store_use_count, convertView);

		SDViewBinder.setTextView(item_xftj_store_name, model.getName());
		SDViewBinder.setTextView(item_xftj_store_use_count, model.getUse_count());
	}
}
