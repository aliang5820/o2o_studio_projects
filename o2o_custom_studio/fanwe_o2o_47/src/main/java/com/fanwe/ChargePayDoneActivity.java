package com.fanwe;

import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.TextView;

import com.fanwe.constant.Constant.TitleType;
import com.fanwe.http.InterfaceServer;
import com.fanwe.http.SDRequestCallBack;
import com.fanwe.library.dialog.SDDialogManager;
import com.fanwe.library.utils.SDViewUtil;
import com.fanwe.model.Payment_codeModel;
import com.fanwe.model.Payment_doneActModel;
import com.fanwe.model.RequestModel;
import com.fanwe.o2o.newo2o.R;
import com.lidroid.xutils.http.ResponseInfo;
import com.lidroid.xutils.view.annotation.ViewInject;

public class ChargePayDoneActivity extends AbstractPayDoneActivity
{
	@ViewInject(R.id.tv_pay_info)
	private TextView mTv_pay_info;

	@ViewInject(R.id.tv_pay)
	private TextView mTv_pay;

	private Payment_doneActModel mActModel;

	@Override
	protected void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
		setmTitleType(TitleType.TITLE);
		setContentView(R.layout.act_charge_pay_done);
		init();
	}

	private void init()
	{
		if (mOrderId <= 0)
		{
			finish();
			return;
		}
		initTitle();
		register();
	}

	private void register()
	{
		mTv_pay.setOnClickListener(new OnClickListener()
		{
			@Override
			public void onClick(View arg0)
			{
				clickPay();
			}
		});
	}

	protected void clickPay()
	{
		if (mActModel == null)
		{
			requestData();
		} else
		{
			startPay(mActModel.getPayment_code());
		}
	}

	private void initTitle()
	{
		mTitle.setMiddleTextTop("订单付款");
	}

	@Override
	protected void requestData()
	{
		RequestModel model = new RequestModel();
		model.putCtl("payment");
		model.putAct("done");
		model.put("id", mOrderId);

		InterfaceServer.getInstance().requestInterface(model, new SDRequestCallBack<Payment_doneActModel>()
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
				if (actModel.getStatus() == 1)
				{
					mActModel = actModel;
					if (actModel.getPay_status() == 1)
					{
						SDViewUtil.show(mTv_pay_info);
						SDViewUtil.hide(mTv_pay);
						mTv_pay_info.setText(actModel.getPay_info());
					} else
					{
						SDViewUtil.show(mTv_pay);
						SDViewUtil.hide(mTv_pay_info);
						bindData();
					}
				}
				super.onSuccess(responseInfo);
			}

			@Override
			public void onFinish()
			{
				SDDialogManager.dismissProgressDialog();
				onRefreshComplete();
				super.onFinish();
			}
		});
	}

	protected void bindData()
	{
		if (mActModel == null)
		{
			return;
		}
		Payment_codeModel payCodeModel = mActModel.getPayment_code();

		if (payCodeModel != null)
		{
			mTv_pay.setText(payCodeModel.getPay_moneyFormat());
		}
	}
}
