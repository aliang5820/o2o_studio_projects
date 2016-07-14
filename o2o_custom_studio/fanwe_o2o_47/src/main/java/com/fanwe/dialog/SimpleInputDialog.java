package com.fanwe.dialog;

import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;

import com.fanwe.library.dialog.SDDialogBase;
import com.fanwe.library.utils.SDViewUtil;
import com.fanwe.o2o.newo2o.R;
import com.lidroid.xutils.ViewUtils;
import com.lidroid.xutils.view.annotation.ViewInject;

public class SimpleInputDialog extends SDDialogBase
{

	@ViewInject(R.id.ll_send)
	public LinearLayout mLl_send;

	@ViewInject(R.id.et_content)
	public EditText mEt_content;

	@ViewInject(R.id.btn_send)
	public Button mBtn_send;

	private InputPopListener mListener;

	public void setmListener(InputPopListener mListener)
	{
		this.mListener = mListener;
	}

	public SimpleInputDialog()
	{
		super(R.style.dialogBase);
		init();
	}

	private void init()
	{
		setContentView(R.layout.dialog_simple_input);
		setCanceledOnTouchOutside(true);
		ViewUtils.inject(this, getDialogView());

		mBtn_send.setOnClickListener(new View.OnClickListener()
		{

			@Override
			public void onClick(View v)
			{
				if (mListener != null)
				{
					mListener.onClickSend(mEt_content.getText().toString(), v);
				}
			}
		});
	}

	@Override
	public void show()
	{
		paddings(0);
		super.show();
		SDViewUtil.showInputMethod(mEt_content, 200);
	}

	@Override
	public void dismiss()
	{
		SDViewUtil.hideInputMethod(mEt_content);
		super.dismiss();
	}

	public interface InputPopListener
	{
		public void onClickSend(String content, View v);
	}

}
