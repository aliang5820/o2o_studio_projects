package com.fanwe;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;

import com.fanwe.app.App;
import com.fanwe.constant.Constant.PaymentType;
import com.fanwe.library.pay.alipay.PayResult;
import com.fanwe.library.pay.alipay.SDAlipayer;
import com.fanwe.library.pay.alipay.SDAlipayer.SDAlipayerListener;
import com.fanwe.library.utils.SDToast;
import com.fanwe.model.MalipayModel;
import com.fanwe.model.Payment_codeModel;
import com.fanwe.model.UpacpappModel;
import com.fanwe.model.WxappModel;
import com.fanwe.wxapp.SDWxappPay;
import com.handmark.pulltorefresh.library.PullToRefreshScrollView;
import com.tencent.mm.sdk.modelpay.PayReq;
import com.unionpay.UPPayAssistEx;

public abstract class AbstractPayDoneActivity extends BasePullToRefreshScrollViewActivity {
    /**
     * 00:正式，01:测试
     */
    private static final String UPACPAPP_MODE = "00";

    /**
     * 订单id (int)
     */
    public static final String EXTRA_ORDER_ID = "extra_order_id";

    protected int mOrderId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getIntentData();
    }

    private void getIntentData() {
        mOrderId = getIntent().getIntExtra(EXTRA_ORDER_ID, 0);
        if (mOrderId <= 0) {
            SDToast.showToast("id为空");
            return;
        }
    }

    @Override
    public void onPullDownToRefresh(PullToRefreshScrollView view) {
        requestData();
    }

    @Override
    protected void onResume() {
        requestData();
        super.onResume();
    }

    /**
     * 用于重写请求接口数据
     */
    protected void requestData() {

    }

    protected void startPay(Payment_codeModel model) {
        if (model == null) {
            SDToast.showToast("Payment_codeModel is null");
            return;
        }

        String payAction = model.getPay_action();
        String className = model.getClass_name();
        if (!TextUtils.isEmpty(payAction)) // wap
        {
            Intent intent = new Intent(App.getApplication(), AppWebViewActivity.class);
            intent.putExtra(AppWebViewActivity.EXTRA_URL, payAction);
            startActivity(intent);
            return;
        } else {
            if (PaymentType.MALIPAY.equals(className) || PaymentType.ALIAPP.equals(className)) // 支付宝sdk新
            {
                payMalipay(model.getMalipay());
            } else if (PaymentType.WXAPP.equals(className)) // 微信
            {
                payWxapp(model.getWxapp());
            } else if (PaymentType.UPACPAPP.equals(className)) // 银联支付
            {
                payUpacpapp(model.getUpacpapp());
            }
        }
    }

    /**
     * 银联支付
     */
    protected void payUpacpapp(UpacpappModel model) {
        if (model == null) {
            SDToast.showToast("获取银联支付参数失败");
            return;
        }

        String tn = model.getTn();
        if (TextUtils.isEmpty(tn)) {
            SDToast.showToast("tn 为空");
            return;
        }
        UPPayAssistEx.startPayByJAR(mActivity, com.unionpay.uppay.PayActivity.class, null, null, tn, UPACPAPP_MODE);
    }

    /**
     * 微信支付
     */
    protected void payWxapp(WxappModel model) {
        if (model == null) {
            SDToast.showToast("获取微信支付参数失败");
            return;
        }

        String appId = model.getAppid();
        if (TextUtils.isEmpty(appId)) {
            SDToast.showToast("appId为空");
            return;
        }

        String partnerId = model.getPartnerid();
        if (TextUtils.isEmpty(partnerId)) {
            SDToast.showToast("partnerId为空");
            return;
        }

        String prepayId = model.getPrepayid();
        if (TextUtils.isEmpty(prepayId)) {
            SDToast.showToast("prepayId为空");
            return;
        }

        String nonceStr = model.getNoncestr();
        if (TextUtils.isEmpty(nonceStr)) {
            SDToast.showToast("nonceStr为空");
            return;
        }

        String timeStamp = model.getTimestamp();
        if (TextUtils.isEmpty(timeStamp)) {
            SDToast.showToast("timeStamp为空");
            return;
        }

        String packageValue = model.getPackagevalue();
        if (TextUtils.isEmpty(packageValue)) {
            SDToast.showToast("packageValue为空");
            return;
        }

        String sign = model.getSign();
        if (TextUtils.isEmpty(sign)) {
            SDToast.showToast("sign为空");
            return;
        }

        SDWxappPay.getInstance().setAppId(appId);

        PayReq req = new PayReq();
        req.appId = appId;
        req.partnerId = partnerId;
        req.prepayId = prepayId;
        req.nonceStr = nonceStr;
        req.timeStamp = timeStamp;
        req.packageValue = packageValue;
        req.sign = sign;

        SDWxappPay.getInstance().pay(req);
    }

    /**
     * 支付宝sdk支付(新)
     */
    protected void payMalipay(MalipayModel model) {
        if (model == null) {
            SDToast.showToast("获取支付宝支付参数失败");
            return;
        }

        String orderSpec = model.getOrder_spec();
        String sign = model.getSign();
        String signType = model.getSign_type();

        if (TextUtils.isEmpty(orderSpec)) {
            SDToast.showToast("order_spec为空");
            return;
        }

        if (TextUtils.isEmpty(sign)) {
            SDToast.showToast("sign为空");
            return;
        }

        if (TextUtils.isEmpty(signType)) {
            SDToast.showToast("signType为空");
            return;
        }

        SDAlipayer payer = new SDAlipayer(mActivity);
        payer.setListener(new SDAlipayerListener() {

            @Override
            public void onResult(PayResult result) {
                String info = result.getMemo();
                String status = result.getResultStatus();

                if ("9000".equals(status)) // 支付成功
                {
                    SDToast.showToast("支付成功");
                } else if ("8000".equals(status)) // 支付结果确认中
                {
                    SDToast.showToast("支付结果确认中");
                } else {
                    SDToast.showToast(info);
                }
            }

            @Override
            public void onFailure(Exception e, String msg) {
                if (e != null) {
                    SDToast.showToast("错误:" + e.toString());
                } else {
                    if (!TextUtils.isEmpty(msg)) {
                        SDToast.showToast(msg);
                    }
                }
            }

        });
        payer.pay(orderSpec, sign, signType);
    }

}
