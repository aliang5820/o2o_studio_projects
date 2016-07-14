package com.fanwe.model;

import java.util.List;

public class Event_indexActModel extends BaseActModel
{
	private Event_infoModel event_info;
	private List<CommentModel> dp_list;

	public Event_infoModel getEvent_info()
	{
		return event_info;
	}

	public void setEvent_info(Event_infoModel event_info)
	{
		this.event_info = event_info;
	}

	public List<CommentModel> getDp_list()
	{
		return dp_list;
	}

	public void setDp_list(List<CommentModel> dp_list)
	{
		this.dp_list = dp_list;
	}

}