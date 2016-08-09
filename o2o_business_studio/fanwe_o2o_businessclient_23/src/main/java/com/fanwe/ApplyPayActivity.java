package com.fanwe;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.RadioGroup;
import android.widget.TextView;

import com.fanwe.businessclient.R;
import com.fanwe.constant.Constant;
import com.fanwe.http.InterfaceServer;
import com.fanwe.http.listener.SDRequestCallBack;
import com.fanwe.library.dialog.SDDialogManager;
import com.fanwe.library.utils.SDToast;
import com.fanwe.model.RequestModel;
import com.fanwe.model.Sms_send_sms_codeActModel;
import com.lidroid.xutils.exception.HttpException;
import com.lidroid.xutils.http.ResponseInfo;
import com.pingplusplus.android.Pingpp;

/**
 * Created by Edison on 2016/7/28.
 * 申请支付
 */
public class ApplyPayActivity extends TitleBaseActivity {
    private TextView pay_desc;
    private RadioGroup payRadioGroup;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.act_apply_pay);
        initView();
        initData();
    }

    private void initView() {
        mTitle.setText("订单支付");
        pay_desc = (TextView) findViewById(R.id.pay_desc);
        payRadioGroup = (RadioGroup) findViewById(R.id.payRadioGroup);
    }

    private void initData() {
        int applyType = getIntent().getIntExtra(Constant.ExtraConstant.EXTRA_TYPE, -1);
        if (applyType == Constant.Apply.HHR) {
            pay_desc.setText("省点网合伙人");
        } else {
            pay_desc.setText("省点网会员店");
        }
    }

    /**
     * onActivityResult 获得支付结果，如果支付成功，服务器会收到ping++ 服务器发送的异步通知。
     * 最终支付成功根据异步通知为准
     */
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        //支付页面返回处理
        if (requestCode == Pingpp.REQUEST_CODE_PAYMENT) {
            if (resultCode == RESULT_OK) {
                String result = data.getExtras().getString("pay_result");
                /* 处理返回值
                 * "success" - payment succeed
                 * "fail"    - payment failed
                 * "cancel"  - user canceld
                 * "invalid" - payment plugin not installed
                 */
                String errorMsg = data.getExtras().getString("error_msg"); // 错误信息
                String extraMsg = data.getExtras().getString("extra_msg"); // 错误信息
                showMsg(result, errorMsg, extraMsg);
            }
        }
    }

    //根据选择支付类型，获取订单
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
    private void requestOrder(String payType) {
        RequestModel model = new RequestModel();
        model.putCtlAct("pay", "orderPay");
        model.put("account_mobile", "");

        InterfaceServer.getInstance().requestInterface(model, new SDRequestCallBack<Sms_send_sms_codeActModel>() {

            @Override
            public void onSuccess(ResponseInfo<String> responseInfo) {
                if (actModel.getStatus() > 0) {
                    //请求成功，进行 支付
                    Pingpp.createPayment(mActivity, responseInfo.result);
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

    class PaymentRequest {
        String channel;
        int amount;

        public PaymentRequest(String channel, int amount) {
            this.channel = channel;
            this.amount = amount;
        }
    }
}