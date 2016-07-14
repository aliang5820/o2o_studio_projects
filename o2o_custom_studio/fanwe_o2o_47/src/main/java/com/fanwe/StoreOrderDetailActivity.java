package com.fanwe;

import java.util.List;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ScrollView;
import android.widget.TextView;

import com.fanwe.adapter.OrderDetailFeeAdapter;
import com.fanwe.adapter.StoreOrderDetailPaymentListAdapter;
import com.fanwe.constant.Constant.TitleType;
import com.fanwe.customview.app.AccountPaymentView;
import com.fanwe.http.InterfaceServer;
import com.fanwe.http.SDRequestCallBack;
import com.fanwe.library.adapter.SDAdapter.ItemStateListener;
import com.fanwe.library.customview.SDGridLinearLayout;
import com.fanwe.library.dialog.SDDialogManager;
import com.fanwe.library.utils.SDToast;
import com.fanwe.library.utils.SDViewBinder;
import com.fanwe.library.utils.SDViewUtil;
import com.fanwe.library.view.select.SDSelectView.SDSelectViewStateListener;
import com.fanwe.model.BaseActModel;
import com.fanwe.model.FeeinfoModel;
import com.fanwe.model.Payment_listModel;
import com.fanwe.model.RequestModel;
import com.fanwe.model.Store_pay_checkActModel;
import com.fanwe.model.Store_pay_count_store_pay_totalActModel;
import com.fanwe.o2o.newo2o.R;
import com.fanwe.utils.SDFormatUtil;
import com.handmark.pulltorefresh.library.PullToRefreshBase;
import com.handmark.pulltorefresh.library.PullToRefreshBase.Mode;
import com.handmark.pulltorefresh.library.PullToRefreshBase.OnRefreshListener2;
import com.handmark.pulltorefresh.library.PullToRefreshScrollView;
import com.lidroid.xutils.http.ResponseInfo;
import com.lidroid.xutils.view.annotation.ViewInject;

/**
 * 到店付订单详情页
 * 
 * @author Administrator
 * 
 */
public class StoreOrderDetailActivity extends BaseActivity
{

	/** 门店自主下单订单id (int) */
	public static final String EXTRA_ORDER_ID = "extra_order_id";

	@ViewInject(R.id.ptrsv_all)
	private PullToRefreshScrollView mPtrsv_all;

	// 支付方式
	@ViewInject(R.id.ll_payment_layout)
	private View mLl_payment_layout;
	@ViewInject(R.id.ll_payment)
	private SDGridLinearLayout mLl_payment;

	// 余额支付
	@ViewInject(R.id.ll_account_payment_layout)
	private View mLl_account_payment_layout;
	@ViewInject(R.id.pv_payment)
	private AccountPaymentView mPv_payment;

	// 费用信息
	@ViewInject(R.id.ll_fee_layout)
	private View mLl_fee_layout;
	@ViewInject(R.id.ll_fee)
	private SDGridLinearLayout mLl_fee;

	@ViewInject(R.id.tv_submit)
	private TextView mTv_submit;

	/** 支付方式id */
	private int mPaymentId;

	/** 是否使用余额支付1:是，0:否 */
	private int mUseAccountPayment;

	/** 订单id */
	private int mId;

	private Store_pay_checkActModel mCheckActModel;
	private StoreOrderDetailPaymentListAdapter mAdapterPaymentList;

	@Override
	protected void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
		setmTitleType(TitleType.TITLE);
		setContentView(R.layout.act_store_order_detail);
		init();
	}

	private void init()
	{
		getIntentData();

		if (mId <= 0)
		{
			SDToast.showToast("订单id为空");
			finish();
			return;
		}
		initTitle();
		initPullToRefreshScrollView();
		register();
	}

	private void register()
	{
		mTv_submit.setOnClickListener(new OnClickListener()
		{

			@Override
			public void onClick(View v)
			{
				requestSubmit();
			}
		});
	}

	private void getIntentData()
	{
		mId = getIntent().getIntExtra(EXTRA_ORDER_ID, 0);
	}

	private void initTitle()
	{
		mTitle.setMiddleTextTop("提交订单");
	}

	private void initPullToRefreshScrollView()
	{
		mPtrsv_all.setMode(Mode.PULL_FROM_START);
		mPtrsv_all.setOnRefreshListener(new OnRefreshListener2<ScrollView>()
		{

			@Override
			public void onPullDownToRefresh(PullToRefreshBase<ScrollView> refreshView)
			{
				if (mCheckActModel == null)
				{
					requestData();
				} else
				{
					requestCalculate();
				}
			}

			@Override
			public void onPullUpToRefresh(PullToRefreshBase<ScrollView> refreshView)
			{
			}
		});
		mPtrsv_all.setRefreshing();
	}

	private void requestData()
	{
		RequestModel model = new RequestModel();
		model.putCtl("store_pay");
		model.putAct("check");
		model.put("order_id", mId);

		InterfaceServer.getInstance().requestInterface(model, new SDRequestCallBack<Store_pay_checkActModel>()
		{
			@Override
			public void onSuccess(ResponseInfo<String> responseInfo)
			{
				if (actModel.getStatus() == 1)
				{
					mCheckActModel = actModel;
					bindData(actModel);
				}
				super.onSuccess(responseInfo);
			}

			@Override
			public void onFinish()
			{
				mPtrsv_all.onRefreshComplete();
				super.onFinish();
			}
		});
	}

	protected void bindData(Store_pay_checkActModel actModel)
	{
		if (actModel == null)
		{
			return;
		}

		// 支付方式
		if (actModel.getShow_payment() == 1)
		{
			SDViewUtil.show(mLl_payment_layout);
			List<Payment_listModel> listPayment = actModel.getPayment_list();
			if (isEmpty(listPayment))
			{
				SDViewUtil.hide(mLl_payment_layout);
			} else
			{
				SDViewUtil.show(mLl_payment_layout);
				mAdapterPaymentList = new StoreOrderDetailPaymentListAdapter(listPayment, StoreOrderDetailActivity.this);
				mLl_payment.setAdapter(mAdapterPaymentList);
				mAdapterPaymentList.setListenerItemState(new ItemStateListener<Payment_listModel>()
				{

					@Override
					public void onSelected(int position, Payment_listModel item)
					{
						if (item != null)
						{
							mPaymentId = item.getId();
						}

						if (mPv_payment.isSelected())
						{
							mPv_payment.setStateNormal();
						}
						requestCalculate();
					}

					@Override
					public void onNormal(int position, Payment_listModel item)
					{
					}
				});
			}
		} else
		{
			SDViewUtil.hide(mLl_payment_layout);
		}

		// 余额支付
		if (actModel.getHas_account() == 1)
		{
			SDViewUtil.show(mLl_account_payment_layout);
			String accountMoneyFormat = SDFormatUtil.formatMoneyChina(actModel.getAccount_money());
			SDViewBinder.setTextView(mPv_payment.mTv_name, accountMoneyFormat);
			mPv_payment.setOnClickListener(new OnClickListener()
			{

				@Override
				public void onClick(View v)
				{
					if (!mPv_payment.isSelected())
					{
						mPv_payment.setStateSelected();
					}
				}
			});
			mPv_payment.setListenerState(new SDSelectViewStateListener()
			{

				@Override
				public void onSelected(View v)
				{
					if (mAdapterPaymentList != null)
					{
						mAdapterPaymentList.getSelectManager().clearSelected();
					}
					mUseAccountPayment = 1;
					mPaymentId = 0;
					requestCalculate();
				}

				@Override
				public void onNormal(View v)
				{
					mUseAccountPayment = 0;
				}
			});
		} else
		{
			SDViewUtil.hide(mLl_account_payment_layout);
		}

		// 费用信息
		bindFeeData(actModel.getPay_data());
	}

	/**
	 * 计算接口
	 */
	protected void requestCalculate()
	{
		RequestModel model = new RequestModel();
		model.putCtl("store_pay");
		model.putAct("count_store_pay_total");
		model.put("order_id", mId);
		model.put("all_account_money", mUseAccountPayment);
		model.put("payment_id", mPaymentId);

		InterfaceServer.getInstance().requestInterface(model, new SDRequestCallBack<Store_pay_count_store_pay_totalActModel>()
		{
			@Override
			public void onStart()
			{
				SDDialogManager.showProgressDialog("");
				super.onStart();
			}

			@Override
			public void onSuccess(ResponseInfo<String> responseInfo)
			{
				if (actModel.getStatus() == 1)
				{
					bindCalculateData(actModel);
				}
				super.onSuccess(responseInfo);
			}

			@Override
			public void onFinish()
			{
				SDDialogManager.dismissProgressDialog();
				mPtrsv_all.onRefreshComplete();
				super.onFinish();
			}
		});
	}

	protected void bindCalculateData(Store_pay_count_store_pay_totalActModel actModel)
	{
		if (actModel == null)
		{
			return;
		}

		// 费用信息
		bindFeeData(actModel.getPay_data());
	}

	private void bindFeeData(List<FeeinfoModel> listModel)
	{
		if (isEmpty(listModel))
		{
			SDViewUtil.hide(mLl_fee_layout);
		} else
		{
			SDViewUtil.show(mLl_fee_layout);
			OrderDetailFeeAdapter adapter = new OrderDetailFeeAdapter(listModel, StoreOrderDetailActivity.this);
			mLl_fee.setAdapter(adapter);
		}
	}

	protected void requestSubmit()
	{
		RequestModel model = new RequestModel();
		model.putCtl("store_pay");
		model.putAct("done");
		model.put("order_id", mId);
		model.put("all_account_money", mUseAccountPayment);
		model.put("payment_id", mPaymentId);

		InterfaceServer.getInstance().requestInterface(model, new SDRequestCallBack<BaseActModel>()
		{
			@Override
			public void onStart()
			{
				SDDialogManager.showProgressDialog("");
				super.onStart();
			}

			@Override
			public void onSuccess(ResponseInfo<String> responseInfo)
			{
				if (actModel.getStatus() == 1)
				{
					// TODO 跳到支付页面
					Intent intent = new Intent(StoreOrderDetailActivity.this, StorePayDoneActivity.class);
					intent.putExtra(StorePayDoneActivity.EXTRA_ORDER_ID, mId);
					startActivity(intent);
					finish();
				}
				super.onSuccess(responseInfo);
			}

			@Override
			public void onFinish()
			{
				SDDialogManager.dismissProgressDialog();
				super.onFinish();
			}
		});
	}
}
