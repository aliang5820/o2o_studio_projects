package com.fanwe.library.view;

import android.content.Context;
import android.graphics.Color;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.widget.ImageView;
import android.widget.TextView;

import com.fanwe.library.R;
import com.fanwe.library.utils.SDViewBinder;
import com.fanwe.library.view.select.SDSelectViewAuto;

public class SDTabUnderline extends SDSelectViewAuto
{

	public TextView mTvTitle;
	public ImageView mIvUnderline;

	public SDTabUnderline(Context context)
	{
		super(context);
		init();
	}

	public SDTabUnderline(Context context, AttributeSet attrs)
	{
		super(context, attrs);
		init();
	}

	@Override
	protected void init()
	{
		LayoutInflater.from(getContext()).inflate(R.layout.view_tab_underline, this, true);

		mTvTitle = (TextView) findViewById(R.id.tv_title);
		mIvUnderline = (ImageView) findViewById(R.id.iv_underline);
		addAutoView(mIvUnderline, mTvTitle);

		setDefaultConfig();
		onNormal();
	}

	@Override
	public void setDefaultConfig()
	{
		getViewConfig(mTvTitle).setTextColorNormal(Color.GRAY).setTextColorSelected(mLibraryConfig.getmMainColor());
		getViewConfig(mIvUnderline).setBackgroundColorNormal(Color.TRANSPARENT).setBackgroundColorSelected(mLibraryConfig.getmMainColor());
		super.setDefaultConfig();
	}

	public void setTextTitle(String title)
	{
		SDViewBinder.setTextView(mTvTitle, title);
	}

}
