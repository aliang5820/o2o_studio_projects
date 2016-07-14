package com.fanwe.library.adapter;

import java.util.List;

import android.app.Activity;
import android.view.View;
import android.widget.ImageView;

import com.fanwe.library.R;
import com.fanwe.library.utils.SDViewBinder;

public class SDSimpleAdvsAdapter<T> extends SDPagerAdapter<T>
{

	public SDSimpleAdvsAdapter(List<T> listModel, Activity activity)
	{
		super(listModel, activity);
	}

	@Override
	public View getView(final View container, final int position)
	{
		final View view = mInflater.inflate(R.layout.item_simple_adv, null);
		final ImageView ivAdv = (ImageView) view.findViewById(R.id.iv_image);

		T model = getItemModel(position);
		if (model != null)
		{
			String url = model.toString();
			SDViewBinder.setImageView(url, ivAdv);
		}
		return view;
	}

}
