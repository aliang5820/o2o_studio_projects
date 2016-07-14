package com.fanwe.fragment;

import java.util.ArrayList;
import java.util.List;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import com.baidu.mapapi.map.InfoWindow;
import com.baidu.mapapi.model.LatLng;
import com.baidu.mapapi.search.core.RouteLine;
import com.baidu.mapapi.search.route.DrivingRouteLine;
import com.baidu.mapapi.search.route.TransitRouteLine;
import com.baidu.mapapi.search.route.WalkingRouteLine;
import com.fanwe.adapter.RouteStepDetaiAdapter;
import com.fanwe.baidumap.BaiduMapManager;
import com.fanwe.constant.Constant.TitleType;
import com.fanwe.library.customview.SlidingDrawer;
import com.fanwe.library.customview.SlidingDrawer.OnDrawerCloseListener;
import com.fanwe.library.customview.SlidingDrawer.OnDrawerOpenListener;
import com.fanwe.library.title.SDTitleItem;
import com.fanwe.library.utils.SDViewBinder;
import com.fanwe.library.utils.SDViewUtil;
import com.fanwe.model.MapBaseRouteModel;
import com.fanwe.o2o.newo2o.R;
import com.lidroid.xutils.view.annotation.ViewInject;

/**
 * 线路详情
 * 
 * @author js02
 * 
 */
public class RouteDetailFragment extends BaseBaiduMapFragment
{

	public static final String EXTRA_MODEL_MAPBASEROUTEMODEL = "extra_model_mapbaseroutemodel";

	public static final String EXTRA_ROUTE_BUS_INDEX = "extra_route_bus_index";
	public static final String EXTRA_ROUTE_DRIVING_INDEX = "extra_route_driving_index";
	public static final String EXTRA_ROUTE_WALKING_INDEX = "extra_route_walking_index";

	@ViewInject(R.id.act_route_detail_sd_drawer)
	private SlidingDrawer mSdDrawer;

	@ViewInject(R.id.act_route_detail_iv_drawer_arrow)
	private ImageView mIvDrawerArrow;

	@ViewInject(R.id.act_route_detail_ll_drawer_handle)
	private LinearLayout mLlDrawerHandle;

	@ViewInject(R.id.act_route_detail_tv_name)
	private TextView mTvName;

	@ViewInject(R.id.act_route_detail_tv_time)
	private TextView mTvTime;

	@ViewInject(R.id.act_route_detail_tv_distance)
	private TextView mTvDistance;

	@ViewInject(R.id.act_route_detail_ll_drawer_content)
	private LinearLayout mLlDrawerContent;

	@ViewInject(R.id.act_route_detail_lv_content)
	private ListView mLvContent;

	private List<String> mListStep = new ArrayList<String>();

	private RouteStepDetaiAdapter mAdapter = null;

	private MapBaseRouteModel mModel = null;

	private int mBusIndex;
	private int mDrivingIndex;
	private int mWalkingIndex;

	private LatLng mLlStart = null;
	private LatLng mLlEnd = null;

	private RouteLine mRouteLine = null;

	@Override
	protected View onCreateContentView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState)
	{
		setmTitleType(TitleType.TITLE);
		return setContentView(R.layout.frag_route_detail);
	}

	@Override
	protected void init()
	{
		initTitle();
		getIntentData();
		initSlidingDrawer();
		bindDefaultData();
	}

	private void initSlidingDrawer()
	{
		mSdDrawer.setOnDrawerOpenListener(new OnDrawerOpenListener()
		{

			@Override
			public void onDrawerOpened()
			{
				mIvDrawerArrow.setImageResource(R.drawable.ic_arrow_down_roange_open);
			}
		});

		mSdDrawer.setOnDrawerCloseListener(new OnDrawerCloseListener()
		{

			@Override
			public void onDrawerClosed()
			{
				mIvDrawerArrow.setImageResource(R.drawable.ic_arrow_up_roange_open);
			}
		});

	}

	@Override
	public void onMapLoaded()
	{
		super.onMapLoaded();
		startLocation(false, true);
	}

	private void bindDefaultData()
	{
		mAdapter = new RouteStepDetaiAdapter(mListStep, getActivity());
		mLvContent.setAdapter(mAdapter);
		mLvContent.setOnItemClickListener(new OnItemClickListener()
		{

			@Override
			public void onItemClick(AdapterView<?> arg0, View arg1, int arg2, long id)
			{
				mAdapter.setSelectPos((int) id);
				clickNode((int) id);
			}
		});
		clickNode(0);
		mSdDrawer.setTopOffset(SDViewUtil.getScreenHeight() / 2);
		mSdDrawer.open();
	}

	private void initTitle()
	{
		mTitle.setMiddleTextTop("线路详情");
		mTitle.initRightItem(1);
		mTitle.getItemRight(0).setTextBot("当前位置");
	}

	@Override
	public void onCLickRight_SDTitleSimple(SDTitleItem v, int index)
	{
		startLocation(true);
	}

	private void getIntentData()
	{
		mModel = (MapBaseRouteModel) getActivity().getIntent().getSerializableExtra(EXTRA_MODEL_MAPBASEROUTEMODEL);
		if (mModel != null)
		{
			SDViewBinder.setTextView(mTvName, mModel.getName());
			SDViewBinder.setTextView(mTvTime, mModel.getTime());
			SDViewBinder.setTextView(mTvDistance, mModel.getDistance());
			List<String> listStep = mModel.getListStep();
			if (listStep != null)
			{
				mListStep = listStep;
			}
		}

		mBusIndex = getActivity().getIntent().getIntExtra(EXTRA_ROUTE_BUS_INDEX, -1);
		mDrivingIndex = getActivity().getIntent().getIntExtra(EXTRA_ROUTE_DRIVING_INDEX, -1);
		mWalkingIndex = getActivity().getIntent().getIntExtra(EXTRA_ROUTE_WALKING_INDEX, -1);

		if (mBusIndex >= 0)
		{
			addBusRoute(mBusIndex);
		}

		if (mDrivingIndex >= 0)
		{
			addDrivingRoute(mDrivingIndex);
		}

		if (mWalkingIndex >= 0)
		{
			addWalkingRoute(mWalkingIndex);
		}

		if (mLlStart != null)
		{
			focusMapTo(mLlStart, true);
		}
	}

	private void addBusRoute(int index)
	{
		List<TransitRouteLine> listRoute = BaiduMapManager.getInstance().getListTransitRouteLine();
		if (listRoute != null && listRoute.size() > index && index >= 0)
		{
			TransitRouteLine line = listRoute.get(index);
			if (line != null)
			{
				mLlStart = BaiduMapManager.getInstance().getLatLngFromRouteLineStep(line, 0);
				mLlEnd = BaiduMapManager.getInstance().getLatLngFromRouteLineStep(line, -1);
				mRouteLine = line;
				addRouteOverlayBus(line);
			}
		}
	}

	private void addDrivingRoute(int index)
	{
		List<DrivingRouteLine> listRoute = BaiduMapManager.getInstance().getListDrivingRouteLine();
		if (listRoute != null && listRoute.size() > index && index >= 0)
		{
			DrivingRouteLine line = listRoute.get(index);
			if (line != null)
			{
				mLlStart = BaiduMapManager.getInstance().getLatLngFromRouteLineStep(line, 0);
				mLlEnd = BaiduMapManager.getInstance().getLatLngFromRouteLineStep(line, -1);
				mRouteLine = line;
				addRouteOverlayDriving(line);
			}
		}
	}

	private void addWalkingRoute(int index)
	{
		List<WalkingRouteLine> listRoute = BaiduMapManager.getInstance().getListWalkingRouteLine();
		if (listRoute != null && listRoute.size() > index && index >= 0)
		{
			WalkingRouteLine line = listRoute.get(index);
			if (line != null)
			{
				mLlStart = BaiduMapManager.getInstance().getLatLngFromRouteLineStep(line, 0);
				mLlEnd = BaiduMapManager.getInstance().getLatLngFromRouteLineStep(line, -1);
				mRouteLine = line;
				addRouteOverlayWalking(line);
			}
		}
	}

	@Override
	public boolean onBusRouteNodeClick(int index)
	{
		clickNode(index);
		return true;
	}

	@Override
	public boolean onDrivingRouteNodeClick(int index)
	{
		clickNode(index);
		return true;
	}

	@Override
	public boolean onWalkingRouteNodeClick(int index)
	{
		clickNode(index);
		return true;
	}

	private void clickNode(int index)
	{
		if (mListStep != null && mListStep.size() > 0 && mListStep.size() > index)
		{
			String name = mListStep.get(index);
			LatLng ll = BaiduMapManager.getInstance().getLatLngFromRouteLineStep(mRouteLine, index);
			if (name != null && ll != null)
			{
				View view = getActivity().getLayoutInflater().inflate(R.layout.pop_route_node, null);
				TextView tvContent = (TextView) view.findViewById(R.id.pop_route_node_tv_content);
				tvContent.setText(name);

				hideInfoWindow();
				showInfoWindow(new InfoWindow(view, ll, 0));
				focusMapTo(ll, true);
			}
		}

	}

}
