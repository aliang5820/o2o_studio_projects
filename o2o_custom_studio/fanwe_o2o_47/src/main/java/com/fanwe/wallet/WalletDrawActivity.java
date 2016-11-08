package com.fanwe.wallet;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
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
import com.fanwe.model.WalletBindResultModel;
import com.fanwe.model.WalletModel;
import com.fanwe.o2o.newo2o.R;
import com.lidroid.xutils.exception.HttpException;
import com.lidroid.xutils.view.annotation.ViewInject;

/**
 * Created by Edison on 2016/11/8.
 */

public class WalletDrawActivity extends BaseActivity {

    @ViewInject(R.id.all_money)
    private TextView all_money;//可提取金额

    @ViewInject(R.id.draw_btn)
    private Button draw_btn;//确认提现

    @ViewInject(R.id.extra_money)
    private TextView extra_money;//扣除的金额

    @ViewInject(R.id.real_money)
    private TextView real_money;//实际到账金额

    @ViewInject(R.id.draw_money)
    private EditText draw_money;//申请金额

    private WalletModel walletModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setmTitleType(Constant.TitleType.TITLE);
        setContentView(R.layout.act_wallet_draw);
        initTitle();
        initData();
    }

    private void initTitle() {
        mTitle.setMiddleTextTop("余额提现");
        draw_btn.setOnClickListener(this);

        draw_money.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void afterTextChanged(Editable editable) {
                if(editable.length() > 0) {
                    double drawMoney = Double.valueOf(editable.toString());
                    if(drawMoney >= 10) {
                        double extraMoney = Math.ceil(drawMoney * 0.02);
                        real_money.setText(getString(R.string.wallet_draw_money, drawMoney - extraMoney));
                        extra_money.setText(getString(R.string.wallet_extra_money, extraMoney));
                    } else if(drawMoney < 10) {
                        extra_money.setText(getString(R.string.wallet_extra_money_empty));
                        real_money.setText("0.00");
                    }
                } else {
                    extra_money.setText(getString(R.string.wallet_extra_money_empty));
                    real_money.setText("0.00");
                }
            }
        });
    }

    private void initData() {
        requestWallet();
        int type = getIntent().getIntExtra(Constant.ExtraConstant.EXTRA_TYPE, -1);
        switch (type) {
            case Constant.DrawType.WX:
                //微信提现
                break;
            case Constant.DrawType.ALIPAY:
                //支付宝提现
                break;
            case Constant.DrawType.BANK:
                //银行卡提现
                break;
        }
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
                //确认提现
                Editable editable = draw_money.getText();
                if(editable.length() > 0) {
                    double drawMoney = Double.valueOf(editable.toString());
                    if(drawMoney < 10) {
                        SDToast.showToast("提现最小额度为¥10.00");
                    } else if(drawMoney > walletModel.getMoney()) {
                        SDToast.showToast("提现金额不能超过您的可用余额");
                    } else {
                        SDToast.showToast("申请提现" + drawMoney);
                        drawMoneyAction(drawMoney);
                    }
                }
                break;
        }
    }

    /**
     * 获取金额提现
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
                if (actModel.getStatus() == 1) {
                    SDDialogManager.dismissProgressDialog();
                    walletModel = actModel;
                    all_money.setText(getString(R.string.wallet_draw_money, actModel.getMoney()));
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

    /**
     * 进行提现
     */
    private void drawMoneyAction(double money) {
        LocalUserModel localUserModel = LocalUserModelDao.queryModel();
        RequestModel model = new RequestModel();
        model.putCtl("uc_money");
        model.putAct("withdrawals");
        model.put("type", 1);//0 支付宝 1微信 3 银行卡
        model.put("user_id", localUserModel.getUser_id());
        model.put("money", money);

        InterfaceServer.getInstance().requestInterface(model, new SDRequestCallBack<WalletBindResultModel>() {

            @Override
            public void onStart() {
                SDDialogManager.showProgressDialog("正在绑定微信账号，请稍候...");
            }

            @Override
            public void onSuccess(WalletBindResultModel resultModel) {
                SDDialogManager.dismissProgressDialog();
                if(resultModel.getStatus() == 1) {
                    showResultDialog(resultModel);
                    SDToast.showToast("申请成功，请等待工作人员审核");
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

    private void showResultDialog(WalletBindResultModel resultModel){
        final AlertDialog.Builder normalDialog = new AlertDialog.Builder(mActivity);
        normalDialog.setTitle("提示");
        normalDialog.setMessage(resultModel.getInfo());
        normalDialog.setPositiveButton("确定",
                new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        finish();
                    }
                });
        // 显示
        normalDialog.show();
    }
}
