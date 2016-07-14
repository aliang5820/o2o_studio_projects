package com.fanwe.adapter;

import java.util.List;

import android.app.Activity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.fanwe.library.adapter.SDSimpleAdapter;
import com.fanwe.library.utils.SDViewBinder;
import com.fanwe.library.utils.SDViewUtil;
import com.fanwe.library.utils.ViewHolder;
import com.fanwe.model.DiscoveryTagModel;
import com.fanwe.o2o.newo2o.R;

public class DiscoveryTagAdapter extends SDSimpleAdapter<DiscoveryTagModel>
{

	public DiscoveryTagAdapter(List<DiscoveryTagModel> listModel, Activity activity)
	{
		super(listModel, activity);
	}

	@Override
	public int getLayoutId(int position, View convertView, ViewGroup parent)
	{
		return R.layout.item_discovery_tag;
	}

	@Override
	public void bindData(int position, View convertView, ViewGroup parent, DiscoveryTagModel model)
	{
		TextView tv_name = ViewHolder.get(R.id.tv_name, convertView);
		View view_bot = ViewHolder.get(R.id.view_bot, convertView);

		SDViewBinder.setTextView(tv_name, model.getName());

		if (model.isSelected())
		{
			SDViewUtil.setTextViewColorResId(tv_name, R.color.main_color);
			SDViewUtil.setBackgroundColorResId(view_bot, R.color.main_color);
		} else
		{
			SDViewUtil.setTextViewColorResId(tv_name, R.color.gray);
			SDViewUtil.setBackgroundColorResId(view_bot, R.color.transparent);
		}
	}

}
