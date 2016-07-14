package com.fanwe.adapter;

import java.util.List;

import android.app.Activity;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.TextView;

import com.fanwe.library.adapter.SDSimpleAdapter;
import com.fanwe.library.utils.SDViewBinder;
import com.fanwe.library.utils.SDViewUtil;
import com.fanwe.library.utils.ViewHolder;
import com.fanwe.model.Deal_attrValueModel;
import com.fanwe.o2o.newo2o.R;

public class DealAttrAdapter extends SDSimpleAdapter<Deal_attrValueModel>
{

	private DealAttrAdapterListener mListener;

	public void setListener(DealAttrAdapterListener listener)
	{
		this.mListener = listener;
	}

	public DealAttrAdapter(List<Deal_attrValueModel> listModel, Activity activity)
	{
		super(listModel, activity);
	}

	@Override
	public int getLayoutId(int position, View convertView, ViewGroup parent)
	{
		return R.layout.item_deal_attr;
	}

	@Override
	public void bindData(final int position, View convertView, ViewGroup parent, final Deal_attrValueModel model)
	{
		TextView tv_name = ViewHolder.get(R.id.tv_name, convertView);
		SDViewBinder.setTextView(tv_name, model.getName());

		if (model.isSelected())
		{
			SDViewUtil.setBackgroundResource(tv_name, R.drawable.layer_goods_attr_selected);
			SDViewUtil.setTextViewColorResId(tv_name, R.color.main_color);
		} else
		{
			SDViewUtil.setBackgroundResource(tv_name, R.drawable.layer_goods_attr_normal);
			SDViewUtil.setTextViewColorResId(tv_name, R.color.gray);
		}
		convertView.setOnClickListener(new OnClickListener()
		{

			@Override
			public void onClick(View v)
			{
				getSelectManager().performClick(position);
				if (mListener != null)
				{
					mListener.onClickItem(v, position, model, DealAttrAdapter.this);
				}
			}
		});
	}

	public interface DealAttrAdapterListener
	{
		public void onClickItem(View v, int position, Deal_attrValueModel model, DealAttrAdapter adapter);
	}
}
