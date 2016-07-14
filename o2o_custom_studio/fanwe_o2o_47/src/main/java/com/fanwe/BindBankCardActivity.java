package com.fanwe;

import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.EditText;
import android.widget.TextView;

import com.fanwe.common.CommonInterface;
import com.fanwe.constant.Constant.TitleType;
import com.fanwe.event.EnumEventTag;
import com.fanwe.http.InterfaceServer;
import com.fanwe.http.SDRequestCallBack;
import com.fanwe.library.common.SDActivityManager;
import com.fanwe.library.customview.SDSendValidateButton;
import com.fanwe.library.customview.SDSendValidateButton.SDSendValidateButtonListener;
import com.fanwe.library.dialog.SDDialogManager;
import com.fanwe.library.utils.SDToast;
import com.fanwe.library.utils.SDViewBinder;
import com.fanwe.library.utils.SDViewUtil;
import com.fanwe.model.RequestModel;
import com.fanwe.model.Sms_send_sms_codeActModel;
import com.fanwe.model.Uc_money_do_withdrawActModel;
import com.fanwe.o2o.newo2o.R;
import com.lidroid.xutils.http.ResponseInfo;
import com.lidroid.xutils.view.annotation.ViewInject;
import com.sunday.eventbus.SDBaseEvent;

/**
 * 绑定银行卡
 * 
 * @author Administrator
 * 
 */
public class BindBankCardActivity extends BaseActivity
{
	private static final String SPLIT_STRING = " ";

	/** 提现金额 (String) */
	public static final String EXTRA_WITHDRAW_MONEY = "extra_withdraw_money";
	/** 手机号 (String) */
	public static final String EXTRA_BANK_MOBILE = "extra_bank_mobile";
	/** 真实姓名 (String) */
	public static final String EXTRA_REAL_NAME = "extra_real_name";

	@ViewInject(R.id.et_bank_account)
	private EditText mEt_bank_account;

	@ViewInject(R.id.et_bank_name)
	private EditText mEt_bank_name;

	@ViewInject(R.id.et_bank_user)
	private EditText mEt_bank_user;

	@ViewInject(R.id.et_bank_mobile)
	private EditText mEt_bank_mobile;

	@ViewInject(R.id.et_code)
	private EditText mEt_code;

	@ViewInject(R.id.btn_send_code)
	private SDSendValidateButton mBtn_send_code;

	@ViewInject(R.id.tv_submit)
	private TextView mTv_submit;

	/** 银行卡卡号 */
	private String mStrBankAccount;
	/** 开户行 */
	private String mStrBankName;
	/** 持卡人真实姓名 */
	private String mStrBankUser;
	/** 银行预留手机号 */
	private String mStrBankMobile;
	/** 验证码 */
	private String mStrCode;
	/** 提现金额 */
	private String mStrMoney;

	@Override
	protected void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
		setmTitleType(TitleType.TITLE);
		setContentView(R.layout.act_bind_bank_card);
		init();
	}

	private void init()
	{
		getIntentData();
		initTitle();
		initSDSendValidateButton();
		register();
	}

	private void register()
	{
		mEt_bank_account.addTextChangedListener(new TextWatcher()
		{

			private String nStrLastFormat;

			@Override
			public void onTextChanged(CharSequence s, int start, int before, int count)
			{
			}

			@Override
			public void beforeTextChanged(CharSequence s, int start, int count, int after)
			{
			}

			@Override
			public void afterTextChanged(Editable s)
			{
				String text = s.toString();
				text = text.replace(SPLIT_STRING, "");
				StringBuilder sb = new StringBuilder();
				if (!text.equals(nStrLastFormat))
				{
					nStrLastFormat = text;
					for (int i = 0; i < text.length(); i++)
					{
						sb.append(text.charAt(i));
						if ((i + 1) % 4 == 0)
						{
							sb.append(SPLIT_STRING);
						}
					}
					mEt_bank_account.setText(sb);
					mEt_bank_account.setSelection(sb.length());
				}
			}
		});

		mTv_submit.setOnClickListener(new OnClickListener()
		{

			@Override
			public void onClick(View v)
			{
				clickSubmit();
			}
		});
	}

	protected void clickSubmit()
	{
		if (validateParmas())
		{
			SDViewUtil.hideInputMethod(mEt_code);
			requestWithdraw();
		}
	}

	private void requestWithdraw()
	{
		RequestModel model = new RequestModel();
		model.putCtl("uc_money");
		model.putAct("do_withdraw");
		model.put("bank_account", mStrBankAccount);
		model.put("bank_name", mStrBankName);
		model.put("bank_user", mStrBankUser);
		model.put("bank_mobile", mStrBankMobile);
		model.put("sms_verify", mStrCode);
		model.put("money", mStrMoney);

		InterfaceServer.getInstance().requestInterface(model, new SDRequestCallBack<Uc_money_do_withdrawActModel>()
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
					// 绑定成功，跳到提现详情页面
					Intent intent = new Intent(getApplicationContext(), WithdrawDetailActivity.class);
					intent.putExtra(WithdrawDetailActivity.EXTRA_BANK_ID, actModel.getWithdraw_id());
					intent.putExtra(WithdrawDetailActivity.EXTRA_WITHDRAW_BANK, actModel.getBank_name());
					intent.putExtra(WithdrawDetailActivity.EXTRA_WITHDRAW_MONEY, mStrMoney);
					startActivity(intent);
					finish();
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

	private boolean validateParmas()
	{
		mStrBankAccount = mEt_bank_account.getText().toString();
		mStrBankAccount = mStrBankAccount.replace(SPLIT_STRING, "");
		if (isEmpty(mStrBankAccount))
		{
			SDToast.showToast("请输入银行卡号");
			return false;
		}

		mStrBankName = mEt_bank_name.getText().toString();
		if (isEmpty(mStrBankName))
		{
			SDToast.showToast("请输入开户行名称");
			return false;
		}

		mStrBankUser = mEt_bank_user.getText().toString();
		if (isEmpty(mStrBankUser))
		{
			SDToast.showToast("请输入持卡人姓名");
			return false;
		}

		if (isEmpty(mStrBankMobile))
		{
			SDToast.showToast("请先发送验证码");
			return false;
		}

		mStrCode = mEt_code.getText().toString();
		if (isEmpty(mStrCode))
		{
			SDToast.showToast("请输入验证码");
			return false;
		}

		return true;
	}

	private void initSDSendValidateButton()
	{
		mBtn_send_code.setmListener(new SDSendValidateButtonListener()
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

	protected void requestSendCode()
	{
		mStrBankMobile = mEt_bank_mobile.getText().toString();
		if (isEmpty(mStrBankMobile))
		{
			SDToast.showToast("请输入手机号");
			return;
		}

		CommonInterface.requestValidateCode(mStrBankMobile, 0, new SDRequestCallBack<Sms_send_sms_codeActModel>()
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
					mBtn_send_code.setmDisableTime(actModel.getLesstime());
					mBtn_send_code.startTickWork();
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

	private void getIntentData()
	{
		mStrMoney = getIntent().getStringExtra(EXTRA_WITHDRAW_MONEY);
		String mobile = getIntent().getStringExtra(EXTRA_BANK_MOBILE);
		String realName = getIntent().getStringExtra(EXTRA_REAL_NAME);

		SDViewBinder.setTextView(mEt_bank_mobile, mobile);
		SDViewBinder.setTextView(mEt_bank_user, realName);
		if (!isEmpty(realName))
		{
			mEt_bank_user.setEnabled(false);
		}
	}

	private void initTitle()
	{
		mTitle.setMiddleTextTop("绑定银行卡");
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
