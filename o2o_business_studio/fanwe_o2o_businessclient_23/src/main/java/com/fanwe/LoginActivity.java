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
import com.fanwe.constant.Constant;
import com.fanwe.http.InterfaceServer;
import com.fanwe.http.listener.SDRequestCallBack;
import com.fanwe.library.customview.ClearEditText;
import com.fanwe.library.dialog.SDDialogManager;
import com.fanwe.library.utils.SDViewBinder;
import com.fanwe.model.AccountInfoModel;
import com.fanwe.model.ApplyInfoModel;
import com.fanwe.model.ApplyResultModel;
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

    public void onRegister(View view) {
        startActivity(new Intent(mActivity, RegisterActivity.class));
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
                            if (actModel.getCheck_info() != null) {
                                ApplyResultModel applyResultModel = actModel.getCheck_info();
                                if(applyResultModel.getIs_pay() == 0) {
                                    //未支付入住费用,再次编辑
                                    requestInfo(applyResultModel);
                                } else {
                                    Intent intent = new Intent(mActivity, ApplyResultActivity.class);
                                    intent.putExtra(Constant.ExtraConstant.EXTRA_MODEL, applyResultModel);
                                    startActivity(intent);
                                }
                            }
                            break;
                        case 1:
                            if(actModel.getAccount_info() != null) {
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
        user.setSubmit_id(accountInfoModel.getSubmit_id());
        user.setSupplier_id(accountInfoModel.getSupplier_id());
        user.setAccount_name(accountInfoModel.getAccount_name());
        user.setAccount_password(accountInfoModel.getAccount_password());
        user.setAccount_type(accountInfoModel.getAccount_type());
        user.setQr_code(accountInfoModel.getQr_code());
        user.setIs_new(accountInfoModel.getIs_new());
        App.getApp().setmLocalUser(user);

        // 保存账号
        AppConfig.setUserName(accountInfoModel.getAccount_name());
        if (accountInfoModel.getIs_new() == 0) {
            //登录成功进入主页之前，需要判断是否已经申请加盟
            startActivity(new Intent(mActivity, MainActivity.class));
            finish();
        } else {
            //申请类别选择
            startActivity(new Intent(mActivity, ApplyTypeActivity.class));
            finish();
        }
    }

    //没有支付的账户，需要再次编辑，获取数据
    private void requestInfo(final ApplyResultModel resultModel) {
        RequestModel model = new RequestModel();
        model.putCtlAct("biz_member", "get_supplier_info");
        model.put("supplier_id", resultModel.getSubmit_id());//商户的id

        InterfaceServer.getInstance().requestInterface(model, new SDRequestCallBack<ApplyInfoModel>() {

            @Override
            public void onFinish() {
                SDDialogManager.dismissProgressDialog();
            }

            @Override
            public void onSuccess(ApplyInfoModel actModel) {
                SDDialogManager.dismissProgressDialog();
                actModel.setSubmit_id(resultModel.getSubmit_id());
                if (!SDInterfaceUtil.dealactModel(actModel, null) && actModel.getStatus() == 1) {
                    //0 普通 ，1 会员店 ，2 商户合伙人，3个人合伙人
                    Intent intent;
                    switch (resultModel.getType()) {
                        case 1:
                            intent = new Intent(mActivity, ApplyHYDActivity.class);
                            intent.putExtra(Constant.ExtraConstant.EXTRA_OTHER_MODEL, actModel);
                            intent.putExtra(Constant.ExtraConstant.EXTRA_TYPE, Constant.Apply.EDIT_HYD);
                            startActivity(intent);
                            break;
                        case 2:
                            intent = new Intent(mActivity, ApplyHHRActivity.class);
                            intent.putExtra(Constant.ExtraConstant.EXTRA_OTHER_MODEL, actModel);
                            intent.putExtra(Constant.ExtraConstant.EXTRA_TYPE, Constant.Apply.EDIT_COMPANY_HHR);
                            startActivity(intent);
                            break;
                        case 3:
                            intent = new Intent(mActivity, ApplyHHRActivity.class);
                            intent.putExtra(Constant.ExtraConstant.EXTRA_OTHER_MODEL, actModel);
                            intent.putExtra(Constant.ExtraConstant.EXTRA_TYPE, Constant.Apply.EDIT_PERSON_HHR);
                            startActivity(intent);
                            break;
                    }
                }
            }

            @Override
            public void onStart() {
                SDDialogManager.showProgressDialog("正在获取数据，请稍候...");
            }
        });
    }

    /*TODO
    * 彩蛋，删除账户
    * */
    public void deleteAccount(View view) {
        RequestModel model = new RequestModel();
        model.putCtlAct("biz_member", "delete_supplier");
        model.put("account_name", "15982034811");

        InterfaceServer.getInstance().requestInterface(model, new SDRequestCallBack<String>() {

            @Override
            public void onFinish() {

            }

            @Override
            public void onSuccess(String actModel) {

            }

            @Override
            public void onStart() {

            }
        });
    }
}
