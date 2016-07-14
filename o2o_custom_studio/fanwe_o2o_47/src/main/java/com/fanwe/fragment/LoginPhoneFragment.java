package com.fanwe.fragment;

import android.os.Bundle;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.TextView;

import com.fanwe.common.CommonInterface;
import com.fanwe.event.EnumEventTag;
import com.fanwe.http.InterfaceServer;
import com.fanwe.http.SDRequestCallBack;
import com.fanwe.library.common.SDActivityManager;
import com.fanwe.library.customview.ClearEditText;
import com.fanwe.library.customview.SDSendValidateButton;
import com.fanwe.library.customview.SDSendValidateButton.SDSendValidateButtonListener;
import com.fanwe.library.dialog.SDDialogManager;
import com.fanwe.library.utils.SDToast;
import com.fanwe.model.LocalUserModel;
import com.fanwe.model.RequestModel;
import com.fanwe.model.Sms_send_sms_codeActModel;
import com.fanwe.model.User_infoModel;
import com.fanwe.o2o.newo2o.R;
import com.lidroid.xutils.exception.HttpException;
import com.lidroid.xutils.http.ResponseInfo;
import com.lidroid.xutils.view.annotation.ViewInject;
import com.sunday.eventbus.SDBaseEvent;

public class LoginPhoneFragment extends LoginBaseFragment
{

	public static final String EXTRA_PHONE_NUMBER = "extra_phone_number";

	@ViewInject(R.id.et_mobile)
	private ClearEditText mEtMobile;

	@ViewInject(R.id.et_code)
	private ClearEditText mEtCode;

	@ViewInject(R.id.btn_send_code)
	private SDSendValidateButton mBtnSendCode;

	@ViewInject(R.id.tv_login)
	private TextView mTv_login;

	private String mStrMobile;
	private String mStrCode;

	@Override
	protected View onCreateContentView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState)
	{
		return setContentView(R.layout.frag_phone_login);
	}

	@Override
	protected void init()
	{
		getIntentData();
		registeClick();
		initSDSendValidateButton();
	}

	private void getIntentData()
	{
		String mobile = getActivity().getIntent().getStringExtra(EXTRA_PHONE_NUMBER);
		if (!TextUtils.isEmpty(mobile))
		{
			mEtMobile.setText(mobile);
		}
	}

	/**
	 * 初始化发送验证码按钮
	 */
	private void initSDSendValidateButton()
	{
		mBtnSendCode.setmListener(new SDSendValidateButtonListener()
		{
			@Override
			public void onTick()
			{
			}

			@Override
			public void onClickSendValidateButton()
			{
				requestSendCode();
			}
		});
	}

	private void registeClick()
	{
		mTv_login.setOnClickListener(new OnClickListener()
		{

			@Override
			public void onClick(View v)
			{
				clickLogin();
			}
		});
	}

	/**
	 * 快捷登录
	 */
	private void clickLogin()
	{
		if (validateParams())
		{
			requestShortcutLogin();
		}
	}

	private boolean validateParams()
	{
		if (TextUtils.isEmpty(mStrMobile))
		{
			SDToast.showToast("请输入手机号码!");
			return false;
		}

		mStrCode = mEtCode.getText().toString();
		if (TextUtils.isEmpty(mStrCode))
		{
			SDToast.showToast("请输入验证码!");
			return false;
		}
		return true;
	}

	/**
	 * 
	 * 验证码接口
	 * 
	 */
	private void requestSendCode()
	{
		mStrMobile = mEtMobile.getText().toString();
		if (TextUtils.isEmpty(mStrMobile))
		{
			SDToast.showToast("请输入手机号码");
			return;
		}

		CommonInterface.requestValidateCode(mStrMobile, 0, new SDRequestCallBack<Sms_send_sms_codeActModel>()
		{

			@Override
			public void onSuccess(ResponseInfo<String> responseInfo)
			{
				switch (actModel.getStatus())
				{
				case -1:
					break;
				case 1:
					mBtnSendCode.setmDisableTime(actModel.getLesstime());
					mBtnSendCode.startTickWork();
					break;

				default:
					break;
				}
			}

			@Override
			public void onStart()
			{
				SDDialogManager.showProgressDialog("请稍候...");
			}

			@Override
			public void onFinish()
			{
				SDDialogManager.dismissProgressDialog();
			}

			@Override
			public void onFailure(HttpException error, String msg)
			{
			}
		});
	}

	/**
	 * 快捷登录接口
	 */
	private void requestShortcutLogin()
	{

		RequestModel model = new RequestModel();
		model.putCtl("user");
		model.putAct("dophlogin");
		model.put("is_login", 1);
		model.put("mobile", mStrMobile);
		model.put("sms_verify", mStrCode);
		SDRequestCallBack<User_infoModel> handler = new SDRequestCallBack<User_infoModel>()
		{

			@Override
			public void onStart()
			{
				SDDialogManager.showProgressDialog("请稍候...");
			}

			@Override
			public void onSuccess(ResponseInfo<String> responseInfo)
			{
				int status = actModel.getStatus();
				if (status == 1)
				{
					dealLoginNormalSuccess(actModel, true);
				}
			}

			@Override
			public void onFailure(HttpException error, String msg)
			{

			}

			@Override
			public void onFinish()
			{
				SDDialogManager.dismissProgressDialog();
			}
		};
		InterfaceServer.getInstance().requestInterface(model, handler);

	}

	protected void dealLoginNormalSuccess(User_infoModel actModel, boolean postEvent)
	{
		LocalUserModel.dealLoginSuccess(actModel, postEvent);
		getActivity().finish();
	}

	@Override
	public void onDestroy()
	{
		if (mBtnSendCode != null)
		{
			mBtnSendCode.stopTickWork();
		}
		super.onDestroy();
	}

	@Override
	public void onEventMainThread(SDBaseEvent event)
	{
		super.onEventMainThread(event);
		switch (EnumEventTag.valueOf(event.getTagInt()))
		{
		case CONFIRM_IMAGE_CODE:
			if (SDActivityManager.getInstance().isLastActivity(getActivity()))
			{
				requestSendCode();
			}
			break;

		default:
			break;
		}
	}
}