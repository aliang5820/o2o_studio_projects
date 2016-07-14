package com.fanwe.adapter;

import java.util.List;

import android.app.Activity;
import android.content.Intent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.fanwe.YouHuiDetailActivity;
import com.fanwe.library.adapter.SDSimpleAdapter;
import com.fanwe.library.utils.SDViewBinder;
import com.fanwe.model.YouhuiModel;
import com.fanwe.o2o.newo2o.R;

public class YouHuiListAdapter extends SDSimpleAdapter<YouhuiModel>
{

	public YouHuiListAdapter(List<YouhuiModel> listModel, Activity activity)
	{
		super(listModel, activity);
	}

	@Override
	public int getLayoutId(int position, View convertView, ViewGroup parent)
	{
		return R.layout.item_youhui_list;
	}

	@Override
	public void bindData(int position, View convertView, ViewGroup parent, final YouhuiModel model)
	{
		ImageView iv_image = get(R.id.iv_image, convertView);
		TextView tv_name = get(R.id.tv_name, convertView);
		TextView tv_tip = get(R.id.tv_tip, convertView);
		TextView tv_address = get(R.id.tv_address, convertView);
		TextView tv_publish_time = get(R.id.tv_publish_time, convertView);
		TextView tv_distance = get(R.id.tv_distance, convertView);

		SDViewBinder.setImageView(model.getIcon(), iv_image);
		SDViewBinder.setTextView(tv_name, model.getName());
		SDViewBinder.setTextView(tv_tip, model.getList_brief());
		SDViewBinder.setTextView(tv_address, String.valueOf(model.getDown_count()));
		SDViewBinder.setTextView(tv_publish_time, model.getBegin_time());
		SDViewBinder.setTextView(tv_distance, model.getDistanceFormat());

		convertView.setOnClickListener(new OnClickListener()
		{

			@Override
			public void onClick(View v)
			{
				Intent intent = new Intent(mActivity, YouHuiDetailActivity.class);
				intent.putExtra(YouHuiDetailActivity.EXTRA_YOUHUI_ID, model.getId());
				mActivity.startActivity(intent);
			}
		});
	}

}
