package com.fanwe.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.fanwe.HomeSearchActivity;
import com.fanwe.MainActivity;
import com.fanwe.MapSearchActivity;
import com.fanwe.constant.Constant.SearchTypeMap;
import com.fanwe.constant.Constant.SearchTypeNormal;
import com.fanwe.constant.Constant.TitleType;
import com.fanwe.event.EnumEventTag;
import com.fanwe.library.title.SDTitleItem;
import com.fanwe.library.utils.SDResourcesUtil;
import com.fanwe.o2o.newo2o.R;
import com.fanwe.work.AppRuntimeWorker;
import com.sunday.eventbus.SDBaseEvent;

public class StoreListContainerFragment extends BaseFragment
{

	private StoreListFragment mFragment;

	@Override
	protected View onCreateContentView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState)
	{
		setmTitleType(TitleType.TITLE);
		return setContentView(R.layout.view_container);
	}

	@Override
	protected void init()
	{
		initTitle();
		addFragments();
	}

	private void addFragments()
	{
		Bundle bundle = getActivity().getIntent().getExtras();

		mFragment = new StoreListFragment();
		mFragment.setArguments(bundle);
		getSDFragmentManager().replace(R.id.view_container_fl_content, mFragment);
	}

	private void initTitle()
	{

		String title = SDResourcesUtil.getString(R.string.supplier);
		String city = AppRuntimeWorker.getCity_name();
		if (!TextUtils.isEmpty(city))
		{
			title = title + " - " + city;
		}
		mTitle.setMiddleTextTop(title);

		if (getActivity() instanceof MainActivity)
		{
			mTitle.setLeftImageLeft(0);
		} else
		{
			mTitle.setLeftImageLeft(R.drawable.ic_arrow_left_white);
		}

		mTitle.initRightItem(2);
		mTitle.getItemRight(0).setImageLeft(R.drawable.ic_location_home_top);
		mTitle.getItemRight(1).setImageLeft(R.drawable.ic_search_home_top);
	}

	@Override
	public void onCLickRight_SDTitleSimple(SDTitleItem v, int index)
	{
		switch (index)
		{
		case 0:
			startNearbyMapSearchActivity();
			break;
		case 1:
			startHomeSearchActivity();
			break;

		default:
			break;
		}
	}

	private void startHomeSearchActivity()
	{
		Intent intent = new Intent(getActivity(), HomeSearchActivity.class);
		intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
		intent.putExtra(HomeSearchActivity.EXTRA_SEARCH_TYPE, SearchTypeNormal.MERCHANT);
		startActivity(intent);
	}

	private void startNearbyMapSearchActivity()
	{
		Intent intent = new Intent(getActivity(), MapSearchActivity.class);
		intent.putExtra(MapSearchActivity.EXTRA_SEARCH_TYPE, SearchTypeMap.STORE);
		startActivity(intent);
	}

	@Override
	public void onEventMainThread(SDBaseEvent event)
	{
		super.onEventMainThread(event);
		switch (EnumEventTag.valueOf(event.getTagInt()))
		{
		case CITY_CHANGE:
			initTitle();
			break;

		default:
			break;
		}
	}

}
