package com.fanwe.library.dialog;

import android.app.Activity;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;
import android.widget.LinearLayout;

import com.fanwe.library.R;
import com.fanwe.library.utils.SDViewUtil;

/**
 * 带标题，输入框，确定按钮和取消按钮的窗口
 * 
 * @author js02
 * 
 */
public class SDDialogInput extends SDDialogCustom
{

	public EditText mEtContent;

	private SDDialogInputListener mListener;

	public SDDialogInputListener getmListener()
	{
		return mListener;
	}

	public SDDialogInput setmListener(SDDialogInputListener mListener)
	{
		this.mListener = mListener;
		return this;
	}

	public SDDialogInput()
	{
		super();
	}

	public SDDialogInput(Activity activity)
	{
		super(activity);
	}

	@Override
	protected void init()
	{
		super.init();
		setmListener(new SDDialogCustomListener()
		{

			@Override
			public void onDismiss(SDDialogCustom dialog)
			{
				if (mListener != null)
				{
					mListener.onDismiss(SDDialogInput.this);
				}
			}

			@Override
			public void onClickConfirm(View v, SDDialogCustom dialog)
			{
				if (mListener != null)
				{
					mListener.onClickConfirm(v, mEtContent.getText().toString(), SDDialogInput.this);
				}
			}

			@Override
			public void onClickCancel(View v, SDDialogCustom dialog)
			{
				if (mListener != null)
				{
					mListener.onClickCancel(v, SDDialogInput.this);
				}
			}
		});
		addEdittext();
	}

	public SDDialogInput setTextContent(String text)
	{
		if (TextUtils.isEmpty(text))
		{
			mEtContent.setText("");
		} else
		{
			mEtContent.setText(text);
		}
		return this;
	}

	private void addEdittext()
	{
		View view = SDViewUtil.inflate(R.layout.dialog_input, null);

		mEtContent = (EditText) view.findViewById(R.id.dialog_input_et_content);
		view.setBackgroundDrawable(mDrawableManager.getLayerWhiteStrokeItemSingle(true));

		LinearLayout.LayoutParams params = SDViewUtil.getLayoutParamsLinearLayoutMW();
		int margin = SDViewUtil.dp2px(10);
		params.bottomMargin = margin;
		params.leftMargin = margin;
		params.rightMargin = margin;
		params.topMargin = margin;

		setCustomView(view, params);
	}

	@Override
	public void show()
	{
		super.show();
		SDViewUtil.showInputMethod(mEtContent, 200);
	}

	@Override
	public void dismiss()
	{
		SDViewUtil.hideInputMethod(mEtContent);
		super.dismiss();
	}

	public interface SDDialogInputListener
	{
		public void onClickCancel(View v, SDDialogInput dialog);

		public void onClickConfirm(View v, String content, SDDialogInput dialog);

		public void onDismiss(SDDialogInput dialog);
	}

}
