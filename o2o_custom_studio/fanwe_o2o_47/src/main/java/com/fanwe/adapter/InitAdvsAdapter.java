package com.fanwe.adapter;

import java.util.List;

import android.app.Activity;
import android.text.TextUtils;
import android.view.View;
import android.widget.ImageView;
import android.widget.ImageView.ScaleType;

import com.fanwe.library.adapter.SDPagerAdapter;
import com.fanwe.library.utils.SDViewBinder;

public class InitAdvsAdapter<T> extends SDPagerAdapter<T>
{

	public InitAdvsAdapter(List<T> listModel, Activity activity)
	{
		super(listModel, activity);
	}

	@Override
	public View getView(View container, int position)
	{
		ImageView ivAdv = new ImageView(mActivity);
		ivAdv.setScaleType(ScaleType.FIT_XY);

		T model = getItemModel(position);
		if (model != null)
		{
			String url = model.toString();
			if (!TextUtils.isEmpty(url))
			{
				SDViewBinder.setImageView(url, ivAdv);
			}
		}
		return ivAdv;
	}

}
