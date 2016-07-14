package com.fanwe.adapter;

import java.util.List;

import android.app.Activity;
import android.content.Intent;
import android.text.TextUtils;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.fanwe.StoreDetailActivity;
import com.fanwe.app.App;
import com.fanwe.library.adapter.SDSimpleAdapter;
import com.fanwe.library.utils.SDIntentUtil;
import com.fanwe.library.utils.SDToast;
import com.fanwe.library.utils.SDViewBinder;
import com.fanwe.library.utils.SDViewUtil;
import com.fanwe.model.StoreModel;
import com.fanwe.o2o.newo2o.R;

public class GoodsDetailMerchantAdapter extends SDSimpleAdapter<StoreModel>
{
	public GoodsDetailMerchantAdapter(List<StoreModel> listModel, Activity activity)
	{
		super(listModel, activity);
	}

	@Override
	public int getLayoutId(int position, View convertView, ViewGroup parent)
	{
		return R.layout.item_goods_detail_merchant;
	}

	@Override
	public void bindData(int position, View convertView, ViewGroup parent, final StoreModel model)
	{
		TextView tv_name = get(R.id.tv_name, convertView);
		TextView tv_address = get(R.id.tv_address, convertView);
		TextView tv_distance = get(R.id.tv_distance, convertView);
		LinearLayout ll_phone = get(R.id.ll_phone, convertView);
		View viewDiv = get(R.id.view_div, convertView);

		if (position == 0)
		{
			SDViewUtil.hide(viewDiv);
		} else
		{
			SDViewUtil.show(viewDiv);
		}

		SDViewBinder.setTextView(tv_name, model.getName());
		SDViewBinder.setTextView(tv_address, model.getAddress());
		SDViewBinder.setTextView(tv_distance, model.getDistanceFormat());

		ll_phone.setOnClickListener(new OnClickListener()
		{
			@Override
			public void onClick(View v)
			{
				if (!TextUtils.isEmpty(model.getTel()))
				{
					Intent intent = SDIntentUtil.getIntentCallPhone(model.getTel());
					mActivity.startActivity(intent);
				} else
				{
					SDToast.showToast("未找到号码");
				}
			}
		});

		convertView.setOnClickListener(new OnClickListener()
		{

			@Override
			public void onClick(View v)
			{
				if (model != null && mActivity != null)
				{
					Intent intent = new Intent();
					intent.setClass(App.getApplication(), StoreDetailActivity.class);
					intent.putExtra(StoreDetailActivity.EXTRA_MERCHANT_ID, model.getId());
					mActivity.startActivity(intent);
				}
			}
		});
	}

}