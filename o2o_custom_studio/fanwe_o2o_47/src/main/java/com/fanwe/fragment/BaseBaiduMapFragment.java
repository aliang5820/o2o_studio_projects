package com.fanwe.fragment;

import android.graphics.Bitmap;
import android.graphics.Point;
import android.os.Bundle;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ZoomControls;

import com.baidu.location.BDLocation;
import com.baidu.location.BDLocationListener;
import com.baidu.mapapi.map.BaiduMap;
import com.baidu.mapapi.map.BaiduMap.OnMapClickListener;
import com.baidu.mapapi.map.BaiduMap.OnMapLoadedCallback;
import com.baidu.mapapi.map.BaiduMap.OnMapStatusChangeListener;
import com.baidu.mapapi.map.BaiduMap.OnMarkerClickListener;
import com.baidu.mapapi.map.BitmapDescriptor;
import com.baidu.mapapi.map.BitmapDescriptorFactory;
import com.baidu.mapapi.map.InfoWindow;
import com.baidu.mapapi.map.MapPoi;
import com.baidu.mapapi.map.MapStatus;
import com.baidu.mapapi.map.MapStatusUpdate;
import com.baidu.mapapi.map.MapStatusUpdateFactory;
import com.baidu.mapapi.map.MapView;
import com.baidu.mapapi.map.Marker;
import com.baidu.mapapi.map.MarkerOptions;
import com.baidu.mapapi.map.MyLocationConfiguration;
import com.baidu.mapapi.map.MyLocationConfiguration.LocationMode;
import com.baidu.mapapi.map.MyLocationData;
import com.baidu.mapapi.map.Overlay;
import com.baidu.mapapi.model.LatLng;
import com.baidu.mapapi.overlayutil.DrivingRouteOverlay;
import com.baidu.mapapi.overlayutil.TransitRouteOverlay;
import com.baidu.mapapi.overlayutil.WalkingRouteOverlay;
import com.baidu.mapapi.search.route.DrivingRouteLine;
import com.baidu.mapapi.search.route.DrivingRouteResult;
import com.baidu.mapapi.search.route.OnGetRoutePlanResultListener;
import com.baidu.mapapi.search.route.TransitRouteLine;
import com.baidu.mapapi.search.route.TransitRouteResult;
import com.baidu.mapapi.search.route.WalkingRouteLine;
import com.baidu.mapapi.search.route.WalkingRouteResult;
import com.fanwe.baidumap.BaiduMapManager;
import com.fanwe.library.utils.SDToast;
import com.fanwe.library.utils.SDViewUtil;

/**
 * 百度地图基类
 * 
 * @author js02
 * 
 */
public class BaseBaiduMapFragment extends BaseFragment implements OnMapStatusChangeListener, OnMapClickListener, OnMarkerClickListener,
		BDLocationListener, OnGetRoutePlanResultListener, OnMapLoadedCallback
{

	private MapView mMapView;
	private BaiduMap mBaiduMap;

	private LatLng mLlTopLeft;
	private LatLng mLlBottomRight;
	private LatLng mLlCurrent;

	/** 是否需要移动地图到当前位置 */
	private boolean mIsNeedAnimateMap = true;
	/** 是否需要一直定位 */
	private boolean mIsLocationAllTheTime = false;

	/**
	 * 获取当前经纬度
	 * 
	 * @return
	 */
	public LatLng getLatLngCurrent()
	{
		if (mLlCurrent == null)
		{
			mLlCurrent = BaiduMapManager.getInstance().getLatLngCurrent();
		}
		if (mLlCurrent == null)
		{
			startLocation(false);
		}
		return mLlCurrent;
	}

	/**
	 * 获取屏幕左上角经纬度
	 * 
	 * @return
	 */
	public LatLng getLatLngTopLeft()
	{
		return mLlTopLeft;
	}

	/**
	 * 获取屏幕右下角经纬度
	 * 
	 * @return
	 */
	public LatLng getLatLngBottomRight()
	{
		return mLlBottomRight;
	}

	public View wrapperView(View resView)
	{
		initBaseBaiduMap();
		View viewFinal = null;
		FrameLayout flLayout = new FrameLayout(getActivity());
		flLayout.addView(mMapView);
		if (resView != null)
		{
			flLayout.addView(resView);
		}
		viewFinal = flLayout;
		return viewFinal;
	}

	public View wrapperView(int resViewId)
	{
		View resView = null;
		if (resViewId != 0)
		{
			resView = getActivity().getLayoutInflater().inflate(resViewId, null);
		}
		return wrapperView(resView);
	}

	@Override
	protected View setContentView(View view)
	{
		return super.setContentView(wrapperView(view));
	}

	private void initBaseBaiduMap()
	{
		initBaiduMap();
	}

	private void initBaiduMap()
	{
		mMapView = new MapView(getActivity());
		mBaiduMap = mMapView.getMap();

		mBaiduMap.setOnMapLoadedCallback(this);
	}

	public void zoomMapTo(float zoom)
	{
		MapStatusUpdate msu = MapStatusUpdateFactory.zoomTo(zoom);
		mBaiduMap.setMapStatus(msu);
	}

	public void showZoomView()
	{
		View zoom = getZoomControls();
		if (zoom != null)
		{
			zoom.setVisibility(View.VISIBLE);
		}
	}

	public void hideZoomView()
	{
		View zoom = getZoomControls();
		if (zoom != null)
		{
			zoom.setVisibility(View.GONE);
		}
	}

	public ZoomControls getZoomControls()
	{
		int childCount = mMapView.getChildCount();
		View zoom = null;
		for (int i = 0; i < childCount; i++)
		{
			zoom = mMapView.getChildAt(i);
			if (zoom instanceof ZoomControls)
			{
				return (ZoomControls) zoom;
			}
		}
		return null;
	}

	public BitmapDescriptor getBitmapDescriptorFromAsset(String path)
	{
		BitmapDescriptor bmp = BitmapDescriptorFactory.fromAsset(path);
		return bmp;
	}

	public BitmapDescriptor getBitmapDescriptorFromBitmap(Bitmap bitmap)
	{
		BitmapDescriptor bmp = BitmapDescriptorFactory.fromBitmap(bitmap);
		return bmp;
	}

	public BitmapDescriptor getBitmapDescriptorFromFile(String path)
	{
		BitmapDescriptor bmp = BitmapDescriptorFactory.fromFile(path);
		return bmp;
	}

	public BitmapDescriptor getBitmapDescriptorFromPath(String path)
	{
		BitmapDescriptor bmp = BitmapDescriptorFactory.fromPath(path);
		return bmp;
	}

	public BitmapDescriptor getBitmapDescriptorFromResource(int resId)
	{
		BitmapDescriptor bmp = BitmapDescriptorFactory.fromResource(resId);
		return bmp;
	}

	public BitmapDescriptor getBitmapDescriptorFromView(View view)
	{
		BitmapDescriptor bmp = BitmapDescriptorFactory.fromView(view);
		return bmp;
	}

	public Overlay addMarkerToMap(LatLng ll, BitmapDescriptor bmp)
	{
		return addMarkerToMap(ll, bmp, null, 0);
	}

	public Overlay addMarkerToMap(LatLng ll, BitmapDescriptor bmp, Bundle data)
	{
		return addMarkerToMap(ll, bmp, data, 0);
	}

	public Overlay addMarkerToMap(LatLng ll, BitmapDescriptor bmp, Bundle data, int zIndex)
	{
		MarkerOptions markerOption = new MarkerOptions().position(ll).icon(bmp).zIndex(zIndex);
		Overlay marker = mBaiduMap.addOverlay(markerOption);
		if (data != null)
		{
			marker.setExtraInfo(data);
		}
		return marker;
	}

	public void clearMap()
	{
		mBaiduMap.clear();
	}

	public void showInfoWindow(InfoWindow infoWindow)
	{
		if (infoWindow != null)
		{
			mBaiduMap.showInfoWindow(infoWindow);
		}
	}

	public void hideInfoWindow()
	{
		mBaiduMap.hideInfoWindow();
	}

	public void openMyLocationOverlay()
	{
		mBaiduMap.setMyLocationEnabled(true);
	}

	public void closeMyLocationOverlay()
	{
		mBaiduMap.setMyLocationEnabled(false);
	}

	public void focusMapTo(LatLng ll, boolean isNeedAnimateMap)
	{
		if (ll != null)
		{
			MapStatusUpdate u = MapStatusUpdateFactory.newLatLng(ll);
			mBaiduMap.animateMapStatus(u);
		}
	}

	public void addRouteOverlayBus(TransitRouteLine line)
	{
		if (line != null)
		{
			TransitRouteOverlay overlay = new TransitRouteOverlay(mBaiduMap)
			{

				@Override
				public boolean onRouteNodeClick(int index)
				{
					return onBusRouteNodeClick(index);
				}
			};
			mBaiduMap.setOnMarkerClickListener(overlay);
			overlay.setData(line);
			overlay.addToMap();
		}
	}

	public boolean onBusRouteNodeClick(int index)
	{
		return false;
	}

	public void addRouteOverlayDriving(DrivingRouteLine line)
	{
		if (line != null)
		{
			DrivingRouteOverlay overlay = new DrivingRouteOverlay(mBaiduMap)
			{

				@Override
				public boolean onRouteNodeClick(int index)
				{
					return onDrivingRouteNodeClick(index);
				}

			};
			mBaiduMap.setOnMarkerClickListener(overlay);
			overlay.setData(line);
			overlay.addToMap();
		}
	}

	public boolean onDrivingRouteNodeClick(int index)
	{
		return false;
	}

	public void addRouteOverlayWalking(WalkingRouteLine line)
	{
		if (line != null)
		{
			WalkingRouteOverlay overlay = new WalkingRouteOverlay(mBaiduMap)
			{

				@Override
				public boolean onRouteNodeClick(int index)
				{
					return onWalkingRouteNodeClick(index);
				}

			};
			mBaiduMap.setOnMarkerClickListener(overlay);
			overlay.setData(line);
			overlay.addToMap();
		}
	}

	public boolean onWalkingRouteNodeClick(int index)
	{
		return false;
	}

	/**
	 * 定位
	 * 
	 * @param isNeedAnimateMap
	 *            是否需要移动地图到当前位置
	 */
	public void startLocation(boolean isNeedAnimateMap)
	{
		startLocation(isNeedAnimateMap, mIsLocationAllTheTime);
	}

	public void startLocation(boolean isNeedAnimateMap, boolean isLocationAllTheTime)
	{
		SDToast.showToast("定位中...");
		this.mIsNeedAnimateMap = isNeedAnimateMap;
		this.mIsLocationAllTheTime = isLocationAllTheTime;
		BaiduMapManager.getInstance().startLocation(mIsLocationAllTheTime, this); // 定位监听
	}

	public void stopLocation()
	{
		BaiduMapManager.getInstance().unRegisterLocationListener(this);
		mIsLocationAllTheTime = false;
	}

	@Override
	public void onResume()
	{
		super.onResume();
		mMapView.onResume();
	}

	@Override
	public void onPause()
	{
		super.onPause();
		mMapView.onPause();
	}

	@Override
	public void onDestroy()
	{
		super.onDestroy();
		stopLocation();
		// 关闭定位图层
		closeMyLocationOverlay();
		mMapView.onDestroy();
		mMapView = null;
	}

	public void updateScreenLatLng()
	{
		mLlTopLeft = mBaiduMap.getProjection().fromScreenLocation(new Point(0, 0));
		mLlBottomRight = mBaiduMap.getProjection().fromScreenLocation(new Point(SDViewUtil.getScreenWidth(), SDViewUtil.getScreenHeight()));
	}

	// =====================OnMapStatusChangeListener==========start================
	@Override
	public void onMapStatusChange(MapStatus mapStatus)
	{
	}

	@Override
	public void onMapStatusChangeFinish(MapStatus mapStatus)
	{
		updateScreenLatLng();
	}

	@Override
	public void onMapStatusChangeStart(MapStatus mapStatus)
	{
	}

	// =====================OnMapStatusChangeListener==========end================

	// =====================OnMapClickListener========start=====================
	@Override
	public void onMapClick(LatLng ll)
	{

	}

	@Override
	public boolean onMapPoiClick(MapPoi mp)
	{
		return false;
	}

	// =====================OnMapClickListener========end=====================

	// =======================OnMarkerClickListener=========start=====================
	@Override
	public boolean onMarkerClick(Marker marker)
	{
		return false;
	}

	// =======================OnMarkerClickListener=========end=====================

	// ====================BDLocationListener========start
	@Override
	public void onReceiveLocation(BDLocation location)
	{
		// map view 销毁后不在处理新接收的位置
		if (location == null || mMapView == null)
		{
			stopLocation();
			return;
		}
		updateScreenLatLng();
		// 此处设置开发者获取到的方向信息，顺时针0-360
		MyLocationData locData = new MyLocationData.Builder().accuracy(location.getRadius()).direction(100).latitude(location.getLatitude())
				.longitude(location.getLongitude()).build();
		mBaiduMap.setMyLocationData(locData);
		mLlCurrent = new LatLng(location.getLatitude(), location.getLongitude());
		if (mIsNeedAnimateMap)
		{
			mIsNeedAnimateMap = false;
			MapStatusUpdate u = MapStatusUpdateFactory.newLatLng(mLlCurrent);
			mBaiduMap.animateMapStatus(u);
		}

		if (mIsLocationAllTheTime)
		{

		} else
		{
			stopLocation();
		}
	}

	// ====================BDLocationListener========end

	// =================OnGetRoutePlanResultListener===========start
	@Override
	public void onGetDrivingRouteResult(DrivingRouteResult result)
	{

	}

	@Override
	public void onGetTransitRouteResult(TransitRouteResult result)
	{

	}

	@Override
	public void onGetWalkingRouteResult(WalkingRouteResult result)
	{

	}

	// =================OnGetRoutePlanResultListener===========end

	// ==========================OnMapLoadedCallback=========start
	@Override
	public void onMapLoaded()
	{
		hideZoomView();
		zoomMapTo(15.0f);
		mBaiduMap.setMyLocationConfigeration(new MyLocationConfiguration(LocationMode.NORMAL, true, null));
		openMyLocationOverlay();
		mBaiduMap.setOnMapStatusChangeListener(this); // 地图状态发生变化监听，如拖动地图
		mBaiduMap.setOnMapClickListener(this); // 地图被点击监听
		mBaiduMap.setOnMarkerClickListener(this); // 地图上面的marker标签被点击监听
	}

	// ==========================OnMapLoadedCallback=========end
}
