package com.fanwe;

import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;

import com.fanwe.application.App;
import com.fanwe.businessclient.R;
import com.fanwe.http.InterfaceServer;
import com.fanwe.http.listener.SDRequestCallBack;
import com.fanwe.library.customview.ClearEditText;
import com.fanwe.library.dialog.SDDialogManager;
import com.fanwe.model.BizUserCtlDoLoginActModel;
import com.fanwe.model.RequestModel;
import com.fanwe.utils.SDToast;
import com.lidroid.xutils.exception.HttpException;
import com.lidroid.xutils.http.ResponseInfo;
import com.lidroid.xutils.view.annotation.ViewInject;

/**
 * Created by Edison on 2016/8/19.
 * 修改密码
 */
public class ModifyPasswordActivity extends TitleBaseActivity implements View.OnClickListener {

    @ViewInject(R.id.et_old_password)
    private ClearEditText mEt_od_pwd;

    @ViewInject(R.id.et_pwd)
    private ClearEditText mEt_pwd;

    @ViewInject(R.id.et_pwd_confirm)
    private ClearEditText mEt_pwd_confirm;

    private String mStrOldPwd;
    private String mStrPwd;
    private String mStrPwdConfirm;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.act_modify_pd);
        init();
    }

    private void init() {
        initTitle();
    }

    private void initTitle() {
        mTitle.setText("修改密码");
    }

    private boolean validateParams() {
        mStrOldPwd = mEt_od_pwd.getText().toString();
        mStrPwd = mEt_pwd.getText().toString().trim();
        mStrPwdConfirm = mEt_pwd_confirm.getText().toString().trim();
        if (TextUtils.isEmpty(mStrOldPwd)) {
            com.fanwe.library.utils.SDToast.showToast("请输入旧密码!");
            return false;
        }

        if (mStrPwd.length() < 6) {
            com.fanwe.library.utils.SDToast.showToast("密码不可少于6位，请重新设置");
            return false;
        }
        if (!TextUtils.equals(mStrPwd, mStrPwdConfirm)) {
            com.fanwe.library.utils.SDToast.showToast("两次密码输入不一致，请重新输入");
            return false;
        }
        return true;
    }

    /**
     * 注册接口
     */
    public void onModify(View view) {
        if (validateParams()) {
            RequestModel model = new RequestModel();
            model.putCtlAct("biz_user", "update_password");
            model.put("account_password", mStrOldPwd);
            model.put("password ", mStrPwd);
            model.put("account_id ", App.getApp().getmLocalUser().getUser_id());
            InterfaceServer.getInstance().requestInterface(model, new SDRequestCallBack<BizUserCtlDoLoginActModel>() {

                @Override
                public void onStart() {
                    SDDialogManager.showProgressDialog("请稍候...");
                }

                @Override
                public void onSuccess(ResponseInfo<String> responseInfo) {
                    if (actModel.getStatus() == 1) {
                        SDDialogManager.dismissProgressDialog();
                        SDToast.showToast("修改密码成功");
                        finish();
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
    }
}