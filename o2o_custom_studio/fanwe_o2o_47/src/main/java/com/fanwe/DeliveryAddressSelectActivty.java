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

import com.alibaba.fastjson.JSONObject;
import com.fanwe.adapter.DeliveryAddressAdapter;
import com.fanwe.common.CommonInterface;
import com.fanwe.constant.Constant.TitleType;
import com.fanwe.event.EnumEventTag;
import com.fanwe.http.InterfaceServer;
import com.fanwe.http.SDRequestCallBack;
import com.fanwe.library.dialog.SDDialogManager;
import com.fanwe.library.title.SDTitleItem;
import com.fanwe.library.utils.SDViewUtil;
import com.fanwe.model.Consignee_infoModel;
import com.fanwe.model.RequestModel;
import com.fanwe.model.Uc_address_indexActModel;
import com.fanwe.o2o.newo2o.R;
import com.fanwe.work.AppRuntimeWorker;
import com.handmark.pulltorefresh.library.PullToRefreshBase;
import com.handmark.pulltorefresh.library.PullToRefreshBase.OnRefreshListener2;
import com.handmark.pulltorefresh.library.PullToRefreshListView;
import com.lidroid.xutils.exception.HttpException;
import com.lidroid.xutils.http.ResponseInfo;
import com.lidroid.xutils.view.annotation.ViewInject;
import com.sunday.eventbus.SDBaseEvent;
import com.sunday.eventbus.SDEventManager;

/**
 * 选择配送地址
 * 
 * @author js02
 * 
 */
public class DeliveryAddressSelectActivty extends BaseActivity
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
		setContentView(R.layout.act_delivery_address_select);
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
			}
		});
		mLvAddress.setOnItemClickListener(new OnItemClickListener()
		{

			@Override
			public void onItemClick(AdapterView<?> arg0, View arg1, int position, long id)
			{
				requestBindDefaultAddress(mAdapter.getItem((int) id), true);
			}
		});
		mLvAddress.setRefreshing();
	}

	protected void requestBindDefaultAddress(final Consignee_infoModel item, final boolean finishWhenSuccess)
	{
		if (item == null)
		{
			return;
		}

		CommonInterface.requestBindDefaultAddress(item.getId(), new SDRequestCallBack<JSONObject>()
		{

			@Override
			public void onSuccess(ResponseInfo<String> responseInfo)
			{
				int status = actModel.getIntValue("set_status");
				if (status == 1)
				{
					if (finishWhenSuccess)
					{
						setDefaultAddressSuccess(item);
					}
				}
			}

			@Override
			public void onStart()
			{
				SDDialogManager.showProgressDialog("请稍候...");
			}

			@Override
			public void onFinish()
			{
				SDDialogManager.dismissProgressDialog();
			}

			@Override
			public void onFailure(HttpException error, String msg)
			{
			}
		});
	}

	protected void setDefaultAddressSuccess(Consignee_infoModel item)
	{
		SDEventManager.post(EnumEventTag.USER_DELIVERY_CHANGE.ordinal());
		finish();
	}

	private void initTitle()
	{
		mTitle.setMiddleTextTop("选择配送地址");
		mTitle.initRightItem(1);
		mTitle.getItemRight(0).setTextBot("管理");
	}

	@Override
	public void onCLickRight_SDTitleSimple(SDTitleItem v, int index)
	{
		Intent intent = new Intent(getApplicationContext(), DeliveryAddressManageActivty.class);
		startActivity(intent);
	}

	private void bindDefaultData()
	{
		mAdapter = new DeliveryAddressAdapter(mListModel, false, this);
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