package com.fanwe;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.fanwe.businessclient.R;
import com.fanwe.constant.Constant;
import com.fanwe.model.ApplyResultModel;
import com.fanwe.utils.SDToast;
import com.lidroid.xutils.view.annotation.ViewInject;

/**
 * Created by Edison on 2016/9/4.
 * 审核结果展示
 */
public class ApplyResultActivity extends TitleBaseActivity implements View.OnClickListener {

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
                    SDToast.showToast("联系客服400 775 5587");
                } else if (resultModel.getCheck_status() == -1) {
                    SDToast.showToast("前往修改");
                }
                break;
        }
    }
}