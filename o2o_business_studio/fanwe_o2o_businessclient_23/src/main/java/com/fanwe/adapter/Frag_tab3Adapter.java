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
import com.fanwe.model.Frag_tab3_itemModel;

public class Frag_tab3Adapter extends SDSimpleAdapter<Frag_tab3_itemModel>
{

	public Frag_tab3Adapter(List<Frag_tab3_itemModel> listModel, Activity activity)
	{
		super(listModel, activity);
	}

	@Override
	public int getLayoutId(int position, View convertView, ViewGroup parent)
	{
		return R.layout.item_frag_tab_3;
	}

	@Override
	public void bindData(int position, View convertView, ViewGroup parent, Frag_tab3_itemModel model)
	{
		TextView item_frag_tab3_f_create_time = ViewHolder.get(R.id.item_frag_tab3_f_create_time, convertView);
		TextView item_frag_tab3_money = ViewHolder.get(R.id.item_frag_tab3_money, convertView);
		TextView item_frag_tab3_status = ViewHolder.get(R.id.item_frag_tab3_status, convertView);

		SDViewBinder.setTextView(item_frag_tab3_f_create_time, model.getF_create_time());
		SDViewBinder.setTextView(item_frag_tab3_money, model.getMoney());
		SDViewBinder.setTextView(item_frag_tab3_status, model.getStatus());
	}

}
