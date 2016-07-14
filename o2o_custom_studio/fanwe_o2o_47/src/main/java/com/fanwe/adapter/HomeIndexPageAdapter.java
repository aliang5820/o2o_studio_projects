package com.fanwe.adapter;

import java.util.List;

import android.app.Activity;
import android.view.View;

import com.fanwe.library.adapter.SDPagerAdapter;
import com.fanwe.library.customview.SDGridLinearLayout;
import com.fanwe.model.IndexActIndexsModel;

public class HomeIndexPageAdapter extends SDPagerAdapter<List<IndexActIndexsModel>>
{

	public HomeIndexPageAdapter(List<List<IndexActIndexsModel>> listModel, Activity activity)
	{
		super(listModel, activity);
	}

	@Override
	public View getView(View container, int position)
	{
		final SDGridLinearLayout ll = new SDGridLinearLayout(mActivity);
		ll.setmColNumber(4);
		HomeIndexAdapter adapter = new HomeIndexAdapter(getItemModel(position), mActivity);
		ll.setAdapter(adapter);
		return ll;
	}

}
