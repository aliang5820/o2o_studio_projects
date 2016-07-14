package com.fanwe;

import java.util.List;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.EditText;
import android.widget.TextView;

import com.fanwe.constant.Constant.TitleType;
import com.fanwe.dialog.InputPasswordValidateDialog;
import com.fanwe.dialog.InputPasswordValidateDialog.InputPasswordValidateDialogListener;
import com.fanwe.dialog.InputPasswordWithdrawDialog;
import com.fanwe.http.InterfaceServer;
import com.fanwe.http.SDRequestCallBack;
import com.fanwe.library.adapter.SDSimpleTextAdapter;
import com.fanwe.library.dialog.SDDialogManager;
import com.fanwe.library.dialog.SDDialogMenu;
import com.fanwe.library.dialog.SDDialogMenu.SDDialogMenuListener;
import com.fanwe.library.utils.SDCollectionUtil;
import com.fanwe.library.utils.SDToast;
import com.fanwe.library.utils.SDTypeParseUtil;
import com.fanwe.model.BaseActModel;
import com.fanwe.model.RequestModel;
import com.fanwe.model.Uc_money_do_withdrawActModel;
import com.fanwe.model.Uc_money_withdraw_bank_listActModel;
import com.fanwe.model.UserBankModel;
import com.fanwe.o2o.newo2o.R;
import com.fanwe.utils.SDFormatUtil;
import com.lidroid.xutils.http.ResponseInfo;
import com.lidroid.xutils.view.annotation.ViewInject;

/**
 * 提现
 * 
 * @author Administrator
 * 
 */
public class WithdrawActivity extends BaseActivity
{

	@ViewInject(R.id.tv_bank)
	private TextView mTv_bank;

	@ViewInject(R.id.et_money)
	private EditText mEt_money;

	@ViewInject(R.id.tv_next)
	private TextView mTv_next;

	/** 提现金额 */
	private String mStrMoney;
	private Uc_money_withdraw_bank_listActModel mActModel;
	private UserBankModel mUserBank;

	@Override
	protected void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
		setmTitleType(TitleType.TITLE);
		setContentView(R.layout.act_withdraw);
		init();
	}

	private void init()
	{
		initTitle();
		registerClick();
	}

	private void registerClick()
	{
		mTv_bank.setOnClickListener(new OnClickListener()
		{
			@Override
			public void onClick(View v)
			{
				// TODO 弹出窗口选择银行卡
				if (mActModel != null)
				{
					List<UserBankModel> listModel = mActModel.getBank_list();
					SDDialogMenu dialog = new SDDialogMenu();
					final SDSimpleTextAdapter<UserBankModel> adapter = new SDSimpleTextAdapter<UserBankModel>(listModel, mActivity);
					dialog.setmListener(new SDDialogMenuListener()
					{

						@Override
						public void onItemClick(View v, int index, SDDialogMenu dialog)
						{
							UserBankModel model = adapter.getItem(index);
							bindSelectedBank(model);
						}

						@Override
						public void onDismiss(SDDialogMenu dialog)
						{

						}

						@Override
						public void onCancelClick(View v, SDDialogMenu dialog)
						{
						}
					});
					dialog.setAdapter(adapter);
					dialog.showBottom();
				} else
				{
					requestData();
				}
			}
		});
		mTv_next.setOnClickListener(new OnClickListener()
		{
			@Override
			public void onClick(View v)
			{
				if (mActModel != null)
				{
					String mobile = mActModel.getMobile();
					if (isEmpty(mobile))
					{
						Intent intent = new Intent(getApplicationContext(), BindMobileActivity.class);
						startActivity(intent);
						return;
					}

					if (mUserBank != null)
					{
						if (mUserBank.getId() > 0)
						{
							doNextHasBank();
						} else
						{
							doNextWithoutBank();
						}
					}
				} else
				{
					requestData();
				}
			}
		});
	}

	/**
	 * 新银行卡提现
	 */
	protected void doNextWithoutBank()
	{
		if (!validateParams())
		{
			return;
		}

		final InputPasswordValidateDialog passwordDialog = new InputPasswordValidateDialog();
		passwordDialog.setmListenerValidatePassword(new InputPasswordValidateDialogListener()
		{

			@Override
			public void onSuccess(BaseActModel actModel, String password)
			{
				if (actModel.getStatus() > 0)
				{
					passwordDialog.dismiss();
					Intent intent = new Intent(getApplicationContext(), BindBankCardActivity.class);
					intent.putExtra(BindBankCardActivity.EXTRA_WITHDRAW_MONEY, mStrMoney);
					intent.putExtra(BindBankCardActivity.EXTRA_BANK_MOBILE, mActModel.getMobile());
					intent.putExtra(BindBankCardActivity.EXTRA_REAL_NAME, mActModel.getReal_name());
					startActivity(intent);
					finish();
				}
			}
		});
		passwordDialog.show();
	}

	/**
	 * 已有银行卡提现
	 */
	protected void doNextHasBank()
	{
		if (!validateParams())
		{
			return;
		}

		final InputPasswordWithdrawDialog passwordDialog = new InputPasswordWithdrawDialog();
		passwordDialog.setMoney(mStrMoney);
		passwordDialog.setmListenerValidatePassword(new InputPasswordValidateDialogListener()
		{

			@Override
			public void onSuccess(BaseActModel actModel, String password)
			{
				passwordDialog.dismiss();
				// TODO 请求提现接口
				requestWithdraw(password);
			}
		});
		passwordDialog.show();
	}

	@Override
	protected void onResume()
	{
		requestData();
		super.onResume();
	}

	private void requestWithdraw(String password)
	{
		RequestModel model = new RequestModel();
		model.putCtl("uc_money");
		model.putAct("do_withdraw");
		model.put("money", mStrMoney);
		model.put("user_bank_id", mUserBank.getId());
		model.put("check_pwd", password);

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

	private boolean validateParams()
	{
		mStrMoney = mEt_money.getText().toString();
		if (isEmpty(mStrMoney))
		{
			SDToast.showToast("请输入提现金额");
			return false;
		}

		double inputMoney = SDTypeParseUtil.getDouble(mStrMoney);
		if (inputMoney <= 0)
		{
			SDToast.showToast("请输入大于0的金额");
			return false;
		}

		if (inputMoney > mActModel.getMoney())
		{
			SDToast.showToast("提现超额");
			return false;
		}

		return true;
	}

	private void initTitle()
	{
		mTitle.setMiddleTextTop("提现");
	}

	/**
	 * 请求银行卡列表数据
	 */
	private void requestData()
	{
		RequestModel model = new RequestModel();
		model.putCtl("uc_money");
		model.putAct("withdraw_bank_list");
		InterfaceServer.getInstance().requestInterface(model, new SDRequestCallBack<Uc_money_withdraw_bank_listActModel>()
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
					mActModel = actModel;
					// TODO 绑定银行卡数据
					if (mUserBank == null)
					{
						bindSelectedBank(SDCollectionUtil.get(mActModel.getBank_list(), 0));
					}
					mEt_money.setHint("可提现金额" + SDFormatUtil.formatMoneyChina(actModel.getMoney()));
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

	private void bindSelectedBank(UserBankModel model)
	{
		this.mUserBank = model;
		if (model != null)
		{
			mTv_bank.setText(model.getBank_name());
		}
	}

}
