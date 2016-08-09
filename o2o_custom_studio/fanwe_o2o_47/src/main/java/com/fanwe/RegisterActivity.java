package com.fanwe;

import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.TextView;

import com.fanwe.config.AppConfig;
import com.fanwe.constant.Constant.TitleType;
import com.fanwe.http.InterfaceServer;
import com.fanwe.http.SDRequestCallBack;
import com.fanwe.library.customview.ClearEditText;
import com.fanwe.library.dialog.SDDialogManager;
import com.fanwe.library.utils.SDToast;
import com.fanwe.model.LocalUserModel;
import com.fanwe.model.RequestModel;
import com.fanwe.model.User_infoModel;
import com.fanwe.o2o.newo2o.R;
import com.lidroid.xutils.http.ResponseInfo;
import com.lidroid.xutils.view.annotation.ViewInject;

/**
 * 注册界面
 *
 * @author Administrator
 */
public class RegisterActivity extends BaseActivity {

    @ViewInject(R.id.et_email)
    private ClearEditText mEtEmail;

    @ViewInject(R.id.et_username)
    private ClearEditText mEtUsername;

    @ViewInject(R.id.et_pwd)
    private ClearEditText mEtPwd;

    @ViewInject(R.id.et_pwd_confirm)
    private ClearEditText mEtPwdConfirm;

    @ViewInject(R.id.et_reference)
    private ClearEditText mEt_reference;

    @ViewInject(R.id.tv_register)
    private TextView mTvRegister;

    private String mStrEmail;
    private String mStrUsername;
    private String mStrPwd;
    private String mStrPwdConfirm;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setmTitleType(TitleType.TITLE);
        setContentView(R.layout.act_register);
        init();
    }

    private void init() {
        initTitle();
        registeClick();
        initViewState();
    }

    private void initViewState() {

    }

    private void initTitle() {
        mTitle.setMiddleTextTop("注册");
    }

    private void registeClick() {
        mTvRegister.setOnClickListener(this);

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.tv_register:
                clickRegister();
                break;

            default:
                break;
        }
    }

    private void clickRegister() {
        if (validateParam()) {
            RequestModel model = new RequestModel();
            model.putCtl("user");
            model.putAct("doregister");
            model.put("user_email", mStrEmail);
            model.put("user_name", mStrUsername);
            model.put("user_pwd", mStrPwd);
            SDRequestCallBack<User_infoModel> handler = new SDRequestCallBack<User_infoModel>() {

                @Override
                public void onStart() {
                    SDDialogManager.showProgressDialog("请稍候...");
                }

                @Override
                public void onSuccess(ResponseInfo<String> responseInfo) {
                    if (actModel.getStatus() == 1) {
                        LocalUserModel.dealLoginSuccess(actModel, true);
                        AppConfig.setUserName(actModel.getUser_name());
                        finish();
                    }
                }

                @Override
                public void onFinish() {
                    SDDialogManager.dismissProgressDialog();
                }
            };
            InterfaceServer.getInstance().requestInterface(model, handler);
        }

    }

    private boolean validateParam() {
        mStrEmail = mEtEmail.getText().toString();
        if (TextUtils.isEmpty(mStrEmail)) {
            SDToast.showToast("邮箱不能为空");
            return false;
        }
        mStrUsername = mEtUsername.getText().toString();
        if (TextUtils.isEmpty(mStrUsername)) {
            SDToast.showToast("昵称不能为空");
            return false;
        }
        mStrPwd = mEtPwd.getText().toString();
        if (TextUtils.isEmpty(mStrPwd)) {
            SDToast.showToast("密码不能为空");
            return false;
        }
        mStrPwdConfirm = mEtPwdConfirm.getText().toString();
        if (TextUtils.isEmpty(mStrPwdConfirm)) {
            SDToast.showToast("确认密码不能为空");
            return false;
        }

        if (!mStrPwd.equals(mStrPwdConfirm)) {
            SDToast.showToast("两次密码不一致");
            return false;
        }

        return true;
    }

}