package com.fanwe.fragment;

import java.util.ArrayList;
import java.util.List;

import android.content.Intent;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;

import com.baidu.mapapi.search.route.WalkingRouteLine;
import com.baidu.mapapi.search.route.WalkingRouteResult;
import com.fanwe.RouteDetailActivity;
import com.fanwe.adapter.MapWalkingRouteAdapter;
import com.fanwe.baidumap.BaiduMapManager;
import com.fanwe.baidumap.BaiduMapManager.OnGetWalkingRoutePlanResultListener;
import com.fanwe.library.utils.SDToast;
import com.fanwe.model.MapWalkingRouteModel;

/**
 * 
 * @author yhz
 * @create time 2014-11-13
 */
public class RouteinfoTabWalkingFragment extends RouteinfoTabBaseFragment
{

	private MapWalkingRouteAdapter mAdapter = null;
	private List<MapWalkingRouteModel> mListModel = new ArrayList<MapWalkingRouteModel>();
	private OnGetWalkingRoutePlanResultListener mListener = null;

	public void setmListener(OnGetWalkingRoutePlanResultListener listener)
	{
		this.mListener = listener;
	}

	@Override
	public void bindDefaultData()
	{
		mAdapter = new MapWalkingRouteAdapter(mListModel, getActivity());
		mPtrlvContent.setAdapter(mAdapter);
		mPtrlvContent.setOnItemClickListener(new OnItemClickListener()
		{

			@Override
			public void onItemClick(AdapterView<?> arg0, View arg1, int position, long id)
			{
				MapWalkingRouteModel model = mAdapter.getItem((int) id);
				if (model != null)
				{
					// TODO 跳到查看具体线路详细页面
					Intent intent = new Intent(getActivity(), RouteDetailActivity.class);
					intent.putExtra(RouteDetailFragment.EXTRA_ROUTE_WALKING_INDEX, ((int) id));
					intent.putExtra(RouteDetailFragment.EXTRA_MODEL_MAPBASEROUTEMODEL, model);
					startActivity(intent);
				}
			}
		});
	}

	@Override
	public void requestData()
	{
		BaiduMapManager.getInstance().searchWalkingRouteLine(mLlStart, mLlEnd, newRoutePlanSearch(), new OnGetWalkingRoutePlanResultListener()
		{

			@Override
			public void onResult(WalkingRouteResult result, boolean success)
			{
				if (success)
				{
					List<WalkingRouteLine> listRoute = result.getRouteLines();
					List<MapWalkingRouteModel> listModel = MapWalkingRouteModel.getListWalkingRouteModel(listRoute);
					if (listModel != null)
					{
						mListModel = listModel;
						mAdapter.updateData(mListModel);
					} else
					{
						SDToast.showToast("未获取到步行线路");
					}
				} else
				{
					SDToast.showToast("未获取到步行线路");
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
