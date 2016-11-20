package com.fanwe;

import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.EditText;
import android.widget.TextView;

import com.fanwe.common.CommonInterface;
import com.fanwe.constant.Constant.TitleType;
import com.fanwe.dao.LocalUserModelDao;
import com.fanwe.event.EnumEventTag;
import com.fanwe.http.SDRequestCallBack;
import com.fanwe.library.common.SDActivityManager;
import com.fanwe.library.customview.SDSendValidateButton;
import com.fanwe.library.customview.SDSendValidateButton.SDSendValidateButtonListener;
import com.fanwe.library.dialog.SDDialogManager;
import com.fanwe.library.utils.SDToast;
import com.fanwe.model.Sms_send_sms_codeActModel;
import com.fanwe.model.User_infoModel;
import com.fanwe.o2o.newo2o.R;
import com.lidroid.xutils.exception.HttpException;
import com.lidroid.xutils.http.ResponseInfo;
import com.lidroid.xutils.view.annotation.ViewInject;
import com.sunday.eventbus.SDBaseEvent;
import com.sunday.eventbus.SDEventManager;

/**
 * 绑定手机号
 *
 * @author Administrator
 */
public class BindMobileActivity extends BaseActivity {

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
        setmTitleType(TitleType.TITLE);
        setContentView(R.layout.act_bind_mobile);
        init();
    }

    private void init() {
        initTitle();
        initSDSendValidateButton();
        register();
    }

    private void register() {
        mTv_submit.setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View v) {
                clickSubmit();
            }
        });
    }

    protected void clickSubmit() {
        if (validateParams()) {
            CommonInterface.requestBindMobile(mStrMobile, mStrCode, new SDRequestCallBack<User_infoModel>() {

                @Override
                public void onSuccess(ResponseInfo<String> responseInfo) {
                    if (actModel.getStatus() == 1) {
                        LocalUserModelDao.queryModel().setUser_mobile(mStrMobile);
                        SDEventManager.post(EnumEventTag.BIND_MOBILE_SUCCESS.ordinal());
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

    private boolean validateParams() {
        if (TextUtils.isEmpty(mStrMobile)) {
            SDToast.showToast("手机号不能为空");
            return false;
        }
        mStrCode = mEt_code.getText().toString();
        if (TextUtils.isEmpty(mStrCode)) {
            SDToast.showToast("验证码不能为空");
            return false;
        }

        return true;
    }

    private void initSDSendValidateButton() {
        mBtn_send_code.setmListener(new SDSendValidateButtonListener() {
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

        CommonInterface.requestValidateCode(mStrMobile, 1, new SDRequestCallBack<Sms_send_sms_codeActModel>() {

            @Override
            public void onSuccess(ResponseInfo<String> responseInfo) {
                switch (actModel.getStatus()) {
                    case -1:
                        break;
                    case 1:
                        mBtn_send_code.setmDisableTime(actModel.getLesstime());
                        mBtn_send_code.startTickWork();
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

            @Override
            public void onFailure(HttpException error, String msg) {
            }
        });
    }

    private void initTitle() {
        mTitle.setMiddleTextTop("绑定手机号");
    }

    @Override
    public void onEventMainThread(SDBaseEvent event) {
        super.onEventMainThread(event);
        switch (EnumEventTag.valueOf(event.getTagInt())) {
            case CONFIRM_IMAGE_CODE:
                if (SDActivityManager.getInstance().isLastActivity(this)) {
                    requestSendCode();
                }
                break;

            default:
                break;
        }
    }
}
