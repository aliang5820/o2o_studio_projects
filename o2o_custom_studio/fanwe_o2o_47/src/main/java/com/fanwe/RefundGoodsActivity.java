package com.fanwe;

import java.util.ArrayList;
import java.util.List;

import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.fanwe.adapter.MyOrderListGoodsAdapter;
import com.fanwe.constant.Constant.TitleType;
import com.fanwe.customview.SDStickyScrollView;
import com.fanwe.event.EnumEventTag;
import com.fanwe.http.InterfaceServer;
import com.fanwe.http.SDRequestCallBack;
import com.fanwe.library.customview.StickyScrollView;
import com.fanwe.library.dialog.SDDialogManager;
import com.fanwe.library.utils.SDToast;
import com.fanwe.model.BaseActModel;
import com.fanwe.model.RequestModel;
import com.fanwe.model.Uc_orderGoodsModel;
import com.fanwe.model.Uc_order_refundActModel;
import com.fanwe.o2o.newo2o.R;
import com.handmark.pulltorefresh.library.PullToRefreshBase;
import com.handmark.pulltorefresh.library.PullToRefreshBase.Mode;
import com.handmark.pulltorefresh.library.PullToRefreshBase.OnRefreshListener2;
import com.lidroid.xutils.http.ResponseInfo;
import com.sunday.eventbus.SDEventManager;

/**
 * 商品申请退款
 * 
 * @author Administrator
 * 
 */
public class RefundGoodsActivity extends BaseActivity
{

	public static final String EXTRA_ID = "extra_id";

	protected SDStickyScrollView mSsv_all;
	protected LinearLayout mLl_deals;
	protected LinearLayout mLl_coupon;
	protected EditText mEt_content;
	protected TextView mTv_submit;

	protected String mStrContent;

	protected List<Uc_orderGoodsModel> mListModel = new ArrayList<Uc_orderGoodsModel>();
	protected MyOrderListGoodsAdapter mAdapter;

	protected int mId;

	@Override
	protected void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
		setmTitleType(TitleType.TITLE);
		setContentView(R.layout.act_refund_goods);
		init();
	}

	protected void init()
	{
		getIntentData();
		initTitle();
		findViews();
		bindDefaultData();
		initSDStickyScrollView();
		registerClick();
	}

	protected void initTitle()
	{
		mTitle.setMiddleTextTop("申请退款");
	}

	private void getIntentData()
	{
		mId = getIntent().getIntExtra(EXTRA_ID, 0);
		if (mId <= 0)
		{
			SDToast.showToast("id为空");
			finish();
		}
	}

	private void bindDefaultData()
	{
		mAdapter = new MyOrderListGoodsAdapter(mListModel, false, mActivity);
	}

	private void findViews()
	{
		mSsv_all = (SDStickyScrollView) findViewById(R.id.ssv_all);
		mLl_deals = (LinearLayout) findViewById(R.id.ll_deals);
		mLl_coupon = (LinearLayout) findViewById(R.id.ll_coupon);
		mEt_content = (EditText) findViewById(R.id.et_content);
		mTv_submit = (TextView) findViewById(R.id.tv_submit);
	}

	private void registerClick()
	{
		mTv_submit.setOnClickListener(new OnClickListener()
		{

			@Override
			public void onClick(View v)
			{
				clickSubmit();
			}
		});
	}

	protected boolean validateParams()
	{
		mStrContent = mEt_content.getText().toString();
		if (isEmpty(mStrContent))
		{
			SDToast.showToast("请输入内容");
			return false;
		}
		return true;
	}

	protected void clickSubmit()
	{
		if (validateParams())
		{
			requestSubmit();
		}

	}

	protected void requestSubmit()
	{
		RequestModel model = new RequestModel();
		model.putUser();
		model.putCtl("uc_order");
		model.putAct("do_refund");
		model.put("content", mStrContent);
		model.put("item_id", mId);

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

	private void initSDStickyScrollView()
	{
		mSsv_all.setMode(Mode.PULL_FROM_START);
		mSsv_all.setOnRefreshListener(new OnRefreshListener2<StickyScrollView>()
		{

			@Override
			public void onPullDownToRefresh(PullToRefreshBase<StickyScrollView> refreshView)
			{
				requestData();
			}

			@Override
			public void onPullUpToRefresh(PullToRefreshBase<StickyScrollView> refreshView)
			{

			}
		});
		mSsv_all.setRefreshing();
	}

	protected void requestData()
	{
		RequestModel model = new RequestModel();
		model.putUser();
		model.putCtl("uc_order");
		model.putAct("refund");
		model.put("item_id", mId);

		InterfaceServer.getInstance().requestInterface(model, new SDRequestCallBack<Uc_order_refundActModel>()
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
					updateTitle(actModel);
				}
			}
		});
	}

	protected void updateTitle(BaseActModel actModel)
	{
		if (actModel != null)
		{
			String title = actModel.getPage_title();
			if (!isEmpty(title))
			{
				mTitle.setMiddleTextTop(title);
			}
		}
	}

	protected void bindData()
	{
		if (mAdapter != null)
		{
			mLl_deals.removeAllViews();
			for (int i = 0; i < mAdapter.getCount(); i++)
			{
				View view = mAdapter.getView(i, null, null);
				mLl_deals.addView(view);
			}
		}
	}

}
