package com.fanwe.adapter;

import java.util.List;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Paint;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.fanwe.TuanDetailActivity;
import com.fanwe.library.adapter.SDSimpleAdapter;
import com.fanwe.library.utils.SDViewBinder;
import com.fanwe.library.utils.ViewHolder;
import com.fanwe.model.DistributionGoodsModel;
import com.fanwe.o2o.newo2o.R;

public class DistributionStoreTuanAdapter extends SDSimpleAdapter<DistributionGoodsModel>
{

	public DistributionStoreTuanAdapter(List<DistributionGoodsModel> listModel, Activity activity)
	{
		super(listModel, activity);
	}

	@Override
	public int getLayoutId(int position, View convertView, ViewGroup parent)
	{
		return R.layout.item_distribution_store_tuan;
	}

	@Override
	public void bindData(int position, View convertView, ViewGroup parent, final DistributionGoodsModel model)
	{
		ImageView iv_image = ViewHolder.get(R.id.iv_image, convertView);
		TextView tv_name = ViewHolder.get(R.id.tv_name, convertView);
		TextView tv_current_price = ViewHolder.get(R.id.tv_current_price, convertView);
		TextView tv_original_price = ViewHolder.get(R.id.tv_original_price, convertView);

		tv_original_price.getPaint().setFlags(Paint.STRIKE_THRU_TEXT_FLAG);

		SDViewBinder.setImageView(model.getIcon_157(), iv_image);
		SDViewBinder.setTextView(tv_name, model.getName());
		SDViewBinder.setTextView(tv_current_price, model.getCurrent_priceFormat());
		SDViewBinder.setTextView(tv_original_price, model.getOrigin_priceFormat());

		convertView.setOnClickListener(new OnClickListener()
		{
			@Override
			public void onClick(View v)
			{
				Intent intent = new Intent(mActivity, TuanDetailActivity.class);
				intent.putExtra(TuanDetailActivity.EXTRA_GOODS_ID, model.getId());
				mActivity.startActivity(intent);
			}
		});
	}

}
