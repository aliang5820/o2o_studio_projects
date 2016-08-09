package com.fanwe;

import android.app.Activity;
import android.app.Dialog;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.alibaba.fastjson.JSON;
import com.fanwe.businessclient.R;
import com.fanwe.common.CommonInterfaceRequestModel;
import com.fanwe.event.EnumEventTag;
import com.fanwe.http.InterfaceServer;
import com.fanwe.http.listener.SDRequestCallBack;
import com.fanwe.library.common.SDActivityManager;
import com.fanwe.library.customview.SDSendValidateButton;
import com.fanwe.library.customview.SDSendValidateButton.SDSendValidateButtonListener;
import com.fanwe.library.dialog.SDDialogManager;
import com.fanwe.library.utils.SDViewBinder;
import com.fanwe.model.Biz_withdrawalCtl_submit_formActModel;
import com.fanwe.model.RequestModel;
import com.fanwe.model.Sms_send_sms_codeActModel;
import com.fanwe.utils.SDDialogUtil;
import com.fanwe.utils.SDInterfaceUtil;
import com.fanwe.utils.SDToast;
import com.lidroid.xutils.http.ResponseInfo;
import com.sunday.eventbus.SDBaseEvent;

/***
 * 申请提现
 */
public class MyWithdrawalsActivity extends TitleBaseActivity implements OnClickListener {
    private TextView mTv_money;
    private SDSendValidateButton mSDSendValidateButton;
    private TextView mTv_apply;
    private EditText mEt_money, mEt_code, mEt_pwd;
    private LinearLayout mLl_code;

    private String mSmsVerify;// 验证码
    private String mMoney;// 金额
    private String mPwd_verify;// 密码
    private Biz_withdrawalCtl_submit_formActModel mActModel;

    @Override
    protected void onCreate(Bundle arg0) {
        super.onCreate(arg0);
        setContentView(R.layout.act_mywithdrawals);
        init();
    }

    private void init() {
        register();
        initTitle();
        initSendValidateButton();
        requestCtlBizWithdrawalActSubmitForm();
    }

    private void initTitle() {
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
                if (mActModel != null) {
                    if (!TextUtils.isEmpty(mActModel.getMobile())) {
                        requestSend_register_codeInterface();
                    }
                }
            }
        });

    }

    private void requestSend_register_codeInterface() {
        String mStrMobile = mActModel.getMobile();

        CommonInterfaceRequestModel.requestValidateCode(mStrMobile, 0, new SDRequestCallBack<Sms_send_sms_codeActModel>() {

            @Override
            public void onSuccess(ResponseInfo<String> responseInfo) {
                switch (actModel.getStatus()) {
                    case -1:
                        break;
                    case 1:
                        mSDSendValidateButton.setmDisableTime(JSON.parseObject(responseInfo.result).getIntValue("lesstime"));
                        mSDSendValidateButton.startTickWork();
                        break;
                    default:
                        break;
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
        });

    }

    private void requestCtlBizWithdrawalActSubmitForm() {
        RequestModel model = new RequestModel();
        model.putCtlAct("biz_withdrawal", "submit_form");
        SDRequestCallBack<Biz_withdrawalCtl_submit_formActModel> handler = new SDRequestCallBack<Biz_withdrawalCtl_submit_formActModel>() {
            private Dialog nDialog;

            @Override
            public void onStart() {
                nDialog = SDDialogUtil.showLoading("加载中");
            }

            @Override
            public void onFinish() {
                if (nDialog != null) {
                    nDialog.dismiss();
                }
            }

            @Override
            public void onSuccess(Biz_withdrawalCtl_submit_formActModel actModel) {
                if (!SDInterfaceUtil.dealactModel(actModel, MyWithdrawalsActivity.this)) {
                    switch (actModel.getStatus()) {
                        case 0:
                            break;
                        case 1:
                            addViewInfo(actModel);
                            break;
                        default:
                            break;
                    }
                }

            }

        };
        InterfaceServer.getInstance().requestInterface(model, handler);

    }

    protected void addViewInfo(Biz_withdrawalCtl_submit_formActModel actModel) {
        mActModel = actModel;
        if (actModel.getSms_on() == 1) {
            mLl_code.setVisibility(View.VISIBLE);
            mEt_pwd.setVisibility(View.GONE);
        } else if (actModel.getSms_on() == 0) {
            mLl_code.setVisibility(View.GONE);
            mEt_pwd.setVisibility(View.VISIBLE);
        }

        SDViewBinder.setTextView(mTv_money, actModel.getSupplier_info().getMoney());
        SDViewBinder.setTextView(mTitle, actModel.getSupplier_info().getName());
    }

    private boolean verifyDoSubmitRequestParams() {
        mSmsVerify = mEt_code.getText().toString();
        mMoney = mEt_money.getText().toString();
        mPwd_verify = mEt_pwd.getText().toString();

        if (TextUtils.isEmpty(mMoney)) {
            SDToast.showToast("亲!请输入金额!");
            return false;
        }
        if (mActModel != null) {
            if (mActModel.getSms_on() == 1) {
                if (TextUtils.isEmpty(mSmsVerify)) {
                    SDToast.showToast("亲!请输入验证码!");
                    return false;
                }
            } else {
                if (TextUtils.isEmpty(mPwd_verify)) {
                    SDToast.showToast("亲!请输入登录密码!");
                    return false;
                }
            }
        }
        return true;

    }

    private void requestDoSubmitAct() {
        RequestModel model = new RequestModel();
        model.putCtlAct("biz_withdrawal", "do_submit");
        model.put("money", mMoney);

        if (mActModel.getSms_on() == 1) {
            model.put("sms_verify", mSmsVerify);
        } else {
            model.put("pwd_verify", mPwd_verify);
        }

        SDRequestCallBack<Biz_withdrawalCtl_submit_formActModel> handler = new SDRequestCallBack<Biz_withdrawalCtl_submit_formActModel>() {
            private Dialog nDialog;

            @Override
            public void onFinish() {
                if (nDialog != null) {
                    nDialog.dismiss();
                }
            }

            @Override
            public void onSuccess(Biz_withdrawalCtl_submit_formActModel actModel) {
                if (!SDInterfaceUtil.dealactModel(actModel, MyWithdrawalsActivity.this)) {

                    switch (actModel.getStatus()) {
                        case 0:
                            SDToast.showToast(actModel.getInfo());
                            break;
                        case 1:
                            SDToast.showToast(actModel.getInfo());
                            setResult(Activity.RESULT_OK);
                            finish();
                            break;
                        default:
                            break;
                    }

                }

            }

            @Override
            public void onStart() {
                nDialog = SDDialogUtil.showLoading("加载中...");
            }

        };
        InterfaceServer.getInstance().requestInterface(model, handler);

    }

    private void clickApply() {
        if (verifyDoSubmitRequestParams()) {
            requestDoSubmitAct();
        }
    }

    private void register() {
        mSDSendValidateButton = (SDSendValidateButton) findViewById(R.id.btn_act_mywithdrawals_send);

        mEt_money = (EditText) findViewById(R.id.et_act_mywithdrawals_money);
        mEt_code = (EditText) findViewById(R.id.et_act_mywithdrawals_code);
        mEt_pwd = (EditText) findViewById(R.id.et_act_mywithdrawals_pwd);
        mEt_pwd.setVisibility(View.GONE);

        mLl_code = (LinearLayout) findViewById(R.id.ll_act_mywithdrawals_code);
        mLl_code.setVisibility(View.GONE);
        mTv_money = (TextView) findViewById(R.id.tv_act_mywithdrawals_money);

        mTv_apply = (TextView) findViewById(R.id.tv_act_mywithdrawals_apply);
        mTv_apply.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        // TODO Auto-generated method stub
        switch (v.getId()) {
            case R.id.tv_act_mywithdrawals_apply:
                clickApply();
                break;
        }
    }

    @Override
    public void onEventMainThread(SDBaseEvent event) {
        super.onEventMainThread(event);
        switch (EnumEventTag.valueOf(event.getTagInt())) {
            case CONFIRM_IMAGE_CODE:
                if (SDActivityManager.getInstance().isLastActivity(this)) {
                    requestSend_register_codeInterface();
                }
                break;
            default:
                break;
        }
    }

}
