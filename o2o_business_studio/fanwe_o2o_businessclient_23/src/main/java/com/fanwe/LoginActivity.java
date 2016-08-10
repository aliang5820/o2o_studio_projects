package com.fanwe;

import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;

import com.fanwe.application.App;
import com.fanwe.application.SysConfig;
import com.fanwe.businessclient.R;
import com.fanwe.config.AppConfig;
import com.fanwe.http.InterfaceServer;
import com.fanwe.http.listener.SDRequestCallBack;
import com.fanwe.library.customview.ClearEditText;
import com.fanwe.library.utils.SDViewBinder;
import com.fanwe.model.AccountInfoModel;
import com.fanwe.model.BizUserCtlDoLoginActModel;
import com.fanwe.model.LocalUserModel;
import com.fanwe.model.RequestModel;
import com.fanwe.service.AppUpgradeService;
import com.fanwe.umeng.UmengPushManager;
import com.fanwe.utils.SDDialogUtil;
import com.fanwe.utils.SDInterfaceUtil;
import com.fanwe.utils.SDToast;

/**
 * 登入
 */
public class LoginActivity extends TitleBaseActivity implements OnClickListener {
    private ClearEditText mEt_username;
    private ClearEditText mEt_password;

    private Button mBtn_login;

    private String mBiz_email;
    private String mBiz_pwd;

    @Override
    protected void onCreate(Bundle arg0) {
        super.onCreate(arg0);
        setContentView(R.layout.act_login);
        init();
    }

    private void init() {
        register();
        initTitle();
        startUpgradeService();
        SysConfig.getInstance().setCheckUpdate(true);
    }

    private void register() {
        mEt_username = (ClearEditText) findViewById(R.id.act_login_et_username);
        mEt_password = (ClearEditText) findViewById(R.id.act_login_et_password);

        mBtn_login = (Button) findViewById(R.id.act_login_btn_login);
        mBtn_login.setOnClickListener(this);
        // 再次登入显示保存账号
        mEt_username.setText(AppConfig.getUserName());
    }

    private void initTitle() {
        SDViewBinder.setTextView(mTitle, "登录");
    }

    private void startUpgradeService() {
        Intent updateIntent = new Intent(this, AppUpgradeService.class);
        startService(updateIntent);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.act_login_btn_login:
                loginclick();
                break;
        }
    }

    private void loginclick() {
        if (verifyReqeustParams()) {
            requestLoginInterface();
        }
    }

    private boolean verifyReqeustParams() {
        mBiz_email = mEt_username.getText().toString();
        mBiz_pwd = mEt_password.getText().toString();

        if (TextUtils.isEmpty(mBiz_email) || TextUtils.isEmpty(mBiz_pwd)) {
            SDToast.showToast("账号或密码不能为空");
            return false;
        }
        return true;
    }

    private void requestLoginInterface() {
        RequestModel model = new RequestModel();
        model.putCtlAct("biz_user", "dologin");
        model.put("account_name", mBiz_email);
        model.put("account_password", mBiz_pwd);
        model.put("device_token", UmengPushManager.getPushAgent().getRegistrationId());

        InterfaceServer.getInstance().requestInterface(model, new SDRequestCallBack<BizUserCtlDoLoginActModel>() {
            private Dialog nDialog;

            @Override
            public void onFinish() {
                if (nDialog != null) {
                    nDialog.dismiss();
                }
            }

            @Override
            public void onSuccess(BizUserCtlDoLoginActModel actModel) {
                if (!SDInterfaceUtil.dealactModel(actModel, null)) {
                    switch (actModel.getStatus()) {
                        case 0:
                            break;
                        case 1:
                            if (actModel.getAccount_info() != null) {
                                dealLoginSuccess(actModel.getAccount_info());
                            }
                            break;
                    }
                }

            }

            @Override
            public void onStart() {
                nDialog = SDDialogUtil.showLoading("登录中...");
            }
        });
    }

    private void dealLoginSuccess(AccountInfoModel accountInfoModel) {
        LocalUserModel user = new LocalUserModel();
        user.setUser_id(accountInfoModel.getAccount_id());
        user.setSupplier_id(accountInfoModel.getSupplier_id());
        user.setAccount_name(accountInfoModel.getAccount_name());
        user.setAccount_password(accountInfoModel.getAccount_password());
        user.setQr_code(accountInfoModel.getQr_code());
        App.getApp().setmLocalUser(user);

        // 保存账号
        AppConfig.setUserName(accountInfoModel.getAccount_name());
        //登录成功进入主页之前，需要判断是否已经申请加盟
        startActivity(new Intent(mActivity, MainActivity.class));
        finish();
    }

    public void onRegister(View view) {
        startActivity(new Intent(mActivity, RegisterActivity.class));
    }
}
