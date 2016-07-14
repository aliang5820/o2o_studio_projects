package com.fanwe.fragment;

import java.util.ArrayList;
import java.util.List;

import android.content.Intent;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;

import com.baidu.mapapi.search.route.DrivingRouteLine;
import com.baidu.mapapi.search.route.DrivingRouteResult;
import com.fanwe.RouteDetailActivity;
import com.fanwe.adapter.MapDrivingRouteAdapter;
import com.fanwe.baidumap.BaiduMapManager;
import com.fanwe.baidumap.BaiduMapManager.OnGetDrivingRoutePlanResultListener;
import com.fanwe.library.utils.SDToast;
import com.fanwe.model.MapDrivingRouteModel;

/**
 * 
 * @author yhz
 * @create time 2014-11-13
 */
public class RouteinfoTabDrivingFragment extends RouteinfoTabBaseFragment
{

	private MapDrivingRouteAdapter mAdapter = null;
	private List<MapDrivingRouteModel> mListModel = new ArrayList<MapDrivingRouteModel>();
	private OnGetDrivingRoutePlanResultListener mListener = null;

	public void setmListener(OnGetDrivingRoutePlanResultListener listener)
	{
		this.mListener = listener;
	}

	@Override
	public void bindDefaultData()
	{
		mAdapter = new MapDrivingRouteAdapter(mListModel, getActivity());
		mPtrlvContent.setAdapter(mAdapter);
		mPtrlvContent.setOnItemClickListener(new OnItemClickListener()
		{
			@Override
			public void onItemClick(AdapterView<?> arg0, View arg1, int position, long id)
			{
				MapDrivingRouteModel model = mAdapter.getItem((int) id);
				if (model != null)
				{
					// TODO 跳到查看具体线路详细页面
					Intent intent = new Intent(getActivity(), RouteDetailActivity.class);
					intent.putExtra(RouteDetailFragment.EXTRA_ROUTE_DRIVING_INDEX, ((int) id));
					intent.putExtra(RouteDetailFragment.EXTRA_MODEL_MAPBASEROUTEMODEL, model);
					startActivity(intent);
				}
			}
		});
	}

	@Override
	public void requestData()
	{
		BaiduMapManager.getInstance().searchDrivingRouteLine(mLlStart, mLlEnd, newRoutePlanSearch(), new OnGetDrivingRoutePlanResultListener()
		{

			@Override
			public void onResult(DrivingRouteResult result, boolean success)
			{
				if (success)
				{
					List<DrivingRouteLine> listRoute = result.getRouteLines();
					List<MapDrivingRouteModel> listModel = MapDrivingRouteModel.getListMapDrivingRouteModel(listRoute);
					if (listModel != null)
					{
						mListModel = listModel;
						mAdapter.updateData(mListModel);
					} else
					{
						SDToast.showToast("未获取到驾驶线路");
					}
				} else
				{
					SDToast.showToast("未获取到驾驶线路");
				}
				if (mListener != null)
				{
					mListener.onResult(result, success);
				}
			}

			@Override
			public void onFinish()
			{
				stopRefresh();
				if (mListener != null)
				{
					mListener.onFinish();
				}
			}
		});
	}

}
