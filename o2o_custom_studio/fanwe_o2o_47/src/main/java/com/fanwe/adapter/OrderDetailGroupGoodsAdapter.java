package com.fanwe.adapter;

import java.util.List;

import android.app.Activity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.fanwe.library.adapter.SDSimpleAdapter;
import com.fanwe.library.customview.SDGridLinearLayout;
import com.fanwe.library.utils.SDViewBinder;
import com.fanwe.library.utils.ViewHolder;
import com.fanwe.model.CartGoodsModel;
import com.fanwe.model.CartGroupGoodsModel;
import com.fanwe.o2o.newo2o.R;

public class OrderDetailGroupGoodsAdapter extends SDSimpleAdapter<CartGroupGoodsModel>
{

	private int mIsScore;

	public OrderDetailGroupGoodsAdapter(List<CartGroupGoodsModel> listModel, Activity activity, int isScore)
	{
		super(listModel, activity);
		this.mIsScore = isScore;
	}

	@Override
	public int getLayoutId(int position, View convertView, ViewGroup parent)
	{
		return R.layout.item_order_detail_group_goods;
	}

	@Override
	public void bindData(int position, View convertView, ViewGroup parent, CartGroupGoodsModel model)
	{
		TextView tv_supplier = ViewHolder.get(R.id.tv_supplier, convertView);
		TextView tv_fee_info = ViewHolder.get(R.id.tv_fee_info, convertView);
		SDGridLinearLayout ll_goods = ViewHolder.get(R.id.ll_goods, convertView);

		SDViewBinder.setTextView(tv_supplier, model.getSupplier());
		SDViewBinder.setTextView(tv_fee_info, model.getDeliveryFeeInfo());

		List<CartGoodsModel> listModel = model.getGoods_list();
		OrderDetailGoodsAdapter adapter = new OrderDetailGoodsAdapter(listModel, mActivity, mIsScore);
		ll_goods.setAdapter(adapter);
	}

}
