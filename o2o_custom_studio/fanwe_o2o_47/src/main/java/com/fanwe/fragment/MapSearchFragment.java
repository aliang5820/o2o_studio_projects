package com.fanwe.fragment;

import java.util.List;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.baidu.mapapi.map.BitmapDescriptor;
import com.baidu.mapapi.map.InfoWindow;
import com.baidu.mapapi.map.InfoWindow.OnInfoWindowClickListener;
import com.baidu.mapapi.map.MapStatus;
import com.baidu.mapapi.map.Marker;
import com.baidu.mapapi.model.LatLng;
import com.baidu.mapapi.search.geocode.GeoCodeResult;
import com.baidu.mapapi.search.geocode.OnGetGeoCoderResultListener;
import com.baidu.mapapi.search.geocode.ReverseGeoCodeResult;
import com.fanwe.EventDetailActivity;
import com.fanwe.RouteInformationActivity;
import com.fanwe.StoreDetailActivity;
import com.fanwe.TuanDetailActivity;
import com.fanwe.YouHuiDetailActivity;
import com.fanwe.baidumap.BaiduGeoCoder;
import com.fanwe.constant.Constant.SearchTypeMap;
import com.fanwe.constant.Constant.TitleType;
import com.fanwe.library.title.SDTitleItem;
import com.fanwe.library.utils.SDCollectionUtil;
import com.fanwe.library.utils.SDIntentUtil;
import com.fanwe.library.utils.SDToast;
import com.fanwe.library.utils.SDViewBinder;
import com.fanwe.model.MapSearchBaseModel;
import com.fanwe.model.RequestModel;
import com.fanwe.o2o.newo2o.R;
import com.lidroid.xutils.http.HttpHandler;

/**
 * 地图附近
 * 
 * @author js02
 * 
 */
public class MapSearchFragment extends BaseBaiduMapFragment
{

	public static final String EXTRA_BASE_LIST_MODEL_INDEX = "extra_base_list_model_index";

	private int mSearchType;
	private String mStrTitle = "地图";

	private LinearLayout mLlBot;

	private List<MapSearchBaseModel> mListModel;

	protected HttpHandler<String> mHandler;

	private BaiduGeoCoder mGeoCoder = new BaiduGeoCoder();

	/**
	 * 传进来的值请引用SearchTypeMap类的常量
	 */
	public void setmSearchType(int mSearchType)
	{
		this.mSearchType = mSearchType;
	}

	public void setmStrTitle(String mStrTitle)
	{
		if (!TextUtils.isEmpty(mStrTitle))
		{
			this.mStrTitle = mStrTitle;
		}
	}

	@Override
	protected View onCreateContentView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState)
	{
		setmTitleType(TitleType.TITLE);
		return setContentView(R.layout.frag_map_search);
	}

	@Override
	protected void init()
	{
		findViews();
		initTitle();
	}

	private void findViews()
	{
		mLlBot = (LinearLayout) getView().findViewById(R.id.ll_bot);
	}

	@Override
	public void onMapLoaded()
	{
		super.onMapLoaded();
		startLocation(true);
	}

	private void initTitle()
	{
		mTitle.setMiddleTextTop(mStrTitle);
		mTitle.initRightItem(1);
		mTitle.getItemRight(0).setTextBot("当前位置");
	}

	@Override
	public void onCLickRight_SDTitleSimple(SDTitleItem v, int index)
	{
		startLocation(true);
	}

	protected void stopRequest()
	{
		if (mHandler != null)
		{
			mHandler.cancel();
		}
	}

	protected void putScreenLatLng(RequestModel model)
	{
		if (model != null)
		{
			if (getLatLngTopLeft() != null)
			{
				model.put("latitude_top", getLatLngTopLeft().latitude);
				model.put("longitude_left", getLatLngTopLeft().longitude);
			}
			if (getLatLngBottomRight() != null)
			{
				model.put("latitude_bottom", getLatLngBottomRight().latitude);
				model.put("longitude_right", getLatLngBottomRight().longitude);
			}
		}
	}

	protected HttpHandler<String> requestMapsearch()
	{
		return null;
	}

	protected void addOverlays(MapSearchModelSupplier model)
	{
		if (model != null)
		{
			addOverlays(model.getListMapSearchModel());
		}
	}

	protected void addOverlays(List<MapSearchBaseModel> listModel)
	{
		mListModel = listModel;
		if (!SDCollectionUtil.isEmpty(listModel))
		{
			clearMap();
			MapSearchBaseModel model = null;
			for (int i = 0; i < listModel.size(); i++)
			{
				model = listModel.get(i);
				if (model != null)
				{
					double xpoint = model.getXpoint();
					double ypoint = model.getYpoint();

					LatLng ll = new LatLng(ypoint, xpoint);

					Bundle data = new Bundle();
					data.putInt(EXTRA_BASE_LIST_MODEL_INDEX, i);
					addMarkerToMap(ll, getMark(), data);
				}
			}
		}
	}

	/**
	 * 根据type获取类型图标
	 * 
	 * @param type
	 * @return
	 */
	private BitmapDescriptor getMark()
	{
		switch (mSearchType)
		{
		case SearchTypeMap.YOU_HUI:
			return getBitmapDescriptorFromResource(R.drawable.ic_map_youhui);
		case SearchTypeMap.EVENT:
			return getBitmapDescriptorFromResource(R.drawable.ic_map_event);
		case SearchTypeMap.TUAN:
			return getBitmapDescriptorFromResource(R.drawable.ic_map_tuan);
		case SearchTypeMap.STORE:
			return getBitmapDescriptorFromResource(R.drawable.ic_map_merchant);
		default:
			return getBitmapDescriptorFromResource(R.drawable.ic_map_default);
		}
	}

	@Override
	public void onMapStatusChangeFinish(MapStatus mapStatus)
	{
		super.onMapStatusChangeFinish(mapStatus);
		mHandler = requestMapsearch();
	}

	@Override
	public boolean onMarkerClick(Marker marker)
	{
		super.onMarkerClick(marker);
		MapSearchBaseModel model = showInfoWindow(marker);
		// TODO 底部信息绑定
		bindBottomData(model);
		return true;
	}

	/**
	 * 显示marker点击弹出窗口
	 * 
	 * @param marker
	 */
	private MapSearchBaseModel showInfoWindow(Marker marker)
	{
		hideInfoWindow();
		final MapSearchBaseModel model = SDCollectionUtil.get(mListModel, marker.getExtraInfo().getInt(EXTRA_BASE_LIST_MODEL_INDEX));
		if (model != null)
		{
			View mPopView = getActivity().getLayoutInflater().inflate(R.layout.pop_act_nearbyshopsmap, null);

			TextView tvName = (TextView) mPopView.findViewById(R.id.act_nearbyshoppopview_tv_name);
			TextView tvSubName = (TextView) mPopView.findViewById(R.id.act_nearbyshoppopview_tv_sub_name);

			tvName.setText(model.getName());

			OnInfoWindowClickListener listener = new OnInfoWindowClickListener()
			{
				public void onInfoWindowClick()
				{
					Intent intent = new Intent();
					switch (mSearchType)
					{
					case SearchTypeMap.YOU_HUI:
						intent.putExtra(YouHuiDetailActivity.EXTRA_YOUHUI_ID, model.getId());
						intent.setClass(getActivity(), YouHuiDetailActivity.class);
						break;
					case SearchTypeMap.EVENT:
						intent.putExtra(EventDetailActivity.EXTRA_EVENT_ID, model.getId());
						intent.setClass(getActivity(), EventDetailActivity.class);
						break;
					case SearchTypeMap.TUAN:
						intent.putExtra(TuanDetailActivity.EXTRA_GOODS_ID, model.getId());
						intent.setClass(getActivity(), TuanDetailActivity.class);
						break;
					case SearchTypeMap.STORE:
						intent.putExtra(StoreDetailActivity.EXTRA_MERCHANT_ID, model.getId());
						intent.setClass(getActivity(), StoreDetailActivity.class);
						break;

					default:
						break;
					}
					startActivity(intent);
				}
			};
			showInfoWindow(new InfoWindow(getBitmapDescriptorFromView(mPopView), marker.getPosition(), 0, listener));
		}
		return model;
	}

	/**
	 * 绑定底部信息
	 * 
	 * @param model
	 */
	private void bindBottomData(final MapSearchBaseModel model)
	{
		if (model != null)
		{
			mLlBot.removeAllViews();
			getActivity().getLayoutInflater().inflate(R.layout.include_map_bot_info, mLlBot);
			TextView tvTitle = (TextView) mLlBot.findViewById(R.id.include_map_bot_info_tv_title);
			final TextView tvContent = (TextView) mLlBot.findViewById(R.id.include_map_bot_info_tv_content);
			Button btnSearch = (Button) mLlBot.findViewById(R.id.include_map_bot_info_btn_search);
			Button btnOpenLocalMap = (Button) mLlBot.findViewById(R.id.include_map_bot_info_btn_open_local_map);

			SDViewBinder.setTextView(tvTitle, model.getName());

			String address = model.getAddress();
			if (!TextUtils.isEmpty(address))
			{
				tvContent.setText(address);
			} else
			{
				LatLng location = new LatLng(model.getYpoint(), model.getXpoint());
				mGeoCoder.listener(new OnGetGeoCoderResultListener()
				{

					@Override
					public void onGetReverseGeoCodeResult(ReverseGeoCodeResult result)
					{
						if (result != null)
						{
							String address = result.getAddress();
							model.setAddress(address);
							SDViewBinder.setTextView(tvContent, address);
						}
					}

					@Override
					public void onGetGeoCodeResult(GeoCodeResult arg0)
					{

					}
				}).location(location).reverseGeoCode();
			}

			btnSearch.setOnClickListener(new OnClickListener()
			{
				@Override
				public void onClick(View view)
				{
					double lat = model.getYpoint();
					double lon = model.getXpoint();

					Intent intent = new Intent(getActivity(), RouteInformationActivity.class);
					intent.putExtra(RouteInformationActivity.EXTRA_END_LAT, lat);
					intent.putExtra(RouteInformationActivity.EXTRA_END_LON, lon);

					intent.putExtra(RouteInformationActivity.EXTRA_END_NAME, model.getAddress());
					startActivity(intent);
				}
			});

			btnOpenLocalMap.setOnClickListener(new OnClickListener()
			{
				@Override
				public void onClick(View v)
				{
					double lat = model.getYpoint();
					double lon = model.getXpoint();
					String name = model.getAddress();
					Intent intent = SDIntentUtil.getIntentLocalMap(lat, lon, name);
					if (intent != null)
					{
						startActivity(intent);
					} else
					{
						SDToast.showToast("打开本地地图失败");
					}

				}
			});
		}
	}

	@Override
	public void onMapClick(LatLng ll)
	{
		super.onMapClick(ll);
		hideInfoWindow();
	}

	@Override
	public void onDestroy()
	{
		mGeoCoder.destroy();
		super.onDestroy();
	}

	public interface MapSearchModelSupplier
	{
		public List<MapSearchBaseModel> getListMapSearchModel();
	}
}