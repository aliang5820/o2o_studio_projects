package com.fanwe.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import com.baidu.location.BDLocation;
import com.baidu.location.BDLocationListener;
import com.fanwe.HomeSearchActivity;
import com.fanwe.MainActivity;
import com.fanwe.MapSearchActivity;
import com.fanwe.adapter.CategoryCateLeftAdapter;
import com.fanwe.adapter.CategoryCateRightAdapter;
import com.fanwe.adapter.CategoryOrderAdapter;
import com.fanwe.adapter.CategoryQuanLeftAdapter;
import com.fanwe.adapter.CategoryQuanRightAdapter;
import com.fanwe.adapter.TuanListAdapter;
import com.fanwe.baidumap.BaiduMapManager;
import com.fanwe.constant.Constant.SearchTypeMap;
import com.fanwe.constant.Constant.SearchTypeNormal;
import com.fanwe.constant.Constant.TitleType;
import com.fanwe.event.EnumEventTag;
import com.fanwe.http.InterfaceServer;
import com.fanwe.http.SDRequestCallBack;
import com.fanwe.library.customview.SD2LvCategoryView;
import com.fanwe.library.customview.SDLvCategoryView;
import com.fanwe.library.customview.SDLvCategoryView.SDLvCategoryViewListener;
import com.fanwe.library.customview.SDViewBase;
import com.fanwe.library.customview.SDViewNavigatorManager;
import com.fanwe.library.dialog.SDDialogManager;
import com.fanwe.library.title.SDTitleItem;
import com.fanwe.library.utils.SDCollectionUtil;
import com.fanwe.library.utils.SDResourcesUtil;
import com.fanwe.library.utils.SDToast;
import com.fanwe.library.utils.SDViewUtil;
import com.fanwe.model.Bcate_listModel;
import com.fanwe.model.CategoryOrderModel;
import com.fanwe.model.GoodsModel;
import com.fanwe.model.PageModel;
import com.fanwe.model.Quan_listModel;
import com.fanwe.model.RequestModel;
import com.fanwe.model.Tuan_indexActModel;
import com.fanwe.o2o.newo2o.R;
import com.fanwe.work.AppRuntimeWorker;
import com.handmark.pulltorefresh.library.PullToRefreshBase;
import com.handmark.pulltorefresh.library.PullToRefreshBase.Mode;
import com.handmark.pulltorefresh.library.PullToRefreshBase.OnRefreshListener2;
import com.handmark.pulltorefresh.library.PullToRefreshListView;
import com.lidroid.xutils.http.ResponseInfo;
import com.lidroid.xutils.view.annotation.ViewInject;
import com.sunday.eventbus.SDBaseEvent;

import java.util.ArrayList;
import java.util.List;

/**
 * 团购列表界面
 *
 * @author js02
 */
public class TuanListFragment extends BaseFragment {

    /**
     * 大分类id(int)
     */
    public static final String EXTRA_CATE_ID = "extra_cate_id";

    /**
     * 小分类id(int)
     */
    public static final String EXTRA_TID = "extra_tid";

    /**
     * 商圈id(int)
     */
    public static final String EXTRA_QID = "extra_qid";

    /**
     * 关键字(String)
     */
    public static final String EXTRA_KEY_WORD = "extra_key_word";

    /**
     * 优惠预告(int) 1显示
     */
    public static final String EXTRA_NOTICE = "extra_notice";

    @ViewInject(R.id.lcv_left)
    private SD2LvCategoryView mCvLeft = null;

    @ViewInject(R.id.lcv_middle)
    private SD2LvCategoryView mCvMiddle = null;

    @ViewInject(R.id.lcv_right)
    private SDLvCategoryView mCvRight = null;

    @ViewInject(R.id.ll_empty)
    private LinearLayout mLlEmpty = null;

    @ViewInject(R.id.ll_current_location)
    private LinearLayout mLlCurrentLocation = null;

    @ViewInject(R.id.tv_current_address)
    private TextView mTvAddress = null;

    @ViewInject(R.id.iv_location)
    private ImageView mIvLocation = null;

    @ViewInject(R.id.ll_current_search)
    private LinearLayout mLlCurrentSearch = null;

    @ViewInject(R.id.tv_current_keyword)
    private TextView mTvCurrentKeyword = null;

    @ViewInject(R.id.ptrlv_content)
    private PullToRefreshListView mPtrlvContent = null;

    private TuanListAdapter mAdapter = null;
    private List<GoodsModel> mListModel = new ArrayList<GoodsModel>();
    private SDViewNavigatorManager mViewManager = new SDViewNavigatorManager();

    private boolean mIsNeedBindLeft = true;
    private boolean mIsNeedBindMiddle = true;
    private boolean mIsNeedBindRight = true;

    // ====================提交服务端参数
    /**
     * 大分类id
     */
    private int cate_id;
    /**
     * 小分类id
     */
    private int tid;
    /**
     * 关键词
     */
    private String keyword;
    /**
     * 商圈id
     */
    private int qid;
    /**
     * 排序类型
     */
    private String order_type;

    private PageModel mPage = new PageModel();

    private int mNotice;

    @Override
    protected View onCreateContentView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        setmTitleType(TitleType.TITLE);
        return setContentView(R.layout.frag_tuan_list);
    }

    private void resetParams() {
        cate_id = 0;
        tid = 0;
        qid = 0;
        order_type = null;
        mIsNeedBindLeft = true;
        mIsNeedBindMiddle = true;
        mIsNeedBindRight = true;
    }

    @Override
    protected void init() {
        initTitle();
        getIntentData();
        bindDefaultLvData();
        bindLocationData();
        initCategoryView();
        initCategoryViewNavigatorManager();
        registeClick();
        initPullRefreshLv();
    }

    private void bindLocationData() {
        String addrShort = BaiduMapManager.getInstance().getCurAddressShort();
        if (TextUtils.isEmpty(addrShort)) {
            locationAddress();
        }
    }

    private void initTitle() {
        String title = SDResourcesUtil.getString(R.string.tuan_gou);
        String cityName = AppRuntimeWorker.getCity_name();
        if (!TextUtils.isEmpty(cityName)) {
            title = title + " - " + cityName;
        }
        mTitle.setMiddleTextTop(title);

        if (getActivity() instanceof MainActivity) {
            mTitle.setLeftImageLeft(0);
        } else {
            mTitle.setLeftImageLeft(R.drawable.ic_arrow_left_white);
        }

        mTitle.initRightItem(2);
        mTitle.getItemRight(0).setImageLeft(R.drawable.ic_location_home_top);
        mTitle.getItemRight(1).setImageLeft(R.drawable.ic_search_home_top);
    }

    @Override
    public void onCLickRight_SDTitleSimple(SDTitleItem v, int index) {
        Intent intent = null;
        switch (index) {
            case 0:
                intent = new Intent(getActivity(), MapSearchActivity.class);
                intent.putExtra(MapSearchActivity.EXTRA_SEARCH_TYPE, SearchTypeMap.TUAN);
                startActivity(intent);
                break;
            case 1:
                intent = new Intent(getActivity(), HomeSearchActivity.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                intent.putExtra(HomeSearchActivity.EXTRA_SEARCH_TYPE, SearchTypeNormal.TUAN);
                startActivity(intent);
                break;

            default:
                break;
        }
    }

    /**
     * 从intent获取数据
     */
    private void getIntentData() {
        Intent intent = getActivity().getIntent();
        cate_id = intent.getIntExtra(EXTRA_CATE_ID, 0);
        tid = intent.getIntExtra(EXTRA_TID, 0);
        qid = intent.getIntExtra(EXTRA_QID, 0);
        keyword = intent.getStringExtra(EXTRA_KEY_WORD);

        mNotice = intent.getIntExtra(EXTRA_NOTICE, -1);

        if (TextUtils.isEmpty(keyword)) {
            SDViewUtil.show(mLlCurrentLocation);
            SDViewUtil.hide(mLlCurrentSearch);
            if (BaiduMapManager.getInstance().getCurAddress() != null) {
                mTvAddress.setText(BaiduMapManager.getInstance().getCurAddressShort());
            }
        } else {
            SDViewUtil.hide(mLlCurrentLocation);
            SDViewUtil.show(mLlCurrentSearch);
            mTvCurrentKeyword.setText(keyword);
        }

    }

    /**
     * 初始化下拉刷新控件
     */
    private void initPullRefreshLv() {
        mPtrlvContent.setMode(Mode.BOTH);
        mPtrlvContent.setOnRefreshListener(new OnRefreshListener2<ListView>() {

            @Override
            public void onPullDownToRefresh(PullToRefreshBase<ListView> refreshView) {
                mPage.resetPage();
                requestData(false);
            }

            @Override
            public void onPullUpToRefresh(PullToRefreshBase<ListView> refreshView) {
                if (mPage.increment()) {
                    requestData(true);

                } else {
                    SDToast.showToast("没有更多数据了");
                    mPtrlvContent.onRefreshComplete();
                }
            }
        });

        mPtrlvContent.setRefreshing();
    }

    private void bindDefaultLvData() {
        mAdapter = new TuanListAdapter(mListModel, getActivity());
        mPtrlvContent.setAdapter(mAdapter);
    }

    private void initCategoryViewNavigatorManager() {
        SDViewBase[] items = new SDViewBase[]{mCvLeft, mCvMiddle, mCvRight};
        mViewManager.setItems(items);
        mViewManager.setmMode(SDViewNavigatorManager.Mode.CAN_NONE_SELECT);
    }

    private void initCategoryView() {

        mCvLeft.getmAttr().setmBackgroundDrawableNormalResId(R.drawable.bg_choosebar_press_down);
        mCvLeft.getmAttr().setmBackgroundDrawableSelectedResId(R.drawable.bg_choosebar_press_up);

        mCvLeft.getmAttr().setmTextColorNormalResId(R.color.text_item_content);
        mCvLeft.getmAttr().setmTextColorSelectedResId(R.color.main_color);
        mCvLeft.setmListener(new SD2LvCategoryView.SD2LvCategoryViewListener() {

            @Override
            public void onRightItemSelect(int leftIndex, int rightIndex, Object leftModel, Object rightModel) {
                Bcate_listModel left = (Bcate_listModel) leftModel;
                Bcate_listModel right = (Bcate_listModel) rightModel;
                cate_id = left.getId();
                tid = right.getId();
                mPtrlvContent.setRefreshing();
            }

            @Override
            public void onLeftItemSelect(int leftIndex, Object leftModel, boolean isNotifyDirect) {
                if (isNotifyDirect) {
                    Bcate_listModel left = (Bcate_listModel) leftModel;
                    Bcate_listModel right = SDCollectionUtil.get(left.getBcate_type(), 0);
                    cate_id = left.getId();
                    if (right != null) {
                        tid = right.getId();
                    } else {
                        tid = 0;
                    }
                    mPtrlvContent.setRefreshing();
                }
            }
        });

        mCvMiddle.getmAttr().setmBackgroundDrawableNormalResId(R.drawable.bg_choosebar_press_down_2);
        mCvMiddle.getmAttr().setmBackgroundDrawableSelectedResId(R.drawable.bg_choosebar_press_up_2);

        mCvMiddle.getmAttr().setmTextColorNormalResId(R.color.text_item_content);
        mCvMiddle.getmAttr().setmTextColorSelectedResId(R.color.main_color);
        mCvMiddle.setmListener(new SD2LvCategoryView.SD2LvCategoryViewListener() {

            @Override
            public void onRightItemSelect(int leftIndex, int rightIndex, Object leftModel, Object rightModel) {
                Quan_listModel right = (Quan_listModel) rightModel;
                qid = right.getId();
                mPtrlvContent.setRefreshing();
            }

            @Override
            public void onLeftItemSelect(int leftIndex, Object leftModel, boolean isNotifyDirect) {
                if (isNotifyDirect) {
                    Quan_listModel left = (Quan_listModel) leftModel;
                    Quan_listModel right = SDCollectionUtil.get(left.getQuan_sub(), 0);
                    if (right != null) {
                        qid = right.getId();
                    }
                    if (qid <= 0) {
                        qid = left.getId();
                    }
                    mPtrlvContent.setRefreshing();
                }
            }
        });

        mCvRight.getmAttr().setmBackgroundDrawableNormalResId(R.drawable.bg_choosebar_press_down_3);
        mCvRight.getmAttr().setmBackgroundDrawableSelectedResId(R.drawable.bg_choosebar_press_up_3);

        mCvRight.getmAttr().setmTextColorNormalResId(R.color.text_item_content);
        mCvRight.getmAttr().setmTextColorSelectedResId(R.color.main_color);
        mCvRight.setmListener(new SDLvCategoryViewListener() {
            @Override
            public void onItemSelect(int index, Object model) {
                if (model instanceof CategoryOrderModel) {
                    CategoryOrderModel orderModel = (CategoryOrderModel) model;
                    order_type = orderModel.getCode();
                    mPtrlvContent.setRefreshing();
                }
            }
        });

    }

    private void requestData(final boolean isLoadMore) {
        RequestModel model = new RequestModel();
        model.putCtl("tuan");
        model.put("order_type", order_type); // 排序类型
        model.put("tid", tid);// 小分类ID
        model.put("cate_id", cate_id);// 大分类ID
        model.put("qid", qid); // 商圈
        model.putPage(mPage.getPage());
        model.put("keyword", keyword);
        if (mNotice > 0) {
            model.put("notice", 1);
        }
        SDRequestCallBack<Tuan_indexActModel> handler = new SDRequestCallBack<Tuan_indexActModel>() {

            @Override
            public void onStart() {
                SDDialogManager.showProgressDialog("请稍候...");
            }

            @Override
            public void onSuccess(ResponseInfo<String> responseInfo) {
                if (actModel.getStatus() == 1) {
                    if (mIsNeedBindLeft) {
                        bindLeftCategoryViewData(actModel.getBcate_list());
                        mIsNeedBindLeft = false;
                    }

                    if (mIsNeedBindMiddle) {
                        bindMiddleCategoryViewData(actModel.getQuan_list());
                        mIsNeedBindMiddle = false;
                    }

                    if (mIsNeedBindRight) {
                        bindRightCategoryViewData(actModel.getNavs());
                        mIsNeedBindRight = false;
                    }
                    mPage.update(actModel.getPage());
                    SDViewUtil.updateAdapterByList(mListModel, actModel.getItem(), mAdapter, isLoadMore);
                }
            }

            @Override
            public void onFinish() {
                dealFinishRequest();
            }
        };
        InterfaceServer.getInstance().requestInterface(model, handler);

    }

    protected void dealFinishRequest() {
        SDDialogManager.dismissProgressDialog();
        mPtrlvContent.onRefreshComplete();
        SDViewUtil.toggleEmptyMsgByList(mListModel, mLlEmpty);
    }

    private void bindLeftCategoryViewData(List<Bcate_listModel> listModel) {
        if (!SDCollectionUtil.isEmpty(listModel)) {
            int[] arrIndex = Bcate_listModel.findIndex(cate_id, tid, listModel);
            int leftIndex = arrIndex[0];
            int rightIndex = arrIndex[1];

            Bcate_listModel leftModel = listModel.get(leftIndex);
            List<Bcate_listModel> listRight = leftModel.getBcate_type();

            CategoryCateLeftAdapter adapterLeft = new CategoryCateLeftAdapter(listModel, getActivity());
            adapterLeft.setmDefaultIndex(leftIndex);

            CategoryCateRightAdapter adapterRight = new CategoryCateRightAdapter(listRight, getActivity());
            adapterRight.setmDefaultIndex(rightIndex);

            mCvLeft.setLeftAdapter(adapterLeft);
            mCvLeft.setRightAdapter(adapterRight);
            mCvLeft.setAdapterFinish();
        }
    }

    private void bindMiddleCategoryViewData(List<Quan_listModel> listModel) {
        if (!SDCollectionUtil.isEmpty(listModel)) {
            int[] arrIndex = Quan_listModel.findIndex(qid, listModel);
            int leftIndex = arrIndex[0];
            int rightIndex = arrIndex[1];

            Quan_listModel leftModel = listModel.get(leftIndex);
            List<Quan_listModel> listRight = leftModel.getQuan_sub();

            CategoryQuanLeftAdapter adapterLeft = new CategoryQuanLeftAdapter(listModel, getActivity());
            adapterLeft.setmDefaultIndex(leftIndex);

            CategoryQuanRightAdapter adapterRight = new CategoryQuanRightAdapter(listRight, getActivity());
            adapterRight.setmDefaultIndex(rightIndex);

            mCvMiddle.setLeftAdapter(adapterLeft);
            mCvMiddle.setRightAdapter(adapterRight);
            mCvMiddle.setAdapterFinish();
        }
    }

    private void bindRightCategoryViewData(List<CategoryOrderModel> listOrderModel) {
        if (!SDCollectionUtil.isEmpty(listOrderModel)) {
            CategoryOrderAdapter adapter = new CategoryOrderAdapter(listOrderModel, getActivity());
            mCvRight.setAdapter(adapter);
        }
    }

    private void registeClick() {
        mIvLocation.setOnClickListener(this);

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {

            case R.id.iv_location:
                clickTv_locaiton();
                break;

            default:
                break;
        }
    }

    private void clickTv_locaiton() {
        locationAddress();
    }

    /**
     * 定位地址
     */
    private void locationAddress() {
        // 开始定位
        setCurrentLocation("定位中", false);
        BaiduMapManager.getInstance().startLocation(new BDLocationListener() {

            @Override
            public void onReceiveLocation(BDLocation location) {
                if (BaiduMapManager.getInstance().hasLocationSuccess(location)) {
                    setCurrentLocation(BaiduMapManager.getInstance().getCurAddressShort(), true);
                }
            }
        });
    }

    private void setCurrentLocation(String string, boolean isLocationSuccess) {
        if (!TextUtils.isEmpty(string)) {
            if (mTvAddress != null) {
                mTvAddress.setText(string);
                if (isLocationSuccess) {
                    mPtrlvContent.setRefreshing();
                }
            }
        }
    }

    @Override
    public void onEventMainThread(SDBaseEvent event) {
        super.onEventMainThread(event);
        switch (EnumEventTag.valueOf(event.getTagInt())) {
            case CITY_CHANGE:
                initTitle();
                resetParams();
                mPtrlvContent.setRefreshing();
                break;
            default:
                break;
        }
    }

}