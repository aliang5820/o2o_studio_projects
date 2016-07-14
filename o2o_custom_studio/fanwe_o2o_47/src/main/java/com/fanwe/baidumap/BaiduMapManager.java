package com.fanwe.baidumap;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import android.app.Application;
import android.text.TextUtils;

import com.baidu.location.BDLocation;
import com.baidu.location.BDLocationListener;
import com.baidu.location.LocationClient;
import com.baidu.location.LocationClientOption;
import com.baidu.mapapi.SDKInitializer;
import com.baidu.mapapi.model.LatLng;
import com.baidu.mapapi.search.core.RouteLine;
import com.baidu.mapapi.search.core.RouteNode;
import com.baidu.mapapi.search.core.SearchResult;
import com.baidu.mapapi.search.route.DrivingRouteLine;
import com.baidu.mapapi.search.route.DrivingRouteLine.DrivingStep;
import com.baidu.mapapi.search.route.DrivingRoutePlanOption;
import com.baidu.mapapi.search.route.DrivingRouteResult;
import com.baidu.mapapi.search.route.OnGetRoutePlanResultListener;
import com.baidu.mapapi.search.route.PlanNode;
import com.baidu.mapapi.search.route.RoutePlanSearch;
import com.baidu.mapapi.search.route.TransitRouteLine;
import com.baidu.mapapi.search.route.TransitRouteLine.TransitStep;
import com.baidu.mapapi.search.route.TransitRoutePlanOption;
import com.baidu.mapapi.search.route.TransitRouteResult;
import com.baidu.mapapi.search.route.WalkingRouteLine;
import com.baidu.mapapi.search.route.WalkingRouteLine.WalkingStep;
import com.baidu.mapapi.search.route.WalkingRoutePlanOption;
import com.baidu.mapapi.search.route.WalkingRouteResult;
import com.baidu.mapapi.utils.DistanceUtil;
import com.fanwe.constant.Constant;
import com.fanwe.library.utils.LogUtil;
import com.fanwe.library.utils.SDTypeParseUtil;

public class BaiduMapManager implements BDLocationListener
{

	private static BaiduMapManager mManager;

	private Application mApplication;

	private boolean mIsInit = false;

	private LocationClient mClient;
	private Map<BDLocationListener, BDLocationListenerInfo> mMapLocationListener = new HashMap<BDLocationListener, BaiduMapManager.BDLocationListenerInfo>();
	private List<BDLocationListener> mListLocationListenerNeedUnRegister = new ArrayList<BDLocationListener>();

	private BDLocation mBDLocation;
	private int[] mArrSuccessLocationType = new int[] { BDLocation.TypeGpsLocation, BDLocation.TypeNetWorkLocation, BDLocation.TypeOffLineLocation };

	private List<WalkingRouteLine> mListWalkingRouteLine;
	private List<TransitRouteLine> mListTransitRouteLine;
	private List<DrivingRouteLine> mListDrivingRouteLine;

	// =====================获取线路
	public List<DrivingRouteLine> getListDrivingRouteLine()
	{
		return mListDrivingRouteLine;
	}

	public List<TransitRouteLine> getListTransitRouteLine()
	{
		return mListTransitRouteLine;
	}

	public List<WalkingRouteLine> getListWalkingRouteLine()
	{
		return mListWalkingRouteLine;
	}

	public String getCurAddressShort()
	{
		String address = getCurAddress();
		if (!TextUtils.isEmpty(address))
		{
			if (address.contains(Constant.EARN_SUB_CHAR))
			{
				address = address.substring(address.indexOf(Constant.EARN_SUB_CHAR) + 1);
			}
		}
		return address;
	}

	public String getCurAddress()
	{
		String address = null;
		if (mBDLocation != null)
		{
			address = mBDLocation.getAddrStr();
		}
		return address;
	}

	public String getCity()
	{
		String city = null;
		if (mBDLocation != null)
		{
			city = mBDLocation.getCity();
		}
		return city;
	}

	public String getCityShort()
	{
		String city = getCity();
		if (!TextUtils.isEmpty(city))
		{
			if (city.contains("市"))
			{
				city = city.replace("市", "");
			}
		}
		return city;
	}

	public String getDistrict()
	{
		String district = null;
		if (mBDLocation != null)
		{
			district = mBDLocation.getDistrict();
		}
		return district;
	}

	public String getDistrictShort()
	{
		String district = getDistrict();
		if (!TextUtils.isEmpty(district))
		{
			if (district.length() >= 3)
			{
				if (district.endsWith("区") || district.endsWith("市") || district.endsWith("县"))
				{
					district = district.substring(0, district.length() - 1);
				}
			}
		}
		return district;
	}

	public boolean hasLocationSuccess()
	{
		return hasLocationSuccess(mBDLocation);
	}

	public boolean hasLocationSuccess(BDLocation location)
	{
		boolean success = false;
		if (location != null)
		{
			int type = location.getLocType();
			for (int typeSuccess : mArrSuccessLocationType)
			{
				if (type == typeSuccess)
				{
					success = true;
					break;
				}
			}
		}
		return success;
	}

	public BDLocation getLocation()
	{
		return mBDLocation;
	}

	/**
	 * 纬度(ypoint)
	 * 
	 * @return
	 */
	public double getLatitude()
	{
		double latitude = 0;
		if (mBDLocation != null)
		{
			latitude = mBDLocation.getLatitude();
		}
		return latitude;
	}

	/**
	 * 经度(xpoint)
	 * 
	 * @return
	 */
	public double getLongitude()
	{
		double longitude = 0;
		if (mBDLocation != null)
		{
			longitude = mBDLocation.getLongitude();
		}
		return longitude;
	}

	public float getRadius()
	{
		float radius = 0;
		if (mBDLocation != null)
		{
			radius = mBDLocation.getRadius();
		}
		return radius;
	}

	public static BaiduMapManager getInstance()
	{
		if (mManager == null)
		{
			mManager = new BaiduMapManager();
		}
		return mManager;
	}

	public void init(Application application)
	{
		this.mApplication = application;
		if (!mIsInit)
		{
			SDKInitializer.initialize(mApplication);
			mClient = new LocationClient(mApplication);
			mClient.setLocOption(getLocationClientOption());
			mClient.registerLocationListener(this);
			mIsInit = true;
		}
	}

	/**
	 * 获得默认定位配置
	 * 
	 * @return
	 */
	private LocationClientOption getLocationClientOption()
	{
		LocationClientOption option = new LocationClientOption();
		option.setOpenGps(true);// 打开gps
		option.setCoorType("bd09ll"); // 设置坐标类型
		option.setIsNeedAddress(true);// 设置是否返回地理信息
		option.setScanSpan(3 * 1000);
		option.setNeedDeviceDirect(true);
		return option;
	}

	/**
	 * 获得当前位置的LatLng
	 * 
	 * @return
	 */
	public LatLng getLatLngCurrent()
	{
		LatLng ll = null;
		double lat = getLatitude();
		double lon = getLongitude();
		if (lat != 0 && lon != 0)
		{
			ll = new LatLng(lat, lon);
		}
		return ll;
	}

	public double getDistance(LatLng llStart, LatLng llEnd)
	{
		if (llStart != null && llEnd != null)
		{
			return DistanceUtil.getDistance(llStart, llEnd);
		} else
		{
			return 0;
		}
	}

	public double getDistanceFromMyLocation(LatLng llEnd)
	{
		return getDistance(getLatLngCurrent(), llEnd);
	}

	/**
	 * 获得我的位置和传进来的经纬度间的距离
	 * 
	 * @param latitude
	 *            纬度(Ypoint)
	 * @param longitude
	 *            经度(Xpoint)
	 * @return
	 */
	public double getDistanceFromMyLocation(double latitude, double longitude)
	{
		return getDistance(getLatLngCurrent(), new LatLng(latitude, longitude));
	}

	/**
	 * 获得我的位置和传进来的经纬度间的距离
	 * 
	 * @param latitude
	 *            纬度(Ypoint)
	 * @param longitude
	 *            经度(Xpoint)
	 * @return
	 */
	public double getDistanceFromMyLocation(String latitude, String longitude)
	{
		return getDistance(getLatLngCurrent(), new LatLng(SDTypeParseUtil.getDouble(latitude, 0), SDTypeParseUtil.getDouble(longitude, 0)));
	}

	public LatLng getLatLngFromRouteLineStep(RouteLine line, int index)
	{
		LatLng nodeLocation = null;
		if (line != null)
		{
			List listStep = line.getAllStep();
			if (listStep != null && listStep.size() > 0 && listStep.size() > index)
			{
				Object step = null;
				if (index < 0)
				{
					step = listStep.get(listStep.size() - 1);
				} else
				{
					step = listStep.get(index);
				}

				if (step instanceof DrivingStep)
				{
					DrivingStep dStep = (DrivingStep) step;
					RouteNode node = dStep.getEntrance();
					if (node != null)
					{
						nodeLocation = node.getLocation();
					}
				} else if (step instanceof WalkingStep)
				{
					WalkingStep dStep = (WalkingStep) step;
					RouteNode node = dStep.getEntrance();
					if (node != null)
					{
						nodeLocation = node.getLocation();
					}
				} else if (step instanceof TransitStep)
				{
					TransitStep dStep = (TransitStep) step;
					RouteNode node = dStep.getEntrance();
					if (node != null)
					{
						nodeLocation = node.getLocation();
					}
				}
			}
		}
		return nodeLocation;
	}

	public String getTitleFromRouteLineStep(RouteLine line, int index)
	{
		String nodeTitle = null;
		if (line != null)
		{
			List listStep = line.getAllStep();
			if (listStep != null && listStep.size() > 0 && listStep.size() > index)
			{
				Object step = null;
				if (index < 0)
				{
					step = listStep.get(listStep.size() - 1);
				} else
				{
					step = listStep.get(index);
				}

				if (step instanceof DrivingStep)
				{
					DrivingStep dStep = (DrivingStep) step;
					nodeTitle = dStep.getInstructions();
				} else if (step instanceof WalkingStep)
				{
					WalkingStep dStep = (WalkingStep) step;
					nodeTitle = dStep.getInstructions();
				} else if (step instanceof TransitStep)
				{
					TransitStep dStep = (TransitStep) step;
					nodeTitle = dStep.getInstructions();
				}
			}
		}
		return nodeTitle;
	}

	public void startLocation(BDLocationListener listener)
	{
		startLocation(false, listener);
	}

	public void startLocation(boolean locationAllTheTime, BDLocationListener listener)
	{
		if (listener != null)
		{
			BDLocationListenerInfo listenerInfo = new BDLocationListenerInfo();
			listenerInfo.listener = listener;
			listenerInfo.locationAllTheTime = locationAllTheTime;
			mMapLocationListener.put(listener, listenerInfo);

			mClient.start();
		}
	}

	// 监听相关
	public void unRegisterLocationListener(BDLocationListener listener)
	{
		addToUnRegisterList(listener);
	}

	private void addToUnRegisterList(BDLocationListener listener)
	{
		if (listener != null)
		{
			if (!mListLocationListenerNeedUnRegister.contains(listener))
			{
				mListLocationListenerNeedUnRegister.add(listener);
			}
		}
	}

	/**
	 * 结束定位
	 */
	private void stopLocation()
	{
		mClient.stop();
		LogUtil.e("stopLocation, isInLocation:" + isInLocation());
	}

	public boolean isInLocation()
	{
		return mClient.isStarted();
	}

	@Override
	public void onReceiveLocation(BDLocation location)
	{
		LogUtil.e("onReceiveLocation,isInLocation:" + isInLocation());
		if (hasLocationSuccess(location))
		{
			mBDLocation = location;
		}

		for (BDLocationListenerInfo item : mMapLocationListener.values())
		{
			item.listener.onReceiveLocation(location);
			if (!item.locationAllTheTime)
			{
				addToUnRegisterList(item.listener);
			}
		}

		shouldStopLocation();
	}

	private void shouldStopLocation()
	{
		for (BDLocationListener listener : mListLocationListenerNeedUnRegister)
		{
			mMapLocationListener.remove(listener);
		}
		mListLocationListenerNeedUnRegister.clear();

		if (mMapLocationListener.isEmpty())
		{
			stopLocation();
		}
	}

	// =================================搜索公交路线=======================start

	public void searchBusRouteLine(LatLng start, LatLng end, RoutePlanSearch search, final OnGetBusRoutePlanResultListener listener)
	{
		if (start != null && end != null && search != null)
		{
			search.setOnGetRoutePlanResultListener(new OnGetRoutePlanResultListener()
			{
				@Override
				public void onGetWalkingRouteResult(WalkingRouteResult arg0)
				{
				}

				@Override
				public void onGetTransitRouteResult(TransitRouteResult result)
				{
					if (result != null && result.error == SearchResult.ERRORNO.NO_ERROR)
					{
						mListTransitRouteLine = result.getRouteLines();
						if (listener != null)
						{
							listener.onResult(result, true);
						}
					} else
					{
						if (listener != null)
						{
							listener.onResult(result, false);
						}
					}
					if (listener != null)
					{
						listener.onFinish();
					}
				}

				@Override
				public void onGetDrivingRouteResult(DrivingRouteResult arg0)
				{
				}
			});
			search.transitSearch(new TransitRoutePlanOption().city(getCity()).from(PlanNode.withLocation(start)).to(PlanNode.withLocation(end)));
		}
	}

	// =================================搜索公交路线=======================end

	// =================================搜索驾车路线=======================start

	public void searchDrivingRouteLine(LatLng start, LatLng end, RoutePlanSearch search, final OnGetDrivingRoutePlanResultListener listener)
	{
		if (start != null && end != null && search != null)
		{
			search.setOnGetRoutePlanResultListener(new OnGetRoutePlanResultListener()
			{

				@Override
				public void onGetWalkingRouteResult(WalkingRouteResult arg0)
				{
				}

				@Override
				public void onGetTransitRouteResult(TransitRouteResult arg0)
				{
				}

				@Override
				public void onGetDrivingRouteResult(DrivingRouteResult result)
				{
					if (result != null && result.error == SearchResult.ERRORNO.NO_ERROR)
					{
						mListDrivingRouteLine = result.getRouteLines();
						if (listener != null)
						{
							listener.onResult(result, true);
						}
					} else
					{
						if (listener != null)
						{
							listener.onResult(result, false);
						}
					}
					if (listener != null)
					{
						listener.onFinish();
					}
				}
			});
			search.drivingSearch(new DrivingRoutePlanOption().from(PlanNode.withLocation(start)).to(PlanNode.withLocation(end)));
		}
	}

	// =================================搜索驾车路线=======================end

	// =================================搜索步行路线=======================start

	public void searchWalkingRouteLine(LatLng start, LatLng end, RoutePlanSearch search, final OnGetWalkingRoutePlanResultListener listener)
	{
		if (start != null && end != null && search != null)
		{
			search.setOnGetRoutePlanResultListener(new OnGetRoutePlanResultListener()
			{

				@Override
				public void onGetWalkingRouteResult(WalkingRouteResult result)
				{
					if (result != null && result.error == SearchResult.ERRORNO.NO_ERROR)
					{
						mListWalkingRouteLine = result.getRouteLines();
						if (listener != null)
						{
							listener.onResult(result, true);
						}
					} else
					{
						if (listener != null)
						{
							listener.onResult(result, false);
						}
					}
					if (listener != null)
					{
						listener.onFinish();
					}
				}

				@Override
				public void onGetTransitRouteResult(TransitRouteResult arg0)
				{
				}

				@Override
				public void onGetDrivingRouteResult(DrivingRouteResult arg0)
				{
				}
			});
			search.walkingSearch(new WalkingRoutePlanOption().from(PlanNode.withLocation(start)).to(PlanNode.withLocation(end)));
		}
	}

	// =================================搜索步行路线=======================end

	/**
	 * 搜索公交线路监听
	 * 
	 * @author js02
	 * 
	 */
	public interface OnGetBusRoutePlanResultListener
	{
		public void onResult(TransitRouteResult result, boolean success);

		public void onFinish();
	}

	/**
	 * 搜索驾车线路监听
	 * 
	 * @author js02
	 * 
	 */
	public interface OnGetDrivingRoutePlanResultListener
	{
		public void onResult(DrivingRouteResult result, boolean success);

		public void onFinish();
	}

	/**
	 * 搜索步行线路监听
	 * 
	 * @author js02
	 * 
	 */
	public interface OnGetWalkingRoutePlanResultListener
	{
		public void onResult(WalkingRouteResult result, boolean success);

		public void onFinish();
	}

	private class BDLocationListenerInfo
	{
		public BDLocationListener listener;
		public boolean locationAllTheTime;
	}

}
