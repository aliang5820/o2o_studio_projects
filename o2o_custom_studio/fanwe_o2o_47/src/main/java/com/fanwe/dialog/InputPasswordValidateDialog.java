package com.fanwe.dialog;

import android.app.Activity;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;

import com.fanwe.common.CommonInterface;
import com.fanwe.http.SDRequestCallBack;
import com.fanwe.library.dialog.SDDialogCustom;
import com.fanwe.library.dialog.SDDialogCustom.SDDialogCustomListener;
import com.fanwe.library.dialog.SDDialogManager;
import com.fanwe.library.utils.SDViewUtil;
import com.fanwe.model.BaseActModel;
import com.fanwe.o2o.newo2o.R;
import com.lidroid.xutils.ViewUtils;
import com.lidroid.xutils.http.ResponseInfo;
import com.lidroid.xutils.view.annotation.ViewInject;

/**
 * 验证密码窗口
 * 
 * @author Administrator
 * 
 */
public class InputPasswordValidateDialog extends SDDialogCustom implements SDDialogCustomListener
{

	@ViewInject(R.id.et_password)
	public EditText mEt_password;

	private InputPasswordValidateDialogListener mListenerValidatePassword;

	public void setmListenerValidatePassword(InputPasswordValidateDialogListener listenerValidatePassword)
	{
		this.mListenerValidatePassword = listenerValidatePassword;
	}

	public InputPasswordValidateDialog()
	{
		super();
	}

	public InputPasswordValidateDialog(Activity activity)
	{
		super(activity);
	}

	@Override
	protected void init()
	{
		super.init();

		setmDismissAfterClick(false);
		setmListener(this);
		setCustomView(R.layout.dialog_input_password_validate);
		ViewUtils.inject(this, getContentView());
	}

	@Override
	public void onClickCancel(View v, SDDialogCustom dialog)
	{
		dismiss();
	}

	@Override
	public void onClickConfirm(View v, SDDialogCustom dialog)
	{
		final String password = mEt_password.getText().toString();
		if (TextUtils.isEmpty(password))
		{
			showTip("请输入密码");
			return;
		}
		CommonInterface.requestValidatePassword(password, new SDRequestCallBack<BaseActModel>()
		{
			@Override
			public void onStart()
			{
				SDDialogManager.showProgressDialog("");
				super.onStart();
			}

			@Override
			public void onSuccess(ResponseInfo<String> responseInfo)
			{
				if (actModel.getStatus() > 0)
				{

				} else
				{
					mEt_password.setText("");
					SDViewUtil.showInputMethod(mEt_password, 200);
				}

				if (mListenerValidatePassword != null)
				{
					mListenerValidatePassword.onSuccess(actModel, password);
				}
				super.onSuccess(responseInfo);
			}

			@Override
			public void onFinish()
			{
				SDDialogManager.dismissProgressDialog();
				super.onFinish();
			}
		});
	}

	@Override
	public void onDismiss(SDDialogCustom dialog)
	{

	}

	@Override
	public void show()
	{
		super.show();
		SDViewUtil.showInputMethod(mEt_password, 200);
	}

	@Override
	public void dismiss()
	{
		SDViewUtil.hideInputMethod(mEt_password);
		super.dismiss();
	}

	public interface InputPasswordValidateDialogListener
	{
		public void onSuccess(BaseActModel actModel, String content);
	}
}
