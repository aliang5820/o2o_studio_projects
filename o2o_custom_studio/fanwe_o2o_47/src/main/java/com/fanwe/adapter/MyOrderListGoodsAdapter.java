package com.fanwe.adapter;

import java.util.List;

import android.app.Activity;
import android.content.Intent;
import android.text.TextUtils;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.fanwe.AddCommentActivity;
import com.fanwe.AppWebViewActivity;
import com.fanwe.RefundGoodsActivity;
import com.fanwe.RefundTuanActivity;
import com.fanwe.RefuseDeliveryActivity;
import com.fanwe.TuanDetailActivity;
import com.fanwe.constant.Constant.CommentType;
import com.fanwe.event.EnumEventTag;
import com.fanwe.http.InterfaceServer;
import com.fanwe.http.SDRequestCallBack;
import com.fanwe.library.adapter.SDSimpleAdapter;
import com.fanwe.library.dialog.SDDialogConfirm;
import com.fanwe.library.dialog.SDDialogCustom;
import com.fanwe.library.dialog.SDDialogCustom.SDDialogCustomListener;
import com.fanwe.library.dialog.SDDialogManager;
import com.fanwe.library.utils.SDResourcesUtil;
import com.fanwe.library.utils.SDToast;
import com.fanwe.library.utils.SDViewBinder;
import com.fanwe.library.utils.SDViewUtil;
import com.fanwe.library.utils.ViewHolder;
import com.fanwe.model.BaseActModel;
import com.fanwe.model.RequestModel;
import com.fanwe.model.Uc_orderGoodsModel;
import com.fanwe.model.Uc_order_check_deliveryActModel;
import com.fanwe.o2o.newo2o.R;
import com.lidroid.xutils.http.ResponseInfo;
import com.sunday.eventbus.SDEventManager;

public class MyOrderListGoodsAdapter extends SDSimpleAdapter<Uc_orderGoodsModel>
{

	private static final int COLOR_ENABLE = R.color.main_color;
	private static final int COLOR_DISABLE = R.color.gray;

	private boolean mShowActions = true;

	public MyOrderListGoodsAdapter(List<Uc_orderGoodsModel> listModel, boolean showActions, Activity activity)
	{
		super(listModel, activity);
		this.mShowActions = showActions;
	}

	@Override
	public int getLayoutId(int position, View convertView, ViewGroup parent)
	{
		return R.layout.item_order_detail_goods;
	}

	@Override
	public void bindData(int position, View convertView, ViewGroup parent, final Uc_orderGoodsModel model)
	{
		ImageView iv_image = ViewHolder.get(R.id.iv_image, convertView);
		TextView tv_name = ViewHolder.get(R.id.tv_name, convertView);
		TextView tv_number = ViewHolder.get(R.id.tv_number, convertView);
		TextView tv_single_price = ViewHolder.get(R.id.tv_single_price, convertView);
		TextView tv_total_price = ViewHolder.get(R.id.tv_total_price, convertView);
		TextView tv_comment = ViewHolder.get(R.id.tv_comment, convertView);
		TextView tv_refund = ViewHolder.get(R.id.tv_refund, convertView);
		TextView tv_delivery = ViewHolder.get(R.id.tv_delivery, convertView);
		LinearLayout ll_tv_delivery_receive = ViewHolder.get(R.id.ll_tv_delivery_receive, convertView);
		TextView tv_query_logistics = ViewHolder.get(R.id.tv_query_logistics, convertView);
		TextView tv_confirmation_receipt = ViewHolder.get(R.id.tv_confirmation_receipt, convertView);
		TextView tv_did_not_receive = ViewHolder.get(R.id.tv_did_not_receive, convertView);

		SDViewUtil.hide(tv_comment);
		SDViewUtil.hide(tv_refund);
		SDViewUtil.hide(tv_delivery);
		SDViewUtil.hide(ll_tv_delivery_receive);
		tv_comment.setOnClickListener(null);
		tv_refund.setOnClickListener(null);

		tv_refund.setTextColor(SDResourcesUtil.getColor(COLOR_DISABLE));
		tv_comment.setTextColor(SDResourcesUtil.getColor(COLOR_DISABLE));

		SDViewBinder.setImageView(model.getDeal_icon(), iv_image);
		SDViewBinder.setTextView(tv_name, model.getSub_name());
		SDViewBinder.setTextView(tv_number, String.valueOf(model.getNumber()));
		SDViewBinder.setTextView(tv_single_price, model.getUnit_priceFormat());
		SDViewBinder.setTextView(tv_total_price, model.getTotal_priceFormat());

		if (mShowActions)
		{
			// 点评状态
			switch (model.getCommentState())
			{
			case 1:
				tv_comment.setText("立即点评");
				tv_comment.setTextColor(SDResourcesUtil.getColor(COLOR_ENABLE));
				SDViewUtil.show(tv_comment);
				tv_comment.setOnClickListener(new OnClickListener()
				{
					@Override
					public void onClick(View v)
					{
						Intent intent = new Intent(mActivity, AddCommentActivity.class);
						intent.putExtra(AddCommentActivity.EXTRA_ID, model.getDeal_id());
						intent.putExtra(AddCommentActivity.EXTRA_NAME, model.getName());
						intent.putExtra(AddCommentActivity.EXTRA_TYPE, CommentType.DEAL);
						mActivity.startActivity(intent);
					}
				});
				break;
			case 2:
				SDViewUtil.show(tv_comment);
				tv_comment.setText("已点评");
				break;

			default:
				break;
			}

			// 退款状态
			switch (model.getRefundState())
			{
			case 0:
				SDViewUtil.show(tv_refund);
				tv_refund.setTextColor(SDResourcesUtil.getColor(COLOR_ENABLE));
				tv_refund.setText("我要退款");
				tv_refund.setOnClickListener(new OnClickListener()
				{
					@Override
					public void onClick(View v)
					{
						// TODO 跳到退款界面
						if (model.isShop())
						{
							Intent intent = new Intent(mActivity, RefundGoodsActivity.class);
							intent.putExtra(RefundGoodsActivity.EXTRA_ID, model.getId());
							mActivity.startActivity(intent);
						} else
						{
							Intent intent = new Intent(mActivity, RefundTuanActivity.class);
							intent.putExtra(RefundGoodsActivity.EXTRA_ID, model.getId());
							mActivity.startActivity(intent);
						}
					}
				});
				break;
			case 1:
				SDViewUtil.show(tv_refund);
				tv_refund.setText("退款审核中");
				break;
			case 2:
				SDViewUtil.show(tv_refund);
				tv_refund.setText("已退款");
				break;
			case 3:
				SDViewUtil.show(tv_refund);
				tv_refund.setText("退款被拒");
				break;

			default:
				break;
			}

			// 物流状态
			switch (model.getDeliveryState())
			{
			case 0:
				SDViewUtil.show(tv_delivery);
				tv_delivery.setText("未发货");
				break;
			case 1:
				SDViewUtil.show(ll_tv_delivery_receive);
				tv_query_logistics.setOnClickListener(new OnClickListener()
				{

					@Override
					public void onClick(View v)
					{
						requestQueryLogistics(model);
					}
				});
				tv_confirmation_receipt.setOnClickListener(new OnClickListener()
				{

					@Override
					public void onClick(View v)
					{
						clickConfirmationReceipt(model);
					}
				});
				tv_did_not_receive.setOnClickListener(new OnClickListener()
				{

					@Override
					public void onClick(View v)
					{
						// TODO 没收到货
						Intent intent = new Intent(mActivity, RefuseDeliveryActivity.class);
						intent.putExtra(RefundGoodsActivity.EXTRA_ID, model.getId());
						mActivity.startActivity(intent);
					}
				});
				break;
			case 2:
				SDViewUtil.show(tv_delivery);
				tv_delivery.setText("已收货");
				break;
			case 3:
				SDViewUtil.show(tv_delivery);
				tv_delivery.setText("维权中");
				break;

			default:
				break;
			}
		}

		convertView.setOnClickListener(new OnClickListener()
		{
			@Override
			public void onClick(View v)
			{
				if (model.getDeal_id() > 0)
				{
					Intent intent = new Intent(mActivity, TuanDetailActivity.class);
					intent.putExtra(TuanDetailActivity.EXTRA_GOODS_ID, model.getDeal_id());
					mActivity.startActivity(intent);
				} else
				{
					SDToast.showToast("未找到商品ID");
				}
			}
		});
	}

	/**
	 * 点击确认收货
	 * 
	 * @param model
	 */
	protected void clickConfirmationReceipt(final Uc_orderGoodsModel model)
	{
		if (model == null)
		{
			return;
		}

		SDDialogConfirm dialog = new SDDialogConfirm();
		dialog.setTextContent("确认收货?");
		dialog.setmListener(new SDDialogCustomListener()
		{

			@Override
			public void onDismiss(SDDialogCustom dialog)
			{

			}

			@Override
			public void onClickConfirm(View v, SDDialogCustom dialog)
			{
				requestConfirmationReceipt(model);
			}

			@Override
			public void onClickCancel(View v, SDDialogCustom dialog)
			{

			}
		});
		dialog.show();
	}

	/**
	 * 确认收货
	 * 
	 * @param model
	 */
	protected void requestConfirmationReceipt(final Uc_orderGoodsModel model)
	{
		if (model == null)
		{
			return;
		}

		RequestModel requestModel = new RequestModel();
		requestModel.putCtl("uc_order");
		requestModel.putAct("verify_delivery");
		requestModel.put("item_id", model.getId());
		InterfaceServer.getInstance().requestInterface(requestModel, new SDRequestCallBack<BaseActModel>()
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
					// TODO 刷新列表
					SDEventManager.post(EnumEventTag.REFRESH_ORDER_LIST.ordinal());
				}
			}
		});
	}

	/**
	 * 查看物流
	 * 
	 * @param model
	 */
	protected void requestQueryLogistics(Uc_orderGoodsModel model)
	{
		if (model == null)
		{
			return;
		}
		RequestModel requestModel = new RequestModel();
		requestModel.putCtl("uc_order");
		requestModel.putAct("check_delivery");
		requestModel.put("item_id", model.getId());
		InterfaceServer.getInstance().requestInterface(requestModel, new SDRequestCallBack<Uc_order_check_deliveryActModel>()
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
					String url = actModel.getUrl();
					if (!TextUtils.isEmpty(url))
					{
						Intent intent = new Intent(mActivity, AppWebViewActivity.class);
						intent.putExtra(AppWebViewActivity.EXTRA_URL, url);
						intent.putExtra(AppWebViewActivity.EXTRA_TITLE, "查看物流");
						mActivity.startActivity(intent);
					}
				}
			}
		});
	}

}
