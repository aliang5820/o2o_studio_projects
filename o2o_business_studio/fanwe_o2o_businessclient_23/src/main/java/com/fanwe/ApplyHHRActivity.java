package com.fanwe;

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
public class ApplyHHRActivity extends TitleBaseActivity {
    private TextView cityView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.act_apply_hyd);
        initTitle();
        initData();
    }

    private void initTitle() {
        mTitle.setText("申请合伙人");
        cityView = (TextView) findViewById(R.id.city);
    }

    private void initData() {
        City city = (City) getIntent().getSerializableExtra(Constant.ExtraConstant.EXTRA_MODEL);
        cityView.setText(city.getName());
    }

    public void onConfirm(View view) {
        //申请合伙人
        Toast.makeText(mActivity, "确定", Toast.LENGTH_SHORT).show();
    }
}
