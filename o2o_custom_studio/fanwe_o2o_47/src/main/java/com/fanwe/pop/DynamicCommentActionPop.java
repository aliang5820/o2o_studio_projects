package com.fanwe.pop;

import android.view.View;
import android.view.View.OnClickListener;
import android.widget.LinearLayout;

import com.fanwe.library.popupwindow.SDPWindowBase;
import com.fanwe.library.utils.SDViewUtil;
import com.fanwe.o2o.newo2o.R;

public class DynamicCommentActionPop extends SDPWindowBase
{

	private LinearLayout mLlFav;
	private LinearLayout mLlReply;

	private DynamicCommentActionPopListener mListener;

	public void setmListener(DynamicCommentActionPopListener mListener)
	{
		this.mListener = mListener;
	}

	public DynamicCommentActionPop()
	{
		super();
		init();
	}

	private void init()
	{
		setWidth((int) (SDViewUtil.getScreenWidth() * 0.5));
		setContentView(R.layout.pop_dynamic_comment_action);
		mLlFav = (LinearLayout) getContentView().findViewById(R.id.pop_dynamic_comment_action_ll_fav);
		mLlReply = (LinearLayout) getContentView().findViewById(R.id.pop_dynamic_comment_action_ll_reply);

		mLlFav.setOnClickListener(new OnClickListener()
		{

			@Override
			public void onClick(View v)
			{
				if (mListener != null)
				{
					mListener.onClickFav(v);
				}
			}
		});

		mLlReply.setOnClickListener(new OnClickListener()
		{

			@Override
			public void onClick(View v)
			{
				if (mListener != null)
				{
					mListener.onClickReply(v);
				}
			}
		});

	}

	public interface DynamicCommentActionPopListener
	{
		public void onClickFav(View v);

		public void onClickReply(View v);
	}

}
