package com.fanwe;

import java.util.ArrayList;
import java.util.List;

import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import com.fanwe.adapter.MerchantListOrderAdapter;
import com.fanwe.app.App;
import com.fanwe.constant.Constant.TitleType;
import com.fanwe.dao.LocalUserModelDao;
import com.fanwe.http.InterfaceServer;
import com.fanwe.http.SDRequestCallBack;
import com.fanwe.library.customview.ClearEditText;
import com.fanwe.library.dialog.SDDialogBase;
import com.fanwe.library.dialog.SDDialogManager;
import com.fanwe.library.title.SDTitleItem;
import com.fanwe.library.utils.SDResourcesUtil;
import com.fanwe.library.utils.SDToast;
import com.fanwe.library.utils.SDTypeParseUtil;
import com.fanwe.library.utils.SDViewBinder;
import com.fanwe.library.utils.SDViewUtil;
import com.fanwe.model.Auto_orderActModel;
import com.fanwe.model.LocalUserModel;
import com.fanwe.model.RequestModel;
import com.fanwe.model.StoreModel;
import com.fanwe.model.Stores_indexActModel;
import com.fanwe.model.Wap_qrcodeActModel;
import com.fanwe.o2o.newo2o.R;
import com.handmark.pulltorefresh.library.PullToRefreshBase;
import com.handmark.pulltorefresh.library.PullToRefreshBase.Mode;
import com.handmark.pulltorefresh.library.PullToRefreshBase.OnRefreshListener2;
import com.handmark.pulltorefresh.library.PullToRefreshListView;
import com.lidroid.xutils.exception.HttpException;
import com.lidroid.xutils.http.ResponseInfo;
import com.lidroid.xutils.view.annotation.ViewInject;

/**
 * 商家列表界面（可以自主下单的商家列表）
 * 
 * @author js02
 * 
 */
public class MerchantListOrderActivity extends BaseActivity
{

	public static final int REQUEST_CODE_REQUEST_SCAN = 1;

	@ViewInject(R.id.act_merchant_list_order_prlv_stores)
	private PullToRefreshListView mPrlvStores = null;

	@ViewInject(R.id.act_merchant_list_order_ll_empty)
	private LinearLayout mLlEmpty = null;

	@ViewInject(R.id.act_merchant_list_order_tv_search)
	private TextView mTvSearch = null;

	@ViewInject(R.id.act_merchant_list_order_et_search)
	private ClearEditText mEtSearch = null;

	private MerchantListOrderAdapter mAdapter = null;
	private List<StoreModel> mListMerchantModel = new ArrayList<StoreModel>();

	private int mPage = 1;
	private int mTotalPage = 0;

	private String mStrKeyword = null;

	@Override
	protected void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
		setmTitleType(TitleType.TITLE);
		setContentView(R.layout.act_merchant_list_order);
		init();
	}

	private void init()
	{
		initTitle();
		registeClick();
		bindDefaultData();
		initPullToRefreshListView();
	}

	private void bindDefaultData()
	{
		mAdapter = new MerchantListOrderAdapter(mListMerchantModel, this);
		mPrlvStores.setAdapter(mAdapter);
	}

	private void initTitle()
	{
		mTitle.setMiddleTextTop(SDResourcesUtil.getString(R.string.supplier));

		mTitle.initRightItem(1);
		mTitle.getItemRight(0).setImageLeft(R.drawable.ic_scan_code);
	}

	@Override
	public void onCLickRight_SDTitleSimple(SDTitleItem v, int index)
	{
		if (LocalUserModelDao.queryModel() == null)
		{
			startActivity(new Intent(getApplicationContext(), LoginActivity.class));
		} else
		{
			Intent intent = new Intent(getApplicationContext(), MyCaptureActivity.class);
			intent.putExtra(MyCaptureActivity.EXTRA_IS_FINISH_ACTIVITY, 1);
			startActivityForResult(intent, REQUEST_CODE_REQUEST_SCAN);
		}
	}

	private void registeClick()
	{
		mTvSearch.setOnClickListener(this);
	}

	private void initPullToRefreshListView()
	{
		mPrlvStores.setMode(Mode.BOTH);
		mPrlvStores.setOnRefreshListener(new OnRefreshListener2<ListView>()
		{

			@Override
			public void onPullDownToRefresh(PullToRefreshBase<ListView> refreshView)
			{
				refreshData();
			}

			@Override
			public void onPullUpToRefresh(PullToRefreshBase<ListView> refreshView)
			{
				loadMoreData();
			}

		});
		mPrlvStores.setOnItemClickListener(new OnItemClickListener()
		{
			@Override
			public void onItemClick(AdapterView<?> parent, View view, int position, long id)
			{
				StoreModel model = mAdapter.getItem((int) id);
				if (model != null)
				{
					Intent itemintent = new Intent();
					itemintent.putExtra(com.fanwe.StoreDetailActivity.EXTRA_MERCHANT_ID, model.getId());
					itemintent.setClass(MerchantListOrderActivity.this, com.fanwe.StoreDetailActivity.class);
					startActivity(itemintent);
				}
			}
		});
		mPrlvStores.setRefreshing();
	}

	protected void refreshData()
	{
		mPage = 1;
		requestMerchantList(false);
	}

	protected void loadMoreData()
	{
		if (++mPage > mTotalPage && mTotalPage > 0)
		{
			mPrlvStores.onRefreshComplete();
			SDToast.showToast("没有更多数据了");
		} else
		{
			requestMerchantList(true);
		}

	}

	private void requestMerchantList(final boolean isLoadMore)
	{
		RequestModel model = new RequestModel();
		model.putCtl("merchantlist");
		model.put("keyword", mStrKeyword);
		model.put("is_auto_order", 1);
		SDRequestCallBack<Stores_indexActModel> handler = new SDRequestCallBack<Stores_indexActModel>()
		{

			@Override
			public void onStart()
			{
				SDDialogManager.showProgressDialog("请稍候...");
			}

			@Override
			public void onSuccess(ResponseInfo<String> responseInfo)
			{
				if (actModel.getStatus() == 1)
				{
					if (actModel.getPage() != null)
					{
						mPage = actModel.getPage().getPage();
						mTotalPage = actModel.getPage().getPage_total();
					}
					SDViewUtil.updateAdapterByList(mListMerchantModel, actModel.getItem(), mAdapter, isLoadMore);
				}
			}

			@Override
			public void onFailure(HttpException error, String msg)
			{

			}

			@Override
			public void onFinish()
			{
				dealFinishRequest();
			}
		};
		InterfaceServer.getInstance().requestInterface(model, handler);

	}

	/**
	 * 提交二维码扫描结果，以获取商家信息
	 * 
	 * @param resultString
	 */
	private void requestMerchanMsgByCode(String resultString)
	{
		if (!TextUtils.isEmpty(resultString))
		{
			RequestModel model = new RequestModel();
			model.putCtl("wap_qrcode");
			model.put("wap_url", resultString);
			SDRequestCallBack<Wap_qrcodeActModel> handler = new SDRequestCallBack<Wap_qrcodeActModel>()
			{

				@Override
				public void onStart()
				{
					SDDialogManager.showProgressDialog("请稍候...");
				}

				@Override
				public void onSuccess(ResponseInfo<String> responseInfo)
				{
					int status = actModel.getStatus();
					if (status == 0)
					{

					} else if (status == 1)
					{
						int openType = SDTypeParseUtil.getInt(actModel.getOp_type(), 0);
						switch (openType)
						{
						case 0: // 下单窗口
							showConfirmOrderDialog(actModel);
							break;
						case 1: // 商家详细页
							String merchantId = actModel.getId();
							if (!TextUtils.isEmpty(merchantId))
							{
								Intent intent = new Intent(getApplicationContext(), StoreDetailActivity.class);
								intent.putExtra(StoreDetailActivity.EXTRA_MERCHANT_ID, merchantId);
								startActivity(intent);
							}
							break;

						default:
							showConfirmOrderDialog(actModel);
							break;
						}
					}
				}

				@Override
				public void onFailure(HttpException error, String msg)
				{

				}

				@Override
				public void onFinish()
				{
					SDDialogManager.dismissProgressDialog();
				}
			};
			InterfaceServer.getInstance().requestInterface(model, handler);
		}
	}

	/**
	 * 显示下单窗口
	 */
	private void showConfirmOrderDialog(final Wap_qrcodeActModel model)
	{
		if (model != null)
		{
			View view = LayoutInflater.from(App.getApplication()).inflate(R.layout.dialog_merchant_submit_order, null);
			final Dialog dialog = new SDDialogBase().setDialogView(view);
			dialog.show();
			TextView tvName = (TextView) view.findViewById(R.id.dialog_merchant_submit_order_tv_name);
			TextView tvBrief = (TextView) view.findViewById(R.id.dialog_merchant_submit_order_tv_brief);
			final EditText etMoney = (EditText) view.findViewById(R.id.dialog_merchant_submit_order_et_money);
			TextView tvConfirm = (TextView) view.findViewById(R.id.dialog_merchant_submit_order_tv_confirm);
			TextView tvCancel = (TextView) view.findViewById(R.id.dialog_merchant_submit_order_tv_cancel);
			final TextView tvTip = (TextView) view.findViewById(R.id.dialog_merchant_submit_order_tv_tip);

			SDViewBinder.setTextView(tvName, model.getName());
			SDViewBinder.setTextView(tvBrief, model.getMobile_brief());
			etMoney.addTextChangedListener(new TextWatcher()
			{

				@Override
				public void onTextChanged(CharSequence s, int start, int before, int count)
				{
					// TODO Auto-generated method stub

				}

				@Override
				public void beforeTextChanged(CharSequence s, int start, int count, int after)
				{
					// TODO Auto-generated method stub

				}

				@Override
				public void afterTextChanged(Editable s)
				{
					String money = s.toString();
					if (!TextUtils.isEmpty(money))
					{
						tvTip.setText("");
					}
				}
			});
			tvConfirm.setOnClickListener(new OnClickListener()
			{
				@Override
				public void onClick(View v)
				{
					String money = etMoney.getText().toString();
					if (TextUtils.isEmpty(money))
					{
						tvTip.setText("请输入金额");
						return;
					}
					requestAutoOrder(model.getId(), money, dialog);
				}
			});
			tvCancel.setOnClickListener(new OnClickListener()
			{
				@Override
				public void onClick(View v)
				{
					dialog.dismiss();
				}
			});
		}
	}

	/**
	 * 请求自主下单接口
	 */
	protected void requestAutoOrder(String id, String money, final Dialog dialog)
	{
		LocalUserModel user = LocalUserModelDao.queryModel();
		if (user != null)
		{
			RequestModel model = new RequestModel();
			model.putCtl("auto_order");
			model.put("location_id", id);
			model.put("money", money);
			model.put("email", user.getUser_name());
			model.put("pwd", user.getUser_pwd());
			SDRequestCallBack<Auto_orderActModel> handler = new SDRequestCallBack<Auto_orderActModel>()
			{

				@Override
				public void onStart()
				{
					SDDialogManager.showProgressDialog("请稍候...");
				}

				@Override
				public void onSuccess(ResponseInfo<String> responseInfo)
				{
					int status = actModel.getStatus();
					if (status == 1)
					{
						SDDialogManager.dismissProgressDialog();
						dialog.dismiss();
						// TODO 跳到订单详细页
					}
				}

				@Override
				public void onFailure(HttpException error, String msg)
				{

				}

				@Override
				public void onFinish()
				{
					SDDialogManager.dismissProgressDialog();
				}
			};
			InterfaceServer.getInstance().requestInterface(model, handler);
		}
	}

	protected void dealFinishRequest()
	{
		SDDialogManager.dismissProgressDialog();
		mPrlvStores.onRefreshComplete();
		SDViewUtil.toggleEmptyMsgByList(mListMerchantModel, mLlEmpty);
	}

	@Override
	public void onClick(View v)
	{
		switch (v.getId())
		{

		case R.id.act_merchant_list_order_tv_search:
			clickSearch();
			break;

		default:
			break;
		}
	}

	private void clickSearch()
	{
		mStrKeyword = mEtSearch.getText().toString();
		requestMerchantList(false);
	}

	@Override
	protected void onActivityResult(int request, int result, Intent data)
	{
		switch (request)
		{
		case REQUEST_CODE_REQUEST_SCAN:
			if (result == MyCaptureActivity.RESULT_CODE_SCAN_SUCCESS)
			{
				if (data != null)
				{
					String resultString = data.getStringExtra(MyCaptureActivity.EXTRA_RESULT_SUCCESS_STRING);
					requestMerchanMsgByCode(resultString);
				}
			}
			break;

		default:
			break;
		}
		super.onActivityResult(request, result, data);
	}

}