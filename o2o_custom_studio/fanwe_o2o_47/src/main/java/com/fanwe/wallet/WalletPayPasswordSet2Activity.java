package com.fanwe.wallet;

import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.fanwe.BaseActivity;
import com.fanwe.constant.Constant;
import com.fanwe.dao.LocalUserModelDao;
import com.fanwe.http.InterfaceServer;
import com.fanwe.http.SDRequestCallBack;
import com.fanwe.library.dialog.SDDialogManager;
import com.fanwe.library.utils.SDToast;
import com.fanwe.model.LocalUserModel;
import com.fanwe.model.RequestModel;
import com.fanwe.model.SetPayPasswordResultModel;
import com.fanwe.o2o.newo2o.R;
import com.lidroid.xutils.exception.HttpException;
import com.lidroid.xutils.view.annotation.ViewInject;

/**
 * Created by Edison on 2016/11/11.
 */

public class WalletPayPasswordSet2Activity extends BaseActivity {

    @ViewInject(R.id.et_password1)
    private EditText et_password1;

    @ViewInject(R.id.et_password2)
    private EditText et_password2;

    @ViewInject(R.id.set_pay_password_submit)
    private Button set_pay_password_submit;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setmTitleType(Constant.TitleType.TITLE);
        setContentView(R.layout.act_set_pay_password2);
        initTitle();
        initData();
    }

    private void initTitle() {
        mTitle.setMiddleTextTop("设置支付密码");
        set_pay_password_submit.setOnClickListener(this);
    }

    private void initData() {

    }

    @Override
    public void onClick(View v) {
        super.onClick(v);
        switch (v.getId()) {
            case R.id.set_pay_password_submit:
                setPayPassword();
                break;
        }
    }

    //验证手机验证码是否正确
    protected void setPayPassword() {
        String mPassword1 = et_password1.getText().toString();
        String mPassword2 = et_password2.getText().toString();
        if (TextUtils.isEmpty(mPassword1)) {
            SDToast.showToast("请确认6位数字的支付密码");
            return;
        }
        if (!TextUtils.equals(mPassword1, mPassword2)) {
            SDToast.showToast("请确认输入的支付密码是否相同");
            return;
        }

        LocalUserModel localUserModel = LocalUserModelDao.queryModel();
        RequestModel model = new RequestModel();
        model.putCtl("uc_money");
        model.putAct("set_pay_password");
        model.put("password", mPassword1);
        model.put("confirm_password", mPassword2);
        model.put("user_id", localUserModel.getUser_id());

        InterfaceServer.getInstance().requestInterface(model, new SDRequestCallBack<SetPayPasswordResultModel>() {

            @Override
            public void onSuccess(SetPayPasswordResultModel responseInfo) {
                if (actModel.getStatus() == 1) {
                    //成功
                    SDToast.showToast(actModel.getInfo());
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
