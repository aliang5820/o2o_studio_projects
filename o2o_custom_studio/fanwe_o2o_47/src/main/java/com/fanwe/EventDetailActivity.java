package com.fanwe;

import android.content.Intent;
import android.os.Bundle;
import android.widget.FrameLayout;
import android.widget.ScrollView;

import com.fanwe.constant.Constant.TitleType;
import com.fanwe.event.EnumEventTag;
import com.fanwe.fragment.EventDetailCommentFragment;
import com.fanwe.fragment.EventDetailFieldFragment;
import com.fanwe.fragment.EventDetailTopFragment;
import com.fanwe.http.InterfaceServer;
import com.fanwe.http.SDRequestCallBack;
import com.fanwe.library.dialog.SDDialogManager;
import com.fanwe.library.title.SDTitleItem;
import com.fanwe.library.utils.SDToast;
import com.fanwe.library.utils.SDViewUtil;
import com.fanwe.model.Event_indexActModel;
import com.fanwe.model.Event_infoModel;
import com.fanwe.model.RequestModel;
import com.fanwe.o2o.newo2o.R;
import com.fanwe.umeng.UmengSocialManager;
import com.handmark.pulltorefresh.library.PullToRefreshBase;
import com.handmark.pulltorefresh.library.PullToRefreshBase.Mode;
import com.handmark.pulltorefresh.library.PullToRefreshBase.OnRefreshListener2;
import com.handmark.pulltorefresh.library.PullToRefreshScrollView;
import com.lidroid.xutils.exception.HttpException;
import com.lidroid.xutils.http.ResponseInfo;
import com.lidroid.xutils.view.annotation.ViewInject;
import com.sunday.eventbus.SDBaseEvent;

/**
 * 活动详情
 *
 * @author js02
 */
public class EventDetailActivity extends BaseActivity {
    /**
     * 活动id (int)
     */
    public static final String EXTRA_EVENT_ID = "extra_event_id";

    @ViewInject(R.id.act_events_detail_ptrsv_all)
    private PullToRefreshScrollView mPtrsvAll;

    @ViewInject(R.id.act_event_detail_fl_field)
    private FrameLayout mFl_field;

    private EventDetailTopFragment mFragTop;
    private EventDetailFieldFragment mFragField;
    private EventDetailCommentFragment mFragComment;

    private Event_indexActModel mActModel;

    private int mId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setmTitleType(TitleType.TITLE);
        setContentView(R.layout.act_event_detail);
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
        initPullToRefreshScrollView();
    }

    private void initPullToRefreshScrollView() {
        mPtrsvAll.setMode(Mode.PULL_FROM_START);
        mPtrsvAll.setOnRefreshListener(new OnRefreshListener2<ScrollView>() {
            @Override
            public void onPullDownToRefresh(PullToRefreshBase<ScrollView> refreshView) {
                requestDetail();
            }

            @Override
            public void onPullUpToRefresh(PullToRefreshBase<ScrollView> refreshView) {
            }
        });
        mPtrsvAll.setRefreshing();
    }

    public void scrollToFields() {
        SDViewUtil.scrollToViewY(mPtrsvAll.getRefreshableView(), (int) mFl_field.getY(), 100);
    }

    private void getIntentData() {
        Intent intent = getIntent();
        mId = intent.getIntExtra(EXTRA_EVENT_ID, -1);
    }

    @Override
    protected void onNewIntent(Intent intent) {
        setIntent(intent);
        init();
        super.onNewIntent(intent);
    }

    private void initTitle() {
        mTitle.setMiddleTextTop("活动详情");

        //屏蔽分享
        //mTitle.initRightItem(1);
        //mTitle.getItemRight(0).setImageLeft(R.drawable.ic_tuan_detail_share);
    }

    @Override
    public void onCLickRight_SDTitleSimple(SDTitleItem v, int index) {
        clickShare();
    }

    /**
     * 分享
     */
    private void clickShare() {
        if (mActModel == null) {
            SDToast.showToast("未找到可分享内容");
            return;
        }

        Event_infoModel infoModel = mActModel.getEvent_info();
        if (infoModel == null) {
            SDToast.showToast("未找到可分享内容");
            return;
        }

        String content = infoModel.getName() + infoModel.getShare_url();
        String imageUrl = infoModel.getIcon();
        String clickUrl = infoModel.getShare_url();

        UmengSocialManager.openShare("分享", content, imageUrl, clickUrl, this, null);
    }

    /**
     * 请求活动详情接口
     */
    public void requestDetail() {
        RequestModel model = new RequestModel();
        model.putCtl("event");
        model.put("data_id", mId);
        model.putUser();
        model.setIsNeedCheckLoginState(false);
        SDRequestCallBack<Event_indexActModel> handler = new SDRequestCallBack<Event_indexActModel>() {

            @Override
            public void onStart() {
                SDDialogManager.showProgressDialog("请稍候...");
            }

            @Override
            public void onSuccess(ResponseInfo<String> responseInfo) {
                if (actModel.getStatus() == 1) {
                    mActModel = actModel;
                    addFragments(actModel);
                }
            }

            @Override
            public void onFailure(HttpException error, String msg) {
            }

            @Override
            public void onFinish() {
                SDDialogManager.dismissProgressDialog();
                mPtrsvAll.onRefreshComplete();
            }
        };
        InterfaceServer.getInstance().requestInterface(model, handler);
    }

    /**
     * 添加fragment
     */
    private void addFragments(Event_indexActModel model) {
        if (model == null) {
            return;
        }

        mFragTop = new EventDetailTopFragment();
        mFragTop.setmEventModel(model);
        getSDFragmentManager().replace(R.id.act_event_detail_fl_top, mFragTop);

        mFragField = new EventDetailFieldFragment();
        mFragField.setmEventModel(model);
        getSDFragmentManager().replace(R.id.act_event_detail_fl_field, mFragField);

        mFragComment = new EventDetailCommentFragment();
        mFragComment.setmEventModel(model);
        getSDFragmentManager().replace(R.id.act_event_detail_fl_comment, mFragComment);
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