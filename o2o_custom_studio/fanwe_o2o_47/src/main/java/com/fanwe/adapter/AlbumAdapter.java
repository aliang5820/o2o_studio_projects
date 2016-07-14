package com.fanwe.adapter;

import java.util.List;

import uk.co.senab.photoview.PhotoView;
import android.app.Activity;
import android.view.View;
import android.widget.ImageView.ScaleType;

import com.fanwe.library.adapter.SDPagerAdapter;
import com.fanwe.library.utils.SDViewBinder;

public class AlbumAdapter extends SDPagerAdapter<String>
{

	public AlbumAdapter(List<String> listModel, Activity activity)
	{
		super(listModel, activity);
	}

	@Override
	public View getView(View container, int position)
	{
		PhotoView image = new PhotoView(mActivity);
		image.setScaleType(ScaleType.FIT_CENTER);
		String path = getItemModel(position);
		SDViewBinder.setImageView(path, image);

		return image;
	}

}
