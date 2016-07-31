package com.fanwe.fragment;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.LinearLayout.LayoutParams;

import com.fanwe.BaseActivity;
import com.fanwe.application.App;
import com.fanwe.businessclient.R;
import com.fanwe.constant.Constant.TitleType;
import com.fanwe.library.fragment.SDBaseFragment;
import com.fanwe.library.title.SDTitleItem;
import com.fanwe.library.title.SDTitleSimple;
import com.fanwe.library.title.SDTitleSimple.SDTitleSimpleListener;
import com.fanwe.library.utils.SDResourcesUtil;
import com.lidroid.xutils.ViewUtils;

public class BaseFragment extends SDBaseFragment implements SDTitleSimpleListener {

    protected SDTitleSimple mTitle;
    private TitleType mTitleType = TitleType.TITLE_NONE;

    public TitleType getmTitleType() {
        return mTitleType;
    }

    public void setmTitleType(TitleType mTitleType) {
        this.mTitleType = mTitleType;
    }

    @Override
    protected View onCreateTitleView() {
        View viewTitle = null;
        switch (getmTitleType()) {
            case TITLE:
                mTitle = new SDTitleSimple(App.getApp());
                mTitle.setLeftImageLeft(R.drawable.ic_arrow_left_white);
                mTitle.setmListener(this);
                viewTitle = mTitle;
                break;

            default:
                break;
        }
        return viewTitle;
    }

    @Override
    protected LayoutParams generateTitleViewLayoutParams() {
        LayoutParams params = new LayoutParams(LayoutParams.MATCH_PARENT, SDResourcesUtil.getDimensionPixelSize(R.dimen.height_title_bar));
        return params;
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        ViewUtils.inject(this, view);
        init();
        super.onViewCreated(view, savedInstanceState);
    }

    protected void init() {

    }

    public BaseActivity getBaseActivity() {
        Activity activity = getActivity();
        if (activity != null && activity instanceof BaseActivity) {
            return (BaseActivity) activity;
        }
        return null;
    }

    @Override
    public void onCLickLeft_SDTitleSimple(SDTitleItem v) {
        getActivity().finish();
    }

    @Override
    public void onCLickMiddle_SDTitleSimple(SDTitleItem v) {

    }

    @Override
    public void onCLickRight_SDTitleSimple(SDTitleItem v, int index) {

    }

    @Override
    protected int onCreateContentView() {
        // TODO Auto-generated method stub
        return 0;
    }

}
