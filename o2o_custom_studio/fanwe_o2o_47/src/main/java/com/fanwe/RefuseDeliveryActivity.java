package com.fanwe;

import com.fanwe.event.EnumEventTag;
import com.fanwe.http.InterfaceServer;
import com.fanwe.http.SDRequestCallBack;
import com.fanwe.library.dialog.SDDialogManager;
import com.fanwe.model.BaseActModel;
import com.fanwe.model.RequestModel;
import com.fanwe.model.Uc_order_refundActModel;
import com.lidroid.xutils.http.ResponseInfo;
import com.sunday.eventbus.SDEventManager;

/**
 * 商品没收到货
 * 
 * @author Administrator
 * 
 */
public class RefuseDeliveryActivity extends RefundGoodsActivity
{

	@Override
	protected void init()
	{
		super.init();
		mEt_content.setHint("请输入详细原因");
	}

	@Override
	protected void requestData()
	{
		RequestModel model = new RequestModel();
		model.putUser();
		model.putCtl("uc_order");
		model.putAct("refuse_delivery");
		model.put("item_id", mId);

		InterfaceServer.getInstance().requestInterface(model, new SDRequestCallBack<Uc_order_refundActModel>()
		{
			@Override
			public void onStart()
			{
				SDDialogManager.showProgressDialog("");
			}

			@Override
			public void onFinish()
			{
				SDDialogManager.dismissProgressDialog();
				mSsv_all.onRefreshComplete();
			}

			@Override
			public void onSuccess(ResponseInfo<String> responseInfo)
			{
				if (actModel.getStatus() == 1)
				{
					mListModel = actModel.getListItem();
					mAdapter.setData(mListModel);
					bindData();
					updateTitle(actModel);
				}
			}
		});
	}

	@Override
	protected void requestSubmit()
	{
		RequestModel model = new RequestModel();
		model.putUser();
		model.putCtl("uc_order");
		model.putAct("do_refuse_delivery");
		model.put("content", mStrContent);
		model.put("item_id", mId);

		InterfaceServer.getInstance().requestInterface(model, new SDRequestCallBack<BaseActModel>()
		{
			@Override
			public void onStart()
			{
				SDDialogManager.showProgressDialog("");
			}

			@Override
			public void onFinish()
			{
				SDDialogManager.dismissProgressDialog();
			}

			@Override
			public void onSuccess(ResponseInfo<String> responseInfo)
			{
				if (actModel.getStatus() == 1)
				{
					SDEventManager.post(EnumEventTag.REFRESH_ORDER_LIST.ordinal());
					finish();
				}
			}
		});
	}

	@Override
	protected void initTitle()
	{
		mTitle.setMiddleTextTop("没收到货");
	}

}
