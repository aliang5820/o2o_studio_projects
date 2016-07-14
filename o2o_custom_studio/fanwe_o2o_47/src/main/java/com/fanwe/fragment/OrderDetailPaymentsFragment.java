package com.fanwe.fragment;

import java.util.List;

import com.fanwe.adapter.OrderDetailPaymentListAdapter;
import com.fanwe.library.adapter.SDAdapter.ItemStateListener;
import com.fanwe.library.customview.SDGridLinearLayout;
import com.fanwe.model.Payment_listModel;
import com.fanwe.o2o.newo2o.R;
import com.lidroid.xutils.view.annotation.ViewInject;

/**
 * 订单详情页（支付方式fragment）
 * 
 * @author js02
 * 
 */
public class OrderDetailPaymentsFragment extends OrderDetailBaseFragment
{

	@ViewInject(R.id.ll_payment)
	private SDGridLinearLayout mLl_payment;

	private OrderDetailPaymentListAdapter mAdapter;

	private OrderDetailPaymentsFragmentListener mListener;
	/** 选中的支付方式id */
	private int mPaymentId;

	private boolean mIsAccountMoneyEnough = false;

	public void setAccountMoneyEnough(boolean isAccountMoneyEnough)
	{
		this.mIsAccountMoneyEnough = isAccountMoneyEnough;
	}

	public void setmListener(OrderDetailPaymentsFragmentListener listener)
	{
		this.mListener = listener;
	}

	public int getPaymentId()
	{
		return mPaymentId;
	}

	@Override
	protected int onCreateContentView()
	{
		return R.layout.frag_order_detail_payments;
	}

	@Override
	protected void init()
	{
		bindData();
	}

	private void resetParams()
	{
		this.mPaymentId = 0;
	}

	private void bindData()
	{
		if (!toggleFragmentView(mCheckActModel))
		{
			resetParams();
			return;
		}
		if (!toggleFragmentView(mCheckActModel.getShow_payment()))
		{
			resetParams();
			return;
		}
		final List<Payment_listModel> listPayment = mCheckActModel.getPayment_list();
		if (!toggleFragmentView(listPayment))
		{
			resetParams();
			return;
		}

		mAdapter = new OrderDetailPaymentListAdapter(listPayment, getActivity());
		mAdapter.setListenerItemState(new ItemStateListener<Payment_listModel>()
		{

			@Override
			public void onNormal(int position, Payment_listModel item)
			{
			}

			@Override
			public void onSelected(int position, Payment_listModel item)
			{
				if (mIsAccountMoneyEnough)
				{
					OrderDetailAccountPaymentFragment frag = getOrderDetailAccountPaymentFragment();
					if (frag != null)
					{
						frag.clearSelectedPayment(false);
					}
				}

				mPaymentId = item.getId();
				if (mListener != null)
				{
					mListener.onPaymentChange(item);
				}
			}
		});
		mLl_payment.setAdapter(mAdapter);

		// 还原选中
		Payment_listModel foundModel = null;
		for (Payment_listModel model : listPayment)
		{
			if (model.getId() == this.mPaymentId)
			{
				foundModel = model;
			}
		}
		if (foundModel != null)
		{
			mAdapter.getSelectManager().performClick(foundModel);
		}

	}

	@Override
	protected void onRefreshData()
	{
		bindData();
		super.onRefreshData();
	}

	public void clearSelectedPayment(boolean notify)
	{
		if (mAdapter != null)
		{
			mAdapter.getSelectManager().clearSelected();
		}
		resetParams();
		if (notify)
		{
			if (mListener != null)
			{
				mListener.onPaymentChange(null);
			}
		}
	}

	public interface OrderDetailPaymentsFragmentListener
	{
		public void onPaymentChange(Payment_listModel model);
	}

}