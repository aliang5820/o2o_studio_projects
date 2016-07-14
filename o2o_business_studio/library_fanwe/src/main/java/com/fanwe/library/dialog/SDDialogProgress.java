package com.fanwe.library.dialog;

import android.app.Activity;
import android.content.DialogInterface;
import android.graphics.Color;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.fanwe.library.R;
import com.fanwe.library.drawable.SDDrawable;
import com.fanwe.library.utils.SDViewUtil;

/**
 * 带环形进度条，和信息提示的窗口
 * 
 * @author js02
 * 
 */
public class SDDialogProgress extends SDDialogBase
{

	private LinearLayout mLlBlur = null;
	private TextView mTxtMsg = null;
	private ProgressBar mPbCircle = null;

	private SDDialogProgressListener mListener = null;

	public SDDialogProgress setmListener(SDDialogProgressListener mListener)
	{
		this.mListener = mListener;
		return this;
	}

	public SDDialogProgress setTextMsg(String msg)
	{
		if (msg != null)
		{
			mTxtMsg.setText(msg);
		}
		return this;
	}

	public SDDialogProgress(Activity activity)
	{
		super(activity);
		init();
	}

	public SDDialogProgress()
	{
		super();
		init();
	}

	private void init()
	{
		View view = View.inflate(getContext(), R.layout.dialog_progress, null);
		mLlBlur = (LinearLayout) view.findViewById(R.id.dialog_progress_ll_blur);
		mTxtMsg = (TextView) view.findViewById(R.id.dialog_progress_txt_progress_msg);
		mPbCircle = (ProgressBar) view.findViewById(R.id.dialog_progress_pb_progress);
		mPbCircle.setIndeterminateDrawable(getContext().getResources().getDrawable(R.drawable.rotate_progress_white));

		setDialogView(view, false);
		initViewStates();

	}

	private void initViewStates()
	{
		setCancelable(false);
		SDDrawable drawable = new SDDrawable().color(Color.parseColor("#000000")).cornerAll(mConfig.getmCornerRadius()).alpha(0x55);

		SDViewUtil.setBackgroundDrawable(mLlBlur, drawable);
	}

	public SDDialogProgress height(int height)
	{
		ViewGroup.LayoutParams params = mLlBlur.getLayoutParams();
		params.height = SDViewUtil.dp2px(height);
		mLlBlur.setLayoutParams(params);
		return this;
	}

	@Override
	public void onDismiss(DialogInterface dialog)
	{
		if (mListener != null)
		{
			mListener.onDismiss(SDDialogProgress.this);
		}
	}

	public interface SDDialogProgressListener
	{
		public void onDismiss(SDDialogProgress dialog);
	}

}
