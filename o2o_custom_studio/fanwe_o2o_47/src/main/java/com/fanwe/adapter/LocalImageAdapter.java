package com.fanwe.adapter;

import java.util.List;

import android.app.Activity;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.fanwe.common.ImageLoaderManager;
import com.fanwe.library.adapter.SDSimpleAdapter;
import com.fanwe.library.common.SDSelectManager.Mode;
import com.fanwe.library.utils.SDViewUtil;
import com.fanwe.library.utils.ViewHolder;
import com.fanwe.model.LocalImageModel;
import com.fanwe.o2o.newo2o.R;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.assist.ImageSize;
import com.nostra13.universalimageloader.core.imageaware.ImageViewAware;

public class LocalImageAdapter extends SDSimpleAdapter<LocalImageModel>
{

	private LocalImageAdapterListener mListener;
	private int mImageSize;

	public LocalImageAdapter(List<LocalImageModel> listModel, Activity activity, LocalImageAdapterListener listener)
	{
		super(listModel, activity);
		getSelectManager().setMode(Mode.MULTI);
		this.mListener = listener;
		mImageSize = SDViewUtil.dp2px(50);
	}

	@Override
	public int getLayoutId(int position, View convertView, ViewGroup parent)
	{
		return R.layout.item_local_image;
	}

	@Override
	public void bindData(final int position, View convertView, ViewGroup parent, final LocalImageModel model)
	{
		ImageView iv_image = ViewHolder.get(R.id.iv_image, convertView);
		final ImageView iv_selected = ViewHolder.get(R.id.iv_selected, convertView);

		if (model.isSelected())
		{
			iv_selected.setImageResource(R.drawable.ic_image_selected);
		} else
		{
			iv_selected.setImageResource(R.drawable.ic_image_unselected);
		}

		iv_selected.setOnClickListener(new OnClickListener()
		{

			@Override
			public void onClick(View v)
			{
				if (mListener != null)
				{
					mListener.onClick(v, position, model);
				}
			}
		});

		ImageLoader.getInstance().displayImage("file://" + model.getPath(), new ImageViewAware(iv_image),
				ImageLoaderManager.getOptionsNoCacheNoResetViewBeforeLoading(), new ImageSize(mImageSize, mImageSize), null, null);
	}

	public interface LocalImageAdapterListener
	{
		void onClick(View v, int position, LocalImageModel model);
	}
}
