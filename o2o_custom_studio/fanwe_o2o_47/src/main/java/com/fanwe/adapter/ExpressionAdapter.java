package com.fanwe.adapter;

import java.util.List;

import android.app.Activity;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.View.OnLongClickListener;
import android.view.ViewGroup;

import com.fanwe.library.adapter.SDSimpleAdapter;
import com.fanwe.library.utils.SDToast;
import com.fanwe.library.utils.ViewHolder;
import com.fanwe.model.ExpressionModel;
import com.fanwe.o2o.newo2o.R;
import com.fanwe.span.SDNetImageTextView;

public class ExpressionAdapter extends SDSimpleAdapter<ExpressionModel>
{

	private ExpressionAdapterListener mListener;

	public void setListener(ExpressionAdapterListener listener)
	{
		this.mListener = listener;
	}

	public ExpressionAdapter(List<ExpressionModel> listModel, Activity activity)
	{
		super(listModel, activity);
	}

	@Override
	public int getLayoutId(int position, View convertView, ViewGroup parent)
	{
		return R.layout.item_expression;
	}

	@Override
	public void bindData(final int position, View convertView, ViewGroup parent, final ExpressionModel model)
	{
		SDNetImageTextView itv_expression = ViewHolder.get(R.id.itv_expression, convertView);
		itv_expression.setImage(model.getFilename());
		itv_expression.setOnClickListener(new OnClickListener()
		{

			@Override
			public void onClick(View v)
			{
				if (mListener != null)
				{
					mListener.onItemClick(model, position);
				}
			}
		});
		itv_expression.setOnLongClickListener(new OnLongClickListener()
		{

			@Override
			public boolean onLongClick(View v)
			{
				SDToast.showToast(model.getTitle());
				return true;
			}
		});
	}

	public interface ExpressionAdapterListener
	{
		public void onItemClick(ExpressionModel model, int position);
	}

}
