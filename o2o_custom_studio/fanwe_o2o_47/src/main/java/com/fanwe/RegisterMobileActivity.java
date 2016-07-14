package com.fanwe;

import android.os.Bundle;
import android.text.TextUtils;
import android.widget.LinearLayout;

import com.fanwe.common.CommonInterface;
import com.fanwe.constant.Constant.TitleType;
import com.fanwe.event.EnumEventTag;
import com.fanwe.http.InterfaceServer;
import com.fanwe.http.SDRequestCallBack;
import com.fanwe.library.common.SDActivityManager;
import com.fanwe.library.customview.ClearEditText;
import com.fanwe.library.customview.SDSendValidateButton;
import com.fanwe.library.customview.SDSendValidateButton.SDSendValidateButtonListener;
import com.fanwe.library.dialog.SDDialogManager;
import com.fanwe.library.title.SDTitleItem;
import com.fanwe.library.utils.SDToast;
import com.fanwe.library.utils.SDViewUtil;
import com.fanwe.model.LocalUserModel;
import com.fanwe.model.RequestModel;
import com.fanwe.model.Sms_send_sms_codeActModel;
import com.fanwe.model.User_infoModel;
import com.fanwe.o2o.newo2o.R;
import com.fanwe.work.AppRuntimeWorker;
import com.lidroid.xutils.exception.HttpException;
import com.lidroid.xutils.http.ResponseInfo;
import com.lidroid.xutils.view.annotation.ViewInject;
import com.sunday.eventbus.SDBaseEvent;

/**
 * 手机号注册界面
 * 
 * @author Administrator
 * 
 */
public class RegisterMobileActivity extends BaseActivity
{

	public static final String EXTRA_PHONE_NUMBER = "extra_phone_number";

	@ViewInject(R.id.ll_reference)
	private LinearLayout mLl_reference;

	@ViewInject(R.id.et_mobile)
	private ClearEditText mEtMobile;

	@ViewInject(R.id.et_code)
	private ClearEditText mEtCode;

	@ViewInject(R.id.et_pwd)
	private ClearEditText mEt_pwd;

	@ViewInject(R.id.et_pwd_confirm)
	private ClearEditText mEt_pwd_confirm;

	@ViewInject(R.id.et_reference)
	private ClearEditText mEt_reference;

	@ViewInject(R.id.btn_send_code)
	private SDSendValidateButton mBtnSendCode;

	private String mStrMobile;
	private String mStrCode;
	private String mStrPwd;
	private String mStrPwdConfirm;
	private String mStrReference;

	@Override
	protected void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
		setmTitleType(TitleType.TITLE);
		setContentView(R.layout.act_register_mobile);
		init();
	}

	private void init()
	{
		initTitle();
		getIntentData();
		initViewState();
		initSDSendValidateButton();
	}

	private void initTitle()
	{
		mTitle.setMiddleTextTop("注册");

		mTitle.initRightItem(1);
		mTitle.getItemRight(0).setTextBot("提交");
	}

	@Override
	public void onCLickRight_SDTitleSimple(SDTitleItem v, int index)
	{
		clickSubmit();
	}

	private void initViewState()
	{
		int registerRebate = AppRuntimeWorker.getRegister_rebate();
		switch (registerRebate)
		{
		case 1:
		case 3:
			SDViewUtil.show(mLl_reference);
			break;

		default:
			SDViewUtil.hide(mLl_reference);
			break;
		}
	}

	private void getIntentData()
	{
		String mobile = getIntent().getStringExtra(EXTRA_PHONE_NUMBER);
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

	private void clickSubmit()
	{
		if (validateParams())
		{
			requestRegister();
		}
	}

	private boolean validateParams()
	{
		if (TextUtils.isEmpty(mStrMobile))
		{
			SDToast.showToast("请输入手机号码!");
			return false;
		}
		if (mStrMobile.length() != 11)
		{
			SDToast.showToast("请输入11位的手机号码");
			return false;
		}

		mStrCode = mEtCode.getText().toString().trim();
		if (TextUtils.isEmpty(mStrCode))
		{
			SDToast.showToast("请输入验证码!");
			return false;
		}

		mStrPwd = mEt_pwd.getText().toString().trim();
		mStrPwdConfirm = mEt_pwd_confirm.getText().toString().trim();

		mStrReference = mEt_reference.getText().toString().trim();
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
			mEtMobile.requestFocus();
			return;
		}

		CommonInterface.requestValidateCode(mStrMobile, 0, new SDRequestCallBack<Sms_send_sms_codeActModel>()
		{

			@Override
			public void onSuccess(ResponseInfo<String> responseInfo)
			{
				if (actModel.getStatus() > 0)
				{
					mBtnSendCode.setmDisableTime(actModel.getLesstime());
					mBtnSendCode.startTickWork();
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
	private void requestRegister()
	{
		RequestModel model = new RequestModel();
		model.putCtl("user");
		model.putAct("dophlogin");
		model.put("mobile", mStrMobile);
		model.put("sms_verify", mStrCode);
		model.put("pwd", mStrPwd);
		model.put("pwd_confirm", mStrPwdConfirm);
		model.put("invite_mobile", mStrReference);
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
				if (actModel.getStatus() == 1)
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
		finish();
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
			if (SDActivityManager.getInstance().isLastActivity(this))
			{
				requestSendCode();
			}
			break;

		default:
			break;
		}
	}

}
