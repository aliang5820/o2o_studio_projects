package com.fanwe.adapter;

import java.util.List;

import android.app.Activity;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.TextView;

import com.fanwe.library.adapter.SDSimpleAdapter;
import com.fanwe.library.utils.SDViewBinder;
import com.fanwe.library.utils.ViewHolder;
import com.fanwe.model.UserfrequentedlistActItemModel;
import com.fanwe.o2o.newo2o.R;

public class FrequentedGoAdapter extends SDSimpleAdapter<UserfrequentedlistActItemModel>
{

	private SubscribeMessageAdapterListener mListener;

	public FrequentedGoAdapter(List<UserfrequentedlistActItemModel> listModel, Activity activity, SubscribeMessageAdapterListener listener)
	{
		super(listModel, activity);
		this.mListener = listener;
	}

	@Override
	public int getLayoutId(int position, View convertView, ViewGroup parent)
	{
		return R.layout.item_lv_frequented_go;
	}

	@Override
	public void bindData(int position, View convertView, ViewGroup parent, final UserfrequentedlistActItemModel model)
	{
		TextView tvTitle = ViewHolder.get(R.id.item_lv_frequented_go_tv_title, convertView);
		TextView tvAddress = ViewHolder.get(R.id.item_lv_frequented_go_tv_address, convertView);
		TextView tvDelete = ViewHolder.get(R.id.item_lv_frequented_go_tv_delete, convertView);

		SDViewBinder.setTextView(tvTitle, model.getTitle());
		SDViewBinder.setTextView(tvAddress, model.getAddr());

		tvDelete.setOnClickListener(new OnClickListener()
		{
			@Override
			public void onClick(View v)
			{
				if (mListener != null)
				{
					mListener.onDeleteClick(model);
				}
			}
		});
	}

	public interface SubscribeMessageAdapterListener
	{
		public void onDeleteClick(UserfrequentedlistActItemModel model);
	}
}
