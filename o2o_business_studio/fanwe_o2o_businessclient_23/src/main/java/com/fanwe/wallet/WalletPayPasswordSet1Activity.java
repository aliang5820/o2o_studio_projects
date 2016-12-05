package com.fanwe.wallet;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import com.fanwe.BaseActivity;
import com.fanwe.businessclient.R;
import com.fanwe.constant.Constant;
import com.fanwe.dao.UserModelDao;
import com.fanwe.http.InterfaceServer;
import com.fanwe.http.listener.SDRequestCallBack;
import com.fanwe.library.customview.SDSendValidateButton;
import com.fanwe.library.dialog.SDDialogManager;
import com.fanwe.library.utils.SDToast;
import com.fanwe.model.LocalUserModel;
import com.fanwe.model.RequestModel;
import com.fanwe.model.Sms_send_sms_codeActModel;
import com.lidroid.xutils.exception.HttpException;
import com.lidroid.xutils.http.ResponseInfo;
import com.lidroid.xutils.view.annotation.ViewInject;

/**
 * Created by Edison on 2016/11/11.
 */

public class WalletPayPasswordSet1Activity extends BaseActivity {

    @ViewInject(R.id.et_mobile)
    private EditText mEt_mobile;

    @ViewInject(R.id.et_code)
    private EditText mEt_code;

    @ViewInject(R.id.btn_send_code)
    private SDSendValidateButton mBtn_send_code;

    @ViewInject(R.id.tv_submit)
    private TextView mTv_submit;

    private String mStrMobile;
    private String mStrCode;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setmTitleType(Constant.TitleType.TITLE);
        setContentView(R.layout.act_set_pay_password1);
        initTitle();
        initData();
    }

    private void initTitle() {
        mTitle.setMiddleTextTop("设置支付密码");
        mTv_submit.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        super.onClick(v);
        switch (v.getId()) {
            case R.id.tv_submit:
                //下一步
                checkSMSCode();
                break;
        }
    }

    private void initData() {
        //将用户手机号填上
        LocalUserModel localUserModel = UserModelDao.getModel();
        mEt_mobile.setText(localUserModel.getAccount_name());

        initSDSendValidateButton();
    }

    private void initSDSendValidateButton() {
        mBtn_send_code.setmListener(new SDSendValidateButton.SDSendValidateButtonListener() {
            @Override
            public void onTick() {
            }

            @Override
            public void onClickSendValidateButton() {
                requestSendCode();
            }
        });

    }

    /**
     * 发送验证码
     */
    protected void requestSendCode() {
        mStrMobile = mEt_mobile.getText().toString();
        if (TextUtils.isEmpty(mStrMobile)) {
            SDToast.showToast("请输入手机号码");
            return;
        }

        LocalUserModel localUserModel = UserModelDao.getModel();
        RequestModel requestModel = new RequestModel();
        requestModel.putCtl("biz_user");
        requestModel.putAct("send_app_sms");
        requestModel.put("account_mobile", mStrMobile);
        requestModel.put("user_type", localUserModel.getAccount_type());

        InterfaceServer.getInstance().requestInterface(requestModel, new SDRequestCallBack<Sms_send_sms_codeActModel>() {

            @Override
            public void onSuccess(ResponseInfo<String> responseInfo) {
                if (actModel.getStatus() == 1) {
                    //发送成功
                    mBtn_send_code.setmDisableTime(60);
                    mBtn_send_code.startTickWork();
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

    //验证手机验证码是否正确
    protected void checkSMSCode() {
        mStrCode = mEt_code.getText().toString();
        if (TextUtils.isEmpty(mStrCode)) {
            SDToast.showToast("请输入手机验证码");
            return;
        }

        LocalUserModel userModel = UserModelDao.getModel();
        RequestModel model = new RequestModel();
        model.putCtl("biz_ucmoney");
        model.putAct("jump_pay_password");
        model.put("account_mobile", mStrMobile);
        model.put("code", mStrCode);
        model.put("user_id", userModel.getSupplier_id());
        model.put("user_type", userModel.getAccount_type());

        InterfaceServer.getInstance().requestInterface(model, new SDRequestCallBack<Sms_send_sms_codeActModel>() {

            @Override
            public void onSuccess(ResponseInfo<String> responseInfo) {
                if (actModel.getStatus() == 1) {
                    //验证成功
                    Intent intent = new Intent(getApplicationContext(), WalletPayPasswordSet2Activity.class);
                    startActivity(intent);
                    finish();
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

}
