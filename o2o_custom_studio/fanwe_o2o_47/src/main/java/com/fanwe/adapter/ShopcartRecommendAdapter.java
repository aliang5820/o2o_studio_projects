package com.fanwe.adapter;

import java.util.List;

import android.app.Activity;
import android.content.Intent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.fanwe.TuanDetailActivity;
import com.fanwe.app.App;
import com.fanwe.library.adapter.SDSimpleAdapter;
import com.fanwe.library.utils.SDViewBinder;
import com.fanwe.library.utils.SDViewUtil;
import com.fanwe.library.utils.ViewHolder;
import com.fanwe.model.GoodsModel;
import com.fanwe.o2o.newo2o.R;

public class ShopcartRecommendAdapter extends SDSimpleAdapter<GoodsModel>
{

	public ShopcartRecommendAdapter(List<GoodsModel> listModel, Activity activity)
	{
		super(listModel, activity);
	}

	@Override
	public int getLayoutId(int position, View convertView, ViewGroup parent)
	{
		return R.layout.item_distribution_hot_goods;
	}

	@Override
	public void bindData(int position, View convertView, ViewGroup parent, final GoodsModel model)
	{
		View view_div = ViewHolder.get(R.id.view_div, convertView);
		ImageView iv_image = ViewHolder.get(R.id.iv_image, convertView);
		TextView tv_name = ViewHolder.get(R.id.tv_name, convertView);
		TextView tv_price = ViewHolder.get(R.id.tv_price, convertView);

		SDViewBinder.setImageView(model.getIcon(), iv_image);
		SDViewBinder.setTextView(tv_name, model.getName());
		SDViewBinder.setTextView(tv_price, model.getCurrent_price_format());

		if (position == 0)
		{
			SDViewUtil.hide(view_div);
		} else
		{
			SDViewUtil.show(view_div);
		}
		convertView.setOnClickListener(new OnClickListener()
		{

			@Override
			public void onClick(View v)
			{
				Intent intent = new Intent(App.getApplication(), TuanDetailActivity.class);
				intent.putExtra(TuanDetailActivity.EXTRA_GOODS_ID, model.getId());
				mActivity.startActivity(intent);
			}
		});
	}
}
