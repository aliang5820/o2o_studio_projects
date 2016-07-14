package com.fanwe.customview.app;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.ImageView;
import android.widget.TextView;

import com.fanwe.library.utils.SDViewBinder;
import com.fanwe.library.view.select.SDSelectView;
import com.fanwe.model.Payment_listModel;
import com.fanwe.o2o.newo2o.R;

public class AccountPaymentView extends SDSelectView
{

	public TextView mTv_name;
	public ImageView mIv_selected;

	public AccountPaymentView(Context context, AttributeSet attrs)
	{
		super(context, attrs);
		init();
	}

	public AccountPaymentView(Context context)
	{
		super(context);
		init();
	}

	@Override
	protected void init()
	{
		setContentView(R.layout.view_account_payment);
		mTv_name = (TextView) findViewById(R.id.tv_name);
		mIv_selected = (ImageView) findViewById(R.id.iv_selected);
		onNormal();
		super.init();
	}

	public void setData(Payment_listModel model)
	{
		if (model != null)
		{
			SDViewBinder.setTextView(mTv_name, model.getName());
			if (model.isSelected())
			{
				onSelected();
			} else
			{
				onNormal();
			}
		}
	}

	@Override
	public void onNormal()
	{
		mIv_selected.setImageResource(R.drawable.ic_payment_normal);
		super.onNormal();
	}

	@Override
	public void onSelected()
	{
		mIv_selected.setImageResource(R.drawable.ic_payment_selected);
		super.onSelected();
	}

}
