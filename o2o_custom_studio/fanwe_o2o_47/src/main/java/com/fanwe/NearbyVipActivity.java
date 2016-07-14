package com.fanwe;

import java.util.ArrayList;
import java.util.List;

import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ListView;

import com.baidu.location.BDLocation;
import com.baidu.location.BDLocationListener;
import com.fanwe.adapter.NearbyVipAdapter;
import com.fanwe.baidumap.BaiduMapManager;
import com.fanwe.constant.Constant.TitleType;
import com.fanwe.http.InterfaceServer;
import com.fanwe.http.SDRequestCallBack;
import com.fanwe.library.dialog.SDDialogManager;
import com.fanwe.library.utils.SDToast;
import com.fanwe.model.NearbyuserActItemModel;
import com.fanwe.model.NearbyuserActModel;
import com.fanwe.model.PageModel;
import com.fanwe.model.RequestModel;
import com.fanwe.o2o.newo2o.R;
import com.fanwe.work.AppRuntimeWorker;
import com.handmark.pulltorefresh.library.PullToRefreshBase;
import com.handmark.pulltorefresh.library.PullToRefreshBase.Mode;
import com.handmark.pulltorefresh.library.PullToRefreshBase.OnRefreshListener2;
import com.handmark.pulltorefresh.library.PullToRefreshListView;
import com.lidroid.xutils.exception.HttpException;
import com.lidroid.xutils.http.ResponseInfo;
import com.lidroid.xutils.view.annotation.ViewInject;

public class NearbyVipActivity extends BaseActivity
{
	public static final int REQUEST_CODE_LOGIN_FOR_FOLLOW_FANS = 1;

	@ViewInject(R.id.act_nearby_vip_lv_vip)
	private PullToRefreshListView mLvVip = null;

	private List<NearbyuserActItemModel> mlistModel = new ArrayList<NearbyuserActItemModel>();
	private NearbyVipAdapter mAdapter = null;

	private PageModel mPage = new PageModel();

	@Override
	protected void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
		setmTitleType(TitleType.TITLE);
		setContentView(R.layout.act_nearby_vip);
		init();
	}

	private void init()
	{
		initTitle();
		bindDefaultData();
		initPullListView();
		startLocation();
	}

	private void bindDefaultData()
	{
		mAdapter = new NearbyVipAdapter(mlistModel, this);
		mLvVip.setAdapter(mAdapter);
		mLvVip.setOnItemClickListener(new OnItemClickListener()
		{

			@Override
			public void onItemClick(AdapterView<?> parent, View view, int position, long id)
			{

			}

		});
	}

	/**
	 * 开始定位
	 */
	private void startLocation()
	{
		SDDialogManager.showProgressDialog("定位中...");
		BaiduMapManager.getInstance().startLocation(new BDLocationListener()
		{
			@Override
			public void onReceiveLocation(BDLocation location)
			{
				if (location != null) // 定位成功
				{
					requestNearByVip(false);
				} else
				{
					SDToast.showToast("定位失败无法获取附近的会员...");
					SDDialogManager.dismissProgressDialog();
				}
			}
		});
	}

	protected void requestNearByVip(final boolean isLoadMore)
	{

		RequestModel model = new RequestModel();
		model.putCtl("nearbyuser");
		model.put("city_id", AppRuntimeWorker.getCity_id());
		model.putUser();
		model.putLocation();
		model.putPage(mPage.getPage());
		SDRequestCallBack<NearbyuserActModel> handler = new SDRequestCallBack<NearbyuserActModel>()
		{

			@Override
			public void onStart()
			{
				SDDialogManager.showProgressDialog("请求数据中...");
			}

			@Override
			public void onSuccess(ResponseInfo<String> responseInfo)
			{
				if (actModel.getStatus() == 1)
				{
					mPage.update(actModel.getPage());
					if (!isLoadMore)
					{
						mlistModel.clear();
					}
					if (actModel.getItem() != null && !actModel.getItem().isEmpty())
					{
						mlistModel.addAll(actModel.getItem());
					} else
					{
						SDToast.showToast("未找到数据");
					}
					mAdapter.updateData(mlistModel);
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
				mLvVip.onRefreshComplete();
			}
		};

		InterfaceServer.getInstance().requestInterface(model, handler);

	}

	private void initPullListView()
	{
		mLvVip.setMode(Mode.BOTH);
		mLvVip.setOnRefreshListener(new OnRefreshListener2<ListView>()
		{

			@Override
			public void onPullDownToRefresh(PullToRefreshBase<ListView> refreshView)
			{
				mPage.resetPage();
				requestNearByVip(false);
			}

			@Override
			public void onPullUpToRefresh(PullToRefreshBase<ListView> refreshView)
			{
				if (!mPage.increment())
				{
					SDToast.showToast("没有更多数据了");
					mLvVip.onRefreshComplete();
				} else
				{
					requestNearByVip(true);
				}
			}
		});
	}

	private void initTitle()
	{
		mTitle.setMiddleTextTop("附近的会员");
	}

}