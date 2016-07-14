package com.fanwe.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.baidu.mapapi.model.LatLng;
import com.fanwe.RouteInformationActivity;
import com.fanwe.constant.Constant.TitleType;
import com.fanwe.library.title.SDTitleItem;
import com.fanwe.library.utils.SDIntentUtil;
import com.fanwe.library.utils.SDResourcesUtil;
import com.fanwe.library.utils.SDToast;
import com.fanwe.library.utils.SDViewBinder;
import com.fanwe.model.Store_infoModel;
import com.fanwe.o2o.newo2o.R;
import com.lidroid.xutils.view.annotation.ViewInject;

/**
 * 商家地图
 * 
 * @author js02
 * 
 */
public class StoreLocationFragment extends BaseBaiduMapFragment
{

	public static final String EXTRA_MODEL_MERCHANTITEMACTMODEL = "extra_model_merchantitemactmodel";

	@ViewInject(R.id.ll_bot)
	private LinearLayout mLlBot;

	private TextView mTvTitle;
	private TextView mTvContent;
	private Button mBtnSearch;
	private Button mBtnOpenLocalMap;

	private Store_infoModel mModel;

	private LatLng mLlEnd;

	@Override
	protected View onCreateContentView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState)
	{
		setmTitleType(TitleType.TITLE);
		return setContentView(R.layout.frag_store_location);
	}

	@Override
	protected void init()
	{
		getIntentData();
		initTitle();
		inflateViews();
		registeClick();
		bindData();
		addEndMarker();
	}

	private void addEndMarker()
	{
		if (mLlEnd != null)
		{
			addMarkerToMap(mLlEnd, getBitmapDescriptorFromResource(R.drawable.ic_map_merchant));
			focusMapTo(mLlEnd, true);
		}
	}

	@Override
	public void onMapLoaded()
	{
		super.onMapLoaded();
		startLocation(false);
	}

	private void inflateViews()
	{
		getActivity().getLayoutInflater().inflate(R.layout.include_map_bot_info, mLlBot);
		mLlBot.setOnClickListener(this);
		mTvTitle = (TextView) getView().findViewById(R.id.include_map_bot_info_tv_title);
		mTvContent = (TextView) getView().findViewById(R.id.include_map_bot_info_tv_content);
		mBtnSearch = (Button) getView().findViewById(R.id.include_map_bot_info_btn_search);
		mBtnOpenLocalMap = (Button) getView().findViewById(R.id.include_map_bot_info_btn_open_local_map);
	}

	private void bindData()
	{
		if (mModel != null)
		{
			SDViewBinder.setTextView(mTvTitle, mModel.getName());
			SDViewBinder.setTextView(mTvContent, mModel.getAddress());

			double lat = mModel.getYpoint();
			double lon = mModel.getXpoint();
			mLlEnd = new LatLng(lat, lon);
		}
	}

	private void getIntentData()
	{
		mModel = (Store_infoModel) getActivity().getIntent().getSerializableExtra(EXTRA_MODEL_MERCHANTITEMACTMODEL);
		if (mModel == null)
		{
			SDToast.showToast("Store_infoModel is null");
			getActivity().finish();
		}
	}

	private void initTitle()
	{
		mTitle.setMiddleTextTop(SDResourcesUtil.getString(R.string.store_location));
		mTitle.initRightItem(1);
		mTitle.getItemRight(0).setTextBot("当前位置");
	}

	@Override
	public void onCLickRight_SDTitleSimple(SDTitleItem v, int index)
	{
		startLocation(true);
	}

	private void registeClick()
	{
		mBtnSearch.setOnClickListener(this);
		mBtnOpenLocalMap.setOnClickListener(this);
	}

	@Override
	public void onClick(View v)
	{
		switch (v.getId())
		{
		case R.id.include_map_bot_info_btn_search:
			clickSearch();
			break;
		case R.id.ll_bot:
			clickLlBot();
			break;
		case R.id.include_map_bot_info_btn_open_local_map:
			clickLocalMap();
			break;
		default:
			break;
		}
	}

	private void clickLocalMap()
	{
		if (mLlEnd != null)
		{
			String name = null;
			if (mModel != null)
			{
				name = mModel.getAddress();
			}
			Intent intent = SDIntentUtil.getIntentLocalMap(mLlEnd.latitude, mLlEnd.longitude, name);
			if (intent != null)
			{
				startActivity(intent);
			} else
			{
				SDToast.showToast("打开本地地图失败");
			}
		}
	}

	private void clickLlBot()
	{
		focusMapTo(mLlEnd, true);
	}

	private void clickSearch()
	{
		if (mModel != null)
		{
			SDViewBinder.setTextView(mTvTitle, mModel.getName());
			SDViewBinder.setTextView(mTvContent, mModel.getAddress());

			double lat = mModel.getYpoint();
			double lon = mModel.getXpoint();

			Intent intent = new Intent(getActivity(), RouteInformationActivity.class);
			intent.putExtra(RouteInformationActivity.EXTRA_END_LAT, lat);
			intent.putExtra(RouteInformationActivity.EXTRA_END_LON, lon);

			intent.putExtra(RouteInformationActivity.EXTRA_END_NAME, mModel.getAddress());
			startActivity(intent);
		}

	}
}