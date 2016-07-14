package com.fanwe;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.TextView;

import com.fanwe.constant.Constant.TitleType;
import com.fanwe.http.InterfaceServer;
import com.fanwe.http.SDRequestCallBack;
import com.fanwe.library.title.SDTitleItem;
import com.fanwe.library.utils.SDViewBinder;
import com.fanwe.library.utils.SDViewUtil;
import com.fanwe.model.Payment_codeModel;
import com.fanwe.model.RequestModel;
import com.fanwe.model.Store_payment_doneActModel;
import com.fanwe.o2o.newo2o.R;
import com.lidroid.xutils.http.ResponseInfo;
import com.lidroid.xutils.view.annotation.ViewInject;

/**
 * 到店付订单支付完成页
 * 
 * @author Administrator
 * 
 */
public class StorePayDoneActivity extends AbstractPayDoneActivity
{

	@ViewInject(R.id.tv_order_sn)
	private TextView mTv_order_sn;

	@ViewInject(R.id.tv_pay_info)
	private TextView mTv_pay_info;

	@ViewInject(R.id.tv_pay)
	private TextView mTv_pay;

	private Store_payment_doneActModel mActModel;

	@Override
	protected void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
		setmTitleType(TitleType.TITLE);
		setContentView(R.layout.act_store_pay_done);
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

	private void initTitle()
	{
		mTitle.setMiddleTextTop("订单支付");
		mTitle.initRightItem(1);
		mTitle.getItemRight(0).setTextBot("订单列表");
	}

	@Override
	public void onCLickRight_SDTitleSimple(SDTitleItem v, int index)
	{
		Intent intent = new Intent(this, StoreOrderListActivity.class);
		startActivity(intent);
		super.onCLickRight_SDTitleSimple(v, index);
	}

	private void register()
	{
		mTv_pay.setOnClickListener(new OnClickListener()
		{

			@Override
			public void onClick(View v)
			{
				clickPay();
			}
		});
	}

	@Override
	protected void requestData()
	{
		RequestModel model = new RequestModel();
		model.putCtl("store_payment");
		model.putAct("done");
		model.put("order_id", mOrderId);

		InterfaceServer.getInstance().requestInterface(model, new SDRequestCallBack<Store_payment_doneActModel>()
		{
			@Override
			public void onSuccess(ResponseInfo<String> responseInfo)
			{
				if (actModel.getStatus() == 1)
				{
					mActModel = actModel;
					bindData();
				}
				super.onSuccess(responseInfo);
			}

			@Override
			public void onFinish()
			{
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

		SDViewBinder.setTextView(mTv_order_sn, mActModel.getOrder_sn());
		SDViewBinder.setTextView(mTv_pay_info, mActModel.getPay_info());

		if (mActModel.getPay_status() == 1)
		{
			SDViewUtil.hide(mTv_pay);
		} else
		{
			SDViewUtil.show(mTv_pay);
			Payment_codeModel model = mActModel.getPayment_code();
			if (model != null)
			{
				mTv_pay.setText(model.getPay_moneyFormat());
			}
		}
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

}
