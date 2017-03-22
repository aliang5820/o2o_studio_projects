package com.fanwe;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.text.TextUtils;

import com.fanwe.app.App;
import com.fanwe.constant.Constant.TitleType;
import com.fanwe.customview.SDStickyScrollView;
import com.fanwe.event.EnumEventTag;
import com.fanwe.fragment.StoreDetailBriefFragment;
import com.fanwe.fragment.StoreDetailCommentFragment;
import com.fanwe.fragment.StoreDetailGoodsFragment;
import com.fanwe.fragment.StoreDetailInfoFragment;
import com.fanwe.fragment.StoreDetailOtherStoreFragment;
import com.fanwe.fragment.StoreDetailTuanFragment;
import com.fanwe.fragment.StoreDetailYouhuiFragment;
import com.fanwe.http.InterfaceServer;
import com.fanwe.http.SDRequestCallBack;
import com.fanwe.library.customview.StickyScrollView;
import com.fanwe.library.dialog.SDDialogManager;
import com.fanwe.library.title.SDTitleItem;
import com.fanwe.library.utils.SDResourcesUtil;
import com.fanwe.library.utils.SDToast;
import com.fanwe.model.RequestModel;
import com.fanwe.model.RoundPhotoActModel;
import com.fanwe.model.StoreActModel;
import com.fanwe.model.Store_infoModel;
import com.fanwe.o2o.newo2o.R;
import com.fanwe.umeng.UmengSocialManager;
import com.handmark.pulltorefresh.library.PullToRefreshBase;
import com.handmark.pulltorefresh.library.PullToRefreshBase.Mode;
import com.handmark.pulltorefresh.library.PullToRefreshBase.OnRefreshListener2;
import com.lidroid.xutils.http.ResponseInfo;
import com.lidroid.xutils.view.annotation.ViewInject;
import com.sunday.eventbus.SDBaseEvent;

/**
 * 门店详情
 *
 * @author js02
 */
public class StoreDetailActivity extends BaseActivity {

    /**
     * 商家id (int)
     */
    public static final String EXTRA_MERCHANT_ID = "extra_merchant_id";

    @ViewInject(R.id.ssv_scroll)
    private SDStickyScrollView mScrollView;

    private StoreDetailInfoFragment mFragInfo;
    private StoreDetailOtherStoreFragment mFragOtherSupplier;
    private StoreDetailBriefFragment mFragBrief;
    private StoreDetailTuanFragment mFragTuan;
    private StoreDetailGoodsFragment mFragGoods;
    private StoreDetailYouhuiFragment mFragYouhui;
    private StoreDetailCommentFragment mFragComment;

    private StoreActModel mActModel;

    private int mId;
    private String location_address;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setmTitleType(TitleType.TITLE);
        setContentView(R.layout.act_store_detail);
        init();
    }

    private void init() {
        getIntentData();
        if (mId <= 0) {
            SDToast.showToast("id为空");
            finish();
            return;
        }
        initTitle();
        initScrollView();
    }

    private void initScrollView() {
        mScrollView.setMode(Mode.PULL_FROM_START);
        mScrollView.setOnRefreshListener(new OnRefreshListener2<StickyScrollView>() {

            @Override
            public void onPullDownToRefresh(PullToRefreshBase<StickyScrollView> refreshView) {
                requestDetail();
            }

            @Override
            public void onPullUpToRefresh(PullToRefreshBase<StickyScrollView> refreshView) {

            }
        });
        mScrollView.setRefreshing();
    }

    private void initTitle() {
        mTitle.setMiddleTextTop(SDResourcesUtil.getString(R.string.store_detail));

        //屏蔽分享
        //mTitle.initRightItem(1);
        //mTitle.getItemRight(0).setTextBot("全景照片");
        //mTitle.getItemRight(0).setImageLeft(R.drawable.ic_tuan_detail_share);
    }

    @Override
    public void onCLickRight_SDTitleSimple(SDTitleItem v, int index) {
        /*Intent intent = new Intent(App.getApplication(), AppWebViewActivity.class);
        intent.putExtra(AppWebViewActivity.EXTRA_URL, location_address);
        startActivity(intent);*/
        Intent intent = new Intent();
        intent.setAction("android.intent.action.VIEW");
        Uri content_url = Uri.parse(location_address);
        intent.setData(content_url);
        startActivity(intent);
    }

    /**
     * 分享
     */
    private void clickShare() {
        if (mActModel == null) {
            SDToast.showToast("未找到可分享内容");
            return;
        }

        Store_infoModel infoModel = mActModel.getStore_info();
        if (infoModel == null) {
            SDToast.showToast("未找到可分享内容");
            return;
        }

        String content = infoModel.getName() + infoModel.getShare_url();
        String imageUrl = infoModel.getPreview();
        String clickUrl = infoModel.getShare_url();

        UmengSocialManager.openShare("分享", content, imageUrl, clickUrl, this, null);
    }

    private void getIntentData() {
        mId = getIntent().getIntExtra(EXTRA_MERCHANT_ID, -1);
    }

    @Override
    protected void onNewIntent(Intent intent) {
        setIntent(intent);
        init();
        super.onNewIntent(intent);
    }

    /**
     * 获取全景照片地址
     */
    private void requestRoundPhotoUrl() {
        RequestModel model = new RequestModel();
        model.putCtl("biz_member");
        model.putAct("get_supplier_map");
        model.put("location_id", mActModel.getStore_info().getId());
        SDRequestCallBack<RoundPhotoActModel> handler = new SDRequestCallBack<RoundPhotoActModel>() {

            @Override
            public void onStart() {
                SDDialogManager.showProgressDialog("请稍候...");
            }

            @Override
            public void onSuccess(RoundPhotoActModel responseInfo) {
                if (actModel.getStatus() == 1) {
                    SDDialogManager.dismissProgressDialog();
                    if(!TextUtils.isEmpty(responseInfo.getLocation_address())) {
                        //屏蔽分享
                        mTitle.initRightItem(1);
                        //mTitle.getItemRight(0).setTextBot("全景照片");
                        mTitle.getItemRight(0).setImageLeft(R.drawable.photo_3d);
                        //mTitle.getItemRight(0).setImageLeft(android.R.drawable.ic_menu_gallery);
                        location_address = responseInfo.getLocation_address();
                    }
                }
            }

            @Override
            public void onFinish() {

            }
        };
        InterfaceServer.getInstance().requestInterface(model, handler);
    }

    /**
     * 加载商家详细信息
     */
    private void requestDetail() {
        RequestModel model = new RequestModel();
        model.putCtl("store");
        model.putUser();
        model.put("data_id", mId);
        SDRequestCallBack<StoreActModel> handler = new SDRequestCallBack<StoreActModel>() {

            @Override
            public void onStart() {
                SDDialogManager.showProgressDialog("请稍候...");
            }

            @Override
            public void onSuccess(ResponseInfo<String> responseInfo) {
                if (actModel.getStatus() == 1) {
                    mActModel = actModel;
                    addFragments(actModel);
                    //获取是否有全景照片
                    requestRoundPhotoUrl();
                }
            }

            @Override
            public void onFinish() {
                SDDialogManager.dismissProgressDialog();
                mScrollView.onRefreshComplete();
            }
        };
        InterfaceServer.getInstance().requestInterface(model, handler);
    }

    private void addFragments(StoreActModel model) {
        if (model == null) {
            return;
        }

        // 商家信息
        mFragInfo = new StoreDetailInfoFragment();
        mFragInfo.setmStoreModel(model);
        getSDFragmentManager().replace(R.id.act_store_detail_fl_info, mFragInfo);

        // 其他门店
        mFragOtherSupplier = new StoreDetailOtherStoreFragment();
        mFragOtherSupplier.setmStoreModel(model);
        getSDFragmentManager().replace(R.id.act_store_detail_fl_other_merchant, mFragOtherSupplier);

        // 商家介绍
        mFragBrief = new StoreDetailBriefFragment();
        mFragBrief.setmStoreModel(model);
        getSDFragmentManager().replace(R.id.act_store_detail_fl_brief, mFragBrief);

        // 商家其他团购
        mFragTuan = new StoreDetailTuanFragment();
        mFragTuan.setmStoreModel(model);
        getSDFragmentManager().replace(R.id.act_store_detail_fl_tuan, mFragTuan);

        // 商家其他商品
        mFragGoods = new StoreDetailGoodsFragment();
        mFragGoods.setmStoreModel(model);
        getSDFragmentManager().replace(R.id.act_store_detail_fl_goods, mFragGoods);

        // 商家的优惠券
        mFragYouhui = new StoreDetailYouhuiFragment();
        mFragYouhui.setmStoreModel(model);
        getSDFragmentManager().replace(R.id.act_store_detail_fl_youhui, mFragYouhui);

        // 商家评价
        mFragComment = new StoreDetailCommentFragment();
        mFragComment.setmStoreModel(model);
        getSDFragmentManager().replace(R.id.act_store_detail_fl_comment, mFragComment);

    }

    @Override
    public void onEventMainThread(SDBaseEvent event) {
        super.onEventMainThread(event);
        switch (EnumEventTag.valueOf(event.getTagInt())) {
            case COMMENT_SUCCESS:
                setmIsNeedRefreshOnResume(true);
                break;

            default:
                break;
        }
    }

    @Override
    protected void onNeedRefreshOnResume() {
        requestDetail();
        super.onNeedRefreshOnResume();
    }

}