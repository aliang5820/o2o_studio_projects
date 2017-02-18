package com.fanwe;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.fanwe.application.App;
import com.fanwe.businessclient.R;
import com.fanwe.constant.Constant;
import com.fanwe.event.EnumEventTag;
import com.fanwe.http.InterfaceServer;
import com.fanwe.http.listener.SDRequestCallBack;
import com.fanwe.library.dialog.SDDialogManager;
import com.fanwe.library.utils.SDToast;
import com.fanwe.model.ApplyPayConfigModel;
import com.fanwe.model.ApplyPayModel;
import com.fanwe.model.ApplyPayModelCtlActModel;
import com.fanwe.model.RequestModel;
import com.fanwe.wxapp.SDWxappPay;
import com.lidroid.xutils.exception.HttpException;
import com.lidroid.xutils.view.annotation.ViewInject;
import com.sunday.eventbus.SDEventManager;
import com.tencent.mm.sdk.modelpay.PayReq;
import com.tencent.mm.sdk.openapi.IWXAPI;
import com.tencent.mm.sdk.openapi.WXAPIFactory;

/**
 * Created by Edison on 2016/7/28.
 * 申请支付
 */
public class ApplyPayActivity extends TitleBaseActivity {
    @ViewInject(R.id.pay_desc)
    private TextView pay_desc;

    @ViewInject(R.id.orderIdView)
    private TextView orderIdView;

    @ViewInject(R.id.pay_money)
    private TextView pay_money;

    @ViewInject(R.id.payRadioGroup)
    private RadioGroup payRadioGroup;

    private long orderId;
    private double price;
    private String submit_id;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.act_apply_pay);
        initView();
        initData();
    }

    private void initView() {
        mTitle.setText("订单支付");
    }

    private void initData() {
        orderId = getIntent().getLongExtra(Constant.ExtraConstant.EXTRA_ID, -1);
        String orderSn = getIntent().getStringExtra(Constant.ExtraConstant.EXTRA_MORE);
        submit_id = getIntent().getStringExtra(Constant.ExtraConstant.EXTRA_OTHER_ID);
        orderIdView.setText(getString(R.string.apply_order_id, orderSn));

        price = getIntent().getDoubleExtra(Constant.ExtraConstant.EXTRA_MODEL, -1);
        pay_money.setText("￥" + price);

        int applyType = getIntent().getIntExtra(Constant.ExtraConstant.EXTRA_TYPE, -1);
        if (applyType == Constant.Apply.HHR_QY) {
            pay_desc.setText("省点云购企业合伙人");
        } else if(applyType == Constant.Apply.HHR_GR) {
            pay_desc.setText("省点云购个人合伙人");
        }else {
            pay_desc.setText("省点云购会员店");
        }
    }

    /**
     * onActivityResult 获得支付结果，如果支付成功，服务器会收到ping++ 服务器发送的异步通知。
     * 最终支付成功根据异步通知为准
     *//*
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        //支付页面返回处理
        if (requestCode == Pingpp.REQUEST_CODE_PAYMENT) {
            if (resultCode == RESULT_OK) {
                String result = data.getExtras().getString("pay_result");
                *//* 处理返回值
                 * "success" - payment succeed
                 * "fail"    - payment failed
                 * "cancel"  - user canceld
                 * "invalid" - payment plugin not installed
                 *//*
                String errorMsg = data.getExtras().getString("error_msg"); // 错误信息
                String extraMsg = data.getExtras().getString("extra_msg"); // 错误信息
                showMsg(result, errorMsg, extraMsg);
            }
        }
    }*/

    //根据选择支付类型，获取订单，暂时只支持微信支付
    public void onPay(View view) {
        switch (payRadioGroup.getCheckedRadioButtonId()) {
            case R.id.wxPay:
                //微信支付
                requestOrder(Constant.Pay.CHANNEL_WECHAT);
                break;
            case R.id.aliPay:
                //支付宝支付
                requestOrder(Constant.Pay.CHANNEL_ALIPAY);
                break;
        }
        // TODO 目前默认支付成功，进入首页
        /*SDEventManager.post(EnumEventTag.EXIT_APP.ordinal());
        startActivity(new Intent(mActivity, MainActivity.class));*/
    }

    //显示支付信息
    public void showMsg(String result, String msg1, String msg2) {
        String str = result;
        if (null != msg1 && msg1.length() != 0) {
            str += "\n" + msg1;
        }
        if (null != msg2 && msg2.length() != 0) {
            str += "\n" + msg2;
        }
        AlertDialog.Builder builder = new AlertDialog.Builder(mActivity);
        builder.setMessage(str);
        builder.setTitle("提示");
        builder.setPositiveButton("确定", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                dialogInterface.dismiss();
                // 支付成功，进入首页
                startActivity(new Intent(mActivity, MainActivity.class));
                finish();
            }
        });
        builder.create().show();
    }

    /**
     * 请求订单接口
     */
    private void requestOrder(int payType) {
        RequestModel model = new RequestModel();
        model.putCtlAct("biz_member", "ApplyMemberOrderPay");
        model.put("supplier_id", submit_id);
        model.put("order_id", orderId);
        model.put("type", payType);

        InterfaceServer.getInstance().requestInterface(model, new SDRequestCallBack<ApplyPayModelCtlActModel>() {

            @Override
            public void onSuccess(ApplyPayModelCtlActModel actModel) {
                if (actModel.getStatus() > 0) {
                    //请求成功，进行 支付
                    ApplyPayConfigModel payModel = actModel.getPayment_code().getConfig();
                    SDWxappPay.mAppId = payModel.getAppid();
                    final IWXAPI msgApi = WXAPIFactory.createWXAPI(mActivity, SDWxappPay.mAppId, true);
                    // 将该app注册到微信
                    boolean isRegister = msgApi.registerApp(SDWxappPay.mAppId);
                    if(isRegister) {
                        //发起微信请求
                        PayReq req = new PayReq();
                        req.appId = payModel.getAppid();
                        req.partnerId = payModel.getPartnerid();
                        req.prepayId = payModel.getPrepayid();
                        req.nonceStr = payModel.getNoncestr();
                        req.timeStamp = payModel.getTimestamp();
                        req.packageValue = payModel.getPackagevalue();
                        req.sign = payModel.getSign();
                        msgApi.sendReq(req);
                    } else {
                        SDToast.showToast("微信支付注册失败");
                    }
                }
            }

            @Override
            public void onStart() {
                SDDialogManager.showProgressDialog("请稍候...");
            }

            @Override
            public void onFinish() {
                SDDialogManager.dismissProgressDialog();
            }

            @Override
            public void onFailure(HttpException error, String msg) {
            }
        });
    }
}