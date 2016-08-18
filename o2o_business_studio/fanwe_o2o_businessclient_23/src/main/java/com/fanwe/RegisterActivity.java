package com.fanwe;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;

import com.fanwe.application.App;
import com.fanwe.businessclient.R;
import com.fanwe.config.AppConfig;
import com.fanwe.http.InterfaceServer;
import com.fanwe.http.listener.SDRequestCallBack;
import com.fanwe.library.customview.ClearEditText;
import com.fanwe.library.customview.SDSendValidateButton;
import com.fanwe.library.dialog.SDDialogManager;
import com.fanwe.library.title.SDTitleItem;
import com.fanwe.library.utils.SDToast;
import com.fanwe.model.BaseCtlActModel;
import com.fanwe.model.BizUserCtlDoLoginActModel;
import com.fanwe.model.LocalUserModel;
import com.fanwe.model.RequestModel;
import com.fanwe.model.Sms_send_sms_codeActModel;
import com.lidroid.xutils.exception.HttpException;
import com.lidroid.xutils.http.ResponseInfo;
import com.lidroid.xutils.view.annotation.ViewInject;

/**
 * Created by Edison on 2016/8/9.
 * 注册页面
 */
public class RegisterActivity extends TitleBaseActivity implements View.OnClickListener {

    public static final String EXTRA_PHONE_NUMBER = "extra_phone_number";

    @ViewInject(R.id.et_mobile)
    private ClearEditText mEtMobile;

    @ViewInject(R.id.et_code)
    private ClearEditText mEtCode;

    @ViewInject(R.id.et_pwd)
    private ClearEditText mEt_pwd;

    @ViewInject(R.id.et_pwd_confirm)
    private ClearEditText mEt_pwd_confirm;

    @ViewInject(R.id.btn_send_code)
    private SDSendValidateButton mBtnSendCode;

    private String mStrMobile;
    private String mStrCode;
    private String mStrPwd;
    private String mStrPwdConfirm;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.act_register_mobile);
        init();
    }

    private void init() {
        initTitle();
        getIntentData();
        initSDSendValidateButton();
    }

    private void initTitle() {
        mTitle.setText("注册");
    }

    private void getIntentData() {
        String mobile = getIntent().getStringExtra(EXTRA_PHONE_NUMBER);
        if (!TextUtils.isEmpty(mobile)) {
            mEtMobile.setText(mobile);
        }
    }

    /**
     * 初始化发送验证码按钮
     */
    private void initSDSendValidateButton() {
        mBtnSendCode.setmListener(new SDSendValidateButton.SDSendValidateButtonListener() {
            @Override
            public void onTick() {
            }

            @Override
            public void onClickSendValidateButton() {
                requestSendCode();
            }
        });
    }

    public void onRegister(View view) {
        if (validateParams()) {
            requestRegister();
        }
    }

    private boolean validateParams() {
        mStrMobile = mEtMobile.getText().toString();
        mStrCode = mEtCode.getText().toString().trim();
        mStrPwd = mEt_pwd.getText().toString().trim();
        mStrPwdConfirm = mEt_pwd_confirm.getText().toString().trim();
        if (TextUtils.isEmpty(mStrMobile)) {
            SDToast.showToast("请输入手机号码!");
            return false;
        }
        if (mStrMobile.length() != 11) {
            SDToast.showToast("请输入11位的手机号码");
            return false;
        }

        if (TextUtils.isEmpty(mStrCode)) {
            SDToast.showToast("请输入验证码!");
            return false;
        }

        if (mStrPwd.length() < 6) {
            SDToast.showToast("密码不可少于6位，请重新设置");
            return false;
        }
        if (!TextUtils.equals(mStrPwd, mStrPwdConfirm)) {
            SDToast.showToast("两次密码输入不一致，请重新输入");
            return false;
        }
        return true;
    }

    /**
     * 验证码接口
     */
    private void requestSendCode() {
        mStrMobile = mEtMobile.getText().toString();
        if (TextUtils.isEmpty(mStrMobile)) {
            SDToast.showToast("请输入手机号码");
            mEtMobile.requestFocus();
            return;
        }

        RequestModel model = new RequestModel();
        model.putCtlAct("biz_user", "send_app_sms");
        model.put("account_mobile", mStrMobile);

        InterfaceServer.getInstance().requestInterface(model, new SDRequestCallBack<Sms_send_sms_codeActModel>() {

            @Override
            public void onSuccess(ResponseInfo<String> responseInfo) {
                if (actModel.getStatus() > 0) {
                    mBtnSendCode.setmDisableTime(actModel.getTime());
                    mBtnSendCode.startTickWork();
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

    /**
     * 注册接口
     */
    private void requestRegister() {
        RequestModel model = new RequestModel();
        model.putCtlAct("biz_user", "app_register");
        model.put("account_mobile", mStrMobile);
        model.put("code", mStrCode);
        model.put("account_password", mStrPwd);
        InterfaceServer.getInstance().requestInterface(model, new SDRequestCallBack<BizUserCtlDoLoginActModel>() {

            @Override
            public void onStart() {
                SDDialogManager.showProgressDialog("请稍候...");
            }

            @Override
            public void onSuccess(ResponseInfo<String> responseInfo) {
                if (actModel.getStatus() == 1) {
                    SDDialogManager.dismissProgressDialog();
                    dealRegisterSuccess(actModel);
                }
            }

            @Override
            public void onFailure(HttpException error, String msg) {

            }

            @Override
            public void onFinish() {
                SDDialogManager.dismissProgressDialog();
            }
        });
    }

    protected void dealRegisterSuccess(BizUserCtlDoLoginActModel actModel) {
        LocalUserModel user = new LocalUserModel();
        user.setUser_id(actModel.getAccount_info().getAccount_id());
        user.setSupplier_id(actModel.getAccount_info().getSupplier_id());
        user.setAccount_name(mStrMobile);
        user.setAccount_password(mStrPwd);
        App.getApp().setmLocalUser(user);

        // 保存账号
        AppConfig.setUserName(user.getAccount_name());
        // 注册成功进入主页之前，需要判断是否已经申请加盟
        //申请类别选择
        startActivity(new Intent(mActivity, ApplyTypeActivity.class));
        finish();
    }

    @Override
    public void onDestroy() {
        if (mBtnSendCode != null) {
            mBtnSendCode.stopTickWork();
        }
        super.onDestroy();
    }
}