package com.fanwe.fragment;

import android.app.Dialog;
import android.content.Intent;
import android.content.pm.PackageInfo;
import android.net.Uri;
import android.text.TextUtils;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ScrollView;
import android.widget.Toast;

import com.fanwe.LoginActivity;
import com.fanwe.ModifyPasswordActivity;
import com.fanwe.application.App;
import com.fanwe.businessclient.R;
import com.fanwe.customview.SDSimpleSetItemView;
import com.fanwe.http.InterfaceServer;
import com.fanwe.http.listener.SDRequestCallBack;
import com.fanwe.library.dialog.SDDialogConfirm;
import com.fanwe.library.dialog.SDDialogCustom;
import com.fanwe.library.dialog.SDDialogCustom.SDDialogCustomListener;
import com.fanwe.model.BaseCtlActModel;
import com.fanwe.model.BizMoreCtlIndexActModel;
import com.fanwe.model.LocalUserModel;
import com.fanwe.model.RequestModel;
import com.fanwe.service.AppUpgradeService;
import com.fanwe.utils.SDDialogUtil;
import com.fanwe.utils.SDInterfaceUtil;
import com.fanwe.utils.SDPackageUtil;
import com.fanwe.utils.SDToast;
import com.handmark.pulltorefresh.library.PullToRefreshBase;
import com.handmark.pulltorefresh.library.PullToRefreshBase.OnRefreshListener;
import com.handmark.pulltorefresh.library.PullToRefreshScrollView;
import com.umeng.message.UmengRegistrar;

/**
 * 更多
 */
public class Tab_4_Fragment extends BaseFragment implements OnClickListener {
    private SDSimpleSetItemView mExit, mBiz_version, mPhone;

    private BizMoreCtlIndexActModel mActModel;
    private PullToRefreshScrollView mPtr_scrollview;

    @Override
    protected int onCreateContentView() {
        return R.layout.m_frag_tab_4;
    }

    private void register(View view) {
        mPtr_scrollview = (PullToRefreshScrollView) view.findViewById(R.id.ptr_scrollview);
        mPtr_scrollview.setOnRefreshListener(new OnRefreshListener<ScrollView>() {
            @Override
            public void onRefresh(PullToRefreshBase<ScrollView> refreshView) {
                requestBizMoreCtlIndexAct();
            }
        });
        mExit = (SDSimpleSetItemView) view.findViewById(R.id.frag_tab3_item_exit);
        mExit.setOnClickListener(this);

        mBiz_version = (SDSimpleSetItemView) view.findViewById(R.id.frag_tab3_item_biz_version);
        mBiz_version.setOnClickListener(this);
        mPhone = (SDSimpleSetItemView) view.findViewById(R.id.frag_tab3_item_phone);
        mPhone.setOnClickListener(this);
    }

    @Override
    protected void init() {
        register(getView());
        requestBizMoreCtlIndexAct();
    }

    private void initItemData(BizMoreCtlIndexActModel actModel) {
        LocalUserModel localUserModel = App.getApp().getmLocalUser();
        if (localUserModel != null) {
            mExit.setTitleSubText("管理员账号:  " + localUserModel.getAccount_name() + "  (退出)");
            PackageInfo packageInfo = SDPackageUtil.getCurrentAppPackageInfo(getActivity(), getActivity().getPackageName());
            mBiz_version.setTitleSubText("版本更新" + "  (版本号" + packageInfo.versionName + ")");
            mPhone.setTitleSubText("商户热线  " + actModel.getShop_tel() + "(点击拨打)");
        }
    }

    private void requestBizMoreCtlIndexAct() {
        RequestModel model = new RequestModel();
        model.putCtl("biz_more");
        SDRequestCallBack<BizMoreCtlIndexActModel> handler = new SDRequestCallBack<BizMoreCtlIndexActModel>() {
            private Dialog nDialog;

            @Override
            public void onFinish() {
                if (nDialog != null) {
                    nDialog.dismiss();
                    mPtr_scrollview.onRefreshComplete();
                }
            }

            @Override
            public void onSuccess(BizMoreCtlIndexActModel actModel) {
                mActModel = actModel;
                if (!SDInterfaceUtil.dealactModel(mActModel, getActivity())) {
                    switch (mActModel.getStatus()) {
                        case 0:
                            SDToast.showToast(mActModel.getInfo());
                            break;
                        case 1:
                            initItemData(mActModel);
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

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.frag_tab3_item_exit:
                clickExit();
                break;
            case R.id.frag_tab3_item_biz_version:
                clickBiz_version();
                break;
            case R.id.frag_tab3_item_phone:
                clickPhone();
                break;
        }
    }

    private void clickPhone() {
        Intent intent = new Intent(Intent.ACTION_DIAL, Uri.parse("tel:" + mActModel.getShop_tel()));
        startActivity(intent);
    }

    private void clickBiz_version() {
        Intent updateIntent = new Intent(getActivity(), AppUpgradeService.class);
        updateIntent.putExtra(AppUpgradeService.EXTRA_SERVICE_START_TYPE, 1); // 1代表该service被用户手动启动检测版本
        getActivity().startService(updateIntent);
    }

    private void clickExit() {
        if (App.getApp().getmLocalUser() != null) // 已登录
        {
            SDDialogConfirm dialogConfirm = new SDDialogConfirm();
            dialogConfirm.setTextContent("确定要退出账号吗?");
            dialogConfirm.setmListener(new SDDialogCustomListener() {
                @Override
                public void onDismiss(SDDialogCustom dialog) {
                }

                @Override
                public void onClickConfirm(View v, SDDialogCustom dialog) {
                    requestInterfaceLoginOut();
                    requestBiz_apns();
                    dialog.dismiss();
                    App.getApp().clearAppsLocalUserModel();
                    SDToast.showToast("成功退出帐号!", Toast.LENGTH_SHORT);
                    startActivity(new Intent(getActivity(), LoginActivity.class));
                    getBaseActivity().finish();
                }

                @Override
                public void onClickCancel(View v, SDDialogCustom dialog) {
                }
            }).show();

        } else {
            startActivity(new Intent(getActivity(), LoginActivity.class));
        }
    }

    private void requestInterfaceLoginOut() {

        RequestModel model = new RequestModel();
        model.putCtlAct("biz_user", "loginout");

        SDRequestCallBack<BaseCtlActModel> handler = new SDRequestCallBack<BaseCtlActModel>() {
        };
        InterfaceServer.getInstance().requestInterface(model, handler);

    }

    private void requestBiz_apns() {
        String device_token = UmengRegistrar.getRegistrationId(getActivity());
        if (TextUtils.isEmpty(device_token)) {
            return;
        }

        RequestModel model = new RequestModel();
        model.put("act", "biz_apns");
        model.put("device_token", device_token);
        SDRequestCallBack<BaseCtlActModel> handler = new SDRequestCallBack<BaseCtlActModel>() {
        };
        InterfaceServer.getInstance().requestInterface(model, handler);
    }

}
