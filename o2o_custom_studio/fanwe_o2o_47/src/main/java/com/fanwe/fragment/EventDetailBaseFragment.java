package com.fanwe.fragment;

import android.app.Activity;

import com.fanwe.EventDetailActivity;
import com.fanwe.model.Event_indexActModel;
import com.fanwe.model.Event_infoModel;

public class EventDetailBaseFragment extends BaseFragment
{

	protected Event_indexActModel mEventModel;
	protected Event_infoModel mInfoModel;

	public void setmEventModel(Event_indexActModel mEventModel)
	{
		this.mEventModel = mEventModel;
		if (mEventModel != null)
		{
			mInfoModel = mEventModel.getEvent_info();
		}
	}

	protected void requestDetail()
	{
		EventDetailActivity activity = getEventDetailActivity();
		if (activity != null)
		{
			activity.requestDetail();
		}
	}

	protected void scrollToFields()
	{
		EventDetailActivity activity = getEventDetailActivity();
		if (activity != null)
		{
			activity.scrollToFields();
		}
	}

	protected EventDetailActivity getEventDetailActivity()
	{
		EventDetailActivity activity = null;
		Activity act = getActivity();
		if (act != null && act instanceof EventDetailActivity)
		{
			activity = (EventDetailActivity) act;
		}
		return activity;
	}

}
