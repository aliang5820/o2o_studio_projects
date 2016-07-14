package com.fanwe.adapter;

import java.util.List;

import android.app.Activity;
import android.content.Intent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.fanwe.EventDetailActivity;
import com.fanwe.library.adapter.SDSimpleAdapter;
import com.fanwe.library.utils.SDViewBinder;
import com.fanwe.model.EventModel;
import com.fanwe.o2o.newo2o.R;

public class EventListAdapter extends SDSimpleAdapter<EventModel>
{

	public EventListAdapter(List<EventModel> listModel, Activity activity)
	{
		super(listModel, activity);
	}

	@Override
	public int getLayoutId(int position, View convertView, ViewGroup parent)
	{
		return R.layout.item_event_list;
	}

	@Override
	public void bindData(int position, View convertView, ViewGroup parent, final EventModel model)
	{
		ImageView iv_image = get(R.id.iv_image, convertView);
		TextView tv_name = get(R.id.tv_name, convertView);
		TextView tv_distance = get(R.id.tv_distance, convertView);
		TextView tv_left_time = get(R.id.tv_left_time, convertView);
		TextView tv_apply_count = get(R.id.tv_apply_count, convertView);

		SDViewBinder.setImageView(model.getIcon(), iv_image);
		SDViewBinder.setTextView(tv_name, model.getName());
		SDViewBinder.setTextView(tv_apply_count, model.getSubmit_countFormat());
		SDViewBinder.setTextView(tv_left_time, model.getSheng_time_format());
		SDViewBinder.setTextView(tv_distance, model.getDistanceFormat());

		convertView.setOnClickListener(new OnClickListener()
		{

			@Override
			public void onClick(View v)
			{
				Intent itemintent = new Intent();
				itemintent.putExtra(EventDetailActivity.EXTRA_EVENT_ID, model.getId());
				itemintent.setClass(mActivity, EventDetailActivity.class);
				mActivity.startActivity(itemintent);
			}
		});
	}

}
