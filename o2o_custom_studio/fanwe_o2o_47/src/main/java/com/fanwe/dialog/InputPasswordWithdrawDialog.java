package com.fanwe.dialog;

import android.app.Activity;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import com.fanwe.dialog.InputPasswordValidateDialog.InputPasswordValidateDialogListener;
import com.fanwe.library.dialog.SDDialogCustom;
import com.fanwe.library.dialog.SDDialogCustom.SDDialogCustomListener;
import com.fanwe.library.utils.SDViewUtil;
import com.fanwe.o2o.newo2o.R;
import com.fanwe.utils.SDFormatUtil;
import com.lidroid.xutils.ViewUtils;
import com.lidroid.xutils.view.annotation.ViewInject;

/**
 * 验证密码提现窗口
 * 
 * @author Administrator
 * 
 */
public class InputPasswordWithdrawDialog extends SDDialogCustom implements SDDialogCustomListener
{

	@ViewInject(R.id.tv_money)
	public TextView mTv_money;

	@ViewInject(R.id.et_password)
	public EditText mEt_password;

	private InputPasswordValidateDialogListener mListenerValidatePassword;

	public void setmListenerValidatePassword(InputPasswordValidateDialogListener listenerValidatePassword)
	{
		this.mListenerValidatePassword = listenerValidatePassword;
	}

	public InputPasswordWithdrawDialog()
	{
		super();
	}

	public InputPasswordWithdrawDialog(Activity activity)
	{
		super(activity);
	}

	@Override
	protected void init()
	{
		super.init();

		setmDismissAfterClick(false);
		setmListener(this);
		setCustomView(R.layout.dialog_input_password_withdraw);
		ViewUtils.inject(this, getContentView());

		setTextTitle("请输入登录密码");
	}

	public void setMoney(String money)
	{
		mTv_money.setText(SDFormatUtil.formatMoneyChina(money));
	}

	@Override
	public void onClickCancel(View v, SDDialogCustom dialog)
	{
		dialog.dismiss();
	}

	@Override
	public void onClickConfirm(View v, SDDialogCustom dialog)
	{
		String password = mEt_password.getText().toString();
		if (TextUtils.isEmpty(password))
		{
			showTip("请输入密码");
			return;
		}
		if (mListenerValidatePassword != null)
		{
			mListenerValidatePassword.onSuccess(null, password);
		}
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
}
