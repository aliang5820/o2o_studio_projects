package com.fanwe.library.customview;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.util.AttributeSet;
import android.widget.ImageView;

import com.fanwe.library.utils.SDViewUtil;

public class SDScaleImageView extends ImageView
{

	public SDScaleImageView(Context context)
	{
		super(context);
		init();
	}

	public SDScaleImageView(Context context, AttributeSet attrs, int defStyle)
	{
		super(context, attrs, defStyle);
		init();
	}

	public SDScaleImageView(Context context, AttributeSet attrs)
	{
		super(context, attrs);
		init();
	}

	private void init()
	{
		setScaleType(ScaleType.FIT_XY);
	}

	@Override
	public void setImageDrawable(Drawable d)
	{
		super.setImageDrawable(d);
		SDViewUtil.updateImageViewSize(this, d);
	}

	@Override
	public void setImageResource(int id)
	{
		super.setImageResource(id);
		SDViewUtil.updateImageViewSize(this, this.getDrawable());
	}

}
