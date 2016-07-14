package com.fanwe;

import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.baidu.mapapi.model.LatLng;
import com.baidu.mapapi.search.geocode.GeoCodeResult;
import com.baidu.mapapi.search.geocode.OnGetGeoCoderResultListener;
import com.baidu.mapapi.search.geocode.ReverseGeoCodeResult;
import com.baidu.mapapi.search.route.DrivingRouteResult;
import com.baidu.mapapi.search.route.TransitRouteResult;
import com.baidu.mapapi.search.route.WalkingRouteResult;
import com.fanwe.baidumap.BaiduGeoCoder;
import com.fanwe.baidumap.BaiduMapManager;
import com.fanwe.baidumap.BaiduMapManager.OnGetBusRoutePlanResultListener;
import com.fanwe.baidumap.BaiduMapManager.OnGetDrivingRoutePlanResultListener;
import com.fanwe.baidumap.BaiduMapManager.OnGetWalkingRoutePlanResultListener;
import com.fanwe.constant.Constant.TitleType;
import com.fanwe.fragment.RouteinfoTabBusFragment;
import com.fanwe.fragment.RouteinfoTabDrivingFragment;
import com.fanwe.fragment.RouteinfoTabWalkingFragment;
import com.fanwe.library.customview.SDTabItemImage;
import com.fanwe.library.customview.SDViewBase;
import com.fanwe.library.customview.SDViewNavigatorManager;
import com.fanwe.library.customview.SDViewNavigatorManager.SDViewNavigatorManagerListener;
import com.fanwe.library.dialog.SDDialogManager;
import com.fanwe.library.dialog.SDDialogProgress;
import com.fanwe.library.dialog.SDDialogProgress.SDDialogProgressListener;
import com.fanwe.library.utils.SDToast;
import com.fanwe.o2o.newo2o.R;
import com.lidroid.xutils.view.annotation.ViewInject;

/**
 * 线路查询
 * 
 * @author Administrator
 * 
 */
public class RouteInformationActivity extends BaseActivity
{
	private static final double EMPTY_NUMBER = -9999;

	/** 起点纬度，double类型 */
	public static final String EXTRA_START_LAT = "extra_start_lat";
	/** 起点经度，double类型 */
	public static final String EXTRA_START_LON = "extra_start_lon";
	/** 终点纬度，double类型 */
	public static final String EXTRA_END_LAT = "extra_end_lat";
	/** 终点经度，double类型 */
	public static final String EXTRA_END_LON = "extra_end_lon";
	/** 起点名称，String类型 */
	public static final String EXTRA_START_NAME = "extra_start_name";
	/** 终点名称，String类型 */
	public static final String EXTRA_END_NAME = "extra_end_name";

	private String mStrStartName = "我的位置";
	private String mStrEndName = "";

	private LatLng mLlStart;
	private LatLng mLlEnd;

	private LinearLayout mViewMyLocation;
	private LinearLayout mViewEndLocation;

	@ViewInject(R.id.act_route_info_ll_first_location)
	private LinearLayout mLlLocationFirst;

	@ViewInject(R.id.act_route_info_ll_second_location)
	private LinearLayout mLlLocationSecond;

	private LinearLayout mLLMyLocation;
	private LinearLayout mLLEndLocation;

	private TextView mTvMyLocation;
	private TextView mTvEndLocation;

	private SDViewNavigatorManager mViewManager = new SDViewNavigatorManager();

	@ViewInject(R.id.act_route_info_iv_swap)
	private ImageView mIvSwap;

	@ViewInject(R.id.act_routeinfo_tab_left)
	private SDTabItemImage mTabLeft;

	@ViewInject(R.id.act_routeinfo_tab_center)
	private SDTabItemImage mTabCenter;

	@ViewInject(R.id.act_routeinfo_tab_right)
	private SDTabItemImage mTabRight;

	private boolean isGetBusRouteFinish = true;
	private boolean isGetDrivingRouteFinish = true;
	private boolean isGetWalkingRouteFinish = true;

	private RouteinfoTabBusFragment mFragBusRoute;
	private RouteinfoTabDrivingFragment mFragDrivingRoute;
	private RouteinfoTabWalkingFragment mFragWalkRoute;

	private BaiduGeoCoder mGeoCoder = new BaiduGeoCoder();

	@Override
	protected void onCreate(Bundle savedInstanceState)
	{
		setmTitleType(TitleType.TITLE);
		super.onCreate(savedInstanceState);
		setContentView(R.layout.act_route_info);
		init();
	}

	private void init()
	{
		getIntentData();
		initTitle();
		initLocations();
		registerClick();
		initTab();
	}

	private void getIntentData()
	{
		double startLat = getIntent().getDoubleExtra(EXTRA_START_LAT, EMPTY_NUMBER);
		double startLon = getIntent().getDoubleExtra(EXTRA_START_LON, EMPTY_NUMBER);

		double endLat = getIntent().getDoubleExtra(EXTRA_END_LAT, EMPTY_NUMBER);
		double endLon = getIntent().getDoubleExtra(EXTRA_END_LON, EMPTY_NUMBER);

		if (startLat == EMPTY_NUMBER || startLon == EMPTY_NUMBER)
		{
			mLlStart = new LatLng(BaiduMapManager.getInstance().getLatitude(), BaiduMapManager.getInstance().getLongitude());
		} else
		{
			mLlStart = new LatLng(startLat, startLon);
		}

		if (endLat == EMPTY_NUMBER || endLon == EMPTY_NUMBER)
		{
			SDToast.showToast("未找到终点位置信息");
			finish();
			return;
		}

		mLlEnd = new LatLng(endLat, endLon);

		String strStart = getIntent().getStringExtra(EXTRA_START_NAME);
		String strEnd = getIntent().getStringExtra(EXTRA_END_NAME);

		if (!TextUtils.isEmpty(strStart))
		{
			mStrStartName = strStart;
		}

		if (!TextUtils.isEmpty(strEnd))
		{
			mStrEndName = strEnd;
		} else
		{
			mGeoCoder.listener(new OnGetGeoCoderResultListener()
			{

				@Override
				public void onGetReverseGeoCodeResult(ReverseGeoCodeResult result)
				{
					if (result != null)
					{
						mStrEndName = result.getAddress();
						mTvEndLocation.setText(mStrEndName);
					}
				}

				@Override
				public void onGetGeoCodeResult(GeoCodeResult arg0)
				{

				}
			}).location(mLlEnd).reverseGeoCode();
		}
	}

	private void registerClick()
	{
		mLLMyLocation.setOnClickListener(this);
		mLLEndLocation.setOnClickListener(this);
		mIvSwap.setOnClickListener(this);
	}

	private void initLocations()
	{
		mViewMyLocation = (LinearLayout) getLayoutInflater().inflate(R.layout.include_map_location_mine, null);
		mLlLocationFirst.addView(mViewMyLocation);

		mViewEndLocation = (LinearLayout) getLayoutInflater().inflate(R.layout.include_map_location_end, null);
		mLlLocationSecond.addView(mViewEndLocation);

		mLLMyLocation = (LinearLayout) mViewMyLocation.findViewById(R.id.include_map_location_mine_ll_location);
		mLLEndLocation = (LinearLayout) mViewEndLocation.findViewById(R.id.include_map_location_end_ll_location);

		mTvMyLocation = (TextView) mViewMyLocation.findViewById(R.id.include_map_location_mine_tv_location);
		mTvEndLocation = (TextView) mViewEndLocation.findViewById(R.id.include_map_location_end_tv_location);

		mTvMyLocation.setText(mStrStartName);
		mTvEndLocation.setText(mStrEndName);
	}

	private void initTitle()
	{
		mTitle.setMiddleTextTop("查看线路");
	}

	private void initTab()
	{

		mTabLeft.getmAttr().setmImageNormalResId(R.drawable.ic_act_routeinfo_tableft_normal);
		mTabLeft.getmAttr().setmImageSelectedResId(R.drawable.ic_act_routeinfo_tableft_click);

		mTabCenter.getmAttr().setmImageNormalResId(R.drawable.ic_act_routeinfo_tabcenter_normal);
		mTabCenter.getmAttr().setmImageSelectedResId(R.drawable.ic_act_routeinfo_tabcenter_click);

		mTabRight.getmAttr().setmImageNormalResId(R.drawable.ic_act_routeinfo_tabright_normal);
		mTabRight.getmAttr().setmImageSelectedResId(R.drawable.ic_act_routeinfo_tabright_click);

		mViewManager.setItems(new SDViewBase[] { mTabLeft, mTabCenter, mTabRight });
		mViewManager.setmListener(new SDViewNavigatorManagerListener()
		{
			@Override
			public void onItemClick(View v, int index)
			{

				switch (index)
				{
				case 0:
					clickBus();
					break;
				case 1:
					clickDriving();
					break;
				case 2:
					clickWalking();
					break;

				default:
					break;
				}
			}

		});

		mViewManager.setSelectIndex(0, mTabLeft, true);
	}

	private void clickBus()
	{
		if (mFragBusRoute == null)
		{
			mFragBusRoute = new RouteinfoTabBusFragment();
			mFragBusRoute.setmLlStart(mLlStart);
			mFragBusRoute.setmLlEnd(mLlEnd);
			mFragBusRoute.setmListener(new OnGetBusRoutePlanResultListener()
			{

				@Override
				public void onResult(TransitRouteResult result, boolean success)
				{

				}

				@Override
				public void onFinish()
				{
					isGetBusRouteFinish = true;
					dealFinish();
				}
			});
		}
		getSDFragmentManager().toggle(R.id.act_route_info_fl_content, null, mFragBusRoute);
	}

	private void clickDriving()
	{
		if (mFragDrivingRoute == null)
		{
			mFragDrivingRoute = new RouteinfoTabDrivingFragment();
			mFragDrivingRoute.setmLlStart(mLlStart);
			mFragDrivingRoute.setmLlEnd(mLlEnd);
			mFragDrivingRoute.setmListener(new OnGetDrivingRoutePlanResultListener()
			{

				@Override
				public void onResult(DrivingRouteResult result, boolean success)
				{

				}

				@Override
				public void onFinish()
				{
					isGetDrivingRouteFinish = true;
					dealFinish();
				}
			});
		}
		getSDFragmentManager().toggle(R.id.act_route_info_fl_content, null, mFragDrivingRoute);
	}

	private void clickWalking()
	{
		if (mFragWalkRoute == null)
		{
			mFragWalkRoute = new RouteinfoTabWalkingFragment();
			mFragWalkRoute.setmLlStart(mLlStart);
			mFragWalkRoute.setmLlEnd(mLlEnd);
			mFragWalkRoute.setmListener(new OnGetWalkingRoutePlanResultListener()
			{

				@Override
				public void onResult(WalkingRouteResult result, boolean success)
				{

				}

				@Override
				public void onFinish()
				{
					isGetWalkingRouteFinish = true;
					dealFinish();
				}
			});
		}
		getSDFragmentManager().toggle(R.id.act_route_info_fl_content, null, mFragWalkRoute);
	}

	@Override
	public void onClick(View view)
	{
		switch (view.getId())
		{
		case R.id.act_route_info_iv_swap:
			clickSwap();
			break;
		case R.id.include_map_location_mine_ll_location:
			clickStartLocation();
			break;
		case R.id.include_map_location_end_ll_location:
			clickEndLocation();
			break;

		default:
			break;
		}

	}

	protected void dealFinish()
	{
		if (isGetBusRouteFinish && isGetDrivingRouteFinish && isGetWalkingRouteFinish)
		{
			SDDialogManager.dismissProgressDialog();
		}
	}

	private void clickSwap()
	{

		showProgressDialog();
		swapView();
		swapLocation();

		if (mFragBusRoute != null)
		{
			isGetBusRouteFinish = false;
			mFragBusRoute.setmLlStart(mLlStart);
			mFragBusRoute.setmLlEnd(mLlEnd);
			mFragBusRoute.refreshData();
		} else
		{
			isGetBusRouteFinish = true;
		}

		if (mFragDrivingRoute != null)
		{
			isGetDrivingRouteFinish = false;
			mFragDrivingRoute.setmLlStart(mLlStart);
			mFragDrivingRoute.setmLlEnd(mLlEnd);
			mFragDrivingRoute.refreshData();
		} else
		{
			isGetDrivingRouteFinish = true;
		}

		if (mFragWalkRoute != null)
		{
			isGetWalkingRouteFinish = false;
			mFragWalkRoute.setmLlStart(mLlStart);
			mFragWalkRoute.setmLlEnd(mLlEnd);
			mFragWalkRoute.refreshData();
		} else
		{
			isGetWalkingRouteFinish = true;
		}
	}

	private void showProgressDialog()
	{
		SDDialogProgress dialog = SDDialogManager.showProgressDialog("正在获取...");
		dialog.setCancelable(true);
		dialog.setmListener(new SDDialogProgressListener()
		{

			@Override
			public void onDismiss(SDDialogProgress dialog)
			{
				if (mFragBusRoute != null)
				{
					mFragBusRoute.destroyRoutePlanSearch();
					mFragBusRoute.stopRefresh();
				}
				if (mFragDrivingRoute != null)
				{
					mFragDrivingRoute.destroyRoutePlanSearch();
					mFragDrivingRoute.stopRefresh();
				}
				if (mFragWalkRoute != null)
				{
					mFragWalkRoute.destroyRoutePlanSearch();
					mFragWalkRoute.stopRefresh();
				}
			}
		});
	}

	private void swapLocation()
	{
		LatLng llTemp = mLlStart;
		mLlStart = mLlEnd;
		mLlEnd = llTemp;
	}

	private void swapView()
	{
		View myChild = mLlLocationFirst.getChildAt(0);
		if (myChild == mViewMyLocation)
		{
			mLlLocationFirst.removeAllViews();
			mLlLocationSecond.removeAllViews();

			mLlLocationFirst.addView(mViewEndLocation);
			mLlLocationSecond.addView(mViewMyLocation);
		} else if (myChild == mViewEndLocation)
		{
			mLlLocationFirst.removeAllViews();
			mLlLocationSecond.removeAllViews();

			mLlLocationFirst.addView(mViewMyLocation);
			mLlLocationSecond.addView(mViewEndLocation);
		}
	}

	private void clickStartLocation()
	{
		// TODO Auto-generated method stub

	}

	private void clickEndLocation()
	{
		// TODO Auto-generated method stub

	}

	@Override
	protected void onDestroy()
	{
		mGeoCoder.destroy();
		super.onDestroy();
	}

}
