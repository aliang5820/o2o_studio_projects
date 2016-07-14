package com.fanwe.adapter;

import java.util.List;

import android.app.Activity;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.fanwe.common.ImageLoaderManager;
import com.fanwe.library.adapter.SDSimpleAdapter;
import com.fanwe.library.utils.SDViewBinder;
import com.fanwe.library.utils.SDViewUtil;
import com.fanwe.model.DistributionMarketCateModel;
import com.fanwe.o2o.newo2o.R;

public class DistributionMarketCateAdapter extends SDSimpleAdapter<DistributionMarketCateModel>
{

	public DistributionMarketCateAdapter(List<DistributionMarketCateModel> listModel, Activity activity)
	{
		super(listModel, activity);
	}

	@Override
	public int getLayoutId(int position, View convertView, ViewGroup parent)
	{
		return R.layout.item_home_index;
	}

	@Override
	public void bindData(int position, View convertView, ViewGroup parent, DistributionMarketCateModel model)
	{
		ImageView ivImage = get(R.id.item_home_index_iv_image, convertView);
		TextView tvName = get(R.id.item_home_index_tv_name, convertView);

		SDViewUtil.setViewWidth(ivImage, SDViewUtil.getScreenWidth() / 9);
		SDViewUtil.setViewHeight(ivImage, SDViewUtil.getScreenWidth() / 9);

		SDViewBinder.setTextView(tvName, model.getName());
		SDViewBinder.setImageView(model.getIcon_img(), ivImage, ImageLoaderManager.getOptionsNoResetViewBeforeLoading());

		convertView.setOnClickListener(new OnClickListener()
		{

			@Override
			public void onClick(View v)
			{
				// TODO 根据分类id刷新接口
			}
		});

	}

}
