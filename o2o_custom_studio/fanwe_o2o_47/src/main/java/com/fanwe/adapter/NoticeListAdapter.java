package com.fanwe.adapter;

import java.util.List;

import android.app.Activity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.fanwe.library.adapter.SDSimpleAdapter;
import com.fanwe.library.utils.SDViewBinder;
import com.fanwe.library.utils.ViewHolder;
import com.fanwe.model.NoticeModel;
import com.fanwe.o2o.newo2o.R;

public class NoticeListAdapter extends SDSimpleAdapter<NoticeModel>
{

	public NoticeListAdapter(List<NoticeModel> listModel, Activity activity)
	{
		super(listModel, activity);
	}

	@Override
	public int getLayoutId(int position, View convertView, ViewGroup parent)
	{
		return R.layout.item_notice_list;
	}

	@Override
	public void bindData(int position, View convertView, ViewGroup parent, NoticeModel model)
	{
		TextView tv_title = ViewHolder.get(R.id.tv_title, convertView);
		SDViewBinder.setTextView(tv_title, model.getName());
	}
}
