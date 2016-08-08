package com.fanwe.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.fanwe.DistributionManageActivity;
import com.fanwe.FriendCircleActivity;
import com.fanwe.MainActivity;
import com.fanwe.MediaHomeActivity;
import com.fanwe.MyAccountActivity;
import com.fanwe.MyCollectionActivity;
import com.fanwe.MyCommentActivity;
import com.fanwe.MyCouponListActivity;
import com.fanwe.MyEventListActivity;
import com.fanwe.MyLotteryActivity;
import com.fanwe.MyOrderListActivity;
import com.fanwe.MyRedEnvelopeActivity;
import com.fanwe.MyYouhuiListActivity;
import com.fanwe.ShopCartActivity;
import com.fanwe.StoreOrderListActivity;
import com.fanwe.UploadUserHeadActivity;
import com.fanwe.common.ImageLoaderManager;
import com.fanwe.constant.Constant.TitleType;
import com.fanwe.dao.LocalUserModelDao;
import com.fanwe.http.InterfaceServer;
import com.fanwe.http.SDRequestCallBack;
import com.fanwe.library.adapter.SDSimpleTextAdapter;
import com.fanwe.library.dialog.SDDialogMenu;
import com.fanwe.library.dialog.SDDialogMenu.SDDialogMenuListener;
import com.fanwe.library.handler.PhotoHandler;
import com.fanwe.library.handler.PhotoHandler.PhotoHandlerListener;
import com.fanwe.library.utils.SDToast;
import com.fanwe.library.utils.SDTypeParseUtil;
import com.fanwe.library.utils.SDViewBinder;
import com.fanwe.library.utils.SDViewUtil;
import com.fanwe.model.RequestModel;
import com.fanwe.model.User_center_indexActModel;
import com.fanwe.o2o.newo2o.R;
import com.fanwe.work.AppRuntimeWorker;
import com.handmark.pulltorefresh.library.PullToRefreshScrollView;
import com.lidroid.xutils.http.HttpHandler;
import com.lidroid.xutils.http.ResponseInfo;
import com.lidroid.xutils.view.annotation.ViewInject;

import java.io.File;
import java.util.Arrays;

/**
 * 我的fragment
 *
 * @author js02
 */
public class MyFragment extends BasePullToRefreshScrollViewFragment {

    @ViewInject(R.id.ll_user_info)
    private LinearLayout mLl_user_info;

    @ViewInject(R.id.iv_user_avatar)
    private ImageView mIv_user_avatar = null; // 用户名

    @ViewInject(R.id.frag_my_account_tv_username)
    private TextView mTvUsername = null; // 用户名

    @ViewInject(R.id.frag_my_account_tv_balance)
    private TextView mTvBalance = null; // 账户余额

    @ViewInject(R.id.frag_my_account_tv_user_score)
    private TextView mTv_user_score = null;// 积分

    @ViewInject(R.id.frag_my_account_ll_group_voucher)
    private LinearLayout mLlGroupVoucher = null;

    @ViewInject(R.id.frag_my_account_tv_group_voucher)
    private TextView mTvGroupVoucher = null; // 我的团购券数量

    @ViewInject(R.id.frag_my_account_ll_group_coupons)
    private LinearLayout mLlGroupCoupons = null;

    @ViewInject(R.id.frag_my_account_tv_coupons)
    private TextView mTvCoupons = null; // 我的优惠券数量

    @ViewInject(R.id.ll_my_friend_circle)
    private LinearLayout mLl_my_friend_circle; // 我的朋友圈

    @ViewInject(R.id.ll_order_not_pay)
    private LinearLayout mLl_order_not_pay;// 待付款订单

    @ViewInject(R.id.tv_order_not_pay)
    private TextView mTv_order_not_pay;

    @ViewInject(R.id.ll_order_has_pay)
    private LinearLayout mLl_order_has_pay; // 已付款订单

    @ViewInject(R.id.tv_order_not_comment)
    private TextView mTv_order_not_comment;

    @ViewInject(R.id.ll_order_store_pay)
    private LinearLayout mLl_order_store_pay; // 到店付订单

    @ViewInject(R.id.ll_order_takeaway_reservation)
    private LinearLayout mLl_order_takeaway_reservation; // 外卖预定订单

    @ViewInject(R.id.ll_my_red_money)
    private LinearLayout mLl_my_red_money; // 我的红包

    @ViewInject(R.id.ll_my_collect)
    private LinearLayout mLl_my_collect; // 我的收藏

    @ViewInject(R.id.ll_my_event)
    private LinearLayout mLl_my_event; // 我的活动

    @ViewInject(R.id.ll_my_lottery)
    private LinearLayout mLl_my_lottery; // 我的抽奖

    @ViewInject(R.id.ll_my_comment)
    private LinearLayout mLl_my_comment; // 我的点评

    @ViewInject(R.id.ll_my_distribution)
    private LinearLayout mLl_my_distribution; // 分销管理

    @ViewInject(R.id.ll_my_media)
    private LinearLayout mLl_my_media; // 自媒体

    @ViewInject(R.id.ll_shopping_cart)
    private LinearLayout mLl_shopping_cart; // 购物车

    private HttpHandler<String> mHttpHandler;

    private PhotoHandler mPhotoHandler;

    @Override
    protected View onCreateContentView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        setmTitleType(TitleType.TITLE);
        return setContentView(R.layout.frag_my);
    }

    @Override
    protected void init() {
        initTitle();
        initPhotoHandler();
        registeClick();
        initViewState();
        initPullToRefreshScrollView();
    }

    private void initPhotoHandler() {
        mPhotoHandler = new PhotoHandler(this);
        mPhotoHandler.setmListener(new PhotoHandlerListener() {

            @Override
            public void onResultFromCamera(File file) {
                if (file != null && file.exists()) {
                    Intent intent = new Intent(getActivity(), UploadUserHeadActivity.class);
                    intent.putExtra(UploadUserHeadActivity.EXTRA_IMAGE_URL, file.getAbsolutePath());
                    startActivity(intent);
                }
            }

            @Override
            public void onResultFromAlbum(File file) {
                if (file != null && file.exists()) {
                    Intent intent = new Intent(getActivity(), UploadUserHeadActivity.class);
                    intent.putExtra(UploadUserHeadActivity.EXTRA_IMAGE_URL, file.getAbsolutePath());
                    startActivity(intent);
                }
            }

            @Override
            public void onFailure(String msg) {
                SDToast.showToast(msg);
            }
        });
    }

    private void initViewState() {
        if (AppRuntimeWorker.getIs_plugin_dc() == 1) {
            SDViewUtil.show(mLl_order_takeaway_reservation);
        } else {
            SDViewUtil.hide(mLl_order_takeaway_reservation);
        }

        if (AppRuntimeWorker.getIs_fx() == 1) {
            SDViewUtil.show(mLl_my_distribution);
        } else {
            SDViewUtil.hide(mLl_my_distribution);
        }

        if (AppRuntimeWorker.getIs_store_pay() == 1) {
            SDViewUtil.show(mLl_order_store_pay);
        } else {
            SDViewUtil.hide(mLl_order_store_pay);
        }

    }

    private void initPullToRefreshScrollView() {
        setModePullFromStart();
        setRefreshing();
    }

    @Override
    public void onPullDownToRefresh(PullToRefreshScrollView view) {
        initViewState();
        requestMyAccount();
    }

    /**
     * 请求我的账户接口
     */
    public void requestMyAccount() {
        if (LocalUserModelDao.queryModel() == null) {
            onRefreshComplete();
            return;
        }
        if (mHttpHandler != null) {
            mHttpHandler.cancel();
        }

        RequestModel model = new RequestModel();
        model.putCtl("user_center");
        model.putUser();
        SDRequestCallBack<User_center_indexActModel> handler = new SDRequestCallBack<User_center_indexActModel>() {

            @Override
            public void onSuccess(ResponseInfo<String> responseInfo) {
                if (actModel.getStatus() == 1) {
                    bindData(actModel);
                }
            }

            @Override
            public void onFinish() {
                onRefreshComplete();
            }
        };
        mHttpHandler = InterfaceServer.getInstance().requestInterface(model, handler);
    }

    protected void bindData(User_center_indexActModel actModel) {
        SDViewBinder.setImageView(actModel.getUser_avatar(), mIv_user_avatar, ImageLoaderManager.getOptionsNoCacheNoResetViewBeforeLoading());

        SDViewBinder.setTextView(mTvUsername, actModel.getUser_name(), "未找到");
        SDViewBinder.setTextView(mTvBalance, actModel.getUser_money_format(), "未找到");
        SDViewBinder.setTextView(mTv_user_score, actModel.getUser_score(), "未找到");

        SDViewBinder.setTextView(mTvGroupVoucher, actModel.getCoupon_count(), "未找到");
        SDViewBinder.setTextView(mTvCoupons, actModel.getYouhui_count(), "未找到");

        // 待付款订单数量
        String strNotPayOrderCount = null;
        int notPayOrderCount = SDTypeParseUtil.getInt(actModel.getNot_pay_order_count());
        if (notPayOrderCount > 0) {
            strNotPayOrderCount = String.valueOf(notPayOrderCount);
        }
        SDViewBinder.setTextView(mTv_order_not_pay, strNotPayOrderCount);

        // 待评价
        String strWaitComment = null;
        int waitComment = SDTypeParseUtil.getInt(actModel.getWait_dp_count());
        if (waitComment > 0) {
            strWaitComment = "待评价 " + waitComment;
        }
        SDViewBinder.setTextViewsVisibility(mTv_order_not_comment, strWaitComment);
    }

    private void initTitle() {
        mTitle.setMiddleTextTop("我的");

        if (getActivity() instanceof MainActivity) {
            mTitle.setLeftImageLeft(0);
        } else {
            mTitle.setLeftImageLeft(R.drawable.ic_arrow_left_white);
        }
    }

    private void registeClick() {
        mIv_user_avatar.setOnClickListener(this);
        mLl_user_info.setOnClickListener(this);
        mLlGroupVoucher.setOnClickListener(this);
        mLlGroupCoupons.setOnClickListener(this);

        mLl_my_friend_circle.setOnClickListener(this);
        mLl_order_not_pay.setOnClickListener(this);
        mLl_order_has_pay.setOnClickListener(this);
        mLl_order_store_pay.setOnClickListener(this);
        mLl_order_takeaway_reservation.setOnClickListener(this);

        mLl_my_red_money.setOnClickListener(this);
        mLl_my_collect.setOnClickListener(this);
        mLl_my_event.setOnClickListener(this);
        mLl_my_lottery.setOnClickListener(this);
        mLl_my_comment.setOnClickListener(this);
        mLl_my_distribution.setOnClickListener(this);
        mLl_my_media.setOnClickListener(this);

        mLl_shopping_cart.setOnClickListener(this);

    }

    @Override
    public void onClick(View v) {
        if (v == mIv_user_avatar) {
            clickUserAvatar();
        } else if (v == mLl_user_info) {
            clickUserInfo();
        } else if (v == mLlGroupVoucher) {
            clickGroupVoucher();
        } else if (v == mLlGroupCoupons) {
            clickGoupCoupons();
        } else if (v == mLl_order_not_pay) {
            clickOrderNotPay();
        } else if (v == mLl_order_has_pay) {
            clickOrderHasPay();
        } else if (v == mLl_order_takeaway_reservation) {
            clickOrderTakeawayReservation();
        } else if (v == mLl_my_collect) {
            clickCollect();
        } else if (v == mLl_my_event) {
            clickMyEvent();
        } else if (v == mLl_my_lottery) {
            clickMyLottery();
        } else if (v == mLl_my_comment) {
            clickMyComment();
        } else if (v == mLl_my_red_money) {
            clickMyRedEnvelope();
        } else if (v == mLl_shopping_cart) {
            clickShopping_cart();
        } else if (v == mLl_my_distribution) {
            clickDistribution();
        } else if (v == mLl_my_friend_circle) {
            clickMyFriendCircle();
        } else if (v == mLl_order_store_pay) {
            clickOrderStorePay();
        } else if (v == mLl_my_media) {
            clickMyMedia();
        }
    }

    /**
     * 自媒体
     */
    private void clickMyMedia() {
        Intent intent = new Intent(getActivity(), MediaHomeActivity.class);
        startActivity(intent);
    }

    /**
     * 到店付订单
     */
    private void clickOrderStorePay() {
        Intent intent = new Intent(getActivity(), StoreOrderListActivity.class);
        startActivity(intent);
    }

    /**
     * 外卖预定订单
     */
    private void clickOrderTakeawayReservation() {
        // TODO 跳到外卖预定订单列表的单独页面

        // Intent intent = new Intent(getActivity(),
        // MyOrderListActivity_dc.class);
        // startActivity(intent);
    }

    /**
     * 我的红包
     */
    private void clickMyRedEnvelope() {
        Intent intent = new Intent(getActivity(), MyRedEnvelopeActivity.class);
        startActivity(intent);
    }

    /**
     * 点击我的朋友圈
     */
    private void clickMyFriendCircle() {
        if (AppRuntimeWorker.isLogin()) {
            Intent intent = new Intent(getActivity(), FriendCircleActivity.class);
            intent.putExtra(FriendCircleActivity.EXTRA_ID, LocalUserModelDao.queryModel().getUser_id());
            startActivity(intent);
        }
    }

    /**
     * 分销管理
     */
    private void clickDistribution() {
        Intent intent = new Intent(getActivity(), DistributionManageActivity.class);
        startActivity(intent);
    }

    private void clickUserInfo() {
        Intent intent = new Intent(getActivity(), MyAccountActivity.class);
        startActivity(intent);
    }

    /**
     * 头像被点击
     */
    private void clickUserAvatar() {
        SDDialogMenu dialog = new SDDialogMenu(getActivity());

        String[] arrItem = new String[]{"拍照", "从手机相册选择"};
        SDSimpleTextAdapter<String> adapter = new SDSimpleTextAdapter<String>(Arrays.asList(arrItem), getActivity());
        dialog.setAdapter(adapter);
        dialog.setmListener(new SDDialogMenuListener() {

            @Override
            public void onItemClick(View v, int index, SDDialogMenu dialog) {
                switch (index) {
                    case 0:
                        mPhotoHandler.getPhotoFromCamera();
                        break;
                    case 1:
                        mPhotoHandler.getPhotoFromAlbum();
                        break;

                    default:
                        break;
                }

            }

            @Override
            public void onDismiss(SDDialogMenu dialog) {
            }

            @Override
            public void onCancelClick(View v, SDDialogMenu dialog) {
            }
        });
        dialog.showBottom();
    }

    /**
     * 我的点评
     */
    private void clickMyComment() {
        startActivity(new Intent(getActivity(), MyCommentActivity.class));
    }

    /**
     * 我的抽奖
     */
    private void clickMyLottery() {
        startActivity(new Intent(getActivity(), MyLotteryActivity.class));
    }

    /**
     * 我的团购券
     */
    private void clickGroupVoucher() {
        startActivity(new Intent(getActivity(), MyCouponListActivity.class));
    }

    /**
     * 我的优惠券
     */
    private void clickGoupCoupons() {
        startActivity(new Intent(getActivity(), MyYouhuiListActivity.class));
    }

    /**
     * 未付款订单
     */
    private void clickOrderNotPay() {
        Intent intent = new Intent(getActivity(), MyOrderListActivity.class);
        intent.putExtra(MyOrderListActivity.EXTRA_PAY_STATUS, 0);
        startActivity(intent);
    }

    /**
     * 已付款订单
     */
    private void clickOrderHasPay() {
        Intent intent = new Intent(getActivity(), MyOrderListActivity.class);
        intent.putExtra(MyOrderListActivity.EXTRA_PAY_STATUS, 1);
        startActivity(intent);
    }

    /**
     * 我的活动
     */
    private void clickMyEvent() {
        Intent intent = new Intent(getActivity(), MyEventListActivity.class);
        startActivity(intent);
    }

    /**
     * 购物车
     */
    private void clickShopping_cart() {
        startActivity(new Intent(getActivity(), ShopCartActivity.class));
    }

    /**
     * 收藏
     */
    private void clickCollect() {
        startActivity(new Intent(getActivity(), MyCollectionActivity.class));
    }

    @Override
    public void onHiddenChanged(boolean hidden) {
        refreshMyAccountFragment();
    }

    @Override
    public void onResume() {
        refreshMyAccountFragment();
        super.onResume();
    }

    private void refreshMyAccountFragment() {
        if (!this.isHidden()) {
            initViewState();
            requestMyAccount();
        }
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        mPhotoHandler.onActivityResult(requestCode, resultCode, data);
        super.onActivityResult(requestCode, resultCode, data);
    }

    @Override
    public void onDestroy() {
        mPhotoHandler.deleteTakePhotoFiles();
        super.onDestroy();
    }

}