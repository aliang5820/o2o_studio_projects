package com.fanwe.model;

import java.util.ArrayList;
import java.util.List;

import com.baidu.mapapi.search.route.WalkingRouteLine;
import com.baidu.mapapi.search.route.WalkingRouteLine.WalkingStep;
import com.fanwe.utils.SDDistanceUtil;
import com.fanwe.utils.SDFormatUtil;

public class MapWalkingRouteModel extends MapBaseRouteModel
{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public static List<MapWalkingRouteModel> getListWalkingRouteModel(List<WalkingRouteLine> listModel)
	{
		List<MapWalkingRouteModel> listReturnModel = new ArrayList<MapWalkingRouteModel>();
		if (listModel != null && listModel.size() > 0)
		{
			for (WalkingRouteLine model : listModel)
			{
				MapWalkingRouteModel routeModel = getWalkingRouteModel(model);
				if (routeModel != null)
				{
					listReturnModel.add(routeModel);
				}
			}
		}
		return listReturnModel;
	}

	public static MapWalkingRouteModel getWalkingRouteModel(WalkingRouteLine line)
	{
		MapWalkingRouteModel model = new MapWalkingRouteModel();
		if (line != null)
		{
			int distance = line.getDistance();
			int time = line.getDuration();
			List<String> listStepString = new ArrayList<String>();
			List<WalkingStep> listStep = line.getAllStep();
			if (listStep != null)
			{
				for (WalkingStep step : listStep)
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
			model.setListStep(listStepString);
			if (listStepString != null && listStepString.size() > 0)
			{
				model.setName(listStepString.get(0));
			}
		}
		return model;
	}

}
