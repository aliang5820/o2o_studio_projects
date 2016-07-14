package com.fanwe;

import java.util.List;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.fanwe.adapter.PayorderCodesAdapter;
import com.fanwe.constant.Constant.TitleType;
import com.fanwe.event.EnumEventTag;
import com.fanwe.http.InterfaceServer;
import com.fanwe.http.SDRequestCallBack;
import com.fanwe.library.title.SDTitleItem;
import com.fanwe.library.utils.SDViewBinder;
import com.fanwe.model.Payment_codeModel;
import com.fanwe.model.Payment_doneActCouponlistModel;
import com.fanwe.model.Payment_doneActModel;
import com.fanwe.model.RequestModel;
import com.fanwe.o2o.newo2o.R;
import com.fanwe.work.AppRuntimeWorker;
import com.lidroid.xutils.http.ResponseInfo;
import com.lidroid.xutils.view.annotation.ViewInject;
import com.sunday.eventbus.SDEventManager;

public class PayDoneActivity extends AbstractPayDoneActivity
{

	@ViewInject(R.id.act_pay_tv_order_sn)
	private TextView mTvOrderSn;

	@ViewInject(R.id.act_pay_tv_pay_info)
	private TextView mTvPayInfo;

	@ViewInject(R.id.act_pay_btn_pay)
	private Button mBtnPay;

	@ViewInject(R.id.act_pay_ll_scan_code)
	private LinearLayout mLlScanCodes;

	private Payment_doneActModel mActModel;

	/** 0:未支付，1:已支付 */
	private int mHasPay = 0;

	@Override
	protected void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
		setmTitleType(TitleType.TITLE);
		setContentView(R.layout.act_pay_done);
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
		registeClick();
	}

	private void initTitle()
	{
		mTitle.setMiddleTextTop("订单支付");
		mTitle.initRightItem(1);
		mTitle.getItemRight(0).setTextBot("订单列表");
	}

	@Override
	public void onCLickRight_SDTitleSimple(SDTitleItem v, int index)
	{
		// TODO 跳到订单列表
		Intent intent = new Intent(getApplicationContext(), MyOrderListActivity.class);
		intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
		intent.addFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP);
		intent.putExtra(MyOrderListActivity.EXTRA_PAY_STATUS, mHasPay);
		startActivity(intent);
		finish();
	}

	private void registeClick()
	{
		mBtnPay.setOnClickListener(new OnClickListener()
		{

			@Override
			public void onClick(View v)
			{
				clickPay();
			}
		});
	}

	private void clickPay()
	{
		if (mActModel == null)
		{
			requestData();
		} else
		{
			startPay(mActModel.getPayment_code());
		}

	}

	@Override
	protected void requestData()
	{
		if (!AppRuntimeWorker.isLogin(mActivity))
		{
			return;
		}
		RequestModel model = new RequestModel();
		model.putCtl("payment");
		model.putAct("done");
		model.putUser();
		model.put("id", mOrderId);
		SDRequestCallBack<Payment_doneActModel> handler = new SDRequestCallBack<Payment_doneActModel>()
		{

			@Override
			public void onSuccess(ResponseInfo<String> responseInfo)
			{
				if (actModel.getStatus() == 1)
				{
					mActModel = actModel;
					bindData();
				}
			}

			@Override
			public void onFinish()
			{
				onRefreshComplete();
			}
		};
		InterfaceServer.getInstance().requestInterface(model, handler);
	}

	protected void bindData()
	{
		if (mActModel == null)
		{
			return;
		}

		SDViewBinder.setTextView(mTvOrderSn, mActModel.getOrder_sn());
		int payStatus = mActModel.getPay_status();
		if (payStatus == 1)// 订单已经付款
		{
			SDEventManager.post(EnumEventTag.PAY_ORDER_SUCCESS.ordinal());
			mHasPay = 1;
			mBtnPay.setVisibility(View.GONE);
			SDViewBinder.setTextView(mTvPayInfo, mActModel.getPay_info());
			bindCouponlistData(mActModel.getCouponlist());
		} else
		{
			mHasPay = 0;
			mBtnPay.setVisibility(View.VISIBLE);
			Payment_codeModel paymentCodeModel = mActModel.getPayment_code();
			if (paymentCodeModel != null)
			{
				SDViewBinder.setTextView(mTvPayInfo, paymentCodeModel.getPay_info());
				mBtnPay.setText(paymentCodeModel.getPay_moneyFormat());
			}
		}
	}

	/**
	 * 绑定二维码
	 * 
	 * @param couponlist
	 */
	protected void bindCouponlistData(List<Payment_doneActCouponlistModel> couponlist)
	{
		if (couponlist != null && couponlist.size() > 0)
		{
			mLlScanCodes.setVisibility(View.VISIBLE);
			mLlScanCodes.removeAllViews();
			PayorderCodesAdapter adapter = new PayorderCodesAdapter(couponlist, PayDoneActivity.this);
			for (int i = 0; i < adapter.getCount(); i++)
			{
				View view = adapter.getView(i, null, null);
				mLlScanCodes.addView(view);
			}
		} else
		{
			mLlScanCodes.setVisibility(View.GONE);
		}
	}
}