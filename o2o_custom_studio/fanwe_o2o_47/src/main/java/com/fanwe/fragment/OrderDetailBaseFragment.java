package com.fanwe.fragment;

import com.fanwe.ConfirmOrderActivity;
import com.fanwe.model.Cart_checkActModel;

public class OrderDetailBaseFragment extends BaseFragment
{
	protected Cart_checkActModel mCheckActModel;

	public Cart_checkActModel getmCheckActModel()
	{
		return mCheckActModel;
	}

	public void setmCheckActModel(Cart_checkActModel mCheckActModel)
	{
		this.mCheckActModel = mCheckActModel;
		refreshData();
	}

	public ConfirmOrderActivity getConfirmOrderActivity()
	{
		ConfirmOrderActivity activity = null;
		if (getActivity() instanceof ConfirmOrderActivity)
		{
			activity = (ConfirmOrderActivity) getActivity();
		}
		return activity;
	}

	public OrderDetailAccountPaymentFragment getOrderDetailAccountPaymentFragment()
	{
		OrderDetailAccountPaymentFragment fragment = null;
		ConfirmOrderActivity activity = getConfirmOrderActivity();
		if (activity != null)
		{
			fragment = activity.getOrderDetailAccountPaymentFragment();
		}
		return fragment;
	}

	public OrderDetailPaymentsFragment getOrderDetailPaymentsFragment()
	{
		OrderDetailPaymentsFragment fragment = null;
		ConfirmOrderActivity activity = getConfirmOrderActivity();
		if (activity != null)
		{
			fragment = activity.getOrderDetailPaymentsFragment();
		}
		return fragment;
	}

}