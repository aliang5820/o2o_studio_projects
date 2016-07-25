package com.fanwe;

import android.app.Activity;
import android.app.Dialog;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.LinearLayout;

import com.alibaba.fastjson.JSON;
import com.fanwe.application.App;
import com.fanwe.businessclient.R;
import com.fanwe.http.InterfaceServer;
import com.fanwe.http.listener.SDRequestCallBack;
import com.fanwe.library.customview.ClearEditText;
import com.fanwe.library.customview.SDSendValidateButton;
import com.fanwe.library.customview.SDSendValidateButton.SDSendValidateButtonListener;
import com.fanwe.library.utils.SDViewBinder;
import com.fanwe.model.BaseCtlActModel;
import com.fanwe.model.Biz_withdrawal_bindbank_Model;
import com.fanwe.model.LocalUserModel;
import com.fanwe.model.RequestModel;
import com.fanwe.utils.SDDialogUtil;
import com.fanwe.utils.SDInterfaceUtil;
import com.fanwe.utils.SDToast;

/**
 * 绑定银行卡
 */
public class BindBankActivity extends TitleBaseActivity implements OnClickListener {
    private Biz_withdrawal_bindbank_Model mBiz_withdrawal_bindbank_Model;

    private ClearEditText mCet_bank_name;
    private ClearEditText mCet_bank_num;
    private ClearEditText mCet_bank_user;
    private ClearEditText mCet_mobile;
    private ClearEditText mCet_sms_verify;
    private ClearEditText mCet_pwd_verify;

    private LinearLayout mLl_mobile;
    private LinearLayout mLl_sms_verify;
    private LinearLayout mLl_pwd_verify;

    private Button mBtn_bindbank;

    private SDSendValidateButton mSDSendValidateButton;

    private String mBank_name;
    private String mBank_num;
    private String mBank_user;
    private String mSms_verify;
    private String mPwd_verify;

    @Override
    protected void onCreate(Bundle arg0) {
        super.onCreate(arg0);
        setContentView(R.layout.act_bindbank);
        init();
    }

    private void init() {
        register();
        initTitle();
        initSendValidateButton();
        request_bindbank();
    }

    private int mOldLength;

    private void register() {
        mCet_bank_name = (ClearEditText) findViewById(R.id.cet_bank_name);
        mCet_bank_num = (ClearEditText) findViewById(R.id.cet_bank_num);
        mCet_bank_num.addTextChangedListener(new TextWatcher() {

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                // TODO Auto-generated method stub

            }

            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                String text = mCet_bank_num.getText().toString();
                if (!TextUtils.isEmpty(text)) {

                    int et_num_length = text.length();
                    if (et_num_length > mOldLength) {
                        if (et_num_length == 4 || et_num_length == 9) {
                            text = text + " ";
                            mCet_bank_num.setText(text);
                            mCet_bank_num.setSelection(text.length());
                        }
                    } else {
                        if (et_num_length == 4 || et_num_length == 9) {
                            text = text.substring(0, text.length() - 1);
                            mCet_bank_num.setText(text);
                            mCet_bank_num.setSelection(text.length());
                        }
                    }
                    mOldLength = et_num_length;
                }

            }
        });
        mCet_bank_user = (ClearEditText) findViewById(R.id.cet_bank_user);
        mCet_mobile = (ClearEditText) findViewById(R.id.cet_mobile);
        mCet_sms_verify = (ClearEditText) findViewById(R.id.cet_sms_verify);
        mCet_pwd_verify = (ClearEditText) findViewById(R.id.cet_pwd_verify);

        mLl_mobile = (LinearLayout) findViewById(R.id.ll_mobile);
        mLl_mobile.setVisibility(View.GONE);
        mLl_sms_verify = (LinearLayout) findViewById(R.id.ll_sms_verify);
        mLl_sms_verify.setVisibility(View.GONE);
        mLl_pwd_verify = (LinearLayout) findViewById(R.id.ll_pwd_verify);
        mLl_pwd_verify.setVisibility(View.GONE);

        mSDSendValidateButton = (SDSendValidateButton) findViewById(R.id.btn_sendcode);

        mBtn_bindbank = (Button) findViewById(R.id.btn_bindbank);
        mBtn_bindbank.setOnClickListener(this);
    }

    /**
     * 初始化发送验证码按钮
     */
    private void initSendValidateButton() {
        mSDSendValidateButton.setmListener(new SDSendValidateButtonListener() {

            @Override
            public void onTick() {
            }

            @Override
            public void onClickSendValidateButton() {
                if (mBiz_withdrawal_bindbank_Model != null) {
                    if (!TextUtils.isEmpty(mBiz_withdrawal_bindbank_Model.getMobile())) {
                        requestSend_register_codeInterface();
                    }
                }
            }
        });

    }

    private void requestSend_register_codeInterface() {
        RequestModel model = new RequestModel();
        model.putCtlAct("sms", "send_sms_code");
        model.put("mobile", mBiz_withdrawal_bindbank_Model.getMobile());

        SDRequestCallBack<BaseCtlActModel> handler = new SDRequestCallBack<BaseCtlActModel>() {
            private Dialog nDialog;

            @Override
            public void onStart() {
                nDialog = SDDialogUtil.showLoading("");
            }

            @Override
            public void onFinish() {
                if (nDialog != null) {
                    nDialog.dismiss();
                }
            }

            @Override
            public void onSuccess(BaseCtlActModel actModel) {
                if (!SDInterfaceUtil.dealactModel(actModel, BindBankActivity.this)) {
                    SDToast.showToast(actModel.getInfo());
                    switch (actModel.getStatus()) {
                        case 0:
                            break;
                        case 1:
                            mSDSendValidateButton.setmDisableTime(JSON.parseObject(responseInfo.result).getIntValue("lesstime"));
                            mSDSendValidateButton.startTickWork();
                            break;
                        default:
                            break;
                    }

                }

            }

        };
        InterfaceServer.getInstance().requestInterface(model, handler);

    }

    private void initTitle() {
        mTitle.setText("绑定银行卡");
    }

    private void request_bindbank() {
        RequestModel model = new RequestModel();
        model.putCtlAct("biz_withdrawal", "bindbank");

        SDRequestCallBack<Biz_withdrawal_bindbank_Model> callback = new SDRequestCallBack<Biz_withdrawal_bindbank_Model>() {
            private Dialog nDialog;

            @Override
            public void onStart() {
                nDialog = SDDialogUtil.showLoading("加载中...");
            }

            @Override
            public void onFinish() {
                if (nDialog != null) {
                    nDialog.dismiss();
                }
            }

            @Override
            public void onSuccess(Biz_withdrawal_bindbank_Model actModel) {
                if (!SDInterfaceUtil.dealactModel(actModel, BindBankActivity.this)) {
                    switch (actModel.getStatus()) {
                        case 0:
                            finish();
                            break;
                        case 1:
                            bindViewInfo(actModel);
                            break;
                    }
                }
            }

        };

        InterfaceServer.getInstance().requestInterface(model, callback);
    }

    private void bindViewInfo(Biz_withdrawal_bindbank_Model actModel) {
        mBiz_withdrawal_bindbank_Model = actModel;
        if (actModel.getSms_on() == 1) {
            mLl_pwd_verify.setVisibility(View.GONE);
            mLl_mobile.setVisibility(View.VISIBLE);
            mLl_sms_verify.setVisibility(View.VISIBLE);
        } else {
            mLl_pwd_verify.setVisibility(View.VISIBLE);
            mLl_mobile.setVisibility(View.GONE);
            mLl_sms_verify.setVisibility(View.GONE);
        }

        SDViewBinder.setTextView(mCet_mobile, actModel.getMobile());
    }

    private boolean verifyParams() {
        mBank_name = mCet_bank_name.getText().toString();
        mBank_num = mCet_bank_num.getText().toString();
        mBank_user = mCet_bank_user.getText().toString();
        mSms_verify = mCet_sms_verify.getText().toString();
        mPwd_verify = mCet_pwd_verify.getText().toString();
        if (TextUtils.isEmpty(mBank_name)) {
            SDToast.showToast("亲!请输入开户行!");
            return false;
        }
        if (TextUtils.isEmpty(mBank_num)) {
            SDToast.showToast("亲!请输入卡号!");
            return false;
        }

        if (mBank_num.length() < 10) {
            SDToast.showToast("亲!请输入正确的卡号!");
            return false;
        }

        if (TextUtils.isEmpty(mBank_user)) {
            SDToast.showToast("亲!请输入持卡人!");
            return false;
        }
        if (mBiz_withdrawal_bindbank_Model.getSms_on() == 1) {
            if (TextUtils.isEmpty(mSms_verify)) {
                SDToast.showToast("亲!请输入验证码!");
                return false;
            }
        } else {
            if (TextUtils.isEmpty(mPwd_verify)) {
                SDToast.showToast("亲!请输入登录密码!");
                return false;
            }
        }
        return true;
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_bindbank:
                click_btn_bindbank();
                break;
        }
    }

    private void click_btn_bindbank() {
        if (verifyParams()) {
            request_do_bindbank();
        }
    }

    private void request_do_bindbank() {
        LocalUserModel localUserModel = App.getApp().getmLocalUser();
        if (localUserModel == null) {
            return;
        }

        RequestModel model = new RequestModel();
        model.putCtlAct("biz_withdrawal", "do_bindbank");
        model.put("bank_name", mBank_name);
        model.put("bank_num", mBank_num.trim());
        model.put("bank_user", mBank_user);

        if (mBiz_withdrawal_bindbank_Model.getSms_on() == 1) {
            model.put("sms_verify", mSms_verify);
        } else {
            model.put("pwd_verify", mPwd_verify);
        }

        SDRequestCallBack<BaseCtlActModel> callback = new SDRequestCallBack<BaseCtlActModel>() {
            private Dialog nDialog;

            @Override
            public void onStart() {
                nDialog = SDDialogUtil.showLoading("加载中...");
            }

            @Override
            public void onSuccess(BaseCtlActModel actModel) {
                if (!SDInterfaceUtil.dealactModel(actModel, BindBankActivity.this)) {
                    switch (actModel.getStatus()) {
                        case 0:
                            break;
                        case 1:
                            setResult(Activity.RESULT_OK);
                            finish();
                            break;
                    }
                }
            }

            @Override
            public void onFinish() {
                if (nDialog != null) {
                    nDialog.dismiss();
                }
            }

        };

        InterfaceServer.getInstance().requestInterface(model, callback);

    }
}
