package com.fanwe.adapter;

import java.util.List;

import android.app.Activity;
import android.text.TextUtils;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.fanwe.library.adapter.SDSimpleAdapter;
import com.fanwe.library.utils.SDViewBinder;
import com.fanwe.library.utils.SDViewUtil;
import com.fanwe.library.utils.ViewHolder;
import com.fanwe.model.OrderGoodsModel;
import com.fanwe.o2o.newo2o.R;

public class OrderGoodsListAdapter extends SDSimpleAdapter<OrderGoodsModel>
{

	public OrderGoodsListAdapter(List<OrderGoodsModel> listModel, Activity activity)
	{
		super(listModel, activity);
	}

	@Override
	public int getLayoutId(int position, View convertView, ViewGroup parent)
	{
		return R.layout.item_order_goods_list;
	}

	@Override
	public void bindData(int position, View convertView, ViewGroup parent, OrderGoodsModel model)
	{
		View viewDiv = ViewHolder.get(R.id.item_order_goods_list_view_div, convertView); // 商品图片
		ImageView ivImage = ViewHolder.get(R.id.item_order_goods_list_iv_goods_image, convertView); // 商品图片
		TextView tvTitle = ViewHolder.get(R.id.item_order_goods_list_tv_goods_title, convertView); // 商品标题
		TextView tvNumber = ViewHolder.get(R.id.item_order_goods_list_tv_number, convertView); // 商品数量
		TextView tvAttr = ViewHolder.get(R.id.item_order_goods_list_tv_attr, convertView); // 商品属性
		TextView tvPriceSingle = ViewHolder.get(R.id.item_order_goods_list_tv_price_single, convertView); // 商品单价
		TextView tvPriceTotal = ViewHolder.get(R.id.item_order_goods_list_tv_price_total, convertView); // 商品总价

		if (position == 0)
		{
			SDViewUtil.hide(viewDiv);
		} else
		{
			SDViewUtil.show(viewDiv);
		}

		if (!TextUtils.isEmpty(model.getAttr_content()))
		{
			tvTitle.setMaxLines(1);
		}

		SDViewBinder.setImageView(model.getImage(), ivImage);
		SDViewBinder.setTextView(tvTitle, model.getName());
		SDViewBinder.setTextView(tvNumber, model.getNum());
		SDViewBinder.setTextViewsVisibility(tvAttr, model.getAttr_content());
		SDViewBinder.setTextView(tvPriceSingle, model.getPrice_format());
		SDViewBinder.setTextView(tvPriceTotal, model.getTotal_money_format());
	}
}
