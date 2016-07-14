package com.fanwe.adapter;

import java.util.List;

import android.app.Activity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.fanwe.library.adapter.SDSimpleAdapter;
import com.fanwe.library.utils.SDViewBinder;
import com.fanwe.library.utils.ViewHolder;
import com.fanwe.o2o.newo2o.R;

public class RouteStepDetaiAdapter extends SDSimpleAdapter<String>
{

	private int mSelectPos = 0;

	public RouteStepDetaiAdapter(List<String> listModel, Activity activity)
	{
		super(listModel, activity);
	}

	@Override
	public int getLayoutId(int position, View convertView, ViewGroup parent)
	{
		return R.layout.item_route_step;
	}

	@Override
	public void bindData(int position, View convertView, ViewGroup parent, String model)
	{
		TextView tvIndex = ViewHolder.get(R.id.item_route_step_tv_index, convertView);
		TextView tvContent = ViewHolder.get(R.id.item_route_step_tv_content, convertView);

		tvIndex.setText(String.valueOf(position + 1));
		SDViewBinder.setTextView(tvContent, getItem(position));
		if (position == mSelectPos)
		{
			convertView.setBackgroundResource(R.drawable.layer_gray);
		} else
		{
			convertView.setBackgroundResource(R.drawable.layer_white);
		}
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent)
	{
		if (convertView == null)
		{
			convertView = mInflater.inflate(R.layout.item_route_step, null);
		}

		return convertView;
	}

	public void setSelectPos(int pos)
	{
		this.mSelectPos = pos;
		notifyDataSetChanged();
	}

}
