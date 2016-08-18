package com.fanwe.fragment;

import android.app.DatePickerDialog;
import android.app.Dialog;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.DatePicker;
import android.widget.ListView;
import android.widget.TextView;

import com.fanwe.adapter.MediaRewardAdapter;
import com.fanwe.constant.Constant;
import com.fanwe.dao.LocalUserModelDao;
import com.fanwe.http.InterfaceServer;
import com.fanwe.http.SDRequestCallBack;
import com.fanwe.library.dialog.SDDialogManager;
import com.fanwe.library.utils.SDCollectionUtil;
import com.fanwe.library.utils.SDToast;
import com.fanwe.model.LocalUserModel;
import com.fanwe.model.MediaRewardItemModel;
import com.fanwe.model.MediaRewardPageModel;
import com.fanwe.model.RequestModel;
import com.fanwe.o2o.newo2o.R;
import com.fanwe.utils.SDInterfaceUtil;
import com.handmark.pulltorefresh.library.PullToRefreshBase;
import com.handmark.pulltorefresh.library.PullToRefreshListView;
import com.lidroid.xutils.view.annotation.ViewInject;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;

/**
 * Created by Edison on 2016/7/31.
 * 奖励详情
 */
public class MediaRewardFragment extends BaseFragment {
    private PullToRefreshListView mList;
    private TextView mTvError;

    private int mCurrentPage = 1;
    private int mTotalPage = 0;

    private List<MediaRewardItemModel> mListModel;
    private MediaRewardAdapter mAdapter;
    /*private List<MediaNextLevelItemModel> mListModel;
    private MediaNextLevelAdapter mAdapter;*/
    private int type;
    private View reward_layout, reward_order_layout;
    private int beginY, beginM, beginD, endY, endM, endD;

    @ViewInject(R.id.beginDate)
    private TextView beginDate;

    @ViewInject(R.id.endDate)
    private TextView endDate;

    @ViewInject(R.id.reward_total_money_tip)
    private TextView reward_total_money_tip;

    @ViewInject(R.id.reward_total_money)
    private TextView reward_total_money;

    private long startTime;
    private long endTime;
    private boolean isQuery = false;

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

        reward_layout = findViewById(R.id.reward_layout);
        reward_order_layout = findViewById(R.id.reward_order_layout);

        beginDate.setOnClickListener(this);
        endDate.setOnClickListener(this);
        view.findViewById(R.id.query).setOnClickListener(this);
    }

    @Override
    protected void init() {
        register(getView());
        bindDefaultData();
        initPullView();
        initDate();
    }

    private void initDate() {
        //初始化Calendar日历对象
        Calendar calendar = Calendar.getInstance(Locale.CHINA);
        Date date = new Date(); //获取当前日期Date对象
        calendar.setTime(date);////为Calendar对象设置时间为当前日期

        beginY = calendar.get(Calendar.YEAR); //获取Calendar对象中的年
        beginM = calendar.get(Calendar.MONTH);//获取Calendar对象中的月
        beginD = calendar.get(Calendar.DAY_OF_MONTH);//获取这个月的第几天

        endY = calendar.get(Calendar.YEAR); //获取Calendar对象中的年
        endM = calendar.get(Calendar.MONTH);//获取Calendar对象中的月
        endD = calendar.get(Calendar.DAY_OF_MONTH);//获取这个月的第几天
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.beginDate:
                /**
                 * 构造函数原型：
                 * public DatePickerDialog (Context context, DatePickerDialog.OnDateSetListener callBack,
                 * int year, int monthOfYear, int dayOfMonth)
                 * content组件运行Activity，
                 * DatePickerDialog.OnDateSetListener：选择日期事件
                 * year：当前组件上显示的年，monthOfYear：当前组件上显示的月，dayOfMonth：当前组件上显示的第几天
                 *
                 */
                DatePickerDialog beginPicker = new DatePickerDialog(getContext(), beginDateListener, beginY, beginM, beginD);
                beginPicker.show();
                break;
            case R.id.endDate:
                /**
                 * 构造函数原型：
                 * public DatePickerDialog (Context context, DatePickerDialog.OnDateSetListener callBack,
                 * int year, int monthOfYear, int dayOfMonth)
                 * content组件运行Activity，
                 * DatePickerDialog.OnDateSetListener：选择日期事件
                 * year：当前组件上显示的年，monthOfYear：当前组件上显示的月，dayOfMonth：当前组件上显示的第几天
                 *
                 */
                DatePickerDialog endPicker = new DatePickerDialog(getContext(), endDateListener, endY, endM, endD);
                endPicker.show();
                break;
            case R.id.query:
                mCurrentPage = 1;
                isQuery = true;
                requestNextLevelActIndex(false);
                break;
        }
    }

    private void bindDefaultData() {
        type = getArguments().getInt(Constant.ExtraConstant.EXTRA_TYPE);
        switch (type) {
            case Constant.Reward.ORDER:
                reward_total_money_tip.setText("订单奖励总额：");
                reward_layout.setVisibility(View.GONE);
                break;
            case Constant.Reward.HHR:
                reward_total_money_tip.setText("合伙人招募奖励总额：");
                reward_order_layout.setVisibility(View.GONE);
                ((TextView) reward_layout.findViewById(R.id.labelTitle)).setText("合伙人昵称");
                break;
            case Constant.Reward.HYD:
                reward_total_money_tip.setText("会员店招募奖励总额：");
                reward_order_layout.setVisibility(View.GONE);
                ((TextView) reward_layout.findViewById(R.id.labelTitle)).setText("会员店");
                break;
        }

        mListModel = new ArrayList<>();
        mAdapter = new MediaRewardAdapter(mListModel, getActivity(), type);
        mList.setAdapter(mAdapter);
        mList.setOnItemClickListener(new AdapterView.OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                //SDToast.showToast("position:" + position);
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
        LocalUserModel localUserModel = LocalUserModelDao.queryModel();

        final RequestModel model = new RequestModel();
        switch (type) {
            case Constant.Reward.ORDER:
                model.putCtl("media");
                model.putAct("get_order_data");
                break;
            case Constant.Reward.HHR:
                model.putCtl("media");
                model.putAct("get_partner_data");
                break;
            case Constant.Reward.HYD:
                model.putCtl("media");
                model.putAct("get_memberStore_data");
                break;
        }
        model.put("user_id", localUserModel.getUser_id());
        model.put("page", mCurrentPage);//请求页码
        if (isQuery) {
            model.put("startTime", startTime / 1000);//查询的开始时间
            model.put("endTime", endTime / 1000);//查询的结束时间
        }
        InterfaceServer.getInstance().requestInterface(model, new SDRequestCallBack<MediaRewardPageModel>() {
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
                    if (actModel.getStatus() == 1) {
                        if (actModel.getPage() != null) {
                            mCurrentPage = actModel.getPage().getPage();
                            mTotalPage = actModel.getPage().getPage_total();
                        }

                        if (actModel.getData_list() != null) {
                            if (!isLoadMore) {
                                mListModel.clear();
                            }
                            mListModel.addAll(actModel.getData_list());
                            mAdapter.updateData(mListModel);
                        } else if (mCurrentPage == 1) {
                            //第一页
                            mListModel.clear();
                            mAdapter.updateData(mListModel);
                        }
                        reward_total_money.setText(getContext().getString(R.string.money, actModel.getTotal_num()));
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


    private DatePickerDialog.OnDateSetListener beginDateListener = new DatePickerDialog.OnDateSetListener() {
        /**params：view：该事件关联的组件
         * params：myyear：当前选择的年
         * params：monthOfYear：当前选择的月
         * params：dayOfMonth：当前选择的日
         */
        @Override
        public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
            //修改year、month、day的变量值，以便以后单击按钮时，DatePickerDialog上显示上一次修改后的值
            beginY = year;
            beginM = monthOfYear;
            beginD = dayOfMonth;
            //更新日期
            beginDate.setText(beginY + "-" + (beginM + 1) + "-" + beginD);

            Calendar calendar = Calendar.getInstance();
            calendar.set(Calendar.YEAR, year);
            calendar.set(Calendar.MONTH, monthOfYear);
            calendar.set(Calendar.DAY_OF_MONTH, dayOfMonth);
            calendar.set(Calendar.HOUR_OF_DAY, 0);
            calendar.set(Calendar.MINUTE, 0);
            calendar.set(Calendar.SECOND, 0);
            startTime = calendar.getTimeInMillis();
        }

    };

    private DatePickerDialog.OnDateSetListener endDateListener = new DatePickerDialog.OnDateSetListener() {
        /**params：view：该事件关联的组件
         * params：myyear：当前选择的年
         * params：monthOfYear：当前选择的月
         * params：dayOfMonth：当前选择的日
         */
        @Override
        public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
            //修改year、month、day的变量值，以便以后单击按钮时，DatePickerDialog上显示上一次修改后的值
            endY = year;
            endM = monthOfYear;
            endD = dayOfMonth;
            //更新日期
            endDate.setText(endY + "-" + (endM + 1) + "-" + endD);

            Calendar calendar = Calendar.getInstance();
            calendar.set(Calendar.YEAR, year);
            calendar.set(Calendar.MONTH, monthOfYear);
            calendar.set(Calendar.DAY_OF_MONTH, dayOfMonth);
            calendar.set(Calendar.HOUR_OF_DAY, 0);
            calendar.set(Calendar.MINUTE, 0);
            calendar.set(Calendar.SECOND, 0);
            endTime = calendar.getTimeInMillis();
        }
    };
}