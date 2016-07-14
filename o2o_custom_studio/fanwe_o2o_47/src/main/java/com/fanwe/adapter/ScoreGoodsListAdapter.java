package com.fanwe.adapter;

import java.util.List;

import android.app.Activity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.fanwe.library.utils.SDViewBinder;
import com.fanwe.library.utils.SDViewUtil;
import com.fanwe.model.GoodsModel;
import com.fanwe.o2o.newo2o.R;

public class ScoreGoodsListAdapter extends TuanListAdapter
{

	public ScoreGoodsListAdapter(List<GoodsModel> listModel, Activity activity)
	{
		super(listModel, activity);
	}

	@Override
	public void bindData(int position, View convertView, ViewGroup parent, GoodsModel model)
	{
		super.bindData(position, convertView, parent, model);

		ImageView iv_auto_order = get(R.id.iv_auto_order, convertView);
		ImageView iv_is_new = get(R.id.iv_is_new, convertView);
		TextView tv_current_price = get(R.id.tv_current_price, convertView);
		TextView tv_original_price = get(R.id.tv_original_price, convertView);
		TextView tv_distance = get(R.id.tv_distance, convertView);
		TextView tv_buy_count_label = get(R.id.tv_buy_count_label, convertView);

		SDViewUtil.hide(iv_auto_order);
		SDViewUtil.hide(iv_is_new);
		SDViewBinder.setTextView(tv_current_price, model.getDeal_scoreFormat());
		SDViewBinder.setTextView(tv_original_price, null);
		SDViewBinder.setTextView(tv_distance, null);
		tv_buy_count_label.setText("已兑换");
	}

}
