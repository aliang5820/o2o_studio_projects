package com.fanwe.fragment;

import java.util.ArrayList;
import java.util.List;

import android.content.Intent;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;

import com.baidu.mapapi.search.route.TransitRouteLine;
import com.baidu.mapapi.search.route.TransitRouteResult;
import com.fanwe.RouteDetailActivity;
import com.fanwe.adapter.MapBusRouteAdapter;
import com.fanwe.baidumap.BaiduMapManager;
import com.fanwe.baidumap.BaiduMapManager.OnGetBusRoutePlanResultListener;
import com.fanwe.library.utils.SDToast;
import com.fanwe.model.MapBusRouteModel;

/**
 * 
 * @author yhz
 * @create time 2014-11-13
 */
public class RouteinfoTabBusFragment extends RouteinfoTabBaseFragment
{

	private MapBusRouteAdapter mAdapter;
	private List<MapBusRouteModel> mListModel = new ArrayList<MapBusRouteModel>();
	private OnGetBusRoutePlanResultListener mListener;

	public void setmListener(OnGetBusRoutePlanResultListener listener)
	{
		this.mListener = listener;
	}

	@Override
	public void bindDefaultData()
	{
		mAdapter = new MapBusRouteAdapter(mListModel, getActivity());
		mPtrlvContent.setAdapter(mAdapter);
		mPtrlvContent.setOnItemClickListener(new OnItemClickListener()
		{
			@Override
			public void onItemClick(AdapterView<?> arg0, View arg1, int position, long id)
			{
				MapBusRouteModel model = mAdapter.getItem((int) id);
				if (model != null)
				{
					// TODO 跳到查看具体线路详细页面
					Intent intent = new Intent(getActivity(), RouteDetailActivity.class);
					intent.putExtra(RouteDetailFragment.EXTRA_ROUTE_BUS_INDEX, ((int) id));
					intent.putExtra(RouteDetailFragment.EXTRA_MODEL_MAPBASEROUTEMODEL, model);
					startActivity(intent);
				}
			}
		});
	}

	@Override
	public void requestData()
	{
		BaiduMapManager.getInstance().searchBusRouteLine(mLlStart, mLlEnd, newRoutePlanSearch(), new OnGetBusRoutePlanResultListener()
		{

			@Override
			public void onResult(TransitRouteResult result, boolean success)
			{
				if (success)
				{
					List<TransitRouteLine> listRoute = result.getRouteLines();
					List<MapBusRouteModel> listModel = MapBusRouteModel.getListBusRouteModel(listRoute);
					if (listModel != null)
					{
						mListModel = listModel;
						mAdapter.updateData(mListModel);
					} else
					{
						SDToast.showToast("未获取到公交线路");
					}
				} else
				{
					SDToast.showToast("未获取到公交线路");
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
