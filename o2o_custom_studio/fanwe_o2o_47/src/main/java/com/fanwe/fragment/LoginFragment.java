package com.fanwe.fragment;

import android.os.Bundle;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.TextView;

import com.fanwe.config.AppConfig;
import com.fanwe.dao.LocalUserModelDao;
import com.fanwe.http.InterfaceServer;
import com.fanwe.http.SDRequestCallBack;
import com.fanwe.library.customview.ClearEditText;
import com.fanwe.library.dialog.SDDialogManager;
import com.fanwe.library.utils.SDToast;
import com.fanwe.model.LocalUserModel;
import com.fanwe.model.RequestModel;
import com.fanwe.model.User_infoModel;
import com.fanwe.o2o.newo2o.R;
import com.lidroid.xutils.exception.HttpException;
import com.lidroid.xutils.http.ResponseInfo;
import com.lidroid.xutils.view.annotation.ViewInject;

public class LoginFragment extends LoginBaseFragment {

    @ViewInject(R.id.act_login_et_email)
    private ClearEditText mEtEmail;

    @ViewInject(R.id.act_login_et_pwd)
    private ClearEditText mEtPwd;

    @ViewInject(R.id.act_login_tv_login)
    private TextView mTvLogin;

    private String mStrUserName;
    private String mStrPassword;

    @Override
    protected View onCreateContentView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return setContentView(R.layout.frag_login);
    }

    @Override
    protected void init() {
        registeClick();
        initViewState();
    }

    private void initViewState() {
        mEtEmail.setText(AppConfig.getUserName());
        validatePassword();
    }

    private void validatePassword() {
        LocalUserModel user = LocalUserModelDao.queryModel();
        if (user != null) {
            String userName = user.getUser_name();
            if (!TextUtils.isEmpty(userName)) {
                mEtEmail.setText(userName);
                mEtEmail.setEnabled(false);
                mEtPwd.setHint("为了保证账户安全，请重新验证密码");
            }
        }
    }

    private void registeClick() {
        mTvLogin.setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View v) {
                clickLoginNormal();
            }
        });
    }

    private void clickLoginNormal() {
        if (validateParam()) {
            RequestModel model = new RequestModel();
            model.putCtl("user");
            model.putAct("dologin");
            model.put("user_key", mStrUserName);
            model.put("user_pwd", mStrPassword);
            SDRequestCallBack<User_infoModel> handler = new SDRequestCallBack<User_infoModel>() {

                @Override
                public void onStart() {
                    SDDialogManager.showProgressDialog("请稍候...");
                }

                @Override
                public void onSuccess(ResponseInfo<String> responseInfo) {
                    SDDialogManager.dismissProgressDialog();
                    if (actModel.getStatus() == 1) {
                        AppConfig.setUserName(actModel.getUser_name());
                        LocalUserModel.dealLoginSuccess(actModel, true);
                        getActivity().finish();
                    }
                }

                @Override
                public void onFailure(HttpException error, String msg) {
                    SDDialogManager.dismissProgressDialog();
                }

                @Override
                public void onFinish() {
                }
            };
            InterfaceServer.getInstance().requestInterface(model, handler);
        }
    }

    private boolean validateParam() {
        mStrUserName = mEtEmail.getText().toString();
        if (TextUtils.isEmpty(mStrUserName)) {
            SDToast.showToast("用户邮箱不能为空");
            mEtEmail.requestFocus();
            return false;
        }
        mStrPassword = mEtPwd.getText().toString();
        if (TextUtils.isEmpty(mStrPassword)) {
            SDToast.showToast("密码不能为空");
            mEtPwd.requestFocus();
            return false;
        }
        return true;
    }
}