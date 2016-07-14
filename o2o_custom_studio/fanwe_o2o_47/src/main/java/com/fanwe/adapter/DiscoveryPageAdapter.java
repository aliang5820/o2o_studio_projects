package com.fanwe.adapter;

import java.util.List;

import android.app.Activity;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;

import com.fanwe.fragment.DiscoveryFragment;
import com.fanwe.library.adapter.SDFragmentPagerAdapter;
import com.fanwe.model.DiscoveryTagModel;

public class DiscoveryPageAdapter extends SDFragmentPagerAdapter<DiscoveryTagModel>
{

	public DiscoveryPageAdapter(List<DiscoveryTagModel> listModel, Activity activity, FragmentManager fm)
	{
		super(listModel, activity, fm);
	}

	@Override
	public Fragment getItemFragment(int position, DiscoveryTagModel model)
	{
		DiscoveryFragment fragment = new DiscoveryFragment();

		String tag = model.getName();
		if (!"全部".equals(tag))
		{
			fragment.setTag(model.getName());
		} else
		{
			fragment.setTag(null);
		}

		return fragment;
	}
}
