package com.fanwe;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.fanwe.app.App;
import com.fanwe.common.CommonInterface;
import com.fanwe.config.AppConfig;
import com.fanwe.constant.Constant.TitleType;
import com.fanwe.dao.LocalUserModelDao;
import com.fanwe.event.EnumEventTag;
import com.fanwe.http.InterfaceServer;
import com.fanwe.http.SDRequestCallBack;
import com.fanwe.library.dialog.SDDialogManager;
import com.fanwe.library.title.SDTitleItem;
import com.fanwe.library.utils.SDOtherUtil;
import com.fanwe.library.utils.SDToast;
import com.fanwe.library.utils.SDViewUtil;
import com.fanwe.model.BaseActModel;
import com.fanwe.model.Init_indexActModel;
import com.fanwe.model.LocalUserModel;
import com.fanwe.model.RequestModel;
import com.fanwe.model.User_infoModel;
import com.fanwe.model.WalletModel;
import com.fanwe.o2o.newo2o.R;
import com.fanwe.umeng.UmengSocialManager;
import com.fanwe.wallet.WalletPayPasswordSet1Activity;
import com.fanwe.work.AppRuntimeWorker;
import com.lidroid.xutils.exception.HttpException;
import com.lidroid.xutils.http.ResponseInfo;
import com.lidroid.xutils.view.annotation.ViewInject;
import com.sunday.eventbus.SDBaseEvent;
import com.sunday.eventbus.SDEventManager;
import com.umeng.socialize.bean.SHARE_MEDIA;
import com.umeng.socialize.controller.listener.SocializeListeners.UMAuthListener;
import com.umeng.socialize.exception.SocializeException;

/**
 * 我的账户
 *
 * @author Administrator
 */
public class MyAccountActivity extends BaseActivity {

    @ViewInject(R.id.et_username)
    private EditText mEt_username; // 用户名

    @ViewInject(R.id.et_email)
    private EditText mEt_email; // 邮箱

    @ViewInject(R.id.ll_pwd)
    private LinearLayout mLl_pwd;

    @ViewInject(R.id.et_pwd)
    private EditText mEt_pwd; // 密码

    @ViewInject(R.id.et_pwd_confirm)
    private EditText mEt_pwd_confirm; // 确认密码

    @ViewInject(R.id.ll_withdraw)
    private LinearLayout mLl_withdraw; // 提现

    @ViewInject(R.id.ll_charge)
    private LinearLayout mLl_charge; // 充值

    @ViewInject(R.id.ll_bind_mobile)
    private LinearLayout mLl_bind_mobile; // 绑定手机

    @ViewInject(R.id.tv_bind_mobile)
    private TextView mTv_bind_mobile;

    @ViewInject(R.id.tv_mobile)
    private TextView mTv_mobile;

    @ViewInject(R.id.tv_mobile_tip)
    private TextView mTv_mobile_tip;

    @ViewInject(R.id.pay_password_status)
    private TextView pay_password_status;

    @ViewInject(R.id.ll_modify_password)
    private LinearLayout mLl_modify_password; // 修改登录密码

    @ViewInject(R.id.ll_modify_pay_password)
    private LinearLayout mLl_modify_pay_password; // 修改支付密码

    @ViewInject(R.id.ll_delivery_address)
    private LinearLayout mLl_delivery_address; // 配送地址

    @ViewInject(R.id.ll_delivery_address_dc)
    private LinearLayout mLl_delivery_address_dc; // 外卖收货地址

    @ViewInject(R.id.ll_third_bind)
    private LinearLayout mLl_third_bind; // 第三方绑定

    @ViewInject(R.id.tv_bind_wx)
    private TextView tv_bind_wx; // 绑定微信

    @ViewInject(R.id.tv_bind_alipay)
    private TextView tv_bind_alipay; // 绑定支付宝

    @ViewInject(R.id.tv_bind_qq)
    private TextView tv_bind_qq; // 绑定QQ
    @ViewInject(R.id.ll_bind_qq)
    private LinearLayout mLl_bind_qq; // 绑定QQ layout

    @ViewInject(R.id.tv_bind_sina)
    private TextView tv_bind_sina; // 绑定新浪微博
    @ViewInject(R.id.ll_bind_sina)
    private LinearLayout mLl_bind_sina; // 绑定新浪微博layout

    @ViewInject(R.id.btn_logout)
    private Button mBtn_logout; // 退出当前帐号

    private String mStrUsername;
    private String mStrEmail;
    private String mStrPwd;
    private String mStrPwdConfirm;

    private LocalUserModel mUser;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setmTitleType(TitleType.TITLE);
        setContentView(R.layout.act_my_account);
        init();
    }

    private void init() {
        refreshUser();
        if (mUser == null) {
            finish();
            return;
        }
        initViewState();
        initTitle();
        registerClick();
    }

    @Override
    protected void onResume() {
        super.onResume();
        requestWallet();
    }

    private void refreshUser() {
        mUser = LocalUserModelDao.queryModel();
    }

    private void initViewState() {
        String username = mUser.getUser_name();
        String email = mUser.getUser_email();

        mEt_username.setText(username);
        mEt_email.setText(email);

        int isTemp = mUser.getIs_tmp();
        if (isTemp == 1) {
            mEt_email.setEnabled(true);
            mEt_username.setEnabled(true);
            SDViewUtil.show(mLl_pwd);
        } else {
            mEt_email.setEnabled(false);
            mEt_username.setEnabled(false);
            SDViewUtil.hide(mLl_pwd);
        }

        String mobile = mUser.getUser_mobile();
        if (isEmpty(mobile)) {
            SDViewUtil.hide(mTv_mobile_tip);
            mTv_bind_mobile.setText("绑定手机");
        } else {
            SDViewUtil.show(mTv_mobile_tip);
            mTv_bind_mobile.setText("已绑定手机");
            mTv_mobile.setText(SDOtherUtil.hideMobile(mobile));
        }

        if (AppRuntimeWorker.getIs_plugin_dc() == 1) {
            SDViewUtil.show(mLl_delivery_address_dc);
        } else {
            SDViewUtil.hide(mLl_delivery_address_dc);
        }

        Init_indexActModel model = AppRuntimeWorker.getInitActModel();
        if (model == null) {
            return;
        }
        //提现移动到上一个页面了
        /*if (model.getMenu_user_withdraw() == 1) {
            SDViewUtil.show(mLl_withdraw);
        } else {
            SDViewUtil.hide(mLl_withdraw);
        }*/

        if (model.getMenu_user_charge() == 1) {
            SDViewUtil.show(mLl_charge);
        } else {
            SDViewUtil.hide(mLl_charge);
        }

        String sinaAppKey = model.getSina_app_key();
        if (TextUtils.isEmpty(sinaAppKey)) {
            SDViewUtil.hide(mLl_bind_sina);
            tv_bind_sina.setText("未绑定");
        } else {
            SDViewUtil.show(mLl_bind_sina);
            tv_bind_sina.setText("已绑定");
        }

        String qqAppKey = model.getQq_app_key();
        if (TextUtils.isEmpty(qqAppKey)) {
            SDViewUtil.hide(mLl_bind_qq);
            tv_bind_qq.setText("未绑定");
        } else {
            SDViewUtil.show(mLl_bind_qq);
            tv_bind_qq.setText("已绑定");
        }
    }

    private void registerClick() {
        mLl_bind_mobile.setOnClickListener(this);
        mLl_modify_password.setOnClickListener(this);
        mLl_modify_pay_password.setOnClickListener(this);
        mLl_delivery_address.setOnClickListener(this);
        mLl_delivery_address_dc.setOnClickListener(this);
        mLl_bind_qq.setOnClickListener(this);
        mLl_bind_sina.setOnClickListener(this);
        mBtn_logout.setOnClickListener(this);
        mLl_withdraw.setOnClickListener(this);
        mLl_charge.setOnClickListener(this);
    }

    private void initTitle() {
        mTitle.setMiddleTextTop("我的账户");

        mTitle.initRightItem(0);
        if (mUser != null) {
            if (mUser.getIs_tmp() == 1) {
                mTitle.initRightItem(1);
                mTitle.getItemRight(0).setTextBot("保存");
            }
        }
    }

    @Override
    public void onCLickRight_SDTitleSimple(SDTitleItem v, int index) {
        clickSubmit();
    }

    @Override
    public void onClick(View v) {
        if (v == mLl_bind_mobile) {
            clickBindMobile(v);
        } else if (v == mLl_modify_password) {
            clickModifyPassword(v);
        } else if (v == mLl_delivery_address) {
            clickDeliveryAddress(v);
        } else if (v == mLl_delivery_address_dc) {
            clickDeliveryAddressDc(v);
        } else if (v == mLl_bind_qq) {
            clickBindQQ(v);
        } else if (v == mLl_bind_sina) {
            clickBindSina(v);
        } else if (v == mBtn_logout) {
            clickLogout(v);
        } else if (v == mLl_withdraw) {
            clickWithdraw(v);
        } else if (v == mLl_charge) {
            clickCharge(v);
        } else if(v == mLl_modify_pay_password) {
            //支付密码
            LocalUserModel localUserModel = LocalUserModelDao.queryModel();
            if(TextUtils.isEmpty(localUserModel.getUser_mobile())) {
                //用户没有绑定手机，跳转到手机绑定页面
                SDToast.showToast("您还没有绑定手机号码，请先绑定手机号码");
                Intent intent = new Intent(getApplicationContext(), BindMobileActivity.class);
                startActivity(intent);
            } else {
                Intent intent = new Intent(getApplicationContext(), WalletPayPasswordSet1Activity.class);
                startActivity(intent);
            }
        }
    }

    private void clickCharge(View v) {
        Intent intent = new Intent(this, ChargePayActivity.class);
        startActivity(intent);
    }

    private void clickWithdraw(View v) {
        Intent intent = new Intent(this, AccountMoneyActivity.class);
        startActivity(intent);
    }

    private void clickDeliveryAddressDc(View v) {
        // TODO 跳转到外卖收货地址列表界面

        // Intent intent = new Intent(this, MyAddressActivity_dc.class);
        // startActivity(intent);
    }

    private void clickLogout(View v) {
        App.getApplication().clearAppsLocalUserModel();
        SDEventManager.post(EnumEventTag.LOGOUT.ordinal());
        CommonInterface.requestLogout(null);
    }

    /**
     * 绑定新浪微博
     *
     * @param v
     */
    private void clickBindSina(View v) {
        UmengSocialManager.doOauthVerify(this, SHARE_MEDIA.SINA, new UMAuthListener() {

            @Override
            public void onStart(SHARE_MEDIA arg0) {
            }

            @Override
            public void onError(SocializeException arg0, SHARE_MEDIA arg1) {
            }

            @Override
            public void onComplete(Bundle bundle, SHARE_MEDIA arg1) {
                String uid = bundle.getString("uid");
                String access_token = bundle.getString("access_secret");
                requestBindSina(uid, access_token);
            }

            @Override
            public void onCancel(SHARE_MEDIA arg0) {
            }
        });
    }

    protected void requestBindSina(String uid, String access_token) {
        RequestModel model = new RequestModel();
        model.putCtl("syncbind");
        model.put("login_type", "Sina");
        model.putUser();
        model.put("sina_id", uid);
        model.put("access_token", access_token);
        SDRequestCallBack<BaseActModel> handler = new SDRequestCallBack<BaseActModel>() {

            @Override
            public void onStart() {
                SDDialogManager.showProgressDialog("请稍候...");
            }

            @Override
            public void onSuccess(ResponseInfo<String> responseInfo) {
            }

            @Override
            public void onFinish() {
                SDDialogManager.dismissProgressDialog();
            }
        };
        InterfaceServer.getInstance().requestInterface(model, handler);
    }

    /**
     * 绑定qq
     *
     * @param v
     */
    private void clickBindQQ(View v) {
        UmengSocialManager.doOauthVerify(this, SHARE_MEDIA.QQ, new UMAuthListener() {

            @Override
            public void onStart(SHARE_MEDIA arg0) {
            }

            @Override
            public void onError(SocializeException arg0, SHARE_MEDIA arg1) {
            }

            @Override
            public void onComplete(Bundle bundle, SHARE_MEDIA arg1) {
                String openId = bundle.getString("openid");
                String access_token = bundle.getString("access_token");
                requestBindQQ(openId, access_token);
            }

            @Override
            public void onCancel(SHARE_MEDIA arg0) {
            }
        });
    }

    protected void requestBindQQ(String openid, String access_token) {
        RequestModel model = new RequestModel();
        model.putCtl("syncbind");
        model.put("login_type", "Qq");
        model.putUser();
        model.put("qqv2_id", openid);
        model.put("access_token", access_token);
        SDRequestCallBack<BaseActModel> handler = new SDRequestCallBack<BaseActModel>() {

            @Override
            public void onStart() {
                SDDialogManager.showProgressDialog("请稍候...");
            }

            @Override
            public void onSuccess(ResponseInfo<String> responseInfo) {
            }

            @Override
            public void onFinish() {
                SDDialogManager.dismissProgressDialog();
            }
        };
        InterfaceServer.getInstance().requestInterface(model, handler);
    }

    /**
     * 配送地址
     *
     * @param v
     */
    private void clickDeliveryAddress(View v) {
        Intent intent = new Intent(this, DeliveryAddressManageActivty.class);
        startActivity(intent);
    }

    /**
     * 修改密码
     *
     * @param v
     */
    private void clickModifyPassword(View v) {
        String mobile = LocalUserModelDao.queryModel().getUser_mobile();
        if (isEmpty(mobile)) {
            startActivity(new Intent(this, BindMobileActivity.class));
        } else {
            startActivity(new Intent(this, ModifyPasswordActivity.class));
        }
    }

    /**
     * 绑定手机
     *
     * @param v
     */
    private void clickBindMobile(View v) {
        Intent intent = new Intent(getApplicationContext(), BindMobileActivity.class);
        startActivity(intent);
    }

    private void clickSubmit() {
        if (!validateParams()) {
            return;
        }

        RequestModel model = new RequestModel();
        model.putUser();
        model.putCtl("uc_account");
        model.putAct("save");
        model.put("user_name", mStrUsername);
        model.put("user_email", mStrEmail);
        model.put("user_pwd", mStrPwd);

        InterfaceServer.getInstance().requestInterface(model, new SDRequestCallBack<User_infoModel>() {

            @Override
            public void onStart() {
                SDDialogManager.showProgressDialog("请稍候");
            }

            @Override
            public void onFinish() {
                SDDialogManager.dismissProgressDialog();
            }

            @Override
            public void onSuccess(ResponseInfo<String> responseInfo) {
                if (actModel.getStatus() == 1) {
                    AppConfig.setUserName(actModel.getUser_name());
                    LocalUserModel.dealLoginSuccess(actModel, false);
                    refreshUser();
                    initViewState();
                    initTitle();
                }
            }
        });
    }

    private boolean validateParams() {
        mStrUsername = mEt_username.getText().toString();
        if (isEmpty(mStrUsername)) {
            SDToast.showToast("用户名不能为空");
            return false;
        }

        mStrEmail = mEt_email.getText().toString();
        if (isEmpty(mStrEmail)) {
            SDToast.showToast("邮箱不能为空");
            return false;
        }

        mStrPwd = mEt_pwd.getText().toString();
        if (isEmpty(mStrPwd)) {
            SDToast.showToast("登录密码不能为空");
            return false;
        }

        mStrPwdConfirm = mEt_pwd_confirm.getText().toString();
        if (isEmpty(mStrPwdConfirm)) {
            SDToast.showToast("确认登录密码不能为空");
            return false;
        }

        if (!mStrPwd.equals(mStrPwdConfirm)) {
            SDToast.showToast("两次密码不一致");
            return false;
        }

        return true;
    }

    /**
     * 用户钱包信息,是否设置有密码，是否绑定有微信，支付宝等信息
     */
    private void requestWallet() {
        RequestModel model = new RequestModel();
        model.putCtl("uc_money");
        model.putAct("user_money_index");

        InterfaceServer.getInstance().requestInterface(model, new SDRequestCallBack<WalletModel>() {

            @Override
            public void onStart() {

            }

            @Override
            public void onSuccess(WalletModel actModel) {
                if (actModel.getStatus() == 1) {
                    if(actModel.getIs_set_pay_password() == 1) {
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

    @Override
    public void onEventMainThread(SDBaseEvent event) {
        super.onEventMainThread(event);
        switch (EnumEventTag.valueOf(event.getTagInt())) {
            case BIND_MOBILE_SUCCESS:
                initViewState();
                break;

            default:
                break;
        }
    }

}
