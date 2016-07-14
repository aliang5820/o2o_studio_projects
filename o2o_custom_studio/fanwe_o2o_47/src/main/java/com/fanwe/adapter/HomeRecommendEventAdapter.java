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
import com.fanwe.app.App;
import com.fanwe.library.adapter.SDSimpleAdapter;
import com.fanwe.library.utils.SDViewBinder;
import com.fanwe.library.utils.ViewHolder;
import com.fanwe.model.EventModel;
import com.fanwe.o2o.newo2o.R;

public class HomeRecommendEventAdapter extends SDSimpleAdapter<EventModel>
{
	public HomeRecommendEventAdapter(List<EventModel> listModel, Activity activity)
	{
		super(listModel, activity);
	}

	@Override
	public int getLayoutId(int position, View convertView, ViewGroup parent)
	{
		return R.layout.item_home_recommend_event;
	}

	@Override
	public void bindData(int position, View convertView, ViewGroup parent, final EventModel model)
	{
		ImageView ivImage = ViewHolder.get(R.id.item_home_recommend_event_iv_image, convertView);
		TextView tvLeftTime = ViewHolder.get(R.id.item_home_recommend_event_tv_left_time, convertView);

		SDViewBinder.setImageView(model.getIcon(), ivImage);
		SDViewBinder.setTextView(tvLeftTime, model.getSheng_time_format());
		convertView.setOnClickListener(new OnClickListener()
		{
			@Override
			public void onClick(View v)
			{
				Intent intent = new Intent(App.getApplication(), EventDetailActivity.class);
				intent.putExtra(EventDetailActivity.EXTRA_EVENT_ID, model.getId());
				mActivity.startActivity(intent);
			}
		});
	}

}