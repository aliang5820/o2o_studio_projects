package com.fanwe.customview;

import android.content.Context;
import android.util.AttributeSet;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.fanwe.businessclient.R;



public class SDSimpleSetItemView extends LinearLayout
{

	private ImageView mImgTitle = null;
	private ImageView mImgArrowRight = null;
	private TextView mTxtTitle = null;
	private TextView mTxtTitleSub = null;
	private TextView mTxtTitleNumber = null;
	
	private View mView = null;

	public ImageView getmImgTitle()
	{
		return mImgTitle;
	}

	public void setmImgTitle(ImageView mImgTitle)
	{
		this.mImgTitle = mImgTitle;
	}

	public ImageView getmImgArrowRight()
	{
		return mImgArrowRight;
	}

	public void setmImgArrowRight(ImageView mImgArrowRight)
	{
		this.mImgArrowRight = mImgArrowRight;
	}

	public TextView getmTxtTitle()
	{
		return mTxtTitle;
	}

	public void setmTxtTitle(TextView mTxtTitle)
	{
		this.mTxtTitle = mTxtTitle;
	}

	public TextView getmTxtTitleSub()
	{
		return mTxtTitleSub;
	}

	public void setmTxtTitleSub(TextView mTxtTitleSub)
	{
		this.mTxtTitleSub = mTxtTitleSub;
	}

	public TextView getmTxtTitleNumber()
	{
		return mTxtTitleNumber;
	}

	public void setmTxtTitleNumber(TextView mTxtTitleNumber)
	{
		this.mTxtTitleNumber = mTxtTitleNumber;
	}

	public SDSimpleSetItemView(Context context)
	{
		super(context);
		init();
	}

	public SDSimpleSetItemView(Context context, AttributeSet attrs)
	{
		super(context, attrs);
		init();
	}

	private void init()
	{
		mView = LayoutInflater.from(getContext()).inflate(R.layout.view_simple_set_item, null);
		mImgTitle = (ImageView) mView.findViewById(R.id.view_simple_set_item_img_title);
		mImgArrowRight = (ImageView) mView.findViewById(R.id.view_simple_set_item_img_arrow_right);
		mTxtTitle = (TextView) mView.findViewById(R.id.view_simple_set_item_txt_title);
		mTxtTitleSub = (TextView) mView.findViewById(R.id.view_simple_set_item_txt_title_sub);
		mTxtTitleNumber = (TextView) mView.findViewById(R.id.view_simple_set_item_txt_number);
		LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(LayoutParams.FILL_PARENT, LayoutParams.FILL_PARENT);
		params.gravity = Gravity.CENTER;
		this.addView(mView, params);
	}

	public void setTitleText(String text)
	{
		if (text != null)
		{
			this.mTxtTitle.setText(text);
		}
	}

	public void setTitleSubText(String text)
	{
		if (text != null)
		{
			this.mTxtTitleSub.setText(text);
		}
	}

	public void setTitleNumber(String text)
	{
		if (text != null)
		{
			this.mTxtTitleNumber.setText(text);
		}
	}

	public void setTitleImage(int imgId)
	{
		this.mImgTitle.setImageResource(imgId);
	}

	public void setTitleImageArrowRight(int imgId)
	{
		this.mImgArrowRight.setImageResource(imgId);
	}
	
	public void setBackgroundImage(int resId)
	{
		mView.setBackgroundResource(resId);
	}

}
