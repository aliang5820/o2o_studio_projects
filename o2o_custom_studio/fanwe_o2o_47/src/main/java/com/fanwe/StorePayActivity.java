package com.fanwe;

import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.EditText;
import android.widget.ScrollView;
import android.widget.TextView;

import com.fanwe.adapter.StorePayPromoteAdapter;
import com.fanwe.constant.ApkConstant;
import com.fanwe.constant.Constant.TitleType;
import com.fanwe.http.InterfaceServer;
import com.fanwe.http.SDRequestCallBack;
import com.fanwe.library.customview.SDGridLinearLayout;
import com.fanwe.library.dialog.SDDialogManager;
import com.fanwe.library.title.SDTitleItem;
import com.fanwe.library.utils.SDToast;
import com.fanwe.library.utils.SDTypeParseUtil;
import com.fanwe.library.utils.SDViewBinder;
import com.fanwe.library.utils.UrlLinkBuilder;
import com.fanwe.model.RequestModel;
import com.fanwe.model.Store_pay_make_orderActModel;
import com.fanwe.model.Store_pay_promoteActModel;
import com.fanwe.o2o.newo2o.R;
import com.handmark.pulltorefresh.library.PullToRefreshBase;
import com.handmark.pulltorefresh.library.PullToRefreshBase.Mode;
import com.handmark.pulltorefresh.library.PullToRefreshBase.OnRefreshListener2;
import com.handmark.pulltorefresh.library.PullToRefreshScrollView;
import com.lidroid.xutils.http.HttpHandler;
import com.lidroid.xutils.http.ResponseInfo;
import com.lidroid.xutils.view.annotation.ViewInject;

/**
 * 到店付
 * 
 * @author Administrator
 * 
 */
public class StorePayActivity extends BaseActivity
{
	/** 门店id (int) */
	public static final String EXTRA_STORE_ID = "extra_store_id";

	@ViewInject(R.id.ptrsv_all)
	private PullToRefreshScrollView mPtrsv_all;

	@ViewInject(R.id.et_money)
	private EditText mEt_money;

	@ViewInject(R.id.et_other_money)
	private EditText mEt_other_money;

	@ViewInject(R.id.ll_content)
	private SDGridLinearLayout mLl_content;

	@ViewInject(R.id.tv_pay_price)
	private TextView mTv_pay_price;

	@ViewInject(R.id.tv_pay)
	private TextView mTv_pay;

	private StorePayPromoteAdapter mAdapter;
	private Store_pay_promoteActModel mActModel;
	private HttpHandler<String> mRequestHandler;

	/** 门店id */
	private int mId;
	/** 消费金额 */
	private double mMoney;
	/** 不可优惠金额 */
	private double mOtherMoney;

	@Override
	protected void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
		setmTitleType(TitleType.TITLE);
		setContentView(R.layout.act_store_pay);
		init();
	}

	private void init()
	{
		getIntentData();
		if (mId <= 0)
		{
			SDToast.showToast("门店id为空");
			finish();
			return;
		}
		initEditText();
		initPullToRefreshScrollView();
		register();
	}

	private void initEditText()
	{
		mEt_money.addTextChangedListener(new TextWatcher()
		{

			@Override
			public void onTextChanged(CharSequence s, int start, int before, int count)
			{
			}

			@Override
			public void beforeTextChanged(CharSequence s, int start, int count, int after)
			{
			}

			@Override
			public void afterTextChanged(Editable s)
			{
				String content = s.toString();
				mMoney = SDTypeParseUtil.getDouble(content);
				requestData();
			}
		});

		mEt_other_money.addTextChangedListener(new TextWatcher()
		{

			@Override
			public void onTextChanged(CharSequence s, int start, int before, int count)
			{
			}

			@Override
			public void beforeTextChanged(CharSequence s, int start, int count, int after)
			{
			}

			@Override
			public void afterTextChanged(Editable s)
			{
				String content = s.toString();
				mOtherMoney = SDTypeParseUtil.getDouble(content);
				requestData();
			}
		});
	}

	private void getIntentData()
	{
		mId = getIntent().getIntExtra(EXTRA_STORE_ID, 0);
	}

	private void initPullToRefreshScrollView()
	{
		mPtrsv_all.setMode(Mode.PULL_FROM_START);
		mPtrsv_all.setOnRefreshListener(new OnRefreshListener2<ScrollView>()
		{

			@Override
			public void onPullDownToRefresh(PullToRefreshBase<ScrollView> refreshView)
			{
				requestData();
			}

			@Override
			public void onPullUpToRefresh(PullToRefreshBase<ScrollView> refreshView)
			{
			}
		});
		mPtrsv_all.setRefreshing();
	}

	private boolean validateMoney()
	{
		if (mMoney >= 0 && mOtherMoney >= 0 && mMoney >= mOtherMoney)
		{
			return true;
		} else
		{
			return false;
		}
	}

	protected void requestData()
	{
		if (mRequestHandler != null)
		{
			mRequestHandler.cancel();
		}

		if (!validateMoney())
		{
			clearMoney();
			mPtrsv_all.onRefreshComplete();
			return;
		}

		RequestModel model = new RequestModel();
		model.putCtl("store_pay");
		model.putAct("promote");
		model.put("location_id", mId);
		model.put("money", mMoney);
		model.put("other_money", mOtherMoney);

		mRequestHandler = InterfaceServer.getInstance().requestInterface(model, new SDRequestCallBack<Store_pay_promoteActModel>()
		{

			@Override
			public void onSuccess(ResponseInfo<String> responseInfo)
			{
				if (actModel.getStatus() == 1)
				{
					mActModel = actModel;
					mTitle.setMiddleTextTop(actModel.getLocation_name());

					mAdapter = new StorePayPromoteAdapter(actModel.getPromote(), StorePayActivity.this);
					mLl_content.setAdapter(mAdapter);

					String payPriceFormat = actModel.getPay_priceFormat();
					SDViewBinder.setTextView(mTv_pay_price, payPriceFormat);
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

	private void clearMoney()
	{
		SDViewBinder.setTextView(mTv_pay_price, "¥0");
		mLl_content.removeAllViews();
	}

	private void register()
	{

		mTitle.initRightItem(1);
		mTitle.getItemRight(0).setTextBot("买单说明");

		mTv_pay.setOnClickListener(new OnClickListener()
		{

			@Override
			public void onClick(View v)
			{
				// TODO 请求生成订单接口
				if (validateParam())
				{
					requestMakeOrder();
				}
			}
		});
	}

	@Override
	public void onCLickRight_SDTitleSimple(SDTitleItem v, int index)
	{
		if (mActModel != null)
		{
			UrlLinkBuilder builder = new UrlLinkBuilder(ApkConstant.getWapUrl());
			builder.add("ctl", "stores_explain");
			builder.add("data_id", String.valueOf(mActModel.getLocation_id()));
			Intent intent = new Intent(this, AppWebViewActivity.class);
			intent.putExtra(AppWebViewActivity.EXTRA_URL, builder.build());
			startActivity(intent);
		}
		super.onCLickRight_SDTitleSimple(v, index);
	}

	protected boolean validateParam()
	{
		if (mMoney <= 0)
		{
			SDToast.showToast("请输入金额");
			return false;
		}
		return true;
	}

	protected void requestMakeOrder()
	{
		RequestModel model = new RequestModel();
		model.putCtl("store_pay");
		model.putAct("make_order");
		model.put("location_id", mId);
		model.put("money", mMoney);

		InterfaceServer.getInstance().requestInterface(model, new SDRequestCallBack<Store_pay_make_orderActModel>()
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
					// TODO 跳到支付界面
					Intent intent = new Intent(StorePayActivity.this, StoreOrderDetailActivity.class);
					intent.putExtra(StoreOrderDetailActivity.EXTRA_ORDER_ID, actModel.getOrder_id());
					startActivity(intent);
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
