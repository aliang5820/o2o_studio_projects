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
import com.fanwe.library.utils.SDViewUtil;
import com.fanwe.library.utils.ViewHolder;
import com.fanwe.model.GoodsModel;
import com.fanwe.o2o.newo2o.R;

public class HomeForenoticeYouhuiAdapter extends SDSimpleAdapter<GoodsModel>
{
	public HomeForenoticeYouhuiAdapter(List<GoodsModel> listModel, Activity activity)
	{
		super(listModel, activity);
	}

	@Override
	public int getLayoutId(int position, View convertView, ViewGroup parent)
	{
		return R.layout.item_home_forenotice_youhui;
	}

	@Override
	public void bindData(int position, View convertView, ViewGroup parent, final GoodsModel model)
	{
		View view_div = ViewHolder.get(R.id.view_div, convertView);
		ImageView iv_image = ViewHolder.get(R.id.iv_image, convertView);
		TextView tv_name = ViewHolder.get(R.id.tv_name, convertView);

		if (position == 0)
		{
			SDViewUtil.hide(view_div);
		} else
		{
			SDViewUtil.show(view_div);
		}

		SDViewBinder.setImageView(model.getIcon(), iv_image);
		SDViewBinder.setTextView(tv_name, model.getName());

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
