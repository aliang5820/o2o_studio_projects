package com.fanwe;

import com.fanwe.http.InterfaceServer;
import com.fanwe.http.SDRequestCallBack;
import com.fanwe.library.dialog.SDDialogManager;
import com.fanwe.library.utils.SDToast;
import com.fanwe.model.Cart_checkActModel;
import com.fanwe.model.Cart_count_buy_totalModel;
import com.fanwe.model.Cart_doneActModel;
import com.fanwe.model.RequestModel;
import com.lidroid.xutils.http.ResponseInfo;

public class OrderDetailActivity extends ConfirmOrderActivity
{
	/** 订单id(int) */
	public static final String EXTRA_ORDER_ID = "extra_order_id";

	private int mId;

	@Override
	protected void init()
	{
		mId = getIntent().getIntExtra(EXTRA_ORDER_ID, 0);
		if (mId <= 0)
		{
			SDToast.showToast("订单id为空");
			finish();
			return;
		}
		super.init();
	}

	@Override
	protected void requestData()
	{
		RequestModel model = new RequestModel();
		model.putCtl("cart");
		model.putAct("order");
		model.put("id", mId);
		model.putUser();

		SDRequestCallBack<Cart_checkActModel> handler = new SDRequestCallBack<Cart_checkActModel>()
		{

			@Override
			public void onStart()
			{
				SDDialogManager.showProgressDialog("正在加载");
			}

			@Override
			public void onSuccess(ResponseInfo<String> responseInfo)
			{
				dealRequestDataSuccess(actModel);
			}

			@Override
			public void onFinish()
			{
				SDDialogManager.dismissProgressDialog();
				mPtrsvAll.onRefreshComplete();
			}
		};
		InterfaceServer.getInstance().requestInterface(model, handler);
	}

	@Override
	protected void requestCalculate()
	{
		RequestModel model = new RequestModel();
		model.putCtl("cart");
		model.putAct("count_order_total");
		model.put("id", mId);
		model.putUser();
		fillCalculateParams(model);
		SDRequestCallBack<Cart_count_buy_totalModel> handler = new SDRequestCallBack<Cart_count_buy_totalModel>()
		{

			@Override
			public void onStart()
			{
				SDDialogManager.showProgressDialog("正在加载");
			}

			@Override
			public void onSuccess(ResponseInfo<String> responseInfo)
			{
				// TODO 绑定所需费用信息
				dealRequestCalculateSuccess(actModel);
			}

			@Override
			public void onFinish()
			{
				SDDialogManager.dismissProgressDialog();
			}
		};
		InterfaceServer.getInstance().requestInterface(model, handler);
	}

	@Override
	protected void requestDoneOrder()
	{
		RequestModel model = new RequestModel();
		model.putCtl("cart");
		model.putAct("order_done");
		model.put("order_id", mId);
		model.putUser();
		fillDoneOrderParams(model);

		SDRequestCallBack<Cart_doneActModel> handler = new SDRequestCallBack<Cart_doneActModel>()
		{

			@Override
			public void onStart()
			{
				SDDialogManager.showProgressDialog("正在加载");
			}

			@Override
			public void onSuccess(ResponseInfo<String> responseInfo)
			{
				dealRequestDoneOrderSuccess(actModel);
			}

			@Override
			public void onFinish()
			{
				SDDialogManager.dismissProgressDialog();
			}
		};
		InterfaceServer.getInstance().requestInterface(model, handler);
	}
}
