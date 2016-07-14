package com.fanwe.fragment;

import java.util.List;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.fanwe.DeliveryAddressSelectActivty;
import com.fanwe.app.App;
import com.fanwe.library.adapter.SDSimpleTextAdapter;
import com.fanwe.library.dialog.SDDialogCustom;
import com.fanwe.library.dialog.SDDialogCustom.SDDialogCustomListener;
import com.fanwe.library.dialog.SDDialogMenu;
import com.fanwe.library.dialog.SDDialogMenu.SDDialogMenuListener;
import com.fanwe.library.utils.SDCollectionUtil;
import com.fanwe.library.utils.SDViewBinder;
import com.fanwe.library.utils.SDViewUtil;
import com.fanwe.model.Cart_count_buy_totalModel;
import com.fanwe.model.Consignee_infoModel;
import com.fanwe.model.Delivery_listModel;
import com.fanwe.model.VoucherModel;
import com.fanwe.o2o.newo2o.R;
import com.lidroid.xutils.view.annotation.ViewInject;

/**
 * 订单详情页（参数设置fragment，如留言，手机号，等。。。）
 * 
 * @author js02
 * 
 */
public class OrderDetailParamsFragment extends OrderDetailBaseFragment
{

	@ViewInject(R.id.frag_order_detail_params_ll_all)
	private LinearLayout mLlAll;

	@ViewInject(R.id.frag_order_detail_params_ll_has_delivery_address)
	private LinearLayout mLlHasDeliveryAddress;

	@ViewInject(R.id.frag_order_detail_params_tv_delivery_address)
	private TextView mTvDeliveryAddress;

	@ViewInject(R.id.frag_order_detail_params_ll_has_delivery_mode)
	private LinearLayout mLlHasDeliveryMode;

	@ViewInject(R.id.frag_order_detail_params_tv_delivery_mode)
	private TextView mTvDeliveryMode;

	@ViewInject(R.id.frag_order_detail_params_ll_has_daijin)
	private LinearLayout mLlHasDaijin;

	@ViewInject(R.id.frag_order_detail_params_tv_daijin)
	private TextView mTvDaijin;

	@ViewInject(R.id.frag_order_detail_params_ll_has_leave_message)
	private LinearLayout mLlHasLeaveMessage;

	@ViewInject(R.id.frag_order_detail_params_tv_leave_message)
	private TextView mTvLeaveMessage;

	private OrderDetailParamsFragmentListener mListener;

	public void setmListener(OrderDetailParamsFragmentListener listener)
	{
		this.mListener = listener;
	}

	private boolean mIsFirstBindData = true;

	// ----------提交参数
	/** 红包 */
	private VoucherModel mVoucherModel;
	/** 配送方式 */
	private Delivery_listModel mDelivery_listModel;
	/** 留言信息 */
	private String content;

	/**
	 * 获取配送方式id
	 * 
	 * @return
	 */
	public int getDelivery_id()
	{
		if (mDelivery_listModel != null)
		{
			return mDelivery_listModel.getId();
		} else
		{
			return 0;
		}
	}

	/**
	 * 获取代金券序列号
	 * 
	 * @return
	 */
	public String getEcv_sn()
	{
		if (mVoucherModel != null)
		{
			return mVoucherModel.getSn();
		} else
		{
			return null;
		}
	}

	/**
	 * 获取留言信息
	 * 
	 * @return
	 */
	public String getContent()
	{
		return content;
	}

	@Override
	protected View onCreateContentView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState)
	{
		return setContentView(R.layout.frag_order_detail_params);
	}

	@Override
	protected void init()
	{
		registeClick();
		bindData();
	}

	protected void bindData()
	{
		if (!toggleFragmentView(mCheckActModel))
		{
			resetParams();
			return;
		}

		if (mIsFirstBindData)
		{
			content = mCheckActModel.getOrder_memo();
			mIsFirstBindData = false;
		}

		setViewsVisibility();
		bindDeliveryAddress(mCheckActModel.getConsignee_info());
		bindDeliveryMode(mDelivery_listModel);
		bindDaijin(mVoucherModel);
		bindLeaveMessage(content);
	}

	private void resetParams()
	{
		this.mDelivery_listModel = null;
		this.content = null;
		this.mVoucherModel = null;
	}

	@Override
	protected void onRefreshData()
	{
		bindData();
		super.onRefreshData();
	}

	/**
	 * 绑定留言信息
	 * 
	 * @param content
	 */
	protected void bindLeaveMessage(String content)
	{
		SDViewBinder.setTextView(mTvLeaveMessage, content, "");
		this.content = content;
	}

	/**
	 * 绑定配送方式
	 * 
	 * @param delivery_listModel
	 */
	private void bindDeliveryMode(Delivery_listModel delivery_listModel)
	{
		if (delivery_listModel != null)
		{
			SDViewBinder.setTextView(mTvDeliveryMode, delivery_listModel.getName());
		} else
		{
			SDViewBinder.setTextView(mTvDeliveryMode, null);
		}
	}

	/**
	 * 绑定代金券
	 * 
	 * @param evcSn
	 */
	protected void bindDaijin(VoucherModel voucherModel)
	{
		if (voucherModel != null)
		{
			SDViewBinder.setTextView(mTvDaijin, voucherModel.getName());
		} else
		{
			SDViewBinder.setTextView(mTvDaijin, null);
		}
	}

	/**
	 * 绑定配送地址
	 * 
	 * @param deliveryAddr
	 */
	protected void bindDeliveryAddress(Consignee_infoModel model)
	{
		if (model != null)
		{
			StringBuilder sb = new StringBuilder();
			if (model.getConsignee() != null) // 收件人
			{
				sb.append(model.getConsignee());
				sb.append("\n");
			}
			if (model.getMobile() != null) // 手机号码
			{
				sb.append(model.getMobile());
				sb.append("\n");
			}

			if (model.getAddressRegion() != null)
			{
				sb.append(model.getAddressRegion());
				sb.append("\n");
			}

			if (model.getAddress() != null) // 地址
			{
				sb.append(model.getAddress());
			}
			mTvDeliveryAddress.setText(sb.toString());
		} else
		{
			mTvDeliveryAddress.setText("");
		}
	}

	protected void setViewsVisibility()
	{
		// 设置配送地址是否可见
		SDViewBinder.setViewsVisibility(mLlHasDeliveryAddress, mCheckActModel.getIs_delivery());
		Cart_count_buy_totalModel calculateModel = mCheckActModel.getCalculateModel();
		if (calculateModel != null)
		{
			if (calculateModel.getIs_pick() == 1)
			{
				SDViewUtil.hide(mLlHasDeliveryAddress);
			}
		}

		// 设置配送方式是否可见
		SDViewBinder.setViewsVisibility(mLlHasDeliveryMode, mCheckActModel.getIs_delivery());

		// 设置代金券是否可见
		if (SDViewBinder.setViewsVisibility(mLlHasDaijin, mCheckActModel.getHas_ecv()))
		{
			List<VoucherModel> listVoucher = mCheckActModel.getVoucher_list();
			if (SDCollectionUtil.isEmpty(listVoucher))
			{
				SDViewUtil.hide(mLlHasDaijin);
			} else
			{
				SDViewUtil.show(mLlHasDaijin);
			}
		}

		// 设置留言框是否可见
		SDViewBinder.setViewsVisibility(mLlHasLeaveMessage, 1);
	}

	private void registeClick()
	{
		mLlHasDeliveryAddress.setOnClickListener(this);
		mLlHasDeliveryMode.setOnClickListener(this);
		mLlHasDaijin.setOnClickListener(this);
		mLlHasLeaveMessage.setOnClickListener(this);
	}

	@Override
	public void onClick(View v)
	{
		switch (v.getId())
		{
		case R.id.frag_order_detail_params_ll_has_delivery_address:
			clickDeliveryAddress();
			break;
		case R.id.frag_order_detail_params_ll_has_delivery_mode:
			clickDeliveryMode();
			break;
		case R.id.frag_order_detail_params_ll_has_daijin:
			clickCoupons();
			break;
		case R.id.frag_order_detail_params_ll_has_leave_message:
			clickLeaveMessage();
			break;
		default:
			break;
		}
	}

	/**
	 * 点击收货地址
	 */
	private void clickDeliveryAddress()
	{
		Intent intent = new Intent(App.getApplication(), DeliveryAddressSelectActivty.class);
		startActivity(intent);
	}

	/**
	 * 点击配送模式
	 */
	private void clickDeliveryMode()
	{
		List<Delivery_listModel> listDelivery_listModels = mCheckActModel.getDelivery_list();
		if (listDelivery_listModels != null && listDelivery_listModels.size() > 0)
		{
			showDeliveryListDialog(listDelivery_listModels);
		}
	}

	/**
	 * 点击优惠券
	 */
	private void clickCoupons()
	{
		List<VoucherModel> listModel = mCheckActModel.getVoucher_list();
		if (isEmpty(listModel))
		{
			return;
		}

		SDDialogMenu dialog = new SDDialogMenu(getActivity());
		final SDSimpleTextAdapter<VoucherModel> adapter = new SDSimpleTextAdapter<VoucherModel>(listModel, getActivity());
		dialog.setAdapter(adapter);
		dialog.setmListener(new SDDialogMenuListener()
		{

			@Override
			public void onItemClick(View v, int index, SDDialogMenu dialog)
			{
				mVoucherModel = adapter.getItem(index);
				bindDaijin(mVoucherModel);
				if (mListener != null)
				{
					mListener.onCalculate();
				}
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
		dialog.showBottom();
	}

	/**
	 * 点击留言
	 */
	private void clickLeaveMessage()
	{
		View view = LayoutInflater.from(App.getApplication()).inflate(R.layout.dialog_input_invoice, null);
		final EditText etContent = (EditText) view.findViewById(R.id.dialog_input_invoice_et_content);
		etContent.setText(content);
		new SDDialogCustom().setTextTitle("请输入留言").setCustomView(view).setmListener(new SDDialogCustomListener()
		{

			@Override
			public void onDismiss(SDDialogCustom dialog)
			{
			}

			@Override
			public void onClickConfirm(View v, SDDialogCustom dialog)
			{
				content = etContent.getText().toString();
				mTvLeaveMessage.setText(content);
			}

			@Override
			public void onClickCancel(View v, SDDialogCustom dialog)
			{
			}
		}).show();
	}

	/**
	 * 显示选择配送方式窗口
	 * 
	 * @param listDelivery_listModels
	 */
	private void showDeliveryListDialog(List<Delivery_listModel> listDelivery_listModels)
	{
		final SDSimpleTextAdapter<Delivery_listModel> adapter = new SDSimpleTextAdapter<Delivery_listModel>(listDelivery_listModels, getActivity());
		new SDDialogMenu().setAdapter(adapter).setmListener(new SDDialogMenuListener()
		{

			@Override
			public void onItemClick(View v, int index, SDDialogMenu dialog)
			{
				mDelivery_listModel = adapter.getItem(index);
				bindDeliveryMode(mDelivery_listModel);
				if (mListener != null)
				{
					mListener.onCalculate();
				}
			}

			@Override
			public void onDismiss(SDDialogMenu dialog)
			{
			}

			@Override
			public void onCancelClick(View v, SDDialogMenu dialog)
			{
			}
		}).showBottom();
	}

	public interface OrderDetailParamsFragmentListener
	{
		public void onCalculate();
	}

}