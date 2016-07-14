package com.fanwe.adapter;

import java.util.List;

import android.app.Activity;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ImageView.ScaleType;

import com.fanwe.common.ImageLoaderManager;
import com.fanwe.library.adapter.SDSimpleAdapter;
import com.fanwe.library.utils.SDViewBinder;
import com.fanwe.library.utils.SDViewUtil;
import com.fanwe.model.LocalImageModel;
import com.fanwe.o2o.newo2o.R;

public class SelectImageAdapter extends SDSimpleAdapter<LocalImageModel>
{

	private SelectImageAdapterListener mListener;

	public void setmListener(SelectImageAdapterListener listener)
	{
		this.mListener = listener;
	}

	public SelectImageAdapter(List<LocalImageModel> listModel, Activity activity)
	{
		super(listModel, activity);
	}

	@Override
	public int getLayoutId(int position, View convertView, ViewGroup parent)
	{
		return R.layout.item_select_image;
	}

	@Override
	public void bindData(final int position, View convertView, ViewGroup parent, LocalImageModel model)
	{
		ImageView iv_image = get(R.id.iv_image, convertView);
		ImageView iv_delete = get(R.id.iv_delete, convertView);

		if (!model.isAddImage()) // 选择或者拍照的图片
		{
			SDViewUtil.show(iv_delete);
			iv_image.setScaleType(ScaleType.CENTER_CROP);
			SDViewBinder.setImageView("file://" + model.getPath(), iv_image, ImageLoaderManager.getOptionsNoCache());
			iv_delete.setOnClickListener(new OnClickListener()
			{

				@Override
				public void onClick(View v)
				{
					removeItem(position);
					if (mListener != null)
					{
						mListener.onClickDelete(position, v);
					}
				}
			});
		} else
		{
			SDViewUtil.hide(iv_delete);
			iv_image.setScaleType(ScaleType.CENTER_INSIDE);
			iv_image.setImageResource(R.drawable.bg_add_comment_image);
		}
	}

	public interface SelectImageAdapterListener
	{
		public void onClickDelete(int position, View v);
	}

}
