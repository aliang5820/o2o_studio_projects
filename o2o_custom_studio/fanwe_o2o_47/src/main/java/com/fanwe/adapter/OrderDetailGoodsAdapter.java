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
import com.fanwe.library.adapter.SDSimpleAdapter;
import com.fanwe.library.utils.SDViewBinder;
import com.fanwe.library.utils.ViewHolder;
import com.fanwe.model.CartGoodsModel;
import com.fanwe.o2o.newo2o.R;

public class OrderDetailGoodsAdapter extends SDSimpleAdapter<CartGoodsModel>
{

	private int mIsScore;

	public OrderDetailGoodsAdapter(List<CartGoodsModel> listModel, Activity activity, int isScore)
	{
		super(listModel, activity);
		this.mIsScore = isScore;
	}

	@Override
	public int getLayoutId(int position, View convertView, ViewGroup parent)
	{
		return R.layout.item_order_detail_goods;
	}

	@Override
	public void bindData(int position, View convertView, ViewGroup parent, final CartGoodsModel model)
	{
		ImageView iv_image = ViewHolder.get(R.id.iv_image, convertView);
		TextView tv_name = ViewHolder.get(R.id.tv_name, convertView);
		TextView tv_number = ViewHolder.get(R.id.tv_number, convertView);
		TextView tv_single_price = ViewHolder.get(R.id.tv_single_price, convertView);
		TextView tv_total_price = ViewHolder.get(R.id.tv_total_price, convertView);

		SDViewBinder.setImageView(model.getIcon(), iv_image);
		SDViewBinder.setTextView(tv_name, model.getSub_name());
		SDViewBinder.setTextView(tv_number, String.valueOf(model.getNumber()));
		setPrice(tv_single_price, tv_total_price, model);

		convertView.setOnClickListener(new OnClickListener()
		{

			@Override
			public void onClick(View v)
			{
				if (model.getDeal_id() > 0)
				{
					Intent intent = new Intent(mActivity, TuanDetailActivity.class);
					intent.putExtra(TuanDetailActivity.EXTRA_GOODS_ID, model.getDeal_id());
					mActivity.startActivity(intent);
				}
			}
		});
	}

	private void setPrice(TextView tvSinglePrice, TextView tvTotalPrice, CartGoodsModel model)
	{
		if (model != null && tvSinglePrice != null && tvTotalPrice != null)
		{
			if (mIsScore == 1)
			{
				SDViewBinder.setTextView(tvSinglePrice, model.getReturn_scoreFormat());
				SDViewBinder.setTextView(tvTotalPrice, model.getReturn_total_scoreFormat());
			} else
			{
				SDViewBinder.setTextView(tvSinglePrice, model.getUnit_priceFormat());
				SDViewBinder.setTextView(tvTotalPrice, model.getTotal_priceFormat());
			}
		}
	}

}
