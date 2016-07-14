package com.fanwe.adapter;

import java.util.List;

import android.app.Activity;
import android.view.View;
import android.widget.ImageView;

import com.fanwe.library.adapter.SDPagerAdapter;
import com.fanwe.model.InitActStart_pageModel;
import com.fanwe.o2o.newo2o.R;
import com.nostra13.universalimageloader.core.ImageLoader;

public class InitAdvsPagerAdapter extends SDPagerAdapter<InitActStart_pageModel>
{

	public InitAdvsPagerAdapter(List<InitActStart_pageModel> listModel, Activity activity)
	{
		super(listModel, activity);
	}

	@Override
	public View getView(View container, int position)
	{
		View itemView = mInflater.inflate(R.layout.item_start_page, null);
		ImageView iv = (ImageView) itemView.findViewById(R.id.iv_image);
		InitActStart_pageModel model = getItemModel(position);
		if (model != null)
		{
			ImageLoader.getInstance().displayImage(model.getImg(), iv);
		}
		return itemView;
	}

}
