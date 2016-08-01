package com.fanwe;

import android.os.Bundle;

import com.fanwe.businessclient.R;

/**
 * Created by Edison on 2016/8/1.
 * 推广海报页
 */
public class MediaPosterActivity extends TitleBaseActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.act_media_poster);
        initTitle();
    }

    private void initTitle() {
        mTitle.setText("推广海报");
    }

}