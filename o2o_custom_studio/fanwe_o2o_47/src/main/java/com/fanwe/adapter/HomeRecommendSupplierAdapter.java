package com.fanwe.adapter;

import java.util.List;

import android.app.Activity;
import android.content.Intent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.fanwe.StoreDetailActivity;
import com.fanwe.app.App;
import com.fanwe.library.adapter.SDSimpleAdapter;
import com.fanwe.library.utils.SDViewBinder;
import com.fanwe.library.utils.SDViewUtil;
import com.fanwe.library.utils.ViewHolder;
import com.fanwe.model.StoreModel;
import com.fanwe.o2o.newo2o.R;

public class HomeRecommendSupplierAdapter extends SDSimpleAdapter<StoreModel>
{

	public HomeRecommendSupplierAdapter(List<StoreModel> listModel, Activity activity)
	{
		super(listModel, activity);
	}

	@Override
	public int getLayoutId(int position, View convertView, ViewGroup parent)
	{
		return R.layout.item_home_recommend_supplier;
	}

	@Override
	public void bindData(int position, View convertView, ViewGroup parent, final StoreModel model)
	{
		View view_div = ViewHolder.get(R.id.view_div, convertView);
		ImageView ivImage = ViewHolder.get(R.id.item_home_recommend_supplier_iv_image, convertView);
		TextView tvName = ViewHolder.get(R.id.item_home_recommend_supplier_tv_name, convertView);

		if (position == 0)
		{
			SDViewUtil.hide(view_div);
		} else
		{
			SDViewUtil.show(view_div);
		}
		SDViewBinder.setImageView(model.getPreview(), ivImage);
		SDViewBinder.setTextView(tvName, model.getName());
		convertView.setOnClickListener(new OnClickListener()
		{
			@Override
			public void onClick(View v)
			{
				Intent intent = new Intent(App.getApplication(), StoreDetailActivity.class);
				intent.putExtra(StoreDetailActivity.EXTRA_MERCHANT_ID, model.getId());
				mActivity.startActivity(intent);
			}
		});
	}
}
