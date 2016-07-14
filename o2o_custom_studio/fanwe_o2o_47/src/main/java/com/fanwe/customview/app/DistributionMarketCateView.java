package com.fanwe.customview.app;

import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.widget.LinearLayout;

import com.fanwe.library.customview.SDSlidingPlayView;
import com.fanwe.o2o.newo2o.R;
import com.lidroid.xutils.ViewUtils;
import com.lidroid.xutils.view.annotation.ViewInject;

public class DistributionMarketCateView extends LinearLayout
{

	@ViewInject(R.id.spv_content)
	public SDSlidingPlayView mSpv_content;

	public DistributionMarketCateView(Context context)
	{
		this(context, null);
	}

	public DistributionMarketCateView(Context context, AttributeSet attrs)
	{
		super(context, attrs);
		init();
	}

	private void init()
	{
		setLayoutId(R.layout.view_sliding_play_container);
	}

	public void setLayoutId(int layoutId)
	{
		LayoutInflater.from(getContext()).inflate(layoutId, this, true);
		ViewUtils.inject(this, this);
	}

}
