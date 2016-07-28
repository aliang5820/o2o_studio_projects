package com.fanwe;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.fanwe.apply.City;
import com.fanwe.businessclient.R;
import com.fanwe.constant.Constant;

/**
 * Created by Edison on 2016/7/25.
 */
public class ApplyHYDActivity extends TitleBaseActivity {

    private TextView cityView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.act_apply_hyd);
        initView();
        initData();
    }

    private void initView() {
        mTitle.setText("申请会员店");
        cityView = (TextView) findViewById(R.id.city);
        findViewById(R.id.selected_city_layout).setVisibility(View.VISIBLE);
    }

    private void initData() {
        City city = (City) getIntent().getSerializableExtra(Constant.ExtraConstant.EXTRA_MODEL);
        cityView.setText(city.getName());
    }

    public void onConfirm(View view) {
        //申请合伙人
        startActivity(new Intent(mActivity, ApplyPayActivity.class));
    }
}
