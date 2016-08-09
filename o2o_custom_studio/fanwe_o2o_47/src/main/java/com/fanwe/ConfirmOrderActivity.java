package com.fanwe;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.ScrollView;

import com.fanwe.common.CommonInterface;
import com.fanwe.constant.Constant.TitleType;
import com.fanwe.event.EnumEventTag;
import com.fanwe.fragment.OrderDetailAccountPaymentFragment;
import com.fanwe.fragment.OrderDetailAccountPaymentFragment.OrderDetailAccountPaymentFragmentListener;
import com.fanwe.fragment.OrderDetailFeeFragment;
import com.fanwe.fragment.OrderDetailGoodsFragment;
import com.fanwe.fragment.OrderDetailParamsFragment;
import com.fanwe.fragment.OrderDetailParamsFragment.OrderDetailParamsFragmentListener;
import com.fanwe.fragment.OrderDetailPaymentsFragment;
import com.fanwe.fragment.OrderDetailPaymentsFragment.OrderDetailPaymentsFragmentListener;
import com.fanwe.http.InterfaceServer;
import com.fanwe.http.SDRequestCallBack;
import com.fanwe.library.dialog.SDDialogManager;
import com.fanwe.library.utils.SDTypeParseUtil;
import com.fanwe.model.Cart_checkActModel;
import com.fanwe.model.Cart_count_buy_totalModel;
import com.fanwe.model.Cart_doneActModel;
import com.fanwe.model.Payment_listModel;
import com.fanwe.model.RequestModel;
import com.fanwe.o2o.newo2o.R;
import com.handmark.pulltorefresh.library.PullToRefreshBase;
import com.handmark.pulltorefresh.library.PullToRefreshBase.Mode;
import com.handmark.pulltorefresh.library.PullToRefreshBase.OnRefreshListener2;
import com.handmark.pulltorefresh.library.PullToRefreshScrollView;
import com.lidroid.xutils.exception.HttpException;
import com.lidroid.xutils.http.ResponseInfo;
import com.sunday.eventbus.SDBaseEvent;
import com.sunday.eventbus.SDEventManager;

/**
 * 确认订单
 *
 * @author js02
 */
public class ConfirmOrderActivity extends BaseActivity {
    public static final int REQUEST_CODE_DELIVERY_ADDRESS = 1;

    protected PullToRefreshScrollView mPtrsvAll;

    protected Button mBtnConfirmOrder;

    protected Cart_checkActModel mCheckActModel;

    // -------------------------fragments
    protected OrderDetailGoodsFragment mFragGoods;
    protected OrderDetailParamsFragment mFragParams;
    protected OrderDetailPaymentsFragment mFragPayments;
    protected OrderDetailAccountPaymentFragment mFragAccountPayment;
    protected OrderDetailFeeFragment mFragFees;

    // =======

    public OrderDetailGoodsFragment getOrderDetailGoodsFragment() {
        return mFragGoods;
    }

    public OrderDetailParamsFragment getOrderDetailParamsFragment() {
        return mFragParams;
    }

    public OrderDetailPaymentsFragment getOrderDetailPaymentsFragment() {
        return mFragPayments;
    }

    public OrderDetailAccountPaymentFragment getOrderDetailAccountPaymentFragment() {
        return mFragAccountPayment;
    }

    public OrderDetailFeeFragment getOrderDetailFeeFragment() {
        return mFragFees;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setmTitleType(TitleType.TITLE);
        setContentView(R.layout.act_confirm_order);
        init();
    }

    protected void init() {
        findViews();
        initTitle();
        registeClick();
        addFragments();
        initPullToRefreshScrollView();
    }

    private void findViews() {
        mPtrsvAll = (PullToRefreshScrollView) findViewById(R.id.act_confirm_order_ptrsv_all);
        mBtnConfirmOrder = (Button) findViewById(R.id.act_confirm_order_btn_confirm_order);
    }

    private void addFragments() {
        // 绑定商品数据
        mFragGoods = new OrderDetailGoodsFragment();
        getSDFragmentManager().replace(R.id.act_confirm_order_fl_goods, mFragGoods);

        // 订单参数
        mFragParams = new OrderDetailParamsFragment();
        mFragParams.setmListener(new OrderDetailParamsFragmentListener() {
            @Override
            public void onCalculate() {
                requestCalculate();
            }
        });
        getSDFragmentManager().replace(R.id.act_confirm_order_fl_params, mFragParams);

        // 支付方式列表
        mFragPayments = new OrderDetailPaymentsFragment();
        mFragPayments.setmListener(new OrderDetailPaymentsFragmentListener() {
            @Override
            public void onPaymentChange(Payment_listModel model) {
                requestCalculate();
            }
        });
        getSDFragmentManager().replace(R.id.act_confirm_order_fl_payments, mFragPayments);

        // 余额支付
        mFragAccountPayment = new OrderDetailAccountPaymentFragment();
        mFragAccountPayment.setmListener(new OrderDetailAccountPaymentFragmentListener() {
            @Override
            public void onPaymentChange(boolean isSelected) {
                requestCalculate();
            }
        });
        getSDFragmentManager().replace(R.id.act_confirm_order_fl_account_payments, mFragAccountPayment);

        // 费用信息
        mFragFees = new OrderDetailFeeFragment();
        getSDFragmentManager().replace(R.id.act_confirm_order_fl_fees, mFragFees);

    }

    private void initPullToRefreshScrollView() {
        mPtrsvAll.setMode(Mode.PULL_FROM_START);
        mPtrsvAll.setOnRefreshListener(new OnRefreshListener2<ScrollView>() {

            @Override
            public void onPullDownToRefresh(PullToRefreshBase<ScrollView> refreshView) {
                requestData();
            }

            @Override
            public void onPullUpToRefresh(PullToRefreshBase<ScrollView> refreshView) {
            }

        });
        mPtrsvAll.setRefreshing();
    }

    /**
     * 获取数据
     */
    protected void requestData() {
        RequestModel model = new RequestModel();
        model.putCtl("cart");
        model.putAct("check");
        model.putUser();

        SDRequestCallBack<Cart_checkActModel> handler = new SDRequestCallBack<Cart_checkActModel>() {

            @Override
            public void onStart() {
                SDDialogManager.showProgressDialog("正在加载");
            }

            @Override
            public void onSuccess(ResponseInfo<String> responseInfo) {
                dealRequestDataSuccess(actModel);
            }

            @Override
            public void onFailure(HttpException error, String msg) {

            }

            @Override
            public void onFinish() {
                SDDialogManager.dismissProgressDialog();
                mPtrsvAll.onRefreshComplete();
            }
        };
        InterfaceServer.getInstance().requestInterface(model, handler);

    }

    protected void dealRequestDataSuccess(Cart_checkActModel actModel) {
        Intent intent = null;
        switch (actModel.getStatus()) {
            case -1:
                intent = new Intent(mActivity, LoginActivity.class);
                startActivity(intent);
                break;
            case 1:
                mCheckActModel = actModel;
                bindData();
                requestCalculate();
                break;

            default:
                break;
        }
    }

    protected void bindData() {
        if (mCheckActModel == null) {
            return;
        }

        // 绑定商品数据
        mFragGoods.setmCheckActModel(mCheckActModel);

        // 订单参数
        mFragParams.setmCheckActModel(mCheckActModel);

        // 支付方式列表
        mFragPayments.setmCheckActModel(mCheckActModel);

        // 余额支付
        mFragAccountPayment.setmCheckActModel(mCheckActModel);
    }

    protected void fillCalculateParams(RequestModel model) {
        if (model != null) {
            if (mFragParams != null) {
                model.put("delivery_id", mFragParams.getDelivery_id());
                model.put("ecvsn", mFragParams.getEcv_sn());
            }
            if (mFragPayments != null) {
                model.put("payment", mFragPayments.getPaymentId());
            }
            if (mFragAccountPayment != null) {
                model.put("all_account_money", mFragAccountPayment.getUseAccountMoney());
            }
        }
    }

    /**
     * 计算价格
     */
    protected void requestCalculate() {
        RequestModel model = new RequestModel();
        model.putCtl("cart");
        model.putAct("count_buy_total");
        model.putUser();
        fillCalculateParams(model);
        SDRequestCallBack<Cart_count_buy_totalModel> handler = new SDRequestCallBack<Cart_count_buy_totalModel>() {

            @Override
            public void onStart() {
                SDDialogManager.showProgressDialog("正在加载");
            }

            @Override
            public void onSuccess(ResponseInfo<String> responseInfo) {
                // TODO 绑定所需费用信息
                dealRequestCalculateSuccess(actModel);
            }

            @Override
            public void onFailure(HttpException error, String msg) {

            }

            @Override
            public void onFinish() {
                SDDialogManager.dismissProgressDialog();
            }
        };
        InterfaceServer.getInstance().requestInterface(model, handler);
    }

    protected void dealRequestCalculateSuccess(Cart_count_buy_totalModel actModel) {
        Intent intent = null;
        switch (actModel.getStatus()) {
            case -1:
                intent = new Intent(mActivity, LoginActivity.class);
                startActivity(intent);
                break;
            case 1:
                double payPrice = SDTypeParseUtil.getDouble(actModel.getPay_price());
                if (payPrice == 0) {
                    // 余额够支付
                    if (mFragPayments != null) {
                        mFragPayments.clearSelectedPayment(false);
                        mFragPayments.setAccountMoneyEnough(true);
                    }
                } else {
                    if (mFragPayments != null) {
                        mFragPayments.setAccountMoneyEnough(false);
                    }
                }

                mFragFees.setListFeeinfo(actModel.getFeeinfo());

                // 把计算的实体赋给第一次请求数据的实体用于更新界面数据
                mCheckActModel.setCalculateModel(actModel);

                // TODO 更新各商家运费
                if (mFragGoods != null) {
                    mFragGoods.setmCheckActModel(mCheckActModel);
                }
                if (mFragParams != null) {
                    mFragParams.setmCheckActModel(mCheckActModel);
                }

                break;

            default:
                break;
        }
    }

    protected void fillDoneOrderParams(RequestModel model) {
        if (model != null) {
            if (mFragParams != null) {
                model.put("delivery_id", mFragParams.getDelivery_id());
                model.put("ecvsn", mFragParams.getEcv_sn());
                model.put("content", mFragParams.getContent());
            }
            if (mFragPayments != null) {
                model.put("payment", mFragPayments.getPaymentId());
            }
            if (mFragAccountPayment != null) {
                model.put("all_account_money", mFragAccountPayment.getUseAccountMoney());
            }
        }
    }

    /**
     * 确认订单
     */
    protected void requestDoneOrder() {
        RequestModel model = new RequestModel();
        model.putCtl("cart");
        model.putAct("done");
        model.putUser();
        fillDoneOrderParams(model);

        SDRequestCallBack<Cart_doneActModel> handler = new SDRequestCallBack<Cart_doneActModel>() {

            @Override
            public void onStart() {
                SDDialogManager.showProgressDialog("正在加载");
            }

            @Override
            public void onSuccess(ResponseInfo<String> responseInfo) {
                SDDialogManager.dismissProgressDialog();
                dealRequestDoneOrderSuccess(actModel);
            }

            @Override
            public void onFailure(HttpException error, String msg) {

            }

            @Override
            public void onFinish() {

            }
        };
        InterfaceServer.getInstance().requestInterface(model, handler);

    }

    protected void dealRequestDoneOrderSuccess(Cart_doneActModel actModel) {
        Intent intent = null;
        switch (actModel.getStatus()) {
            case -1:
                intent = new Intent(mActivity, LoginActivity.class);
                startActivity(intent);
                break;
            case 1:
                CommonInterface.updateCartNumber();
                SDEventManager.post(EnumEventTag.DONE_CART_SUCCESS.ordinal());
                intent = new Intent(mActivity, PayDoneActivity.class);
                intent.putExtra(PayDoneActivity.EXTRA_ORDER_ID, actModel.getOrder_id());
                startActivity(intent);
                finish();
                break;

            default:
                break;
        }
    }

    private void registeClick() {
        mBtnConfirmOrder.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                requestDoneOrder();
            }
        });

    }

    protected void initTitle() {
        mTitle.setMiddleTextTop("确认订单");
    }

    @Override
    public void onEventMainThread(SDBaseEvent event) {
        super.onEventMainThread(event);
        switch (EnumEventTag.valueOf(event.getTagInt())) {
            case USER_DELIVERY_CHANGE:
                setmIsNeedRefreshOnResume(true);
                break;

            default:
                break;
        }
    }

    @Override
    protected void onNeedRefreshOnResume() {
        requestData();
        super.onNeedRefreshOnResume();
    }

}