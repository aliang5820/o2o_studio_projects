package com.fanwe.baidumap;

import android.text.TextUtils;

import com.baidu.mapapi.model.LatLng;
import com.baidu.mapapi.search.geocode.GeoCodeOption;
import com.baidu.mapapi.search.geocode.GeoCoder;
import com.baidu.mapapi.search.geocode.OnGetGeoCoderResultListener;
import com.baidu.mapapi.search.geocode.ReverseGeoCodeOption;

public class BaiduGeoCoder
{
	private String city;
	private String address;
	private LatLng location;
	private OnGetGeoCoderResultListener listener;

	private GeoCoder mSearch = GeoCoder.newInstance();

	public BaiduGeoCoder city(String city)
	{
		this.city = city;
		return this;
	}

	public BaiduGeoCoder address(String address)
	{
		this.address = address;
		return this;
	}

	public BaiduGeoCoder location(LatLng location)
	{
		this.location = location;
		return this;
	}

	public BaiduGeoCoder listener(OnGetGeoCoderResultListener listener)
	{
		this.listener = listener;
		mSearch.setOnGetGeoCodeResultListener(this.listener);
		return this;
	}

	/**
	 * 地址转经纬度
	 */
	public void geocode()
	{
		GeoCodeOption option = getOptionGeocode();
		if (option != null)
		{
			mSearch.geocode(option);
		}
	}

	public GeoCodeOption getOptionGeocode()
	{
		GeoCodeOption option = new GeoCodeOption();

		// city
		if (TextUtils.isEmpty(city))
		{
			city = BaiduMapManager.getInstance().getCity();
		}
		if (TextUtils.isEmpty(city))
		{
			BaiduMapManager.getInstance().startLocation(null);
			return null;
		}
		option.city(city);

		// address
		if (TextUtils.isEmpty(address))
		{
			return null;
		}
		option.address(address);

		return option;
	}

	/**
	 * 经纬度转地址
	 */
	public void reverseGeoCode()
	{
		ReverseGeoCodeOption option = getOptionReverseGeocode();
		if (option != null)
		{
			mSearch.reverseGeoCode(option);
		}
	}

	public ReverseGeoCodeOption getOptionReverseGeocode()
	{
		ReverseGeoCodeOption option = new ReverseGeoCodeOption();
		if (location == null)
		{
			return null;
		}
		option.location(location);

		return option;
	}

	public void destroy()
	{
		mSearch.destroy();
	}
}
