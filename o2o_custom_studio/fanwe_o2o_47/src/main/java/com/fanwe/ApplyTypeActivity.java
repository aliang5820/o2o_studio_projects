package com.fanwe;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.fanwe.constant.Constant;
import com.fanwe.o2o.newo2o.R;

/**
 * Created by Edison on 2016/7/24.
 * 申请类别选择
 */
public class ApplyTypeActivity extends BaseActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.act_apply_type);
        initTitle();
    }

    private void initTitle() {
        mTitle.setMiddleTextTop("申请类别");
    }

    public void onHHR(View view) {
        //申请合伙人
        startCitySelect(Constant.Apply.HHR);
    }

    public void onHYD(View view) {
        //申请会员店
        startCitySelect(Constant.Apply.HYD);
    }

    //传入对应的参数，启动城市选择页面
    private void startCitySelect(int type) {
        Intent intent = new Intent(mActivity, ApplyCityActivity.class);
        intent.putExtra(Constant.ExtraConstant.EXTRA_TYPE, type);
        startActivity(intent);
    }
}
