package com.fanwe.adapter;

import java.util.List;

import android.app.Activity;
import android.content.Intent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.fanwe.library.adapter.SDSimpleAdapter;
import com.fanwe.library.utils.SDActivityUtil;
import com.fanwe.library.utils.SDViewBinder;
import com.fanwe.library.utils.SDViewUtil;
import com.fanwe.library.utils.ViewHolder;
import com.fanwe.model.IndexActIndexsModel;
import com.fanwe.o2o.newo2o.R;
import com.fanwe.work.AppRuntimeWorker;

public class HomeIndexAdapter extends SDSimpleAdapter<IndexActIndexsModel>
{

	public HomeIndexAdapter(List<IndexActIndexsModel> listModel, Activity activity)
	{
		super(listModel, activity);
	}

	@Override
	public int getLayoutId(int position, View convertView, ViewGroup parent)
	{
		return R.layout.item_home_index;
	}

	@Override
	public void bindData(int position, View convertView, ViewGroup parent, final IndexActIndexsModel model)
	{
		ImageView ivImage = ViewHolder.get(R.id.item_home_index_iv_image, convertView);
		TextView tvName = ViewHolder.get(R.id.item_home_index_tv_name, convertView);

		SDViewUtil.setViewWidth(ivImage, SDViewUtil.getScreenWidth() / 9);
		SDViewUtil.setViewHeight(ivImage, SDViewUtil.getScreenWidth() / 9);

		SDViewBinder.setTextView(tvName, model.getName());
		SDViewBinder.setImageView(model.getImg(), ivImage);

		convertView.setOnClickListener(new OnClickListener()
		{

			@Override
			public void onClick(View v)
			{
				int type = model.getType();
				Intent intent = AppRuntimeWorker.createIntentByType(type, model.getData(), true);
				SDActivityUtil.startActivity(mActivity, intent);
			}
		});
	}

}