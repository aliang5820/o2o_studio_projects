package com.fanwe;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.LinearLayout;

import com.fanwe.common.CommonInterface;
import com.fanwe.constant.Constant.TitleType;
import com.fanwe.customview.SDStickyScrollView;
import com.fanwe.event.EnumEventTag;
import com.fanwe.fragment.TuanDetailAttrsFragment;
import com.fanwe.fragment.TuanDetailBuyNoticelFragment;
import com.fanwe.fragment.TuanDetailCombinedPackagesFragment;
import com.fanwe.fragment.TuanDetailCommentFragment;
import com.fanwe.fragment.TuanDetailDetailFragment;
import com.fanwe.fragment.TuanDetailImagePriceFragment;
import com.fanwe.fragment.TuanDetailMoreDetailFragment;
import com.fanwe.fragment.TuanDetailOtherMerchantFragment;
import com.fanwe.fragment.TuanDetailRatingFragment;
import com.fanwe.fragment.TuanDetailSetMealFragment;
import com.fanwe.http.InterfaceServer;
import com.fanwe.http.SDRequestCallBack;
import com.fanwe.library.customview.StickyScrollView;
import com.fanwe.library.dialog.SDDialogManager;
import com.fanwe.library.title.SDTitleItem;
import com.fanwe.library.utils.SDCollectionUtil;
import com.fanwe.library.utils.SDResourcesUtil;
import com.fanwe.library.utils.SDToast;
import com.fanwe.library.utils.SDViewUtil;
import com.fanwe.model.BaseActModel;
import com.fanwe.model.Deal_add_collectActModel;
import com.fanwe.model.Deal_indexActModel;
import com.fanwe.model.RequestModel;
import com.fanwe.o2o.newo2o.R;
import com.fanwe.umeng.UmengSocialManager;
import com.fanwe.work.AppRuntimeWorker;
import com.handmark.pulltorefresh.library.PullToRefreshBase;
import com.handmark.pulltorefresh.library.PullToRefreshBase.Mode;
import com.handmark.pulltorefresh.library.PullToRefreshBase.OnRefreshListener2;
import com.lidroid.xutils.exception.HttpException;
import com.lidroid.xutils.http.ResponseInfo;
import com.lidroid.xutils.view.annotation.ViewInject;
import com.sunday.eventbus.SDBaseEvent;

import java.util.List;

public class TuanDetailActivity extends BaseActivity {

    /**
     * 商品id (int)
     */
    public static final String EXTRA_GOODS_ID = "extra_goods_id";

    @ViewInject(R.id.ll_add_distribution)
    private LinearLayout mLl_add_distribution;

    @ViewInject(R.id.btn_add_distribution)
    private Button mBtn_add_distribution;

    @ViewInject(R.id.act_tuan_detail_ssv_scroll)
    private SDStickyScrollView mScrollView;

    @ViewInject(R.id.act_tuan_detail_fl_attr)
    private FrameLayout mFlAttr;

    private int mId;
    private Deal_indexActModel mGoodsModel;

    private TuanDetailImagePriceFragment mFragImagePrice;
    private TuanDetailDetailFragment mFragDetail;
    private TuanDetailRatingFragment mFragRating;
    private TuanDetailOtherMerchantFragment mFragOtherMerchant;
    private TuanDetailAttrsFragment mFragAttr;
    private TuanDetailCombinedPackagesFragment mFragCombinedPackages;
    private TuanDetailMoreDetailFragment mFragMoreDetail;
    private TuanDetailBuyNoticelFragment mFragBuyNotice;
    private TuanDetailSetMealFragment mFragSetMeal;
    private TuanDetailCommentFragment mFragComment;

    public TuanDetailAttrsFragment getTuanDetailAttrsFragment() {
        return mFragAttr;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setmTitleType(TitleType.TITLE);
        setContentView(R.layout.act_tuan_detail);
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
        registerClick();
        initScrollView();
    }

    private void registerClick() {
        mBtn_add_distribution.setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View v) {
                if (mGoodsModel == null) {
                    return;
                }
                if (AppRuntimeWorker.isLogin(mActivity)) {
                    int isMyFx = mGoodsModel.getIs_my_fx();
                    if (isMyFx == 0) {
                        requestAddDistribution();
                    } else if (isMyFx == 1) {
                        requestDeleteDistribution();
                    }
                }
            }
        });
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

    public void scrollToAttr() {
        SDViewUtil.scrollToViewY(mScrollView.getRefreshableView(), (int) mFlAttr.getY(), 100);
    }

    @Override
    protected void onNewIntent(Intent intent) {
        setIntent(intent);
        init();
        super.onNewIntent(intent);
    }

    private void initTitle() {
        String title = SDResourcesUtil.getString(R.string.detail);

        mTitle.setMiddleTextTop(title);

        //屏蔽分享
        /*mTitle.initRightItem(2);
        mTitle.getItemRight(0).setImageLeft(R.drawable.ic_tuan_detail_share);
        mTitle.getItemRight(1).setImageLeft(R.drawable.ic_tuan_detail_un_collection);*/
        mTitle.initRightItem(1);
        mTitle.getItemRight(0).setImageLeft(R.drawable.ic_tuan_detail_un_collection);
    }

    @Override
    public void onCLickRight_SDTitleSimple(SDTitleItem v, int index) {
        switch (index) {
            /*case 0:
                clickShare();
                break;*/
            case 0:
                clickCollect();
                break;

            default:
                break;
        }
    }

    private void changeTitle() {
        if (mGoodsModel != null) {
            String title = SDResourcesUtil.getString(R.string.detail);
            switch (mGoodsModel.getIs_shop()) {
                case 0:
                    title = SDResourcesUtil.getString(R.string.tuan_gou_detail);
                    break;
                case 1:
                    title = SDResourcesUtil.getString(R.string.goods_detail);
                    break;
                default:
                    break;
            }
            mTitle.setMiddleTextTop(title);
            initCollectBtn(mGoodsModel.getIs_collect());
        }
    }

    private void initCollectBtn(int isCollect) {
        switch (isCollect) {
            case 0:
                mTitle.getItemRight(0).setImageLeft(R.drawable.ic_tuan_detail_un_collection);
                break;
            case 1:
                mTitle.getItemRight(0).setImageLeft(R.drawable.ic_tuan_detail_collection);
                break;

            default:
                break;
        }
    }

    /**
     * 请求商品详情接口
     */
    private void requestDetail() {
        RequestModel model = new RequestModel();
        model.putCtl("deal");
        model.put("data_id", mId);
        model.putUser();
        model.putLocation();
        SDRequestCallBack<Deal_indexActModel> handler = new SDRequestCallBack<Deal_indexActModel>() {

            @Override
            public void onStart() {
                SDDialogManager.showProgressDialog("请稍候...");
            }

            @Override
            public void onSuccess(ResponseInfo<String> responseInfo) {
                if (actModel.getStatus() == 1) {
                    mGoodsModel = actModel;
                    bindData();
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

    protected void bindData() {
        if (mGoodsModel == null) {
            return;
        }
        changeTitle();

        // 是否显示我要分销
        int isFx = AppRuntimeWorker.getIs_fx();
        if (isFx == 1) {
            if (mGoodsModel.getIs_fx() == 2) {
                SDViewUtil.show(mLl_add_distribution);
                int isMyFx = mGoodsModel.getIs_my_fx();
                if (isMyFx == 0) {
                    mBtn_add_distribution.setText("我要分销");
                } else if (isMyFx == 1) {
                    mBtn_add_distribution.setText("取消分销");
                }
            } else {
                SDViewUtil.hide(mLl_add_distribution);
            }
        } else {
            SDViewUtil.hide(mLl_add_distribution);
        }

        addFragments(mGoodsModel);
    }

    private void getIntentData() {
        Intent intent = getIntent();
        mId = intent.getIntExtra(EXTRA_GOODS_ID, -1);
    }

    /**
     * 添加fragment
     */
    private void addFragments(Deal_indexActModel model) {
        if (model == null) {
            return;
        }

        mFragImagePrice = new TuanDetailImagePriceFragment();
        mFragImagePrice.setmDealModel(model);
        getSDFragmentManager().replace(R.id.act_tuan_detail_fl_image_price, mFragImagePrice);

        mFragDetail = new TuanDetailDetailFragment();
        mFragDetail.setmDealModel(model);
        getSDFragmentManager().replace(R.id.act_tuan_detail_fl_detail, mFragDetail);

        // ---------------评分----------------
        mFragRating = new TuanDetailRatingFragment();
        mFragRating.setmDealModel(model);
        getSDFragmentManager().replace(R.id.act_tuan_detail_fl_rating, mFragRating);

        // ---------------商品属性----------------
        mFragAttr = new TuanDetailAttrsFragment();
        mFragAttr.setmDealModel(model);
        getSDFragmentManager().replace(R.id.act_tuan_detail_fl_attr, mFragAttr);

        // ---------------set_meal----------------
        mFragSetMeal = new TuanDetailSetMealFragment();
        mFragSetMeal.setmDealModel(model);
        getSDFragmentManager().replace(R.id.act_tuan_detail_fl_set_meal, mFragSetMeal);

        // ---------------组合推荐----------------
        mFragCombinedPackages = new TuanDetailCombinedPackagesFragment();
        mFragCombinedPackages.setmDealModel(model);
        getSDFragmentManager().replace(R.id.act_tuan_detail_fl_combined_packages, mFragCombinedPackages);

        // ---------------购买须知----------------
        mFragBuyNotice = new TuanDetailBuyNoticelFragment();
        mFragBuyNotice.setmDealModel(model);
        getSDFragmentManager().replace(R.id.act_tuan_detail_fl_buy_notice, mFragBuyNotice);

        // ---------------更多详情----------------
        mFragMoreDetail = new TuanDetailMoreDetailFragment();
        mFragMoreDetail.setmDealModel(model);
        getSDFragmentManager().replace(R.id.act_tuan_detail_fl_more_detail, mFragMoreDetail);

        // ---------------其他门店----------------
        mFragOtherMerchant = new TuanDetailOtherMerchantFragment();
        mFragOtherMerchant.setmDealModel(model);
        getSDFragmentManager().replace(R.id.act_tuan_detail_fl_other_merchant, mFragOtherMerchant);

        // ---------------评论----------------
        mFragComment = new TuanDetailCommentFragment();
        mFragComment.setmDealModel(model);
        getSDFragmentManager().replace(R.id.act_tuan_detail_fl_comment, mFragComment);
    }

    @Override
    public void onClick(View v) {
    }

    /**
     * 分享
     */
    private void clickShare() {
        if (mGoodsModel != null) {
            String content = mGoodsModel.getName() + mGoodsModel.getShare_url();
            String imageUrl = null;
            String clickUrl = mGoodsModel.getShare_url();

            imageUrl = mGoodsModel.getIcon();

            if (TextUtils.isEmpty(imageUrl)) {
                List<String> listImages = mGoodsModel.getImages();
                if (!SDCollectionUtil.isEmpty(listImages)) {
                    imageUrl = listImages.get(0);
                }
            }

            UmengSocialManager.openShare("分享", content, imageUrl, clickUrl, this, null);
        }
    }

    /**
     * 收藏
     */
    private void clickCollect() {
        if (!AppRuntimeWorker.isLogin(mActivity)) {
            return;
        }
        requestCollect();
    }

    private void requestCollect() {
        RequestModel model = new RequestModel();
        model.putCtl("deal");
        model.putAct("add_collect");
        model.put("id", mId);
        model.putUser();
        SDRequestCallBack<Deal_add_collectActModel> handler = new SDRequestCallBack<Deal_add_collectActModel>() {

            @Override
            public void onStart() {
                SDDialogManager.showProgressDialog("请稍候...");
            }

            @Override
            public void onSuccess(ResponseInfo<String> responseInfo) {
                if (actModel.getStatus() == 1) {
                    initCollectBtn(actModel.getIs_collect());
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

    @Override
    public void onEventMainThread(SDBaseEvent event) {
        super.onEventMainThread(event);
        switch (EnumEventTag.valueOf(event.getTagInt())) {
            case COMMENT_SUCCESS:
                requestDetail();
                break;
            case ADD_DISTRIBUTION_GOODS_SUCCESS:
                requestDetail();
            default:
                break;
        }
    }

    private void requestDeleteDistribution() {
        if (mGoodsModel == null) {
            return;
        }
        CommonInterface.requestDeleteDistribution(mGoodsModel.getId(), new SDRequestCallBack<BaseActModel>() {

            @Override
            public void onSuccess(ResponseInfo<String> responseInfo) {
                if (actModel.getStatus() == 1) {
                    requestDetail();
                }
            }

            @Override
            public void onStart() {
                SDDialogManager.showProgressDialog("请稍候");
            }

            @Override
            public void onFinish() {
                SDDialogManager.dismissProgressDialog();
            }

            @Override
            public void onFailure(HttpException error, String msg) {
            }
        });
    }

    private void requestAddDistribution() {
        if (mGoodsModel == null) {
            return;
        }
        CommonInterface.requestAddDistribution(mGoodsModel.getId(), new SDRequestCallBack<BaseActModel>() {

            @Override
            public void onSuccess(ResponseInfo<String> responseInfo) {
                if (actModel.getStatus() == 1) {
                    requestDetail();
                }
            }

            @Override
            public void onStart() {
                SDDialogManager.showProgressDialog("请稍候");
            }

            @Override
            public void onFinish() {
                SDDialogManager.dismissProgressDialog();
            }

            @Override
            public void onFailure(HttpException error, String msg) {
            }
        });
    }

}