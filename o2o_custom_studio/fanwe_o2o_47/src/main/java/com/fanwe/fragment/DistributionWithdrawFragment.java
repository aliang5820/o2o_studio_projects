package com.fanwe.fragment;

import java.util.Arrays;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.fanwe.BindMobileActivity;
import com.fanwe.DistributionWithdrawLogActivity;
import com.fanwe.common.CommonInterface;
import com.fanwe.constant.Constant.TitleType;
import com.fanwe.customview.SDStickyScrollView;
import com.fanwe.dao.LocalUserModelDao;
import com.fanwe.event.EnumEventTag;
import com.fanwe.http.InterfaceServer;
import com.fanwe.http.SDRequestCallBack;
import com.fanwe.library.adapter.SDSimpleTextAdapter;
import com.fanwe.library.common.SDActivityManager;
import com.fanwe.library.customview.SDSendValidateButton;
import com.fanwe.library.customview.SDSendValidateButton.SDSendValidateButtonListener;
import com.fanwe.library.customview.StickyScrollView;
import com.fanwe.library.dialog.SDDialogManager;
import com.fanwe.library.dialog.SDDialogMenu;
import com.fanwe.library.dialog.SDDialogMenu.SDDialogMenuListener;
import com.fanwe.library.title.SDTitleItem;
import com.fanwe.library.utils.SDToast;
import com.fanwe.library.utils.SDViewBinder;
import com.fanwe.library.utils.SDViewUtil;
import com.fanwe.model.BaseActModel;
import com.fanwe.model.LocalUserModel;
import com.fanwe.model.RequestModel;
import com.fanwe.model.Sms_send_sms_codeActModel;
import com.fanwe.model.Uc_fxwithdraw_indexActModel;
import com.fanwe.o2o.newo2o.R;
import com.handmark.pulltorefresh.library.PullToRefreshBase;
import com.handmark.pulltorefresh.library.PullToRefreshBase.Mode;
import com.handmark.pulltorefresh.library.PullToRefreshBase.OnRefreshListener2;
import com.lidroid.xutils.exception.HttpException;
import com.lidroid.xutils.http.ResponseInfo;
import com.lidroid.xutils.view.annotation.ViewInject;
import com.sunday.eventbus.SDBaseEvent;

/**
 * 分销提现
 * 
 * @author Administrator
 * 
 */
public class DistributionWithdrawFragment extends BaseFragment
{

	@ViewInject(R.id.ssv_scroll)
	private SDStickyScrollView mSsv_scroll;

	@ViewInject(R.id.tv_distribution_earn_money)
	private TextView mTv_distribution_earn_money;

	@ViewInject(R.id.ll_withdraw_type)
	private LinearLayout mLl_withdraw_type;

	@ViewInject(R.id.tv_withdraw_type)
	private TextView mTv_withdraw_type;

	@ViewInject(R.id.et_money)
	private EditText mEt_money;

	@ViewInject(R.id.ll_bank_info)
	private LinearLayout mLl_bank_info;

	@ViewInject(R.id.et_bank_name)
	private EditText mEt_bank_name;

	@ViewInject(R.id.et_bank_number)
	private EditText mEt_bank_number;

	@ViewInject(R.id.et_real_name)
	private EditText mEt_real_name;

	@ViewInject(R.id.et_code)
	private EditText mEt_code;

	@ViewInject(R.id.btn_send_code)
	private SDSendValidateButton mBtn_send_code;

	@ViewInject(R.id.btn_submit)
	private Button mBtn_submit;

	/** 0表示提现至余额 1表示提至银行卡 */
	private int mWithdrawType;

	private String mStrMoney;
	private String mStrBankName;
	private String mStrBankNumber;
	private String mStrRealName;
	private String mStrCode;
	private String mStrMobile;

	@Override
	protected View onCreateContentView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState)
	{
		setmTitleType(TitleType.TITLE);
		return setContentView(R.layout.frag_distribution_withdraw);
	}

	@Override
	protected void init()
	{
		initTitle();
		registerClick();
		initMobile();
		changeViewByWithdrawType();
		initSDSendValidateButton();
		initSDStickyScrollView();
	}

	private void initMobile()
	{
		LocalUserModel user = LocalUserModelDao.queryModel();
		if (user != null)
		{
			mStrMobile = user.getUser_mobile();
		}
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

	private void showBindMobileDialog()
	{
		Intent intent = new Intent(getActivity(), BindMobileActivity.class);
		startActivity(intent);
	}

	/**
	 * 请求验证码
	 */
	protected void requestSendCode()
	{
		CommonInterface.requestValidateCode(mStrMobile, 3, new SDRequestCallBack<Sms_send_sms_codeActModel>()
		{

			@Override
			public void onSuccess(ResponseInfo<String> responseInfo)
			{
				switch (actModel.getStatus())
				{
				case 0:
					showBindMobileDialog();
					break;
				case 1:
					mBtn_send_code.setmDisableTime(actModel.getLesstime());
					mBtn_send_code.startTickWork();
					break;

				default:
					break;
				}
			}

			@Override
			public void onStart()
			{
				SDDialogManager.showProgressDialog("请稍候");
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

	private void initSDStickyScrollView()
	{
		mSsv_scroll.setMode(Mode.PULL_FROM_START);
		mSsv_scroll.setOnRefreshListener(new OnRefreshListener2<StickyScrollView>()
		{

			@Override
			public void onPullDownToRefresh(PullToRefreshBase<StickyScrollView> refreshView)
			{
				requestData(false);
			}

			@Override
			public void onPullUpToRefresh(PullToRefreshBase<StickyScrollView> refreshView)
			{
			}
		});
		mSsv_scroll.setRefreshing();
	}

	private void registerClick()
	{
		mLl_withdraw_type.setOnClickListener(this);
		mBtn_submit.setOnClickListener(this);
	}

	private void initTitle()
	{
		mTitle.setMiddleTextTop("分销提现");

		mTitle.initRightItem(1);
		mTitle.getItemRight(0).setTextBot("提现日志");
	}

	@Override
	public void onCLickRight_SDTitleSimple(SDTitleItem v, int index)
	{
		clickWithdrawLog();
	}

	/**
	 * 提现日志
	 */
	private void clickWithdrawLog()
	{
		// TODO 跳到提现日志界面
		Intent intent = new Intent(getActivity(), DistributionWithdrawLogActivity.class);
		startActivity(intent);
	}

	private void requestData(final boolean isLoadMore)
	{
		RequestModel model = new RequestModel();
		model.putCtl("uc_fxwithdraw");
		model.putPage(1);

		InterfaceServer.getInstance().requestInterface(model, new SDRequestCallBack<Uc_fxwithdraw_indexActModel>()
		{

			@Override
			public void onStart()
			{

			}

			@Override
			public void onSuccess(ResponseInfo<String> responseInfo)
			{
				if (actModel.getStatus() == 1)
				{
					bindData(actModel);
				}
			}

			@Override
			public void onFinish()
			{
				mSsv_scroll.onRefreshComplete();
			}
		});
	}

	protected void bindData(Uc_fxwithdraw_indexActModel actModel)
	{
		if (actModel == null)
		{
			return;
		}

		String title = actModel.getPage_title();
		if (!isEmpty(title))
		{
			mTitle.setMiddleTextTop(title);
		}

		SDViewBinder.setTextView(mTv_distribution_earn_money, actModel.getFxmoney());
	}

	@Override
	public void onClick(View v)
	{
		if (v == mLl_withdraw_type)
		{
			clickWithDrawType();
		} else if (v == mBtn_submit)
		{
			clickSubmit();
		}
	}

	private void clickSubmit()
	{
		// TODO 请求提现接口
		if (!validateParams())
		{
			return;
		}

		RequestModel model = new RequestModel();
		model.putCtl("uc_fxwithdraw");
		model.putAct("save");
		model.put("money", mStrMoney);
		model.put("type", mWithdrawType);
		model.put("bank_name", mStrBankName);
		model.put("bank_account", mStrBankNumber);
		model.put("bank_user", mStrRealName);
		model.put("sms_verify", mStrCode);

		InterfaceServer.getInstance().requestInterface(model, new SDRequestCallBack<BaseActModel>()
		{
			@Override
			public void onStart()
			{
				SDDialogManager.showProgressDialog("");
			}

			@Override
			public void onSuccess(ResponseInfo<String> responseInfo)
			{
				if (actModel.getStatus() == 1)
				{
					// 提交成功
					mEt_code.setText("");
					Intent intent = new Intent(getActivity(), DistributionWithdrawLogActivity.class);
					startActivity(intent);
				}
			}

			@Override
			public void onFinish()
			{
				SDDialogManager.dismissProgressDialog();
			}
		});

	}

	private boolean validateParams()
	{

		mStrMoney = mEt_money.getText().toString().trim();
		if (isEmpty(mStrMoney))
		{
			SDToast.showToast("请输入提现金额");
			return false;
		}

		switch (mWithdrawType)
		{
		case 0:
			mStrBankName = null;
			mStrBankNumber = null;
			mStrRealName = null;
			break;
		case 1:
			mStrBankName = mEt_bank_name.getText().toString().trim();
			if (isEmpty(mStrBankName))
			{
				SDToast.showToast("请输入开户行名称");
				return false;
			}

			mStrBankNumber = mEt_bank_number.getText().toString().trim();
			if (isEmpty(mStrBankNumber))
			{
				SDToast.showToast("请输入银行卡号");
				return false;
			}

			mStrRealName = mEt_real_name.getText().toString().trim();
			if (isEmpty(mStrRealName))
			{
				SDToast.showToast("请输入姓名");
				return false;
			}
			break;

		default:
			break;
		}

		mStrCode = mEt_code.getText().toString().trim();
		if (isEmpty(mStrCode))
		{
			SDToast.showToast("请输入验证码");
			return false;
		}

		return true;
	}

	/**
	 * 点击提现类型
	 */
	private void clickWithDrawType()
	{
		// TODO 弹出dialog选择提现类型
		SDDialogMenu dialog = new SDDialogMenu(getActivity());
		String[] arrType = new String[] { "账户余额", "银行卡" };
		final SDSimpleTextAdapter<String> adapter = new SDSimpleTextAdapter<String>(Arrays.asList(arrType), getActivity());
		dialog.setmListener(new SDDialogMenuListener()
		{

			@Override
			public void onItemClick(View v, int index, SDDialogMenu dialog)
			{
				switch (index)
				{
				case 0:
					mWithdrawType = 0;
					break;
				case 1:
					mWithdrawType = 1;
					break;

				default:
					break;
				}
				changeViewByWithdrawType();
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
	}

	protected void changeViewByWithdrawType()
	{
		switch (mWithdrawType)
		{
		case 0:
			mTv_withdraw_type.setText("账户余额");
			SDViewUtil.hide(mLl_bank_info);
			break;
		case 1:
			mTv_withdraw_type.setText("银行卡");
			SDViewUtil.show(mLl_bank_info);
			break;

		default:
			break;
		}

	}

	@Override
	public void onDestroy()
	{
		mBtn_send_code.stopTickWork();
		super.onDestroy();
	}

	@Override
	public void onEventMainThread(SDBaseEvent event)
	{
		super.onEventMainThread(event);
		switch (EnumEventTag.valueOf(event.getTagInt()))
		{
		case BIND_MOBILE_SUCCESS:
			initMobile();
			break;
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
