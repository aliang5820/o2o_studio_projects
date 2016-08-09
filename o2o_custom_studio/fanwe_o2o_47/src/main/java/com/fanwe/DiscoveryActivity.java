package com.fanwe;

import android.os.Bundle;
import android.view.View;

import com.fanwe.adapter.DiscoveryPageAdapter;
import com.fanwe.adapter.DiscoveryTagAdapter;
import com.fanwe.constant.Constant.TitleType;
import com.fanwe.http.InterfaceServer;
import com.fanwe.http.SDRequestCallBack;
import com.fanwe.library.customview.SDViewPager;
import com.fanwe.library.dialog.SDDialogManager;
import com.fanwe.library.view.HorizontalScrollViewPageIndicator;
import com.fanwe.library.view.HorizontalScrollViewPageIndicator.OnTabItemSelectedListener;
import com.fanwe.model.Discover_indexActModel;
import com.fanwe.model.DiscoveryTagModel;
import com.fanwe.model.RequestModel;
import com.fanwe.o2o.newo2o.R;
import com.lidroid.xutils.http.ResponseInfo;
import com.lidroid.xutils.view.annotation.ViewInject;

import java.util.ArrayList;
import java.util.List;

/**
 * 发现
 *
 * @author Administrator
 */
public class DiscoveryActivity extends BaseActivity {

    @ViewInject(R.id.hsvpi_tabs)
    private HorizontalScrollViewPageIndicator mHsvpi_tabs;

    @ViewInject(R.id.vp_content)
    private SDViewPager mVp_content;

    private DiscoveryTagAdapter mAdapterTag;
    private DiscoveryPageAdapter mAdapterPage;

    private Discover_indexActModel mActModel;

    private List<DiscoveryTagModel> mListModel = new ArrayList<DiscoveryTagModel>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setmTitleType(TitleType.TITLE);
        setContentView(R.layout.act_discovery);
        init();
    }

    private void init() {
        initTitle();
        initHorizontalScrollViewPageIndicator();
        requestData(false);
    }

    private void initHorizontalScrollViewPageIndicator() {
        mHsvpi_tabs.setListenerOnTabItemSelected(new OnTabItemSelectedListener() {

            @Override
            public void onSelected(View v, int index) {
                mAdapterTag.getSelectManager().performClick(index);
            }
        });
        mHsvpi_tabs.setViewPager(mVp_content);
    }

    private void initTitle() {
        mTitle.setMiddleTextTop("发现");
    }

    private void requestData(final boolean isLoadMore) {
        RequestModel model = new RequestModel();
        model.putCtl("discover");
        model.putPage(1);

        InterfaceServer.getInstance().requestInterface(model, new SDRequestCallBack<Discover_indexActModel>() {
            @Override
            public void onStart() {
                SDDialogManager.showProgressDialog("");
            }

            @Override
            public void onSuccess(ResponseInfo<String> responseInfo) {
                if (actModel.getStatus() == 1) {
                    mActModel = actModel;
                    bindTags();
                }
            }

            @Override
            public void onFinish() {
                SDDialogManager.dismissProgressDialog();
            }
        });
    }

    private void bindTags() {
        mListModel = mActModel.getTag_list();

        if (isEmpty(mListModel)) {
            return;
        }

        // tab 数据
        mAdapterTag = new DiscoveryTagAdapter(mListModel, mActivity);
        mHsvpi_tabs.setAdapter(mAdapterTag);

        mAdapterPage = new DiscoveryPageAdapter(mListModel, mActivity, getSupportFragmentManager());
        mVp_content.setAdapter(mAdapterPage);

        mHsvpi_tabs.setCurrentItem(0);
    }

}
