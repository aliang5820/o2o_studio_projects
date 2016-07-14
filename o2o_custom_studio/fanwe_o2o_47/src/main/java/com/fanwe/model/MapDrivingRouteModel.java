package com.fanwe.model;

import java.util.ArrayList;
import java.util.List;

import com.baidu.mapapi.search.route.DrivingRouteLine;
import com.baidu.mapapi.search.route.DrivingRouteLine.DrivingStep;
import com.fanwe.utils.SDDistanceUtil;
import com.fanwe.utils.SDFormatUtil;

public class MapDrivingRouteModel extends MapBaseRouteModel
{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public static List<MapDrivingRouteModel> getListMapDrivingRouteModel(List<DrivingRouteLine> listModel)
	{
		List<MapDrivingRouteModel> listReturnModel = new ArrayList<MapDrivingRouteModel>();
		if (listModel != null && listModel.size() > 0)
		{
			for (DrivingRouteLine model : listModel)
			{
				MapDrivingRouteModel routeModel = getMapDrivingRouteModel(model);
				if (routeModel != null)
				{
					listReturnModel.add(routeModel);
				}
			}
		}
		return listReturnModel;
	}

	public static MapDrivingRouteModel getMapDrivingRouteModel(DrivingRouteLine line)
	{
		MapDrivingRouteModel model = new MapDrivingRouteModel();
		if (line != null)
		{
			int distance = line.getDistance();
			int time = line.getDuration();
			String name = null;
			List<String> listStepString = new ArrayList<String>();
			List<DrivingStep> listStep = line.getAllStep();
			if (listStep != null)
			{
				for (DrivingStep step : listStep)
				{
					if (step != null)
					{
						String stepContent = step.getInstructions();
						listStepString.add(stepContent);
					}
				}
			}
			model.setDistance(SDDistanceUtil.getKmDistanceString(distance));
			model.setTime(SDFormatUtil.formatDuring(time * 1000));
			model.setName(name);
			model.setListStep(listStepString);
			if (listStepString != null && listStepString.size() > 0)
			{
				model.setName(listStepString.get(0));
			}
		}
		return model;
	}

}
