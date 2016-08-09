package com.fanwe.http;

import android.app.Activity;
import android.content.Intent;
import android.text.TextUtils;

import com.alibaba.fastjson.JSONException;
import com.fanwe.LoginActivity;
import com.fanwe.app.App;
import com.fanwe.config.AppConfig;
import com.fanwe.constant.ApkConstant;
import com.fanwe.constant.Constant.UserLoginState;
import com.fanwe.event.EnumEventTag;
import com.fanwe.library.common.SDActivityManager;
import com.fanwe.library.utils.AESUtil;
import com.fanwe.library.utils.LogUtil;
import com.fanwe.library.utils.SDBase64;
import com.fanwe.library.utils.SDToast;
import com.fanwe.model.BaseActModel;
import com.fanwe.model.RequestModel;
import com.fanwe.model.RequestModel.ResponseDataType;
import com.fanwe.utils.JsonUtil;
import com.lidroid.xutils.exception.HttpException;
import com.lidroid.xutils.http.ResponseInfo;
import com.lidroid.xutils.http.callback.RequestCallBack;
import com.lidroid.xutils.util.LogUtils;
import com.sunday.eventbus.SDEventManager;

import org.apache.http.conn.ConnectTimeoutException;

import java.net.ConnectException;
import java.net.SocketException;
import java.net.SocketTimeoutException;
import java.net.UnknownHostException;

public class RequestCallBackProxy extends RequestCallBack<String> {
    private static final String STRING_NULL = "\":null";
    private static final String STRING_FALSE = "\":false";
    private static final String STRING_EMPTY_ARRAY = "\":[]";

    private RequestCallBack<String> mOriginalCallBack;
    private RequestModel mRequestModel;
    private BaseActModel mBaseActModel;

    public RequestCallBackProxy(RequestCallBack<String> originalCallBack, RequestModel model) {
        this.mOriginalCallBack = originalCallBack;
        this.mRequestModel = model;
    }

    @Override
    public void onStart() {
        if (mOriginalCallBack != null) {
            mOriginalCallBack.onStart();
        }
    }

    @Override
    public void onLoading(long total, long current, boolean isUploading) {
        if (mOriginalCallBack != null) {
            mOriginalCallBack.onLoading(total, current, isUploading);
        }
    }

    private void beforeOnSuccessBack(ResponseInfo<String> responseInfo) {
        String content = responseInfo.result;
        if (ApkConstant.DEBUG) {
            LogUtils.e(getCtl() + "," + getAct() + ":" + content);
        }

        if (!TextUtils.isEmpty(content)) {
            switch (mRequestModel.getResponseDataType()) {
                case ResponseDataType.BASE64:
                    content = SDBase64.decodeToString(content);
                    break;
                case ResponseDataType.JSON:

                    break;
                case ResponseDataType.AES:
                    content = AESUtil.decrypt(content, ApkConstant.KEY_AES);
                    break;

                default:
                    break;
            }

            // 替换false为null
            if (content.contains(STRING_FALSE)) {
                content = content.replace(STRING_FALSE, STRING_NULL);
            }
            // 替换[]为null
            if (content.contains(STRING_EMPTY_ARRAY)) {
                content = content.replace(STRING_EMPTY_ARRAY, STRING_NULL);
            }

            mBaseActModel = JsonUtil.json2Object(content, BaseActModel.class);
            if (mBaseActModel != null) {
                // 保存session
                String session = mBaseActModel.getSess_id();
                if (!TextUtils.isEmpty(session)) {
                    AppConfig.setSessionId(session);
                }

                // 保存refId
                String refId = mBaseActModel.getRef_uid();
                AppConfig.setRefId(refId);
            }
        }
        responseInfo.result = content;
    }

    private String getAct() {
        String act = null;
        if (ApkConstant.DEBUG && mRequestModel != null) {
            act = String.valueOf(mRequestModel.get("act"));
        }
        return act;
    }

    private String getCtl() {
        String ctl = null;
        if (ApkConstant.DEBUG && mRequestModel != null) {
            ctl = String.valueOf(mRequestModel.get("ctl"));
        }
        return ctl;
    }

    @Override
    public void onSuccessBack(ResponseInfo<String> responseInfo) {
        if (ApkConstant.DEBUG) {
            beforeOnSuccessBack(responseInfo);
            if (mOriginalCallBack != null) {
                mOriginalCallBack.onSuccessBack(responseInfo);
            }
        } else {
            try {
                beforeOnSuccessBack(responseInfo);
                if (mOriginalCallBack != null) {
                    mOriginalCallBack.onSuccessBack(responseInfo);
                }
            } catch (Exception e) {
                showErrorTip(e);
            }
        }
    }

    @Override
    public void onSuccess(ResponseInfo<String> responseInfo) {
        if (beforeOnSuccess(responseInfo)) {

        } else {
            if (ApkConstant.DEBUG) {
                if (mOriginalCallBack != null) {
                    mOriginalCallBack.onSuccess(responseInfo);
                }
            } else {
                try {
                    if (mOriginalCallBack != null) {
                        mOriginalCallBack.onSuccess(responseInfo);
                    }
                } catch (Exception e) {
                    showErrorTip(e);
                }
            }
        }
    }

    /**
     * @param responseInfo
     * @return true:回调不继续执行，false:回调继续执行
     */
    private boolean beforeOnSuccess(ResponseInfo<String> responseInfo) {
        if (checkLoginState()) {
            return true;
        }
        return false;
    }

    private boolean checkLoginState() {
        boolean result = false;
        if (mRequestModel != null) {
            if (mRequestModel.isNeedCheckLoginState()) {
                if (mBaseActModel != null) {
                    int loginStatus = mBaseActModel.getUser_login_status();
                    switch (loginStatus) {
                        case UserLoginState.LOGIN:

                            break;
                        case UserLoginState.UN_LOGIN:
                            App.getApplication().clearAppsLocalUserModel();
                            SDEventManager.post(EnumEventTag.UN_LOGIN.ordinal());
                            startLoginActivity();
                            result = true;
                            break;
                        case UserLoginState.TEMP_LOGIN:
                            SDEventManager.post(EnumEventTag.TEMP_LOGIN.ordinal());
                            startLoginActivity();
                            result = true;
                            break;
                        default:
                            break;
                    }
                }
            }
        }
        return result;
    }

    private void startLoginActivity() {
        Activity lastActivity = SDActivityManager.getInstance().getLastActivity();
        if (lastActivity != null) {
            Intent intent = new Intent(lastActivity, LoginActivity.class);
            lastActivity.startActivity(intent);
        }
    }

    private void beforeOnFailure(HttpException exception, String content) {
        if (exception == null) {
            if (!TextUtils.isEmpty(content)) {
                showErrorToast("错误:" + content);
            } else {
                showErrorToast("未知错误,请求失败.");
            }
        } else {
            showErrorTip(exception.getCause());
        }
    }

    private void showErrorToast(String text) {
        if (mRequestModel != null && mRequestModel.isNeedShowErrorTip()) {
            SDToast.showToast(text);
        }
    }

    private void showErrorTip(Throwable error) {
        if (error != null) {
            if (error instanceof JSONException) {
                showErrorToast("错误:" + "数据解析异常!");
            } else if (error instanceof UnknownHostException) {
                showErrorToast("错误:" + "无法访问的服务器地址!");
            } else if (error instanceof ConnectException) {
                showErrorToast("错误:" + "连接服务器失败!");
            } else if (error instanceof SocketTimeoutException || error instanceof ConnectTimeoutException) {
                showErrorToast("错误:" + "连接超时!");
            } else if (error instanceof SocketException) {
                showErrorToast("错误:" + "连接服务器失败!");
            } else {
                showErrorToast("未知错误,请求失败!");
            }
            LogUtil.e("ctl:" + getCtl() + ",act:" + getAct() + ",error:" + error.toString());
        } else {
            showErrorToast("未知错误,请求失败!");
        }
    }

    @Override
    public void onFailure(HttpException error, String msg) {
        beforeOnFailure(error, msg);
        if (mOriginalCallBack != null) {
            mOriginalCallBack.onFailure(error, msg);
        }
    }

    @Override
    public void onFinish() {
        if (mOriginalCallBack != null) {
            mOriginalCallBack.onFinish();
        }
    }

    @Override
    public void onCancelled() {
        if (mOriginalCallBack != null) {
            mOriginalCallBack.onCancelled();
        }
    }

}
