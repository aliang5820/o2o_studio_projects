package com.fanwe.wallet;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.fanwe.BaseActivity;
import com.fanwe.constant.Constant;
import com.fanwe.http.InterfaceServer;
import com.fanwe.http.SDRequestCallBack;
import com.fanwe.library.dialog.SDDialogManager;
import com.fanwe.library.utils.SDToast;
import com.fanwe.model.RequestModel;
import com.fanwe.model.WalletModel;
import com.fanwe.o2o.newo2o.R;
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
                    Intent intent = new Intent(mActivity, WalletBindListActivity.class);
                    intent.putExtra(Constant.ExtraConstant.EXTRA_MODEL, walletModel);
                    startActivity(intent);
                } else {
                    SDToast.showToast("数据出现错误");
                }
                break;
        }
    }

    /**
     * 用户自媒体信息
     */
    private void requestWallet() {
        RequestModel model = new RequestModel();
        model.putCtl("uc_money");
        model.putAct("user_money_index");

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
