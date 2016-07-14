package com.fanwe.model;

import java.util.ArrayList;
import java.util.List;

import com.baidu.mapapi.search.core.VehicleInfo;
import com.baidu.mapapi.search.route.TransitRouteLine;
import com.baidu.mapapi.search.route.TransitRouteLine.TransitStep;
import com.fanwe.utils.SDDistanceUtil;
import com.fanwe.utils.SDFormatUtil;

public class MapBusRouteModel extends MapBaseRouteModel
{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public static List<MapBusRouteModel> getListBusRouteModel(List<TransitRouteLine> listModel)
	{
		List<MapBusRouteModel> listReturnModel = new ArrayList<MapBusRouteModel>();
		if (listModel != null && listModel.size() > 0)
		{
			for (TransitRouteLine model : listModel)
			{
				MapBusRouteModel routeModel = getBusRouteModel(model);
				if (routeModel != null)
				{
					listReturnModel.add(routeModel);
				}
			}
		}
		return listReturnModel;
	}

	public static MapBusRouteModel getBusRouteModel(TransitRouteLine line)
	{
		MapBusRouteModel model = new MapBusRouteModel();
		if (line != null)
		{
			int distance = line.getDistance();
			int time = line.getDuration();
			String name = null;
			List<String> listStepString = new ArrayList<String>();
			List<TransitStep> listStep = line.getAllStep();

			if (listStep != null)
			{
				for (TransitStep step : listStep)
				{
					if (step != null)
					{
						VehicleInfo info = step.getVehicleInfo();
						if (info != null)
						{
							name = info.getTitle();
						}
						String stepContent = step.getInstructions();
						listStepString.add(stepContent);
					}
				}
			}
			model.setDistance(SDDistanceUtil.getKmDistanceString(distance));
			model.setTime(SDFormatUtil.formatDuring(time * 1000));
			model.setName(name);
			model.setListStep(listStepString);
		}
		return model;
	}

}
