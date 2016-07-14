package com.fanwe;

import java.util.List;

import android.view.View;

import com.fanwe.adapter.RefundCouponAdapter;
import com.fanwe.event.EnumEventTag;
import com.fanwe.http.InterfaceServer;
import com.fanwe.http.SDRequestCallBack;
import com.fanwe.library.dialog.SDDialogManager;
import com.fanwe.library.utils.SDCollectionUtil;
import com.fanwe.library.utils.SDResourcesUtil;
import com.fanwe.library.utils.SDToast;
import com.fanwe.library.utils.SDViewUtil;
import com.fanwe.model.BaseActModel;
import com.fanwe.model.OrderCoupon_listModel;
import com.fanwe.model.RequestModel;
import com.fanwe.model.Uc_order_refund_couponActModel;
import com.fanwe.o2o.newo2o.R;
import com.lidroid.xutils.http.ResponseInfo;
import com.sunday.eventbus.SDEventManager;

/**
 * 团购申请退款
 * 
 * @author Administrator
 * 
 */
public class RefundTuanActivity extends RefundGoodsActivity
{

	private RefundCouponAdapter mAdapterCoupon;

	@Override
	protected void init()
	{
		super.init();
	}

	@Override
	protected void requestData()
	{
		RequestModel model = new RequestModel();
		model.putUser();
		model.putCtl("uc_order");
		model.putAct("refund_coupon");
		model.put("item_id", mId);

		InterfaceServer.getInstance().requestInterface(model, new SDRequestCallBack<Uc_order_refund_couponActModel>()
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
					bindCoupons(actModel.getCoupon_list());
					updateTitle(actModel);
				}
			}
		});
	}

	protected void bindCoupons(List<OrderCoupon_listModel> listModel)
	{
		if (SDCollectionUtil.isEmpty(listModel))
		{
			SDViewUtil.hide(mLl_coupon);
			return;
		}

		SDViewUtil.show(mLl_coupon);
		mLl_coupon.removeAllViews();
		mAdapterCoupon = new RefundCouponAdapter(listModel, mActivity);
		for (int i = 0; i < mAdapterCoupon.getCount(); i++)
		{
			View view = mAdapterCoupon.getView(i, null, null);
			mLl_coupon.addView(view);
		}
	}

	@Override
	protected void requestSubmit()
	{
		if (mAdapter == null)
		{
			return;
		}

		List<String> listIds = mAdapterCoupon.getSelectedIds();
		if (isEmpty(listIds))
		{
			SDToast.showToast(SDResourcesUtil.getString(R.string.please_select_refund_tuan_gou_coupon));
			return;
		}

		RequestModel model = new RequestModel();
		model.putUser();
		model.putCtl("uc_order");
		model.putAct("do_refund_coupon");
		model.put("content", mStrContent);
		model.put("item_id", listIds);

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

}
