package com.fanwe;

import java.util.ArrayList;
import java.util.List;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ListView;
import android.widget.TextView;

import com.fanwe.adapter.DeliveryAddressAdapter;
import com.fanwe.constant.Constant.TitleType;
import com.fanwe.event.EnumEventTag;
import com.fanwe.http.InterfaceServer;
import com.fanwe.http.SDRequestCallBack;
import com.fanwe.library.dialog.SDDialogManager;
import com.fanwe.library.utils.SDViewUtil;
import com.fanwe.model.Consignee_infoModel;
import com.fanwe.model.RequestModel;
import com.fanwe.model.Uc_address_indexActModel;
import com.fanwe.o2o.newo2o.R;
import com.fanwe.work.AppRuntimeWorker;
import com.handmark.pulltorefresh.library.PullToRefreshBase;
import com.handmark.pulltorefresh.library.PullToRefreshBase.OnRefreshListener2;
import com.handmark.pulltorefresh.library.PullToRefreshListView;
import com.lidroid.xutils.http.ResponseInfo;
import com.lidroid.xutils.view.annotation.ViewInject;
import com.sunday.eventbus.SDBaseEvent;

/**
 * 选择配送地址
 * 
 * @author js02
 * 
 */
public class DeliveryAddressManageActivty extends BaseActivity
{

	@ViewInject(R.id.act_delivery_address_tv_add_address)
	private TextView mTvAddAddress;

	@ViewInject(R.id.act_delivery_address_lv_address)
	private PullToRefreshListView mLvAddress;

	private List<Consignee_infoModel> mListModel = new ArrayList<Consignee_infoModel>();
	private DeliveryAddressAdapter mAdapter;

	@Override
	protected void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
		setmTitleType(TitleType.TITLE);
		setContentView(R.layout.act_delivery_address_manage);
		init();
	}

	private void init()
	{
		initTitle();
		registeClick();
		bindDefaultData();
		initPullToRefreshListView();
	}

	private void initPullToRefreshListView()
	{
		mLvAddress.setMode(com.handmark.pulltorefresh.library.PullToRefreshBase.Mode.PULL_FROM_START);
		mLvAddress.setOnRefreshListener(new OnRefreshListener2<ListView>()
		{

			@Override
			public void onPullDownToRefresh(PullToRefreshBase<ListView> refreshView)
			{
				requestUsersAddress(false);
			}

			@Override
			public void onPullUpToRefresh(PullToRefreshBase<ListView> refreshView)
			{
				// TODO Auto-generated method stub
			}
		});
		mLvAddress.setOnItemClickListener(new OnItemClickListener()
		{

			@Override
			public void onItemClick(AdapterView<?> arg0, View arg1, int position, long id)
			{
				// TODO 管理当前收货地址
				Consignee_infoModel model = mAdapter.getItem((int) id);
				if (model != null)
				{
					Intent intent = new Intent(getApplicationContext(), AddDeliveryAddressActivity.class);
					intent.putExtra(AddDeliveryAddressActivity.EXTRA_DELIVERY_MODEL, model);
					startActivity(intent);
				}
			}
		});
		mLvAddress.setRefreshing();
	}

	private void initTitle()
	{
		mTitle.setMiddleTextTop("配送地址");
	}

	private void bindDefaultData()
	{
		mAdapter = new DeliveryAddressAdapter(mListModel, true, this);
		mLvAddress.setAdapter(mAdapter);
	}

	/**
	 * 请求用户地址
	 */
	private void requestUsersAddress(final boolean isLoadMore)
	{
		if (AppRuntimeWorker.isLogin())
		{
			RequestModel model = new RequestModel();
			model.putCtl("uc_address");
			model.putUser();
			SDRequestCallBack<Uc_address_indexActModel> handler = new SDRequestCallBack<Uc_address_indexActModel>()
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
						SDViewUtil.updateAdapterByList(mListModel, actModel.getConsignee_list(), mAdapter, isLoadMore, "未找到配送地址", "未找到更多数据");
					}
				}

				@Override
				public void onFinish()
				{
					SDDialogManager.dismissProgressDialog();
					mLvAddress.onRefreshComplete();
				}
			};
			InterfaceServer.getInstance().requestInterface(model, handler);
		} else
		{
			startActivity(new Intent(getApplicationContext(), LoginActivity.class));
		}

	}

	private void registeClick()
	{
		mTvAddAddress.setOnClickListener(this);

	}

	@Override
	public void onClick(View v)
	{
		switch (v.getId())
		{
		case R.id.act_delivery_address_tv_add_address:
			clickAddAddress();
			break;

		default:
			break;
		}
	}

	/**
	 * 添加地址
	 */
	private void clickAddAddress()
	{
		// TODO 跳到添加收货地址界面
		startAddAddressActivity();
	}

	private void startAddAddressActivity()
	{
		Intent intent = new Intent(getApplicationContext(), AddDeliveryAddressActivity.class);
		startActivity(intent);
	}

	@Override
	protected void onNeedRefreshOnResume()
	{
		requestUsersAddress(false);
		super.onNeedRefreshOnResume();
	}

	@Override
	public void onEventMainThread(SDBaseEvent event)
	{
		super.onEventMainThread(event);
		switch (EnumEventTag.valueOf(event.getTagInt()))
		{
		case USER_DELIVERY_CHANGE:
			setmIsNeedRefreshOnResume(true);
			break;

		default:
			break;
		}
	}

}