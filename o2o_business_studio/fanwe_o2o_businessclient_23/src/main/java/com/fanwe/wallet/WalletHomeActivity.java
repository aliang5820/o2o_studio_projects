package com.fanwe.wallet;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.TextView;

import com.fanwe.BaseActivity;
import com.fanwe.businessclient.R;
import com.fanwe.constant.Constant;
import com.fanwe.dao.UserModelDao;
import com.fanwe.http.InterfaceServer;
import com.fanwe.http.listener.SDRequestCallBack;
import com.fanwe.library.dialog.SDDialogManager;
import com.fanwe.library.utils.SDToast;
import com.fanwe.model.LocalUserModel;
import com.fanwe.model.RequestModel;
import com.fanwe.model.WalletModel;
import com.lidroid.xutils.exception.HttpException;
import com.lidroid.xutils.view.annotation.ViewInject;

/**
 * Created by Edison on 2016/11/7.
 */

public class WalletHomeActivity extends BaseActivity {

    @ViewInject(R.id.all_money)
    private TextView all_money;

    @ViewInject(R.id.draw_btn)
    private TextView draw_btn;

    @ViewInject(R.id.money1)
    private TextView money1;

    @ViewInject(R.id.money2)
    private TextView money2;

    @ViewInject(R.id.all_draw_record)
    private View all_draw_record;

    private WalletModel walletModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setmTitleType(Constant.TitleType.TITLE);
        setContentView(R.layout.act_wallet_home);
        initTitle();
        initData();
    }

    private void initTitle() {
        mTitle.setMiddleTextTop("我的钱包");
        draw_btn.setOnClickListener(this);
        all_draw_record.setOnClickListener(this);
    }

    private void initData() {
        requestWallet();
    }

    @Override
    protected void onResume() {
        super.onResume();
        requestWallet();
    }

    @Override
    public void onClick(View v) {
        super.onClick(v);
        switch (v.getId()) {
            case R.id.draw_btn:
                //申请提现
                if(walletModel != null) {
                    if (walletModel.getIs_set_pay_password() == 1) {
                        Intent intent = new Intent(mActivity, WalletBindListActivity.class);
                        intent.putExtra(Constant.ExtraConstant.EXTRA_MODEL, walletModel);
                        startActivity(intent);
                    } else {
                        /*LocalUserModel localUserModel = UserModelDao.getModel();
                        if (TextUtils.isEmpty(localUserModel.getAccount_name())) {
                            //用户没有绑定手机，跳转到手机绑定页面
                            SDToast.showToast("您还没有绑定手机号码，请先绑定手机号码");
                            Intent intent = new Intent(getApplicationContext(), BindMobileActivity.class);
                            startActivity(intent);
                        } else {
                            SDToast.showToast("您还没有设置支付密码，请先设置支付密码");
                            Intent intent = new Intent(getApplicationContext(), WalletPayPasswordSet1Activity.class);
                            startActivity(intent);
                        }*/
                        SDToast.showToast("您还没有设置支付密码，请先设置支付密码");
                        Intent intent = new Intent(getApplicationContext(), WalletPayPasswordSet1Activity.class);
                        startActivity(intent);
                    }
                } else {
                    SDToast.showToast("出现数据错误，请重新登录");
                }
                break;
            case R.id.all_draw_record:
                startActivity(new Intent(mActivity, WalletDrawRecordActivity.class));
                break;
        }
    }

    /**
     * 用户钱包信息
     */
    private void requestWallet() {
        LocalUserModel userModel = UserModelDao.getModel();
        RequestModel model = new RequestModel();
        model.putCtl("biz_ucmoney");
        model.putAct("user_money_index");
        model.put("user_id", userModel.getSupplier_id());
        model.put("user_type", userModel.getAccount_type());

        InterfaceServer.getInstance().requestInterface(model, new SDRequestCallBack<WalletModel>() {

            @Override
            public void onStart() {
                SDDialogManager.showProgressDialog("请稍候...");
            }

            @Override
            public void onSuccess(WalletModel actModel) {
                walletModel = actModel;
                if (actModel.getStatus() == 1) {
                    SDDialogManager.dismissProgressDialog();
                    all_money.setText(getString(R.string.wallet_all_money, actModel.getMoney()));
                    money1.setText(getString(R.string.wallet_all_money, actModel.getIn_day_money()));
                    money2.setText(getString(R.string.wallet_all_money, actModel.getOut_money()));
                }
            }

            @Override
            public void onFailure(HttpException error, String msg) {
                error.printStackTrace();
            }

            @Override
            public void onFinish() {
                SDDialogManager.dismissProgressDialog();
            }
        });
    }
}
