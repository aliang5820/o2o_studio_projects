package com.fanwe.customview;

import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.fanwe.library.utils.SDViewBinder;
import com.fanwe.library.utils.SDViewUtil;
import com.fanwe.o2o.newo2o.R;

public class SDSimpleSetItem extends LinearLayout
{

	public ImageView mIv_title;
	public TextView mTv_title;
	public TextView mTv_title_sub;
	public LinearLayout mLl_tip;
	public TextView mTv_tip;
	public ImageView mIv_arrow;

	public SDSimpleSetItem(Context context)
	{
		this(context, null);
	}

	public SDSimpleSetItem(Context context, AttributeSet attrs)
	{
		super(context, attrs);
		init();
	}

	private void init()
	{
		LayoutInflater.from(getContext()).inflate(R.layout.view_simple_set_item, this, true);

		mIv_title = (ImageView) findViewById(R.id.iv_title);
		mTv_title = (TextView) findViewById(R.id.tv_title);
		mTv_title_sub = (TextView) findViewById(R.id.tv_title_sub);
		mLl_tip = (LinearLayout) findViewById(R.id.ll_tip);
		mTv_tip = (TextView) findViewById(R.id.tv_tip);
		mIv_arrow = (ImageView) findViewById(R.id.iv_arrow);
	}

	public void setImageTitle(int resId)
	{
		if (resId == 0)
		{
			SDViewUtil.hide(mIv_title);
		} else
		{
			SDViewUtil.show(mIv_title);
			mIv_title.setImageResource(resId);
		}
	}

	public void setImageArrow(int resId)
	{
		if (resId == 0)
		{
			SDViewUtil.hide(mIv_arrow);
		} else
		{
			SDViewUtil.show(mIv_arrow);
			mIv_arrow.setImageResource(resId);
		}
	}

	public void setTextTitle(String text)
	{
		SDViewBinder.setTextView(mTv_title, text);
	}

	public void setTextTitleSub(String text)
	{
		SDViewBinder.setTextView(mTv_title_sub, text);
	}

	public void setTextTip(String text)
	{
		SDViewBinder.setTextView(mTv_tip, text);
	}

}
