package com.fanwe;

import java.util.List;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.EditText;
import android.widget.TextView;

import com.fanwe.adapter.StoreOrderDetailPaymentListAdapter;
import com.fanwe.constant.Constant.TitleType;
import com.fanwe.http.InterfaceServer;
import com.fanwe.http.SDRequestCallBack;
import com.fanwe.library.adapter.SDAdapter.ItemStateListener;
import com.fanwe.library.common.SDSelectManager.SDSelectManagerListener;
import com.fanwe.library.customview.SDGridLinearLayout;
import com.fanwe.library.dialog.SDDialogManager;
import com.fanwe.library.utils.SDToast;
import com.fanwe.library.utils.SDViewUtil;
import com.fanwe.library.view.select.SDSelectViewManager;
import com.fanwe.model.Payment_listModel;
import com.fanwe.model.RequestModel;
import com.fanwe.model.Uc_chargeDoneActModel;
import com.fanwe.model.Uc_chargeIndexActModel;
import com.fanwe.o2o.newo2o.R;
import com.lidroid.xutils.http.ResponseInfo;
import com.lidroid.xutils.view.annotation.ViewInject;

public class ChargePayActivity extends BaseActivity
{
	@ViewInject(R.id.tv_money_one)
	private TextView mTv_money_one;
	@ViewInject(R.id.tv_money_two)
	private TextView mTv_money_two;
	@ViewInject(R.id.tv_money_three)
	private TextView mTv_money_three;
	@ViewInject(R.id.tv_money_four)
	private TextView mTv_money_four;
	@ViewInject(R.id.tv_money_five)
	private TextView mTv_money_five;
	@ViewInject(R.id.et_money_other)
	private EditText mEt_money_other;

	@ViewInject(R.id.ll_payment)
	private SDGridLinearLayout mLl_payment;
	@ViewInject(R.id.tv_submit)
	private TextView mTv_submit;

	private SDSelectViewManager<TextView> mManager = new SDSelectViewManager<TextView>();
	private StoreOrderDetailPaymentListAdapter mAdapterPaymentList;

	/** 支付方式id */
	private int mPaymentId;
	/** 支付金额 */
	private Double mMoney;

	@Override
	protected void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
		setmTitleType(TitleType.TITLE);
		setContentView(R.layout.act_charge_pay);
		init();
	}

	private void init()
	{
		initTitle();
		requestData();
		registerClick();
	}

	private void requestData()
	{
		RequestModel model = new RequestModel();
		model.putCtl("uc_charge");

		InterfaceServer.getInstance().requestInterface(model, new SDRequestCallBack<Uc_chargeIndexActModel>()
		{
			@Override
			public void onSuccess(ResponseInfo<String> responseInfo)
			{
				if (actModel.getStatus() == 1)
				{
					bindData(actModel);
				}
				super.onSuccess(responseInfo);
			}

			@Override
			public void onFinish()
			{
				super.onFinish();
			}
		});
	}

	protected void bindData(Uc_chargeIndexActModel actModel)
	{
		if (actModel == null)
		{
			return;
		}
		List<Payment_listModel> listPayment = actModel.getPayment_list();

		mAdapterPaymentList = new StoreOrderDetailPaymentListAdapter(listPayment, ChargePayActivity.this);
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
			}

			@Override
			public void onNormal(int position, Payment_listModel item)
			{
			}
		});
	}

	private void initTitle()
	{
		mTitle.setMiddleTextTop("会员充值");
	}

	private void registerClick()
	{
		mManager.setListener(new SDSelectManagerListener<TextView>()
		{
			public void onNormal(int index, TextView item)
			{
				SDViewUtil.setBackgroundResource(item, R.drawable.layer_white_stroke_all);
				item.setTextColor(getResources().getColor(R.color.gray));
			}

			public void onSelected(int index, TextView item)
			{
				SDViewUtil.setBackgroundResource(item, R.drawable.layer_transparent_stroke_main_color);
				SDViewUtil.setBackgroundResource(mEt_money_other, R.drawable.layer_white_stroke_all);
				mEt_money_other.setTextColor(getResources().getColor(R.color.gray));
				item.setTextColor(getResources().getColor(R.color.main_color));

			}
		});

		TextView[] items = new TextView[] { mTv_money_one, mTv_money_two, mTv_money_three, mTv_money_four, mTv_money_five };
		mManager.setItems(items);

		mEt_money_other.setOnClickListener(new OnClickListener()
		{
			@Override
			public void onClick(View arg0)
			{
				mManager.clearSelected();
				mEt_money_other.setTextColor(getResources().getColor(R.color.main_color));
				SDViewUtil.setBackgroundResource(mEt_money_other, R.drawable.layer_transparent_stroke_main_color);
			}
		});

		mTv_submit.setOnClickListener(new OnClickListener()
		{
			@Override
			public void onClick(View v)
			{
				requestSubmit();
			}
		});
	}

	protected void requestSubmit()
	{

		if (mManager.getSelectedIndex() >= 0)
		{
			mMoney = Double.valueOf(mManager.getSelectedItem().getText().toString());
		} else
		{
			// 输入框内容
			String money = mEt_money_other.getText().toString();
			if (!TextUtils.isEmpty(money))
			{
				mMoney = Double.parseDouble(money);
			}
		}

		if (mPaymentId <= 0)
		{
			SDToast.showToast("请选择支付方式");
			return;
		}

		RequestModel model = new RequestModel();
		model.putCtl("uc_charge");
		model.putAct("done");
		model.put("payment_id", mPaymentId);
		model.put("money", mMoney);

		InterfaceServer.getInstance().requestInterface(model, new SDRequestCallBack<Uc_chargeDoneActModel>()
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
					Intent intent = new Intent(ChargePayActivity.this, ChargePayDoneActivity.class);
					intent.putExtra(ChargePayDoneActivity.EXTRA_ORDER_ID, actModel.getOrder_id());
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
