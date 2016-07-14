package com.fanwe.baidumap;

import android.text.TextUtils;

import com.baidu.mapapi.model.LatLng;
import com.baidu.mapapi.search.sug.OnGetSuggestionResultListener;
import com.baidu.mapapi.search.sug.SuggestionSearch;
import com.baidu.mapapi.search.sug.SuggestionSearchOption;

public class BaiduSuggestionSearch
{
	private String city;
	private String keyword;
	private LatLng latlng;
	private OnGetSuggestionResultListener listener;

	private SuggestionSearch mSearch = SuggestionSearch.newInstance();

	public BaiduSuggestionSearch city(String city)
	{
		this.city = city;
		return this;
	}

	public BaiduSuggestionSearch keyword(String keyword)
	{
		this.keyword = keyword;
		return this;
	}

	public BaiduSuggestionSearch location(LatLng latlng)
	{
		this.latlng = latlng;
		return this;
	}

	public BaiduSuggestionSearch listener(OnGetSuggestionResultListener listener)
	{
		this.listener = listener;
		mSearch.setOnGetSuggestionResultListener(this.listener);
		return this;
	}

	public void search()
	{
		SuggestionSearchOption option = getOption();
		if (option != null)
		{
			mSearch.requestSuggestion(option);
		}
	}

	private SuggestionSearchOption getOption()
	{
		SuggestionSearchOption option = new SuggestionSearchOption();

		// location
		if (latlng != null)
		{
			option.location(latlng);
		} else
		{
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
		}

		if (!TextUtils.isEmpty(keyword))
		{
			option.keyword(keyword);
		}
		return option;
	}

	public void destroy()
	{
		mSearch.destroy();
	}

}
