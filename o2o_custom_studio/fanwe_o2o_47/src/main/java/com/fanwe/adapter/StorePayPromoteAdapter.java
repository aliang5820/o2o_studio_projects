package com.fanwe.adapter;

import java.util.List;

import android.app.Activity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.fanwe.library.adapter.SDSimpleAdapter;
import com.fanwe.library.utils.SDViewBinder;
import com.fanwe.model.PromoteModel;
import com.fanwe.o2o.newo2o.R;

public class StorePayPromoteAdapter extends SDSimpleAdapter<PromoteModel>
{

	public StorePayPromoteAdapter(List<PromoteModel> listModel, Activity activity)
	{
		super(listModel, activity);
	}

	@Override
	public int getLayoutId(int position, View convertView, ViewGroup parent)
	{
		return R.layout.item_store_pay_promote;
	}

	@Override
	public void bindData(int position, View convertView, ViewGroup parent, PromoteModel model)
	{
		TextView tv_name = get(R.id.tv_name, convertView);
		TextView tv_content = get(R.id.tv_content, convertView);

		SDViewBinder.setTextView(tv_name, model.getDescription());
		SDViewBinder.setTextView(tv_content, model.getDiscount_priceFormat());
	}

}
