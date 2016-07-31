package com.fanwe.fragment;

import android.app.Dialog;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;

import com.fanwe.adapter.MediaNextLevelAdapter;
import com.fanwe.businessclient.R;
import com.fanwe.http.InterfaceServer;
import com.fanwe.http.listener.SDRequestCallBack;
import com.fanwe.library.dialog.SDDialogManager;
import com.fanwe.model.MediaNextLevelCtlItemModel;
import com.fanwe.model.MediaNextLevelPageModel;
import com.fanwe.model.RequestModel;
import com.fanwe.utils.SDCollectionUtil;
import com.fanwe.utils.SDInterfaceUtil;
import com.fanwe.utils.SDToast;
import com.handmark.pulltorefresh.library.PullToRefreshBase;
import com.handmark.pulltorefresh.library.PullToRefreshListView;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Edison on 2016/7/31.
 */
public class MediaNextLevelFragment extends BaseFragment {
    private PullToRefreshListView mList;
    private TextView mTvError;

    private int mCurrentPage = 1;
    private int mTotalPage = 0;

    private List<MediaNextLevelCtlItemModel> mListModel;
    private MediaNextLevelAdapter mAdapter;

    @Override
    protected int onCreateContentView() {
        return R.layout.base_list_include;
    }

    private void register(View view) {
        mList = (PullToRefreshListView) view.findViewById(R.id.list);
        mTvError = (TextView) view.findViewById(R.id.tv_error);
    }

    @Override
    protected void init() {
        register(getView());
        bindDefaultData();
        initPullView();
    }

    private void bindDefaultData() {
        mListModel = new ArrayList<>();
        mAdapter = new MediaNextLevelAdapter(mListModel, getActivity());
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
        model.putCtlAct("biz_dealr", "index");
        model.put("page", mCurrentPage);
        SDRequestCallBack<MediaNextLevelPageModel> handler = new SDRequestCallBack<MediaNextLevelPageModel>() {
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
            public void onSuccess(MediaNextLevelPageModel actModel) {
                if (!SDInterfaceUtil.dealactModel(actModel, getActivity())) {
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
                                //TODO 测试数据
                                List<MediaNextLevelCtlItemModel> list = new ArrayList<>();
                                MediaNextLevelCtlItemModel itemModel = new MediaNextLevelCtlItemModel();
                                MediaNextLevelCtlItemModel itemModel1 = new MediaNextLevelCtlItemModel();
                                MediaNextLevelCtlItemModel itemModel2 = new MediaNextLevelCtlItemModel();
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
        if (SDCollectionUtil.isListHasData(mListModel)) {
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
