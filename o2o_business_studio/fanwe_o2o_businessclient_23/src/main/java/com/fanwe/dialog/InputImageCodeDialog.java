package com.fanwe.dialog;

import android.app.Activity;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;

import com.fanwe.businessclient.R;
import com.fanwe.common.ImageLoaderManager;
import com.fanwe.config.AppConfig;
import com.fanwe.event.EnumEventTag;
import com.fanwe.library.dialog.SDDialogCustom;
import com.fanwe.library.utils.SDViewBinder;
import com.fanwe.library.utils.SDViewUtil;
import com.lidroid.xutils.ViewUtils;
import com.lidroid.xutils.view.annotation.ViewInject;
import com.sunday.eventbus.SDEventManager;

public class InputImageCodeDialog extends SDDialogCustom
{

	@ViewInject(R.id.iv_image_code)
	public ImageView mIv_image_code;

	@ViewInject(R.id.et_code)
	public EditText mEt_code;

	private String mStrUrl;

	private InputImageCodeDialogListener mListenerImageCode;

	public void setmListenerImageCode(InputImageCodeDialogListener mListenerImageCode)
	{
		this.mListenerImageCode = mListenerImageCode;
	}

	public InputImageCodeDialog()
	{
		super();
	}

	public InputImageCodeDialog(Activity activity)
	{
		super(activity);
	}

	@Override
	protected void init()
	{
		super.init();
		setmDismissAfterClick(false);
		setCustomView(R.layout.dialog_input_image_code);
		ViewUtils.inject(this, getContentView());

		mIv_image_code.setOnClickListener(new View.OnClickListener()
		{

			@Override
			public void onClick(View v)
			{
				SDViewBinder.setImageView(mStrUrl, mIv_image_code, ImageLoaderManager.getOptionsNoCache());
			}
		});

		setmListener(new SDDialogCustomListener()
		{

			@Override
			public void onDismiss(SDDialogCustom dialog)
			{
				if (mListenerImageCode != null)
				{
					mListenerImageCode.onDismiss(dialog);
				}
			}

			@Override
			public void onClickConfirm(View v, SDDialogCustom dialog)
			{
				String content = mEt_code.getText().toString();
				if (TextUtils.isEmpty(content))
				{
					showTip("请输入图形验证码");
					return;
				}
				AppConfig.setImageCode(content);
				if (mListenerImageCode != null)
				{
					mListenerImageCode.onClickConfirm(content, v, dialog);
				}
				SDEventManager.post(EnumEventTag.CONFIRM_IMAGE_CODE.ordinal());
				dialog.dismiss();
			}

			@Override
			public void onClickCancel(View v, SDDialogCustom dialog)
			{
				if (mListenerImageCode != null)
				{
					mListenerImageCode.onClickCancel(v, dialog);
				}
				dialog.dismiss();
			}
		});
	}

	public void setImage(String url)
	{
		this.mStrUrl = url;
		SDViewBinder.setImageView(mStrUrl, mIv_image_code, ImageLoaderManager.getOptionsNoCache());
	}

	@Override
	public void show()
	{
		super.show();
		SDViewUtil.showInputMethod(mEt_code, 200);
	}

	@Override
	public void dismiss()
	{
		SDViewUtil.hideInputMethod(mEt_code);
		super.dismiss();
	}

	public interface InputImageCodeDialogListener
	{
		public void onClickCancel(View v, SDDialogCustom dialog);

		public void onClickConfirm(String content, View v, SDDialogCustom dialog);

		public void onDismiss(SDDialogCustom dialog);
	}

}
