package com.fanwe.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.TextView;

import com.fanwe.MediaHomeActivity;
import com.fanwe.ModifyPasswordActivity;
import com.fanwe.application.App;
import com.fanwe.businessclient.R;
import com.fanwe.dao.UserModelDao;
import com.fanwe.http.InterfaceServer;
import com.fanwe.http.listener.SDRequestCallBack;
import com.fanwe.library.utils.SDToast;
import com.fanwe.model.LocalUserModel;
import com.fanwe.model.RequestModel;
import com.fanwe.model.WalletModel;
import com.fanwe.wallet.WalletHomeActivity;
import com.fanwe.wallet.WalletPayPasswordSet1Activity;
import com.lidroid.xutils.exception.HttpException;
import com.lidroid.xutils.view.annotation.ViewInject;

/**
 * Created by Edison on 2016/7/28.
 * 个人中心
 */
public class Tab_Mine_Fragment extends BaseFragment {

    @ViewInject(R.id.pay_password_status)
    private TextView pay_password_status;

    @ViewInject(R.id.tv_bind_wx)
    private TextView tv_bind_wx; // 绑定微信

    @ViewInject(R.id.tv_bind_alipay)
    private TextView tv_bind_alipay; // 绑定支付宝

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.act_mine, container, false);
        initView(view);
        return view;
    }

    private void initView(View view) {

        TextView user_name = (TextView) view.findViewById(R.id.user_name);
        user_name.setText(App.getApp().getmLocalUser().getAccount_name());

        view.findViewById(R.id.mine_wallet).setOnClickListener(this);
        view.findViewById(R.id.mine_media).setOnClickListener(this);
        view.findViewById(R.id.modify_password).setOnClickListener(this);
        view.findViewById(R.id.ll_modify_pay_password).setOnClickListener(this);// 修改支付密码
        view.findViewById(R.id.ll_bind_wx).setOnClickListener(this);//微信绑定

        requestWallet();
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.mine_wallet:
                startActivity(new Intent(getActivity(), WalletHomeActivity.class));
                break;
            case R.id.mine_media:
                startActivity(new Intent(getActivity(), MediaHomeActivity.class));
                break;
            case R.id.modify_password:
                //修改密码
                startActivity(new Intent(getContext(), ModifyPasswordActivity.class));
                break;
            case R.id.ll_modify_pay_password:
                //支付密码
                LocalUserModel localUserModel = UserModelDao.getModel();
                /*if (TextUtils.isEmpty(localUserModel.getAccount_name())) {
                    //用户没有绑定手机，跳转到手机绑定页面
                    SDToast.showToast("您还没有绑定手机号码，请先绑定手机号码");
                    Intent intent = new Intent(getApplicationContext(), BindMobileActivity.class);
                    startActivity(intent);
                } else {
                    Intent intent = new Intent(getApplicationContext(), WalletPayPasswordSet1Activity.class);
                    startActivity(intent);
                }*/
                Intent intent = new Intent(getContext(), WalletPayPasswordSet1Activity.class);
                startActivity(intent);
                break;
            case R.id.ll_bind_wx:
                //解除绑定微信
                clickUnBindWx();
                break;
        }
    }

    @Override
    public void onResume() {
        super.onResume();
        requestWallet();
    }

    /**
     * 解除微信绑定
     */
    private void clickUnBindWx() {
        if (TextUtils.equals(tv_bind_wx.getText(), "已绑定")) {
            View popupView = LayoutInflater.from(getContext()).inflate(R.layout.pop_unbind_third_account, null);
            TextView tip = (TextView) popupView.findViewById(R.id.tip);
            tip.setText(getString(R.string.account_unbind, "微信"));
            final PopupWindow mPopupWindow = new PopupWindow(popupView, ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT, true);
            mPopupWindow.setTouchable(true);
            mPopupWindow.setOutsideTouchable(true);
            View cancel = popupView.findViewById(R.id.cancel);
            View submit = popupView.findViewById(R.id.submit);
            cancel.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    mPopupWindow.dismiss();
                }
            });
            submit.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    mPopupWindow.dismiss();
                    unbindWallet(1);
                }
            });

            mPopupWindow.showAtLocation(tv_bind_wx, Gravity.CENTER, 0, 0);
        }
    }

    /**
     * 用户钱包信息,是否设置有密码，是否绑定有微信，支付宝等信息
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

            }

            @Override
            public void onSuccess(WalletModel actModel) {
                if (actModel.getStatus() == 1) {
                    if (actModel.getIs_set_pay_password() == 1) {
                        pay_password_status.setText("已设置");
                    } else {
                        pay_password_status.setText("未设置");
                    }

                    if (actModel.getIs_bind_wx() == 0) {
                        tv_bind_wx.setText("未绑定");
                    } else {
                        tv_bind_wx.setText("已绑定");
                    }

                    if (actModel.getIs_bind_zf() == 0) {
                        tv_bind_alipay.setText("未绑定");
                    } else {
                        tv_bind_alipay.setText("已绑定");
                    }
                }
            }

            @Override
            public void onFailure(HttpException error, String msg) {
                error.printStackTrace();
            }

            @Override
            public void onFinish() {

            }
        });
    }

    /**
     * 解除绑定
     */
    private void unbindWallet(int type) {
        LocalUserModel localUserModel = UserModelDao.getModel();
        RequestModel model = new RequestModel();
        model.putCtl("biz_ucmoney");
        model.putAct("remove_bind");
        model.put("type", type);
        model.put("user_id", localUserModel.getSupplier_id());
        model.put("user_type", localUserModel.getAccount_type());

        InterfaceServer.getInstance().requestInterface(model, new SDRequestCallBack<WalletModel>() {

            @Override
            public void onStart() {

            }

            @Override
            public void onSuccess(WalletModel actModel) {
                if (actModel.getStatus() == 1) {
                    SDToast.showToast("解除绑定成功");
                    requestWallet();
                }
            }

            @Override
            public void onFailure(HttpException error, String msg) {
                error.printStackTrace();
            }

            @Override
            public void onFinish() {

            }
        });
    }
}
