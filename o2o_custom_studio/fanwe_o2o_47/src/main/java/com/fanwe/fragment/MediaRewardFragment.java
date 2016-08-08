package com.fanwe.fragment;

import android.app.Dialog;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;

import com.fanwe.adapter.MediaRewardAdapter;
import com.fanwe.constant.Constant;
import com.fanwe.http.InterfaceServer;
import com.fanwe.http.SDRequestCallBack;
import com.fanwe.library.dialog.SDDialogManager;
import com.fanwe.library.utils.SDCollectionUtil;
import com.fanwe.library.utils.SDToast;
import com.fanwe.model.MediaRewardCtlItemModel;
import com.fanwe.model.MediaRewardPageModel;
import com.fanwe.model.RequestModel;
import com.fanwe.o2o.newo2o.R;
import com.fanwe.utils.SDInterfaceUtil;
import com.handmark.pulltorefresh.library.PullToRefreshBase;
import com.handmark.pulltorefresh.library.PullToRefreshListView;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Edison on 2016/7/31.
 * 奖励详情
 */
public class MediaRewardFragment extends BaseFragment {
    private PullToRefreshListView mList;
    private TextView mTvError, reward_total_money;
    private int mCurrentPage = 1;
    private int mTotalPage = 0;

    private List<MediaRewardCtlItemModel> mListModel;
    private MediaRewardAdapter mAdapter;
    /*private List<MediaNextLevelCtlItemModel> mListModel;
    private MediaNextLevelAdapter mAdapter;*/
    private int type;
    private View reward_layout, reward_order_layout;

    public static MediaRewardFragment getInstance(int type) {
        MediaRewardFragment fragment = new MediaRewardFragment();
        Bundle bundle = new Bundle();
        bundle.putInt(Constant.ExtraConstant.EXTRA_TYPE, type);
        fragment.setArguments(bundle);
        return fragment;
    }

    @Override
    protected int onCreateContentView() {
        return R.layout.frag_media_reward;
    }

    private void register(View view) {
        mList = (PullToRefreshListView) view.findViewById(R.id.list);
        mTvError = (TextView) view.findViewById(R.id.tv_error);
        reward_total_money = (TextView) view.findViewById(R.id.reward_total_money);

        reward_layout = findViewById(R.id.reward_layout);
        reward_order_layout = findViewById(R.id.reward_order_layout);
    }

    @Override
    protected void init() {
        register(getView());
        bindDefaultData();
        initPullView();
    }

    private void bindDefaultData() {
        type = getArguments().getInt(Constant.ExtraConstant.EXTRA_TYPE);
        switch (type) {
            case Constant.Reward.ORDER:
                reward_total_money.setText("订单奖励总额：￥688");
                reward_layout.setVisibility(View.GONE);
                break;
            case Constant.Reward.HHR:
                reward_total_money.setText("合伙人招募奖励总额：￥788");
                reward_order_layout.setVisibility(View.GONE);
                ((TextView) reward_layout.findViewById(R.id.label1)).setText("合伙人昵称");
                break;
            case Constant.Reward.HYD:
                reward_total_money.setText("会员店招募奖励总额：￥888");
                reward_order_layout.setVisibility(View.GONE);
                ((TextView) reward_layout.findViewById(R.id.label1)).setText("会员店");
                break;
        }

        mListModel = new ArrayList<>();
        mAdapter = new MediaRewardAdapter(mListModel, getActivity(), type);
        //mAdapter = new MediaNextLevelAdapter(mListModel, getActivity());
        mList.setAdapter(mAdapter);
        mList.setOnItemClickListener(new AdapterView.OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                SDToast.showToast("position:" + position);
            }
        });
    }

    private void initPullView() {
        mList.setMode(PullToRefreshBase.Mode.BOTH);
        mList.setOnRefreshListener(new PullToRefreshBase.OnRefreshListener2<ListView>() {
            @Override
            public void onPullDownToRefresh(PullToRefreshBase<ListView> refreshView) {
                refreshData();
            }

            @Override
            public void onPullUpToRefresh(PullToRefreshBase<ListView> refreshView) {
                loadMoreDataType0();
            }
        });
        mList.setRefreshing();
    }

    @Override
    protected void onRefreshData() {
        mCurrentPage = 1;
        requestNextLevelActIndex(false);
    }

    private void loadMoreDataType0() {
        if (mListModel != null && mListModel.size() > 0) {
            mCurrentPage++;
            if (mCurrentPage > mTotalPage && mTotalPage != 0) {
                SDToast.showToast("没有更多数据了!");
                mList.onRefreshComplete();
            } else {
                requestNextLevelActIndex(true);
            }
        } else {
            refreshData();
        }
    }

    //获取下线数据
    protected void requestNextLevelActIndex(final boolean isLoadMore) {
        final RequestModel model = new RequestModel();
        model.putCtl("biz_dealr");
        model.putAct("index");
        model.put("page", mCurrentPage);
        SDRequestCallBack<MediaRewardPageModel<MediaRewardCtlItemModel>> handler = new SDRequestCallBack<MediaRewardPageModel<MediaRewardCtlItemModel>>() {
            private Dialog nDialog;

            @Override
            public void onFinish() {
                if (nDialog != null) {
                    nDialog.dismiss();
                }
                mList.onRefreshComplete();
                toggleEmptyMsg();
            }

            @Override
            public void onSuccess(MediaRewardPageModel actModel) {
                if (!SDInterfaceUtil.isActModelNull(actModel)) {
                    switch (actModel.getStatus()) {
                        case 0:
                            SDToast.showToast(actModel.getInfo());
                            break;
                        case 1:
                            if (actModel.getPage() != null) {
                                mCurrentPage = actModel.getPage().getPage();
                                mTotalPage = actModel.getPage().getPage_total();
                            }
                            if (actModel.getItem() != null && actModel.getItem().size() > 0) {
                                if (!isLoadMore) {
                                    mListModel.clear();
                                }
                                mListModel.addAll(actModel.getItem());
                                mAdapter.updateData(mListModel);
                            } else {
                                //SDToast.showToast("未找到数据!");
                                //TODO 测试奖励数据
                                List<MediaRewardCtlItemModel> list = new ArrayList<>();
                                MediaRewardCtlItemModel itemModel = new MediaRewardCtlItemModel();
                                MediaRewardCtlItemModel itemModel1 = new MediaRewardCtlItemModel();
                                MediaRewardCtlItemModel itemModel2 = new MediaRewardCtlItemModel();
                                list.add(itemModel);
                                list.add(itemModel1);
                                list.add(itemModel2);
                                mListModel.addAll(list);
                                mAdapter.updateData(mListModel);
                            }
                    }
                }

            }

            @Override
            public void onStart() {
                nDialog = SDDialogManager.showProgressDialog("加载中...");
            }
        };
        InterfaceServer.getInstance().requestInterface(model, handler);
    }

    protected void toggleEmptyMsg() {
        if (!SDCollectionUtil.isEmpty(mListModel)) {
            if (mTvError.getVisibility() == View.VISIBLE) {
                mTvError.setVisibility(View.GONE);
            }
        } else {
            if (mTvError.getVisibility() == View.GONE) {
                mTvError.setVisibility(View.VISIBLE);
            }
        }
    }

}