package com.fanwe.adapter;

import java.util.List;

import android.app.Activity;
import android.content.Intent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.fanwe.OrderDetailActivity;
import com.fanwe.common.CommonInterface;
import com.fanwe.http.SDRequestCallBack;
import com.fanwe.library.adapter.SDSimpleAdapter;
import com.fanwe.library.dialog.SDDialogConfirm;
import com.fanwe.library.dialog.SDDialogCustom;
import com.fanwe.library.dialog.SDDialogCustom.SDDialogCustomListener;
import com.fanwe.library.dialog.SDDialogManager;
import com.fanwe.library.utils.SDCollectionUtil;
import com.fanwe.library.utils.SDViewBinder;
import com.fanwe.library.utils.SDViewUtil;
import com.fanwe.library.utils.ViewHolder;
import com.fanwe.model.BaseActModel;
import com.fanwe.model.Uc_orderGoodsModel;
import com.fanwe.model.Uc_orderModel;
import com.fanwe.o2o.newo2o.R;
import com.lidroid.xutils.exception.HttpException;
import com.lidroid.xutils.http.ResponseInfo;

public class MyOrderListAdapter extends SDSimpleAdapter<Uc_orderModel>
{

	public MyOrderListAdapter(List<Uc_orderModel> listModel, Activity activity)
	{
		super(listModel, activity);
	}

	@Override
	public int getLayoutId(int position, View convertView, ViewGroup parent)
	{
		return R.layout.item_my_order_list;
	}

	@Override
	public void bindData(final int position, View convertView, ViewGroup parent, final Uc_orderModel model)
	{
		LinearLayout ll_goods = ViewHolder.get(R.id.ll_goods, convertView);
		TextView tv_order_sn = ViewHolder.get(R.id.tv_order_sn, convertView);
		TextView tv_total_price = ViewHolder.get(R.id.tv_total_price, convertView);
		TextView tv_pay_amount = ViewHolder.get(R.id.tv_pay_amount, convertView);
		TextView tv_number = ViewHolder.get(R.id.tv_number, convertView);
		TextView tv_status = ViewHolder.get(R.id.tv_status, convertView);
		TextView tv_create_time = ViewHolder.get(R.id.tv_create_time, convertView);
		TextView tv_pay = ViewHolder.get(R.id.tv_pay, convertView);
		TextView tv_cancel = ViewHolder.get(R.id.tv_cancel, convertView);

		List<Uc_orderGoodsModel> listGoods = model.getDeal_order_item();
		MyOrderListGoodsAdapter adapter = new MyOrderListGoodsAdapter(listGoods, true, mActivity);
		if (!SDCollectionUtil.isEmpty(listGoods))
		{
			ll_goods.removeAllViews();
			int size = listGoods.size();
			for (int i = 0; i < size; i++)
			{
				ll_goods.addView(adapter.getView(i, null, null));
			}
		}

		SDViewBinder.setTextView(tv_order_sn, model.getOrder_sn());
		SDViewBinder.setTextView(tv_total_price, model.getTotal_priceFormat());
		SDViewBinder.setTextView(tv_pay_amount, model.getPay_amountFormat());
		SDViewBinder.setTextView(tv_number, String.valueOf(model.getC()));
		SDViewBinder.setTextView(tv_status, model.getStatus());
		SDViewBinder.setTextView(tv_create_time, model.getCreate_time());

		if (model.getPay_status() == 0)
		{
			SDViewUtil.show(tv_pay);
			tv_pay.setOnClickListener(new OnClickListener()
			{

				@Override
				public void onClick(View v)
				{
					Intent intent = new Intent(mActivity, OrderDetailActivity.class);
					intent.putExtra(OrderDetailActivity.EXTRA_ORDER_ID, model.getId());
					mActivity.startActivity(intent);
				}
			});
		} else
		{
			SDViewUtil.hide(tv_pay);
		}

		if (SDViewBinder.setViewsVisibility(tv_cancel, model.hasCancelButton()))
		{
			SDViewBinder.setTextView(tv_cancel, model.getCancelButtonText());
			tv_cancel.setOnClickListener(new OnClickListener()
			{
				@Override
				public void onClick(View v)
				{
					deleteOrder(model, position);
				}
			});
		}
	}

	private void deleteOrder(final Uc_orderModel model, final int position)
	{
		if (model == null)
		{
			return;
		}

		SDDialogConfirm dialog = new SDDialogConfirm();
		dialog.setTextContent("确定删除订单？");
		dialog.setmListener(new SDDialogCustomListener()
		{

			@Override
			public void onDismiss(SDDialogCustom dialog)
			{
			}

			@Override
			public void onClickConfirm(View v, SDDialogCustom dialog)
			{
				requestDeleteOrder(model, position);
			}

			@Override
			public void onClickCancel(View v, SDDialogCustom dialog)
			{
			}
		});
		dialog.show();
	}

	private void requestDeleteOrder(final Uc_orderModel model, final int position)
	{
		CommonInterface.requestDeleteOrder(model.getId(), new SDRequestCallBack<BaseActModel>()
		{

			@Override
			public void onSuccess(ResponseInfo<String> responseInfo)
			{
				if (actModel.getStatus() == 1)
				{
					mListModel.remove(position);
					notifyDataSetChanged();
				}
			}

			@Override
			public void onStart()
			{
				SDDialogManager.showProgressDialog("正在删除");
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

}
