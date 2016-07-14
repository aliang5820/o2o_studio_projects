package com.fanwe.fragment;

import java.util.ArrayList;
import java.util.List;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.TextView;

import com.fanwe.EventListActivity;
import com.fanwe.adapter.HomeRecommendEventAdapter;
import com.fanwe.library.customview.SDGridLinearLayout;
import com.fanwe.model.EventModel;
import com.fanwe.model.Index_indexActModel;
import com.fanwe.o2o.newo2o.R;
import com.lidroid.xutils.view.annotation.ViewInject;

/**
 * 首页推荐活动
 * 
 * @author js02
 * 
 */
public class HomeRecommendEvnetFragment extends BaseFragment
{

	@ViewInject(R.id.frag_home_recommend_event_ll_events)
	private SDGridLinearLayout mLlEvents;

	@ViewInject(R.id.tv_see_all_event)
	private TextView mTv_see_all_event;

	private List<EventModel> mListModel = new ArrayList<EventModel>();

	private Index_indexActModel mIndexModel;

	public void setmIndexModel(Index_indexActModel indexModel)
	{
		this.mIndexModel = indexModel;
		this.mListModel = mIndexModel.getEvent_list();
	}

	@Override
	protected View onCreateContentView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState)
	{
		return setContentView(R.layout.frag_home_recommend_event);
	}

	@Override
	protected void init()
	{
		bindData();
		registeClick();
	}

	private void bindData()
	{
		if (!toggleFragmentView(mListModel))
		{
			return;
		}

		HomeRecommendEventAdapter adapter = new HomeRecommendEventAdapter(mListModel, getActivity());
		mLlEvents.setAdapter(adapter);
	}

	private void registeClick()
	{
		mTv_see_all_event.setOnClickListener(new OnClickListener()
		{

			@Override
			public void onClick(View v)
			{
				clickSeeAllEvent();
			}
		});
	}

	private void clickSeeAllEvent()
	{
		startActivity(new Intent(getActivity(), EventListActivity.class));
	}

}