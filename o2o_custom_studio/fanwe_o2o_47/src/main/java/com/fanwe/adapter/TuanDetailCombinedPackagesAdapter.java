package com.fanwe.adapter;

import java.util.List;

import android.app.Activity;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.fanwe.common.ImageLoaderManager;
import com.fanwe.library.adapter.SDSimpleAdapter;
import com.fanwe.library.common.SDSelectManager.Mode;
import com.fanwe.library.utils.SDViewBinder;
import com.fanwe.library.utils.ViewHolder;
import com.fanwe.model.Deal_indexActModel;
import com.fanwe.o2o.newo2o.R;

public class TuanDetailCombinedPackagesAdapter extends SDSimpleAdapter<Deal_indexActModel>
{

	private TuanDetailCombinedPackagesAdapterListener mListener;

	public void setmListener(TuanDetailCombinedPackagesAdapterListener mListener)
	{
		this.mListener = mListener;
	}

	public TuanDetailCombinedPackagesAdapter(List<Deal_indexActModel> listModel, Activity activity)
	{
		super(listModel, activity);
		getSelectManager().setMode(Mode.MULTI);
	}

	@Override
	public int getLayoutId(int position, View convertView, ViewGroup parent)
	{
		return R.layout.item_tuan_detail_combined_packages;
	}

	@Override
	public void bindData(final int position, View convertView, ViewGroup parent, final Deal_indexActModel model)
	{
		ImageView iv_image = ViewHolder.get(R.id.iv_image, convertView);
		ImageView iv_selected = ViewHolder.get(R.id.iv_selected, convertView);
		LinearLayout ll_select = ViewHolder.get(R.id.ll_select, convertView);
		TextView tv_name = ViewHolder.get(R.id.tv_name, convertView);

		if (model.isSelected())
		{
			iv_selected.setImageResource(R.drawable.ic_payment_selected);
		} else
		{
			iv_selected.setImageResource(R.drawable.ic_payment_normal);
		}
		SDViewBinder.setTextView(tv_name, model.getName());
		SDViewBinder.setImageView(model.getIcon(), iv_image, ImageLoaderManager.getOptionsNoResetViewBeforeLoading());
		ll_select.setOnClickListener(new OnClickListener()
		{

			@Override
			public void onClick(View v)
			{
				if (mListener != null)
				{
					mListener.onClickSelected(v, position, model, TuanDetailCombinedPackagesAdapter.this);
				}
			}
		});
		convertView.setOnClickListener(new OnClickListener()
		{

			@Override
			public void onClick(View v)
			{
				if (mListener != null)
				{
					mListener.onClickItem(v, position, model, TuanDetailCombinedPackagesAdapter.this);
				}
			}
		});
	}

	public interface TuanDetailCombinedPackagesAdapterListener
	{
		public void onClickSelected(View view, int position, Deal_indexActModel model, TuanDetailCombinedPackagesAdapter adapter);

		public void onClickItem(View view, int position, Deal_indexActModel model, TuanDetailCombinedPackagesAdapter adapter);

	}
}
