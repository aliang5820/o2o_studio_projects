package com.fanwe.fragment;

import android.view.View;
import android.view.View.OnClickListener;

import com.fanwe.customview.app.AccountPaymentView;
import com.fanwe.library.utils.SDTypeParseUtil;
import com.fanwe.library.view.select.SDSelectView.SDSelectViewStateListener;
import com.fanwe.o2o.newo2o.R;
import com.lidroid.xutils.view.annotation.ViewInject;

/**
 * 订单详情页（余额支付fragment）
 * 
 * @author js02
 * 
 */
public class OrderDetailAccountPaymentFragment extends OrderDetailBaseFragment
{

	@ViewInject(R.id.pv_payment)
	private AccountPaymentView mPv_payment;

	private OrderDetailAccountPaymentFragmentListener mListener;

	public void setmListener(OrderDetailAccountPaymentFragmentListener listener)
	{
		this.mListener = listener;
	}

	public int getUseAccountMoney()
	{
		int useAccountMoney = 0;
		if (mPv_payment.isSelected())
		{
			useAccountMoney = 1;
		} else
		{
			useAccountMoney = 0;
		}
		return useAccountMoney;
	}

	@Override
	protected int onCreateContentView()
	{
		return R.layout.frag_order_detail_account_payment;
	}

	@Override
	protected void init()
	{
		bindData();
		afterBindData();
	}

	private void bindData()
	{
		if (!toggleFragmentView(mCheckActModel))
		{
			return;
		}

		int hasAccount = mCheckActModel.getHas_account();
		if (hasAccount == 0)
		{
			mPv_payment.setStateNormal();
			hideFragmentView();
			return;
		}

		double accountMoney = SDTypeParseUtil.getDouble(mCheckActModel.getAccount_money());
		if (accountMoney <= 0)
		{
			mPv_payment.setStateNormal();
			hideFragmentView();
			return;
		} else
		{
			showFragmentView();
		}

		mPv_payment.mTv_name.setText(mCheckActModel.getAccount_moneyFormat());

		mPv_payment.setOnClickListener(new OnClickListener()
		{
			@Override
			public void onClick(View v)
			{
				mPv_payment.toggleSelected();
			}
		});

		mPv_payment.setListenerState(new SDSelectViewStateListener()
		{

			@Override
			public void onSelected(View v)
			{
				if (mListener != null)
				{
					mListener.onPaymentChange(mPv_payment.isSelected());
				}
			}

			@Override
			public void onNormal(View v)
			{
				if (mListener != null)
				{
					mListener.onPaymentChange(mPv_payment.isSelected());
				}
			}
		});

	}

	private void afterBindData()
	{
		mPv_payment.setStateSelected();
	}

	@Override
	protected void onRefreshData()
	{
		bindData();
		super.onRefreshData();
	}

	public void clearSelectedPayment(boolean notify)
	{
		mPv_payment.setStateNormal(false);
		if (notify)
		{
			if (mListener != null)
			{
				mListener.onPaymentChange(mPv_payment.isSelected());
			}
		}
	}

	public interface OrderDetailAccountPaymentFragmentListener
	{
		public void onPaymentChange(boolean isSelected);
	}

}