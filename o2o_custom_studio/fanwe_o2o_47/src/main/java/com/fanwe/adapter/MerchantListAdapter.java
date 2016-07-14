package com.fanwe.adapter;

import java.util.List;

import android.app.Activity;
import android.content.Intent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;

import com.fanwe.StoreDetailActivity;
import com.fanwe.app.App;
import com.fanwe.library.adapter.SDSimpleAdapter;
import com.fanwe.library.utils.SDViewBinder;
import com.fanwe.model.StoreModel;
import com.fanwe.o2o.newo2o.R;

public class MerchantListAdapter extends SDSimpleAdapter<StoreModel>
{

	public MerchantListAdapter(List<StoreModel> listModel, Activity activity)
	{
		super(listModel, activity);
	}

	@Override
	public int getLayoutId(int position, View convertView, ViewGroup parent)
	{
		return R.layout.item_merchant_list;
	}

	@Override
	public void bindData(int position, View convertView, ViewGroup parent, final StoreModel model)
	{
		ImageView iv_image = get(R.id.iv_image, convertView);
		TextView tv_name = get(R.id.tv_name, convertView);
		TextView tv_distance = get(R.id.tv_distance, convertView);
		RatingBar rb_rating = get(R.id.rb_rating, convertView);
		TextView tv_address = get(R.id.tv_address, convertView);

		SDViewBinder.setImageView(model.getPreview(), iv_image);
		SDViewBinder.setTextView(tv_name, model.getName());
		SDViewBinder.setRatingBar(rb_rating, model.getAvg_point());
		SDViewBinder.setTextView(tv_address, model.getAddress());
		SDViewBinder.setTextView(tv_distance, model.getDistanceFormat());

		convertView.setOnClickListener(new OnClickListener()
		{
			@Override
			public void onClick(View v)
			{
				Intent itemintent = new Intent();
				itemintent.putExtra(StoreDetailActivity.EXTRA_MERCHANT_ID, model.getId());
				itemintent.setClass(App.getApplication(), StoreDetailActivity.class);
				mActivity.startActivity(itemintent);
			}
		});
	}

}
