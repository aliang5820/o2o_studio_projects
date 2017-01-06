package com.fanwe.wxapp;

import android.text.TextUtils;

import com.fanwe.library.common.SDActivityManager;
import com.tencent.mm.sdk.modelpay.PayReq;
import com.tencent.mm.sdk.openapi.IWXAPI;
import com.tencent.mm.sdk.openapi.WXAPIFactory;

public class SDWxappPay {

    private String mAppId;
    private boolean mIsRegister = false;

    public SDWxappPay(String wxKey) {
        setAppId(wxKey);
    }

    public String getAppId() {
        return this.mAppId;
    }

    public void setAppId(String appId) {
        this.mAppId = appId;
        register();
    }

    public void register() {
        if (!mIsRegister && !TextUtils.isEmpty(mAppId)) {
            mIsRegister = getWXAPI().registerApp(mAppId);
        }
    }

    public void pay(PayReq request) {
        if (request != null) {
            getWXAPI().sendReq(request);
        }
    }

    public IWXAPI getWXAPI() {
        return WXAPIFactory.createWXAPI(SDActivityManager.getInstance().getLastActivity(), mAppId);
    }

}
