package com.fanwe.customview;

import android.content.Context;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.fanwe.library.customview.SDViewBase;
import com.fanwe.library.utils.SDViewBinder;
import com.fanwe.model.Payment_listModel;
import com.fanwe.o2o.newo2o.R;

public class SDPaymentListView extends SDViewBase
{

	public ImageView mIv_image;
	public TextView mTv_name;
	public ImageView mIv_selected;

	private Payment_listModel mModel;

	public SDPaymentListView(Context context)
	{
		this(context, null);
	}

	public SDPaymentListView(Context context, AttributeSet attrs)
	{
		super(context, attrs);
		init();
	}

	private void init()
	{
		LayoutInflater.from(getContext()).inflate(R.layout.item_payment_list, this, true);
		setBackgroundResource(R.drawable.selector_white_gray_stroke_top_left_right);

		mIv_image = (ImageView) findViewById(R.id.iv_image);
		mTv_name = (TextView) findViewById(R.id.tv_name);
		mIv_selected = (ImageView) findViewById(R.id.iv_selected);
		mIv_image.setVisibility(View.GONE);
	}

	public void setData(Payment_listModel model)
	{
		this.mModel = model;
		bindData();
	}

	public Payment_listModel getData()
	{
		return mModel;
	}

	private void bindData()
	{
		if (mModel == null)
		{
			return;
		}

		SDViewBinder.setTextView(mTv_name, mModel.getName());

		String logo = mModel.getLogo();
		if (!TextUtils.isEmpty(logo))
		{
			mIv_image.setVisibility(View.VISIBLE);
			SDViewBinder.setImageView(mIv_image, mModel.getLogo());
		}

		mIv_selected.setImageResource(R.drawable.ic_payment_normal);
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
