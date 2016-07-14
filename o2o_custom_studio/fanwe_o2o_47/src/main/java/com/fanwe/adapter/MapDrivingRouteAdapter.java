package com.fanwe.adapter;

import java.util.List;

import android.app.Activity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.fanwe.library.adapter.SDSimpleAdapter;
import com.fanwe.library.utils.SDViewBinder;
import com.fanwe.library.utils.ViewHolder;
import com.fanwe.model.MapDrivingRouteModel;
import com.fanwe.o2o.newo2o.R;

public class MapDrivingRouteAdapter extends SDSimpleAdapter<MapDrivingRouteModel>
{

	public MapDrivingRouteAdapter(List<MapDrivingRouteModel> listModel, Activity activity)
	{
		super(listModel, activity);
	}

	@Override
	public int getLayoutId(int position, View convertView, ViewGroup parent)
	{
		return R.layout.item_routeinfo;
	}

	@Override
	public void bindData(int position, View convertView, ViewGroup parent, MapDrivingRouteModel model)
	{
		TextView tvIndex = ViewHolder.get(R.id.item_routeinfo_tv_index, convertView);
		TextView tvName = ViewHolder.get(R.id.item_routeinfo_tv_name, convertView);
		TextView tvTime = ViewHolder.get(R.id.item_routeinfo_tv_time, convertView);
		TextView tvDistance = ViewHolder.get(R.id.item_routeinfo_tv_distance, convertView);

		tvIndex.setText(String.valueOf(position + 1));
		SDViewBinder.setTextView(tvName, model.getName());
		SDViewBinder.setTextView(tvTime, model.getTime());
		SDViewBinder.setTextView(tvDistance, model.getDistance());
	}

}
