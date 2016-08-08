package com.fanwe;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.fanwe.constant.Constant;
import com.fanwe.o2o.newo2o.R;

/**
 * Created by Edison on 2016/7/29.
 * 自媒体首页
 */
public class MediaHomeActivity extends BaseActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setmTitleType(Constant.TitleType.TITLE);
        setContentView(R.layout.act_media_home);
        initTitle();
    }

    private void initTitle() {
        mTitle.setMiddleTextTop("自媒体");
    }

    public void onNextLevel(View view) {
        startActivity(new Intent(mActivity, MediaNextLevelActivity.class));
    }

    public void onPoster(View view) {
        startActivity(new Intent(mActivity, MediaPosterActivity.class));
    }

    public void onRewardOrder(View view) {
        Intent intent = new Intent(mActivity, MediaRewardActivity.class);
        intent.putExtra(Constant.ExtraConstant.EXTRA_TYPE, Constant.Reward.ORDER);
        startActivity(intent);
    }

    public void onRewardHHR(View view) {
        Intent intent = new Intent(mActivity, MediaRewardActivity.class);
        intent.putExtra(Constant.ExtraConstant.EXTRA_TYPE, Constant.Reward.HHR);
        startActivity(intent);
    }

    public void onRewardHYD(View view) {
        Intent intent = new Intent(mActivity, MediaRewardActivity.class);
        intent.putExtra(Constant.ExtraConstant.EXTRA_TYPE, Constant.Reward.HYD);
        startActivity(intent);
    }
}
