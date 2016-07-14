package com.fanwe.fragment;

import java.util.List;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.ScrollView;
import android.widget.TextView;

import com.fanwe.ConfirmOrderActivity;
import com.fanwe.LoginActivity;
import com.fanwe.MainActivity;
import com.fanwe.adapter.ShopCartAdapter;
import com.fanwe.common.CommonInterface;
import com.fanwe.constant.Constant.TitleType;
import com.fanwe.customview.SDListViewInScroll;
import com.fanwe.event.EnumEventTag;
import com.fanwe.http.InterfaceServer;
import com.fanwe.http.SDRequestCallBack;
import com.fanwe.library.common.SDActivityManager;
import com.fanwe.library.customview.ClearEditText;
import com.fanwe.library.customview.SDSendValidateButton;
import com.fanwe.library.customview.SDSendValidateButton.SDSendValidateButtonListener;
import com.fanwe.library.dialog.SDDialogManager;
import com.fanwe.library.title.SDTitleItem;
import com.fanwe.library.utils.SDToast;
import com.fanwe.library.utils.SDViewUtil;
import com.fanwe.model.CartGoodsModel;
import com.fanwe.model.Cart_check_cartActModel;
import com.fanwe.model.Cart_indexActModel;
import com.fanwe.model.LocalUserModel;
import com.fanwe.model.RequestModel;
import com.fanwe.model.Sms_send_sms_codeActModel;
import com.fanwe.model.User_infoModel;
import com.fanwe.o2o.newo2o.R;
import com.fanwe.work.AppRuntimeWorker;
import com.handmark.pulltorefresh.library.PullToRefreshBase;
import com.handmark.pulltorefresh.library.PullToRefreshBase.Mode;
import com.handmark.pulltorefresh.library.PullToRefreshBase.OnRefreshListener2;
import com.handmark.pulltorefresh.library.PullToRefreshScrollView;
import com.lidroid.xutils.exception.HttpException;
import com.lidroid.xutils.http.ResponseInfo;
import com.lidroid.xutils.view.annotation.ViewInject;
import com.sunday.eventbus.SDBaseEvent;

/**
 * 购物车
 * 
 * @author js02
 * 
 */
public class ShopCartFragment extends BaseFragment
{

	@ViewInject(R.id.ptrsv_all)
	private PullToRefreshScrollView mPtrsvAll;

	@ViewInject(R.id.lv_cart_goods)
	private SDListViewInScroll mLvCartGoods;

	@ViewInject(R.id.rl_empty)
	private RelativeLayout mRlEmpty;
	/** 手机登录布局 */
	@ViewInject(R.id.ll_phone_login)
	private LinearLayout mLlPhoneLogin;
	/** 手机登录输入手机号的输入框 */
	@ViewInject(R.id.et_mobile)
	private ClearEditText mEt_mobile;
	/** 验证码输入框 */
	@ViewInject(R.id.et_code)
	private ClearEditText mEt_code;
	/** 推荐人手机号 */
	@ViewInject(R.id.et_reference)
	private ClearEditText mEt_reference;
	/** 发送验证码按钮 */
	@ViewInject(R.id.btn_send_code)
	private SDSendValidateButton mBtn_send_code;

	@ViewInject(R.id.tv_unlogin_buy)
	private TextView mTvUnLoginBuy;

	private ShopCartAdapter mAdapter;
	private Cart_indexActModel mActModel;

	private String mStrMobile;
	private String mStrCode;
	private String mStrReference;

	@Override
	protected View onCreateContentView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState)
	{
		setmTitleType(TitleType.TITLE);
		return setContentView(R.layout.frag_shop_cart);
	}

	@Override
	protected void init()
	{
		initTitle();
		registeClick();
		initSDSendValidateButton();
		initPullToRefreshScrollView();
	}

	private void initPullToRefreshScrollView()
	{
		mPtrsvAll.setMode(Mode.PULL_FROM_START);
		mPtrsvAll.setOnRefreshListener(new OnRefreshListener2<ScrollView>()
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
		mPtrsvAll.setRefreshing();
	}

	/**
	 * 初始化发送验证码按钮
	 */
	private void initSDSendValidateButton()
	{
		mBtn_send_code.setmListener(new SDSendValidateButtonListener()
		{
			@Override
			public void onTick()
			{
			}

			@Override
			public void onClickSendValidateButton()
			{
				requestSendCode();
			}
		});
	}

	/**
	 * 请求验证码
	 */
	protected void requestSendCode()
	{
		mStrMobile = mEt_mobile.getText().toString();
		if (TextUtils.isEmpty(mStrMobile))
		{
			SDToast.showToast("请输入手机号码");
			mEt_mobile.requestFocus();
			return;
		}
		CommonInterface.requestValidateCode(mStrMobile, 0, new SDRequestCallBack<Sms_send_sms_codeActModel>()
		{

			@Override
			public void onSuccess(ResponseInfo<String> responseInfo)
			{
				if (actModel.getStatus() > 0)
				{
					mBtn_send_code.stopTickWork();
					mBtn_send_code.setmDisableTime(actModel.getLesstime());
					mBtn_send_code.startTickWork();
				}
			}

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
			public void onFailure(HttpException error, String msg)
			{
			}
		});
	}

	private void registeClick()
	{
		mTvUnLoginBuy.setOnClickListener(this);
	}

	private void initTitle()
	{
		mTitle.setMiddleTextTop("购物车");

		if (getActivity() instanceof MainActivity)
		{
			mTitle.setLeftImageLeft(0);
		}

		mTitle.initRightItem(1);
		mTitle.getItemRight(0).setTextBot(null);
	}

	@Override
	public void onCLickRight_SDTitleSimple(SDTitleItem v, int index)
	{
		if (AppRuntimeWorker.isLogin())
		{
			if (mAdapter == null)
			{
				SDToast.showToast("购物车为空");
				return;
			}
			if (mAdapter.getCount() <= 0)
			{
				SDToast.showToast("购物车为空");
				return;
			}
			clickSettleAccounts();
		} else
		{
			startActivity(new Intent(getActivity(), LoginActivity.class));
		}
	}

	/**
	 * 设置标题栏右边按钮状态
	 * 
	 * @param isLogin
	 */
	private void initTitleRightButton()
	{
		SDTitleItem item = mTitle.getItemRight(0);
		if (!AppRuntimeWorker.isLogin())
		{
			item.setTextBot("登录");
		} else
		{
			if (mAdapter != null && mAdapter.getCount() > 0)
			{
				item.setTextBot("去结算");
			} else
			{
				item.setTextBot(null);
			}
		}
	}

	/**
	 * 去结算
	 */
	private void clickSettleAccounts()
	{
		if (mLlPhoneLogin.getVisibility() == View.VISIBLE)
		{
			if (TextUtils.isEmpty(mStrMobile))
			{
				SDToast.showToast("请输入手机号");
				return;
			}
			mStrCode = mEt_code.getText().toString();
			if (TextUtils.isEmpty(mStrCode))
			{
				SDToast.showToast("请输入验证码");
				return;
			}
			mStrReference = mEt_reference.getText().toString();
		}

		// TODO 去结算
		requestCheckCart();
	}

	private void requestCheckCart()
	{
		if (mActModel == null)
		{
			return;
		}

		RequestModel model = new RequestModel();
		model.putCtl("cart");
		model.putAct("check_cart");
		model.putUser();
		if (mAdapter != null)
		{
			model.put("num", mAdapter.getMapNumber());
		}
		model.put("mobile", mStrMobile);
		model.put("sms_verify", mStrCode);
		model.put("invite_mobile", mStrReference);
		SDRequestCallBack<Cart_check_cartActModel> handler = new SDRequestCallBack<Cart_check_cartActModel>()
		{

			@Override
			public void onStart()
			{
				SDDialogManager.showProgressDialog("请稍候");
			}

			@Override
			public void onSuccess(ResponseInfo<String> responseInfo)
			{
				if (actModel.getStatus() == 1)
				{
					CommonInterface.updateCartNumber();
					User_infoModel model = actModel.getUser_data();
					if (model != null)
					{
						LocalUserModel.dealLoginSuccess(model, false);
					}
					initTitleRightButton();
					SDViewUtil.hide(mLlPhoneLogin);
					// TODO 跳到确认订单界面
					startConfirmOrderActivity();
				}
			}

			@Override
			public void onFinish()
			{
				SDDialogManager.dismissProgressDialog();
			}
		};
		InterfaceServer.getInstance().requestInterface(model, handler);
	}

	private void startConfirmOrderActivity()
	{
		if (mAdapter != null && mAdapter.getCount() > 0)
		{
			Intent intent = new Intent(getActivity(), ConfirmOrderActivity.class);
			startActivity(intent);
		} else
		{
			requestData();
		}
	}

	private void requestData()
	{
		CommonInterface.requestCart(new SDRequestCallBack<Cart_indexActModel>()
		{

			@Override
			public void onSuccess(ResponseInfo<String> responseInfo)
			{
				if (actModel.getStatus() == 1)
				{
					mActModel = actModel;
					bindData();
				}
			}

			@Override
			public void onStart()
			{
			}

			@Override
			public void onFinish()
			{
				mPtrsvAll.onRefreshComplete();
			}

			@Override
			public void onFailure(HttpException error, String msg)
			{

			}
		});
	}

	protected void bindData()
	{
		if (mActModel == null)
		{
			return;
		}

		ShopcartRecommendFragment fragRecommend = new ShopcartRecommendFragment();
		fragRecommend.setmActModel(mActModel);
		getSDFragmentManager().replace(R.id.frag_shop_cart_fl_recommend, fragRecommend);

		boolean isScore = false;
		if (mActModel.getIs_score() == 1)
		{
			isScore = true;
		}

		List<CartGoodsModel> listModel = mActModel.getListCartGoods();
		SDViewUtil.toggleEmptyMsgByList(listModel, mRlEmpty);
		mAdapter = new ShopCartAdapter(listModel, getActivity(), isScore);
		mLvCartGoods.setAdapter(mAdapter);
		changeViewState();
		initTitleRightButton();
	}

	private void changeViewState()
	{
		if (mActModel == null)
		{
			return;
		}

		if (mActModel.getHas_mobile() == 1)
		{
			SDViewUtil.hide(mLlPhoneLogin);
		} else
		{
			SDViewUtil.show(mLlPhoneLogin);
			changeUnloginBuyButton();
		}
	}

	private void changeUnloginBuyButton()
	{
		if (AppRuntimeWorker.isLogin())
		{
			mTvUnLoginBuy.setText("绑定手机号码");
			SDViewUtil.hide(mEt_reference);
		} else
		{
			mTvUnLoginBuy.setText("手机登录并结算");
			int registerRebate = AppRuntimeWorker.getRegister_rebate();
			switch (registerRebate)
			{
			case 1:
			case 2:
				SDViewUtil.show(mEt_reference);
				break;

			default:
				SDViewUtil.hide(mEt_reference);
				break;
			}
		}
	}

	@Override
	public void onResume()
	{
		initTitleRightButton();
		requestData();
		super.onResume();
	}

	@Override
	public void onHiddenChanged(boolean hidden)
	{
		if (!hidden)
		{
			initTitleRightButton();
			requestData();
		}
		super.onHiddenChanged(hidden);
	}

	@Override
	public void onClick(View v)
	{
		switch (v.getId())
		{
		case R.id.tv_unlogin_buy:
			clickSettleAccounts();
			break;

		default:
			break;
		}

	}

	@Override
	protected void onNeedRefreshOnResume()
	{
		requestData();
		super.onNeedRefreshOnResume();
	}

	@Override
	public void onEventMainThread(SDBaseEvent event)
	{
		super.onEventMainThread(event);
		switch (EnumEventTag.valueOf(event.getTagInt()))
		{
		case DONE_CART_SUCCESS:
			mAdapter.updateData(null);
			setmIsNeedRefreshOnResume(true);
			break;

		case ADD_CART_SUCCESS:
			setmIsNeedRefreshOnResume(true);
			break;

		case DELETE_CART_GOODS_SUCCESS:
			requestData();
			break;
		case LOGIN_SUCCESS:
			initTitleRightButton();
			setmIsNeedRefreshOnResume(true);
			break;

		case LOGOUT:
			initTitleRightButton();
			changeUnloginBuyButton();
			break;
		case CONFIRM_IMAGE_CODE:
			if (SDActivityManager.getInstance().isLastActivity(getActivity()))
			{
				requestSendCode();
			}
			break;
		default:
			break;
		}
	}

	@Override
	public void onDestroy()
	{
		mBtn_send_code.stopTickWork();
		super.onDestroy();
	}

}