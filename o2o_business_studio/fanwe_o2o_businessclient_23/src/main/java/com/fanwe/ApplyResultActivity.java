package com.fanwe;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.fanwe.application.App;
import com.fanwe.businessclient.R;
import com.fanwe.constant.Constant;
import com.fanwe.event.EnumEventTag;
import com.fanwe.http.InterfaceServer;
import com.fanwe.http.listener.SDRequestCallBack;
import com.fanwe.library.dialog.SDDialogManager;
import com.fanwe.model.ApplyInfoModel;
import com.fanwe.model.ApplyOrderCtlActModel;
import com.fanwe.model.ApplyResultModel;
import com.fanwe.model.ApplyServiceTypeModel;
import com.fanwe.model.RequestModel;
import com.fanwe.utils.SDInterfaceUtil;
import com.fanwe.utils.SDToast;
import com.lidroid.xutils.view.annotation.ViewInject;
import com.sunday.eventbus.SDEventManager;

/**
 * Created by Edison on 2016/9/4.
 * 审核结果展示
 */
public class ApplyResultActivity extends TitleBaseActivity implements View.OnClickListener {

    @ViewInject(R.id.label2Title)
    private TextView label2Title;

    @ViewInject(R.id.label1)
    private TextView label1; //账号

    @ViewInject(R.id.label2)
    private TextView label2; //企业名称

    @ViewInject(R.id.label3)
    private TextView label3; //申请类别

    @ViewInject(R.id.label4)
    private TextView label4; //所属区域

    @ViewInject(R.id.label5)
    private TextView label5; //状态提示

    @ViewInject(R.id.status_icon)
    private ImageView status_icon; //状态图标

    @ViewInject(R.id.confirmBtn)
    private Button confirmBtn;//按钮

    @ViewInject(R.id.failed_reason_layout)
    private View failed_reason_layout;

    @ViewInject(R.id.failed_reason)
    private TextView failed_reason; //失败原因

    private ApplyResultModel resultModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.act_apply_result);

        mTitle.setText("审核结果");
        confirmBtn.setOnClickListener(this);

        initData();
    }

    private void initData() {
        resultModel = (ApplyResultModel) getIntent().getSerializableExtra(Constant.ExtraConstant.EXTRA_MODEL);

        label1.setText(resultModel.getAccount_name());
        //0 普通 ，1 会员店 ，2 商户合伙人，3个人合伙人
        if(resultModel.getType() == 3) {
            label2Title.setText("姓名:");
        } else {
            label2Title.setText("企业名称:");
        }
        label2.setText(resultModel.getH_name());
        label3.setText(resultModel.getType_str());
        String area = resultModel.getProvince() + resultModel.getCity() + resultModel.getArea();
        label4.setText(area);
        //-1 失败 0 审核中 1成功
        if (resultModel.getCheck_status() == 0) {
            label5.setText("审核中，请耐心等待");
            confirmBtn.setText("联系客服");
            status_icon.setImageResource(R.drawable.apply_result_checking);
        } else if (resultModel.getCheck_status() == -1) {
            label5.setText("审核失败");
            confirmBtn.setText("前往修改");
            status_icon.setImageResource(R.drawable.apply_result_failed);
            failed_reason_layout.setVisibility(View.VISIBLE);
            failed_reason.setText(resultModel.getCheck_failed_reason());
        }
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.confirmBtn:
                if (resultModel.getCheck_status() == 0) {
                    //用intent启动拨打电话
                    Intent intent = new Intent(Intent.ACTION_CALL, Uri.parse("tel:4007755587"));
                    startActivity(intent);
                } else if (resultModel.getCheck_status() == -1) {
                    requestInfo();
                }
                break;
        }
    }

    //再次编辑，获取数据
    private void requestInfo() {
        RequestModel model = new RequestModel();
        model.putCtlAct("biz_member", "get_supplier_info");
        model.put("supplier_id", resultModel.getSubmit_id());//商户的id

        InterfaceServer.getInstance().requestInterface(model, new SDRequestCallBack<ApplyInfoModel>() {

            @Override
            public void onFinish() {
                SDDialogManager.dismissProgressDialog();
            }

            @Override
            public void onSuccess(ApplyInfoModel actModel) {
                SDDialogManager.dismissProgressDialog();
                actModel.setSubmit_id(resultModel.getSubmit_id());
                if (!SDInterfaceUtil.dealactModel(actModel, null) && actModel.getStatus() == 1) {
                    //0 普通 ，1 会员店 ，2 商户合伙人，3个人合伙人
                    Intent intent;
                    switch (resultModel.getType()) {
                        case 1:
                            intent = new Intent(mActivity, ApplyHYDActivity.class);
                            intent.putExtra(Constant.ExtraConstant.EXTRA_OTHER_MODEL, actModel);
                            intent.putExtra(Constant.ExtraConstant.EXTRA_TYPE, Constant.Apply.EDIT_HYD);
                            startActivity(intent);
                            break;
                        case 2:
                            intent = new Intent(mActivity, ApplyHHRActivity.class);
                            intent.putExtra(Constant.ExtraConstant.EXTRA_OTHER_MODEL, actModel);
                            intent.putExtra(Constant.ExtraConstant.EXTRA_TYPE, Constant.Apply.EDIT_COMPANY_HHR);
                            startActivity(intent);
                            break;
                        case 3:
                            intent = new Intent(mActivity, ApplyHHRActivity.class);
                            intent.putExtra(Constant.ExtraConstant.EXTRA_OTHER_MODEL, actModel);
                            intent.putExtra(Constant.ExtraConstant.EXTRA_TYPE, Constant.Apply.EDIT_PERSON_HHR);
                            startActivity(intent);
                            break;
                    }
                }
            }

            @Override
            public void onStart() {
                SDDialogManager.showProgressDialog("正在获取数据，请稍候...");
            }
        });
    }
}