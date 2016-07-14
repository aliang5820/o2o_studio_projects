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
import com.fanwe.library.utils.ViewHolder;
import com.fanwe.model.Uc_eventActItemModel;
import com.fanwe.o2o.newo2o.R;

public class MyEventAdapter extends SDSimpleAdapter<Uc_eventActItemModel>
{

	public MyEventAdapter(List<Uc_eventActItemModel> listModel, Activity activity)
	{
		super(listModel, activity);
	}

	@Override
	public int getLayoutId(int position, View convertView, ViewGroup parent)
	{
		return R.layout.item_my_event_list;
	}

	@Override
	public void bindData(int position, View convertView, ViewGroup parent, final Uc_eventActItemModel model)
	{
		ImageView iv_qrcode = ViewHolder.get(R.id.iv_qrcode, convertView);
		TextView tv_sn_number = ViewHolder.get(R.id.tv_sn_number, convertView);
		TextView tv_name = ViewHolder.get(R.id.tv_name, convertView);
		TextView tv_expire_time = ViewHolder.get(R.id.tv_expire_time, convertView);

		SDViewBinder.setImageView(model.getQrcode(), iv_qrcode);
		SDViewBinder.setTextView(tv_sn_number, model.getEvent_sn());
		SDViewBinder.setTextView(tv_name, model.getName());
		SDViewBinder.setTextView(tv_expire_time, model.getEvent_end_time());

		convertView.setOnClickListener(new OnClickListener()
		{

			@Override
			public void onClick(View arg0)
			{
				Intent itemintent = new Intent(mActivity, EventDetailActivity.class);
				itemintent.putExtra(EventDetailActivity.EXTRA_EVENT_ID, model.getId());
				mActivity.startActivity(itemintent);
			}
		});
	}
}
