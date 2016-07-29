package com.fanwe;

import android.os.Bundle;

import com.fanwe.businessclient.R;

/**
 * Created by Edison on 2016/7/29.
 * 自媒体首页
 */
public class MediaHomeActivity extends TitleBaseActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.act_media_home);
        initTitle();
    }

    private void initTitle() {
        mTitle.setText("自媒体");
    }

}
