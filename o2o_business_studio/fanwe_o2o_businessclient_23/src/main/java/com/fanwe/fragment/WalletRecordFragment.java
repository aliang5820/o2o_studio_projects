package com.fanwe.fragment;

import android.app.Dialog;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;

import com.fanwe.adapter.WalletRecordAdapter;
import com.fanwe.businessclient.R;
import com.fanwe.dao.UserModelDao;
import com.fanwe.http.InterfaceServer;
import com.fanwe.http.listener.SDRequestCallBack;
import com.fanwe.library.dialog.SDDialogManager;
import com.fanwe.library.utils.SDCollectionUtil;
import com.fanwe.library.utils.SDToast;
import com.fanwe.model.LocalUserModel;
import com.fanwe.model.RequestModel;
import com.fanwe.model.WalletRecordModel;
import com.fanwe.model.WalletRecordPageModel;
import com.handmark.pulltorefresh.library.PullToRefreshBase;
import com.handmark.pulltorefresh.library.PullToRefreshListView;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Edison on 2016/11/8.
 * 我的钱包底部的列表
 */
public class WalletRecordFragment extends BaseFragment {
    private PullToRefreshListView mList;
    private TextView mTvError;
    private int mCurrentPage = 1;
    private int mTotalPage = 0;
    private List<WalletRecordModel> mListModel;
    private WalletRecordAdapter mAdapter;

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
        mAdapter = new WalletRecordAdapter(mListModel, getActivity());
        mList.setAdapter(mAdapter);
        mList.setOnItemClickListener(new AdapterView.OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

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

    //获取交易记录数据
    protected void requestNextLevelActIndex(final boolean isLoadMore) {
        LocalUserModel userModel = UserModelDao.getModel();
        final RequestModel model = new RequestModel();
        model.putCtl("biz_ucmoney");
        //钱包首页的交易记录
        model.putAct("get_pay_log_info");
        model.put("page", mCurrentPage);
        model.put("user_id", userModel.getSupplier_id());
        model.put("user_type", userModel.getAccount_type());

        InterfaceServer.getInstance().requestInterface(model, new SDRequestCallBack<WalletRecordPageModel>() {
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
            public void onSuccess(WalletRecordPageModel actModel) {
                if (actModel.getStatus() == 1) {
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
                    }
                }
            }

            @Override
            public void onStart() {
                nDialog = SDDialogManager.showProgressDialog("加载中...");
            }
        });
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