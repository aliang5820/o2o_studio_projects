package com.fanwe.wallet;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.CheckBox;
import android.widget.TextView;

import com.fanwe.BaseActivity;
import com.fanwe.constant.Constant;
import com.fanwe.dao.InitActModelDao;
import com.fanwe.library.utils.SDToast;
import com.fanwe.model.Init_indexActModel;
import com.fanwe.model.WalletBindResultModel;
import com.fanwe.model.WalletModel;
import com.fanwe.o2o.newo2o.R;
import com.lidroid.xutils.view.annotation.ViewInject;
import com.tencent.mm.sdk.modelmsg.SendAuth;
import com.tencent.mm.sdk.openapi.IWXAPI;
import com.tencent.mm.sdk.openapi.WXAPIFactory;

/**
 * Created by Edison on 2016/11/8.
 */

public class WalletBindListActivity extends BaseActivity {

    @ViewInject(R.id.wx_account)
    private TextView wx_account;

    @ViewInject(R.id.wx_checkBox)
    private CheckBox wx_checkBox;

    @ViewInject(R.id.wx_bind_btn)
    private TextView wx_bind_btn;

    @ViewInject(R.id.alipay_account)
    private TextView alipay_account;

    @ViewInject(R.id.alipay_checkBox)
    private CheckBox alipay_checkBox;

    @ViewInject(R.id.alipay_bind_btn)
    private TextView alipay_bind_btn;

    @ViewInject(R.id.bank_account)
    private TextView bank_account;

    @ViewInject(R.id.bank_checkBox)
    private CheckBox bank_checkBox;

    @ViewInject(R.id.bank_bind_btn)
    private TextView bank_bind_btn;

    @ViewInject(R.id.bind_list)
    private View bind_list;

    @ViewInject(R.id.unbind_layout)
    private View unbind_layout;

    @ViewInject(R.id.go_bind_btn)
    private View go_bind_btn;

    @ViewInject(R.id.next_step)
    private View next_step;//进入提现页面

    private BindBroadCastReceiver bindBroadCastReceiver = new BindBroadCastReceiver();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setmTitleType(Constant.TitleType.TITLE);
        setContentView(R.layout.act_wallet_bind_list);
        initTitle();
        initData();
    }

    private void initTitle() {
        mTitle.setMiddleTextTop("提现方式");
        wx_bind_btn.setOnClickListener(this);
        alipay_bind_btn.setOnClickListener(this);
        bank_bind_btn.setOnClickListener(this);
        go_bind_btn.setOnClickListener(this);
        next_step.setOnClickListener(this);
    }

    private void initData() {
        Intent intent = getIntent();
        WalletModel model = (WalletModel) intent.getSerializableExtra(Constant.ExtraConstant.EXTRA_MODEL);
        if (model.getIs_bind_wx() == 0 && model.getIs_bind_zf() == 0 && model.getIs_bind_yh() == 0) {
            bind_list.setVisibility(View.GONE);
            unbind_layout.setVisibility(View.VISIBLE);
        } else {
            next_step.setVisibility(View.VISIBLE);
            //微信
            if (model.getIs_bind_wx() == 1) {
                wx_bind_btn.setVisibility(View.GONE);
                wx_checkBox.setVisibility(View.VISIBLE);
            } else {
                wx_bind_btn.setVisibility(View.VISIBLE);
                wx_checkBox.setVisibility(View.GONE);
            }
            //支付宝
            if (model.getIs_bind_zf() == 1) {
                alipay_bind_btn.setVisibility(View.GONE);
                alipay_checkBox.setVisibility(View.VISIBLE);
            } else {
                alipay_bind_btn.setVisibility(View.VISIBLE);
                alipay_checkBox.setVisibility(View.GONE);
            }
            //银行卡
            if (model.getIs_bind_yh() == 1) {
                bank_bind_btn.setVisibility(View.GONE);
                bank_checkBox.setVisibility(View.VISIBLE);
            } else {
                bank_bind_btn.setVisibility(View.VISIBLE);
                bank_checkBox.setVisibility(View.GONE);
            }
        }

        IntentFilter intentFilter = new IntentFilter();
        intentFilter.addAction(Constant.ACTION_BIND_WX_SUCCESS);
        registerReceiver(bindBroadCastReceiver, intentFilter);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        unregisterReceiver(bindBroadCastReceiver);
    }

    @Override
    public void onClick(View v) {
        super.onClick(v);
        switch (v.getId()) {
            case R.id.wx_bind_btn:
                //进入微信绑定
                Init_indexActModel indexActModel = InitActModelDao.query();
                IWXAPI api = WXAPIFactory.createWXAPI(mActivity, indexActModel.getWx_app_key(), true);
                api.registerApp(indexActModel.getWx_app_key());
                SendAuth.Req req = new SendAuth.Req();
                req.scope = "snsapi_userinfo";
                req.state = "wechat_sdk_demo_test";
                api.sendReq(req);
                break;
            case R.id.alipay_bind_btn:
                SDToast.showToast("暂未开放，敬请期待");
                break;
            case R.id.bank_bind_btn:
                SDToast.showToast("暂未开放，敬请期待");
                break;
            case R.id.go_bind_btn:
                //前往绑定
                unbind_layout.setVisibility(View.GONE);
                bind_list.setVisibility(View.VISIBLE);
                break;
            case R.id.next_step:
                //进入提现填写金额页面
                if(wx_checkBox.isChecked()) {
                    Intent intent = new Intent(mActivity, WalletDrawActivity.class);
                    intent.putExtra(Constant.ExtraConstant.EXTRA_TYPE, Constant.DrawType.WX);
                    startActivity(intent);
                } else {
                    SDToast.showToast("请选择提现方式");
                }
                break;
        }
    }

    class BindBroadCastReceiver extends BroadcastReceiver {

        @Override
        public void onReceive(Context context, Intent intent) {
            String action = intent.getAction();
            if (TextUtils.equals(action, Constant.ACTION_BIND_WX_SUCCESS)) {
                WalletBindResultModel resultModel = (WalletBindResultModel) intent.getSerializableExtra(Constant.ExtraConstant.EXTRA_MODEL);
                if (resultModel.isBind_status()) {
                    wx_bind_btn.setVisibility(View.GONE);
                    wx_checkBox.setVisibility(View.VISIBLE);
                    next_step.setVisibility(View.VISIBLE);
                    SDToast.showToast(resultModel.getInfo());
                } else {
                    SDToast.showToast(resultModel.getInfo());
                }
            } else {
                //其他绑定方式结果
            }
        }
    }
}
