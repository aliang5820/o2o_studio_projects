package com.fanwe.fragment;

import java.util.List;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.LinearLayout.LayoutParams;
import android.widget.TextView;

import com.fanwe.TuanListActivity;
import com.fanwe.adapter.HomeForenoticeYouhuiAdapter;
import com.fanwe.model.GoodsModel;
import com.fanwe.o2o.newo2o.R;
import com.fanwe.utils.SDTimerDown;
import com.fanwe.utils.SDTimerDown.SDTimerDownListener;
import com.lidroid.xutils.view.annotation.ViewInject;

/**
 * 优惠预告(米果定制fragment)
 * 
 * @author Administrator
 * 
 */
public class HomeForenoticeYouhui extends HomeBaseFragment
{

	@ViewInject(R.id.tv_more)
	private TextView mTv_more;

	@ViewInject(R.id.tv_time_left)
	private TextView mTv_time_left;

	@ViewInject(R.id.ll_content)
	private LinearLayout mLl_content;

	private SDTimerDown mTimerDown = new SDTimerDown();

	@Override
	protected View onCreateContentView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState)
	{
		return setContentView(R.layout.frag_home_forenotice_youhui);
	}

	@Override
	protected void init()
	{
		bindData();
		registerClick();
	}

	private void bindData()
	{
		if (!toggleFragmentView(mIndexModel))
		{
			return;
		}

		List<GoodsModel> listModel = mIndexModel.getSeckill();
		if (!toggleFragmentView(listModel))
		{
			return;
		}

		mLl_content.removeAllViews();
		HomeForenoticeYouhuiAdapter adapter = new HomeForenoticeYouhuiAdapter(listModel, getActivity());
		int listSize = listModel.size();
		LinearLayout.LayoutParams param = new LinearLayout.LayoutParams(0, LayoutParams.WRAP_CONTENT, 1);
		for (int i = 0; i < listSize; i++)
		{
			View view = adapter.getView(i, null, null);
			mLl_content.addView(view, param);
		}

		long leftTime = mIndexModel.getLeft_time();
		if (leftTime > 0)
		{
			mTimerDown.startCount(mTv_time_left, leftTime, new SDTimerDownListener()
			{

				@Override
				public void onTickFinish()
				{

				}

				@Override
				public void onTick()
				{

				}

				@Override
				public void onStart()
				{

				}
			});
		}
	}

	private void registerClick()
	{
		mTv_more.setOnClickListener(new OnClickListener()
		{

			@Override
			public void onClick(View v)
			{
				clickMore();
			}
		});
	}

	protected void clickMore()
	{
		Intent intent = new Intent(getActivity(), TuanListActivity.class);
		intent.putExtra(TuanListFragment.EXTRA_NOTICE, 1);
		startActivity(intent);
	}

	@Override
	public void onDestroy()
	{
		mTimerDown.stopCount();
		super.onDestroy();
	}

}
