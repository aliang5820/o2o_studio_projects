package com.fanwe;

import android.os.Bundle;

import com.fanwe.o2o.newo2o.R;
/**
 * Created by Edison on 2016/8/1.
 * 推广海报页
 */
public class MediaPosterActivity extends BaseActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.act_media_poster);
        initTitle();
    }

    private void initTitle() {
        mTitle.setMiddleTextTop("推广海报");
    }

}