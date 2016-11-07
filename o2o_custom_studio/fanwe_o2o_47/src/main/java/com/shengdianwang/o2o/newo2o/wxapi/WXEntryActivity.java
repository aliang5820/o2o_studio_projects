package com.shengdianwang.o2o.newo2o.wxapi;


import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;

import com.fanwe.constant.Constant;
import com.fanwe.dao.InitActModelDao;
import com.fanwe.dao.LocalUserModelDao;
import com.fanwe.http.InterfaceServer;
import com.fanwe.http.SDRequestCallBack;
import com.fanwe.library.dialog.SDDialogManager;
import com.fanwe.library.utils.LogUtil;
import com.fanwe.library.utils.SDToast;
import com.fanwe.model.Init_indexActModel;
import com.fanwe.model.LocalUserModel;
import com.fanwe.model.RequestModel;
import com.fanwe.model.WalletBindResultModel;
import com.fanwe.model.WalletModel;
import com.fanwe.o2o.newo2o.R;
import com.lidroid.xutils.exception.HttpException;
import com.tencent.mm.sdk.modelbase.BaseReq;
import com.tencent.mm.sdk.modelbase.BaseResp;
import com.tencent.mm.sdk.modelmsg.SendAuth;
import com.tencent.mm.sdk.openapi.IWXAPI;
import com.tencent.mm.sdk.openapi.IWXAPIEventHandler;
import com.tencent.mm.sdk.openapi.WXAPIFactory;

public class WXEntryActivity extends Activity implements IWXAPIEventHandler {


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Init_indexActModel indexActModel = InitActModelDao.query();
        //注册API
        IWXAPI api = WXAPIFactory.createWXAPI(this, indexActModel.getWx_app_key());
        api.handleIntent(getIntent(), this);
    }

    @Override
    public void onReq(BaseReq baseReq) {

    }

    @Override
    public void onResp(BaseResp resp) {
        if (resp instanceof SendAuth.Resp) {
            SendAuth.Resp newResp = (SendAuth.Resp) resp;
            //获取微信传回的code
            String code = newResp.code;
            /*LogUtil.d(">>>>>>" + code);
            finish();*/
            bindWX(code);
        }
    }

    /**
     * 绑定微信用户
     */
    private void bindWX(String code) {
        LocalUserModel localUserModel = LocalUserModelDao.queryModel();
        RequestModel model = new RequestModel();
        model.putCtl("uc_money");
        model.putAct("bind_bank_card");
        model.put("type", 1);//0 支付宝 1微信 3 银行卡
        model.put("user_id", localUserModel.getUser_id());
        model.put("code", code);

        InterfaceServer.getInstance().requestInterface(model, new SDRequestCallBack<WalletBindResultModel>() {

            @Override
            public void onStart() {
                SDDialogManager.showProgressDialog("正在绑定微信账号，请稍候...");
            }

            @Override
            public void onSuccess(WalletBindResultModel resultModel) {
                SDDialogManager.dismissProgressDialog();
                if(resultModel.getStatus() == 1) {
                    Intent intent = new Intent(Constant.ACTION_BIND_WX_SUCCESS);
                    intent.putExtra(Constant.ExtraConstant.EXTRA_MODEL, resultModel);
                    sendBroadcast(intent);
                    finish();
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