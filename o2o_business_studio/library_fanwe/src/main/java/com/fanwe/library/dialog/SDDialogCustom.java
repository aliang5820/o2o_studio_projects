package com.fanwe.library.dialog;

import android.app.Activity;
import android.content.DialogInterface;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.fanwe.library.R;
import com.fanwe.library.utils.SDTimer;
import com.fanwe.library.utils.SDTimer.SDTimerListener;
import com.fanwe.library.utils.SDViewUtil;

public class SDDialogCustom extends SDDialogBase
{

	public View mView;
	public TextView mTvTitle;
	public TextView mTvTip;
	public LinearLayout mLlContent;
	public TextView mTvCancel;
	public TextView mTvConfirm;

	private SDDialogCustomListener mListener;

	private SDTimer mTimer = new SDTimer();

	public SDDialogCustom setmListener(SDDialogCustomListener mListener)
	{
		this.mListener = mListener;
		return this;
	}

	public SDDialogCustom(Activity activity)
	{
		super(activity);
		init();
	}

	public SDDialogCustom()
	{
		super();
		init();
	}

	protected void init()
	{
		mView = LayoutInflater.from(getContext()).inflate(R.layout.dialog_custom, null);
		mTvTitle = (TextView) mView.findViewById(R.id.dialog_custom_tv_title);
		mTvTip = (TextView) mView.findViewById(R.id.dialog_custom_tv_tip);
		mLlContent = (LinearLayout) mView.findViewById(R.id.dialog_custom_ll_content);
		mTvCancel = (TextView) mView.findViewById(R.id.dialog_custom_tv_cancel);
		mTvConfirm = (TextView) mView.findViewById(R.id.dialog_custom_tv_confirm);

		setDialogView(mView);
		initViewStates();
	}

	private void initViewStates()
	{
		mTvTitle.setVisibility(View.GONE);
		mTvCancel.setVisibility(View.GONE);
		mTvConfirm.setVisibility(View.GONE);

		mTvCancel.setOnClickListener(this);
		mTvConfirm.setOnClickListener(this);

		setTextColorCancel(mConfig.getmMainColor());
		setTextColorConfirm(mConfig.getmMainColor());

		setTextTitle("提示").setTextConfirm("确定").setTextCancel("取消");
	}

	public SDDialogCustom setCustomView(View view)
	{
		setCustomView(view, null);
		return this;
	}

	public SDDialogCustom setCustomView(View view, LinearLayout.LayoutParams params)
	{
		mLlContent.removeAllViews();
		if (params == null)
		{
			params = SDViewUtil.getLayoutParamsLinearLayoutMM();
		}
		mLlContent.addView(view, params);
		return this;
	}

	public SDDialogCustom setCustomView(int layoutId)
	{
		mLlContent.removeAllViews();
		LayoutInflater.from(getContext()).inflate(layoutId, mLlContent, true);
		return this;
	}

	private void changeBackground()
	{
		if (mTvCancel.getVisibility() == View.VISIBLE && mTvConfirm.getVisibility() == View.VISIBLE)
		{
			SDViewUtil.setBackgroundDrawable(mTvCancel, getBackgroundBottomLeft());
			SDViewUtil.setBackgroundDrawable(mTvConfirm, getBackgroundBottomRight());
		} else if (mTvCancel.getVisibility() == View.VISIBLE)
		{
			SDViewUtil.setBackgroundDrawable(mTvCancel, getBackgroundBottomSingle());
		} else if (mTvConfirm.getVisibility() == View.VISIBLE)
		{
			SDViewUtil.setBackgroundDrawable(mTvConfirm, getBackgroundBottomSingle());
		}
	}

	// tip
	public void showTip(String tip, long time)
	{
		if (!TextUtils.isEmpty(tip) && time > 0)
		{
			mTvTip.setText(tip);
			SDViewUtil.show(mTvTip);
			mTimer.startWork(time, new SDTimerListener()
			{

				@Override
				public void onWorkMain()
				{
					mTvTip.setText("");
					SDViewUtil.hide(mTvTip);
				}

				@Override
				public void onWork()
				{
				}
			});
		}
	}

	public void showTip(String tip)
	{
		showTip(tip, 2000);
	}

	// ---------------------------color

	public SDDialogCustom setTextColorTitle(int color)
	{
		mTvTitle.setTextColor(color);
		return this;
	}

	public SDDialogCustom setTextColorCancel(int color)
	{
		mTvCancel.setTextColor(color);
		return this;
	}

	public SDDialogCustom setTextColorConfirm(int color)
	{
		mTvConfirm.setTextColor(color);
		return this;
	}

	// ---------------------------text
	public SDDialogCustom setTextTitle(String text)
	{
		if (TextUtils.isEmpty(text))
		{
			mTvTitle.setVisibility(View.GONE);
		} else
		{
			mTvTitle.setVisibility(View.VISIBLE);
			mTvTitle.setText(text);
		}
		return this;
	}

	public SDDialogCustom setTextCancel(String text)
	{
		if (TextUtils.isEmpty(text))
		{
			mTvCancel.setVisibility(View.GONE);
		} else
		{
			mTvCancel.setVisibility(View.VISIBLE);
			mTvCancel.setText(text);
		}
		changeBackground();
		return this;
	}

	public SDDialogCustom setTextConfirm(String text)
	{
		if (TextUtils.isEmpty(text))
		{
			mTvConfirm.setVisibility(View.GONE);
		} else
		{
			mTvConfirm.setVisibility(View.VISIBLE);
			mTvConfirm.setText(text);
		}
		changeBackground();
		return this;
	}

	@Override
	public void onClick(View v)
	{
		if (v == mTvCancel)
		{
			clickCancel(v);
		} else if (v == mTvConfirm)
		{
			clickConfirm(v);
		}

	}

	private void clickCancel(View v)
	{
		if (mListener != null)
		{
			mListener.onClickCancel(v, SDDialogCustom.this);
		}
		dismissClick();
	}

	private void clickConfirm(View v)
	{
		if (mListener != null)
		{
			mListener.onClickConfirm(v, SDDialogCustom.this);
		}
		dismissClick();
	}

	@Override
	public void onDismiss(DialogInterface dialog)
	{
		if (mListener != null)
		{
			mListener.onDismiss(SDDialogCustom.this);
		}
	}

	@Override
	public void dismiss()
	{
		mTimer.stopWork();
		super.dismiss();
	}

	public interface SDDialogCustomListener
	{
		public void onClickCancel(View v, SDDialogCustom dialog);

		public void onClickConfirm(View v, SDDialogCustom dialog);

		public void onDismiss(SDDialogCustom dialog);
	}
}
