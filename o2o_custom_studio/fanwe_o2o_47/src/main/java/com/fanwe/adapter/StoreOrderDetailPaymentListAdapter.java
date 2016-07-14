package com.fanwe.adapter;

import java.util.List;

import android.app.Activity;
import android.text.TextUtils;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.fanwe.library.adapter.SDSimpleAdapter;
import com.fanwe.library.common.SDSelectManager.Mode;
import com.fanwe.library.utils.SDViewBinder;
import com.fanwe.library.utils.SDViewUtil;
import com.fanwe.model.Payment_listModel;
import com.fanwe.o2o.newo2o.R;

public class StoreOrderDetailPaymentListAdapter extends SDSimpleAdapter<Payment_listModel>
{

	public StoreOrderDetailPaymentListAdapter(List<Payment_listModel> listModel, Activity activity)
	{
		super(listModel, activity);
		getSelectManager().setMode(Mode.SINGLE_MUST_ONE_SELECTED);
	}

	@Override
	public int getLayoutId(int position, View convertView, ViewGroup parent)
	{
		return R.layout.item_store_order_detail_payment_list;
	}

	@Override
	public void bindData(final int position, View convertView, ViewGroup parent, Payment_listModel model)
	{
		ImageView iv_image = get(R.id.iv_image, convertView);
		TextView tv_name = get(R.id.tv_name, convertView);
		ImageView iv_selected = get(R.id.iv_selected, convertView);

		String logo = model.getLogo();
		if (TextUtils.isEmpty(logo))
		{
			SDViewUtil.hide(iv_image);
		} else
		{
			SDViewUtil.show(iv_image);
			SDViewBinder.setImageView(model.getLogo(), iv_image);
		}

		SDViewBinder.setTextView(tv_name, model.getName());
		if (model.isSelected())
		{
			iv_selected.setImageResource(R.drawable.ic_payment_selected);
		} else
		{
			iv_selected.setImageResource(R.drawable.ic_payment_normal);
		}
		convertView.setOnClickListener(new OnClickListener()
		{

			@Override
			public void onClick(View v)
			{
				getSelectManager().performClick(position);
			}
		});
	}

}
