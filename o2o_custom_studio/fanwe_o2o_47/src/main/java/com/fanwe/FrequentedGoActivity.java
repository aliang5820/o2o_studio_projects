package com.fanwe;

import java.util.ArrayList;
import java.util.List;

import android.os.Bundle;
import android.widget.ImageView;
import android.widget.ListView;

import com.fanwe.adapter.FrequentedGoAdapter;
import com.fanwe.adapter.FrequentedGoAdapter.SubscribeMessageAdapterListener;
import com.fanwe.constant.Constant.TitleType;
import com.fanwe.dao.LocalUserModelDao;
import com.fanwe.http.InterfaceServer;
import com.fanwe.http.SDRequestCallBack;
import com.fanwe.library.dialog.SDDialogManager;
import com.fanwe.library.utils.SDToast;
import com.fanwe.library.utils.SDViewUtil;
import com.fanwe.model.LocalUserModel;
import com.fanwe.model.RequestModel;
import com.fanwe.model.UserfrequentedlistActItemModel;
import com.fanwe.model.UserfrequentedlistActModel;
import com.fanwe.o2o.newo2o.R;
import com.handmark.pulltorefresh.library.PullToRefreshBase;
import com.handmark.pulltorefresh.library.PullToRefreshBase.Mode;
import com.handmark.pulltorefresh.library.PullToRefreshBase.OnRefreshListener2;
import com.handmark.pulltorefresh.library.PullToRefreshListView;
import com.lidroid.xutils.exception.HttpException;
import com.lidroid.xutils.http.ResponseInfo;
import com.lidroid.xutils.view.annotation.ViewInject;

/**
 * 常去地点
 * 
 * @author js02
 * 
 */
public class FrequentedGoActivity extends BaseActivity
{

	@ViewInject(R.id.act_frequented_go_ptrlv_content)
	private PullToRefreshListView mPtrlvContent = null;

	@ViewInject(R.id.act_frequented_go_iv_empty)
	private ImageView mIvEmpty = null;

	private FrequentedGoAdapter mAdapter = null;

	private List<UserfrequentedlistActItemModel> mListModel = new ArrayList<UserfrequentedlistActItemModel>();

	private String mDeleteId = "0";

	@Override
	protected void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
		setmTitleType(TitleType.TITLE);
		setContentView(R.layout.act_frequented_go);
		init();
	}

	private void init()
	{
		initTitle();
		bindDefaultData();
		initPullToRefreshListView();
	}

	private void bindDefaultData()
	{
		mAdapter = new FrequentedGoAdapter(mListModel, this, new SubscribeMessageAdapterListener()
		{
			@Override
			public void onDeleteClick(UserfrequentedlistActItemModel model)
			{
				if (model != null && model.getId() != null)
				{
					mDeleteId = model.getId();
					requestUserFrequentedList();
				}

			}
		});
		mPtrlvContent.setAdapter(mAdapter);
	}

	private void initPullToRefreshListView()
	{
		mPtrlvContent.setMode(Mode.PULL_FROM_START);
		mPtrlvContent.setOnRefreshListener(new OnRefreshListener2<ListView>()
		{
			@Override
			public void onPullDownToRefresh(PullToRefreshBase<ListView> refreshView)
			{
				requestUserFrequentedList();
			}

			@Override
			public void onPullUpToRefresh(PullToRefreshBase<ListView> refreshView)
			{
			}
		});

		mPtrlvContent.setRefreshing();
	}

	private void requestUserFrequentedList()
	{
		LocalUserModel user = LocalUserModelDao.queryModel();
		if (user != null)
		{
			RequestModel model = new RequestModel();
			model.putCtl("userfrequentedlist");
			model.put("email", user.getUser_name());
			model.put("pwd", user.getUser_pwd());
			model.put("del_id", mDeleteId);
			SDRequestCallBack<UserfrequentedlistActModel> handler = new SDRequestCallBack<UserfrequentedlistActModel>()
			{

				@Override
				public void onStart()
				{
					SDDialogManager.showProgressDialog("请稍候...");
				}

				@Override
				public void onSuccess(ResponseInfo<String> responseInfo)
				{
					if (actModel.getStatus() == 1)
					{
						if (actModel.getItem() != null && actModel.getItem().size() > 0)
						{
							mListModel.clear();
							mListModel.addAll(actModel.getItem());
						} else
						{
							mListModel.clear();
							SDToast.showToast("未找到数据");
						}
						mAdapter.updateData(mListModel);
					}
				}

				@Override
				public void onFailure(HttpException error, String msg)
				{

				}

				@Override
				public void onFinish()
				{
					SDDialogManager.dismissProgressDialog();
					mPtrlvContent.onRefreshComplete();
					SDViewUtil.toggleEmptyMsgByList(mListModel, mIvEmpty);
				}
			};
			InterfaceServer.getInstance().requestInterface(model, handler);
		}

	}

	private void initTitle()
	{
		mTitle.setMiddleTextTop("常去地点");
		mTitle.initRightItem(1);
		mTitle.getItemRight(0).setTextBot("附近");
	}

}