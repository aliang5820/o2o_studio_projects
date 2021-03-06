package com.fanwe;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.fanwe.constant.Constant;
import com.fanwe.o2o.newo2o.R;

/**
 * Created by Edison on 2016/7/28.
 */
public class ApplyPayActivity extends BaseActivity {
    private TextView pay_desc;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setmTitleType(Constant.TitleType.TITLE);
        setContentView(R.layout.act_apply_pay);
        initView();
        initData();
    }

    private void initView() {
        mTitle.setMiddleTextTop("订单支付");
        pay_desc = (TextView) findViewById(R.id.pay_desc);
    }

    private void initData() {
        int applyType = getIntent().getIntExtra(Constant.ExtraConstant.EXTRA_TYPE, -1);
        if (applyType == Constant.Apply.HHR) {
            pay_desc.setText("省点网合伙人");
        } else {
            pay_desc.setText("省点网会员店");
        }
    }

    public void onConfirm(View view) {
        //申请合伙人
        startActivity(new Intent(mActivity, MainActivity.class));
        finish();
    }
}