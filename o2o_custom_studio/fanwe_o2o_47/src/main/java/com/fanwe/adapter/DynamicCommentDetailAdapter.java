package com.fanwe.adapter;

import java.util.List;

import android.app.Activity;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.TextView;

import com.fanwe.library.adapter.SDSimpleAdapter;
import com.fanwe.library.utils.SDViewBinder;
import com.fanwe.library.utils.ViewHolder;
import com.fanwe.model.DynamicReplyModel;
import com.fanwe.o2o.newo2o.R;

public class DynamicCommentDetailAdapter extends SDSimpleAdapter<DynamicReplyModel>
{

	private DynamicCommentDetailAdapter_onClick mListener;

	public void setListener(DynamicCommentDetailAdapter_onClick mListener)
	{
		this.mListener = mListener;
	}

	public DynamicCommentDetailAdapter(List<DynamicReplyModel> listModel, Activity activity)
	{
		super(listModel, activity);
	}

	@Override
	public int getLayoutId(int position, View convertView, ViewGroup parent)
	{
		return R.layout.item_dynamic_comment_detail;
	}

	@Override
	public void bindData(int position, View convertView, ViewGroup parent, final DynamicReplyModel model)
	{
		TextView tv_comment_user = ViewHolder.get(R.id.tv_comment_user, convertView);
		TextView tv_comment_content = ViewHolder.get(R.id.tv_comment_content, convertView);

		SDViewBinder.setTextView(tv_comment_user, model.getUser_name());
		SDViewBinder.setTextView(tv_comment_content, ":" + model.getContent());

		tv_comment_user.setOnClickListener(new OnClickListener()
		{
			@Override
			public void onClick(View v)
			{
				if (mListener != null)
				{
					mListener.onClickCommentUsername(model, v);
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
					mListener.onClickComment(model, v);
				}
			}
		});
	}

	public interface DynamicCommentDetailAdapter_onClick
	{
		public void onClickComment(DynamicReplyModel model, View v);

		public void onClickCommentUsername(DynamicReplyModel model, View v);
	}

}
